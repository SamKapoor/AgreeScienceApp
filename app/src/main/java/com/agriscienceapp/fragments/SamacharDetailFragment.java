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
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.activity.HomeActivity;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.Detail;
import com.agriscienceapp.model.SamacharDetailDescModel;
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
public class SamacharDetailFragment extends Fragment {
    @Bind(R.id.tv_samachar_detail_title)
    AgriScienceTextView tvSamacharDetailTitle;
    @Bind(R.id.timeline_samachar_detail)
    TextView timelineSamacharDetail;
    @Bind(R.id.iv_share_samachar_detail)
    ImageView ivShareSamacharDetail;
    @Bind(R.id.iv_Samachar_detail_first)
    ImageView ivSamacharDetailFirst;
    @Bind(R.id.progressbar_samachar_detail_first)
    ProgressBar progressbarSamacharDetailFirst;
    @Bind(R.id.rl_samachar_image_first)
    RelativeLayout rlSamacharImageFirst;
    @Bind(R.id.tv_samachar_detail_discription)
    AgriScienceTextView tvSamacharDetailDiscription;
    @Bind(R.id.tv_samachar_detail_discription2)
    AgriScienceTextView tvSamacharDetailDiscription2;
    @Bind(R.id.progressbar_samachar_detail_second)
    ProgressBar progressbarSamacharDetailSecond;
    @Bind(R.id.iv_Samachar_detail_second)
    ImageView ivSamacharDetailSecond;
    @Bind(R.id.iv_Samachar_detail_second_adv)
    ImageView ivSamacharDetailSecondAdv;
    @Bind(R.id.rl_samachar_detail_main)
    RelativeLayout rlSamacharDetailMain;

    private String BASEURL = "http://agriscienceindia.com/api/MasterDetailV2/";
    private String Methods = "NewslistDetail?";
    private String KEY_NEWSID = "NewsId";

    private String APICALL_URL = "";
    private String TAG = "getActivity.this";
    private View SamacharView;
    private ProgressDialog progress;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    private int NewsId = 0;
    int getPosition = 0;

    public AQuery aq;
    private LayoutInflater inflater;

    public SamacharDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;
        //Layout is used Relative
        SamacharView = inflater.inflate(R.layout.freagment_samachar_discription, container, false);
        ButterKnife.bind(this, SamacharView);

        aq = new AQuery(getActivity());

        imageLoader = ImageLoader.getInstance();
        initLoader(getActivity());

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Jai Kisaan..");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        try {
            if (getArguments() != null) {
                NewsId = getArguments().getInt("newsID");
                getPosition = getArguments().getInt("position");
                Log.e(TAG, "News ID: " + NewsId);

                APICALL_URL = BASEURL + Methods + KEY_NEWSID + "=" + NewsId; // Pass news id insteadof of 5.
            }
        } catch (Exception e) {

        }
        Log.e("Request: ", "" + APICALL_URL);

        if (AndroidNetworkUtility.isNetworkConnected(getActivity())) {
            GetNewsDetails getNewsDetails = new GetNewsDetails(getActivity(), APICALL_URL);
            getNewsDetails.execute();
        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            progress.dismiss();
//            Class<HomeActivity> homeActivityClass = HomeActivity.class;
//            homeActivityClass.getClass();
        }

        ivShareSamacharDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");

                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.

                FontUtils.setFontForText(getActivity(), tvSamacharDetailTitle, "bold");
                String extraText = tvSamacharDetailTitle.getText().toString() + "...ખેતીની વધુ માહિતી અને બજારભાવ માટે ડાઉનલોડ કરો ઍગ્રીસાયન્સ કૃષિ માહિતી અપ્લિકેશન.  " + "https://play.google.com/store/apps/details?id=com.agriscienceapp";
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

        progress.dismiss();
        return SamacharView;
    }

    public class GetNewsDetails extends AsyncTask<Void, Void, Void> {
        Activity callerActivity;
        private Context mContext;
        private static final String TAG = "GetNewsDetails";
        private String APIURL_CALL;
        private ProgressDialog progress;
        SamacharDetailDescModel samacharDetailDescModel;

        public GetNewsDetails(Context context, String APIURL_CALL) {
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
            String getNewsDetailData = httpUtil.getHttpResponse(httpGet);
            Log.e(TAG, "Response: " + getNewsDetailData);

            try {
                JSONObject mainJson = new JSONObject(getNewsDetailData);
                String result = mainJson.getString("result");
                Log.e("Result", "" + result);
                JSONArray detail = mainJson.getJSONArray("detail");
                if (detail != null && detail.length() > 0) {
                    for (int i = 0; i < detail.length(); i++) {
                        JSONObject detailJson = detail.getJSONObject(i);
                        samacharDetailDescModel = new SamacharDetailDescModel();
                        samacharDetailDescModel.setNewsId(detailJson.getInt("NewsId"));
                        samacharDetailDescModel.setNewsTitle(detailJson.getString("NewsTitle"));
                        samacharDetailDescModel.setNewsDetail(detailJson.getString("NewsDetail"));
                        samacharDetailDescModel.setNewsDetail2(detailJson.getString("NewsDetail2"));
                        samacharDetailDescModel.setNewsTimeline(detailJson.getString("NewsTimeline"));
                        samacharDetailDescModel.setDetailAdd(detailJson.getString("DetailAdd"));
                        samacharDetailDescModel.setDetailMiddleAdd(detailJson.getString("DetailMiddleAdd"));
                        samacharDetailDescModel.setPhoto(detailJson.getString("Photo"));
                        samacharDetailDescModel.setContactNo(detailJson.getString("ContactNo"));
                        samacharDetailDescModel.setContactNo2(detailJson.getString("ContactNo2"));
                        samacharDetailDescModel.setPopup(detailJson.getString("Popup"));
                        samacharDetailDescModel.setPopup2(detailJson.getString("Popup2"));
                        samacharDetailDescModel.setWidth(detailJson.getString("Width"));
                        samacharDetailDescModel.setHeight(detailJson.getString("Height"));
                        samacharDetailDescModel.setWidth2(detailJson.getString("Width2"));
                        samacharDetailDescModel.setHeight2(detailJson.getString("Height2"));
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
                if (!TextUtils.isEmpty(samacharDetailDescModel.getNewsTitle())) {
                    FontUtils.setFontForText(mContext, tvSamacharDetailTitle, "bold");
                    tvSamacharDetailTitle.setText(samacharDetailDescModel.getNewsTitle());
                }
                if (!TextUtils.isEmpty(samacharDetailDescModel.getNewsDetail())) {
                    FontUtils.setFontForText(mContext, tvSamacharDetailDiscription, "regular");
                    tvSamacharDetailDiscription.setText(samacharDetailDescModel.getNewsDetail());
                }
                if (!TextUtils.isEmpty(samacharDetailDescModel.getNewsDetail2())) {
                    FontUtils.setFontForText(mContext, tvSamacharDetailDiscription2, "regular");
                    tvSamacharDetailDiscription2.setText(samacharDetailDescModel.getNewsDetail2());
                }
                if (!TextUtils.isEmpty(samacharDetailDescModel.getNewsTimeline())) {
                    timelineSamacharDetail.setText(samacharDetailDescModel.getNewsTimeline());
                }
                if (!TextUtils.isEmpty(samacharDetailDescModel.getDetailAdd())) {
//                    imageLoader.displayImage(samacharDetailDescModel.getDetailAdd(), ivSamacharDetailSecond);
                    int width = 50;
                    int height = 50;
                    try{
                        width =Integer.parseInt(samacharDetailDescModel.getWidth());
                        height = Integer.parseInt(samacharDetailDescModel.getHeight());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Glide.with(getActivity()).load(samacharDetailDescModel.getDetailAdd().trim()).into(ivSamacharDetailSecond);
                    ivSamacharDetailSecond.getLayoutParams().height = height;
                    ivSamacharDetailSecond.requestLayout();
                } else {
                    ivSamacharDetailSecond.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(samacharDetailDescModel.getDetailMiddleAdd())) {
//                    imageLoader.displayImage(samacharDetailDescModel.getDetailMiddleAdd(), ivSamacharDetailSecondAdv);
                    int width = 50;
                    int height = 50;
                    try{
                        height = Integer.parseInt(samacharDetailDescModel.getHeight2());
                        width =Integer.parseInt(samacharDetailDescModel.getWidth2());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Glide.with(getActivity()).load(samacharDetailDescModel.getDetailMiddleAdd().trim()).into(ivSamacharDetailSecondAdv);
                    ivSamacharDetailSecondAdv.getLayoutParams().height = height;
                    ivSamacharDetailSecondAdv.requestLayout();

                } else {
                    ivSamacharDetailSecondAdv.setVisibility(View.GONE);
                }
                ivSamacharDetailSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAdvClick(samacharDetailDescModel);
                    }
                });

                ivSamacharDetailSecondAdv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setMiddleAdvClick(samacharDetailDescModel);
                    }
                });
                if (!TextUtils.isEmpty(samacharDetailDescModel.getPhoto())) {
                    imageLoader.displayImage(samacharDetailDescModel.getPhoto(), ivSamacharDetailFirst);
                    aq.id(R.id.iv_Samachar_detail_first).progress(R.id.progressbar_samachar_detail_first).image(samacharDetailDescModel.getPhoto(), false, true);
                }
            } catch (Exception e) {

            }
        }
    }

    private void setAdvClick(final SamacharDetailDescModel samacharModel) {
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
                        imageLoader.displayImage(samacharModel.getPopup().trim(), image, options);
                    }
                });
                dialog.show();
            }
        }
    }

     private void setMiddleAdvClick(final SamacharDetailDescModel samacharModel) {
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


                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                        imageLoader.displayImage(samacharModel.getPopup2().trim(), image, options);
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


