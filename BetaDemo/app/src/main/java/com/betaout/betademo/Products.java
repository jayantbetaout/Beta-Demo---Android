package com.betaout.betademo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 30/6/17.
 */
public class Products implements Serializable {

    String name;
    String id;
    String sku;
    double price;
    double cost_price;
    double old_price;
    double discount;
    double margin;
    String img;
    String url;
    String specs;
    List<String> tags;
    String event_property;
    String brandName;
    String product_group_id;
    String product_group_name;
    String cat_id;
    String cat_name;
    String parent_cat_id;

    public Products(String name, String id, String img, String sku, double price,
                    double cost_price, double old_price, double discount, double margin,
                    String url, String specs, List<String> tags, String event_property,
                    String brandName, String product_group_id, String product_group_name, String cat_id, String cat_name, String parent_cat_id){
        this.name = name;
        this.id = id;
        this.img = img;
        this.sku = sku;
        this.price = price;
        this.cost_price = cost_price;
        this.old_price = old_price;
        this.discount = discount;
        this.margin = margin;
        this.url = url;
        this.specs = specs;
        this.tags = tags;
        this.event_property = event_property;
        this.brandName = brandName;
        this.product_group_id = product_group_id;
        this.product_group_name = product_group_name;
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.parent_cat_id = parent_cat_id;
        
    }

}
