//
// Created by Михаил Титов on 12.12.2020.
//

#include "Vector.h"

/**
 * Allocates dynamically new vector element.
 * @param elem_copy_func func which copies the element stored in the vector (returns
 * dynamically allocated copy).
 * @param elem_cmp_func func which is used to compare elements stored in the vector.
 * @param elem_free_func func which frees elements stored in the vector.
 * @return pointer to dynamically allocated vector.
 * @if_fail return NULL.
 */
Vector *VectorAlloc(VectorElemCpy elem_copy_func, VectorElemCmp elem_cmp_func, VectorElemFree elem_free_func){
  if (elem_cmp_func == NULL || elem_free_func == NULL || elem_copy_func == NULL){
    return NULL;
  }

  Vector* new_vec = calloc(1, sizeof (Vector));
  if (new_vec == NULL){
    return NULL;
  }
  new_vec->data = calloc(VECTOR_INITIAL_CAP, sizeof (void*));
  if (new_vec->data == NULL){
    free(new_vec);
    return NULL;
  }
  new_vec->capacity = VECTOR_INITIAL_CAP;
  new_vec->size = 0;
  new_vec->elem_copy_func = elem_copy_func;
  new_vec->elem_free_func = elem_free_func;
  new_vec->elem_cmp_func = elem_cmp_func;
  return new_vec;
}

/**
 * Frees a vector and the elements the vector itself allocated.
 * @param p_vector pointer to dynamically allocated pointer to vector.
 */
void VectorFree(Vector **p_vector){
  if (p_vector == NULL){
    return;
  }
  if (*p_vector == NULL){
    return;
  }
  for(size_t i = 0; i < (*p_vector)->size; ++i){
    (*p_vector)->elem_free_func(&(*p_vector)->data[i]);
  }
  free((*p_vector)->data);
  (*p_vector)->data = NULL;
  free((*p_vector));
  *p_vector = NULL;
}

/**
 * Returns the element at the given index.
 * @param vector pointer to a vector.
 * @param ind the index of the element we want to get.
 * @return the element the given index if exists (the element itself, not a copy of it)
 * , NULL otherwise.
 */
void *VectorAt(Vector *vector, size_t ind){
  if (vector == NULL){
    return NULL;
  }
  if (ind >= vector->size){
    return NULL;
  }
  return vector->data[ind];
}

/**
 * Gets a value and checks if the value is in the vector.
 * @param vector a pointer to vector.
 * @param value the value to look for.
 * @return the index of the given value if it is in the
 * vector ([0, vector_size - 1]).
 * Returns -1 if no such value in the vector.
 */
int VectorFind(Vector *vector, void *value){
  if (vector == NULL || value == NULL){
    return -1;
  }
  for (size_t i = 0; i < vector->size; ++i){
    if (vector->elem_cmp_func(vector->data[i],value)){
      return i;
    }
  }
  return -1;
}

/**
 * Adds a new value to the back (index vector_size) of the vector.
 * @param vector a pointer to vector.
 * @param value the value to be added to the vector.
 * @return 1 if the adding has been done successfully, 0 otherwise.
 */
int VectorPushBack(Vector *vector, void *value){
  if (vector == NULL || value == NULL){
    return 0;
  }
  if (vector->size < vector->capacity) {
    void* new_elem = vector->elem_copy_func(value);
    if (new_elem == NULL){
      return 0;
    }
    //переделать
    vector->data[vector->size] = new_elem;
    ++vector->size;
    if (VectorGetLoadFactor(vector) > VECTOR_MAX_LOAD_FACTOR) {
      size_t new_size = vector->capacity * VECTOR_GROWTH_FACTOR;
      void **tmp = realloc(vector->data, new_size * sizeof (void*));
      if (*tmp == NULL){
        free(vector->data);
        new_size = vector->capacity;
        return 1;
      }
      vector->capacity = new_size;
      vector->data = tmp;
    }
    return 1;
  }
  return 0;
}

/**
 * This function returns the load factor of the vector.
 * @param vector a vector.
 * @return the vector's load factor, -1 if the function failed.
 */
double VectorGetLoadFactor(Vector *vector){
  if (vector == NULL){
    return -1;
  }
  return (double)vector->size / (double)vector->capacity;
}

/**
 * Removes the element at the given index from the vector.
 * @param vector a pointer to vector.
 * @param ind the index of the element to be removed.
 * @return 1 if the removing has been done successfully, 0 otherwise.
 */
int VectorErase(Vector *vector, size_t ind) {
  if (vector == NULL) {
    return 0;
  }
  if (vector->size <= ind) {
    return 0;
  }
  for (size_t i = ind; i < (vector->size - 1); ++i) {
    void *to_free = vector->data[i];
    vector->elem_free_func(&to_free);
    vector->data[i] = vector->elem_copy_func(vector->data[i + 1]);
  }
  --vector->size;
  void *to_free = vector->data[vector->size];
  vector->elem_free_func(&to_free);
  if (VectorGetLoadFactor(vector) < VECTOR_MIN_LOAD_FACTOR) {
    size_t new_size = vector->capacity / VECTOR_GROWTH_FACTOR;
    void **tmp = realloc(vector->data, new_size * sizeof(void *));
    if (*tmp == NULL) {
      free(vector->data);
      new_size = vector->capacity;
      return 1;
    }
    vector->capacity = new_size;
    vector->data = tmp;
    return 1;
  }
  return 1;
}

/**
 * Deletes all the elements in the vector.
 * @param vector vector a pointer to vector.
 */
void VectorClear(Vector *vector){
  if (vector == NULL){
    return;
  }
  while (vector->size > 0){
    VectorErase(vector, vector->size - 1);
  }
}
