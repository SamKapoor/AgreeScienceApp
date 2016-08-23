package com.agriscienceapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.common.Utility;
import com.agriscienceapp.model.UtaraModel;
import com.agriscienceapp.model.utaraDetail;
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
public class UtaraFragment extends Fragment {

    public final String TAG = UtaraFragment.class.getSimpleName();

    @Bind(R.id.webview_utara)
    WebView webviewUtara;
    @Bind(R.id.tv_utara)
    TextView tvUtara;

    ProgressDialog progressDialog;
    ArrayList<utaraDetail> getUtaraListArrayList;
    int position = 0;

    public UtaraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_utara, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Jai Kisaan..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        getUtaraListArrayList = new ArrayList<utaraDetail>();

        if (Utility.isConnectingToInternet(getActivity())) {
            RestClientRetroFit.getWebServices().getUtara(new Callback<UtaraModel>() {
                @Override
                public void success(UtaraModel utaraModel, Response response) {
                    getUtaraListArrayList = (ArrayList<utaraDetail>) utaraModel.getDetail();
                    Log.e(TAG, "" + getUtaraListArrayList.size());
                    progressDialog.dismiss();

                    try {
                        if (getUtaraListArrayList.get(position).getURLStatus()) {

//                        WebSettings webSettings = webviewUtara.getSettings();
                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setMessage("Loading...");
                            progressDialog.show();
//                        webSettings.setJavaScriptEnabled(true);
                            webviewUtara.loadUrl("https://anyror.gujarat.gov.in/");
                            webviewUtara.setWebViewClient(new WebViewClient());
                            webviewUtara.getSettings().setJavaScriptEnabled(true);
                            webviewUtara.getSettings().setBuiltInZoomControls(true);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "No internet Connection.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }catch (Exception e){

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
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
