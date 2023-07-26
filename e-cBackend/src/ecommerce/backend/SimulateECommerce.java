package ecommerce.backend;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class SimulateECommerce {
    public static void run() {
        ArrayList<Product> products = new ArrayList<Product>();
        ArrayList<CartItem> items = new ArrayList<CartItem>();
        ArrayList<Cart> carts = new ArrayList<Cart>();

        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            Product temp = new Product(Integer.toString(i), new BigDecimal(String.format("%d.%d", random.nextInt(100), random.nextInt(100))));
            products.add(temp);
        }

        for (int i = 0; i < 20; i++) {
            CartItem temp = new CartItem(products.get(random.nextInt(50)), random.nextInt(10) + 1);
            items.add(temp);
        }

        for (int i = 0; i < 2; i++) {
            Cart temp = new Cart();
            for (int j = 0; j < 10; j++) {
                try {
                    temp.addItem(items.get(random.nextInt(20)));
                }
                catch (IllegalArgumentException e) {
                    System.out.println("OK");
                    j--;
                }
            }
            carts.add(temp);
        }

        for (Product item : products) {
            System.out.println(item.toString());
        }

        for (CartItem item : items) {
            System.out.println(item.toString() + " Total Price: " + String.format("%.2f", item.calculatePrice()));
        }

        for (Cart item : carts) {
            System.out.println(item.toString() + "Total Price: " + String.format("%.2f", item.calculateTotalPriceWithDeliveryFee()));
        }
    }
}
