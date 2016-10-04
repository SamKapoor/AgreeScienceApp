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
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.BajarBhavYardListDetailModel;
import com.agriscienceapp.webservice.AndroidNetworkUtility;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BajarBhavYardListFragment extends Fragment {

    @Bind(R.id.listview_bajar_yard)
    GridView listviewBajarYard;
    @Bind(R.id.tv_bajar_yard_title)
    TextView tv_bajar_yard_title;

    private String BASEURL = "http://agriscienceindia.com/api/MasterDetailV2/";
    private String Methods = "YardList?";
    private String KEY_ZONEID = "ZoneId";

    private String APICALL_URL = "";
    private String TAG = "getActivity.this";
    private View BajarYardDetailView;
    private ProgressDialog progress;
    ArrayList<BajarBhavYardListDetailModel> getBajarBhavYardDetailListArrayList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private int ZoneId = 0;
    int getPosition = 0;

    public BajarBhavYardListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BajarYardDetailView = inflater.inflate(R.layout.fragment_bajar_bhav_yard_list, container, false);
        ButterKnife.bind(this, BajarYardDetailView);

        getBajarBhavYardDetailListArrayList = new ArrayList<BajarBhavYardListDetailModel>();

        if (getArguments() != null) {
            ZoneId = getArguments().getInt("zoneID");
            getPosition = getArguments().getInt("position");
            Log.e(TAG, "Zone ID: " + ZoneId);

            APICALL_URL = BASEURL + Methods + KEY_ZONEID + "=" + ZoneId; // Pass news id insteadof of 5.
        }

        switch (getPosition) {
            case 1:
                tv_bajar_yard_title.setText("ઉત્તર ગુજરાત માર્કેટ યાર્ડ");
                break;
            case 2:
                tv_bajar_yard_title.setText("દક્ષિણ ગુજરાત માર્કેટ યાર્ડ");
                break;
            case 3:
                tv_bajar_yard_title.setText("મધ્ય઼ ગુજરાત માર્કેટ યાર્ડ");
                break;
            case 4:
                tv_bajar_yard_title.setText("સૌરાષ્ટ્ર માર્કેટ યાર્ડ");
                break;
            case 5:
                tv_bajar_yard_title.setText("કચ્છ માર્કેટ યાર્ડ");
                break;
            default:
                tv_bajar_yard_title.setText("અન્ય માર્કેટ યાર્ડ");
                break;
        }

        Log.e("Request: ", "" + APICALL_URL);

        if (AndroidNetworkUtility.isNetworkConnected(getActivity())) {
            GetYardListDetails getYardListDetails = new GetYardListDetails(getActivity(), APICALL_URL);
            getYardListDetails.execute();
        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            if (progress != null && progress.isShowing())
                progress.dismiss();
        }

        return BajarYardDetailView;
    }

    public class GetYardListDetails extends AsyncTask<Void, Void, Void> {
        Activity callerActivity;
        private Context mContext;
        private static final String TAG = "GetBajarYardList Detail";
        private String APIURL_CALL;
        private ProgressDialog progress;
        BajarBhavYardListDetailModel bajarBhavYardListDetailModel;

        public GetYardListDetails(Context context, String APIURL_CALL) {
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
            String getYardDetailData = httpUtil.getHttpResponse(httpGet);
            Log.e(TAG, "Response: " + getYardDetailData);

            try {
                JSONObject mainJson = new JSONObject(getYardDetailData);
                String result = mainJson.getString("result");
                Log.e("Result", "" + result);
                JSONArray detail = mainJson.getJSONArray("detail");
                if (detail != null && detail.length() > 0) {
                    for (int i = 0; i < detail.length(); i++) {
                        JSONObject detailJson = detail.getJSONObject(i);
                        bajarBhavYardListDetailModel = new BajarBhavYardListDetailModel();
                        bajarBhavYardListDetailModel.setZoneId(detailJson.getInt("ZoneId"));
                        bajarBhavYardListDetailModel.setYardId(detailJson.getInt("YardId"));
                        bajarBhavYardListDetailModel.setYardName(detailJson.getString("YardName"));
                        getBajarBhavYardDetailListArrayList.add(bajarBhavYardListDetailModel);
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

            Log.d(TAG, "onPostExecute: BajarYardList Size" + getBajarBhavYardDetailListArrayList.size());

            if (getBajarBhavYardDetailListArrayList != null && getBajarBhavYardDetailListArrayList.size() > 0) {
                CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity(), getBajarBhavYardDetailListArrayList);
                listviewBajarYard.setAdapter(customBaseAdapter);
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
        List<BajarBhavYardListDetailModel> rowItems;
        LayoutInflater mInflater;

        public CustomBaseAdapter(Context context, List<BajarBhavYardListDetailModel> items) {
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            this.rowItems = items;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fragment_bajar_yardlist_detail, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // for alternate color of listview
           /* if (position % 2 == 1) {
                convertView.setBackgroundResource(R.drawable.listview_selector_even);
            }
            else {
                convertView.setBackgroundResource(R.drawable.listview_selector_odd);
            }*/


            FontUtils.setFontForText(context, holder.tvBajarYardDetail, "bold");
            try {
                if (!TextUtils.isEmpty(getBajarBhavYardDetailListArrayList.get(position).getYardName())) {
                    holder.tvBajarYardDetail.setText(rowItems.get(position).getYardName());
                }
            } catch (Exception e) {

            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   Toast.makeText(getActivity(), "BajarBhavZone Detail Fragment:" + getBajarBhavYardDetailListArrayList.get(position).getYardId(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("zoneID", getBajarBhavYardDetailListArrayList.get(position).getZoneId());
                    bundle.putInt("yardID", getBajarBhavYardDetailListArrayList.get(position).getYardId());
                    bundle.putInt("position", position);
                    bundle.putInt("position", position);

                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    BajarBhavCropListFragment bajarBhavCropListFragment = new BajarBhavCropListFragment();
                    fragmentTransaction.replace(R.id.frame_container, bajarBhavCropListFragment, BajarBhavCropListFragment.class.getSimpleName());
                    bajarBhavCropListFragment.setArguments(bundle);
                    //   fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.addToBackStack(BajarBhavCropListFragment.class.getSimpleName());
                    fragmentTransaction.commit();
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
            return position;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'fragment_bajar_yardlist_detail.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */

        class ViewHolder {
            @Bind(R.id.tv_bajar_YardDetail)
            AgriScienceTextView tvBajarYardDetail;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}


