package com.burhanrashid52.photoeditor.ui.idea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.burhanrashid52.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

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
        Integer imgs = (Integer) listImg.get(position);
//        viewHolder1.imgIdea.setImageResource(imgs);
        Glide.with(holder.itemView.getContext()).load(imgs).into(viewHolder1.imgIdea);
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
