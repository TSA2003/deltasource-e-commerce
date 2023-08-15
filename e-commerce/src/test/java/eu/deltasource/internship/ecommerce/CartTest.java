package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CartTest {

    @Test
    public void testAddingNullCartItemToCartIsNotPossible() {
        // Given
        CartItem cartItem = null;
        Cart cart = new Cart();
        int expectedItems = 0;

        // When
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(cartItem));

        // Then
        assertEquals(expectedItems, cart.getCartItems().stream().count());
    }

    @Test
    public void testAddingExistingCartItemIsNotPossible() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        Product product = new Product(label, price);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(product, firstQuantity);
        CartItem secondCartItem = new CartItem(product, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        int expectedItems = 1;

        // When
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(secondCartItem));

        // Then
        assertEquals(expectedItems, cart.getCartItems().stream().count());
    }

    @Test
    public void testAddCartItem() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        int expectedItems = 2;

        // When
        cart.addItem(secondCartItem);

        // Then
        assertEquals(expectedItems, cart.getCartItems().stream().count());
    }

    @Test
    public void testUpdateCartItem() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        int indexOfUpdatedItem = 1;
        int newQuantity = 4;
        int expectedQuantity = 4;

        // When
        cart.updateItem(indexOfUpdatedItem, newQuantity);

        // Then
        assertEquals(expectedQuantity, cart.getCartItems().get(indexOfUpdatedItem).getQuantity());
    }

    @Test
    public void testRemoveCartItemIsNotPossibleWithIndexGreaterThanCartItemsCount() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        int indexOfRemovedItem = 2;
        int expectedSize = 2;

        // When
        assertThrows(IllegalArgumentException.class, () -> cart.removeItem(indexOfRemovedItem));

        // Then
        assertEquals(expectedSize, cart.getCartItems().stream().count());
    }

    @Test
    public void testRemoveCartItem() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        int indexOfRemovedItem = 1;
        int expectedSize = 1;

        // When
        cart.removeItem(indexOfRemovedItem);

        // Then
        assertEquals(expectedSize, cart.getCartItems().stream().count());
    }

    @Test
    public void testCalculateTotalPriceWithoutDeliveryFee() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        boolean isDiscountUsed = false;
        BigDecimal expectedPrice = BigDecimal.valueOf(360);

        // When
        BigDecimal totalPrice = cart.calculateTotalPrice(isDiscountUsed);

        // Then
        assertTrue(expectedPrice.compareTo(totalPrice) == 0);
    }

    @Test
    public void testCalculateTotalPriceWithDeliveryFee5() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(10);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        boolean isDiscountUsed = false;
        BigDecimal expectedPrice = BigDecimal.valueOf(149);

        // When
        BigDecimal totalPrice = cart.calculateTotalPrice(isDiscountUsed);

        // Then
        assertTrue(expectedPrice.compareTo(totalPrice) == 0);
    }

    @Test
    public void testCalculateTotalPriceWithDeliveryFee10() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(50);
        Product firstProduct = new Product(firstLabel, firstPrice);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(10);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        boolean isDiscountUsed = false;
        BigDecimal expectedPrice = BigDecimal.valueOf(94);

        // When
        BigDecimal totalPrice = cart.calculateTotalPrice(isDiscountUsed);

        // Then
        assertTrue(expectedPrice.compareTo(totalPrice) == 0);
    }

    @Test
    public void testCalculateTotalPriceWithAvailableDiscount() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        LocalDate discountStart = LocalDate.now().minusDays(10);
        LocalDate discountEnd = LocalDate.now().plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        firstProduct.setDiscount(discount);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        boolean isDiscountUsed = true;
        BigDecimal expectedPrice = BigDecimal.valueOf(348);

        // When
        BigDecimal totalPrice = cart.calculateTotalPrice(isDiscountUsed);

        // Then
        assertTrue(expectedPrice.compareTo(totalPrice) == 0);
    }

    @Test
    public void testGetNumberOfSpecialItems() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        LocalDate discountStart = LocalDate.now().minusDays(10);
        LocalDate discountEnd = LocalDate.now().plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        firstProduct.setDiscount(discount);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        BigDecimal loyaltyDiscountRate = BigDecimal.valueOf(0.05);
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount(loyaltyDiscountRate);
        secondProduct.setLoyaltyDiscount(loyaltyDiscount);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        int expectedSpecialProducts = 3;

        // When
        int actualSpecialProducts = cart.getNumberOfSpecialProducts();

        // Then
        assertEquals(expectedSpecialProducts, actualSpecialProducts);
    }

    @Test
    public void testGetLoyaltyCardDiscount() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        LocalDate discountStart = LocalDate.now().minusDays(10);
        LocalDate discountEnd = LocalDate.now().plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        firstProduct.setDiscount(discount);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        BigDecimal loyaltyDiscountRate = BigDecimal.valueOf(0.05);
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount(loyaltyDiscountRate);
        secondProduct.setLoyaltyDiscount(loyaltyDiscount);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        BigDecimal expectedDiscount = BigDecimal.valueOf(12);

        // When
        BigDecimal actualDiscount = cart.getLoyaltyCardDiscount();

        // Then
        assertTrue(expectedDiscount.compareTo(actualDiscount) == 0);
    }
}
