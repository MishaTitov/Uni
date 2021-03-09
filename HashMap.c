//
// Created by Михаил Титов on 12.12.2020.
//
#include "HashMap.h"

/**
 * Allocates dynamically new hash map element.
 * @param hash_func a function which "hashes" keys.
 * @param pair_cpy a function which copies pairs.
 * @param pair_cmp a function which compares pairs.
 * @param pair_free a function which frees pairs.
 * @return pointer to dynamically allocated HashMap.
 * @if_fail return NULL.
 */
HashMap *HashMapAlloc(
    HashFunc hash_func, HashMapPairCpy pair_cpy,
    HashMapPairCmp pair_cmp, HashMapPairFree pair_free){
  if (hash_func == NULL || pair_cpy == NULL || pair_cmp == NULL || pair_free == NULL){
    return NULL;
  }
  HashMap* new_hm = calloc(1, sizeof (HashMap));
  if (new_hm == NULL){
    return NULL;
  }
  new_hm->buckets = calloc(HASH_MAP_INITIAL_CAP, sizeof (Vector*));
  if (new_hm->buckets == NULL){
    return NULL;
  }
  //сразу создать все векторы
  for (size_t i = 0; i < HASH_MAP_INITIAL_CAP; ++i){
    new_hm->buckets[i] = VectorAlloc(pair_cpy, pair_cmp, pair_free);
    if (new_hm->buckets[i] == NULL){
      for (size_t j = 0; j < i; ++j){
        VectorFree(&(new_hm->buckets[j]));
      }
    }
  }
  new_hm->capacity = HASH_MAP_INITIAL_CAP;
  new_hm->size = 0;
  new_hm->pair_free = pair_free;
  new_hm->hash_func = hash_func;
  new_hm->pair_cpy = pair_cpy;
  new_hm->pair_cmp = pair_cmp;
  return new_hm;
}

/**
 * Frees a vector and the elements the vector itself allocated.
 * @param p_hash_map pointer to dynamically allocated pointer to hash_map.
 */
void HashMapFree(HashMap **p_hash_map){
  if (p_hash_map == NULL){
    return;
  }
  if (*p_hash_map == NULL){
    return;
  }
  // порядок пары->векторы->ведра->хачкарту
  for (size_t i = 0; i < (*p_hash_map)->capacity; ++i){
    VectorFree(&((*p_hash_map)->buckets[i]));
    (*p_hash_map)->buckets[i] = NULL;
  }
  free((*p_hash_map)->buckets);
  (*p_hash_map)->buckets = NULL;
  free((*p_hash_map));
  (*p_hash_map) = NULL;
}

static size_t HashFunHelper(HashMap *hash_map, KeyT key){
  return hash_map->hash_func(key) & (hash_map->capacity - 1);
}

/**
 * Inserts a new pair to the hash map.
 * The function inserts *new*, *copied*, *dynamically allocated* pair,
 * NOT the pair it receives as a parameter.
 * @param hash_map the hash map to be inserted with new element.
 * @param pair a pair the hash map would contain.
 * @return returns 1 for successful insertion, 0 otherwise.
 */
int HashMapInsert(HashMap *hash_map, Pair *pair){
  if (hash_map == NULL || pair == NULL){
    return 0;
  }
  // remake
  size_t ind = HashFunHelper(hash_map, pair->key);
  for (size_t i = 0; i < hash_map->buckets[ind]->size; ++i){
    Pair *tmp_pair = hash_map->buckets[ind]->data[i];
    if (tmp_pair->key_cmp(tmp_pair->key, pair->key) == 1){
      hash_map->pair_free(&(hash_map->buckets[ind]->data[i]));
      Pair *new_pair = hash_map->pair_cpy(pair);
      hash_map->buckets[ind]->data[i] = new_pair;
      return 1;
    }
  }
  if (VectorPushBack(hash_map->buckets[ind], pair) == 0){
    return 0;
  }
  ++hash_map->size;
  //resize increase
  if (HashMapGetLoadFactor(hash_map) > HASH_MAP_MAX_LOAD_FACTOR){
    size_t new_size = hash_map->capacity * HASH_MAP_GROWTH_FACTOR;
    Vector **tmp_buckets = calloc(new_size, sizeof (Vector*));
    for (size_t i = 0; i < new_size; ++i){
      tmp_buckets[i] = VectorAlloc(hash_map->pair_cpy, hash_map->pair_cmp, hash_map->pair_free);
    }
    if (*tmp_buckets == NULL){
      return 1;
    }
    // in case that all pushing will success
    for (size_t i = 0; i < hash_map->capacity; ++i){
      for(size_t j = 0; j < hash_map->buckets[i]->size; ++j){
        Pair *tmp_pair = hash_map->buckets[i]->data[j];
        size_t index = hash_map->hash_func(tmp_pair->key) & (new_size - 1);
        VectorPushBack(&(*tmp_buckets[index]), hash_map->buckets[i]->data[j]);
      }
      VectorFree(&(hash_map->buckets[i]));
    }
    free(hash_map->buckets);
    hash_map->buckets = tmp_buckets;
    hash_map->capacity = new_size;
  }
  return 1;
}

/**
 * The function checks if the given key exists in the hash map.
 * @param hash_map a hash map.
 * @param key the key to be checked.
 * @return 1 if the key is in the hash map, 0 otherwise.
 */
int HashMapContainsKey(HashMap *hash_map, KeyT key){
  if (hash_map == NULL || key == NULL){
    return 0;
  }
  size_t ind = HashFunHelper(hash_map, key);
  if (hash_map->buckets[ind]->size == 0){
    return 0;
  }
  for (size_t i = 0; i < hash_map->buckets[ind]->size; ++i){
    Pair* tmp_pair = hash_map->buckets[ind]->data[i];
    if (tmp_pair->key_cmp(tmp_pair->key, key) == 1){
      return 1;
    }
  }
  return 0;
}

/**
 * The function checks if the given value exists in the hash map.
 * @param hash_map a hash map.
 * @param value the value to be checked.
 * @return 1 if the value is in the hash map, 0 otherwise.
 */
int HashMapContainsValue(HashMap *hash_map, ValueT value){
  if (hash_map == NULL || value == NULL){
    return 0;
  }
  for (size_t i = 0; i < hash_map->capacity; ++i){
    if (hash_map->buckets[i]->size > 0){
      for (size_t j = 0; j < hash_map->buckets[i]->size; ++j){
        Pair* tmp_pair = hash_map->buckets[i]->data[j];
        if (tmp_pair->value_cmp(tmp_pair->value, value) == 1){
          return 1;
        }
      }
    }
  }
  return 0;
}

/**
 * The function returns the value associated with the given key.
 * @param hash_map a hash map.
 * @param key the key to be checked.
 * @return the value associated with key if exists, NULL otherwise.
 */
ValueT HashMapAt(HashMap *hash_map, KeyT key){
  if (hash_map == NULL || key == NULL){
    return NULL;
  }
  if (HashMapContainsKey(hash_map, key) == 0){
    return NULL;
  }
  size_t ind = HashFunHelper(hash_map, key);
  for (size_t i = 0; i < hash_map->buckets[ind]->size; ++i){
    Pair *tmp_pair = hash_map->buckets[ind]->data[i];
    if (tmp_pair->key_cmp(tmp_pair->key, key) == 1){
      return tmp_pair->value;
    }
  }
  return NULL;
}

/**
 * The function erases the pair associated with key.
 * @param hash_map a hash map.
 * @param key a key of the pair to be erased.
 * @return 1 if the erasing was done successfully, 0 otherwise.
 */
int HashMapErase(HashMap *hash_map, KeyT key){
  if (hash_map == NULL || key == NULL){
    return 0;
  }
  if (HashMapContainsKey(hash_map, key) == 0){
    return 0;
  }
  //erasing
  size_t ind = HashFunHelper(hash_map, key);
  for (size_t i = 0; i < hash_map->buckets[ind]->size; ++i){
    Pair *tmp_pair = hash_map->buckets[ind]->data[i];
    if (tmp_pair->key_cmp(tmp_pair->key, key) == 1){
      if (VectorErase(hash_map->buckets[ind], i) == 0){
        return 0;
      }
      --hash_map->size;
    }
  }
  //resize decreasing, in case that all pushing will success
  if (HashMapGetLoadFactor(hash_map) < HASH_MAP_MIN_LOAD_FACTOR){
    size_t new_size = hash_map->capacity / HASH_MAP_GROWTH_FACTOR;
    Vector **tmp_buckets = calloc(new_size, sizeof (Vector*));
    for (size_t i = 0; i < new_size; ++i){
      tmp_buckets[i] = VectorAlloc(hash_map->pair_cpy, hash_map->pair_cmp, hash_map->pair_free);
    }
    if (*tmp_buckets == NULL){
      return 1;
    }
    // in case that all pushing will success
    for (size_t i = 0; i < hash_map->capacity; ++i){
      for(size_t j = 0; j < hash_map->buckets[i]->size; ++j){
        Pair *tmp_pair = hash_map->buckets[i]->data[j];
        size_t index = hash_map->hash_func(tmp_pair->key) & (new_size - 1);
        VectorPushBack(&(*tmp_buckets[index]), tmp_pair);
      }
      VectorFree(&(hash_map->buckets[i]));
    }
    free(hash_map->buckets);
    hash_map->buckets = tmp_buckets;
    hash_map->capacity = new_size;
  }
  return 1;
}

/**
 * This function returns the load factor of the hash map.
 * @param hash_map a hash map.
 * @return the hash map's load factor, -1 if the function failed.
 */
double HashMapGetLoadFactor(HashMap *hash_map){
  if (hash_map == NULL){
    return -1;
  }
  return (double) hash_map->size / (double) hash_map->capacity;
}

/**
 * This function deletes all the elements in the hash map.
 * @param hash_map a hash map to be cleared.
 */
void HashMapClear(HashMap *hash_map){
  if (hash_map == NULL){
    return;
  }
  size_t i = 0;
  while (hash_map->size > 0){
    if (hash_map->buckets[i]->size > 0){
      while (hash_map->buckets[i]->size > 0){
        size_t ind = hash_map->buckets[i]->size - 1;
        Pair *tmp_pair = hash_map->buckets[i]->data[ind];
        size_t old_capacity = hash_map->capacity;
        HashMapErase(hash_map, tmp_pair->key);
        if (hash_map->capacity != old_capacity) {
          i = 0;
          break;
        }
      }
    } else {
      ++i;
    }
  }
}