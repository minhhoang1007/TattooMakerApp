package com.pinterest.tattoo.realistic.tatuajes2.ui.mydesign;

import com.pinterest.tattoo.realistic.tatuajes2.base.MvpPresenter;

import java.io.File;

public interface IAlbumPresenter<V extends IAlbumView> extends MvpPresenter<V> {
    void clickItem(File file);
}
