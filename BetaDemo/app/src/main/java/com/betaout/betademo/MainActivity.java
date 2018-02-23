package com.betaout.betademo;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.betaout.betademo.net.VolleySingleton;
import com.betaout.sdk.app.BetaOut;
import com.betaout.sdk.model.BOOrder;
import com.betaout.sdk.model.BOProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Created by root on 30/6/17.
 */
public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    JSONObject jsonData;
    List<String> categories = new ArrayList<>();

    VolleySingleton volleySingleton;
    RequestQueue requestQueue;

    int openPage = 0;


    private void getData(Intent intent){
        try {
            Uri uri = intent.getData();
            int temp = 0;
            if (intent.getData() != null) {
                temp = Integer.parseInt(uri.getQueryParameter("category"));
            }
            if (temp >= 0) {
                openPage = temp;


                Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(viewPager != null && openPage < viewPager.getChildCount()) {

                                viewPager.setCurrentItem(openPage);
                            }
                        }
                    }, 100);

            }
        }catch (Exception e){
        }
    }

    public void onNewIntent(Intent intent){
        getData(intent);
    }


    public void requestJsonData(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestURL(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    jsonData = response;
                    Log.i("AppInfo", response.toString());
                    jsonData = jsonData.getJSONObject("data");

                    Log.i("AppInfo", response.optInt("app_version") + "");

                    if(response.optInt("app_version") > BuildConfig.VERSION_CODE){
                        showUpdateDialog();
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences("jsonData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("jsonData", response.toString());
                    editor.commit();


                    JSONArray cat = jsonData.getJSONArray("categories");

                    for(int i = 0; i < cat.length(); i++){
                        categories.add(((JSONObject)cat.get(i)).getString("cat_name"));
                    }

                    viewPager = (ViewPager)findViewById(R.id.viewPager);
                    viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                    tabLayout = (TabLayout)findViewById(R.id.tabLayout);
                    tabLayout.setupWithViewPager(viewPager);


                }catch(JSONException e){

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SharedPreferences sharedPreferences = getSharedPreferences("jsonData", MODE_PRIVATE);
                if(sharedPreferences.getString("jsonData", "").length() > 0){
                    try{
                        jsonData = new JSONObject(sharedPreferences.getString("jsonData", ""));

                        Log.i("AppInfo", jsonData.toString());
                        Log.i("AppInfo", jsonData.optInt("app_version") + "");
                        if(jsonData.optInt("app_version") > BuildConfig.VERSION_CODE){
                            showUpdateDialog();
                        }

                        jsonData = jsonData.getJSONObject("data");

                        JSONArray cat = jsonData.getJSONArray("categories");

                        for(int i = 0; i < cat.length(); i++){
                            categories.add(((JSONObject)cat.get(i)).getString("cat_name"));
                        }

                        viewPager = (ViewPager)findViewById(R.id.viewPager);
                        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setCurrentItem(openPage);


                    }catch(JSONException e){

                    }
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public byte[] getBody(){
                return null;
            }
        };
        requestQueue.add(request);
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public String getRequestURL(){


        Uri builtUri;
        builtUri = Uri.parse(Constants.EndPoints.DATA_FETCH_URL).buildUpon()
                .build();

        return builtUri.toString();
    }


    private static boolean isCallable(Intent intent) {
        List<ResolveInfo> list = null;
        try {
            Class<?> c = Class.forName("android.app.ActivityThread");
            android.app.Application APP = (android.app.Application) c.getDeclaredMethod("currentApplication").invoke(null);
            android.content.Context context = APP.getApplicationContext();
            list = context.getPackageManager().queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
        }catch (Exception e){
        }
        if(list != null)
            return list.size() > 0;
        else
            return false;
    }

    public static String getGZippedData(String payload) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        GZIPOutputStream boutStream = new GZIPOutputStream(bout);
        boutStream.write(payload.getBytes());
        boutStream.flush();
        boutStream.close();
        return new String(bout.toByteArray());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        SharedPreferences sharedPreferencesID = getSharedPreferences("cmid", Context.MODE_PRIVATE);
        Toast.makeText(this, sharedPreferencesID.getString("cmid", ""), Toast.LENGTH_LONG).show();
        try {
            Log.e("AppInfo", getGZippedData("{ \"t\": \"βetaOut Survey\", \"nid\": \"ocadssdfdr2Y\", \"type\": \"basic\", \"c\": 1, \"m\": \"Complete your survey\",\"l\":\"https://goo.gl/7iA8WM\", \"g\":[{\"lt\":28.603167, \"lg\":77.389861, \"r\":1000, \"id\":\"geeeee\", \"It\":\"23-12-2017 23:00:00\", \"ot\":\"02-12-2017 00:00:00\"}]}"));
        }catch (Exception e){

        }

        getData(getIntent());

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        SharedPreferences sharedPreferences2 = getSharedPreferences("shouldShowPrompt",MODE_PRIVATE);
        if(sharedPreferences2.getBoolean("shouldShowPrompt", false)) {
            SharedPreferences.Editor editor = sharedPreferences2.edit();
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .setTitle("Enable Push Notifications")
                    .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setMessage("Allowing this permission will help us improve your app experience. You can turn it off later at any time from Settings.")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            BetaOut.getInstance().enablePushNotifications(MainActivity.this);
                        }
                    }).show();

            editor.putBoolean("shouldShowPrompt", false);
            editor.commit();
        }




        SharedPreferences sharedPreferences = getSharedPreferences("jsonData", MODE_PRIVATE);

        if(sharedPreferences.getString("jsonData", "").length() == 0 && isNetworkAvailable(this)){
            Log.i("AppInfo", "Requesting");
            requestJsonData();
        }else if(sharedPreferences.getString("jsonData", "").length() > 0){
            try{
                jsonData = new JSONObject(sharedPreferences.getString("jsonData", ""));
                Log.i("AppInfo", jsonData.toString());
                Log.i("AppInfo", jsonData.optInt("app_version") + "");
                if(jsonData.optInt("app_version") > BuildConfig.VERSION_CODE){
                    showUpdateDialog();
                }

                jsonData = jsonData.getJSONObject("data");

                JSONArray cat = jsonData.getJSONArray("categories");

                for(int i = 0; i < cat.length(); i++){
                    categories.add(((JSONObject)cat.get(i)).getString("cat_name"));
                }

                viewPager = (ViewPager)findViewById(R.id.viewPager);
                viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                tabLayout = (TabLayout)findViewById(R.id.tabLayout);
                tabLayout.setupWithViewPager(viewPager);

            }catch(JSONException e){

            }
        }else{
            try{

                jsonData = new JSONObject(Constants.jsonData);
                Log.i("AppInfo", jsonData.toString());
                jsonData = jsonData.getJSONObject("data");


                JSONArray cat = jsonData.getJSONArray("categories");

                for(int i = 0; i < cat.length(); i++){
                    categories.add(((JSONObject)cat.get(i)).getString("cat_name"));
                }

                viewPager = (ViewPager)findViewById(R.id.viewPager);
                viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                tabLayout = (TabLayout)findViewById(R.id.tabLayout);
                tabLayout.setupWithViewPager(viewPager);

            }catch(JSONException e){

            }
        }

        initViews();



    }

    public void initViews(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BetaOut.getInstance().explicitLogout();
                BetaOut.getInstance().explicitLogout();
                SharedPreferences sharedPreferences = getSharedPreferences("gmail", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finishAffinity();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton inboxFab = (FloatingActionButton) findViewById(R.id.inboxFab);
        inboxFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis() % 2 == 0) {
                    BOProduct product = BOProduct.create("23", 2399.34);
                    product.setName("Men"); //vertical name if possible

                    product.addCategoryToProduct("2", "clothes", "0");
                    product.addCategoryToProduct("21", "Men", "2");

                    //Adding Product Specifications
                    Hashtable<String, String> specs = new Hashtable<>();
                    specs.put("L1_id", "2");
                    specs.put("L1_name", "clothes");
                    specs.put("L2_id", "21");
                    specs.put("L2_name", "Men");
                    product.addSpecs(specs);

                    //Create Order Object
                    BOOrder mBOOrder = BOOrder.create("MEN" + System.currentTimeMillis(), 2399.34, 2399.34, "", "SUCCESS", "DEBIT_CARD");

                    //Make Purchase Event with Order object as created above
                    BetaOut.getInstance().completePurchaseOrder(mBOOrder, null, product, null, null, null);
                } else {
                    BOProduct product = BOProduct.create("33", 5999.86);
                    product.setName("Women"); //vertical name if possible

                    product.addCategoryToProduct("2", "clothes", "0");
                    product.addCategoryToProduct("31", "Women", "2");

                    //Adding Product Specifications
                    Hashtable<String, String> specs = new Hashtable<>();
                    specs.put("L1_id", "2");
                    specs.put("L1_name", "clothes");
                    specs.put("L2_id", "31");
                    specs.put("L2_name", "Women");
                    product.addSpecs(specs);

                    //Create Order Object
                    BOOrder mBOOrder = BOOrder.create("WOMEN" + System.currentTimeMillis(), 5999.86, 5999.86, "", "SUCCESS", "DEBIT_CARD");

                    //Make Purchase Event with Order object as created above
                    BetaOut.getInstance().completePurchaseOrder(mBOOrder, null, product, null, null, null);
                }

                Intent intent = new Intent(MainActivity.this, InboxActivity.class);
                startActivity(intent);
            }
        });
    }



    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            ProductsFragment fragment = new ProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", categories.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position);
        }

    }

    public void showUpdateDialog() {
        AlertDialog.Builder dialogUpdate = new AlertDialog.Builder(this, AlertDialog.THEME_TRADITIONAL).setTitle("New Version Available")
                .setIcon(getResources()

                        .getDrawable(android.R.drawable.stat_sys_download))
                .setMessage("New version of app is available. Update your app")
                .setCancelable(false)
                .setNeutralButton("UPDATE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            // TODO: handle exception

                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));

                        }


                    }
                });

        AlertDialog updatedialog = dialogUpdate.create();

        updatedialog.show();
    }

}
