package com.pinterest.tattoo.realistic.tatuajes.ui.mydesign;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pinterest.tattoo.realistic.tatuajes.R;

import java.io.File;
import java.util.List;

public class AlbumAdapater extends RecyclerView.Adapter {
    private String TAG = "AlbumAdapter";
    private List<File> listImg;
    private OnClickLisener onClickLisener;

    public AlbumAdapater(List<File> listImg) {
        this.listImg = listImg;
    }
    public void setOnClickLisener(OnClickLisener onClickLisener) {
        this.onClickLisener = onClickLisener;
    }
    public void setVideoList(List<File> listImg) {
        this.listImg = listImg;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View modView = inflater.inflate(R.layout.item_album, parent, false);
        AlbumAdapater.ViewHolder viewHolder = new AlbumAdapater.ViewHolder(modView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlbumAdapater.ViewHolder viewHolder1 = (AlbumAdapater.ViewHolder) holder;
        File albumimg = listImg.get(position);
        Uri urimg =  Uri.fromFile(albumimg);
        Log.e(TAG, "onBindViewHolder: " + urimg  );
        Glide.with(holder.itemView.getContext())
                .load(urimg) // or URI/path
                .into(viewHolder1.imgAlbum);
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
        private ImageView imgAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.imgAlbum);
        }
    }


}
