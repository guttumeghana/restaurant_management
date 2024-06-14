import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface MenuItem {
    String getName();
    String getDescription();
    double getPrice();
}

class RestaurantMenuItem implements MenuItem {//RestaurantMenuItem class implements the MenuItem interface.
    private String name;
    private String description;
    private double price;

    public RestaurantMenuItem(String name, String description, double price) {//parametrised constructor 
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }
}

interface Order {
    void addItem(MenuItem item);
    void removeItem(MenuItem item);
    double calculateTotalPrice();
    List<MenuItem> getItems();//gets all the items in the order as a list.
}

class RestaurantOrder implements Order {//for customers order
    private List<MenuItem> items = new ArrayList<>();

    @Override
    public void addItem(MenuItem item) {
        items.add(item);
    }

    @Override
    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    @Override
    public double calculateTotalPrice() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public List<MenuItem> getItems() {
        return items;
    }
}

interface RestaurantSystem {
    Order createOrder();
    List<MenuItem> getMenuItems();
}

class RestaurantOrderSystem implements RestaurantSystem {
    private List<MenuItem> menuItems = new ArrayList<>();

    @Override
    public Order createOrder() {
        return new RestaurantOrder();
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public void removeMenuItem(MenuItem item) {
        menuItems.remove(item);
    }
}

public class Main {
    public static void main(String[] args) {
        RestaurantOrderSystem restaurantSystem = new RestaurantOrderSystem();
        restaurantSystem.addMenuItem(new RestaurantMenuItem("Burger", "Delicious burger with cheese", 5.99));//parameterised constructor
        restaurantSystem.addMenuItem(new RestaurantMenuItem("Pizza", "Tasty pizza with pepperoni", 8.99));
        restaurantSystem.addMenuItem(new RestaurantMenuItem("Salad", "Fresh salad with dressing", 4.99));

        Scanner scanner = new Scanner(System.in);

        // Display menu items
        System.out.println("Menu Items:");
        List<MenuItem> menuItems = restaurantSystem.getMenuItems();
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);//keyword for list
            System.out.println((i + 1) + ". " + item.getName() + " - " + item.getDescription() + " - $" + item.getPrice());
        }

        // Create order
        Order order = restaurantSystem.createOrder();

        // Prompt user to add or remove items from the order
        boolean modifyingOrder = true;
        while (modifyingOrder) {
            System.out.println("\nEnter 1 to add an item, 2 to remove an item, 3 to display all the in the order, or 0 to finish:");
            int choice = scanner.nextInt();//reads the text along with new line
            switch (choice) {
                case 1:
                    // Add item to order
                    System.out.println("\nMenu Items:");
                    for (int i = 0; i < menuItems.size(); i++) {
                        MenuItem item = menuItems.get(i);
                        System.out.println((i + 1) + ". " + item.getName() + " - " + item.getDescription() + " - $" + item.getPrice());
                    }
                    System.out.println("Enter the number of the item to add to the order:");
                    int addItemChoice = scanner.nextInt();
                    if (addItemChoice >= 1 && addItemChoice <= menuItems.size()) {
                        order.addItem(menuItems.get(addItemChoice - 1));
                        System.out.println("Item added to the order.");
                    } else {
                        System.out.println("Invalid choice. Please enter a valid number.");
                    }
                    break;
                case 2:
                    // Remove item from order
                    List<MenuItem> items = order.getItems();
                    System.out.println("\nItems in the order:");
                    for (int i = 0; i < items.size(); i++) {
                        MenuItem item = items.get(i);
                        System.out.println((i + 1) + ". " + item.getName() + " - " + item.getDescription() + " - $" + item.getPrice());
                    }
                    System.out.println("Enter the number of the item to remove from the order:");
                    int removeItemChoice = scanner.nextInt();
                    if (removeItemChoice >= 1 && removeItemChoice <= items.size()) {
                        order.removeItem(items.get(removeItemChoice - 1));
                        System.out.println("Item removed from the order.");
                    } else {
                        System.out.println("Invalid choice. Please enter a valid number.");
                    }
                    break;
                case 3:
                    //Display the items present in the order
                    List<MenuItem> itemss = order.getItems();
                    System.out.println("\nItems in the order:");
                    for (int i = 0; i < itemss.size(); i++) {
                        MenuItem item = itemss.get(i);
                        System.out.println((i + 1) + ". " + item.getName() + " - " + item.getDescription() + " - $" + item.getPrice());
                    }
                    break;
                case 0:
                    modifyingOrder = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 0.");
                    break;
            }
        }

        // Display items in the order
        System.out.println("\nItems in the order:");
        for (MenuItem item : order.getItems()) {
            System.out.println(item.getName() + " - " + item.getDescription() + " - $" + item.getPrice());
        }

        // Display total price
        System.out.println("\nTotal price: $" + order.calculateTotalPrice());

        scanner.close();
    }
}