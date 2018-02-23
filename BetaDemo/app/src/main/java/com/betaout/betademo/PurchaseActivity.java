package com.betaout.betademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.betaout.sdk.app.BetaOut;
import com.betaout.sdk.model.BOCart;
import com.betaout.sdk.model.BOOrder;
import com.betaout.sdk.model.BOProduct;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 1/9/17.
 */
public class PurchaseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PurchaseAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);



        adapter = new PurchaseAdapter(App.getProducts(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }


    public void purchase(View view){
        if(App.getProducts().size() == 0){
            Toast.makeText(this, "No Products in Bag", Toast.LENGTH_LONG).show();
        }else {


            try{
                List<Object> products = App.getProducts();
                List<BOProduct> productsList = new ArrayList<>();
                double revenue = 0;
                double discount = 0;
                for(int position = 0; position < App.getProducts().size(); position++) {
                    revenue += ((Products) products.get(position)).price;
                    BOProduct product = BOProduct.create(((Products) products.get(position)).id, ((Products) products.get(position)).price);
                    product.setProductImageUrl(((Products) products.get(position)).img);
                    product.setCostPrice(((Products) products.get(position)).cost_price);
                    product.setDiscount(((Products) products.get(position)).discount);
                    discount += ((Products) products.get(position)).discount;
                    product.setBrand(((Products) products.get(position)).brandName);
                    product.setName(((Products) products.get(position)).name);
                    product.setMargin(((Products) products.get(position)).margin);
                    product.setOldPrice(((Products) products.get(position)).old_price);
                    product.setProductGroupID(((Products) products.get(position)).product_group_id);
                    product.setProductGroupName(((Products) products.get(position)).product_group_name);
                    product.setProductUrl(((Products) products.get(position)).url);
                    product.setSku(((Products) products.get(position)).sku);
                    product.addTags(((Products) products.get(position)).tags);
                    product.addCategoryToProduct(((Products)products.get(position)).cat_id,
                            ((Products)products.get(position)).cat_name,
                            ((Products)products.get(position)).parent_cat_id);


                    String ep = ((Products) products.get(position)).event_property;
                    JSONObject epo = new JSONObject(ep);
                    JSONObject append = epo.getJSONObject("append");
                    JSONObject update = epo.getJSONObject("update");

                    Hashtable<String, String> appendHT = null;
                    Hashtable<String, String> updateHT = null;

                    Iterator<String> keys = append.keys();

                    while (keys.hasNext()) {
                        String key = keys.next();
                        appendHT = new Hashtable<>();
                        appendHT.put(key, append.getString(key));
                    }

                    JSONObject specO = new JSONObject(((Products) products.get(position)).specs);
                    Hashtable<String, String> specsHT = new Hashtable<>();
                    keys = specO.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        specsHT.put(key, specO.getString(key));
                    }
                    product.addSpecs(specsHT);

                    keys = update.keys();

                    while (keys.hasNext()) {
                        String key = keys.next();
                        updateHT = new Hashtable<>();
                        updateHT.put(key, update.getString(key));
                    }

                    productsList.add(product);



                }

                BOCart mBOCart = BOCart.create(revenue, revenue, "INR");
                BOOrder mBOOrder = BOOrder.create("Test" + System.currentTimeMillis(), revenue, revenue, "INR", "completed", "Demo Bank");// Order ID, Order Total Revenue from Cart, Currency of Purchase in String, Status, Method of Payment
                mBOOrder.setShipping(0.0);// Any shipping charges
                mBOOrder.setTax(0.0);// Any tax applicable
                mBOOrder.setDiscount(discount);// Any discount applicable

                if(((Button)view).getText().toString().equalsIgnoreCase("Complete Purchase")) {
                    mBOOrder.updateOrderStatus("Complete");
                    mBOOrder.setShipping_method("SHIPPED");
                    BetaOut.getInstance().completePurchaseOrderWithProperties(mBOOrder, mBOCart, productsList, null, null);
                }else{
                    BetaOut.getInstance().orderPlacedWithProperties(mBOOrder, mBOCart, productsList, null, null);

                }

            }catch (Exception e){

            }




            if(((Button)view).getText().toString().equalsIgnoreCase("Complete Purchase")) {
                App.clearProducts();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Purchased completed Successfully. Thanks!", Toast.LENGTH_LONG).show();
                finishAffinity();

                Intent intent = new Intent(this, SplashScreen.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Order Placed Successfully. Thanks!", Toast.LENGTH_LONG).show();
                ((Button)view).setText("Complete Purchase");
            }
        }
    }

    public void clearCart(View view){
        if(App.getProducts().size() == 0){
            Toast.makeText(this, "No Products in Bag", Toast.LENGTH_LONG).show();
        }else {
            double revenue = 0;
            for(int i = 0; i < App.getProducts().size(); i++){
                revenue += ((Products)App.getProducts().get(i)).price;
            }
            BOCart mBOCart = BOCart.create(revenue, revenue, "INR");
            BetaOut.getInstance().clearCartWithProperties(mBOCart, null);
            App.clearProducts();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Cart Cleared Successfully", Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent);
        }
    }
}
