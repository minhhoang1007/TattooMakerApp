package com.pinterest.tattoo.realistic.tatuajes.base;

public interface MvpView {
    void showMessage(String message);
    boolean isNetworkConnected();
}
