package com.agriscienceapp.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.KrushiSalahAdvisoryDetailModel;
import com.agriscienceapp.webservice.AndroidNetworkUtility;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class KrushiSalahDetailFragment extends Fragment {

    @Bind(R.id.ll_Advisory_list)
    ListView llAdvisoryList;
    @Bind(R.id.adView_KrushiSalah_Detail)
    AdView adViewKrushiSalahDetail;
    @Bind(R.id.iv_crop_krushisalahDetail)
    ImageView ivCropKrushisalahDetail;
    @Bind(R.id.ll_header_crop)
    LinearLayout llHeaderCrop;
    @Bind(R.id.iv_ads_samachar)
    ImageView ivAdsSamachar;
    @Bind(R.id.tv_cropname_krushisalah)
    AgriScienceTextView tvCropnameKrushisalah;
    private String BASEURL = "http://agriscienceindia.com/api/MasterDetailV2/";
    private String Methods = "Advisory?";
    private String KEY_CROPID = "CropId";

    private String APICALL_URL = "";
    private String TAG = "getActivity.this";
    private View KrushiSalahDetailView;
    private ProgressDialog progress;
    ArrayList<KrushiSalahAdvisoryDetailModel> getKrushiSalahAdvisoryDetailArrayList;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private int CropTitleId = 0;
    int getPosition = 0;
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;
    private DisplayImageOptions optionsAdBanner;

    public KrushiSalahDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        KrushiSalahDetailView = inflater.inflate(R.layout.fragment_krushisalah_detail, container, false);
        ButterKnife.bind(this, KrushiSalahDetailView);

        imageLoader = ImageLoader.getInstance();
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.app_logo)
                .showImageForEmptyUri(R.drawable.app_logo)
                .showImageOnFail(R.drawable.app_logo).cacheInMemory(true)
                .cacheOnDisc(true).build();
        optionsAdBanner = new DisplayImageOptions.Builder()
                .build();

        // for advertise Banner
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adViewKrushiSalahDetail.loadAd(adRequest);

        getKrushiSalahAdvisoryDetailArrayList = new ArrayList<KrushiSalahAdvisoryDetailModel>();

        if (getArguments() != null) {
            CropTitleId = getArguments().getInt("cropTitleID");
            tvCropnameKrushisalah.setText(getArguments().getString("cropTitle"));
            imageLoader.displayImage(getArguments().getString("cropThumb"), ivCropKrushisalahDetail, displayImageOptions);
            getPosition = getArguments().getInt("position");
            Log.e(TAG, "CropTitle ID: " + CropTitleId);

            APICALL_URL = BASEURL + Methods + KEY_CROPID + "=" + CropTitleId; // Pass news id insteadof of 5.
        }
        Log.e("Request: ", "" + APICALL_URL);

        if (AndroidNetworkUtility.isNetworkConnected(getActivity())) {
            GetCropDetails getCropDetails = new GetCropDetails(getActivity(), APICALL_URL);
            getCropDetails.execute();
        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        return KrushiSalahDetailView;
    }

    public class GetCropDetails extends AsyncTask<Void, Void, Void> {
        Activity callerActivity;
        private Context mContext;
        private static final String TAG = "GetCropDetails";
        private String APIURL_CALL;
        private ProgressDialog progress;
        KrushiSalahAdvisoryDetailModel krushiSalahAdvisoryDetailModel;
        private String imgAddUrl;

        public GetCropDetails(Context context, String APIURL_CALL) {
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
            String getCropDetailData = httpUtil.getHttpResponse(httpGet);
            Log.e(TAG, "Response: " + getCropDetailData);

            try {
                JSONObject mainJson = new JSONObject(getCropDetailData);

                String result = mainJson.getString("result");
                Log.e("Result", "" + result);

                JSONArray detail = mainJson.getJSONArray("detail");

                if (detail != null && detail.length() > 0) {

                    for (int i = 0; i < detail.length(); i++) {

                        JSONObject detailJson = detail.getJSONObject(i);

                        krushiSalahAdvisoryDetailModel = new KrushiSalahAdvisoryDetailModel();
                        krushiSalahAdvisoryDetailModel.setCropTitleId(detailJson.getInt("CropTitleId"));
                        krushiSalahAdvisoryDetailModel.setAdviseId(detailJson.getInt("AdviseId"));
                        krushiSalahAdvisoryDetailModel.setAdviseTitle(detailJson.getString("AdviseTitle"));
                        krushiSalahAdvisoryDetailModel.setDescription(detailJson.getString("Description"));
                        krushiSalahAdvisoryDetailModel.setThumbs(detailJson.getString("Thumbs"));
                        //    krushiSalahDetailModel.setCropTitleName(detailJson.getString("CropTitleName"));
                        getKrushiSalahAdvisoryDetailArrayList.add(krushiSalahAdvisoryDetailModel);
                        //    headerKrushisalahArrayList.add(krushiSalahDetailModel);
                        if (i == 0) {
                            imgAddUrl = detailJson.getString("MainAdd");
                        }
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

            Log.d(TAG, "onPostExecute: KrushiSalahList Size" + getKrushiSalahAdvisoryDetailArrayList.size());
            try {
                if (!TextUtils.isEmpty(imgAddUrl)) {
                    imageLoader.displayImage(imgAddUrl, ivAdsSamachar, optionsAdBanner);
                } else {
                    ivAdsSamachar.setVisibility(View.GONE);
                }

                if (getKrushiSalahAdvisoryDetailArrayList != null && getKrushiSalahAdvisoryDetailArrayList.size() > 0) {
                    CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity(), getKrushiSalahAdvisoryDetailArrayList);
                    llAdvisoryList.setAdapter(customBaseAdapter);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @OnItemClick({R.id.ll_Advisory_list})
    void ListViewItemClick(int position) {
        //  Toast.makeText(getActivity(), "KrushiSalah Detail  Fragment: " + getKrushiSalahAdvisoryDetailArrayList.get(position).getAdviseId(), Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putInt("adviseID", getKrushiSalahAdvisoryDetailArrayList.get(position).getAdviseId());
            bundle.putInt("position", position);

            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            AdvisoryDetailFragment advisoryDetailFragment = new AdvisoryDetailFragment();
            fragmentTransaction.replace(R.id.frame_container, advisoryDetailFragment, AdvisoryDetailFragment.class.getSimpleName());
            advisoryDetailFragment.setArguments(bundle);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(AdvisoryDetailFragment.class.getSimpleName());
            fragmentTransaction.commit();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class CustomBaseAdapter extends BaseAdapter {
        Context context;
        List<KrushiSalahAdvisoryDetailModel> rowItems;
        LayoutInflater mInflater;

        public CustomBaseAdapter(Context context, List<KrushiSalahAdvisoryDetailModel> items) {
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            this.rowItems = items;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.krushisalah_advisory_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            FontUtils.setFontForText(context, holder.krushisalahAdvisoryTvTitle, "bold");
            try {
                if (!TextUtils.isEmpty(getKrushiSalahAdvisoryDetailArrayList.get(position).getAdviseTitle())) {
                    holder.krushisalahAdvisoryTvTitle.setText(rowItems.get(position).getAdviseTitle());
                }
                //      imageLoader.displayImage(rowItems.get(position).getThumbs(), holder.krushisalahAdvisoryThumb, options);
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
            return position;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'krushisalah_advisory_list.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */

        class ViewHolder {
            @Bind(R.id.krushisalah_advisory_tv_title)
            AgriScienceTextView krushisalahAdvisoryTvTitle;
            @Bind(R.id.krushisalah_advisory_ll_thumb)
            LinearLayout krushisalahAdvisoryLlThumb;
            @Bind(R.id.ll_krushisalah_advisory)
            LinearLayout llKrushisalahAdvisory;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
