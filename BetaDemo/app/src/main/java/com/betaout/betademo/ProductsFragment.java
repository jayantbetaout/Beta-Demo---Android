package com.betaout.betademo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.betaout.betademo.net.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 30/6/17.
 */
public class ProductsFragment extends Fragment {


    LinearLayoutManager linearLayoutManager;
    GridLayoutManager linearLayoutManager2;
    RecyclerView recyclerView;
    List<Products> products = new ArrayList<>();
    String category;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getArguments();
        category = bundle.getString("category");
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        try {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("jsonData", Context.MODE_PRIVATE);
            JSONObject jsonData = new JSONObject(sharedPreferences.getString("jsonData", ""));
            jsonData = jsonData.getJSONObject("data");
            jsonData = jsonData.getJSONObject("collections");

            JSONArray jsonArray = jsonData.getJSONArray(category);


            String cat_id = "";
            String cat_name = "";
            String parent_cat_id = "";

            if(category.equalsIgnoreCase("men")){

                cat_id = "12";
                parent_cat_id = "11";
                cat_name = "Men";

            }else if(category.equalsIgnoreCase("women")){

                cat_id = "13";
                parent_cat_id = "11";
                cat_name = "Women";

            }


            for(int i = 0; i < jsonArray.length(); i++){

                String tags = ((JSONObject)jsonArray.get(i)).getString("tags");
                JSONArray tagsArray = new JSONArray(tags);
                List<String> tagsList = new ArrayList<>();
                for(int j = 0; j < tagsArray.length(); j++){
                    tagsList.add(tagsArray.getString(j));
                }

                Products product = new Products(((JSONObject)jsonArray.get(i)).getString("name"), ((JSONObject)jsonArray.get(i)).getString("id"),
                        ((JSONObject)jsonArray.get(i)).getString("img"), ((JSONObject)jsonArray.get(i)).getString("sku"),
                        ((JSONObject)jsonArray.get(i)).getDouble("price"), ((JSONObject)jsonArray.get(i)).getDouble("cost_price"),
                        ((JSONObject)jsonArray.get(i)).getDouble("old_price"), ((JSONObject)jsonArray.get(i)).getDouble("discount"),
                        ((JSONObject)jsonArray.get(i)).getDouble("margin"), ((JSONObject)jsonArray.get(i)).getString("url"),
                        ((JSONObject)jsonArray.get(i)).getJSONObject("specs").toString(), tagsList,
                        ((JSONObject)jsonArray.get(i)).getString("event_property"), ((JSONObject)jsonArray.get(i)).getString("brandName"),
                        ((JSONObject)jsonArray.get(i)).getString("product_group_id"), ((JSONObject)jsonArray.get(i)).getString("product_group_name"),
                        cat_id, cat_name, parent_cat_id);

                products.add(product);
            }


        }catch (JSONException e){

        }

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView)inflater.inflate(R.layout.recyclerview, container, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager2 = new GridLayoutManager(getContext(), 2);

        Adapter adapter = new Adapter(products, getActivity());
        recyclerView.setAdapter(adapter);
        if(products.size() == 0) {
            recyclerView.setLayoutManager(linearLayoutManager);
        }else{
            recyclerView.setLayoutManager(linearLayoutManager2);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();

        return recyclerView;
    }


}
