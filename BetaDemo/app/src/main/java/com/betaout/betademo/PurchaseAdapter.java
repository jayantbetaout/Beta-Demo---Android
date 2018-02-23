package com.betaout.betademo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;

/**
 * Created by root on 1/9/17.
 */
public class PurchaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Object> products;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader imageLoader;

    public PurchaseAdapter(List<Object> products, Context context) {
        this.products = products;
        this.context = context;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_shopping_bag, parent, false));
        else
            return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.not_available, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        try {
            switch (holder.getItemViewType()) {
                case 0:
                    final ViewHolder viewHolder0 = (ViewHolder)holder;

                    viewHolder0.name.setText(((Products)products.get(position)).name);
                    viewHolder0.imgPageImage.setImageUrl(((Products)products.get(position)).img, imageLoader);
                    viewHolder0.quantity.setText("Quantity : 1");
                    viewHolder0.remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                BOProduct product = BOProduct.create(((Products) products.get(position)).id, ((Products) products.get(position)).price);
                                product.setProductImageUrl(((Products) products.get(position)).img);
                                product.setCostPrice(((Products) products.get(position)).cost_price);
                                product.setDiscount(((Products) products.get(position)).discount);
                                product.setBrand(((Products) products.get(position)).brandName);
                                product.setName(((Products) products.get(position)).name);
                                product.setMargin(((Products) products.get(position)).margin);
                                product.setOldPrice(((Products) products.get(position)).old_price);
                                product.setProductGroupID(((Products) products.get(position)).product_group_id);
                                product.setProductGroupName(((Products) products.get(position)).product_group_name);
                                product.setProductUrl(((Products) products.get(position)).url);
                                product.setSku(((Products) products.get(position)).sku);
                                product.addCategoryToProduct(((Products) products.get(position)).cat_id, ((Products) products.get(position)).cat_name,
                                        ((Products) products.get(position)).parent_cat_id);
                                //product.addTags(((Products) products.get(position)).tags);

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

                                BOCart mBOCart = BOCart.create(((Products) products.get(position)).price, ((Products) products.get(position)).price, "INR");

                                BetaOut.getInstance().removeProductsForCartWithProperties(product, mBOCart, updateHT);

                            }catch (Exception e){

                            }


                            App.removeProducts(products.get(position));
                            notifyDataSetChanged();
                            Toast.makeText(context, "Product Removed Successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                    viewHolder0.price.setText("Price : " + ((Products)products.get(position)).price + "");
                    viewHolder0.cardPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {

                                BOProduct product = BOProduct.create(((Products)products.get(position)).id, ((Products)products.get(position)).price);
                                product.setProductImageUrl(((Products)products.get(position)).img);
                                product.setCostPrice(((Products)products.get(position)).cost_price);
                                product.setDiscount(((Products)products.get(position)).discount);
                                product.setBrand(((Products)products.get(position)).brandName);
                                product.setName(((Products)products.get(position)).name);
                                product.setMargin(((Products)products.get(position)).margin);
                                product.setOldPrice(((Products)products.get(position)).old_price);
                                product.setProductGroupID(((Products)products.get(position)).product_group_id);
                                product.setProductGroupName(((Products)products.get(position)).product_group_name);
                                product.setProductUrl(((Products)products.get(position)).url);
                                product.setSku(((Products)products.get(position)).sku);
                                product.addTags(((Products)products.get(position)).tags);
                                product.addCategoryToProduct(((Products)products.get(position)).cat_id,
                                        ((Products)products.get(position)).cat_name,
                                        ((Products)products.get(position)).parent_cat_id);

                                String ep = ((Products)products.get(position)).event_property;
                                JSONObject epo = new JSONObject(ep);
                                JSONObject append = epo.getJSONObject("append");
                                JSONObject update = epo.getJSONObject("update");

                                Hashtable<String, String> appendHT = null;
                                Hashtable<String, String> updateHT = null;

                                Iterator<String> keys = append.keys();

                                while(keys.hasNext()) {
                                    String key = keys.next();
                                    appendHT = new Hashtable<>();
                                    appendHT.put(key, append.getString(key));
                                }

                                JSONObject specO = new JSONObject(((Products)products.get(position)).specs);
                                Hashtable<String, String> specsHT = new Hashtable<>();
                                keys = specO.keys();
                                while (keys.hasNext()){
                                    String key = keys.next();
                                    specsHT.put(key, specO.getString(key));
                                }
                                product.addSpecs(specsHT);

                                keys = update.keys();

                                while(keys.hasNext()) {
                                    String key = keys.next();
                                    updateHT = new Hashtable<>();
                                    updateHT.put(key, update.getString(key));
                                }

                                BetaOut.getInstance().viewProductsWithProperties(product, updateHT);

                                Intent intent = new Intent(context, ProductDetails.class);
                                intent.putExtra("product", ((Products)products.get(position)));
                                context.startActivity(intent);

                            }catch (Exception e){
                                Log.e("AppInfo", e.toString());
                            }

                        }
                    });

                    break;
                case 1:
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public int getItemViewType(int position) {
        if(products.size() == 0){
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if(products.size() == 0)
            return 1;
        else
            return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView imgPageImage;
        TextView name;
        TextView quantity, price;
        //TextView viewProduct;
        CardView cardPage;
        Button remove;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPageImage = (NetworkImageView) itemView.findViewById(R.id.imgPageImage);
            name = (TextView) itemView.findViewById(R.id.name);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            price = (TextView) itemView.findViewById(R.id.price);
            remove = (Button) itemView.findViewById(R.id.remove);

            //viewProduct = (TextView) itemView.findViewById(R.id.cart);
            cardPage = (CardView) itemView.findViewById(R.id.cardPage);

        }


    }


    public class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder2(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);

        }



    }


}

