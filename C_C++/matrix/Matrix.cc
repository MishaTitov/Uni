//
// Created by Михаил Титов on 01.01.2021.
//
#include <iostream>
#include "Matrix.h"
#include "MatrixException.h"
#define INV_DIM_MSG "Invalid matrix dimensions.\n"
#define DIV_ZERO_MSG "Division by zero.\n"
#define INV_RNG_MSG "Index out of range.\n"
#define BAD_ALL_MSG "Allocation failed.\n"
#define INV_INP_STR "Error loading from input stream.\n"

Matrix::Matrix(int rows, int cols) {
  if (rows <= 0 || cols <= 0) {
    throw MatrixException(INV_DIM_MSG);
  }
  n_rows = rows;
  n_cols = cols;
  try {
    _data = new float[rows * cols]();
  } catch (std::bad_alloc &ba) {
    throw MatrixException(BAD_ALL_MSG);
  }
}

Matrix::Matrix() : Matrix(1, 1) {}

Matrix::Matrix(const Matrix &other) {
  n_cols = other.GetCols();
  n_rows = other.GetRows();
  int size = other.GetRows() * other.GetCols();
  try {
    _data = new float[size]();
  } catch (std::bad_alloc &ba) {
    throw MatrixException(BAD_ALL_MSG);
  }
  for (int i = 0; i < size; ++i) {
    _data[i] = other._data[i];
  }
}

Matrix::~Matrix() noexcept {
  delete[] _data;
}

int Matrix::GetRows() const noexcept { return n_rows; }

int Matrix::GetCols() const noexcept { return n_cols; }

Matrix &Matrix::Vectorize() noexcept {
  if (n_cols == 1) {
    return *this;
  }
  n_rows = n_rows * n_cols;
  n_cols = 1;
  return *this;
}

void Matrix::Print() noexcept {
  for (int i = 0; i < n_rows; ++i) {
    for (int j = 0; j < n_cols - 1; ++j) {
      std::cout << _data[n_cols * i + j] << " ";
    }
    std::cout << _data[i * n_cols + n_cols - 1] << std::endl;
  }
}

Matrix &Matrix::operator=(const Matrix &other) {
  if (this != &other) {
    n_cols = other.GetCols();
    n_rows = other.GetRows();
    delete[] _data;
    int size = n_rows * n_cols;
    _data = new float[size];
    for (int i = 0; i < size; ++i) {
      _data[i] = other._data[i];
    }
  }
  return *this;
}

Matrix Matrix::operator*(const Matrix &other) const {
  if (n_cols != other.n_rows) {
    throw MatrixException(INV_DIM_MSG);
  }
  Matrix result = Matrix(n_rows, other.n_cols);
  for (int i = 0; i < n_rows; ++i) {
    for (int k = 0; k < n_cols; ++k) {
      float tmp = operator()(i, k);
      for (int j = 0; j < result.n_cols; ++j) {
        result(i, j) += tmp * other(k, j);
      }
    }
  }
  return result;
}

Matrix &Matrix::operator*=(const Matrix &other) {
  Matrix result = (*this) * other;
  (*this) = result;
  return *this;
}

Matrix &Matrix::operator*=(const float scalar) noexcept {
  for (int i = 0; i < n_rows * n_cols; ++i) {
    _data[i] = _data[i] * scalar;
  }
  return *this;
}

Matrix Matrix::operator*(const float scalar) const {
  Matrix result(n_rows, n_cols);
  for (int i = 0; i < n_rows * n_cols; ++i) {
    result._data[i] = _data[i] * scalar;
  }
  return result;
}

Matrix operator*(const float scalar, const Matrix &mat) {
  return mat * scalar;
}

Matrix &Matrix::operator/=(const float scalar) {
  if (scalar == 0) {
    throw MatrixException(DIV_ZERO_MSG);
  }
  for (int i = 0; i < n_rows * n_cols; ++i) {
    _data[i] = _data[i] / scalar;
  }
  return *this;
}

Matrix Matrix::operator/(const float scalar) {
  if (scalar == 0) {
    throw MatrixException(DIV_ZERO_MSG);
  }
  Matrix result(n_rows, n_cols);
  for (int i = 0; i < n_rows * n_cols; ++i) {
    result._data[i] = _data[i] / scalar;
  }
  return result;
}

Matrix &Matrix::operator+=(const Matrix &other) {
  Matrix result = (*this) + other;
  (*this) = result;
  return *this;
}

Matrix Matrix::operator+(const Matrix &other) {
  if (n_rows != other.n_rows || n_cols != other.n_cols) {
    throw MatrixException(INV_DIM_MSG);
  }
  Matrix result(n_rows, n_cols);
  for (int i = 0; i < n_rows * n_cols; ++i) {
    result._data[i] = _data[i] + other._data[i];
  }
  return result;
}

Matrix &Matrix::operator+=(const float scalar) noexcept {
  for (int i = 0; i < n_rows * n_cols; ++i) {
    _data[i] = _data[i] + scalar;
  }
  return *this;
}

float &Matrix::operator()(int row, int col) {
  if (row < 0 || row > n_rows || col < 0 || col > n_cols) {
    throw MatrixException(INV_RNG_MSG);
  }
  return _data[row * n_cols + col];
}

float Matrix::operator()(int row, int col) const {
  if (row < 0 || row > n_rows || col < 0 || col > n_cols) {
    throw MatrixException(INV_RNG_MSG);
  }
  return _data[row * n_cols + col];
}

float &Matrix::operator[](int ind) {
  if (ind < 0 || ind >= n_rows * n_cols) {
    throw MatrixException(INV_RNG_MSG);
  }
  return _data[ind];
}

float Matrix::operator[](int ind) const {
  if (ind < 0 || ind >= n_rows * n_cols) {
    throw MatrixException(INV_RNG_MSG);
  }
  return _data[ind];
}

bool Matrix::operator==(const Matrix &other) noexcept {
  if (n_rows != other.n_rows || n_cols != other.n_cols) {
    return false;
  }
  for (int i = 0; i < n_cols * n_rows; ++i) {
    if (_data[i] != other._data[i]) {
      return false;
    }
  }
  return true;
}

bool Matrix::operator!=(const Matrix &other) noexcept {
  return !(*this == other);
}

std::istream &operator>>(std::istream &input, Matrix &matrix) {
  if (!input.good()) {
    throw MatrixException(INV_INP_STR);
  }
  float tmp = 0;
  int i = 0;
  while (input >> tmp && i < matrix.GetCols() * matrix.GetRows() - 1) {
    matrix._data[i] = tmp;
    ++i;
  }
  return input;
}

std::ostream &operator<<(std::ostream &out, const Matrix &matrix) {
  if (!out.good()) {
    throw MatrixException(INV_INP_STR);
  }
  for (int i = 0; i < matrix.n_rows; ++i) {
    for (int j = 0; j < matrix.n_cols - 1; ++j) {
      out << matrix._data[i * matrix.n_cols + j] << " ";
    }
    out << matrix._data[i * matrix.n_cols + matrix.n_cols - 1] << std::endl;
  }
  return out;
}

