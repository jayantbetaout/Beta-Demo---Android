package com.betaout.betademo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.betaout.sdk.app.BetaOut;
import com.betaout.sdk.app.BetaOutConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/2/17.
 */
public class App extends Application{

    private static Application singletonInstance;

    static ArrayList<Object> products;

    @Override
    public void onCreate() {
        super.onCreate();

        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/

        singletonInstance = this;
        Log.i("AppInfo", "Initialized");
        BetaOutConfig config = BetaOutConfig.init("<API Key>", "<Project ID>", "<Sender ID>");
        BetaOut.init(config, this);
        products = new ArrayList<>();
        TinyDB tinyDB = new TinyDB(getContext());
        products = tinyDB.getListObject("store", Products.class);


    }

    public static boolean addProducts(Object product){
        for(int i = 0; i < products.size(); i++){
            if(((Products)products.get(i)).id.equals(((Products)product).id)){
                Log.i("AppInfo", "false returned");
                return false;
            }
        }

        Log.i("AppInfo", "true returned");
        products.add(product);
        TinyDB tinydb = new TinyDB(getContext());
        tinydb.putListObject("store", products);
        return true;
    }

    public static void removeProducts(Object product){
        products.remove(product);
        TinyDB tinydb = new TinyDB(getContext());
        tinydb.putListObject("store", products);
    }

    public static void clearProducts(){
        products.clear();
        TinyDB tinydb = new TinyDB(getContext());
        tinydb.putListObject("store", products);
    }

    public static List<Object> getProducts() {
        Log.i("AppInfo", products.size() + " size returned");
        return products;
    }


    public static Context getContext(){
        return singletonInstance.getApplicationContext();
    }

    public static Application getInstance(){
        return singletonInstance;
    }

}
