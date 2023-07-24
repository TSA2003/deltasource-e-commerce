package ecommerce.backend;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<Product>();
        ArrayList<CartItem> items = new ArrayList<CartItem>();
        ArrayList<Cart> carts = new ArrayList<Cart>();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            Product temp = new Product(Integer.toString(i), new BigDecimal(random.nextDouble(0, 100)));
            products.add(temp);
        }
        for (int i = 0; i < 10; i++) {
            CartItem temp = new CartItem(products.get(random.nextInt(50)), random.nextInt(10) + 1);
            items.add(temp);
        }
        for (int i = 0; i < 2; i++) {
            Cart temp = new Cart();
            for (int j = 0; j < 10; j++) {
                temp.addItem(items.get(random.nextInt(10)));
            }
            carts.add(temp);
        }
        for (Product item : products) {
            System.out.println(item.toString());
        }
        for (CartItem item : items) {
            System.out.println(item.toString() + " " + item.calculatePrice());
        }
        for (Cart item : carts) {
            System.out.println(item.toString() + " " + item.calculatePriceWithVAT());
        }
    }
}