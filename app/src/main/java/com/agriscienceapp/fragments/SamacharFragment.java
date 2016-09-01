package com.agriscienceapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agriscienceapp.R;
import com.agriscienceapp.common.ConnectivityDetector;
import com.agriscienceapp.common.Utility;
import com.agriscienceapp.font.AgriScienceTextView;
import com.agriscienceapp.font.FontUtils;
import com.agriscienceapp.model.Detail;
import com.agriscienceapp.model.KheduSafalGathaDetailModel;
import com.agriscienceapp.model.MessageListVo;
import com.agriscienceapp.model.SamacharModel;
import com.agriscienceapp.webservice.GetJsonWithCallBack;
import com.agriscienceapp.webservice.GetJsonWithCallBackFragment;
import com.agriscienceapp.webservice.MessageDao;
import com.agriscienceapp.webservice.OnUpdateListener;
import com.agriscienceapp.webservice.RestClientRetroFit;
import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SamacharFragment extends Fragment {

    private final String TAG = SamacharFragment.class.getSimpleName();

    @Bind(R.id.listview_samachar)
    ListView listviewSamachar;
    @Bind(R.id.iv_ads_samachar)
    ImageView ivAdsSamachar;
    private View SamacharView;
    private ProgressDialog progressDialog;
    ArrayList<Detail> getSamacharDetailListArrayList;
    ArrayList<Detail> tempArrListSamachar;

    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LayoutInflater inflaterView;
    Detail detail;
    int position = 0;

    public AQuery aq;
    private DisplayImageOptions optionsAdBanner;
    private ArrayList<MessageListVo> listMessagesVo = new ArrayList<MessageListVo>();
    String strMessage, strReason;
    private CountDownTimer timer;
    public static  int strVenueID=21;

    public SamacharFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SamacharView = inflater.inflate(R.layout.fragment_samachar, container, false);
        ButterKnife.bind(this, SamacharView);
        getIntentData();
        getNewMessage();
        aq = new AQuery(getActivity());

        imageLoader = ImageLoader.getInstance();
        initLoader(getActivity());

//             AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
//        AdRequest adRequest = new AdRequest.Builder().build();
//           adView.setAdSize(AdSize.FULL_BANNER);
//        adView.loadAd(adRequest);

        getSamacharDetailListArrayList = new ArrayList<Detail>();

        inflaterView = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        progressDialog = new ProgressDialog(getActivity());
        // progressDialog.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        progressDialog.setMessage("Jai Kisaan ...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        // for change the color of text in progressDialog
//        String s= "Jai Kisaan...";
//        SpannableString ss2=  new SpannableString(s);
//        ss2.setSpan(new RelativeSizeSpan(2f), 0, ss2.length(), 0);
//        ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 0, ss2.length(), 0);
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(ss2);
//        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
//        progressDialog.show();

        // for color of spinner in progress Dialog
//        progressbar = new android.widget.ProgressBar(
//                getContext(),
//                null,
//                android.R.attr.progressBarStyle);
//        progressbar.getIndeterminateDrawable().setColorFilter(new LightingColorFilter(0x006633, getId()));

        if (Utility.isConnectingToInternet(getActivity())) {
            RestClientRetroFit.getWebServices().getSamacharList(new Callback<SamacharModel>() {
                @Override
                public void success(SamacharModel samacharModel, Response response) {
                    getSamacharDetailListArrayList = (ArrayList<Detail>) samacharModel.getDetail();
                    Log.e(TAG, "" + getSamacharDetailListArrayList.size());
                    progressDialog.dismiss();

                    if (getSamacharDetailListArrayList != null && getSamacharDetailListArrayList.size() > 0) {

                        View listHeader = inflater.inflate(R.layout.header_samachar, null);
                        listviewSamachar.addHeaderView(listHeader);

                        final TextView headerTvSamachar = (TextView) listHeader.findViewById(R.id.header_Tv_Samachar);
                        ImageView headerIVSamachar = (ImageView) listHeader.findViewById(R.id.header_iv_Samachar);
                        //     ImageView headerIVShare = (ImageView) listHeader.findViewById(R.id.iv_samachar_share);
                        LinearLayout headerllSamachar = (LinearLayout) listHeader.findViewById(R.id.header_ll_Samachar_main);

                        FontUtils.setFontForText(getActivity(), headerIVSamachar, "bold");

                        try {
                            if (!TextUtils.isEmpty(getSamacharDetailListArrayList.get(0).getNewsTitle())) {
                                headerTvSamachar.setText(getSamacharDetailListArrayList.get(0).getNewsTitle());
                            }
                            if (!TextUtils.isEmpty(getSamacharDetailListArrayList.get(0).getThumbs())) {
                                imageLoader.displayImage(getSamacharDetailListArrayList.get(0).getThumbs().replace("_Thumbs", ""), headerIVSamachar, options);
                                aq.id(R.id.header_iv_Samachar).progress(R.id.progressbar_samachar_header).image(getSamacharDetailListArrayList.get(0).getThumbs(), false, false);
                            }

                            if (!TextUtils.isEmpty(getSamacharDetailListArrayList.get(0).getMainAdd())) {
//                                imageLoader.displayImage(getSamacharDetailListArrayList.get(0).getMainAdd(), ivAdsSamachar, optionsAdBanner);
                                Glide.with(getActivity()).load(getSamacharDetailListArrayList.get(0).getMainAdd().trim()).into(ivAdsSamachar);
                                int width = 50;
                                int height = 50;
                                try{
                                    height = Integer.parseInt(getSamacharDetailListArrayList.get(0).getHeight());
                                    width =Integer.parseInt(getSamacharDetailListArrayList.get(0).getWidth());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                ivAdsSamachar.getLayoutParams().height = height;
                                ivAdsSamachar.requestLayout();
//                            aq.id(R.id.iv_ads_samachar).progress(R.id.progressbar_samachar_header).image(getSamacharDetailListArrayList.get(0).getThumbs(), false, false);
                            } else {
                                ivAdsSamachar.setVisibility(View.GONE);
                            }
                            ivAdsSamachar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setAdvClick(getSamacharDetailListArrayList.get(0));
                                }
                            });

                        } catch (Exception e) {

                        }

                        //     for transparent view of header
//                        headerTvSamachar.setTextColor(Color.WHITE);
//                        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);
//                        int gradientStartColor = Color.argb(0, 0, 0, 0);
//                        int gradientEndColor = Color.argb(255, 0, 0, 0);
//                        GradientOverImageDrawable gradientDrawable = new GradientOverImageDrawable(getResources(), image);
//                        gradientDrawable.setGradientColors(gradientStartColor, gradientEndColor);
//                        headerIVSamachar.setImageDrawable(gradientDrawable);

                        tempArrListSamachar = new ArrayList<Detail>();
                        for (int count = 1; count < getSamacharDetailListArrayList.size(); count++) {
                            tempArrListSamachar.add(getSamacharDetailListArrayList.get(count));
                        }

                        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity(), tempArrListSamachar);
                        listviewSamachar.setAdapter(customBaseAdapter);


                        headerllSamachar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "ListView Clicked");
                                //    Toast.makeText(getActivity(), "Samachar  Fragment: " + getSamacharDetailListArrayList.get(0).getNewsId(), Toast.LENGTH_SHORT).show();
                                Bundle bundle = new Bundle();
                                bundle.putInt("newsID", getSamacharDetailListArrayList.get(0).getNewsId());
                                bundle.putInt("position", position);

                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                SamacharDetailFragment samacharDetailFragment = new SamacharDetailFragment();
                                fragmentTransaction.replace(R.id.frame_container, samacharDetailFragment, SamacharDetailFragment.class.getSimpleName());
                                samacharDetailFragment.setArguments(bundle);
                                //        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                fragmentTransaction.addToBackStack(SamacharDetailFragment.class.getSimpleName());
                                fragmentTransaction.commit();
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
        return SamacharView;
    }

    private void setAdvClick(final Detail samacharModel) {
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

                View dialogLayout = inflaterView.inflate(R.layout.detail_adv_dialog_layout, null);
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

    private void getIntentData() {
//        Bundle args = getArguments();
//        strVenueID = args.getString("result");

        Intent intent = getActivity().getIntent();
        // strVenueID = intent.getStringExtra("result");
//        Toast.makeText(getActivity(), "strVenueID." + strVenueID, Toast.LENGTH_SHORT).show();
//        Bundle b = intent.getExtras();
//        if (intent != null) {
//            notificationUserListVo = new NotificationListVo();
//            notificationUserListVo = (NotificationListVo) b.getSerializable("UD");
//        }
    }

    private void getNewMessage() {
        try {
            timer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (listMessagesVo.size() > 0) {
                        callreceiveMessageService();
                    } else {
//                        callGetMessageService();
                    }
                }

                @Override
                public void onFinish() {
                    getNewMessage();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callreceiveMessageService() {
        try {
            if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
                if (listMessagesVo.size() > 0) {

                    JSONObject jsonObjectGetMessage = new JSONObject();

                    jsonObjectGetMessage.put("NewsId", 21);

                    GetJsonWithCallBackFragment getJsonWithCallBackFragment = new GetJsonWithCallBackFragment(getActivity(), jsonObjectGetMessage, 0, new OnUpdateListener() {

                        @Override
                        public void onUpdateComplete(JSONObject jsonObject,
                                                     boolean isSuccess) {
                            try {
                                if (isSuccess) {
                                    ArrayList<MessageListVo> listMessagesVoTemp = new ArrayList<MessageListVo>();
                                    MessageDao messageDao = new MessageDao();
                                    listMessagesVoTemp = messageDao.getAllUsersFromJson(jsonObject.getJSONArray("detail"));
                                    listMessagesVo.addAll(listMessagesVoTemp);

                                    for (int i = 0; i < listMessagesVoTemp.size(); i++) {
                                        Log.i("listMessagesVoTemp::", "listMessagesVoTemp::" + listMessagesVoTemp.get(i).getNewsTitle());
                                    }
//
//                                      listMessageAdapter.notifyDataSetChanged();
//
//                                    if(listMessagesVoTemp.size() > 0){
//                                        scrollMyListViewToBottom();
//                                    }
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
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //class for transparent view of image over text in header
//    public class GradientOverImageDrawable extends BitmapDrawable {
//
//        private int[] gradientColors;
//        private float[] gradientPositions;
//        private double gradientStart = 0.55;
//        private double gradientEnd = 1;
//
//        public GradientOverImageDrawable(Resources res, Bitmap bitmap) {
//            super(res, bitmap);
//        }
//
//        public GradientOverImageDrawable(Resources res, String filepath) {
//            super(res, filepath);
//        }
//
//        public GradientOverImageDrawable(Resources res, InputStream is) {
//            super(res, is);
//        }
//
//        public void setGradientColors(int[] gradientColors) {
//            this.gradientColors = gradientColors;
//        }
//
//        public void setGradientColors(int startColor, int... endColors) {
//            if (endColors.length == 0) {
//                throw new IllegalArgumentException("The endColors array must have at least one element");
//            }
//            gradientColors = new int[endColors.length + 1];
//            gradientColors[0] = startColor;
//            System.arraycopy(endColors, 0, gradientColors, 1, endColors.length);
//        }
//
//        public int[] getGradientColors() {
//            return gradientColors;
//        }
//
//        public float[] getGradientPositions() {
//            return gradientPositions;
//        }
//
//        public void setGradientPositions(float[] gradientPositions) {
//            for (float pos : gradientPositions) {
//                if (pos > 1 || pos < 0) {
//                    throw new IllegalArgumentException("The gradient position must be a float number between 0 and 1");
//                }
//            }
//            this.gradientPositions = gradientPositions;
//        }
//
//        public double getGradientStart() {
//            return gradientStart;
//        }
//
//        public void setGradientStart(double gradientStart) {
//            this.gradientStart = gradientStart;
//        }
//
//        public double getGradientEnd() {
//            return gradientEnd;
//        }
//
//        public void setGradientEnd(double gradientEnd) {
//            this.gradientEnd = gradientEnd;
//        }
//
//        @Override
//        public void draw(Canvas canvas) {
//            super.draw(canvas);
//            if (gradientColors != null) {
//                Rect bounds = getBounds();
//
//                int x0 = bounds.left;
//                int y0 = (int) Math.round(bounds.bottom * gradientStart);
//                int x1 = x0;
//                int y1 = (int) Math.round(bounds.bottom * gradientEnd);
//
//                LinearGradient shader = new LinearGradient(x0, y0, x1, y1, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
//                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//                paint.setShader(shader);
//                canvas.drawRect(x0, y0, bounds.right, y1, paint);
//            }
//        }
//    }

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
        optionsAdBanner = new DisplayImageOptions.Builder()
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        imageLoader.destroy();
        ButterKnife.unbind(this);
    }

    public class CustomBaseAdapter extends BaseAdapter {
        Context context;
        List<Detail> rowItems;
        ImageLoader imageLoader;
        DisplayImageOptions displayImageOptions;
        LayoutInflater mInflater;

        public CustomBaseAdapter(Context context, List<Detail> items) {
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            this.rowItems = items;
            imageLoader = ImageLoader.getInstance();
            displayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.app_logo)
                    .showImageForEmptyUri(R.drawable.app_logo)
                    .showImageOnFail(R.drawable.app_logo).cacheInMemory(true)
                    .cacheOnDisc(true).build();
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
                convertView = mInflater.inflate(R.layout.row_samachar_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            FontUtils.setFontForText(context, holder.rowSamacharTvTitle, "bold");
            try {
                if (!TextUtils.isEmpty(tempArrListSamachar.get(position).getNewsTitle())) {
                    holder.rowSamacharTvTitle.setText(rowItems.get(position).getNewsTitle());
                }

                if (!TextUtils.isEmpty(tempArrListSamachar.get(position).getThumbs())) {
                    imageLoader.displayImage(rowItems.get(position).getThumbs(), holder.rowSamacharThumb, options);
                    //        aq.id(R.id.row_samachar_thumb).progress(R.id.progressbar_samachar_listview).image(tempArrListSamachar.get(position).getThumbs(), false, false);
                }
            } catch (Exception e) {

            }
            convertView.setSelected(true);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "ListView Clicked");
                    //         Toast.makeText(getActivity(), "Samachar  Fragment: " + tempArrListSamachar.get(position).getNewsId(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("newsID", tempArrListSamachar.get(position).getNewsId());
                    bundle.putInt("position", position);

                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    SamacharDetailFragment samacharDetailFragment = new SamacharDetailFragment();
                    fragmentTransaction.replace(R.id.frame_container, samacharDetailFragment, SamacharDetailFragment.class.getSimpleName());
                    samacharDetailFragment.setArguments(bundle);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.addToBackStack(SamacharDetailFragment.class.getSimpleName());
                    fragmentTransaction.commit();
                }
            });

            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'row_samachar_list.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */

        class ViewHolder {
            @Bind(R.id.row_samachar_thumb)
            ImageView rowSamacharThumb;
            @Bind(R.id.progressbar_samachar_listview)
            ProgressBar progressbarSamacharListview;
            @Bind(R.id.row_samachar_tv_title)
            AgriScienceTextView rowSamacharTvTitle;
            @Bind(R.id.row_samachar_ll_thumb)
            LinearLayout rowSamacharLlThumb;


            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
