package com.agriscienceapp.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.common.Utility;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.KrushiSalahDetailModel;
import com.agriscienceapp.model.KrushiSalahModel;
import com.agriscienceapp.webservice.RestClientRetroFit;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KrushiSalahFragment extends Fragment {

    private final String TAG = KrushiSalahFragment.class.getSimpleName();

    @Bind(R.id.grid_krushi_salah)
    GridView gridKrushiSalah;
    @Bind(R.id.adView_KrushiSalah)
    AdView adViewKrushiSalah;

    private View KrushiSalahView;
    private ProgressDialog progressDialog;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArrayList<KrushiSalahDetailModel> getKrushiSalahDetailModelListArrayList;

    public KrushiSalahFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        KrushiSalahView = inflater.inflate(R.layout.fragment_krushi_salah, container, false);
        ButterKnife.bind(this, KrushiSalahView);

        /*AdRequest adRequest = new AdRequest.Builder().build();
        adViewKrushiSalah.loadAd(adRequest);*/

        imageLoader = ImageLoader.getInstance();
        initLoader(getActivity());

        getKrushiSalahDetailModelListArrayList = new ArrayList<KrushiSalahDetailModel>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Jai Kisaan...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (Utility.isConnectingToInternet(getActivity())) {
            RestClientRetroFit.getWebServices().getKrushiSalahList(new Callback<KrushiSalahModel>() {

                @Override
                public void success(KrushiSalahModel krushiSalahModel, Response response) {
                    getKrushiSalahDetailModelListArrayList = (ArrayList<KrushiSalahDetailModel>) krushiSalahModel.getDetail();
                    Log.e(TAG, "" + getKrushiSalahDetailModelListArrayList.size());
                    progressDialog.dismiss();

                    if (getKrushiSalahDetailModelListArrayList != null && getKrushiSalahDetailModelListArrayList.size() > 0) {
                        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity(), getKrushiSalahDetailModelListArrayList);
                        gridKrushiSalah.setAdapter(customBaseAdapter);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No internet Connection.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        return KrushiSalahView;
    }

    @OnItemClick({R.id.grid_krushi_salah})
    void GridViewOnItemClick(int position) {
            //  Toast.makeText(getActivity(), "KrushiSalah  Fragment: " + getKrushiSalahDetailModelListArrayList.get(position).getCropTitleId(), Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putInt("cropTitleID", getKrushiSalahDetailModelListArrayList.get(position).getCropTitleId());
            bundle.putString("cropTitle", getKrushiSalahDetailModelListArrayList.get(position).getCropTitleName());
            bundle.putString("cropThumb", getKrushiSalahDetailModelListArrayList.get(position).getThumbs());
            bundle.putInt("position", position);

            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            KrushiSalahDetailFragment krushiSalahDetailFragment = new KrushiSalahDetailFragment();
            fragmentTransaction.replace(R.id.frame_container, krushiSalahDetailFragment, KrushiSalahDetailFragment.class.getSimpleName());
            krushiSalahDetailFragment.setArguments(bundle);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(KrushiSalahDetailFragment.class.getSimpleName());
            fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class CustomBaseAdapter extends BaseAdapter {
        Context context;
        List<KrushiSalahDetailModel> rowItems;
        ImageLoader imageLoader;
        DisplayImageOptions displayImageOptions;
        LayoutInflater mInflater;

        public CustomBaseAdapter(Context context, List<KrushiSalahDetailModel> items) {
            mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            this.rowItems = items;
            imageLoader = ImageLoader.getInstance();
            displayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.app_logo)
                    .showImageForEmptyUri(R.drawable.app_logo)
                    .showImageOnFail(R.drawable.app_logo).cacheInMemory(true)
                    .cacheOnDisc(true).build();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_krushi_salah_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

//            holder.rowSamacharTvTitle.setText(rowItems.getNewsTitle());

            FontUtils.setFontForText(context, holder.tvKrushisalahItem, "bold");

            try {
                if (!TextUtils.isEmpty(getKrushiSalahDetailModelListArrayList.get(position).getCropTitleName())) {
                    holder.tvKrushisalahItem.setText(rowItems.get(position).getCropTitleName());
                }
                if (!TextUtils.isEmpty(getKrushiSalahDetailModelListArrayList.get(position).getThumbs())) {
                    imageLoader.displayImage(rowItems.get(position).getThumbs(), holder.ivKrushisalahItem, options);
                }
            }catch (Exception e){

            }
            return convertView;
        }

        @Override
        public int getCount() {
            return rowItems.size();
        }

        @Override
        public Object getItem(int position) {
            return rowItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return rowItems.indexOf(getItem(position));
        }

        class ViewHolder {
            @Bind(R.id.iv_krushisalah_item)
            ImageView ivKrushisalahItem;
            @Bind(R.id.tv_krushisalah_item)
            TextView tvKrushisalahItem;
            @Bind(R.id.row_ll_krushisalah_item)
            LinearLayout rowLlKrushisalahItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public void initLoader(Context context) {
        config = new ImageLoaderConfiguration.Builder(getActivity())
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)
                .imageDownloader(new BaseImageDownloader(getActivity())) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.app_logo)
                .showImageForEmptyUri(R.drawable.app_logo)
                .showImageOnFail(R.drawable.app_logo)
                .build();
        ImageLoader.getInstance().init(config);
    }
}