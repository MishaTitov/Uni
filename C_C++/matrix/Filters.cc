//
// Created by Михаил Титов on 05.01.2021.
//
#include "Filters.h"
#include <cmath>
#define SHADES 256
#define LIMIT_UP 255.0
#define LIMIT_DW 0.0

Matrix ConvolutionDummy(const Matrix &mat, const Matrix &other) {
  int n_rows = mat.GetRows();
  int n_cols = mat.GetCols();
  Matrix result = Matrix(n_rows, n_cols);
  for (int i = 0; i < n_rows; ++i) {
    for (int j = 0; j < n_cols; ++j) {
      result(i, j) += other(1, 1) * mat(i, j);
      if (j + 1 < n_cols) {
        result(i, j) += other(1, 2) * mat(i, j + 1);
      }
      if (i + 1 < n_rows) {
        result(i, j) += other(2, 1) * mat(i + 1, j);
      }
      if (j + 1 < n_cols && i + 1 < n_rows) {
        result(i, j) += other(2, 2) * mat(i + 1, j + 1);
      }
      if (j - 1 >= 0) {
        result(i, j) += other(1, 0) * mat(i, j - 1);
      }
      if (i - 1 >= 0) {
        result(i, j) += other(0, 1) * mat(i - 1, j);
      }
      if (j - 1 >= 0 && i - 1 >= 0) {
        result(i, j) += other(0, 0) * mat(i - 1, j - 1);
      }
      if (j + 1 < n_cols && i - 1 >= 0) {
        result(i, j) += other(0, 2) * mat(i - 1, j + 1);
      }
      if (j - 1 >= 0 && i + 1 < n_rows) {
        result(i, j) += other(2, 0) * mat(i + 1, j - 1);
      }
    }
  }
  return result;
}

void LimitHelper(Matrix &mat) {
  for (int i = 0; i < mat.GetCols() * mat.GetRows(); ++i) {
    if (mat[i] > LIMIT_UP) {
      mat[i] = rintf(LIMIT_UP);
    } else if (mat[i] < LIMIT_DW) {
      mat[i] = rintf(LIMIT_DW);
    } else {
      mat[i] = rintf(mat[i]);
    }
  }
}

Matrix Quantization(const Matrix &image, int levels) {
  size_t len_scala = SHADES / levels;
  int *avg_arr = new int[levels];
  for (int i = 0; i < levels; ++i) {
    avg_arr[i] = (i * len_scala + (i + 1) * len_scala - 1) / 2;
  }
  Matrix new_mat = image;
  for (int i = 0; i < image.GetRows() * image.GetCols(); ++i) {
    int tmp_ind = floor(image[i] / len_scala);
    new_mat[i] = avg_arr[tmp_ind];
  }
  delete[] avg_arr;
  return new_mat;
}

Matrix Blur(const Matrix &image) {
  Matrix tmp = Matrix(3, 3);
  tmp[0] = (float) 1 / 16;
  tmp[1] = (float) 2 / 16;
  tmp[2] = (float) 1 / 16;
  tmp[3] = (float) 2 / 16;
  tmp[4] = (float) 4 / 16;
  tmp[5] = (float) 2 / 16;
  tmp[6] = (float) 1 / 16;
  tmp[7] = (float) 2 / 16;
  tmp[8] = (float) 1 / 16;

  Matrix result = ConvolutionDummy(image, tmp);
  LimitHelper(result);
  return result;
}

Matrix Sobel(const Matrix &image) {
  Matrix gx = Matrix(3, 3);
  gx[0] = (float) 1 / 8;
  gx[1] = (float) 0;
  gx[2] = (float) -1 / 8;
  gx[3] = (float) 2 / 8;
  gx[4] = (float) 0;
  gx[5] = (float) -2 / 8;
  gx[6] = (float) 1 / 8;
  gx[7] = (float) 0;
  gx[8] = (float) -1 / 8;

  Matrix gy = Matrix(3, 3);
  gy[0] = (float) 1 / 8;
  gy[1] = (float) 2 / 8;
  gy[2] = (float) 1 / 8;
  gy[3] = (float) 0;
  gy[4] = (float) 0;
  gy[5] = (float) 0;
  gy[6] = (float) -1 / 8;
  gy[7] = (float) -2 / 8;
  gy[8] = (float) -1 / 8;

  Matrix tmpx = ConvolutionDummy(image, gx);
  Matrix tmpy = ConvolutionDummy(image, gy);
  Matrix result = tmpx + tmpy;
  LimitHelper(result);
  return result;
}
