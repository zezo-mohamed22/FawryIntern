package Modules;

import jakarta.ejb.Singleton;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Singleton

public class storeManager {
    Set<Product> products = new TreeSet<Product>(Comparator.comparing(Product::getName));
    public storeManager(){
        Product product1 = new Product(1, "Laptop", 1200.00);
        Product product2 = new Product(2, "Smartphone", 800.00);
        Product product3 = new Product(3, "Tablet", 400.00);
        products.add(product1);
        products.add(product2);
        products.add(product3);

    }
    public String add(Product product) {
        String validProduct = validationofProduct(product);
        if (validProduct.equals("Product is added to the store")) {
            products.add(product);
        }
        return validProduct;
    }



    public String removeByName(String name) {
        Optional<Product> product = getProduct(name);
        if (product.isPresent()) {
            products.remove(product.get());
            return "Product is removed from the store";
        }
        return "Product isn't in the store";
    }

    public String updateProduct(Product newProduct) {
        String message = validationofProduct(newProduct);

        if (message.equals("Product is already in the store")) {
            Optional<Product> oldProduct = getProduct(newProduct.getName());

            if (oldProduct.isEmpty()) return "This product doesn't exist in store";

            products.remove(oldProduct.get());
            products.add(newProduct);

            return "Product has been updated successfully";
        }
        return message;
    }
    public Optional<Product> searchByName(String name) {
        Optional<Product> product = getProduct(name);
        return product;
    }
    public Optional<Product> searchById(int id) {
        Optional<Product> product = getProduct(id);
        return product;
    }
    private String validationofProduct(Product product) {
        String name = product.getName();
        double price = product.getPrice();
        if (name.isEmpty()) {
            return "Name should not be empty";
        }
        if (price < 0) {
            return "Price should not be negative";
        }
        if (name.length() > 100) {
            return "Name should not exceed 100 characters";
        }
        if (products.contains(product)) {
            return "Product is already in the store";
        }
        return "Product is added to the store";
    }

    public Optional<Product> getProduct(String name) {
        return products.stream().filter(product -> product.getName().equals(name)).findFirst();
    }

    public Optional<Product> getProduct(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }
    public Set<Product> getProducts() {
        return products;
    }
}
