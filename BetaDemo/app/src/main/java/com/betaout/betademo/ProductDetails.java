package com.betaout.betademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.betaout.betademo.net.VolleySingleton;
import com.betaout.sdk.app.BetaOut;
import com.betaout.sdk.model.BOCart;
import com.betaout.sdk.model.BOProduct;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by root on 31/8/17.
 */
public class ProductDetails extends AppCompatActivity {

    Products product;
    TextView name, brand, id, groupId, groupName, costPrice, margin, discount, oldPrice, url,
            properties, sku, tags, specs, price, nameH;
    NetworkImageView imageView;
    
    Button cart;

    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader imageLoader;

    LinearLayout container;

    TextView cartTotal;


    @Override
    protected void onResume() {
        super.onResume();
        cartTotal.setText(App.getProducts().size() + "");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);



        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        imageLoader = volleySingleton.getImageLoader();

        product = (Products) getIntent().getSerializableExtra("product");

        name = (TextView) findViewById(R.id.productName);
        nameH = (TextView) findViewById(R.id.name);
        brand = (TextView) findViewById(R.id.productBrand);
        id = (TextView) findViewById(R.id.productId);
        groupId = (TextView) findViewById(R.id.productGroupId);
        groupName = (TextView) findViewById(R.id.productGroupName);
        costPrice = (TextView) findViewById(R.id.productCostPrice);
        margin = (TextView) findViewById(R.id.productMargin);
        discount = (TextView) findViewById(R.id.productDiscount);
        oldPrice = (TextView) findViewById(R.id.productOldPrice);
        url = (TextView) findViewById(R.id.productUrl);
        properties = (TextView) findViewById(R.id.productEventProperty);
        sku = (TextView) findViewById(R.id.productSKU);
        tags = (TextView) findViewById(R.id.productTags);
        specs = (TextView) findViewById(R.id.productSpecs);
        price = (TextView) findViewById(R.id.productPrice);
        cartTotal = (TextView) findViewById(R.id.cartTotal);
        imageView = (NetworkImageView) findViewById(R.id.productImage);
        cart = (Button) findViewById(R.id.addToCart);
        container = (LinearLayout) findViewById(R.id.container);

        cartTotal.setText(App.getProducts().size() + "");

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(!App.addProducts((Object)product)){
                        Toast.makeText(ProductDetails.this,
                                "Demo app allows only 1 quantity for a particular product though there is no boundation",
                                Toast.LENGTH_LONG).show();
                    }else {
                        Log.i("AppInfo", "added");
                        Toast.makeText(ProductDetails.this, "Product Added Successfully", Toast.LENGTH_LONG).show();
                        cartTotal.setText(App.getProducts().size() + "");
                        BOProduct productB = BOProduct.create(product.id, product.price);
                        productB.setProductImageUrl(product.img);
                        productB.setCostPrice(product.cost_price);
                        productB.setDiscount(product.discount);
                        productB.setBrand(product.brandName);
                        productB.setName(product.name);
                        productB.setMargin(product.margin);
                        productB.setOldPrice(product.old_price);
                        productB.setProductGroupID(product.product_group_id);
                        productB.setProductGroupName(product.product_group_name);
                        productB.setProductUrl(product.url);
                        productB.setSku(product.sku);
                        productB.addTags(product.tags);
                        productB.addCategoryToProduct(product.cat_id, product.cat_name,
                                product.parent_cat_id);

                        String ep = product.event_property;
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

                        JSONObject specO = new JSONObject(product.specs);
                        Hashtable<String, String> specsHT = new Hashtable<>();
                        keys = specO.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            specsHT.put(key, specO.getString(key));
                        }
                        productB.addSpecs(specsHT);

                        keys = update.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            updateHT = new Hashtable<>();
                            updateHT.put(key, update.getString(key));
                        }


                        BOCart mBOCart = BOCart.create(product.price, product.price, "INR"); // Cart Total Value, Revenue from Cart, Currency of Purchase in String
                        BetaOut.getInstance().addProductsToCartWithProperties(productB, mBOCart, updateHT);
                    }
                }catch (Exception e){
                    Log.i("AppInfo", e.toString());
                }
            }
        });

        imageView.setImageUrl(product.img, imageLoader);

        name.setText("Product Name : " + product.name);
        nameH.setText(product.name);
        brand.setText("Product Brand : " + product.brandName);
        id.setText("Product ID : " + product.id);
        groupId.setText("Product Group ID : " + product.product_group_id);
        groupName.setText("Product Group Name : " + product.product_group_name);
        costPrice.setText("Product Cost Price : " + product.cost_price + "");
        margin.setText("Product Margin : " + product.margin + "");
        discount.setText("Product Discount : " + product.discount + "");
        oldPrice.setText("Product Old Price : " + product.old_price + "");
        url.setText("Product URL : " + product.url);
        properties.setText("Product Properties : " + product.event_property);
        sku.setText("Product SKU : " + product.sku);
        tags.setText("Product Tags : " + product.tags.toString());
        specs.setText("Product Specs : " + product.specs);
        price.setText("Product Price : " + product.price + "");

        container.setVisibility(View.GONE);

        /*try {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (Build.VERSION.SDK_INT > 23) {
                if (pm.isIgnoringBatteryOptimizations(packageName)) {
                    //intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                } else {
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                }
            }
        }catch (Exception e){

        }*/

    }

    public void bag(View view){
        Intent intent = new Intent(this, PurchaseActivity.class);
        startActivity(intent);
    }
}
