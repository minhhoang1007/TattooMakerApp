package com.pinterest.tattoo.realistic.tatuajes2.data;


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
