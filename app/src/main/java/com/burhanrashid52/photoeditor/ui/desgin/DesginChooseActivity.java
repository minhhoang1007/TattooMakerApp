package com.burhanrashid52.photoeditor.ui.desgin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burhanrashid52.photoeditor.EditImageActivity;
import com.burhanrashid52.photoeditor.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DesginChooseActivity extends AppCompatActivity {
    private static final String TAG = "DesginChooseActivity";
    @BindView(R.id.btnBackDes)
    ImageView btnBackDes;
    @BindView(R.id.revChooseDesgin)
    RecyclerView revChooseDesgin;
    ArrayList<ModelDesgin> imgChooseDes;
    DesginChooseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desgin_choose);
        ButterKnife.bind(this);
        initData();
        initView();
    }
    private void initData(){
        imgChooseDes = new ArrayList<>();
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_0));
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_1));
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_2));
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_3));
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_4));
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_5));
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_6));
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_7));
        imgChooseDes.add(new ModelDesgin("FOR PRINT", R.drawable.t_8));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_01_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_01_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_02_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_02_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_03_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_03_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_04_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_04_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_05_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_05_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_06_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_06_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_07_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_07_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_08_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_08_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_09_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_09_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_010_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_010_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_011_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_011_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_012_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_012_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_013_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_013_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_014_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_014_1));
        imgChooseDes.add(new ModelDesgin("FRONT", R.drawable.tb_015_0));
        imgChooseDes.add(new ModelDesgin("BACK", R.drawable.tb_015_1));
        imgChooseDes.add(new ModelDesgin("", R.drawable.tg_01));
        imgChooseDes.add(new ModelDesgin("", R.drawable.tg_02));
        imgChooseDes.add(new ModelDesgin("", R.drawable.tg_03));
        imgChooseDes.add(new ModelDesgin("", R.drawable.tg_04));
        imgChooseDes.add(new ModelDesgin("", R.drawable.tg_05));
        imgChooseDes.add(new ModelDesgin("", R.drawable.tg_06));
        imgChooseDes.add(new ModelDesgin("", R.drawable.tg_07));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_0));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_1));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_2));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_3));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_4));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_5));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_6));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_7));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_8));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_9));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_10));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_11));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_12));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_13));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_14));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_15));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_16));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_17));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_18));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_19));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_20));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_21));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_22));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_23));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_24));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_25));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_26));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_27));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_28));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_29));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_30));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_31));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_32));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_33));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_34));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_35));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_36));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_37));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_38));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_39));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_40));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_41));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_42));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_43));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_44));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_45));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_46));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_47));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_48));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_49));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_50));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_51));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_52));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_53));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_54));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_55));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_56));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_57));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_58));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_59));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_60));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_61));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_62));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_63));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_64));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_65));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_66));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_67));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_68));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_69));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_70));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_71));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_72));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_73));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_74));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_75));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_76));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_77));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_78));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_79));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_80));
        imgChooseDes.add(new ModelDesgin("", R.drawable.ts_81));
    }
    private void initView(){
        revChooseDesgin.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        adapter = new DesginChooseAdapter(imgChooseDes);
        revChooseDesgin.setLayoutManager(linearLayoutManager);
        revChooseDesgin.setAdapter(adapter);
        adapter.setOnClickLisener(new DesginChooseAdapter.OnClickLisener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), EditImageActivity.class);
                intent.putExtra("desgin", imgChooseDes.get(position).getImgDes());
                intent.putExtra("frame", R.drawable.img_frame);
                startActivity(intent);
                finish();
            }
        });
    }
    @OnClick(R.id.btnBackDes)
    public void onViewClicked() {
        onBackPressed();
    }
}