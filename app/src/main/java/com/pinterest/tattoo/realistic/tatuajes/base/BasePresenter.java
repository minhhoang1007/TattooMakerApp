package com.pinterest.tattoo.realistic.tatuajes.base;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    protected V view;

    @Override
    public void onAttact(V view) {
        this.view = view;
    }

    @Override
    public void onDetact() {

    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }
}
