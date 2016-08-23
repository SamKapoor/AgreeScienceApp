package com.agriscienceapp.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.activity.NavigationDrawerActivity;
import com.agriscienceapp.common.Common;
import com.agriscienceapp.common.Utility;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.model.PakPasandCropDetailModel;
import com.agriscienceapp.model.PakPasandCropModel;
import com.agriscienceapp.model.PakPasandYardDetailModel;
import com.agriscienceapp.model.PakPasandYardModel;
import com.agriscienceapp.webservice.RestClientRetroFit;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PakPasandFragment extends Fragment {

    @Bind(R.id.spinner_crop)
    Spinner spinnerCrop;
    @Bind(R.id.spinner_yard)
    Spinner spinnerYard;
    @Bind(R.id.btn_add)
    TextView btnAdd;

    @Bind(R.id.tv_Alert)
    TextView tv_Alert;

    @Bind(R.id.ll_pak_pasand)
    LinearLayout llPakPasand;


    @Bind(R.id.ll_selected_crop1)
    LinearLayout llCrop1;
    @Bind(R.id.tv_advertise_1)
    TextView tvAdvertise1;
    @Bind(R.id.btn_delete_Adv_1)
    TextView btnDeleteAdv1;

    @Bind(R.id.ll_selected_crop2)
    LinearLayout llCrop2;
    @Bind(R.id.tv_advertise_2)
    TextView tvAdvertise2;
    @Bind(R.id.btn_delete_Adv_2)
    TextView btnDeleteAdv2;

    @Bind(R.id.ll_selected_crop3)
    LinearLayout llCrop3;
    @Bind(R.id.tv_advertise_3)
    TextView tvAdvertise3;
    @Bind(R.id.btn_delete_Adv_3)
    TextView btnDeleteAdv3;

    @Bind(R.id.ll_selected_crop4)
    LinearLayout llCrop4;
    @Bind(R.id.tv_advertise_4)
    TextView tvAdvertise4;
    @Bind(R.id.btn_delete_Adv_4)
    TextView btnDeleteAdv4;

    @Bind(R.id.ll_selected_crop5)
    LinearLayout llCrop5;
    @Bind(R.id.tv_advertise_5)
    TextView tvAdvertise5;
    @Bind(R.id.btn_delete_Adv_5)
    TextView btnDeleteAdv5;


    ArrayList<PakPasandCropDetailModel> getCropArrayList;
    ArrayList<PakPasandYardDetailModel> getYardArrayList;

    private String BASEURL = "http://agriscienceindia.com/api/MasterDetail/";
    private String MethodsCrop = "PakPasandCrop";
    private String MethodsYard = "PakPasandYard";

    private String TAG = PakPasandFragment.class.getSimpleName();
    private ProgressDialog progressDialog;

    private int cropId, yardId;
    private String cropName, yardName;
    private boolean changed;

    public PakPasandFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pak_pasand, container, false);
        ButterKnife.bind(this, view);

        getCropArrayList = new ArrayList<PakPasandCropDetailModel>();
        getYardArrayList = new ArrayList<PakPasandYardDetailModel>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Jai Kisaan...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
//        progressDialog.show();
        setTexts();
        if (Utility.isConnectingToInternet(getActivity())) {
            RestClientRetroFit.getWebServices().getPakPasandCrop(new Callback<PakPasandCropModel>() {

                @Override
                public void success(PakPasandCropModel pakPasandCropModel, Response response) {
                    getCropArrayList = (ArrayList<PakPasandCropDetailModel>) pakPasandCropModel.getDetail();
                    Log.e(TAG, "" + getCropArrayList.size());
                    progressDialog.dismiss();
                    if (getCropArrayList != null && getCropArrayList.size() > 0) {
                        CustomBaseAdapterCrop customBaseAdapterCrop = new CustomBaseAdapterCrop(getActivity(), getCropArrayList);
                        spinnerCrop.setAdapter(customBaseAdapterCrop);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                }
            });
        }else {
            Toast.makeText(getActivity(), "No internet Connection.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }


        try {
            spinnerCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    changed = true;
                    PakPasandCropDetailModel clickedObj = (PakPasandCropDetailModel) parent.getItemAtPosition(position);
                    getPakPasandYard(clickedObj.getCropId());
                    cropId = clickedObj.getCropId();
                    cropName = clickedObj.getCropName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerYard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    changed = true;
                    PakPasandYardDetailModel clickedObj = (PakPasandYardDetailModel) parent.getItemAtPosition(position);
                    yardId = clickedObj.getYardId();
                    yardName = clickedObj.getYardName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }catch (Exception e){

        }
        progressDialog.dismiss();
        return view;
    }

    @OnClick(R.id.btn_delete_Adv_1)
    public void onDeleteClick1() {
        llCrop1.setVisibility(View.GONE);
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1), "", getString(R.string.app_name));
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1id), "", getString(R.string.app_name));
        ((NavigationDrawerActivity) getActivity()).getPakPasand(getAllSavedCrops());
    }

    @OnClick(R.id.btn_delete_Adv_2)
    public void onDeleteClick2() {
        llCrop2.setVisibility(View.GONE);
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2), "", getString(R.string.app_name));
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2id), "", getString(R.string.app_name));
        ((NavigationDrawerActivity) getActivity()).getPakPasand(getAllSavedCrops());
    }

    @OnClick(R.id.btn_delete_Adv_3)
    public void onDeleteClick3() {
        llCrop3.setVisibility(View.GONE);
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3), "", getString(R.string.app_name));
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3id), "", getString(R.string.app_name));
        ((NavigationDrawerActivity) getActivity()).getPakPasand(getAllSavedCrops());
    }

    @OnClick(R.id.btn_delete_Adv_4)
    public void onDeleteClick4() {
        llCrop4.setVisibility(View.GONE);
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4), "", getString(R.string.app_name));
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4id), "", getString(R.string.app_name));
        ((NavigationDrawerActivity) getActivity()).getPakPasand(getAllSavedCrops());
    }

    @OnClick(R.id.btn_delete_Adv_5)
    public void onDeleteClick5() {
        llCrop5.setVisibility(View.GONE);
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5), "", getString(R.string.app_name));
        Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5id), "", getString(R.string.app_name));
        ((NavigationDrawerActivity) getActivity()).getPakPasand(getAllSavedCrops());
    }

    @OnClick(R.id.btn_add)
    public void onAddClick() {

        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1), getString(R.string.app_name)).trim().length() > 0
                && Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2), getString(R.string.app_name)).trim().length() > 0
                && Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3), getString(R.string.app_name)).trim().length() > 0
                && Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4), getString(R.string.app_name)).trim().length() > 0
                && Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5), getString(R.string.app_name)).trim().length() > 0
                ) {
            if (tv_Alert.getVisibility() == View.GONE) {
                tv_Alert.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tv_Alert.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 5000);
            }
        }


        if (changed) {
            changed = false;
            if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1), getString(R.string.app_name)).trim().length() == 0) {
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1), cropName + "  " + yardName, getString(R.string.app_name));
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1id), cropId + "-" + yardId, getString(R.string.app_name));
            } else if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2), getString(R.string.app_name)).trim().length() == 0) {
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2), cropName + "  " + yardName, getString(R.string.app_name));
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2id), cropId + "-" + yardId, getString(R.string.app_name));
            } else if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3), getString(R.string.app_name)).trim().length() == 0) {
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3), cropName + "  " + yardName, getString(R.string.app_name));
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3id), cropId + "-" + yardId, getString(R.string.app_name));
            } else if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4), getString(R.string.app_name)).trim().length() == 0) {
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4), cropName + "  " + yardName, getString(R.string.app_name));
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4id), cropId + "-" + yardId, getString(R.string.app_name));
            } else if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5), getString(R.string.app_name)).trim().length() == 0) {
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5), cropName + "  " + yardName, getString(R.string.app_name));
                Common.setStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5id), cropId + "-" + yardId, getString(R.string.app_name));
            } else {
//                Common.showAlertDialog(getActivity(), "", "5 crops already added", false);
                if (tv_Alert.getVisibility() == View.GONE) {
                    tv_Alert.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tv_Alert.setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 5000);
                }
            }
            setTexts();
        }
    }

    private void setTexts() {
        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1), getString(R.string.app_name)).trim().length() > 0) {
            llCrop1.setVisibility(View.VISIBLE);
            tvAdvertise1.setText(Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1), getString(R.string.app_name)));
        }
        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2), getString(R.string.app_name)).trim().length() > 0) {
            llCrop2.setVisibility(View.VISIBLE);
            tvAdvertise2.setText(Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2), getString(R.string.app_name)));
        }
        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3), getString(R.string.app_name)).trim().length() > 0) {
            llCrop3.setVisibility(View.VISIBLE);
            tvAdvertise3.setText(Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3), getString(R.string.app_name)));
        }
        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4), getString(R.string.app_name)).trim().length() > 0) {
            llCrop4.setVisibility(View.VISIBLE);
            tvAdvertise4.setText(Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4), getString(R.string.app_name)));
        }

        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5), getString(R.string.app_name)).trim().length() > 0) {
            llCrop5.setVisibility(View.VISIBLE);
            tvAdvertise5.setText(Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5), getString(R.string.app_name)));
        }
        ((NavigationDrawerActivity) getActivity()).getPakPasand(getAllSavedCrops());
    }

    private String getAllSavedCrops() {
        String savedCrops = "";
        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop1id), getString(R.string.app_name)) + ",";

        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop2id), getString(R.string.app_name)) + ",";

        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop3id), getString(R.string.app_name)) + ",";

        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop4id), getString(R.string.app_name)) + ",";

        if (Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5id), getString(R.string.app_name)).trim().length() > 0)
            savedCrops += Common.getStringPrefrences(getActivity(), getString(R.string.pref_selected_crop5id), getString(R.string.app_name)) + ",";

        if (savedCrops.trim().length() > 0) {
            return savedCrops.substring(0, savedCrops.length());
        } else {
            changed = false;
            return "0-0";
        }
    }

    private void getPakPasandYard(int cropId) {
        progressDialog.show();
        if (Utility.isConnectingToInternet(getActivity())) {
            RestClientRetroFit.getWebServices().getPakPasandCropYard(cropId, new Callback<PakPasandYardModel>() {

                @Override
                public void success(PakPasandYardModel pakPasandYardModel, Response response) {
                    progressDialog.dismiss();
                    getYardArrayList = (ArrayList<PakPasandYardDetailModel>) pakPasandYardModel.getDetail();
                    Log.e(TAG, "" + getYardArrayList.size());
//                    progressDialog.dismiss();
                    if (getYardArrayList != null && getYardArrayList.size() > 0) {
                        CustomBaseAdapterYard customBaseAdapterYard = new CustomBaseAdapterYard(getActivity(), getYardArrayList);
                        spinnerYard.setAdapter(customBaseAdapterYard);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                }
            });
        }else {
            Toast.makeText(getActivity(), "No internet Connection.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class CustomBaseAdapterCrop extends BaseAdapter {
        Context context;
        List<PakPasandCropDetailModel> rowItems;
        LayoutInflater mInflater;

        public CustomBaseAdapterCrop(Context context, List<PakPasandCropDetailModel> items) {
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            this.rowItems = items;
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_spinner_crop, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                if (!TextUtils.isEmpty(getCropArrayList.get(position).getCropName())) {
                    holder.tvRowSpinnerCrop.setText(rowItems.get(position).getCropName());
                }
            }catch (Exception e){

            }
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.tv_row_spinner_crop)
            AgriScienceTextView tvRowSpinnerCrop;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    public class CustomBaseAdapterYard extends BaseAdapter {
        Context context;
        List<PakPasandYardDetailModel> rowItems;
        LayoutInflater mInflater;

        public CustomBaseAdapterYard(Context context, List<PakPasandYardDetailModel> items) {
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            this.rowItems = items;
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_spinner_yard, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                if (!TextUtils.isEmpty(getYardArrayList.get(position).getYardName())) {
                    holder.tvRowSpinnerYard.setText(getYardArrayList.get(position).getYardName());
                }
            }catch (Exception e){

            }
            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'row_spinner_yard.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */

        class ViewHolder {
            @Bind(R.id.tv_row_spinner_yard)
            AgriScienceTextView tvRowSpinnerYard;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
