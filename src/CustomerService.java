import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private ArrayList<Customer> customers = new ArrayList<>();
    private Customer loggedInCustomer = null;

    public CustomerService() {
        loadCustomers();
    }

    public Customer findCustomerByUsername(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equalsIgnoreCase(username)) {
                return c;
            }
        }
        return null;
    }


    private void loadCustomers() {
        customers.clear();
        customers.addAll(DataManager.loadUsers());
    }

    private void saveCustomers() {
        DataManager.saveUsers(customers);
    }

    public void registerCustomer(String username, String password, String firstName, String lastName,
                                 String email, String phone, String licenseNumber) throws InvalidInputException {
        for (Customer customer : customers) {
            if (customer.getUsername().equalsIgnoreCase(username)) {
                throw new InvalidInputException("Username already exists!");
            }
        }

        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("Username cannot be empty!");
        }
        if (password == null || password.length() < 4) {
            throw new InvalidInputException("Password must be at least 4 characters!");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidInputException("First name cannot be empty!");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidInputException("Last name cannot be empty!");
        }

        Customer customer = new Customer(username, password, firstName, lastName, email, phone, licenseNumber);
        customers.add(customer);
        saveCustomers();
    }

    public Customer login(String username, String password) throws InvalidInputException {
        for (Customer customer : customers) {
            if (customer.getUsername().equalsIgnoreCase(username) &&
                    customer.getPassword().equals(password)) {
                loggedInCustomer = customer;
                return customer;
            }
        }
        throw new InvalidInputException("Invalid username or password!");
    }

    public void logout() {
        loggedInCustomer = null;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public boolean isLoggedIn() {
        return loggedInCustomer != null;
    }

    public void updateProfile(String firstName, String lastName, String email,
                              String phone, String licenseNumber) throws InvalidInputException {
        if (loggedInCustomer == null) {
            throw new InvalidInputException("No user logged in!");
        }

        loggedInCustomer.setFirstName(firstName);
        loggedInCustomer.setLastName(lastName);
        loggedInCustomer.setEmail(email);
        loggedInCustomer.setPhone(phone);
        loggedInCustomer.setLicenseNumber(licenseNumber);
        saveCustomers();
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
}