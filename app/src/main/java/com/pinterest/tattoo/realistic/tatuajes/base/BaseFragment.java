package com.pinterest.tattoo.realistic.tatuajes.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author <a href="https://github.com/burhanrashid52">Burhanuddin Rashid</a>
 * @version 0.1.2
 * @since 5/25/2018
 */
public abstract class BaseFragment extends Fragment {
    private FragmentCallBack fragmentCallBack;
    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() == 0) {
            throw new IllegalArgumentException("Invalid layout id");
        }
        return inflater.inflate(getLayoutId(), container, false);
    }
    public BaseFragment(FragmentCallBack fragmentCallBack) {
        this.fragmentCallBack = fragmentCallBack;
    }

    public FragmentCallBack getFragmentCallBack() {
        return fragmentCallBack;
    }

    public void setFragmentCallBack(FragmentCallBack fragmentCallBack) {
        this.fragmentCallBack = fragmentCallBack;
    }
    public interface FragmentCallBack {
        void setVisibilityLoading(int i);
    }
}
