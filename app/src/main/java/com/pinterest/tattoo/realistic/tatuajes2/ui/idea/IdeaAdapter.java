package com.pinterest.tattoo.realistic.tatuajes2.ui.idea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.pinterest.tattoo.realistic.tatuajes2.R;

import java.util.ArrayList;

public class IdeaAdapter extends RecyclerView.Adapter {
    private static final String TAG = "IdeaAdapter";
    private ArrayList listImg;
    private OnClickLisener onClickLisener;

    public IdeaAdapter(ArrayList listImg) {
        this.listImg = listImg;
    }
    public void setOnClickLisener(OnClickLisener onClickLisener) {
        this.onClickLisener = onClickLisener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View modView = inflater.inflate(R.layout.item_idea, parent, false);
        IdeaAdapter.ViewHolder viewHolder = new IdeaAdapter.ViewHolder(modView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder1 = (ViewHolder) holder;
        String imgs = (String) listImg.get(position);
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load("file:///android_asset/" + imgs) // or URI/path
                .into(viewHolder1.imgIdea);
       // Glide.with(holder.itemView.getContext()).load(imgs).into(viewHolder1.imgIdea);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLisener.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImg.size();
    }
    public interface OnClickLisener {
        void onClickItem(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIdea;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIdea = itemView.findViewById(R.id.imgIdea);
        }
    }
}
