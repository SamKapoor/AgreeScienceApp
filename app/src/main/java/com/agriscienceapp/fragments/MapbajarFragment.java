package com.agriscienceapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.common.Utility;
import com.agriscienceapp.model.BajarBhavZoneListDetailModel;
import com.agriscienceapp.model.BajarBhavZoneListModel;
import com.agriscienceapp.webservice.RestClientRetroFit;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapbajarFragment extends Fragment {

    public final String TAG = MapbajarFragment.class.getSimpleName();

    ArrayList<BajarBhavZoneListDetailModel> getBajarBhavZoneDetailListArrayList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ProgressDialog progressDialog;
    public View MapZoneView;
    int position = 0;

    @Bind(R.id.img_bajar_map_UtarGujarat)
    ImageView imgBajarMapUtarGujarat;
    @Bind(R.id.img_bajar_map_Kutch)
    ImageView imgBajarMapKutch;
    @Bind(R.id.img_bajar_map_MadhyaGujarat)
    ImageView imgBajarMapMadhyaGujarat;
    @Bind(R.id.img_bajar_map_Saurastra)
    ImageView imgBajarMapSaurastra;
    @Bind(R.id.img_bajar_map_DakshinGujarat)
    ImageView imgBajarMapDakshinGujarat;
    @Bind(R.id.img_bajar_map_AnyaBajarBhav)
    ImageView imgBajarMapAnyaBajarBhav;

    public MapbajarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MapZoneView = inflater.inflate(R.layout.fragment_mapbajar, container, false);
        ButterKnife.bind(this, MapZoneView);

        //   progressDialog = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Jai Kisaan...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();


        getBajarBhavZoneDetailListArrayList = new ArrayList<BajarBhavZoneListDetailModel>();

        if (Utility.isConnectingToInternet(getActivity())) {
            RestClientRetroFit.getWebServices().getBajarBhavZoneList(new Callback<BajarBhavZoneListModel>() {

                @Override
                public void success(BajarBhavZoneListModel bajarBhavZoneListModel, Response response) {
                    getBajarBhavZoneDetailListArrayList = (ArrayList<BajarBhavZoneListDetailModel>) bajarBhavZoneListModel.getDetail();
                    Log.e(TAG, "" + getBajarBhavZoneDetailListArrayList.size());
                    progressDialog.dismiss();

                    if (getBajarBhavZoneDetailListArrayList != null && getBajarBhavZoneDetailListArrayList.size() > 0) {

                            imgBajarMapKutch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("zoneID", getBajarBhavZoneDetailListArrayList.get(4).getZoneId());
                                        bundle.putInt("position", 5);

                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        BajarBhavYardListFragment bajarBhavYardListFragment = new BajarBhavYardListFragment();
                                        fragmentTransaction.replace(R.id.frame_container, bajarBhavYardListFragment, BajarBhavYardListFragment.class.getSimpleName());
                                        bajarBhavYardListFragment.setArguments(bundle);
                                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                        fragmentTransaction.addToBackStack(BajarBhavYardListFragment.class.getSimpleName());
                                        fragmentTransaction.commit();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });

                            imgBajarMapUtarGujarat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("zoneID", getBajarBhavZoneDetailListArrayList.get(0).getZoneId());
                                        bundle.putInt("position", 1);

                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        BajarBhavYardListFragment bajarBhavYardListFragment = new BajarBhavYardListFragment();
                                        fragmentTransaction.replace(R.id.frame_container, bajarBhavYardListFragment, BajarBhavYardListFragment.class.getSimpleName());
                                        bajarBhavYardListFragment.setArguments(bundle);
                                        //      fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                        fragmentTransaction.addToBackStack(BajarBhavYardListFragment.class.getSimpleName());
                                        fragmentTransaction.commit();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                            imgBajarMapSaurastra.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("zoneID", getBajarBhavZoneDetailListArrayList.get(3).getZoneId());
                                        bundle.putInt("position", 4);

                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        BajarBhavYardListFragment bajarBhavYardListFragment = new BajarBhavYardListFragment();
                                        fragmentTransaction.replace(R.id.frame_container, bajarBhavYardListFragment, BajarBhavYardListFragment.class.getSimpleName());
                                        bajarBhavYardListFragment.setArguments(bundle);
                                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                        fragmentTransaction.addToBackStack(BajarBhavYardListFragment.class.getSimpleName());
                                        fragmentTransaction.commit();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                            imgBajarMapMadhyaGujarat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("zoneID", getBajarBhavZoneDetailListArrayList.get(2).getZoneId());
                                        bundle.putInt("position", 3);

                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        BajarBhavYardListFragment bajarBhavYardListFragment = new BajarBhavYardListFragment();
                                        fragmentTransaction.replace(R.id.frame_container, bajarBhavYardListFragment, BajarBhavYardListFragment.class.getSimpleName());
                                        bajarBhavYardListFragment.setArguments(bundle);
                                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                        fragmentTransaction.addToBackStack(BajarBhavYardListFragment.class.getSimpleName());
                                        fragmentTransaction.commit();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                            imgBajarMapAnyaBajarBhav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("zoneID", getBajarBhavZoneDetailListArrayList.get(5).getZoneId());
                                        bundle.putInt("position", 0);

                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        BajarBhavYardListFragment bajarBhavYardListFragment = new BajarBhavYardListFragment();
                                        fragmentTransaction.replace(R.id.frame_container, bajarBhavYardListFragment, BajarBhavYardListFragment.class.getSimpleName());
                                        bajarBhavYardListFragment.setArguments(bundle);
                                        //               fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                        fragmentTransaction.addToBackStack(BajarBhavYardListFragment.class.getSimpleName());
                                        fragmentTransaction.commit();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            imgBajarMapDakshinGujarat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("zoneID", getBajarBhavZoneDetailListArrayList.get(1).getZoneId());
                                        bundle.putInt("position", 2);

                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        BajarBhavYardListFragment bajarBhavYardListFragment = new BajarBhavYardListFragment();
                                        fragmentTransaction.replace(R.id.frame_container, bajarBhavYardListFragment, BajarBhavYardListFragment.class.getSimpleName());
                                        bajarBhavYardListFragment.setArguments(bundle);
                                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                        fragmentTransaction.addToBackStack(BajarBhavYardListFragment.class.getSimpleName());
                                        fragmentTransaction.commit();
                                    }catch (Exception e){

                                    }
                                }
                            });
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

        return MapZoneView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
