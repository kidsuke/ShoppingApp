package kidsuke.shoppingapp.model;

/**
 * Created by ADMIN on 02-Feb-17.
 */

public class Product {
    private String productCode;
    private String productName;
    private float retailPrice;
    private int count;

    public Product(String productCode, String productName, String retailPrice){
        this.productCode = productCode;
        this.productName = productName;
        this.retailPrice = Float.parseFloat(retailPrice);
        this.count = 1;
    }

    public String getProductCode(){
        return  productCode;
    }

    public String getRetailPrice(){
        return Float.toString(retailPrice);
    }

    public String getProductName(){
        return productName;
    }

    public String getCount(){
        return Integer.toString(count);
    }

    public void increaseCount(){
        count++;
    }

    public void decreaseCount(){
        if (count > 0)
            count --;
    }
}
