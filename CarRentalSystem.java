import javax.swing.*;
import java.util.ArrayList;

// ===== Car class =====
class Car {
    private String carId;
    private String brand;
    private String model;
    private double pricePerDay;
    private boolean available;

    public Car(String carId, String brand, String model, double pricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.available = true;
    }

    public String getCarId() { return carId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return available; }

    public void rent() { available = false; }
    public void returnCar() { available = true; }
}

// ===== Customer class =====
class Customer {
    private String name;
    private int days;
    private double totalPrice;

    public Customer(String name, int days, double totalPrice) {
        this.name = name;
        this.days = days;
        this.totalPrice = totalPrice;
    }

    public String getName() { return name; }
    public int getDays() { return days; }
    public double getTotalPrice() { return totalPrice; }
}

// ===== Car Rental System =====
class CarRentalSystem {
    private ArrayList<Car> cars = new ArrayList<>();

    public void addCar(Car car) { cars.add(car); }

    public String displayAvailableCars() {
        StringBuilder sb = new StringBuilder();
        for (Car c : cars) {
            if (c.isAvailable()) {
                sb.append(c.getCarId()).append(" - ").append(c.getBrand()).append(" ")
                  .append(c.getModel()).append(" | Price/Day: ₹").append((int)c.getPricePerDay()).append("\n");
            }
        }
        if (sb.length() == 0) sb.append("No cars available right now.");
        return sb.toString();
    }

    public Car findCarById(String id) {
        for (Car c : cars) {
            if (c.getCarId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }
}

// ===== Main GUI Class =====
public class CarRentalSimpleGUI {
    public static void main(String[] args) {
        CarRentalSystem system = new CarRentalSystem();

        // Add sample cars
        system.addCar(new Car("C001", "Toyota", "Camry", 4500));
        system.addCar(new Car("C002", "Honda", "City", 4200));
        system.addCar(new Car("C003", "Mahindra", "Thar", 9000));
        system.addCar(new Car("C004", "Suzuki", "Swift", 3000));

        while (true) {
            String menu = "Car Rental System\n\n"
                    + "1. View Available Cars\n"
                    + "2. Rent a Car\n"
                    + "3. Return a Car\n"
                    + "4. Exit";
            String choiceStr = JOptionPane.showInputDialog(menu);
            if (choiceStr == null) break; // user pressed cancel
            int choice = Integer.parseInt(choiceStr);

            switch (choice) {
                case 1: // View cars
                    JOptionPane.showMessageDialog(null, system.displayAvailableCars(), "Available Cars", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case 2: // Rent car
                    String carsList = system.displayAvailableCars();
                    String carId = JOptionPane.showInputDialog("Available Cars:\n" + carsList + "\nEnter Car ID to rent:");
                    Car car = system.findCarById(carId);
                    if (car != null && car.isAvailable()) {
                        String name = JOptionPane.showInputDialog("Enter your name:");
                        String daysStr = JOptionPane.showInputDialog("For how many days you need the car?");
                        try {
                            int days = Integer.parseInt(daysStr);
                            double total = car.getPricePerDay() * days;
                            car.rent();
                            Customer customer = new Customer(name, days, total);
                            JOptionPane.showMessageDialog(null,
                                    "Car rented successfully to " + name +
                                            "\nTotal Price for " + days + " days: ₹" + (int)total);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid number of days!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Car ID or car is already rented!");
                    }
                    break;

                case 3: // Return car
                    String returnId = JOptionPane.showInputDialog("Enter Car ID to return:");
                    Car returnCar = system.findCarById(returnId);
                    if (returnCar != null && !returnCar.isAvailable()) {
                        returnCar.returnCar();
                        JOptionPane.showMessageDialog(null, "Car returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Car ID or car is already available!");
                    }
                    break;

                case 4: // Exit
                    JOptionPane.showMessageDialog(null, "Thank you for using the Car Rental System!");
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Try again.");
            }
        }
    }
}
