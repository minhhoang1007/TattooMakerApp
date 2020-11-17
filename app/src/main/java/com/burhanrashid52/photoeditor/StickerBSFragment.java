package com.burhanrashid52.photoeditor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.burhanrashid52.photoeditor.ui.unlock.UnlockTattooActivity;
import com.bumptech.glide.Glide;
import com.burhanrashid52.photoeditor.utils.SharedPrefsUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class StickerBSFragment extends BottomSheetDialogFragment {
    private String TAG = "StickerBSFragment";
    int[] stickerList;
    int[] stickerListTwo;
    int[] stickerListThree;
    int[] stickerListFour;
    int[] stickerListFive;
    int[] stickerListSix;
    int[] stickerListSeven;
    int[] stickerListEight;
    int[] stickerListNine;
    ImageView lock2,lock3, lock4,lock5,lock6,lock7,lock8,lock9;
    boolean ulock2, ulock3,ulock4,ulock5,ulock6,ulock7, ulock8, ulock9;
    Button btnOne;
    TextView btnTwo, btnThree, btnFour,btnFive, btnSix , btnSeven,btnEight,btnNine;
    RecyclerView rvEmoji;
    StickerAdapter stickerAdapter;

    @Override
    public void onStart() {
        super.onStart();
        initBoolean();
    }

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
        initView();
        btnOne = contentView.findViewById(R.id.btnOne);
        btnTwo = contentView.findViewById(R.id.btnTwo);
        btnThree = contentView.findViewById(R.id.btnThree);
        btnFour = contentView.findViewById(R.id.btnFour);
        btnFive = contentView.findViewById(R.id.btnFive);
        btnSix = contentView.findViewById(R.id.btnSix);
        btnSeven = contentView.findViewById(R.id.btnSeven);
        btnEight = contentView.findViewById(R.id.btnEight);
        btnNine = contentView.findViewById(R.id.btnNine);
        lock2 = contentView.findViewById(R.id.lock2);
        lock3 = contentView.findViewById(R.id.lock3);
        lock4 = contentView.findViewById(R.id.lock4);
        lock5 = contentView.findViewById(R.id.lock5);
        lock6 = contentView.findViewById(R.id.lock6);
        lock7 = contentView.findViewById(R.id.lock7);
        lock8 = contentView.findViewById(R.id.lock8);
        lock9 = contentView.findViewById(R.id.lock9);
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        rvEmoji = contentView.findViewById(R.id.rvEmoji);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvEmoji.setLayoutManager(gridLayoutManager);
        stickerAdapter = new StickerAdapter(stickerList);
        rvEmoji.setAdapter(stickerAdapter);
        //get boolean
        initBoolean();
    }

    private void initView() {
        stickerList = new int[]{R.drawable.pack_1_0, R.drawable.pack_1_1, R.drawable.pack_1_2, R.drawable.pack_1_3, R.drawable.pack_1_4,
                R.drawable.pack_1_5, R.drawable.pack_1_6, R.drawable.pack_1_7, R.drawable.pack_1_8, R.drawable.pack_1_9, R.drawable.pack_1_10,
                R.drawable.pack_1_11, R.drawable.pack_1_12, R.drawable.pack_1_13, R.drawable.pack_1_14, R.drawable.pack_1_15, R.drawable.pack_1_16,
                R.drawable.pack_1_17, R.drawable.pack_1_18, R.drawable.pack_1_19, R.drawable.pack_1_20};
        stickerListTwo = new int[]{R.drawable.pack_2_0, R.drawable.pack_2_1, R.drawable.pack_2_2, R.drawable.pack_2_3, R.drawable.pack_2_4,
                R.drawable.pack_2_5, R.drawable.pack_2_6, R.drawable.pack_2_7, R.drawable.pack_2_8, R.drawable.pack_2_9, R.drawable.pack_2_10, R.drawable.pack_2_11,
                R.drawable.pack_2_12, R.drawable.pack_2_13, R.drawable.pack_2_14, R.drawable.pack_2_15, R.drawable.pack_2_16, R.drawable.pack_2_17, R.drawable.pack_2_18,
                R.drawable.pack_2_19,R.drawable.pack_2_20};
        stickerListThree = new int[]{R.drawable.pack_3_0, R.drawable.pack_3_1, R.drawable.pack_3_2, R.drawable.pack_3_3, R.drawable.pack_3_4,
                R.drawable.pack_3_5, R.drawable.pack_3_6, R.drawable.pack_3_7, R.drawable.pack_3_8, R.drawable.pack_3_9, R.drawable.pack_3_10};
        stickerListFour = new int[]{R.drawable.pack_4_0, R.drawable.pack_4_1, R.drawable.pack_4_2, R.drawable.pack_4_3, R.drawable.pack_4_4,
                R.drawable.pack_4_5, R.drawable.pack_4_6, R.drawable.pack_4_7, R.drawable.pack_4_8, R.drawable.pack_4_9, R.drawable.pack_4_10,
                R.drawable.pack_4_11, R.drawable.pack_4_12, R.drawable.pack_4_13, R.drawable.pack_4_14, R.drawable.pack_4_15, R.drawable.pack_4_16,
                R.drawable.pack_4_17, R.drawable.pack_4_18, R.drawable.pack_4_19,R.drawable.pack_4_20,R.drawable.pack_4_21,R.drawable.pack_4_22,
                R.drawable.pack_4_23,R.drawable.pack_4_24 };
        stickerListFive = new int[]{R.drawable.pack_5_01, R.drawable.pack_5_02, R.drawable.pack_5_03, R.drawable.pack_5_04, R.drawable.pack_5_05,
                R.drawable.pack_5_06, R.drawable.pack_5_07, R.drawable.pack_5_08, R.drawable.pack_5_1, R.drawable.pack_5_2, R.drawable.pack_5_3,
                R.drawable.pack_5_4, R.drawable.pack_5_5, R.drawable.pack_5_6, R.drawable.pack_5_7, R.drawable.pack_5_8, R.drawable.pack_5_9,
                R.drawable.pack_5_10, R.drawable.pack_5_11, R.drawable.pack_5_12, R.drawable.pack_5_13,  R.drawable.pack_5_14,  R.drawable.pack_5_15,
                R.drawable.pack_5_16,  R.drawable.pack_5_17,  R.drawable.pack_5_18,  R.drawable.pack_5_19};
        stickerListSix = new int[]{R.drawable.pack_6_1, R.drawable.pack_6_2, R.drawable.pack_6_3, R.drawable.pack_6_4, R.drawable.pack_6_5,
                R.drawable.pack_6_6, R.drawable.pack_6_7, R.drawable.pack_6_8, R.drawable.pack_6_9, R.drawable.pack_6_10, R.drawable.pack_6_11,
                R.drawable.pack_6_12, R.drawable.pack_6_13, R.drawable.pack_6_14, R.drawable.pack_6_15, R.drawable.pack_6_16, R.drawable.pack_6_17,
                R.drawable.pack_6_18, R.drawable.pack_6_19, R.drawable.pack_6_20, R.drawable.pack_6_21,  R.drawable.pack_6_22,  R.drawable.pack_6_23,
                R.drawable.pack_6_24,  R.drawable.pack_6_25,  R.drawable.pack_6_26,  R.drawable.pack_6_27, R.drawable.pack_6_28};
        stickerListSeven = new int[]{R.drawable.christmas_0,R.drawable.christmas_1,R.drawable.christmas_2,R.drawable.christmas_3,R.drawable.christmas_4,
                R.drawable.christmas_5,R.drawable.christmas_6,R.drawable.christmas_7,R.drawable.christmas_8,R.drawable.christmas_9,R.drawable.christmas_10,
                R.drawable.christmas_11,R.drawable.christmas_12, R.drawable.christmas_13};
        stickerListEight = new int[]{R.drawable.newyear_0,R.drawable.newyear_1,R.drawable.newyear_2,R.drawable.newyear_3,R.drawable.newyear_4,R.drawable.newyear_5,
                R.drawable.newyear_6, R.drawable.newyear_7,R.drawable.newyear_8, R.drawable.newyear_10, R.drawable.newyear_11};
        stickerListNine = new int[]{R.drawable.lunar_0, R.drawable.lunar_1,R.drawable.lunar_2,R.drawable.lunar_3,R.drawable.lunar_4,R.drawable.lunar_5,
                R.drawable.lunar_6,R.drawable.lunar_7,R.drawable.lunar_8,R.drawable.lunar_9};
    }
    private void initBoolean(){
        ulock2 = SharedPrefsUtils.getInstance(getContext()).getBoolean("ulock2");
        if(ulock2){
            lock2.setVisibility(View.GONE);
        }
        ulock3 = SharedPrefsUtils.getInstance(getContext()).getBoolean("ulock3");
        if(ulock3){
            lock3.setVisibility(View.GONE);
        }
        ulock4 = SharedPrefsUtils.getInstance(getContext()).getBoolean("ulock4");
        if(ulock4){
            lock4.setVisibility(View.GONE);
        }
        ulock5 = SharedPrefsUtils.getInstance(getContext()).getBoolean("ulock5");
        if(ulock5){
            lock5.setVisibility(View.GONE);
        }
        ulock6 = SharedPrefsUtils.getInstance(getContext()).getBoolean("ulockthree");
        if(ulock6){
            lock6.setVisibility(View.GONE);
        }
        ulock7 = SharedPrefsUtils.getInstance(getContext()).getBoolean("ulock7");
        if(ulock7){
            lock7.setVisibility(View.GONE);
        }
        ulock8 = SharedPrefsUtils.getInstance(getContext()).getBoolean("unlock");
        if(ulock8){
            lock8.setVisibility(View.GONE);
        }
        ulock9 = SharedPrefsUtils.getInstance(getContext()).getBoolean("unlocktwo");
        if(ulock9){
            lock9.setVisibility(View.GONE);
        }
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button));
                btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button));
                btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button));
                btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button));
                btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button));
                btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button));
                btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button));
                btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button));
                stickerAdapter = new StickerAdapter(stickerList);
                rvEmoji.setAdapter(stickerAdapter);
                stickerAdapter.notifyDataSetChanged();
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ulock2) {
                    lock2.setVisibility(View.GONE);
                    btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                    btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    stickerAdapter = new StickerAdapter(stickerListTwo);
                    rvEmoji.setAdapter(stickerAdapter);
                    stickerAdapter.notifyDataSetChanged();
                }else{
                    Intent intent = new Intent(getActivity(), UnlockTattooActivity.class);
                    intent.putExtra("name", 22);
                    startActivity(intent);
                }
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ulock3) {
                    lock2.setVisibility(View.GONE);
                    btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                    btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    stickerAdapter = new StickerAdapter(stickerListThree);
                    rvEmoji.setAdapter(stickerAdapter);
                    stickerAdapter.notifyDataSetChanged();
                }else{
                    Intent intent = new Intent(getActivity(), UnlockTattooActivity.class);
                    intent.putExtra("name", 33);
                    startActivity(intent);
                }
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ulock4) {
                    lock2.setVisibility(View.GONE);
                    btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                    btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    stickerAdapter = new StickerAdapter(stickerListFour);
                    rvEmoji.setAdapter(stickerAdapter);
                    stickerAdapter.notifyDataSetChanged();
                }else{
                    Intent intent = new Intent(getActivity(), UnlockTattooActivity.class);
                    intent.putExtra("name", 44);
                    startActivity(intent);
                }
            }
        });

        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ulock5) {
                    lock2.setVisibility(View.GONE);
                    btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                    btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    stickerAdapter = new StickerAdapter(stickerListFive);
                    rvEmoji.setAdapter(stickerAdapter);
                    stickerAdapter.notifyDataSetChanged();
                }else{
                    Intent intent = new Intent(getActivity(), UnlockTattooActivity.class);
                    intent.putExtra("name", 55);
                    startActivity(intent);
                }
            }
        });
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ulock6) {
                    lock2.setVisibility(View.GONE);
                    btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                    btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    stickerAdapter = new StickerAdapter(stickerListSix);
                    rvEmoji.setAdapter(stickerAdapter);
                    stickerAdapter.notifyDataSetChanged();
                }else{
                    Intent intent = new Intent(getActivity(), UnlockTattooActivity.class);
                    intent.putExtra("name", 3);
                    startActivity(intent);
                }
            }
        });
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ulock7) {
                    btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                    btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    stickerAdapter = new StickerAdapter(stickerListSeven);
                    rvEmoji.setAdapter(stickerAdapter);
                    stickerAdapter.notifyDataSetChanged();
                }else{
                    Intent intent = new Intent(getActivity(), UnlockTattooActivity.class);
                    intent.putExtra("name", 77);
                    startActivity(intent);
                }
            }
        });
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ulock8) {
                    btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                    btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    stickerAdapter = new StickerAdapter(stickerListEight);
                    rvEmoji.setAdapter(stickerAdapter);
                    stickerAdapter.notifyDataSetChanged();
                }else{
                    Intent intent = new Intent(getActivity(), UnlockTattooActivity.class);
                    intent.putExtra("name", 1);
                    startActivity(intent);
                }
            }
        });
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ulock9) {
                    btnOne.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnTwo.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnThree.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFour.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnFive.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSix.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnSeven.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnEight.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    btnNine.setBackground(getResources().getDrawable(R.drawable.custom_button_click));
                    stickerAdapter = new StickerAdapter(stickerListNine);
                    rvEmoji.setAdapter(stickerAdapter);
                    stickerAdapter.notifyDataSetChanged();
                }else{
                    Intent intent = new Intent(getActivity(), UnlockTattooActivity.class);
                    intent.putExtra("name", 2);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        int[] stickerList = new int[]{R.drawable.pack_1_0, R.drawable.pack_1_1, R.drawable.pack_1_2, R.drawable.pack_1_3, R.drawable.pack_1_4,
                R.drawable.pack_1_5, R.drawable.pack_1_6, R.drawable.pack_1_7, R.drawable.pack_1_8, R.drawable.pack_1_9, R.drawable.pack_1_10,
                R.drawable.pack_1_11, R.drawable.pack_1_12, R.drawable.pack_1_13, R.drawable.pack_1_14, R.drawable.pack_1_15, R.drawable.pack_1_16,
                R.drawable.pack_1_17, R.drawable.pack_1_18, R.drawable.pack_1_19, R.drawable.pack_1_20};

        public StickerAdapter(int[] stickerList) {
            this.stickerList = stickerList;
        }

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
                            Log.e(TAG, "onClick: sticker 1");
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}