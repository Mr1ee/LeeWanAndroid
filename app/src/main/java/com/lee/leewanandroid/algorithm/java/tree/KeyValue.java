package com.lee.leewanandroid.algorithm.java.tree;

import org.jetbrains.annotations.NotNull;

public class KeyValue<K, V> {
    public final K key;
    public final V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KeyValue)) {
            return false;
        }
        KeyValue<?, ?> p = (KeyValue<?, ?>) o;
        return p.key.equals(key);
    }

    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }

    @NotNull
    @Override
    public String toString() {
        return "<" + key + "," + value + ">";
    }

}
