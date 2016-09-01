package com.agriscienceapp.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.model.AgriScienceTVDetailModel;
import com.agriscienceapp.model.BajarBhavCropListDetailModel;
import com.agriscienceapp.webservice.AndroidNetworkUtility;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BajarBhavCropListFragment extends Fragment {
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.tv_bajar_bhav_crop_title)
    AgriScienceTextView tvBajarBhavCropTitle;
    @Bind(R.id.tv_time)
    AgriScienceTextView tvTime;
    @Bind(R.id.iv_bajar_bhav_share)
    ImageView ivBajarBhavShare;
    @Bind(R.id.ll_crop_first)
    LinearLayout llCropFirst;
    @Bind(R.id.tv_whatsapp_number)
    AgriScienceTextView tvWhatsappNumber;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.tv_pak_header)
    AgriScienceTextView tvPakHeader;
    @Bind(R.id.rl_crop_name)
    LinearLayout rlCropName;
    @Bind(R.id.tv_lower_header)
    AgriScienceTextView tvLowerHeader;
    @Bind(R.id.tv_Average_header)
    AgriScienceTextView tvAverageHeader;
    @Bind(R.id.tv_higher_header)
    AgriScienceTextView tvHigherHeader;
    @Bind(R.id.ll_bhav)
    LinearLayout llBhav;
    @Bind(R.id.rl_main_bajar_price)
    LinearLayout rlMainBajarPrice;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.list_fragment_bajar_crop)
    ListView listFragmentBajarCrop;
    @Bind(R.id.iv_ads_samachar)
    ImageView ivAdsSamachar;
    @Bind(R.id.ll_bajar_bhav_capture)
    RelativeLayout llBajarBhavCapture;

    private String BASEURL = "http://agriscienceindia.com/api/MasterDetailV2/";
    private String Methods = "CropList?";
    private String KEY_ZONEID = "ZoneId";
    private String KEY_YARDID = "YardId";

    private String APICALL_URL = "";
    private String TAG = "getActivity.this";
    private View BajarBhavCropListView;
    ArrayList<BajarBhavCropListDetailModel> getBajarBhavCropDetailListArrayList;
    private ProgressDialog progress;
    private int ZoneId = 0;
    private int YardId = 0;
    int getPosition = 0;
    int position;
    private ImageLoader imageLoader;
    private DisplayImageOptions optionsAdBanner;
    private ImageLoaderConfiguration config;
    private DisplayImageOptions options;
    private LayoutInflater inflater;

    public BajarBhavCropListFragment() {
        // Required empty public constructor
    }

    // Capture image on share button
    @OnClick(R.id.iv_bajar_bhav_share)
    public void onClickShare() {
        ivBajarBhavShare.setVisibility(View.GONE);
        ivAdsSamachar.setVisibility(View.GONE);
        Bitmap bitmap = captureView(llBajarBhavCapture);
        String pathofBmp = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "title", null);
        Uri bmpUri = Uri.parse(pathofBmp);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/jpeg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        ivBajarBhavShare.setVisibility(View.VISIBLE);
        ivAdsSamachar.setVisibility(View.VISIBLE);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        BajarBhavCropListView = inflater.inflate(R.layout.fragment_bajar_bhav_crop_list, container, false);
        ButterKnife.bind(this, BajarBhavCropListView);

        optionsAdBanner = new DisplayImageOptions.Builder()
                .build();
        this.inflater =inflater;
        imageLoader = ImageLoader.getInstance();
        initLoader(getActivity());
        getBajarBhavCropDetailListArrayList = new ArrayList<BajarBhavCropListDetailModel>();

        try {
            if (getArguments() != null) {
                ZoneId = getArguments().getInt("zoneID");
                YardId = getArguments().getInt("yardID");
                getPosition = getArguments().getInt("position");
                getPosition = getArguments().getInt("position");
                Log.e(TAG, "zone ID: " + ZoneId);
                Log.e(TAG, "yard ID: " + YardId);

                //      APICALL_URL = BASEURL + Methods + "KEY_ZONEID=" + ZoneId +"&KEY_YARDID="+YardId;  // Pass news id insteadof of 5.
                APICALL_URL = BASEURL + Methods + KEY_ZONEID + "=" + ZoneId + "&" + KEY_YARDID + "=" + YardId; // Pass news id insteadof of 5.
            }
        }catch (Exception e){

        }

        Log.e("Request: ", "" + APICALL_URL);

        if (AndroidNetworkUtility.isNetworkConnected(getActivity())) {
            GetCropListDetails getCropListDetails = new GetCropListDetails(getActivity(), APICALL_URL);
            getCropListDetails.execute();
        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        return BajarBhavCropListView;
    }

    public class GetCropListDetails extends AsyncTask<Void, Void, Void> {
        Activity callerActivity;
        private Context mContext;
        private static final String TAG = "GetBajarCropList Detail";
        private String APIURL_CALL;
        private ProgressDialog progress;
        BajarBhavCropListDetailModel bajarBhavCropListDetailModel;
        private String yardName;

        public GetCropListDetails(Context context, String APIURL_CALL) {
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

        @Override
        protected Void doInBackground(Void... params) {

            HttpPost httpGet = new HttpPost(APIURL_CALL);
            //setting header to request for a JSON response
            httpGet.setHeader("Accept", "application/json");
            AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
            String getCropDetailData = httpUtil.getHttpResponse(httpGet);
            Log.e(TAG, "Response: " + getCropDetailData);

            try {
                JSONObject mainJson = new JSONObject(getCropDetailData);
                String result = mainJson.getString("result");
                Log.e("Result", "" + result);
                Log.e(TAG, "Success");
                JSONArray detail = mainJson.getJSONArray("detail");

                if (detail != null && detail.length() > 0) {

                    for (int i = 0; i < detail.length(); i++) {

                        JSONObject detailJson = detail.getJSONObject(i);
                        bajarBhavCropListDetailModel = new BajarBhavCropListDetailModel();
                        bajarBhavCropListDetailModel.setZoneId(detailJson.getInt("ZoneId"));
                        bajarBhavCropListDetailModel.setCropId(detailJson.getInt("CropId"));
                        bajarBhavCropListDetailModel.setYardId(detailJson.getInt("YardId"));
                        bajarBhavCropListDetailModel.setCropName(detailJson.getString("CropName"));
                        bajarBhavCropListDetailModel.setLowestPrice(detailJson.getString("LowestPrice"));
                        bajarBhavCropListDetailModel.setAveragePrice(detailJson.getString("HighestPrice"));
                        bajarBhavCropListDetailModel.setHighestPrice(detailJson.getString("AveragePrice"));
                        bajarBhavCropListDetailModel.setTransactionDate(detailJson.getString("TransactionDate"));
                        bajarBhavCropListDetailModel.setDetailAdd(detailJson.getString("DetailAdd"));
                        bajarBhavCropListDetailModel.setWidth(detailJson.getString("Width"));
                        bajarBhavCropListDetailModel.setHeight(detailJson.getString("Height"));
                        getBajarBhavCropDetailListArrayList.add(bajarBhavCropListDetailModel);
                        if (i == 0) {
                            yardName = detailJson.getString("YardName");
                        }
                        Log.e(TAG, "Sucess");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progress != null) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }
            }
            Log.d(TAG, "onPostExecute: BajarCropList Size" + getBajarBhavCropDetailListArrayList.size());

            try {
                if (getBajarBhavCropDetailListArrayList != null && getBajarBhavCropDetailListArrayList.size() > 0) {
                    // tv_bajar_bhav_crop_title.setText(yardName + " માર્કેટ યાર્ડ" + "\n " + stringToDate(getBajarBhavCropDetailListArrayList.get(0).getTransactionDate().replace("T", ""), "yyyy-MM-ddhh:mm:ss"));
                    tvBajarBhavCropTitle.setText(yardName + " માર્કેટયાર્ડ");
                    tvTime.setText("તા." + stringToDate(getBajarBhavCropDetailListArrayList.get(0).getTransactionDate().replace("T", ""), "yyyy-MM-ddhh:mm:ss"));
                    CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity(), getBajarBhavCropDetailListArrayList);
                    listFragmentBajarCrop.setAdapter(customBaseAdapter);
                } else {
                    tvBajarBhavCropTitle.setText(" માર્કેટયાર્ડ");
                }

                if (!TextUtils.isEmpty(getBajarBhavCropDetailListArrayList.get(position).getDetailAdd())) {
                    imageLoader.displayImage(getBajarBhavCropDetailListArrayList.get(position).getDetailAdd(), ivAdsSamachar, optionsAdBanner);
//                            aq.id(R.id.iv_ads_samachar).progress(R.id.progressbar_samachar_header).image(getSamacharDetailListArrayList.get(0).getThumbs(), false, false);
                    int width = 50;
                    int height = 50;
                    try{
                        height = Integer.parseInt(getBajarBhavCropDetailListArrayList.get(0).getHeight());
                        width =Integer.parseInt(getBajarBhavCropDetailListArrayList.get(0).getWidth());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    ivAdsSamachar.getLayoutParams().height = height;
                    ivAdsSamachar.requestLayout();
                } else {
                    ivAdsSamachar.setVisibility(View.GONE);
                }
                ivAdsSamachar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAdvClick(getBajarBhavCropDetailListArrayList.get(0));
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }

            }

    }

    private void setAdvClick(final BajarBhavCropListDetailModel samacharModel) {
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

    public static String stringToDate(String strdate, String format) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            date = formatter.parse(strdate);
            return new SimpleDateFormat("dd MMM").format(date.getTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class CustomBaseAdapter extends BaseAdapter {
        Context context;
        List<BajarBhavCropListDetailModel> rowItems;
        LayoutInflater mInflater;

        public CustomBaseAdapter(Context context, ArrayList<BajarBhavCropListDetailModel> items) {
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            this.rowItems = items;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_bajarbhav_croplist, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                if (!TextUtils.isEmpty(getBajarBhavCropDetailListArrayList.get(position).getCropName())) {
                    holder.rowBajarbhavCropItem.setText(rowItems.get(position).getCropName());
                }
                if (!TextUtils.isEmpty(getBajarBhavCropDetailListArrayList.get(position).getLowestPrice())) {
                    holder.tvPriceLowDigit.setText(rowItems.get(position).getLowestPrice());
                }
                if (!TextUtils.isEmpty(getBajarBhavCropDetailListArrayList.get(position).getHighestPrice())) {
                    holder.tvPriceHighDigit.setText(rowItems.get(position).getHighestPrice());
                }
                if (!TextUtils.isEmpty(getBajarBhavCropDetailListArrayList.get(position).getAveragePrice())) {
                    holder.tvPriceAverageDigit.setText(rowItems.get(position).getAveragePrice());
                }
            }catch (Exception e){
                e.printStackTrace();
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
            return position;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'row_bajarbhav_croplist.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        class ViewHolder {
            @Bind(R.id.row_bajarbhav_crop_item)
            AgriScienceTextView rowBajarbhavCropItem;
            @Bind(R.id.rl_crop_name)
            LinearLayout rlCropName;
            @Bind(R.id.tv_price_low_digit)
            TextView tvPriceLowDigit;
            @Bind(R.id.tv_price_Average_digit)
            TextView tvPriceAverageDigit;
            @Bind(R.id.tv_price_high_digit)
            TextView tvPriceHighDigit;
            @Bind(R.id.rl_bhav)
            LinearLayout rlBhav;
            @Bind(R.id.rl_main_bajar_price)
            LinearLayout rlMainBajarPrice;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public static Bitmap captureView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
}

