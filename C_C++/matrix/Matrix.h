//
// Created by Михаил Титов on 01.01.2021.
//

#ifndef EX5__MATRIX_H_
#define EX5__MATRIX_H_
#include <iostream>

class Matrix {
 private:
  int n_rows;
  int n_cols;
  float *_data;
 public:
  Matrix(int rows, int cols);
  Matrix();
  Matrix(const Matrix &other);
  ~Matrix() noexcept;
  int GetRows() const noexcept;
  int GetCols() const noexcept;
  Matrix &Vectorize() noexcept;
  void Print() noexcept;
  Matrix &operator=(const Matrix &other);
  Matrix operator*(const Matrix &other) const;
  Matrix &operator*=(const Matrix &other);
  Matrix &operator*=(float scalar) noexcept;
  Matrix operator*(float scalar) const;
  friend Matrix operator*(float scalar, const Matrix &mat);
  Matrix &operator/=(float scalar);
  Matrix operator/(float scalar);
  Matrix &operator+=(const Matrix &other);
  Matrix operator+(const Matrix &other);
  Matrix &operator+=(float scalar) noexcept;
  float &operator()(int row, int col);
  float operator()(int row, int col) const;
  float &operator[](int index);
  float operator[](int index) const;
  bool operator==(const Matrix &other) noexcept;
  bool operator!=(const Matrix &other) noexcept;
  friend std::istream &operator>>(std::istream &input, Matrix &matrix);
  friend std::ostream &operator<<(std::ostream &out, const Matrix &matrix);

};
#endif //EX5__MATRIX_H_
