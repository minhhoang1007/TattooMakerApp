package com.burhanrashid52.photoeditor.data;


import com.burhanrashid52.photoeditor.common.ImageList;

import java.util.List;

public interface IApiHelper {
    void getAds(CallBackData<String> callBackData);
    interface CallBackDataNetWork<V>{
        void onSuccess(V data);
        void onFail(String mess);
    }
    interface CallBackData<V>{
        void onSuccess(V data);
        void onFail(String mess);
    }
}
