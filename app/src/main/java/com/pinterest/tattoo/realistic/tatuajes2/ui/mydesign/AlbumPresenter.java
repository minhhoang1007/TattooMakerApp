package com.pinterest.tattoo.realistic.tatuajes2.ui.mydesign;

import android.app.Activity;
import android.content.Intent;

import com.pinterest.tattoo.realistic.tatuajes2.ui.detail.DetailAlbumActivity;

import java.io.File;

public class AlbumPresenter<V extends IAlbumView> implements IAlbumPresenter<V> {
    Activity activity;
    IAlbumView view;

    public AlbumPresenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void clickItem(File file) {
        Intent intent = new Intent(activity, DetailAlbumActivity.class);
        intent.putExtra("album", file);
        activity.startActivity(intent);
    }

    @Override
    public void onAttact(V view) {
        this.view = view;

    }

    @Override
    public void onDetact() {

    }

    @Override
    public boolean isViewAttached() {
        return false;
    }
}
