package com.betaout.betademo;

/**
 * Created by root on 30/6/17.
 */
public class Constants {

    public interface EndPoints {
        String DATA_FETCH_URL = "https://stag.betaout.in/beta_demo";
    }

    public static final String jsonData = "{\"data\":{\"categories\":[{\"cat_name\":\"Men\",\"cat_id\":\"12\",\"parent_cat_id\":\"11\"}, {\"cat_name\":\"Women\",\"cat_id\":\"13\",\"parent_cat_id\":\"11\"}, {\"cat_name\":\"Clothing\",\"cat_id\":\"11\",\"parent_cat_id\":\"\"}],\"collections\":{\"Men\":[{\"name\":\"Off White Solid Regular Fit Formal Shirt\",\"id\":\"21\",\"sku\":\"VA131MA32XBZINDFAS\",\"price\":1800,\"cost_price\":2999,\"old_price\":2999,\"discount\":40.13,\"margin\":123.33,\"img\":\"http://static2.jassets.com/p/Van-Heusen-Off-White-Solid-Regular-Fit-Formal-Shirt-1800-767200003-1-pdp_slider_m.jpg\",\"url\":\"http://www.jabong.com/Van_Heusen-Off-White-Solid-Regular-Fit-Formal-Shirt-300002767.html\",\"specs\":{\"color\":\"Off White\",\"size\":\"XL\",\"Fit\":\"Regular\",\"Color\":\"Off White\",\"Sleeves\":\"Full Sleeves\",\"Fabric\":\"Silk\",\"Wash Care\":\"Normal Wash, Use Only Branded Detergent\",\"Style\":\"Solid\",\"Model Stats\":\"Model is wearing size 40 and his height is 6'1, chest 36 and waist 30'5.\"},\"tags\":[\"Clothing\",\"Shirts\",\"Formal Shirts\"],\"event_property\":{\"append\":{\"FormalShirtsViewed\":\"http://www.jabong.com/Van_Heusen-Off-White-Solid-Regular-Fit-Formal-Shirt-300002767.html\"},\"update\":{\"FormalShirtsSize\":\"XL\"}},\"brandName\":\"Van Heusen\",\"product_group_id\":\"324\",\"product_group_name\":\"SALE\"},{\"name\":\"Aqua Blue Checked Slim Fit Formal Shirt\",\"id\":\"22\",\"sku\":\"PA830MA74MFTINDFAS\",\"price\":1740,\"cost_price\":2899,\"old_price\":2899,\"discount\":40.13,\"margin\":420.33,\"img\":\"http://static1.jassets.com/p/Park-Avenue-Aqua-Blue-Checked-Slim-Fit-Formal-Shirt-8979-5270982-1-pdp_slider_m.jpg\",\"url\":\"http://www.jabong.com/park-avenue-Aqua-Blue-Checked-Slim-Fit-Formal-Shirt-2890725.html\",\"specs\":{\"color\":\"Aqua Blue\",\"size\":\"XL\",\"Fit\":\"Slim\",\"Color\":\"Aqua Blue\",\"Neck\":\"COLLAR\",\"Sleeves\":\"Full Sleeves\",\"Fabric\":\"Linen\",\"Wash Care\":\"Normal Wash, Use Only Branded Detergent\",\"Style\":\"Checked\",\"Model Stats\":\"Model is wearing size M and his height is 6'1, chest 40 and waist 32.\"},\"tags\":[\"Clothing\",\"Shirts\",\"Formal Shirts\"],\"event_property\":{\"append\":{\"FormalShirtsViewed\":\"http://www.jabong.com/park-avenue-Aqua-Blue-Checked-Slim-Fit-Formal-Shirt-2890725.html\"},\"update\":{\"FormalShirtsSize\":\"XL\"}},\"brandName\":\"Park Avenue\",\"product_group_id\":\"324\",\"product_group_name\":\"SALE\"},{\"name\":\"Brown Checked Slim Fit Formal Shirt\",\"id\":\"24\",\"sku\":\"SKUPE185MA60XJIINDFAS\",\"price\":840,\"cost_price\":1399,\"old_price\":1399,\"discount\":40.23,\"margin\":40.23,\"img\":\"http://static2.jassets.com/p/Peter-England-Brown-Checked-Slim-Fit-Formal-Shirt-5142-672390003-1-pdp_slider_m.jpg\",\"url\":\"http://www.jabong.com/Peter_England-Brown-Checked-Slim-Fit-Formal-Shirt-300093276.html\",\"specs\":{\"size\":\"XL\",\"Fit\":\"Slim\",\"Color\":\"Brown\",\"Sleeves\":\"Full Sleeves\",\"Fabric\":\"Cotton\",\"Wash Care\":\"Machine Wash, Do Not Bleach, Do Not Tumble Dry, Cool Iron, Dry Clean\",\"Style\":\"Checked\",\"Model Stats\":\"Model is wearing size 40 and his height is 6'0, chest 37 and waist 32.\"},\"tags\":[\"Clothing\",\"Shirts\",\"Formal Shirts\"],\"event_property\":{\"append\":{\"FormalShirtsViewed\":\"http://www.jabong.com/Peter_England-Brown-Checked-Slim-Fit-Formal-Shirt-300093276.html\"},\"update\":{\"FormalShirtsSize\":\"XL\"}},\"brandName\":\"Peter England\",\"product_group_id\":\"324\",\"product_group_name\":\"SALE\"}],\"Women\":[{\"name\":\"Off White Solid Regular Fit Formal Shirt\",\"id\":\"21\",\"sku\":\"VA131MA32XBZINDFAS\",\"price\":1800,\"cost_price\":2999,\"old_price\":2999,\"discount\":40.13,\"margin\":123.33,\"img\":\"http://static2.jassets.com/p/Van-Heusen-Off-White-Solid-Regular-Fit-Formal-Shirt-1800-767200003-1-pdp_slider_m.jpg\",\"url\":\"http://www.jabong.com/Van_Heusen-Off-White-Solid-Regular-Fit-Formal-Shirt-300002767.html\",\"specs\":{\"color\":\"Off White\",\"size\":\"XL\",\"Fit\":\"Regular\",\"Color\":\"Off White\",\"Sleeves\":\"Full Sleeves\",\"Fabric\":\"Silk\",\"Wash Care\":\"Normal Wash, Use Only Branded Detergent\",\"Style\":\"Solid\",\"Model Stats\":\"Model is wearing size 40 and his height is 6'1, chest 36 and waist 30'5.\"},\"tags\":[\"Clothing\",\"Shirts\",\"Formal Shirts\"],\"event_property\":{\"append\":{\"FormalShirtsViewed\":\"http://www.jabong.com/Van_Heusen-Off-White-Solid-Regular-Fit-Formal-Shirt-300002767.html\"},\"update\":{\"FormalShirtsSize\":\"XL\"}},\"brandName\":\"Van Heusen\",\"product_group_id\":\"324\",\"product_group_name\":\"SALE\"},{\"name\":\"Aqua Blue Checked Slim Fit Formal Shirt\",\"id\":\"22\",\"sku\":\"PA830MA74MFTINDFAS\",\"price\":1740,\"cost_price\":2899,\"old_price\":2899,\"discount\":40.13,\"margin\":420.33,\"img\":\"http://static1.jassets.com/p/Park-Avenue-Aqua-Blue-Checked-Slim-Fit-Formal-Shirt-8979-5270982-1-pdp_slider_m.jpg\",\"url\":\"http://www.jabong.com/park-avenue-Aqua-Blue-Checked-Slim-Fit-Formal-Shirt-2890725.html\",\"specs\":{\"color\":\"Aqua Blue\",\"size\":\"XL\",\"Fit\":\"Slim\",\"Color\":\"Aqua Blue\",\"Neck\":\"COLLAR\",\"Sleeves\":\"Full Sleeves\",\"Fabric\":\"Linen\",\"Wash Care\":\"Normal Wash, Use Only Branded Detergent\",\"Style\":\"Checked\",\"Model Stats\":\"Model is wearing size M and his height is 6'1, chest 40 and waist 32.\"},\"tags\":[\"Clothing\",\"Shirts\",\"Formal Shirts\"],\"event_property\":{\"append\":{\"FormalShirtsViewed\":\"http://www.jabong.com/park-avenue-Aqua-Blue-Checked-Slim-Fit-Formal-Shirt-2890725.html\"},\"update\":{\"FormalShirtsSize\":\"XL\"}},\"brandName\":\"Park Avenue\",\"product_group_id\":\"324\",\"product_group_name\":\"SALE\"},{\"name\":\"Brown Checked Slim Fit Formal Shirt\",\"id\":\"24\",\"sku\":\"SKUPE185MA60XJIINDFAS\",\"price\":840,\"cost_price\":1399,\"old_price\":1399,\"discount\":40.23,\"margin\":40.23,\"img\":\"http://static2.jassets.com/p/Peter-England-Brown-Checked-Slim-Fit-Formal-Shirt-5142-672390003-1-pdp_slider_m.jpg\",\"url\":\"http://www.jabong.com/Peter_England-Brown-Checked-Slim-Fit-Formal-Shirt-300093276.html\",\"specs\":{\"size\":\"XL\",\"Fit\":\"Slim\",\"Color\":\"Brown\",\"Sleeves\":\"Full Sleeves\",\"Fabric\":\"Cotton\",\"Wash Care\":\"Machine Wash, Do Not Bleach, Do Not Tumble Dry, Cool Iron, Dry Clean\",\"Style\":\"Checked\",\"Model Stats\":\"Model is wearing size 40 and his height is 6'0, chest 37 and waist 32.\"},\"tags\":[\"Clothing\",\"Shirts\",\"Formal Shirts\"],\"event_property\":{\"append\":{\"FormalShirtsViewed\":\"http://www.jabong.com/Peter_England-Brown-Checked-Slim-Fit-Formal-Shirt-300093276.html\"},\"update\":{\"FormalShirtsSize\":\"XL\"}},\"brandName\":\"Peter England\",\"product_group_id\":\"324\",\"product_group_name\":\"SALE\"}]}}}";

}
