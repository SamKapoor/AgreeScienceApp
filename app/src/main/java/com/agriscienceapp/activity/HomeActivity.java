package com.agriscienceapp.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.agriscienceapp.R;
import com.agriscienceapp.font.AgriScienceTextView;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Bind(R.id.iv_app_logo)
    ImageView ivAppLogo;
    @Bind(R.id.tv_pak_pasand)
    AgriScienceTextView tvPakPasand;
    @Bind(R.id.activity_home_ll_top)
    LinearLayout activityHomeLlTop;
    @Bind(R.id.iv_samachar)
    ImageView ivSamachar;
    @Bind(R.id.iv_bajar)
    ImageView ivBajar;
    @Bind(R.id.ll_first_image)
    LinearLayout llFirstImage;
    @Bind(R.id.tv_samachar)
    AgriScienceTextView tvSamachar;
    @Bind(R.id.tv_bajar)
    AgriScienceTextView tvBajar;
    @Bind(R.id.ll_first_text)
    LinearLayout llFirstText;
    @Bind(R.id.ll_main_first)
    LinearLayout llMainFirst;
    @Bind(R.id.iv_krushisalah)
    ImageView ivKrushisalah;
    @Bind(R.id.iv_khedutsafalgatha)
    ImageView ivKhedutsafalgatha;
    @Bind(R.id.ll_second_image)
    LinearLayout llSecondImage;
    @Bind(R.id.tv_krushisalah)
    AgriScienceTextView tvKrushisalah;
    @Bind(R.id.tv_khedutsafalgatha)
    AgriScienceTextView tvKhedutsafalgatha;
    @Bind(R.id.ll_second_text)
    LinearLayout llSecondText;
    @Bind(R.id.ll_main_second)
    LinearLayout llMainSecond;
    @Bind(R.id.iv_agriscience)
    ImageView ivAgriscience;
    @Bind(R.id.iv_utara)
    ImageView ivUtara;
    @Bind(R.id.ll_third_image)
    LinearLayout llThirdImage;
    @Bind(R.id.tv_agriscience)
    AgriScienceTextView tvAgriscience;
    @Bind(R.id.tv_utara)
    AgriScienceTextView tvUtara;
    @Bind(R.id.ll_first_)
    LinearLayout llFirst;
    @Bind(R.id.ll_main_third)
    LinearLayout llMainThird;
    @Bind(R.id.home)
    LinearLayout home;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //   client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Typeface font = Typeface.createFromAsset(getAssets(), "Shruti-bold.ttf");
        tvSamachar.setTypeface(font);
        tvBajar.setTypeface(font);
        tvBajar.setTypeface(font);
        tvKrushisalah.setTypeface(font);
        tvKhedutsafalgatha.setTypeface(font);
        tvAgriscience.setTypeface(font);
        tvUtara.setTypeface(font);

     /*   if (getIntent().getExtras() != null) {
            Value = getIntent().getIntExtra("Value", 7);
        }*/

        /*FontUtils.setFontForText(get(), tvSamachar, "bold");
        Log.e(TAG, "Fontutills");
        FontUtils.setFontForText(HomeActivity.this, tvBajar, "bold");
        FontUtils.setFontForText(HomeActivity.this, tvKrushisalah, "bold");
        FontUtils.setFontForText(HomeActivity.this, tvKhedutsafalgatha, "bold");
        FontUtils.setFontForText(HomeActivity.this, tvAgriscience, "bold");
        FontUtils.setFontForText(getApplication(), tvUtara, "bold");*/
    }

    @OnClick({R.id.tv_pak_pasand, R.id.iv_samachar, R.id.iv_bajar, R.id.iv_krushisalah, R.id.iv_khedutsafalgatha, R.id.iv_agriscience, R.id.iv_utara, R.id.tv_samachar, R.id.tv_bajar, R.id.tv_krushisalah, R.id.tv_khedutsafalgatha, R.id.tv_agriscience, R.id.tv_utara})
    void ButtonClick(View view) {
        Intent intent = null;

        if (view == ivSamachar) {
            //  Toast.makeText(HomeActivity.this, "Samachar", Toast.LENGTH_SHORT).show();
//            ivSamachar.setSelected(true);
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 1);
        } else if (view == tvSamachar) {
            //  Toast.makeText(HomeActivity.this, "Samachar", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 1);
        } else if (view == ivBajar) {
            //  Toast.makeText(HomeActivity.this, "Bajar", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 2);
        } else if (view == tvBajar) {
            //  Toast.makeText(HomeActivity.this, "Samachar", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 2);
        } else if (view == ivKrushisalah) {
            //  Toast.makeText(HomeActivity.this, "Krushisalah", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 3);
        } else if (view == tvKrushisalah) {
            //  Toast.makeText(HomeActivity.this, "Samachar", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 3);
        } else if (view == ivKhedutsafalgatha) {
            //  Toast.makeText(HomeActivity.this, "KhedutGatha", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 4);
        } else if (view == tvKhedutsafalgatha) {
            //  Toast.makeText(HomeActivity.this, "Samachar", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 4);
        } else if (view == ivAgriscience) {
            //  Toast.makeText(HomeActivity.this, "TV", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 5);
        } else if (view == tvAgriscience) {
            //  Toast.makeText(HomeActivity.this, "Samachar", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 5);
        } else if (view == tvUtara) {
            //  Toast.makeText(HomeActivity.this, "Samachar", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 6);
        } else if (view == ivUtara) {
            //  Toast.makeText(HomeActivity.this, "Utara", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 6);
        } else if (view == tvPakPasand) {
            intent = new Intent(HomeActivity.this, NavigationDrawerActivity.class);
            intent.putExtra("Value", 7);
        }
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
