package example.store;


import example.account.Customer;

public interface Store {
    void buy(Product product, Customer customer);
}
