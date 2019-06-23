package com.lee.leewanandroid.algorithm.kotin.sort.interfaces;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public interface ISort<T> {
    List<T> sort(@NonNull ArrayList<T> data);
}
