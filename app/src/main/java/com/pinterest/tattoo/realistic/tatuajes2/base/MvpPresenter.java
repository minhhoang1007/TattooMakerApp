package com.pinterest.tattoo.realistic.tatuajes2.base;

public interface MvpPresenter<V extends MvpView> {
    void onAttact(V view);

    void onDetact();

    boolean isViewAttached();
}
