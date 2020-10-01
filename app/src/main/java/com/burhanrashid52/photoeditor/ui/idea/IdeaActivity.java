package com.burhanrashid52.photoeditor.ui.idea;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burhanrashid52.photoeditor.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdeaActivity extends AppCompatActivity {
    private static final String TAG = "IdeaActivity";
    @BindView(R.id.btnBackIdea)
    ImageView btnBackIdea;
    @BindView(R.id.revIdea)
    RecyclerView revIdea;
    ArrayList<Integer> imgList;
    IdeaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);
        ButterKnife.bind(this);
        initData();
        initView();
    }
    private void initData(){
        imgList =  new ArrayList<>();
        imgList.add(R.drawable.id_0);
        imgList.add(R.drawable.id_1);
        imgList.add(R.drawable.id_2);
        imgList.add(R.drawable.id_3);
        imgList.add(R.drawable.id_4);
        imgList.add(R.drawable.id_5);
        imgList.add(R.drawable.id_6);
        imgList.add(R.drawable.id_7);
        imgList.add(R.drawable.id_8);
        imgList.add(R.drawable.id_9);
        imgList.add(R.drawable.id_10);
        imgList.add(R.drawable.id_11);
        imgList.add(R.drawable.id_12);
        imgList.add(R.drawable.id_13);
        imgList.add(R.drawable.id_14);
        imgList.add(R.drawable.id_15);
    }
    private void initView(){
        revIdea.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        adapter = new IdeaAdapter(imgList);
        revIdea.setLayoutManager(linearLayoutManager);
        revIdea.setAdapter(adapter);
        adapter.setOnClickLisener(new IdeaAdapter.OnClickLisener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), DetailIdeaActivity.class);
                intent.putExtra("stt", imgList.get(position));
                startActivity(intent);
            }
        });
    }
    @OnClick(R.id.btnBackIdea)
    public void onViewClicked() {
        onBackPressed();
    }
}