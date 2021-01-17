package com.facechanger.funnyface.videoeditor.untils;

import android.os.Environment;
import android.util.Log;

import com.pinterest.tattoo.realistic.tatuajes2.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {
    public static String rootFilePath;
    public static String folderPath = "";
    public static final String FILE_PROVIDER = BuildConfig.APPLICATION_ID+".provider";
    public static void createFolderIfNotExits() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + BuildConfig.APPLICATION_ID;
        Log.e("TAG", "createFolderIfNotExits: " + path);
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        folderPath = file1.getAbsolutePath();
    }
    public static boolean deleteDir(File file) {
        try {
            if (file.isDirectory()) {
                String[] list = file.list();
                for (String file2 : list) {
                    if (!deleteDir(new File(file, file2))) {
                        return false;
                    }
                }
            }
            return file.delete();
        } catch (Exception unused) {
            file.delete();
            return false;
        }
    }
    public static String getPath_DataTemp() {
        String str = rootFilePath;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/temp_data");
        return sb.toString();
    }
    public static String createNameTime() {
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
    }
}