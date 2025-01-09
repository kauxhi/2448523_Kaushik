import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

class Customer implements Comparable<Customer> {
    public String customerId;
    public String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer(customerId=" + customerId + ", name=" + name + ")";
    }

    @Override
    public int compareTo(Customer otherCustomer) {
        return this.getCustomerId().compareTo(otherCustomer.getCustomerId());
    }
}

class Product {
    public String productId;
    public String name;
    public double price;

    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product(productId=" + productId + ", name=" + name + ", price=" + price + ")";
    }
}

class Order {
    public String orderId;
    public Customer customer;
    public ArrayList<Product> products = new ArrayList<>();

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Order(orderId=" + orderId + ", customer=" + customer + ", products=" + products + ")";
    }
}

public class Lab7 {
    public static ArrayList<Customer> customerList = new ArrayList<>();
    public static HashMap<String, Product> productMap = new HashMap<>();
    public static HashSet<Product> uniqueProducts = new HashSet<>();
    public static TreeSet<Customer> sortedCustomers = new TreeSet<>();
    public static ArrayList<Order> orderList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Amazon CRM Menu: ");
            System.out.println("1. Insert Customer Details");
            System.out.println("2. Insert Product Details");
            System.out.println("3. Place a Order");
            System.out.println("4. View Customers in sorted order");
            System.out.println("5. View all Products");
            System.out.println("6. View all Orders");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addCustomer(scanner);
                case 2 -> addProduct(scanner);
                case 3 -> placeOrder(scanner);
                case 4 -> viewCustomers();
                case 5 -> viewProducts();
                case 6 -> viewOrders();
                case 7 -> {
                    System.out.println("Exiting the Menu. Thank You!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void addCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        Customer customer = new Customer(customerId, name);
        customerList.add(customer);
        sortedCustomers.add(customer);

        System.out.println("Customer added successfully!");
    }

    public static void addProduct(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();

        Product product = new Product(productId, name, price);
        productMap.put(productId, product);
        uniqueProducts.add(product);

        System.out.println("Product added successfully!");
    }

    public static void placeOrder(Scanner scanner) {
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();

        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        Order order = new Order(orderId, customer);
        while (true) {
            System.out.print("Enter Product ID (or 'done' to finish): ");
            String productId = scanner.nextLine();
            if (productId.equalsIgnoreCase("done")) {
                break;
            }

            Product product = productMap.get(productId);
            if (product != null) {
                order.getProducts().add(product);
            } else {
                System.out.println("Product not found!");
            }
        }
        orderList.add(order);

        System.out.println("Order placed successfully!");
    }

    public static void viewCustomers() {
        System.out.println("\nSorted Customers:");
        for (Customer customer : sortedCustomers) {
            System.out.println(customer);
        }
    }

    public static void viewProducts() {
        System.out.println("\nList of Products:");
        for (Product product : productMap.values()) {
            System.out.println(product);
        }
    }

    public static void viewOrders() {
        System.out.println("\nOrders placed:");
        for (Order order : orderList) {
            System.out.println(order);
        }
    }

    public static Customer findCustomerById(String customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
}