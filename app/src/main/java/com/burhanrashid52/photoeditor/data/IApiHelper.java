package com.burhanrashid52.photoeditor.data;


import com.burhanrashid52.photoeditor.common.ImageList;

import java.util.List;

public interface IApiHelper {
    void getData(IApiHelper.CallBackDataNetWork<String> callBackData);
    interface CallBackDataNetWork<V>{
        void onSuccess(List<ImageList> data);
        void onFail(String mess);
    }
}
