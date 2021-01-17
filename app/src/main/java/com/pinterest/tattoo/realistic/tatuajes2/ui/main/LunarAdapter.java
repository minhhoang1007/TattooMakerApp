package com.pinterest.tattoo.realistic.tatuajes2.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pinterest.tattoo.realistic.tatuajes2.R;

import java.util.List;

public class LunarAdapter extends RecyclerView.Adapter<LunarAdapter.AllHolder> {
    private String TAG = "LunarAdapter";
    private List<String> mListLunar;
    private OnClickLisener onClickLisener;

    public LunarAdapter(List<String> mListLunar) {
        this.mListLunar = mListLunar;
    }

    public void setOnClickLisener(OnClickLisener onClickLisener) {
        this.onClickLisener = onClickLisener;
    }
    @NonNull
    @Override
    public AllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_lunar, parent, false);
        AllHolder allHolder = new LunarAdapter.AllHolder(view);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllHolder holder, int position) {
        String all = mListLunar.get(position);
        Glide.with(holder.itemView.getContext())
                    .asBitmap()
                    .load("file:///android_asset/Lunar/" + all) // or URI/path
                    .into(holder.imgLunar);
        holder.imgLunar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLisener.onClickItem(all);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListLunar.size();
    }
    public class AllHolder extends RecyclerView.ViewHolder {
        private ImageView imgLunar;
        public AllHolder(@NonNull View itemView) {
            super(itemView);
            imgLunar = itemView.findViewById(R.id.imgLunar);
        }
    }
    public interface OnClickLisener {
        void onClickItem(String position);
    }
}
