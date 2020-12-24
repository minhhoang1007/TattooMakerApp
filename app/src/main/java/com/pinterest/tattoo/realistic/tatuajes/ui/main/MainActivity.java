package com.pinterest.tattoo.realistic.tatuajes.ui.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pinterest.tattoo.realistic.tatuajes.BuildConfig;
import com.pinterest.tattoo.realistic.tatuajes.EditImageActivity;
import com.pinterest.tattoo.realistic.tatuajes.R;
import com.pinterest.tattoo.realistic.tatuajes.common.Common;
import com.pinterest.tattoo.realistic.tatuajes.ui.detail.DetailIdeaActivity;
import com.pinterest.tattoo.realistic.tatuajes.ui.idea.IdeaActivity;
import com.pinterest.tattoo.realistic.tatuajes.ui.mydesign.AlbumActivity;
import com.pinterest.tattoo.realistic.tatuajes.ui.sample.SampleActivity;
import com.pinterest.tattoo.realistic.tatuajes.ui.unlock.UnlockTattooActivity;
import com.pinterest.tattoo.realistic.tatuajes.utils.SharedPrefsUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.oneadx.android.oneads.AdInterstitial;
import com.oneadx.android.oneads.AdListener;
import com.oneadx.android.ratedialog.RatingDialog;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final int CODE_IMG_GALLERY = 1;
    private final int CODE_IMG_CAMERA = 0;
    private final String SAMPLE_CROPPED_IMG_NAME = "SampleCropImg";
    @BindView(R.id.btnMenu)
    ImageView btnMenu;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.btnNotification)
    ImageView btnNotification;
    TextView txtVersion, txtVersionnav;
    RecyclerView rvNewYear, rvLunar, rvArrivals, rvIdeas;
    NewYearAdapter newYearAdapter;
    LunarAdapter lunarAdapter;
    ArrivalsAdapter arrivalsAdapter;
    TattooIdeaAdapter tattooIdeaAdapter;
    List<String> listNewYear;
    List<String> listLunar;
    List<String> listArrials;
    List<String> listIdeas;
    private Uri selectedImageUri;
    private String strFile;
    private ProgressDialog progressDialog;
    private AdInterstitial adInterstitial;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBar();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (checkStoragePermission()) {
            createFolderIfNotExits();
        } else {
            requestStoragePermission();
        }
        onClickItemMenu();
        initView();
        rateAuto();
    }

    private void statusBar() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black90));
    }

    private void initView() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        rvNewYear = findViewById(R.id.revNewYear);
        rvLunar = findViewById(R.id.revLunar);
        rvArrivals = findViewById(R.id.revArrivals);
        rvIdeas = findViewById(R.id.revIdeas);
        initData();
        initAds();
        //New Year
        rvNewYear.setHasFixedSize(true);
        newYearAdapter = new NewYearAdapter(listNewYear);
        rvNewYear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvNewYear.setAdapter(newYearAdapter);
        newYearAdapter.setOnClickLisener(new NewYearAdapter.OnClickLisener() {
            @Override
            public void onClickItem(String position) {
                boolean unlock = SharedPrefsUtils.getInstance(getApplicationContext()).getBoolean("unlock");
                if (unlock) {
                    showDialogAdd("NewYear/" + position);
                } else {
                    Intent intentOne = new Intent(getApplicationContext(), UnlockTattooActivity.class);
                    intentOne.putExtra("name", 1);
                    startActivity(intentOne);
                }
            }
        });
        // Lunar
        rvLunar.setHasFixedSize(true);
        lunarAdapter = new LunarAdapter(listLunar);
        rvLunar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvLunar.setAdapter(lunarAdapter);
        lunarAdapter.setOnClickLisener(new LunarAdapter.OnClickLisener() {
            @Override
            public void onClickItem(String position) {
                boolean unlocktwo = SharedPrefsUtils.getInstance(getApplicationContext()).getBoolean("unlocktwo");
                if (unlocktwo) {
                    showDialogAdd("Lunar/" + position);
                } else {
                    Intent intentTwo = new Intent(getApplicationContext(), UnlockTattooActivity.class);
                    intentTwo.putExtra("name", 2);
                    startActivity(intentTwo);
                }
            }
        });
        //Arrivals
        rvArrivals.setHasFixedSize(true);
        arrivalsAdapter = new ArrivalsAdapter(listArrials);
        rvArrivals.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvArrivals.setAdapter(arrivalsAdapter);
        arrivalsAdapter.setOnClickLisener(new ArrivalsAdapter.OnClickLisener() {
            @Override
            public void onClickItem(String position) {
                boolean unlockthree = SharedPrefsUtils.getInstance(getApplicationContext()).getBoolean("unlockthree");
                if (unlockthree) {
                    showDialogAdd(position);
                } else {
                    Intent intentThree = new Intent(getApplicationContext(), UnlockTattooActivity.class);
                    intentThree.putExtra("name", 3);
                    startActivity(intentThree);
                }
            }
        });
        // Tattoo Idea
        rvIdeas.setHasFixedSize(true);
        tattooIdeaAdapter = new TattooIdeaAdapter(listIdeas);
        rvIdeas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvIdeas.setAdapter(tattooIdeaAdapter);
        tattooIdeaAdapter.setOnClickLisener(new TattooIdeaAdapter.OnClickLisener() {
            @Override
            public void onClickItem(String position) {
                Intent intentIdeas = new Intent(getApplicationContext(), DetailIdeaActivity.class);
                intentIdeas.putExtra("fileStr", position);
                startActivity(intentIdeas);
            }
        });
    }

    private void initData() {
        listNewYear = new ArrayList<>();
        listNewYear.add("NewYear_0.png");
        listNewYear.add("NewYear_4.png");
        listNewYear.add("NewYear_5.png");
        listNewYear.add("NewYear_6.png");
        listNewYear.add("NewYear_2.png");
        listLunar = new ArrayList<>();
        listLunar.add("Lunar_2.png");
        listLunar.add("Lunar_3.png");
        listLunar.add("Lunar_4.png");
        listLunar.add("Lunar_5.png");
        listLunar.add("Lunar_6.png");
        listArrials = new ArrayList<>();
        listArrials.add("pack_6_10.png");
        listArrials.add("pack_6_11.png");
        listArrials.add("pack_6_12.png");
        listArrials.add("pack_6_13.png");
        listArrials.add("pack_6_14.png");
        listIdeas = new ArrayList<>();
        listIdeas.add("tattoo_idea_1.png");
        listIdeas.add("tattoo_idea_2.png");
        listIdeas.add("tattoo_idea_3.png");
        listIdeas.add("tattoo_idea_4.png");
        listIdeas.add("tattoo_idea_5.png");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_sample:
                    initAdsInter(SampleActivity.class);
                    return false;
                case R.id.navigation_gallery:
                    //showDialogAdd();
                    startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT)
                            .setType("image/*"), CODE_IMG_GALLERY);
                    return false;
                case R.id.navigation_design:
                    initAdsInter(AlbumActivity.class);
                    return false;
                case R.id.navigation_ideas:
                    initAdsInter(IdeaActivity.class);
                    return false;
            }
            return false;
        }
    };

    private void initAdsInter(Class classActivity) {
        progressDialog.show();
        Random rd = new Random();
        int rands = rd.nextInt(10);
        if(rands < Common.is_random) {
            adInterstitial.show(new com.oneadx.android.oneads.AdListener() {
                @Override
                public void onAdClosed() {
                    progressDialog.dismiss();
                    Intent intentIdea = new Intent(getApplicationContext(), classActivity);
                    startActivity(intentIdea);
                }
            });
        }else{
            progressDialog.dismiss();
            Intent intentIdea = new Intent(getApplicationContext(), classActivity);
            startActivity(intentIdea);
        }
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
                } else if (id == R.id.savelocation) {
                    Toast.makeText(MainActivity.this, "Save in: " + Common.folderPath, Toast.LENGTH_LONG).show();
                } else if (id == R.id.rate) {
                    rateAuto();
                } else if (id == R.id.about) {
                    showDialog();
                } else if (id == R.id.security) {
                    String url = "https://docs.google.com/document/d/1xMaG0Lpvaq72mtTKURcmA0vCHnty31OxVE57QBISGbE/edit?usp=sharing";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initAds() {
        adInterstitial = new AdInterstitial(this);
        adInterstitial.load();
    }

    @OnClick({R.id.btnMenu, R.id.btnNotification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMenu:
//                btnMenu.setColorFilter(Color.RED);
                onClickDrawer();
                break;
            case R.id.btnNotification:
                adInterstitial.show(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        Toast.makeText(MainActivity.this, "Better luck next time. Thank you!!!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void showDialogAdd(String fileTattoo) {
        strFile = fileTattoo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add the tattoo to your photo from: ");
        builder.setCancelable(false);
        builder.setPositiveButton("SAMPLE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intentSample = new Intent(getApplicationContext(), SampleActivity.class);
                intentSample.putExtra("fileTattoo", fileTattoo);
                startActivity(intentSample);
//                AppUtil.deleteDir(new File(AppUtil.getPath_DataTemp()));
//                AppUtil.createFolderIfNotExits();
//                StringBuilder sb = new StringBuilder();
//                sb.append(AppUtil.getPath_DataTemp());
//                sb.append("/");
//                sb.append(AppUtil.createNameTime());
//                sb.append(".png");
//                selectedImageUri = FileProvider.getUriForFile(
//                        getApplicationContext(),
//                        AppUtil.FILE_PROVIDER, //(use your app signature + ".provider" )
//                        new File(sb.toString()));
//
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
//                startActivityForResult(intent, CODE_IMG_CAMERA);
            }
        });
        builder.setNegativeButton("PHOTO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT)
                        .setType("image/*"), CODE_IMG_GALLERY);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + selectedImageUri);
        if ((requestCode == CODE_IMG_GALLERY || requestCode == CODE_IMG_CAMERA)) {
            if (data != null) {
                selectedImageUri = data.getData();
                Log.e(TAG, "onActivityResult: " + selectedImageUri);
                startCrop(selectedImageUri);
            } else {
                if (selectedImageUri != null) {
                    startCrop(selectedImageUri);
                }
            }

        }
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri mImageUri = UCrop.getOutput(data);
            if (mImageUri != null) {
                Intent intent = new Intent(getApplicationContext(), EditImageActivity.class);
                intent.putExtra("desgin", mImageUri.toString());
                intent.putExtra("fileTattoo", strFile);
                Log.e(TAG, "onActivityResult: handleIntentImage " + mImageUri.toString());
                startActivity(intent);
            }
        }
    }

    private void startCrop(@NonNull Uri uri) {
        //String destinationFileName = SAMPLE_CROPPED_IMG_NAME;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        timeStamp += ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), timeStamp)));
        uCrop.withAspectRatio(1, 1);
        //uCrop.withAspectRatio(3,4);
        uCrop.withMaxResultSize(350, 350);
        uCrop.withOptions(getCropOptions());
        uCrop.start(this);
    }

    private UCrop.Options getCropOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setStatusBarColor(getResources().getColor(R.color.blue_light));
        options.setToolbarColor(getResources().getColor(R.color.blue_light));
        options.setAllowedGestures(UCropActivity.ALL, UCropActivity.ALL, UCropActivity.ALL);
        return options;
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
    //Rate
    private void rateAuto() {
        RatingDialog ratingDialog = new RatingDialog(this);
        ratingDialog.showDialog();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}