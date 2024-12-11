package com.fawry;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        functionalInterfaceAndLamdaExpression();

        forEach();

        streamsExamples();

        optional();

    }

    private static void functionalInterfaceAndLamdaExpression() {

        //normal flow -> create class implements the object and instaniating from class
        ExampleInterface ex = new ExampleClasss();
        ex.exampleMethod("from concrete class");

        //anonymous class
        ExampleInterface ex2 = new ExampleInterface() {
            @Override
            public void exampleMethod(String msg) {
                System.out.println(msg);
            }
        };
        ex2.exampleMethod("from anonymous class");

        //lamda expression
        ExampleInterface ex3 = msg -> System.out.println(msg);
        ex3.exampleMethod("from lamda expression");

        //lamda expression with method refernce
        ExampleInterface ex4 = System.out::println;
        ex4.exampleMethod("from lamda expression");

        ExampleInterface ex5 = ExampleInterface::staticMethod;

        ExampleInterface ex6 = String::format;

        Thread thread = new Thread(() -> System.out.println("in thread"));

        //there are some ready functional interfaces in java
        //consumer takes argument and returns nothing
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("consumed value");

        //supplir takes no argument but gives a value
        Supplier<String> supplier = () -> "supplied value";
        System.out.println(supplier.get());

        //predicate takes a parameter and returns a boolean
        Predicate<String> predicate = x -> x.length() == 10;
        System.out.println(predicate.test("test value"));

        //function takes a parameter and returns a value
        Function<String, Integer> function = str -> str.length();
        System.out.println(function.apply("test string"));
    }

    private static void forEach() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5);

        //normal for loop
        for (int i = 0; i < nums.size(); i++) {
            System.out.println(nums.get(i));
        }

        //enhanced for loop
        for(Integer num : nums) {
            System.out.println(num);
        }

        //foreach method is now present in Iterable interface which is extended by collection interface
        nums.forEach(x -> System.out.println(x));
    }

    private static void optional() {
        OrderRepository orderRepository = new OrderRepository();

//        Order order = orderRepository.findOrderById(19);
//        System.out.println(order.getCustomerName());    //NullPointerException
//
        Optional<Order> orderOptional = orderRepository.findOrderByIdWithOptional(19);
        if(orderOptional.isPresent()) {
            System.out.println(orderOptional.get().getItems());
        }

        //you can give some operation to be done if order is present
        orderOptional.ifPresent( x -> System.out.println(x.getId()) );

        //you can give a default value to use if order is not present
        Order order = orderRepository.findOrderByIdWithOptional(19).orElse(new Order());

        //you can throw an exception if item is not present
        Order order2 = orderRepository.findOrderByIdWithOptional(19).orElseThrow(() -> new RuntimeException("item not found"));
    }

    private static void streamsExamples() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9,10,10);

        //filter even numbers and return in a new list
        List<Integer> evenNumbers = nums.stream()
                .filter(num -> num % 2 == 0)
                .collect(Collectors.toList());
        System.out.println(evenNumbers);

        //filter even numbers and return in a new list
        Set<Integer> evenNumbersWithoutDuplicates = nums.stream()
                .filter(num -> num % 2 == 0)
                .collect(Collectors.toSet());
        System.out.println(evenNumbersWithoutDuplicates);

        //double the even number and get total
        int sumOfEvenNumbersUsingForLoop = 0;
        for (int i = 0; i < nums.size(); i++) {
            if(nums.get(i) % 2 == 0){
                sumOfEvenNumbersUsingForLoop += nums.get(i);
            }
        }
        System.out.println(sumOfEvenNumbersUsingForLoop);

        //double the even number and get total using stream
        int sumOfEvenNumbersUsingStream = nums.stream()
                .filter(num -> num % 2 == 0)
                .map(num -> num * 2)
                .reduce(0, (total, num) -> total + num);
        System.out.println(sumOfEvenNumbersUsingStream);

        //double the even number and get total with intstream
        int sumOfEvenNumbersUsingIntStream = nums.stream()
                .filter(num -> num % 2 == 0)
                .map(num -> num * 2)
                .mapToInt(num -> num)
                .sum();
        System.out.println(sumOfEvenNumbersUsingIntStream);


        //count of even numbers
        long evenNumbersCount = nums.stream()
                .filter(num -> num % 2 == 0)
                .count();
        System.out.println(evenNumbersCount);


        //first number greater than 3 if none found return 0
        int result = nums.stream()
                .filter(num -> num > 3 && num % 2 ==0)
                .findFirst()
                .orElse(0);
        System.out.println(result);


        OrderRepository orderRepository = new OrderRepository();
        List<Order> orders = orderRepository.findAll();

        //cearte Hashmap with id as key and items as value
        Map<Integer, List<Item>> map = orders.stream()
                .collect(Collectors.toMap(order -> order.getId(), order -> order.getItems()));
        System.out.println(map);


        //get List Of All orders items
        List<Item> items = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toList());
        System.out.println(items);


        //streams are lazy these intermediate operations will not be executed until we add terminal operations
        Stream<Integer> evenNumbersDoubledStream = nums.stream()
                .filter(num -> isEven(num))
                .map(number -> doubleInt(number));
//        evenNumbersDoubledStream.collect(Collectors.toList());
    }



    public static boolean isEven (Integer num){
        System.out.println(num + " isEven is executing");
        return num % 2 == 0;
    }

    public static Integer doubleInt (Integer num){
        System.out.println(num + " doubleInt is executing");
        return num  * 2;
    }

}