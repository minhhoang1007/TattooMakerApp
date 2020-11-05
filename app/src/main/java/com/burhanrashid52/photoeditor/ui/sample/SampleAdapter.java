package com.burhanrashid52.photoeditor.ui.sample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.burhanrashid52.photoeditor.R;

import java.util.ArrayList;

public class SampleAdapter extends RecyclerView.Adapter {
    private static final String TAG = "SampleAdapter";
    private ArrayList<ModelSample> listImg;
    private OnClickLisener onClickLisener;

    public SampleAdapter(ArrayList listImg) {
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
        View modView = inflater.inflate(R.layout.item_choose_desgin, parent, false);
        SampleAdapter.ViewHolder viewHolder = new SampleAdapter.ViewHolder(modView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SampleAdapter.ViewHolder viewHolder1 = (SampleAdapter.ViewHolder) holder;
        //Integer imgs = (Integer) listImg.get(position);
        ModelSample ModelSample = (ModelSample) listImg.get(position);
        viewHolder1.txtDes.setText(ModelSample.getNameDes());
        Log.e(TAG, "onBindViewHolder: " + ModelSample.getImgDes() );
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load("file:///android_asset/" + ModelSample.getImgDes()) // or URI/path
                .into(viewHolder1.imgChooseDes);
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
        private TextView txtDes;
        private ImageView imgChooseDes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDes = itemView.findViewById(R.id.txtTextDesgin);
            imgChooseDes = itemView.findViewById(R.id.imgDesgin);
        }
    }
}
