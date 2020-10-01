package com.burhanrashid52.photoeditor.base;

public interface MvpPresenter<V extends MvpView> {
    void onAttact(V view);

    void onDetact();

    boolean isViewAttached();
}
