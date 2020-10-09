package com.burhanrashid52.photoeditor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class StickerBSFragment extends BottomSheetDialogFragment {
    private String TAG = "StickerBSFragment";
    public StickerBSFragment() {
        // Required empty public constructor
    }

    private StickerListener mStickerListener;

    public void setStickerListener(StickerListener stickerListener) {
        mStickerListener = stickerListener;
    }

    public interface StickerListener {
        void onStickerClick(Bitmap bitmap);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        RecyclerView rvEmoji = contentView.findViewById(R.id.rvEmoji);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvEmoji.setLayoutManager(gridLayoutManager);
        StickerAdapter stickerAdapter = new StickerAdapter();
        rvEmoji.setAdapter(stickerAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        int[] stickerList = new int[]{R.drawable.mixigaming,R.drawable.aa, R.drawable.bb, R.drawable.cate_1_0, R.drawable.cate_1_1,
                R.drawable.cate_1_2, R.drawable.cate_1_3, R.drawable.cate_1_4,
                R.drawable.cate_1_5, R.drawable.cate_1_6, R.drawable.cate_1_7, R.drawable.cate_1_8, R.drawable.cate_1_9,
                R.drawable.cate_1_10, R.drawable.cate_1_11, R.drawable.cate_1_12, R.drawable.cate_1_13, R.drawable.cate_1_14,
                R.drawable.cate_1_15, R.drawable.cate_1_16, R.drawable.cate_1_17, R.drawable.cate_1_18, R.drawable.cate_1_19,
                R.drawable.cate_1_20, R.drawable.cate_1_21, R.drawable.cate_2_0, R.drawable.cate_2_1, R.drawable.cate_2_2,
                R.drawable.cate_2_3, R.drawable.cate_2_4, R.drawable.cate_2_5, R.drawable.cate_2_6, R.drawable.cate_2_7,
                R.drawable.cate_2_8, R.drawable.cate_2_9, R.drawable.cate_2_10, R.drawable.cate_2_11, R.drawable.cate_2_12,
                R.drawable.cate_2_13, R.drawable.cate_2_14, R.drawable.cate_2_15, R.drawable.cate_3_0, R.drawable.cate_3_1,
                R.drawable.cate_3_2, R.drawable.cate_3_3, R.drawable.cate_3_4, R.drawable.cate_3_5, R.drawable.cate_3_6,
                R.drawable.cate_3_7, R.drawable.cate_3_8, R.drawable.cate_3_9, R.drawable.cate_3_10, R.drawable.cate_4_0,
                R.drawable.cate_4_1, R.drawable.cate_4_2, R.drawable.cate_4_3, R.drawable.cate_4_4, R.drawable.cate_4_5,
                R.drawable.cate_4_6, R.drawable.cate_4_7, R.drawable.cate_4_8, R.drawable.cate_4_9, R.drawable.cate_4_10,
                R.drawable.cate_5_0, R.drawable.cate_5_1, R.drawable.cate_5_2, R.drawable.cate_5_3, R.drawable.cate_5_4,
                R.drawable.cate_5_5, R.drawable.cate_5_6, R.drawable.cate_5_7, R.drawable.cate_5_8, R.drawable.cate_5_9,
                R.drawable.cate_5_10, R.drawable.cate_5_11, R.drawable.cate_5_12, R.drawable.cate_5_13, R.drawable.cate_5_14,
                R.drawable.cate_5_15, R.drawable.cate_5_16, R.drawable.cate_5_17, R.drawable.cate_5_18, R.drawable.cate_5_19,
                R.drawable.cate_5_20, R.drawable.cate_5_21, R.drawable.cate_5_22, R.drawable.cate_5_23, R.drawable.cate_5_24,
                R.drawable.cate_6_0, R.drawable.cate_6_1, R.drawable.cate_6_2, R.drawable.cate_6_3, R.drawable.cate_6_4,
                R.drawable.cate_6_5, R.drawable.cate_6_6, R.drawable.cate_6_7, R.drawable.cate_6_8, R.drawable.cate_6_9,
                R.drawable.cate_6_10, R.drawable.cate_6_11, R.drawable.cate_6_12, R.drawable.cate_6_13, R.drawable.cate_6_14,
                R.drawable.cate_6_15, R.drawable.cate_6_16, R.drawable.cate_6_17, R.drawable.cate_7_0, R.drawable.cate_7_1,
                R.drawable.cate_7_2, R.drawable.cate_7_3, R.drawable.cate_7_4, R.drawable.cate_7_5, R.drawable.cate_7_6,
                R.drawable.cate_7_7, R.drawable.cate_7_8, R.drawable.cate_7_9, R.drawable.cate_7_10, R.drawable.cate_7_11,
                R.drawable.cate_7_12, R.drawable.cate_7_13, R.drawable.cate_7_14, R.drawable.cate_7_15, R.drawable.cate_7_16,
                R.drawable.cate_7_17, R.drawable.cate_7_18, R.drawable.cate_7_19, R.drawable.cate_7_20, R.drawable.cate_7_21,
                R.drawable.cate_7_22, R.drawable.cate_7_23, R.drawable.cate_7_24, R.drawable.cate_7_25};

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sticker, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //holder.imgSticker.setImageResource(stickerList[position]);
            Glide.with(holder.itemView.getContext()).load(stickerList[position]).into(holder.imgSticker);
        }

        @Override
        public int getItemCount() {
            return stickerList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgSticker;

            ViewHolder(View itemView) {
                super(itemView);
                imgSticker = itemView.findViewById(R.id.imgSticker);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mStickerListener != null) {
                            Log.e(TAG, "onClick: sticker 1" );
                            mStickerListener.onStickerClick(
                                    BitmapFactory.decodeResource(getResources(),
                                            stickerList[getLayoutPosition()]));
                        }
                        dismiss();
                    }
                });
            }
        }
    }

    private String convertEmoji(String emoji) {
        String returnedEmoji = "";
        try {
            int convertEmojiToInt = Integer.parseInt(emoji.substring(2), 16);
            returnedEmoji = getEmojiByUnicode(convertEmojiToInt);
        } catch (NumberFormatException e) {
            returnedEmoji = "";
        }
        return returnedEmoji;
    }

    private String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}