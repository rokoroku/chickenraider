package com.bung.bungapp.core;

/**
 * Created by rok on 2015. 11. 14..
 */
public interface Callback<T> {
    void onSuccess(T result);
    void onFailure();
}
