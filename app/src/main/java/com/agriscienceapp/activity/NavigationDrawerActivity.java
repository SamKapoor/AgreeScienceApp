package com.agriscienceapp.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agriscienceapp.R;
import com.agriscienceapp.common.Common;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.fragments.AgriScienceTVFragment;
import com.agriscienceapp.fragments.KhedutSafalGathaFragment;
import com.agriscienceapp.fragments.KrushiSalahFragment;
import com.agriscienceapp.fragments.MapbajarFragment;
import com.agriscienceapp.fragments.PakPasandFragment;
import com.agriscienceapp.fragments.SamacharFragment;
import com.agriscienceapp.fragments.UtaraFragment;
import com.agriscienceapp.model.PakPasandDetailModel;
import com.agriscienceapp.model.PakPasandModel;
import com.agriscienceapp.webservice.RestClientRetroFit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = NavigationDrawerActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_drawer_ll_top_heading)
    RelativeLayout activityDrawerLlTopHeading;
    @Bind(R.id.img_samachar)
    ImageView imgSamachar;
    @Bind(R.id.tv_samachar)
    TextView tvSamachar;
    @Bind(R.id.ll_item_samachar)
    LinearLayout llItemSamachar;
    @Bind(R.id.img_bajar)
    ImageView imgBajar;
    @Bind(R.id.tv_bajar)
    TextView tvBajar;
    @Bind(R.id.ll_item_bajar)
    LinearLayout llItemBajar;
    @Bind(R.id.img_krushishalah)
    ImageView imgKrushishalah;
    @Bind(R.id.tv_krushisalah)
    TextView tvKrushisalah;
    @Bind(R.id.ll_item_krushisalah)
    LinearLayout llItemKrushisalah;
    @Bind(R.id.img_khedut_gatha)
    ImageView imgKhedutGatha;
    @Bind(R.id.tv_khedut_gatha)
    TextView tvKhedutGatha;
    @Bind(R.id.ll_item_khedut_gatha)
    LinearLayout llItemKhedutGatha;
    @Bind(R.id.img_agrisci_tv)
    ImageView imgTv;
    @Bind(R.id.tv_agrisci_tv)
    TextView tvAgrisciTv;
    @Bind(R.id.img_utara)
    ImageView imgTvUtara;
    @Bind(R.id.tv_utara)
    TextView tvUtara;
    @Bind(R.id.ll_item_utara)
    LinearLayout llItemUtara;
    @Bind(R.id.activity_drawer_ll_option)
    LinearLayout activityDrawerLlOption;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.ll_samachar)
    LinearLayout llSamachar;
    @Bind(R.id.ll_bajar)
    LinearLayout llBajar;
    @Bind(R.id.ll_krushisalah)
    LinearLayout llKrushisalah;
    @Bind(R.id.ll_khedut_gatha)
    LinearLayout llKhedutGatha;
    @Bind(R.id.ll_agrisci_tv)
    LinearLayout llAgrisciTv;
    @Bind(R.id.ll_utara)
    LinearLayout llUtara;
    @Bind(R.id.tv_pak_pasand)
    AgriScienceTextView tvPakPasand;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Bind(R.id.frame_container)
    FrameLayout frameContainer;
    @Bind(R.id.ll_item_agrisci_tv)
    LinearLayout llItemAgrisciTv;

    ArrayList<PakPasandDetailModel> getPakPasandDetailArrayList;
    ProgressDialog progressDialog;

    private int previousValue = 0;
    private TextView tvRotateAdd;
    int position = 0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        ButterKnife.bind(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "Shruti-bold.ttf");
        tvSamachar.setTypeface(font);
        tvBajar.setTypeface(font);
        tvKrushisalah.setTypeface(font);
        tvKhedutGatha.setTypeface(font);
        tvAgrisciTv.setTypeface(font);
        tvUtara.setTypeface(font);

       /* FontUtils.setFontForText(NavigationDrawerActivity.this, tvSamachar, "bold");
        FontUtils.setFontForText(NavigationDrawerActivity.this, tvBajar, "bold");
        FontUtils.setFontForText(NavigationDrawerActivity.this, tvKrushisalah, "bold");
        FontUtils.setFontForText(NavigationDrawerActivity.this, tvKhedutGatha, "bold");
        FontUtils.setFontForText(NavigationDrawerActivity.this, tvAgrisciTv, "bold");
        FontUtils.setFontForText(NavigationDrawerActivity.this, tvUtara, "bold");*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvRotateAdd = (TextView) toolbar.findViewById(R.id.tv_rotate_add);

        getPakPasandDetailArrayList = new ArrayList<PakPasandDetailModel>();

        getPakPasand(getAllSavedCrops());

       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_logo);*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (getIntent().getExtras() != null) {
            previousValue = getIntent().getIntExtra("Value", 1);
        }

        if (previousValue == 1) {
            SamacharFragment samacharFragment = new SamacharFragment();
            getFragmentData(samacharFragment, SamacharFragment.class.getSimpleName());
        } else if (previousValue == 2) {
            MapbajarFragment mapbajarFragment = new MapbajarFragment();
            getFragmentData(mapbajarFragment, MapbajarFragment.class.getSimpleName());
        } else if (previousValue == 3) {
            KrushiSalahFragment krushiSalahFragment = new KrushiSalahFragment();
            getFragmentData(krushiSalahFragment, KrushiSalahFragment.class.getSimpleName());
        } else if (previousValue == 4) {
            KhedutSafalGathaFragment khedutSafalGathaFragment = new KhedutSafalGathaFragment();
            getFragmentData(khedutSafalGathaFragment, KhedutSafalGathaFragment.class.getSimpleName());
        } else if (previousValue == 5) {
            AgriScienceTVFragment agriScienceTVFragment = new AgriScienceTVFragment();
            getFragmentData(agriScienceTVFragment, AgriScienceTVFragment.class.getSimpleName());
        } else if (previousValue == 6) {
            UtaraFragment utaraFragment = new UtaraFragment();
            getFragmentData(utaraFragment, UtaraFragment.class.getSimpleName());
        } else if (previousValue == 7) {
            PakPasandFragment pakPasandFragment = new PakPasandFragment();
            getFragmentData(pakPasandFragment, PakPasandFragment.class.getSimpleName());
        }


        /*KrushiSalahFragment krushiSalahFragment = new KrushiSalahFragment();
        getFragmentData(krushiSalahFragment, KrushiSalahFragment.class.getSimpleName());*/
    }

    private String getAllSavedCrops() {
        String savedCrops = "";
        if (Common.getStringPrefrences(this, getString(R.string.pref_selected_crop1id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(this, getString(R.string.pref_selected_crop1id), getString(R.string.app_name)) + ",";

        if (Common.getStringPrefrences(this, getString(R.string.pref_selected_crop2id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(this, getString(R.string.pref_selected_crop2id), getString(R.string.app_name)) + ",";

        if (Common.getStringPrefrences(this, getString(R.string.pref_selected_crop3id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(this, getString(R.string.pref_selected_crop3id), getString(R.string.app_name)) + ",";

        if (Common.getStringPrefrences(this, getString(R.string.pref_selected_crop4id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(this, getString(R.string.pref_selected_crop4id), getString(R.string.app_name)) + ",";

        if (Common.getStringPrefrences(this, getString(R.string.pref_selected_crop5id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(this, getString(R.string.pref_selected_crop5id), getString(R.string.app_name)) + ",";

        if (savedCrops.trim().length() > 0) {
            return savedCrops.substring(0, savedCrops.length());
        } else {
            return "0-0";
        }
    }
    public void getPakPasand(String cropIds) {
        RestClientRetroFit.getWebServices().getPakPasand(cropIds, 1, new Callback<PakPasandModel>() {
            @Override
            public void success(PakPasandModel pakPasandModel, Response response) {
                getPakPasandDetailArrayList = (ArrayList<PakPasandDetailModel>) pakPasandModel.getDetail();
                Log.e(TAG, "size " + getPakPasandDetailArrayList.size());

                if (getPakPasandDetailArrayList != null && getPakPasandDetailArrayList.size() > 0) {
                    startTimer();
//                    tvRotateAdd.setText(getPakPasandDetailArrayList.get(position).getPakPasand().replace("ramesh", "\n"));
                    Log.e(TAG, "pak Pasand");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "pak Pasand failure" + error);
            }
        });
    }

    @OnClick({R.id.ll_samachar, R.id.ll_bajar, R.id.ll_krushisalah, R.id.ll_khedut_gatha, R.id.ll_agrisci_tv, R.id.ll_utara})
    void ButtonClick(View view) {
        if (view == llSamachar) {
            //    Toast.makeText(NavigationDrawerActivity.this, "Samachar", Toast.LENGTH_SHORT).show();
            SamacharFragment samacharFragment = new SamacharFragment();
            getFragmentData(samacharFragment, SamacharFragment.class.getSimpleName());

        } else if (view == llBajar) {
            //    Toast.makeText(NavigationDrawerActivity.this, "Bajar", Toast.LENGTH_SHORT).show();
            MapbajarFragment mapbajarFragment = new MapbajarFragment();
            getFragmentData(mapbajarFragment, MapbajarFragment.class.getSimpleName());

        } else if (view == llKrushisalah) {
            //    Toast.makeText(NavigationDrawerActivity.this, "Krushi Salah", Toast.LENGTH_SHORT).show();
            KrushiSalahFragment krushiSalahFragment = new KrushiSalahFragment();
            getFragmentData(krushiSalahFragment, KrushiSalahFragment.class.getSimpleName());

        } else if (view == llKhedutGatha) {
            //    Toast.makeText(NavigationDrawerActivity.this, "Khedut Gatha", Toast.LENGTH_SHORT).show();
            KhedutSafalGathaFragment khedutSafalGathaFragment = new KhedutSafalGathaFragment();
            getFragmentData(khedutSafalGathaFragment, KhedutSafalGathaFragment.class.getSimpleName());

        } else if (view == llAgrisciTv) {
            //    Toast.makeText(NavigationDrawerActivity.this, "AgriScience", Toast.LENGTH_SHORT).show();
            AgriScienceTVFragment agriScienceTVFragment = new AgriScienceTVFragment();
            getFragmentData(agriScienceTVFragment, AgriScienceTVFragment.class.getSimpleName());

        } else if (view == llUtara) {
            //    Toast.makeText(NavigationDrawerActivity.this, "Utara", Toast.LENGTH_SHORT).show();
            UtaraFragment utaraFragment = new UtaraFragment();
            getFragmentData(utaraFragment, UtaraFragment.class.getSimpleName());
        } else if (view == tvPakPasand) {
            //    Toast.makeText(NavigationDrawerActivity.this, "Utara", Toast.LENGTH_SHORT).show();
            PakPasandFragment pakPasandFragment = new PakPasandFragment();
            getFragmentData(pakPasandFragment, PakPasandFragment.class.getSimpleName());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void getFragmentData(Fragment fragmentname, String strName) {
        fragmentManager = getSupportFragmentManager();
        //  strName.substring(0, strName.length());
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragmentname, strName);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Advertisement code on Toolbar

    Timer timer;
    TimerTask timerTask;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    public void startTimer() {

        stoptimertask();

        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 5000ms
        timer.schedule(timerTask, 0, 5000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        if (position >= getPakPasandDetailArrayList.size())
                            position = 0;

                        if(getPakPasandDetailArrayList.size() >0) {
                            tvRotateAdd.setText(getPakPasandDetailArrayList.get(position).getPakPasand().replace("ramesh", "\n").trim());
                            position++;
                        }
                        //get the current timeStamp

                    }
                });
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stoptimertask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stoptimertask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPakPasandDetailArrayList != null && getPakPasandDetailArrayList.size() > 0) {
            startTimer();
//                    tvRotateAdd.setText(getPakPasandDetailArrayList.get(position).getPakPasand().replace("ramesh", "\n"));
            Log.e(TAG, "pak Pasand");
        }
    }
}
