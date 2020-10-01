package com.burhanrashid52.photoeditor.ui.album;

import android.app.Activity;

public class AlbumPresenter<V extends IAlbumView> implements IAlbumPresenter<V> {
    Activity activity;
    IAlbumView view;
    @Override
    public void clickItem() {

    }

    @Override
    public void onAttact(V view) {

    }

    @Override
    public void onDetact() {

    }

    @Override
    public boolean isViewAttached() {
        return false;
    }
}
