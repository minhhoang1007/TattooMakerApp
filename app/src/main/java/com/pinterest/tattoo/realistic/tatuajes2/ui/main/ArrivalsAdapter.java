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

public class ArrivalsAdapter extends RecyclerView.Adapter<ArrivalsAdapter.AllHolder> {
    private String TAG = "ArrivalsAdapter";
    private List<String> mListArrivals;
    private OnClickLisener onClickLisener;

    public ArrivalsAdapter(List<String> mListArrivals) {
        this.mListArrivals = mListArrivals;
    }

    public void setOnClickLisener(OnClickLisener onClickLisener) {
        this.onClickLisener = onClickLisener;
    }
    @NonNull
    @Override
    public AllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_arrivals, parent, false);
        AllHolder allHolder = new ArrivalsAdapter.AllHolder(view);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllHolder holder, int position) {
        String all = mListArrivals.get(position);
        Glide.with(holder.itemView.getContext())
                    .asBitmap()
                    .load("file:///android_asset/" + all) // or URI/path
                    .into(holder.imgArrivals);
        holder.imgArrivals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLisener.onClickItem(all);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListArrivals.size();
    }
    public class AllHolder extends RecyclerView.ViewHolder {
        private ImageView imgArrivals;
        public AllHolder(@NonNull View itemView) {
            super(itemView);
            imgArrivals = itemView.findViewById(R.id.imgArrivals);
        }
    }
    public interface OnClickLisener {
        void onClickItem(String position);
    }
}
