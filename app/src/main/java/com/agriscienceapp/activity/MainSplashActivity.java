package com.agriscienceapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.common.ConnectivityDetector;
import com.agriscienceapp.common.Consts;
import com.agriscienceapp.common.Installation;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.TermsAndConditons;
import com.agriscienceapp.webservice.AndroidNetworkUtility;
import com.agriscienceapp.webservice.GetJsonWithCallBack;
import com.agriscienceapp.webservice.OnUpdateListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MainSplashActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button btnOk;
    private Button btnCancel;
    private EditText edtName;
    private EditText edtMobile;
    String strTokenId = "abcd";
    CheckBox chekTerms;
    private String BASEURL = "http://agriscienceindia.com/api/MasterDetail/";
    private String Methods = "getTermsCondition?";
    private String APICALL_URL = "";
    private ProgressDialog progress;
    TextView tv_termsDetails;
    Dialog dialog;
    private JSONObject jObjLogin = new JSONObject();
    public static final String PROPERTY_REG_ID = "registration_id";

    ProgressDialog prgDialog;
    RequestParams params = new RequestParams();
    GoogleCloudMessaging gcmObj;
    Context applicationContext;
    String regId = "";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String REG_ID = "regId";
//    public static final String EMAIL_ID = "eMailId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);


        applicationContext = getApplicationContext();


        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Jai Kisan...");
        prgDialog.setCancelable(false);

        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (!TextUtils.isEmpty(registrationId)) {
            Intent i = new Intent(applicationContext, SplashActivity.class);
            i.putExtra("regId", registrationId);

            String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.d("android_id------------", "android_id--------" + android_id);
            Log.d("Id------------", "--------------" + registrationId);
            Installation.id(applicationContext);
            startActivity(i);
            finish();
        }

//
//        if (Installation.isFirstLaunch(this)) {
//            Log.i("FIRST", "FIRST");
//            callAppsUserInsertService();
//        } else {
//            Log.i("Allready REG user", "Allready REG user");
//
//            Intent intentLogin = new Intent(getApplicationContext(), HomeActivity.class);
//            startActivity(intentLogin);
//            finish();
//
//        }
//

        dialog = new Dialog(MainSplashActivity.this);
        dialog.setContentView(R.layout.dialog_terms);

        getIDs();
        setLitner();


    }

    private void registerInBackground(final String emailID) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging.getInstance(applicationContext);
                    }
                    regId = gcmObj.register(Consts.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    storeRegIdinSharedPref(applicationContext, regId);
                    callAppsUserInsertService(regId);
//                    Toast.makeText(applicationContext, "Registered with GCM Server successfully.nn" + msg, Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(applicationContext, "Reg ID Creation Failed.nnEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time." + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    private void storeRegIdinSharedPref(Context context, String regId) {
        SharedPreferences prefs = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        Log.d("Id------------", "--------------" + regId);

//        editor.putString(EMAIL_ID, emailID);
        editor.commit();
        storeRegIdinServer();

    }

    private void storeRegIdinServer() {
        prgDialog.show();
        params.put("regId", regId);
//        Log.d("Id------------", "--------------" + regId);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Consts.APP_SERVER_URL, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
//                        if (prgDialog != null) {
//                            prgDialog.dismiss();
//                        }
//                        Toast.makeText(applicationContext, "Reg Id shared successfully with Web App ",
//                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(applicationContext,
                                SplashActivity.class);
                        i.putExtra("regId", regId);
//                        Log.d("Id------------", "--------------" + regId);

                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        if (statusCode == 404) {
//                            Toast.makeText(applicationContext, "Requested resource not found", Toast.LENGTH_LONG).show();
                        } else if (statusCode == 500) {
//                            Toast.makeText(applicationContext, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                        } else {
//                            Toast.makeText(applicationContext,
//                                    "Unexpected Error occcured! [Most common Error: Device might "
//                                            + "not be connected to Internet or remote server is not up and running], check for other errors as well",
//                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
//                Toast.makeText(applicationContext, "This device doesn't support Play services, App will not work normally",
//                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
//            Toast.makeText(applicationContext, "This device supports Play services, App will work normally",
//                    Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    private void getIDs() {


        try {
            edtName = (EditText) findViewById(R.id.edtName);
            edtMobile = (EditText) findViewById(R.id.edtMobile);
            chekTerms = (CheckBox) findViewById(R.id.chekTerms);
            btnOk = (Button) findViewById(R.id.btnOk);
            btnCancel = (Button) findViewById(R.id.btnCancel);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void setLitner() {

        try {
            chekTerms.setOnCheckedChangeListener(this);
            findViewById(R.id.tvTerms).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.setTitle("Terms and Conditions");


                    tv_termsDetails = (TextView) dialog.findViewById(R.id.tv_termsDetails);
//                tv_title = (TextView) dialog.findViewById(R.id.tv_title);

                    try {
                        APICALL_URL = BASEURL + Methods; // Pass news id insteadof of 5.

                    } catch (Exception e) {

                    }
                    Log.e("Request: ", "" + APICALL_URL);

                    if (AndroidNetworkUtility.isNetworkConnected(MainSplashActivity.this)) {
                        GetAdvisoryDetails getAdvisoryDetails = new GetAdvisoryDetails(MainSplashActivity.this, APICALL_URL);
                        getAdvisoryDetails.execute();
                    } else {
                        Toast.makeText(MainSplashActivity.this, "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
//                    progress.dismiss();
                    }
                }
            });
            btnOk.setOnClickListener(this);
            btnCancel.setOnClickListener(this);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        try {

            switch (v.getId()) {
                case R.id.btnOk:

                    if (edtName.getText().toString().length() == 0) {
                        edtName.setFocusable(true);
                        Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                    } else if (edtName.getText().toString().length() >= 24) {
                        edtName.setFocusable(true);
                        Toast.makeText(getApplicationContext(), "Enter Maximum name 24 character ", Toast.LENGTH_SHORT).show();
                    } else if (edtMobile.getText().toString().length() == 0) {
                        edtMobile.setFocusable(true);
                        Toast.makeText(getApplicationContext(), "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    } else if (edtMobile.getText().toString().length() >= 12) {
                        edtMobile.setFocusable(true);
                        Toast.makeText(getApplicationContext(), "Enter Maximum 12 Digits Mobile Number", Toast.LENGTH_SHORT).show();
                    } else if (!chekTerms.isChecked()){
                        Toast.makeText(getApplicationContext(), "Please check terms & conditions.", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        registerInBackground(regId);


                        //  Toast.makeText(getApplicationContext(), "This is Ok to get Device id ", Toast.LENGTH_SHORT).show();

                    }


                    break;
                case R.id.btnCancel:

//                    Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show();
                    finish();
                    break;

                default:
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callAppsUserInsertService(String regId) {
        try {
            if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
                JSONObject jsonObjectlogin = new JSONObject();

//                jsonObjectlogin.put(WebField.MODE, WebField.LOGIN_USER.MODE);
//                Log.i("DeviceId===", "DeviceId===" + REG_ID);
//                jsonObjectlogin.put("UserName", edtName.getText().toString());
//                jsonObjectlogin.put("MobileNo", edtMobile.getText().toString());
//                jsonObjectlogin.put("DeviceID", SessionManager.getRegistrationId(MainSplashActivity.this));
//
                GetJsonWithCallBack getJsonWithCallBackFragment = new GetJsonWithCallBack(MainSplashActivity.this, jsonObjectlogin, strTokenId, edtName.getText().toString(), edtMobile.getText().toString(), regId, 1,
                        new OnUpdateListener() {

                            @Override
                            public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                                try {
                                    if (isSuccess) {
                                        jObjLogin = jsonObject;

                                        Intent intentLogin = new Intent(getApplicationContext(), SplashActivity.class);
                                        intentLogin.putExtra("isLaunch", false);
                                        startActivity(intentLogin);
                                        finish();

                                    } else {

//                                        String error = jsonObject.getString(WebField.MESSAGE);
                                        String error = "please try again..";
                                        //AlertDialogUtility.showToast(MainSplashActivity.this, error);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getJsonWithCallBackFragment.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    getJsonWithCallBackFragment.execute();
                }
            } else {
                Toast.makeText(MainSplashActivity.this, "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        try {
            /*if (isChecked == true) {


                dialog.setTitle("Terms and Conditions");


                tv_termsDetails = (TextView) dialog.findViewById(R.id.tv_termsDetails);
//                tv_title = (TextView) dialog.findViewById(R.id.tv_title);

                try {
                    APICALL_URL = BASEURL + Methods; // Pass news id insteadof of 5.

                } catch (Exception e) {

                }
                Log.e("Request: ", "" + APICALL_URL);

                if (AndroidNetworkUtility.isNetworkConnected(MainSplashActivity.this)) {
                    GetAdvisoryDetails getAdvisoryDetails = new GetAdvisoryDetails(MainSplashActivity.this, APICALL_URL);
                    getAdvisoryDetails.execute();
                } else {
                    Toast.makeText(MainSplashActivity.this, "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
//                    progress.dismiss();
                }


            } else {
                if (isChecked == true) {
                    chekTerms.setChecked(false);
                    dialog.dismiss();
                }

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class GetAdvisoryDetails extends AsyncTask<Void, Void, Void> {
        Activity callerActivity;
        private Context mContext;
        private static final String TAG = "GetTermsCondition";
        private String APIURL_CALL;
        private ProgressDialog progress;
        TermsAndConditons termsModel;

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
            Log.i("APIURL_CALL","APIURL_CALL");
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
                        termsModel = new TermsAndConditons();
                        JSONObject detailJson = detail.getJSONObject(i);
                        termsModel.setTermsId(detailJson.getInt("TermsId"));
                        termsModel.setTermsCondition(detailJson.getString("TermsCondition").toString());

//                        termsModel.setTermsStatus(detailJson.getString("TermsStatus"));
                        //  ts.setIsExist(detailJson.getString("TermsId"));


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
//            tv_title.setText("" + termsModel.getTermsId());
//            tv_termsDetails.setText("" + termsModel.getTermsCondition().toString());

            if (progress != null) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }
            }


            FontUtils.setFontForText(mContext, tv_termsDetails, "bold");
            try {

//                tv_title.setText("" + termsModel.getTermsId());

                if (!TextUtils.isEmpty(termsModel.getTermsCondition())) {

//                    Log.i("SecondModel", "SecondModel" + termsModel.getTermsCondition());
                    tv_termsDetails.setText(termsModel.getTermsCondition());
                }

            } catch (Exception e) {

            }
            dialog.show();
        }
    }
}
