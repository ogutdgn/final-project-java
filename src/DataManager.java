import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String USERS_FILE = "data/users.txt";
    private static final String CARS_FILE = "data/cars.txt";

    static {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public static void saveUsers(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (Customer customer : customers) {
                writer.write(customer.getUsername() + "|" +
                        customer.getPassword() + "|" +
                        customer.getFirstName() + "|" +
                        customer.getLastName() + "|" +
                        customer.getEmail() + "|" +
                        customer.getPhone() + "|" +
                        customer.getLicenseNumber());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public static List<Customer> loadUsers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(USERS_FILE);

        if (!file.exists()) {
            createSampleUsers();
            return loadUsers();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    Customer customer = new Customer(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            parts[5],
                            parts[6]
                    );
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }

        return customers;
    }

    private static void createSampleUsers() {
        List<Customer> sampleUsers = new ArrayList<>();

        sampleUsers.add(new Customer("john_doe", "pass123", "John", "Doe",
                "john@email.com", "1234567890", "DL123456"));
        sampleUsers.add(new Customer("jane_smith", "pass456", "Jane", "Smith",
                "jane@email.com", "0987654321", "DL789012"));
        sampleUsers.add(new Customer("mike_wilson", "pass789", "Mike", "Wilson",
                "mike@email.com", "5551234567", "DL345678"));
        sampleUsers.add(new Customer("sarah_jones", "pass321", "Sarah", "Jones",
                "sarah@email.com", "5559876543", "DL901234"));
        sampleUsers.add(new Customer("david_brown", "pass654", "David", "Brown",
                "david@email.com", "5555551234", "DL567890"));

        saveUsers(sampleUsers);
    }

    public static void saveCars(List<Car> cars) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CARS_FILE))) {
            for (Car car : cars) {
                String ownerUsername = car.getOwner() != null ? car.getOwner().getUsername() : "NONE";
                String renterUsername = car.getRenter() != null ? car.getRenter().getUsername() : "NONE";

                writer.write(car.getMake() + "|" +
                        car.getModel() + "|" +
                        car.getPlateNumber() + "|" +
                        car.getManufactureYear() + "|" +
                        car.getPrice() + "|" +
                        car.isAvailable() + "|" +
                        ownerUsername + "|" +
                        renterUsername);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving cars: " + e.getMessage());
        }
    }

    public static List<CarData> loadCars() {
        List<CarData> carsData = new ArrayList<>();
        File file = new File(CARS_FILE);

        if (!file.exists()) {
            return carsData;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(CARS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    CarData carData = new CarData();
                    carData.make = parts[0];
                    carData.model = parts[1];
                    carData.plateNumber = parts[2];
                    carData.year = Integer.parseInt(parts[3]);
                    carData.price = Integer.parseInt(parts[4]);
                    carData.available = Boolean.parseBoolean(parts[5]);
                    carData.ownerUsername = parts.length > 6 && !parts[6].equals("NONE") ? parts[6] : null;
                    carData.renterUsername = parts.length > 7 && !parts[7].equals("NONE") ? parts[7] : null;

                    carsData.add(carData);
                }
            }
        }

        catch (IOException e) {
            System.err.println("Error loading cars: " + e.getMessage());
        }

        return carsData;
    }

    public static class CarData {
        public String make;
        public String model;
        public String plateNumber;
        public int year;
        public int price;
        public boolean available;
        public String ownerUsername;
        public String renterUsername;
    }
}