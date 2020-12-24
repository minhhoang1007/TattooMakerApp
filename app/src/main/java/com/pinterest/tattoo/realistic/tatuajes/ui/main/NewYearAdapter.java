package com.pinterest.tattoo.realistic.tatuajes.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import com.pinterest.tattoo.realistic.tatuajes.R;

public class NewYearAdapter extends RecyclerView.Adapter<NewYearAdapter.AllHolder> {
    private String TAG = "NewYearAdapter";
    private List<String> mListNewYear;
    private OnClickLisener onClickLisener;

    public NewYearAdapter(List<String> mListNewYear) {
        this.mListNewYear = mListNewYear;
    }

    public void setOnClickLisener(OnClickLisener onClickLisener) {
        this.onClickLisener = onClickLisener;
    }
    @NonNull
    @Override
    public AllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_newyear, parent, false);
        AllHolder allHolder = new AllHolder(view);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllHolder holder, int position) {
        String all = mListNewYear.get(position);
        Glide.with(holder.itemView.getContext())
                    .asBitmap()
                    .load("file:///android_asset/NewYear/" + all) // or URI/path
                    .into(holder.imgNewYear);
        holder.imgNewYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLisener.onClickItem(all);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListNewYear.size();
    }
    public class AllHolder extends RecyclerView.ViewHolder {
       private ImageView imgNewYear;
        public AllHolder(@NonNull View itemView) {
            super(itemView);
            imgNewYear = itemView.findViewById(R.id.imgNewYear);
        }
    }
    public interface OnClickLisener {
        void onClickItem(String position);
    }
}
