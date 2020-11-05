package com.burhanrashid52.photoeditor.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.burhanrashid52.photoeditor.R;

import java.util.List;

public class TattooIdeaAdapter extends RecyclerView.Adapter<TattooIdeaAdapter.AllHolder> {
    private String TAG = "NewYearAdapter";
    private List<String> mListIdeas;
    private OnClickLisener onClickLisener;

    public TattooIdeaAdapter(List<String> mListIdeas) {
        this.mListIdeas = mListIdeas;
    }

    public void setOnClickLisener(OnClickLisener onClickLisener) {
        this.onClickLisener = onClickLisener;
    }
    @NonNull
    @Override
    public AllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tattoo_ideas, parent, false);
        AllHolder allHolder = new TattooIdeaAdapter.AllHolder(view);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllHolder holder, int position) {
        String all = mListIdeas.get(position);
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load("file:///android_asset/" + all) // or URI/path
                .into(holder.imgTattooIdeas);
        holder.imgTattooIdeas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLisener.onClickItem(all);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListIdeas.size();
    }
    public class AllHolder extends RecyclerView.ViewHolder {
        private ImageView imgTattooIdeas;
        public AllHolder(@NonNull View itemView) {
            super(itemView);
            imgTattooIdeas = itemView.findViewById(R.id.imgTattooIdeas);
        }
    }
    public interface OnClickLisener {
        void onClickItem(String position);
    }
}
