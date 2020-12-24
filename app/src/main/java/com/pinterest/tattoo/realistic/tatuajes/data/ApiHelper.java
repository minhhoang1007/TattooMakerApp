package com.pinterest.tattoo.realistic.tatuajes.data;

import android.util.Log;
import com.pinterest.tattoo.realistic.tatuajes.asytask.OkHttpHandler;
import org.json.JSONException;
import org.json.JSONObject;


public class ApiHelper implements IApiHelper {
    private String TAG = "ApiHelper";

    @Override
    public void getAds(CallBackData<String> callBackData) {
        new OkHttpHandler(new OkHttpHandler.CallBackData(){
            @Override
            public void callBack(String data) {
                if (!data.equals("")) {
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        Log.e(TAG, "callBack: " + jsonObject.toString());
                        callBackData.onSuccess(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callBackData.onSuccess("");
                    }
                }else {
                    callBackData.onSuccess("");
                }
            }
        }).execute("https://raw.githubusercontent.com/minhhoang1007/tattoo_maker/master/api_ads.json");
    }

    private boolean checkNull(Object data) {
        return data == null;
    }
}
