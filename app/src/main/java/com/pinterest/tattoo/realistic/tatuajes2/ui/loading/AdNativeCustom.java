package com.pinterest.tattoo.realistic.tatuajes2.ui.loading;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.oneadx.android.oneads.AdInterstitialNative;
import com.pinterest.tattoo.realistic.tatuajes2.R;
public class AdNativeCustom extends AdInterstitialNative{
    @Override
    public void onCreate(@Nullable Bundle bundle) {
        //super.setCustomLoadingAdLayout(R.layout.custom_ad_loading); // Truyền layout custom vào đây
        super.onCreate(bundle);
    }
}
