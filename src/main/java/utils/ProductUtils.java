package utils;

import model.Product;

import java.util.ArrayList;

public class ProductUtils {

    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void loadProduct() {
        products.add(new Product(1, "Perfume - Blue de Channel", 14999));
        products.add(new Product(2, "Shoe - Puma", 1990));
        products.add(new Product(3, "Apple - M1 Air", 139999));
        products.add(new Product(4, "Arrow - Shirt", 1900));
    }
}
