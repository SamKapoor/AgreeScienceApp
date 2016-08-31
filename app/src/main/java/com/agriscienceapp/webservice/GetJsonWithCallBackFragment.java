package com.agriscienceapp.webservice;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.agriscienceapp.common.ConnectivityDetector;
import com.agriscienceapp.common.GlobalData;

public class GetJsonWithCallBackFragment extends
        AsyncTask<String, JSONObject, JSONObject> {

    private OnUpdateListener onUpdateListener;
    private Context context;
    private ProgressDialog progressDialog;
    private JSONObject jsonObject;
    private int intDialogShow = 0;

    public GetJsonWithCallBackFragment(Context context, JSONObject jsonObject,
                                       int intDialogShow, OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
        this.jsonObject = jsonObject;
        this.intDialogShow = intDialogShow;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        if (!ConnectivityDetector.isConnectingToInternet(context)) {
            Toast.makeText(context, "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            return;
        }

        super.onPreExecute();
        if (intDialogShow == 1) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(GlobalData.STR_PROGRESS_DIALOG_MESSAGE);
            progressDialog.setTitle("");
            progressDialog.show();
        }
    }

    @Override
    protected JSONObject doInBackground(String... param) {
        JSONObject getJsonObject = null;
        try {
            JSONParser jsonParser = new JSONParser();
            getJsonObject = jsonParser.getJSONFromUrl("http://agriscienceindia.com/api/MasterDetailV2/NewsList/", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getJsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonResult) {
        super.onPostExecute(jsonResult);
        try {
            if (jsonResult != null) {
                if (jsonResult.getString("result").equals("1")) {
                    onUpdateListener.onUpdateComplete(jsonResult, true);
                } else {
                    onUpdateListener.onUpdateComplete(jsonResult, false);
//					AlertDialogUtility.showToast(context,
//							jsonResult.getString(WebField.MESSAGE));
                }
            }
            if (intDialogShow == 1) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}