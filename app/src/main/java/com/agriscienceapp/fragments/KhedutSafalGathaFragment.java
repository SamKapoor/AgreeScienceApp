package com.agriscienceapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.common.Common;
import com.agriscienceapp.common.Utility;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.KheduSafalGathaDetailModel;
import com.agriscienceapp.model.KhedutSafalGathaModel;
import com.agriscienceapp.webservice.AndroidNetworkUtility;
import com.agriscienceapp.webservice.RestClientRetroFit;
import com.androidquery.AQuery;
import com.google.android.gms.ads.AdView;
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
public class KhedutSafalGathaFragment extends Fragment {

    private final String TAG = KhedutSafalGathaFragment.class.getSimpleName();

    @Bind(R.id.listview_khedut_safal_gatha)
    ListView listviewKhedutSafalGatha;
    @Bind(R.id.adView)
    AdView adView;
    @Bind(R.id.iv_ads_khedutgatha)
    ImageView ivAdsKhedutgatha;

    private View KhedutSafalGathaView;
    ImageLoaderConfiguration config;
    ProgressDialog progressDialog;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    ArrayList<KheduSafalGathaDetailModel> getKhedutSafalGathaDetailListArrayList;
    ArrayList<KheduSafalGathaDetailModel> tempArrListKhedutGatha;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LayoutInflater inflaterView;
    int position = 0;

    private AQuery aq;
    private DisplayImageOptions optionsAdBanner;

    public KhedutSafalGathaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        KhedutSafalGathaView = inflater.inflate(R.layout.fragment_khedut_safal_gatha, container, false);
        ButterKnife.bind(this, KhedutSafalGathaView);
        optionsAdBanner = new DisplayImageOptions.Builder()
                .build();
        aq = new AQuery(getActivity());

        imageLoader = ImageLoader.getInstance();
        initLoader(getActivity());

        getKhedutSafalGathaDetailListArrayList = new ArrayList<KheduSafalGathaDetailModel>();

        inflaterView = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Jai Kisaan...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (Utility.isConnectingToInternet(getActivity())) {
            RestClientRetroFit.getWebServices().getKhedutSafalGathaList(new Callback<KhedutSafalGathaModel>() {

                @Override
                public void success(KhedutSafalGathaModel khedutSafalGathaModel, Response response) {
                    getKhedutSafalGathaDetailListArrayList = (ArrayList<KheduSafalGathaDetailModel>) khedutSafalGathaModel.getDetail();
                    Log.e(TAG, "" + getKhedutSafalGathaDetailListArrayList.size());
                    progressDialog.dismiss();

                    try {
                        if (getKhedutSafalGathaDetailListArrayList != null && getKhedutSafalGathaDetailListArrayList.size() > 0) {

                            //header of listview
                            View listHeader = inflater.inflate(R.layout.header_khedutsafalgatha, null);
                            listviewKhedutSafalGatha.addHeaderView(listHeader);

                            TextView headerTvKhedutgatha = (TextView) listHeader.findViewById(R.id.header_tv_khedutgatha);
                            ImageView headerIvKhedutgatha = (ImageView) listHeader.findViewById(R.id.header_iv_KhedutGatha);
                            LinearLayout headerllKhedutgataha = (LinearLayout) listHeader.findViewById(R.id.ll_header_KhedutGatha);
                            //    ImageView headerIVshare = (ImageView) listHeader.findViewById(R.id.iv_khedutgatha_share);

                            FontUtils.setFontForText(getActivity(), headerTvKhedutgatha, "bold");
                            if (!TextUtils.isEmpty(getKhedutSafalGathaDetailListArrayList.get(0).getStoryTitle())) {
                                headerTvKhedutgatha.setText(getKhedutSafalGathaDetailListArrayList.get(0).getStoryTitle().toString());
                            }
                            if (!TextUtils.isEmpty(getKhedutSafalGathaDetailListArrayList.get(0).getThumbs()) && !getKhedutSafalGathaDetailListArrayList.get(0).getThumbs().equalsIgnoreCase("null")) {
                                imageLoader.displayImage(getKhedutSafalGathaDetailListArrayList.get(0).getThumbs().replace("_Thumbs", ""), headerIvKhedutgatha, options);
                                aq.id(R.id.header_iv_KhedutGatha).progress(R.id.progressbar_khedutgatha_header).image(getKhedutSafalGathaDetailListArrayList.get(0).getThumbs(), false, false);
//                            header_rl_img_khedutgatha.setVisibility(View.VISIBLE);
                            } else {
//                            header_rl_img_khedutgatha.setVisibility(View.GONE);
                            }

                            if (!TextUtils.isEmpty(getKhedutSafalGathaDetailListArrayList.get(0).getMainAdd()) && !getKhedutSafalGathaDetailListArrayList.get(0).getMainAdd().equalsIgnoreCase("null")) {
                                imageLoader.displayImage(getKhedutSafalGathaDetailListArrayList.get(0).getMainAdd(), ivAdsKhedutgatha, optionsAdBanner);
//                            aq.id(R.id.iv_ads_samachar).progress(R.id.progressbar_samachar_header).image(getSamacharDetailListArrayList.get(0).getThumbs(), false, false);
                            } else {
                                ivAdsKhedutgatha.setVisibility(View.GONE);
                            }

                            ivAdsKhedutgatha.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setAdvClick(getKhedutSafalGathaDetailListArrayList.get(0));
                                }
                            });
                            //      Arraylist for listview
                            tempArrListKhedutGatha = new ArrayList<KheduSafalGathaDetailModel>();
                            for (int count = 1; count < getKhedutSafalGathaDetailListArrayList.size(); count++) {
                                tempArrListKhedutGatha.add(getKhedutSafalGathaDetailListArrayList.get(count));
                            }

                            CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity(), tempArrListKhedutGatha);
                            listviewKhedutSafalGatha.setAdapter(customBaseAdapter);

                            headerllKhedutgataha.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                Toast.makeText(getActivity(), "KhedutSafalGatha  Fragment: " + getKhedutSafalGathaDetailListArrayList.get(0).getStoryId(), Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("storyID", getKhedutSafalGathaDetailListArrayList.get(0).getStoryId());
                                    bundle.putInt("position", position);

                                    fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    KhedutSafalGathaDetailFragment khedutSafalGathaDetailFragment = new KhedutSafalGathaDetailFragment();
                                    fragmentTransaction.replace(R.id.frame_container, khedutSafalGathaDetailFragment, KhedutSafalGathaDetailFragment.class.getSimpleName());
                                    khedutSafalGathaDetailFragment.setArguments(bundle);
                                    //  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                    fragmentTransaction.addToBackStack(KhedutSafalGathaDetailFragment.class.getSimpleName());
                                    fragmentTransaction.commit();
                                }
                            });
                        }
                    } catch (Exception e) {

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

        return KhedutSafalGathaView;
    }

    private void setAdvClick(final KheduSafalGathaDetailModel kheduSafalGathaDetailModel) {
        if (kheduSafalGathaDetailModel != null) {
            if (kheduSafalGathaDetailModel.getContactNo().trim().length() > 0) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + kheduSafalGathaDetailModel.getContactNo().trim()));
                startActivity(intent);
            } else if (kheduSafalGathaDetailModel.getPopup().trim().length() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();

                View dialogLayout = inflaterView.inflate(R.layout.detail_adv_dialog_layout, null);
                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);



                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                        imageLoader.displayImage(kheduSafalGathaDetailModel.getPopup().trim(), image, optionsAdBanner);
                    }
                });
                dialog.show();
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        imageLoader.destroy();
        ButterKnife.unbind(this);
    }

    public class CustomBaseAdapter extends BaseAdapter {
        Context context;
        List<KheduSafalGathaDetailModel> rowItems;
        ImageLoader imageLoader;
        DisplayImageOptions displayImageOptions;
        LayoutInflater mInflater;

        public CustomBaseAdapter(Context context, List<KheduSafalGathaDetailModel> items) {
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

        public int getCount() {
            return rowItems.size();
        }

        @Override
        public Object getItem(int position) {
            return rowItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_khedut_safal_gatha_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            FontUtils.setFontForText(context, holder.rowKhedutSafalGathaTitle, "bold");
            try {
                if (!TextUtils.isEmpty(tempArrListKhedutGatha.get(position).getStoryTitle())) {
                    holder.rowKhedutSafalGathaTitle.setText(rowItems.get(position).getStoryTitle());
                }
                if (!TextUtils.isEmpty(tempArrListKhedutGatha.get(position).getThumbs())) {
                    imageLoader.displayImage(rowItems.get(position).getThumbs(), holder.rowKhedutSafalGathaThumb, options);
                    //    aq.id(R.id.row_khedut_safal_gatha_thumb).progress(R.id.progressbar_khedutgatha_listview).image(tempArrListKhedutGatha.get(position).getThumbs(), false, false);
                }
            } catch (Exception e) {

            }

            convertView.setSelected(true);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //    Toast.makeText(getActivity(), "KhedutSafalGatha  Fragment: " + tempArrListKhedutGatha.get(position).getStoryId(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("storyID", tempArrListKhedutGatha.get(position).getStoryId());
                    bundle.putInt("position", position);

                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    KhedutSafalGathaDetailFragment khedutSafalGathaDetailFragment = new KhedutSafalGathaDetailFragment();
                    fragmentTransaction.replace(R.id.frame_container, khedutSafalGathaDetailFragment, KhedutSafalGathaDetailFragment.class.getSimpleName());
                    khedutSafalGathaDetailFragment.setArguments(bundle);
                    //  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.addToBackStack(KhedutSafalGathaDetailFragment.class.getSimpleName());
                    fragmentTransaction.commit();
                }
            });

            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'row_khedut_safal_gatha_list.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        class ViewHolder {
            @Bind(R.id.row_khedut_safal_gatha_thumb)
            ImageView rowKhedutSafalGathaThumb;
            @Bind(R.id.row_khedut_safal_gatha_title)
            AgriScienceTextView rowKhedutSafalGathaTitle;
            @Bind(R.id.row_samachar_ll_thumb)
            LinearLayout rowSamacharLlThumb;

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
