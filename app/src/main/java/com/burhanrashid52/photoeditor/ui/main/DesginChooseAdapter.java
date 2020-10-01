package com.burhanrashid52.photoeditor.ui.main;

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
import com.burhanrashid52.photoeditor.ui.idea.IdeaAdapter;

import java.util.ArrayList;

public class DesginChooseAdapter extends RecyclerView.Adapter {
    private static final String TAG = "DesginChooseAdapter";
    private ArrayList<ModelDesgin> listImg;
    private OnClickLisener onClickLisener;

    public DesginChooseAdapter(ArrayList listImg) {
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
        DesginChooseAdapter.ViewHolder viewHolder = new DesginChooseAdapter.ViewHolder(modView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DesginChooseAdapter.ViewHolder viewHolder1 = (DesginChooseAdapter.ViewHolder) holder;
        //Integer imgs = (Integer) listImg.get(position);
        ModelDesgin modelDesgin = (ModelDesgin) listImg.get(position);
        viewHolder1.txtDes.setText(modelDesgin.getNameDes());
        Log.e(TAG, "onBindViewHolder: " + modelDesgin.getImgDes() );
        Glide.with(holder.itemView.getContext()).load(modelDesgin.getImgDes()).into(viewHolder1.imgChooseDes);
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
