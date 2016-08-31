package com.agriscienceapp.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.common.Utility;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.AgriScienceTVDetailModel;
import com.agriscienceapp.model.AgriScienceTVModel;
import com.agriscienceapp.model.Detail;
import com.agriscienceapp.webservice.RestClientRetroFit;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgriScienceTVFragment extends Fragment {

    private final String TAG = AgriScienceTVFragment.class.getSimpleName();
    @Bind(R.id.listview_agriscience_tv)
    ListView listviewAgriscienceTv;
    @Bind(R.id.iv_ads_tv)
    ImageView ivAgriscienceTvAd;

    private View AgriScienceTVView;
    private ProgressDialog progressDialog;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    ImageLoader imageLoader;

    ArrayList<AgriScienceTVDetailModel> getAgriScienceDetailModelArrayList;

    String packageName = "com.google.android.youtube";
    private LayoutInflater inflater;

    public AgriScienceTVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AgriScienceTVView = inflater.inflate(R.layout.fragment_agri_science_tv, container, false);
        ButterKnife.bind(this, AgriScienceTVView);

        this.inflater = inflater;
        imageLoader = ImageLoader.getInstance();
        initLoader(getActivity());

        getAgriScienceDetailModelArrayList = new ArrayList<AgriScienceTVDetailModel>();

   //     progressDialog = ProgressDialog.show(getActivity(), "Fetching Data", "Jai Kisaan...", false, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Jai Kisaan...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();


        if (Utility.isConnectingToInternet(getActivity())) {
            RestClientRetroFit.getWebServices().getAgriScienceTVList(new Callback<AgriScienceTVModel>() {

                @Override
                public void success(AgriScienceTVModel agriScienceTVModel, Response response) {
                   getAgriScienceDetailModelArrayList = (ArrayList<AgriScienceTVDetailModel>) agriScienceTVModel.getDetail();
                    Log.e(TAG, "" + getAgriScienceDetailModelArrayList.size());
                    progressDialog.dismiss();

                    if (getAgriScienceDetailModelArrayList != null && getAgriScienceDetailModelArrayList.size() > 0) {
                        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity(), getAgriScienceDetailModelArrayList);
                        listviewAgriscienceTv.setAdapter(customBaseAdapter);
                    }

                    if (!TextUtils.isEmpty(getAgriScienceDetailModelArrayList.get(0).getMainAdd())) {
                        Glide.with(getActivity()).load(getAgriScienceDetailModelArrayList.get(0).getMainAdd()).into(ivAgriscienceTvAd);
//                            aq.id(R.id.iv_ads_samachar).progress(R.id.progressbar_samachar_header).image(getSamacharDetailListArrayList.get(0).getThumbs(), false, false);
                    } else {
                        ivAgriscienceTvAd.setVisibility(View.GONE);
                    }
                    ivAgriscienceTvAd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setAdvClick(getAgriScienceDetailModelArrayList.get(0));
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                }
            });
        }else{
            Toast.makeText(getActivity(), "No internet Connection.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        ButterKnife.bind(this, AgriScienceTVView);
        return AgriScienceTVView;
    }

    private void setAdvClick(final AgriScienceTVDetailModel samacharModel) {
        if (samacharModel != null) {
            if (samacharModel.getContactNo().trim().length() > 0) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + samacharModel.getContactNo().trim()));
                startActivity(intent);
            } else if (samacharModel.getPopup().trim().length() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();

                View dialogLayout = inflater.inflate(R.layout.detail_adv_dialog_layout, null);
                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
//                        imageLoader.displayImage(samacharModel.getPopup().trim(), image, optionsAdBanner);
                        System.out.println("Popup: 11- " + samacharModel.getPopup().trim());
                        Glide.with(getActivity()).load(samacharModel.getPopup().trim()).into(image);
                    }
                });
                dialog.show();
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class CustomBaseAdapter extends BaseAdapter {
        Context context;
        List<AgriScienceTVDetailModel> rowItems;

        public CustomBaseAdapter(Context context, List<AgriScienceTVDetailModel> items) {
            this.context = context;
            this.rowItems = items;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_agri_science_tv_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            FontUtils.setFontForText(context, holder.rowTvAgrisciTvTitle, "bold");

            try {
                if (!TextUtils.isEmpty(getAgriScienceDetailModelArrayList.get(position).getVideoTitle())) {
                    holder.rowTvAgrisciTvTitle.setText(rowItems.get(position).getVideoTitle());
                }
                if (!TextUtils.isEmpty(getAgriScienceDetailModelArrayList.get(position).getThumbs())) {
                    imageLoader.displayImage(rowItems.get(position).getThumbs(), holder.rowTvAgrisciIvBack, options);
                }
            }catch (Exception e){

            }
            convertView.setSelected(true);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, "onClick: Youtube URL:" + rowItems.get(position).getVideoLink());
                //    Toast.makeText(context, "Youtube Click ", Toast.LENGTH_SHORT).show();

                    if (isAppAvailable(getActivity(), packageName)) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(rowItems.get(position).getVideoLink())));
                    } else {
                        Toast.makeText(context, "Youtube is not installed. ", Toast.LENGTH_SHORT).show();
                    }
                }
            });


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

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'row_agri_science_tv_list.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */

        class ViewHolder {
            @Bind(R.id.row_tv_agrisci_iv_back)
            ImageView rowTvAgrisciIvBack;
            @Bind(R.id.row_tv_agrisci_iv)
            ImageView rowTvAgrisciIv;
            @Bind(R.id.row_ll_agrisci_tv_link)
            RelativeLayout rowLlAgrisciTvLink;
            @Bind(R.id.row_tv_agrisci_tv_title)
            AgriScienceTextView rowTvAgrisciTvTitle;
            @Bind(R.id.row_ll_agrisci_tv_title)
            LinearLayout rowLlAgrisciTvTitle;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'row_krushi_salah_list.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
    private void initLoader(FragmentActivity activity) {
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

    private boolean isAppAvailable(Context context, String pkgName) {

        boolean isAppInstall = false;
        PackageManager pm = context.getPackageManager();

        List<ApplicationInfo> list = pm.getInstalledApplications(0);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).packageName.equals(pkgName)) {
                //do what you want
                isAppInstall = true;
            }

        }
        return isAppInstall;
    }


}