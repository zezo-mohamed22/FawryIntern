package example.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class accountTest {
    AccountManager accountManager = new AccountManagerImpl();

    @Test
    void addMoneytoCustomerSuccess() {
        //Arrange
        Customer customer = new Customer();
        //act
        accountManager.deposit(customer, 50);
        //assert
        Assertions.assertEquals(customer.getBalance(), 50);
    }

    @Test
    void setCustomerBalanceSuccess() {
        //Arrange
        Customer customer = new Customer();
        //act
        customer.setBalance(100);
        String message = accountManager.withdraw(customer, 50);
        //assert
        Assertions.assertEquals("success", message);
        Assertions.assertEquals(customer.getBalance(), 50);
    }

    @Test
    void setCustomerBalancewithdraAndNonCreditAllowed() {
        //Arrange
        Customer customer = new Customer();
        //act
        customer.setBalance(100);
        customer.setCreditAllowed(false);
        String message = accountManager.withdraw(customer, 50);
        //assert
        Assertions.assertEquals("insufficient account balance",message);
        Assertions.assertEquals(customer.getBalance(), 50);
    }
    @Test
    void setCustomerBalancewithdraAndCreditAllowed() {
        Customer customer = new Customer();
        customer.setBalance(100);
        customer.setCreditAllowed(true);
        String message = accountManager.withdraw(customer, 50);
        Assertions.assertEquals("success", message);
        Assertions.assertEquals(customer.getBalance(), 50);
    }
    @Test
    void setCustomerBalancewithdraAndCreditAllowedAndExeededMaxLimit(){
        Customer customer = new Customer();
        customer.setBalance(100);
        customer.setCreditAllowed(true);
        String message = accountManager.withdraw(customer, 1101);
        Assertions.assertEquals("maximum credit exceeded", message);
        Assertions.assertEquals(customer.getBalance(), 100);
    }
    @Test
    void setCustomerBalancewithdraAndCreditAllowedAndExeededWithVip(){
        Customer customer = new Customer();
        customer.setBalance(100);
        customer.setCreditAllowed(true);
        customer.setVip(true);
        String message = accountManager.withdraw(customer, 1101);
        Assertions.assertEquals("success", message);
        Assertions.assertEquals(customer.getBalance(), -101);
    }

}

