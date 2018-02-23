package com.betaout.betademo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.betaout.betademo.net.VolleySingleton;
import com.betaout.sdk.app.BetaOut;
import com.betaout.sdk.model.BOProduct;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 30/6/17.
 */
public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Products> products;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ImageLoader imageLoader;

    public Adapter(List<Products> products, Context context) {
        this.products = products;
        this.context = context;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_select, parent, false));
        else
            return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.not_available, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        try {
            switch (holder.getItemViewType()) {
                case 0:
                    final ViewHolder viewHolder0 = (ViewHolder)holder;

                    viewHolder0.name.setText(products.get(position).name);
                    viewHolder0.id.setText(products.get(position).id);
                    viewHolder0.imgPageImage.setImageUrl(products.get(position).img, imageLoader);
                    viewHolder0.cardPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {

                                BOProduct product = BOProduct.create(products.get(position).id, products.get(position).price);
                                product.setProductImageUrl(products.get(position).img);
                                product.setCostPrice(products.get(position).cost_price);
                                product.setDiscount(products.get(position).discount);
                                product.setBrand(products.get(position).brandName);
                                product.setName(products.get(position).name);
                                product.setMargin(products.get(position).margin);
                                product.setOldPrice(products.get(position).old_price);
                                product.setProductGroupID(products.get(position).product_group_id);
                                product.setProductGroupName(products.get(position).product_group_name);
                                product.setProductUrl(products.get(position).url);
                                product.setSku(products.get(position).sku);
                                product.addTags(products.get(position).tags);
                                product.addCategoryToProduct(products.get(position).cat_id,
                                        products.get(position).cat_name,
                                        products.get(position).parent_cat_id);

                                String ep = products.get(position).event_property;
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

                                JSONObject specO = new JSONObject(products.get(position).specs);
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
                                intent.putExtra("product", products.get(position));
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
        TextView id;
        //TextView viewProduct;
        CardView cardPage;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPageImage = (NetworkImageView) itemView.findViewById(R.id.imgPageImage);
            name = (TextView) itemView.findViewById(R.id.name);
            id = (TextView) itemView.findViewById(R.id.id);
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
