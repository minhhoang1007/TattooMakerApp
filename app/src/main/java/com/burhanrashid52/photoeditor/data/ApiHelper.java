package com.burhanrashid52.photoeditor.data;

import android.util.Log;

import com.burhanrashid52.photoeditor.asytask.OkHttpHandler;
import com.burhanrashid52.photoeditor.common.ImageList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ApiHelper implements IApiHelper {
    private String TAG = "ApiHelper";

    @Override
    public void getData(IApiHelper.CallBackDataNetWork<String> callBackData) {
        new OkHttpHandler(new OkHttpHandler.CallBackData() {
            @Override
            public void callBack(String data) {
                if (checkNull(data)) {
                    callBackData.onFail("Loading fail please check Internet connection!");
                } else {
//                    Type type1 = new TypeToken<ArrayList<String>>() {
//                    }.getType();
//                    String dataa = new Gson().fromJson(data, type1);
//                    Log.e(TAG, "callBack: " + dataa.toString());
                    Log.e(TAG, "onPostExecute: " + data.toString() );
                    Gson gson = new Gson();
                    Type userType = new TypeToken<ArrayList<ImageList>>(){}.getType();
                    List<ImageList> exampleList = gson.fromJson(data, userType);
                    Log.e(TAG, "onPostExecute: 1" + exampleList.size() );
                    if (exampleList.size() > 0) {
                        callBackData.onSuccess(exampleList);
                    }
                }
            }
        }).execute("https://raw.githubusercontent.com/minhhoang1007/tshirt_desgin/master/data.json");
    }

    private boolean checkNull(Object data) {
        return data == null;
    }
}
