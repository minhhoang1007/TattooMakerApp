package com.burhanrashid52.photoeditor.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.burhanrashid52.photoeditor.BuildConfig;
import com.burhanrashid52.photoeditor.R;
import com.burhanrashid52.photoeditor.common.Common;
import com.burhanrashid52.photoeditor.common.ImageList;
import com.burhanrashid52.photoeditor.data.AppDataHelper;
import com.burhanrashid52.photoeditor.data.IApiHelper;
import com.burhanrashid52.photoeditor.data.IAppDataHelper;
import com.burhanrashid52.photoeditor.ui.album.AlbumActivity;
import com.burhanrashid52.photoeditor.ui.desgin.DesginChooseActivity;
import com.burhanrashid52.photoeditor.ui.idea.IdeaActivity;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.linDesgin)
    LinearLayout linDesgin;
    @BindView(R.id.linIdea)
    LinearLayout linIdea;
    @BindView(R.id.linAlbum)
    LinearLayout linAlbum;
    @BindView(R.id.linMore)
    LinearLayout linMore;
    @BindView(R.id.btnMenu)
    ImageView btnMenu;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    TextView txtVersion, txtVersionnav;
    IAppDataHelper appDataHelper = new AppDataHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (checkStoragePermission()) {
            createFolderIfNotExits();
        } else {
            requestStoragePermission();
        }
        onClickItemMenu();

        appDataHelper.getData(new IApiHelper.CallBackDataNetWork<String>() {
            @Override
            public void onSuccess(List<ImageList> data) {
                Log.e(TAG, "onSuccess: " + data.toString() );
            }

            @Override
            public void onFail(String mess) {
                Log.e(TAG, "onFail: " + mess.toString() );
            }
        });

    }

    private void onClickDrawer() {
        txtVersionnav = navView.findViewById(R.id.txtVersionNav);
        txtVersionnav.setText("Version " + BuildConfig.VERSION_NAME);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.blue_light));
        if (!drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.openDrawer(GravityCompat.START);
        else drawerLayout.closeDrawer(GravityCompat.END);
    }

    private void onClickItemMenu() {
        navView.setItemIconTintList(null);
        navView.setFitsSystemWindows(true);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.share) {
                    shareApp();
                } else if (id == R.id.rate) {
                    //rateAuto();
                } else if (id == R.id.about) {
                    showDialog();
                } else if (id == R.id.favorite) {
                    //Intent intentFav = new Intent(MainActivity.this, FavoriteActivity.class);
                    //startActivity(intentFav);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @OnClick({R.id.linDesgin, R.id.linIdea, R.id.linAlbum, R.id.linMore, R.id.btnMenu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linDesgin:
                Intent intentDesgin = new Intent(this, DesginChooseActivity.class);
                startActivity(intentDesgin);
                break;
            case R.id.linIdea:
                Intent intentIdea = new Intent(this, IdeaActivity.class);
                startActivity(intentIdea);
                break;
            case R.id.linAlbum:
                Intent intentAlbum = new Intent(this, AlbumActivity.class);
                startActivity(intentAlbum);
                break;
            case R.id.linMore:
                break;
            case R.id.btnMenu:
                btnMenu.setColorFilter(Color.RED);
                onClickDrawer();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (checkStoragePermission()) {
                createFolderIfNotExits();
            } else {
                Toast.makeText(this, "Please accept this permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

    private void createFolderIfNotExits() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + BuildConfig.APPLICATION_ID;
        Log.e("TAG", "createFolderIfNotExits: " + path);
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        Common.folderPath = file1.getAbsolutePath();
    }

    public void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share using"));
    }

    public void showDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        txtVersion = alertLayout.findViewById(R.id.txtVersion);
        txtVersion.setText("Version " + BuildConfig.VERSION_NAME);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}