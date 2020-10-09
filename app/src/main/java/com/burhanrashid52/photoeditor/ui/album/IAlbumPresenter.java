package com.burhanrashid52.photoeditor.ui.album;

import com.burhanrashid52.photoeditor.base.MvpPresenter;

import java.io.File;

public interface IAlbumPresenter<V extends IAlbumView> extends MvpPresenter<V> {
    void clickItem(File file);
}
