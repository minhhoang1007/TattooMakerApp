package com.pinterest.tattoo.realistic.tatuajes.ui.unlock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pinterest.tattoo.realistic.tatuajes.R;

import java.util.List;

public class UnlockTattooAdapter extends RecyclerView.Adapter<UnlockTattooAdapter.AllHolder> {
    private String TAG = "UnlockTattooAdapter";
    private List<String> mListUnlock;
    private OnClickLisener onClickLisener;

    public UnlockTattooAdapter(List<String> mListUnlock) {
        this.mListUnlock = mListUnlock;
    }

    public void setOnClickLisener(OnClickLisener onClickLisener) {
        this.onClickLisener = onClickLisener;
    }
    @NonNull
    @Override
    public AllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_unlock, parent, false);
        AllHolder allHolder = new UnlockTattooAdapter.AllHolder(view);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllHolder holder, int position) {
        String all = mListUnlock.get(position);
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load("file:///android_asset/" + all) // or URI/path
                .into(holder.imgItemUnlock);
        holder.imgItemUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLisener.onClickItem(all);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListUnlock.size();
    }
    public class AllHolder extends RecyclerView.ViewHolder {
        private ImageView imgItemUnlock;
        public AllHolder(@NonNull View itemView) {
            super(itemView);
            imgItemUnlock = itemView.findViewById(R.id.imgItemUnlock);
        }
    }
    public interface OnClickLisener {
        void onClickItem(String position);
    }
}
