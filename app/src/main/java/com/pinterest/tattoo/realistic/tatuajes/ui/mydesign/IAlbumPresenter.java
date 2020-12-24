package com.pinterest.tattoo.realistic.tatuajes.ui.mydesign;

import com.pinterest.tattoo.realistic.tatuajes.base.MvpPresenter;

import java.io.File;

public interface IAlbumPresenter<V extends IAlbumView> extends MvpPresenter<V> {
    void clickItem(File file);
}
