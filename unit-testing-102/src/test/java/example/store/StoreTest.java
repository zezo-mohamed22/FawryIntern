package example.store;

import example.account.AccountManager;
import example.account.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StoreTest {


    @Test
    void buyProductWithStockofProduct() {
        // arrange
        Product product = new Product();
        Customer customer = new Customer();
        AccountManager MockaccountManager = mock(AccountManager.class);
        Store store = new StoreImpl(MockaccountManager);
        product.setQuantity(4);
        when(MockaccountManager.withdraw(customer, product.getPrice())).thenAnswer((Answer) -> "success");
        // act
        store.buy(product, customer);
        //assertion
        Assertions.assertEquals(3, product.getQuantity());
    }

    @Test
    void buyProductWithoutStockofProductWithException() {

       // arrange
        Product product = new Product();
        Customer customer = new Customer();
        AccountManager MockaccountManager = mock(AccountManager.class);
        Store store = new StoreImpl(MockaccountManager);
        // act

        product.setQuantity(0);
        //assertion

        Assertions.assertThrows(RuntimeException.class, () -> store.buy(product, customer));
    }
    @Test
    void buyProductWithoutPayment() {
        // arrange

        Product product = new Product();
        Customer customer = new Customer();
        AccountManager MockaccountManager = mock(AccountManager.class);
        Store store = new StoreImpl(MockaccountManager);
        // act
        when(MockaccountManager.withdraw(customer, product.getPrice())).thenAnswer((Answer) -> "maximum credit exceeded");
        // assertion
        Assertions.assertThrows(RuntimeException.class, () -> store.buy(product, customer));

    }

}
