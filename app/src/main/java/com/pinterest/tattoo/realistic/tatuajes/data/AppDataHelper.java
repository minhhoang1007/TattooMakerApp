package com.pinterest.tattoo.realistic.tatuajes.data;

import android.content.Context;

public class AppDataHelper implements IAppDataHelper {
    Context context;
    IApiHelper apiHelper;

    public AppDataHelper(Context context) {
        this.context = context;
        apiHelper = new ApiHelper();
    }

    @Override
    public void getAds(CallBackData<String> callBackData) {
        apiHelper.getAds(callBackData);
    }
}
