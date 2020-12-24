package com.pinterest.tattoo.realistic.tatuajes.asytask;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHandler extends AsyncTask<String, Void, String> {
    private static final String TAG = "OkHttpHandler";
    private CallBackData callBackData;
    private OkHttpClient client = new OkHttpClient();
//        Context c;


    public OkHttpHandler(CallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public CallBackData getCallBackData() {
        return callBackData;
    }

    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "doInBackground: " + "start");
        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        Request request = builder.build();
        int a = 50;
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            return data;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        this.callBackData.callBack(data);
//        Log.d(TAG, "onPostExecute: " + data);
    }

    public interface CallBackData {
        void callBack(String data);
    }
}
