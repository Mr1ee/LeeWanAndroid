package com.lee.leewanandroid.algorithm.sort.interfaces;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public interface ISort<T> {
    List<T> sort(@NonNull ArrayList<T> data);
}
