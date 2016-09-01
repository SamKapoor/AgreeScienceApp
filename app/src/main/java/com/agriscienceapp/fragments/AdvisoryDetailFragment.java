package com.agriscienceapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.Detail;
import com.agriscienceapp.model.KrushiSalahAdvisoryDetailSecondModel;
import com.agriscienceapp.webservice.AndroidNetworkUtility;
import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvisoryDetailFragment extends Fragment {
    @Bind(R.id.tv_advisory_detail_title)
    AgriScienceTextView tvAdvisoryDetailTitle;
    @Bind(R.id.iv_advisory_detail_first)
    ImageView ivAdvisoryDetailFirst;
    @Bind(R.id.progressbar_advisory_detail_first)
    ProgressBar progressbarAdvisoryDetailFirst;
    @Bind(R.id.ll_advisory_image_first)
    RelativeLayout llAdvisoryImageFirst;
    @Bind(R.id.ll_advisory_image_second)
    RelativeLayout ll_advisory_image_second;
    @Bind(R.id.progressbar_advisory_detail_second)
    ProgressBar progressbarAdvisoryDetailSecond;
   /* @Bind(R.id.iv_advisory_detail_second)
    ImageView ivAdvisoryDetailSecond;*/
    @Bind(R.id.rl_advisory_detail_main)
    RelativeLayout rlAdvisoryDetailMain;
    @Bind(R.id.tv_advisory_decsription_detail)
    AgriScienceTextView tvAdvisoryDecsriptionDetail;
    @Bind(R.id.iv_ads_advisory_detail)
    ImageView ivAdsAdvisoryDetail;

    private String BASEURL = "http://agriscienceindia.com/api/MasterDetailV2/";
    private String Methods = "AdvisoryDetail?";
    private String KEY_ADVISEID = "AdviseId";

    private String APICALL_URL = "";
    private String TAG = "getActivity.this";
    private View AdvisoryDetailView;
    private ProgressDialog progress;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    public AQuery aq;

    private int AdviseId = 0;
    int getPosition = 0;
    private LayoutInflater inflater;

    public AdvisoryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AdvisoryDetailView = inflater.inflate(R.layout.fragment_advisory_detail, container, false);
        ButterKnife.bind(this, AdvisoryDetailView);

        this.inflater = inflater;
        aq = new AQuery(getActivity());

        imageLoader = ImageLoader.getInstance();
        initLoader(getActivity());

        try {
            if (getArguments() != null) {
                AdviseId = getArguments().getInt("adviseID");
                getPosition = getArguments().getInt("position");
                Log.e(TAG, "Advise ID: " + AdviseId);

                APICALL_URL = BASEURL + Methods + KEY_ADVISEID + "=" + AdviseId; // Pass news id insteadof of 5.
            }
        }catch (Exception e){

        }
        Log.e("Request: ", "" + APICALL_URL);

        if (AndroidNetworkUtility.isNetworkConnected(getActivity())) {
            GetAdvisoryDetails getAdvisoryDetails = new GetAdvisoryDetails(getActivity(), APICALL_URL);
            getAdvisoryDetails.execute();
        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
        return AdvisoryDetailView;
    }

    public class GetAdvisoryDetails extends AsyncTask<Void, Void, Void> {
        Activity callerActivity;
        private Context mContext;
        private static final String TAG = "GetAdviseDetail";
        private String APIURL_CALL;
        private ProgressDialog progress;
        KrushiSalahAdvisoryDetailSecondModel krushiSalahAdvisoryDetailSecondModel;

        public GetAdvisoryDetails(Context context, String APIURL_CALL) {
            this.mContext = context;
            this.APIURL_CALL = APIURL_CALL;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progress == null) {
                progress = new ProgressDialog(mContext);
                progress.setMessage("Jai Kisaan...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
            } else {
                progress.show();
            }
        }

        protected Void doInBackground(Void... params) {

            HttpPost httpGet = new HttpPost(APIURL_CALL);
            //setting header to request for a JSON response
            httpGet.setHeader("Accept", "application/json");
            AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
            String getAdviseDetailData = httpUtil.getHttpResponse(httpGet);
            Log.e(TAG, "Response: " + getAdviseDetailData);

            try {
                JSONObject mainJson = new JSONObject(getAdviseDetailData);
                String result = mainJson.getString("result");
                Log.e("Result", "" + result);
                JSONArray detail = mainJson.getJSONArray("detail");
                if (detail != null && detail.length() > 0) {
                    for (int i = 0; i < detail.length(); i++) {
                        JSONObject detailJson = detail.getJSONObject(i);
                        krushiSalahAdvisoryDetailSecondModel = new KrushiSalahAdvisoryDetailSecondModel();
                        krushiSalahAdvisoryDetailSecondModel.setAdviseId(detailJson.getInt("AdviseId"));
                        krushiSalahAdvisoryDetailSecondModel.setAdviseTitle(detailJson.getString("AdviseTitle"));
                        krushiSalahAdvisoryDetailSecondModel.setDescription(detailJson.getString("Description"));
                        krushiSalahAdvisoryDetailSecondModel.setPhoto(detailJson.getString("Photo"));
                        krushiSalahAdvisoryDetailSecondModel.setDetailAdd(detailJson.getString("DetailAdd"));
                        krushiSalahAdvisoryDetailSecondModel.setContactNo(detailJson.getString("ContactNo"));
                        krushiSalahAdvisoryDetailSecondModel.setPopup(detailJson.getString("Popup"));
                        krushiSalahAdvisoryDetailSecondModel.setWidth(detailJson.getString("Width"));
                        krushiSalahAdvisoryDetailSecondModel.setHeight(detailJson.getString("Height"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progress != null) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }
            }

            FontUtils.setFontForText(mContext, tvAdvisoryDetailTitle, "bold");

            try {
                if (!TextUtils.isEmpty(krushiSalahAdvisoryDetailSecondModel.getAdviseTitle())) {
                    tvAdvisoryDetailTitle.setText(krushiSalahAdvisoryDetailSecondModel.getAdviseTitle());
                }
                if (!TextUtils.isEmpty(krushiSalahAdvisoryDetailSecondModel.getDescription())) {
                    tvAdvisoryDecsriptionDetail.setText(krushiSalahAdvisoryDetailSecondModel.getDescription());
                }
                if (!TextUtils.isEmpty(krushiSalahAdvisoryDetailSecondModel.getPhoto()) && !krushiSalahAdvisoryDetailSecondModel.getPhoto().equalsIgnoreCase("null")) {
                    imageLoader.displayImage(krushiSalahAdvisoryDetailSecondModel.getPhoto(), ivAdvisoryDetailFirst);
                    aq.id(R.id.iv_advisory_detail_first).progress(R.id.progressbar_advisory_detail_first).image(krushiSalahAdvisoryDetailSecondModel.getPhoto(), false, true);
                    llAdvisoryImageFirst.setVisibility(View.VISIBLE);
                } else {
                    llAdvisoryImageFirst.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(krushiSalahAdvisoryDetailSecondModel.getDetailAdd()) && !krushiSalahAdvisoryDetailSecondModel.getDetailAdd().equalsIgnoreCase("null")) {
                    imageLoader.displayImage(krushiSalahAdvisoryDetailSecondModel.getDetailAdd(), ivAdsAdvisoryDetail);
                    int width = 50;
                    int height = 50;
                    try{
                        height = Integer.parseInt(krushiSalahAdvisoryDetailSecondModel.getHeight());
                        width =Integer.parseInt(krushiSalahAdvisoryDetailSecondModel.getWidth());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    ivAdsAdvisoryDetail.getLayoutParams().height = height;
                    ivAdsAdvisoryDetail.requestLayout();
//                aq.id(R.id.iv_ads_advisory_detail).progress(R.id.progressbar_advisory_detail_second).image(krushiSalahAdvisoryDetailSecondModel.getPhoto(), false, true);
                    ll_advisory_image_second.setVisibility(View.VISIBLE);
                } else {
                    ll_advisory_image_second.setVisibility(View.GONE);
                }
                ivAdsAdvisoryDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAdvClick(krushiSalahAdvisoryDetailSecondModel);
                    }
                });
            }catch (Exception e){

            }
        }
    }

    private void setAdvClick(final KrushiSalahAdvisoryDetailSecondModel samacharModel) {
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

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

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

}
