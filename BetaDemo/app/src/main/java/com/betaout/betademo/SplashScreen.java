package com.betaout.betademo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.betaout.sdk.app.BetaOut;
import com.betaout.sdk.model.BOCart;
import com.betaout.sdk.model.BOOrder;
import com.betaout.sdk.model.BOProduct;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener {
    private static final long SPLASH_DISPLAY_LENGTH = 1500;
    Button Get_Started;
    String name;
    ImageView imgLogo;
    TextView txtLogo, loggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("gmail", MODE_PRIVATE);
        if(sharedPreferences.getString("gmail", "").length() > 0){
            Toast.makeText(this, sharedPreferences.getString("gmail", ""), Toast.LENGTH_LONG).show();
            finishAffinity();
            startActivity(new Intent(this, MainActivity.class));
        }

        initViews();
        initClickListeners();

        Get_Started.setVisibility(View.VISIBLE);
        txtLogo.setVisibility(View.VISIBLE);
        imgLogo.setVisibility(View.VISIBLE);
    }


    private void initClickListeners() {
        Get_Started.setOnClickListener(this);
    }

    private void initViews() {
        Get_Started = (Button) findViewById(R.id.Get_Started);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        txtLogo = (TextView) findViewById(R.id.txtLogo);
        loggedIn = (TextView) findViewById(R.id.loggedIn);
        SharedPreferences sharedPreferences = getSharedPreferences("gmail", MODE_PRIVATE);
        loggedIn.setText(sharedPreferences.getString("gmail", ""));
    }


    public void benchmark(View view){
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                BOCart mBOCart = BOCart.create(7370.50, 231.10, "INR"); // Cart Total Value, Revenue from Cart, Currency of Purchase in String
                mBOCart.setAbandon_cart_url("http://www.himalayastore.com/quickorder.aspx");
                mBOCart.setAbandon_cart_deeplink_ios("app://1001394201/myapp/cart?id=22l");
                mBOCart.setAbandon_cart_deeplink_android("app://com.myapp.tkpd/1001394201/myapp/cart?id=22l");



                BOProduct product = BOProduct.create("2610", 4181.75);//  Product ID, Product price (single unit)
                product.addCategoryToProduct("62", "Diabetics Health", "13");//CategoryID, Category Name, Parent CategoryID
                product.setName("QATEST");
                product.setProductImageUrl("http://www.himalayastore.com/images/search/smach.jpg");
                product.setProductUrl("http://www.himalayastore.com/pharmaceuticals/sm21-2s.htm");
                product.setQuantity(5);// MAKE SURE THIS IS NON ZERO POSITIVE VALUE product.setSku("7010080");




                List<BOProduct> products = new ArrayList<BOProduct>();
                products.add(product);


                char ch = 73;
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 100; j++){
                        BOOrder mBOOrder = BOOrder.create(ch + "" + (ch + 1) + "_" + System.currentTimeMillis() + "", 415.0, 415.0, "INR", "completed", "BANK");// Order ID, Order Total Revenue from Cart, Currency of Purchase in String, Status, Method of Payment
                        mBOOrder.setCoupon("HIM10");// Any coupon or promo code applied
                        mBOOrder.setShipping(20.0);// Any shipping charges
                        mBOOrder.setTax(10.0);// Any tax applicable
                        mBOOrder.setDiscount(5.0);// Any discount applicable
                        mBOOrder.updateOrderStatus("Complete");
                        mBOOrder.setShipping_method("SHIPPED");

                        BetaOut.getInstance().completePurchaseOrderWithProperties(mBOOrder, mBOCart, products, null, null);

                        Log.i("Sent", ch + "" + (ch + 1) + "_" + j);
                        //BetaOut.getInstance().logEvents("bo_test_" + ch + "_" + j, null, null);
                        try {
                            Thread.sleep(200);
                        }catch (Exception e){

                        }
                    }
                    try {

                        Thread.sleep(2000);
                        ch++;
                    }catch (InterruptedException e){

                    }

                }


            }
        }, 5000);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.Get_Started) {
            SharedPreferences sharedPreferences = getSharedPreferences("gmail", MODE_PRIVATE);

            if (sharedPreferences.getString("gmail", "").length() == 0) {

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            } else {

                BetaOut.getInstance().explicitLogout();
                BetaOut.getInstance().setCustomerEmail(sharedPreferences.getString("gmail", ""));
                BetaOut.getInstance().setCustomerId(sharedPreferences.getString("id", ""));

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        }

    }

}
