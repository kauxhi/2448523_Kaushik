import java.util.*;

class CounterEmptyException extends Exception {
    public CounterEmptyException(String message) {
        super(message);
    }
}

class CoffeeShop {
    public final Queue<String> counter = new LinkedList<>();
    public final int MAX_CAPACITY = 3;

    public synchronized void producer(String coffee, String baristaName) {
        while (counter.size() == MAX_CAPACITY) {
            try {
                System.out.println(baristaName + " is waiting. Counter is full.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        counter.add(coffee);
        System.out.println(baristaName + " prepared coffee. Counter: " + counter.size());
        notifyAll();
    }

    public synchronized String consumer(String customerName) throws CounterEmptyException {
        while (counter.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (counter.isEmpty()) {
            throw new CounterEmptyException(customerName + " tried to pick up coffee, but the counter is empty!");
        }
        String coffee = counter.poll();
        System.out.println(customerName + " picked up coffee. Counter: " + counter.size());
        notifyAll();
        return coffee;
    }

    public synchronized String reviewer(String reviewerName) throws CounterEmptyException {
        while (counter.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (counter.isEmpty()) {
            throw new CounterEmptyException(reviewerName + " tried to review coffee, but the counter is empty!");
        }
        String coffee = counter.poll();
        System.out.println(reviewerName + " sampled coffee. Counter: " + counter.size());
        notifyAll();
        return coffee;
    }
}

public class CoffeeShopOrderEnhancement {
    public static void main(String[] args) {
        CoffeeShop coffeeShop = new CoffeeShop();
        Scanner scanner = new Scanner(System.in);

        // Get inputs for baristas
        System.out.print("Enter the number of baristas: ");
        int numBaristas = scanner.nextInt();
        Map<Integer, Integer> baristaTasks = new HashMap<>();
        for (int i = 1; i <= numBaristas; i++) {
            System.out.print("Enter the number of coffees Barista " + i + " will prepare: ");
            baristaTasks.put(i, scanner.nextInt());
        }

        System.out.print("Enter the number of customers: ");
        int numCustomers = scanner.nextInt();
        Map<Integer, Integer> customerTasks = new HashMap<>();
        for (int i = 1; i <= numCustomers; i++) {
            System.out.print("Enter the number of coffees Customer " + i + " will pick up: ");
            customerTasks.put(i, scanner.nextInt());
        }

        System.out.print("Enter the number of reviewers: ");
        int numReviewers = scanner.nextInt();
        Map<Integer, Integer> reviewerTasks = new HashMap<>();
        for (int i = 1; i <= numReviewers; i++) {
            System.out.print("Enter the number of coffees Reviewer " + i + " will sample: ");
            reviewerTasks.put(i, scanner.nextInt());
        }

        List<Thread> threads = new ArrayList<>();

        baristaTasks.forEach((id, taskCount) -> {
            threads.add(new Thread(() -> {
                for (int i = 0; i < taskCount; i++) {
                    coffeeShop.producer("Coffee from Barista " + id, "Barista " + id);
                }
            }, "Barista " + id));
        });

        // Customer threads (consumer)
        customerTasks.forEach((id, taskCount) -> {
            threads.add(new Thread(() -> {
                for (int i = 0; i < taskCount; i++) {
                    try {
                        coffeeShop.consumer("Customer " + id);
                    } catch (CounterEmptyException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }, "Customer " + id));
        });

        // Reviewer threads (observer)
        reviewerTasks.forEach((id, taskCount) -> {
            threads.add(new Thread(() -> {
                for (int i = 0; i < taskCount; i++) {
                    try {
                        coffeeShop.reviewer("Reviewer " + id);
                    } catch (CounterEmptyException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }, "Reviewer " + id));
        });

        threads.stream().filter(t -> t.getName().contains("Barista")).forEach(Thread::start);

        try {
            Thread.sleep(100); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        threads.stream().filter(t -> t.getName().contains("Customer")).forEach(Thread::start);

        threads.stream().filter(t -> t.getName().contains("Reviewer")).forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        scanner.close();
    }
}