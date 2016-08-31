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
import com.agriscienceapp.model.KheduSafalGathaDetailModel;
import com.agriscienceapp.model.SamacharDetailDescModel;
import com.agriscienceapp.webservice.AndroidNetworkUtility;
import com.androidquery.AQuery;
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
public class KhedutSafalGathaDetailFragment extends Fragment {

    @Bind(R.id.tv_khedutsafalgatha_description_title)
    AgriScienceTextView tvKhedutsafalgathaDescriptionTitle;
    @Bind(R.id.iv_khedutsafalgatha_description)
    ImageView ivKhedutsafalgathaDescription;
    @Bind(R.id.progressbar_khedutgatha_detail_first)
    ProgressBar progressbarKhedutgathaDetailFirst;
    @Bind(R.id.rl_khedutsafalgatha_description)
    RelativeLayout rlKhedutsafalgathaDescription;
    @Bind(R.id.tv_khedutsafalgatha_decsription_detail)
    AgriScienceTextView tvKhedutsafalgathaDecsriptionDetail;
    @Bind(R.id.tv_khedutsafalgatha_decsription_detail2)
    AgriScienceTextView tvKhedutsafalgathaDecsriptionDetail2;
    @Bind(R.id.iv_khedutsafalgatha_description_second)
    ImageView ivKhedutsafalgathaDescriptionSecond;
    @Bind(R.id.iv_khedutsafalgatha_description_secondadv)
    ImageView ivKhedutsafalgathaDescriptionSecondadv;
    @Bind(R.id.progressbar_khedutgatha_detail_second)
    ProgressBar progressbarKhedutgathaDetailSecond;
    @Bind(R.id.rl_khedutgatha_detail_main)
    RelativeLayout rlKhedutgathaDetailMain;
    @Bind(R.id.iv_share_khedutgatha_detail)
    ImageView ivShareKhedutGathaDetail;

    private String BASEURL = "http://agriscienceindia.com/api/MasterDetail/";
    private String Methods = "StoriesDetail?";
    private String KEY_STORYID = "StoryId";

    private String APICALL_URL = "";
    private String TAG = "getActivity.this";
    private View KhedutSafalGathaView;
    private ProgressDialog progress;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    private int StoryId = 0;
    int getPosition = 0;

    public AQuery aq;
    private LayoutInflater inflater;

    public KhedutSafalGathaDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        KhedutSafalGathaView = inflater.inflate(R.layout.fragment_khedut_safal_gatha_detail, container, false);
        ButterKnife.bind(this, KhedutSafalGathaView);

        aq = new AQuery(getActivity());

        imageLoader = ImageLoader.getInstance();
        initLoader(getActivity());

        //  progress = ProgressDialog.show(getActivity(), "Fetching Data", "Jai Kisaan", false, false);

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Jai Kisaan..");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        try {
            if (getArguments() != null) {
                StoryId = getArguments().getInt("storyID");
                getPosition = getArguments().getInt("position");
                Log.e(TAG, "Story ID: " + StoryId);

                APICALL_URL = BASEURL + Methods + KEY_STORYID + "=" + StoryId; // Pass news id insteadof of 5.
            }
        }catch (Exception e){

        }
        Log.e("Request: ", "" + APICALL_URL);

        if (AndroidNetworkUtility.isNetworkConnected(getActivity())) {
            GetKhedulGathaDetails getKhedulGathaDetails = new GetKhedulGathaDetails(getActivity(), APICALL_URL);
            getKhedulGathaDetails.execute();
        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        ivShareKhedutGathaDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");

                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.

                FontUtils.setFontForText(getActivity(), tvKhedutsafalgathaDescriptionTitle, "bold");
                String extraText = tvKhedutsafalgathaDescriptionTitle.getText().toString()+ "...ખેતીની વધુ માહિતી અને બજારભાવ માટે ડાઉનલોડ કરો ઍગ્રીસાયન્સ કૃષિ માહિતી અપ્લિકેશન.  " + "https://play.google.com/store/apps/details?id=com.agriscienceapp";
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

        progress.dismiss();
        return KhedutSafalGathaView;

    }

    public class GetKhedulGathaDetails extends AsyncTask<Void, Void, Void> {
        Activity callerActivity;
        private Context mContext;
        private static final String TAG = "GetKhedutGathaDetails";
        private String APIURL_CALL;
        private ProgressDialog progress;
        KheduSafalGathaDetailModel kheduSafalGathaDetailModel;

        public GetKhedulGathaDetails(Context context, String APIURL_CALL) {
            this.mContext = context;
            this.APIURL_CALL = APIURL_CALL;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progress == null) {
                progress = new ProgressDialog(mContext);
                progress.setMessage("Jai Kisaan..");
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
            String getKhedutGathaDetailData = httpUtil.getHttpResponse(httpGet);
            Log.e(TAG, "Response: " + getKhedutGathaDetailData);

            try {
                JSONObject mainJson = new JSONObject(getKhedutGathaDetailData);
                String result = mainJson.getString("result");
                Log.e("Result", "" + result);
                JSONArray detail = mainJson.getJSONArray("detail");
                if (detail != null && detail.length() > 0) {
                    for (int i = 0; i < detail.length(); i++) {
                        JSONObject detailJson = detail.getJSONObject(i);
                        kheduSafalGathaDetailModel = new KheduSafalGathaDetailModel();
                        kheduSafalGathaDetailModel.setStoryId(detailJson.getInt("StoryId"));
                        kheduSafalGathaDetailModel.setStoryTitle(detailJson.getString("StoryTitle"));
                        kheduSafalGathaDetailModel.setDescription(detailJson.getString("Description"));
                        kheduSafalGathaDetailModel.setDescription2(detailJson.getString("Description2"));
                        kheduSafalGathaDetailModel.setPhotoPath(detailJson.getString("PhotoPath"));
                        kheduSafalGathaDetailModel.setDetailAdd(detailJson.getString("DetailAdd"));
                        kheduSafalGathaDetailModel.setDetailMiddleAdd(detailJson.getString("DetailMiddleAdd"));
                        kheduSafalGathaDetailModel.setContactNo(detailJson.getString("ContactNo"));
                        kheduSafalGathaDetailModel.setContactNo2(detailJson.getString("ContactNo2"));
                        kheduSafalGathaDetailModel.setPopup(detailJson.getString("Popup"));
                        kheduSafalGathaDetailModel.setPopup2(detailJson.getString("Popup2"));
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

            try {
                if (!TextUtils.isEmpty(kheduSafalGathaDetailModel.getStoryTitle())) {
                    FontUtils.setFontForText(mContext, tvKhedutsafalgathaDescriptionTitle, "bold");
                    tvKhedutsafalgathaDescriptionTitle.setText(kheduSafalGathaDetailModel.getStoryTitle());
                }
                if (!TextUtils.isEmpty(kheduSafalGathaDetailModel.getDescription())) {
                    FontUtils.setFontForText(mContext, tvKhedutsafalgathaDecsriptionDetail, "regular");
                    tvKhedutsafalgathaDecsriptionDetail.setText(kheduSafalGathaDetailModel.getDescription());
                }
                if (!TextUtils.isEmpty(kheduSafalGathaDetailModel.getDescription2())) {
                    FontUtils.setFontForText(mContext, tvKhedutsafalgathaDecsriptionDetail2, "regular");
                    tvKhedutsafalgathaDecsriptionDetail2.setText(kheduSafalGathaDetailModel.getDescription2());
                }
                if (!TextUtils.isEmpty(kheduSafalGathaDetailModel.getPhotoPath()) && !kheduSafalGathaDetailModel.getPhotoPath().equalsIgnoreCase("null")) {
                    imageLoader.displayImage(kheduSafalGathaDetailModel.getPhotoPath(), ivKhedutsafalgathaDescription);
                    aq.id(R.id.iv_khedutsafalgatha_description).progress(R.id.progressbar_khedutgatha_detail_first).image(kheduSafalGathaDetailModel.getPhotoPath(), false, false);
                    rlKhedutsafalgathaDescription.setVisibility(View.VISIBLE);
//                imageLoader.displayImage(kheduSafalGathaDetailModel.getPhotoPath(), ivKhedutsafalgathaDescriptionSecond);
//                aq.id(R.id.iv_khedutsafalgatha_description_second).progress(R.id.progressbar_khedutgatha_detail_second).image(kheduSafalGathaDetailModel.getPhotoPath(), false, false);
                } else {
                    rlKhedutsafalgathaDescription.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(kheduSafalGathaDetailModel.getDetailAdd())) {
                    imageLoader.displayImage(kheduSafalGathaDetailModel.getDetailAdd(), ivKhedutsafalgathaDescriptionSecond);
                } else {
                    ivKhedutsafalgathaDescriptionSecond.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(kheduSafalGathaDetailModel.getDetailMiddleAdd())) {
                    imageLoader.displayImage(kheduSafalGathaDetailModel.getDetailMiddleAdd(), ivKhedutsafalgathaDescriptionSecondadv);
                } else {
                    ivKhedutsafalgathaDescriptionSecondadv.setVisibility(View.GONE);
                }

                ivKhedutsafalgathaDescriptionSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAdvClick(kheduSafalGathaDetailModel);
                    }
                });

                ivKhedutsafalgathaDescriptionSecondadv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setMiddleAdvClick(kheduSafalGathaDetailModel);
                    }
                });
            }catch (Exception e){

            }
        }
    }

    private void setAdvClick(final KheduSafalGathaDetailModel samacharModel) {
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

                dialog.show();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                        imageLoader.displayImage(samacharModel.getPopup().trim(), image, options);
                    }
                });
            }
        }
    }

    private void setMiddleAdvClick(final KheduSafalGathaDetailModel samacharModel) {
        if (samacharModel != null) {
            if (samacharModel.getContactNo2().trim().length() > 0) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + samacharModel.getContactNo2().trim()));
                startActivity(intent);
            } else if (samacharModel.getPopup2().trim().length() > 0) {
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

                dialog.show();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                        imageLoader.displayImage(samacharModel.getPopup2().trim(), image, options);
                    }
                });
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        imageLoader.destroy();
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