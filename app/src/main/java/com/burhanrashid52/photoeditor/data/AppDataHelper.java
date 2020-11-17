package com.burhanrashid52.photoeditor.data;

import android.content.Context;
import java.util.List;

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
