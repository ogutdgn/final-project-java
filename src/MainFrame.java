import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CarService carService = new CarService();
    private final CustomerService customerService = new CustomerService();
    private final RentalService rentalService = new RentalService(carService);

    private JLabel userLabel;

    private JButton loginBtn;

    private final DefaultListModel<String> rentListModel = new DefaultListModel<>();
    private final JList<String> rentCarList = new JList<>(rentListModel);

    private final DefaultListModel<String> returnListModel = new DefaultListModel<>();
    private final JList<String> returnCarList = new JList<>(returnListModel);

    private final DefaultListModel<String> myCarsListModel = new DefaultListModel<>();
    private final JList<String> myCarsList = new JList<>(myCarsListModel);

    public MainFrame() {
        setTitle("🚗 Rental Car Service");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(new Color(245, 245, 245));

        initializeData();

        createHeaderPanel();
        createTabbedPane();

        updateAllTabs();
        updateUserLabel();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeData() {
        java.util.Map<String, Customer> customerMap = new java.util.HashMap<>();
        for (Customer customer : customerService.getAllCustomers()) {
            customerMap.put(customer.getUsername(), customer);
        }

        carService.setCustomerMap(customerMap);
        carService.loadCars();

        rentalService.reconstructRentals();

        if (carService.getCarCount() == 0) {
            carService.addCar("Toyota", "Corolla", "ABC-1234", 2020, 60);
            carService.addCar("Honda", "Civic", "XYZ-5678", 2019, 55);
            carService.addCar("BMW" ,"3 Series", "BMW-9012", 2022, 120);
            carService.addCar("Mercedes-Benz", "C-Class", "MER-3456", 2021, 150);
        }
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(800, 70));

        JLabel titleLabel = new JLabel("Car Rental Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(new Color(41, 128, 185));
        userLabel = new JLabel("Guest");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        loginBtn = new JButton("👤 Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 12));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(new Color(52, 152, 219));
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> handleLoginLogout());

        userPanel.add(userLabel);
        userPanel.add(Box.createHorizontalStrut(10));
        userPanel.add(loginBtn);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("🔑 Rent Car", createRentCarPanel());
        tabbedPane.addTab("↩️ Return Car", createReturnCarPanel());
        tabbedPane.addTab("➕ Add Car", createAddCarPanel());
        tabbedPane.addTab("🚗 My Cars", createMyCarsPanel());

        tabbedPane.addChangeListener(e -> updateAllTabs());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createRentCarPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Available Cars for Rent");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        rentCarList.setFont(new Font("Monospaced", Font.PLAIN, 13));
        rentCarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(rentCarList);

        JButton rentBtn = createStyledButton("Rent Selected Car", new Color(46, 204, 113));
        rentBtn.addActionListener(e -> handleRentCar());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(rentBtn);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createReturnCarPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("My Rented Cars");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        returnCarList.setFont(new Font("Monospaced", Font.PLAIN, 13));
        returnCarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(returnCarList);

        JButton returnBtn = createStyledButton("Return Selected Car", new Color(241, 196, 15));
        returnBtn.addActionListener(e -> handleReturnCar());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(returnBtn);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAddCarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Add Your Car");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 30, 10);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        JComboBox<CarMake> makeBox = new JComboBox<>(CarMake.values());
        JComboBox<String> modelBox = new JComboBox<>();
        JTextField plateField = new JTextField(25);
        JTextField yearField = new JTextField(25);
        JTextField priceField = new JTextField(25);

        int row = 1;
        gbc.gridy = row++;
        gbc.gridx = 0;
        panel.add(createLabel("Make:"), gbc);
        gbc.gridx = 1;
        panel.add(makeBox, gbc);

        gbc.gridy = row++;
        gbc.gridx = 0;
        panel.add(createLabel("Model:"), gbc);
        gbc.gridx = 1;
        panel.add(modelBox, gbc);

        gbc.gridy = row++;
        gbc.gridx = 0;
        panel.add(createLabel("Plate Number:"), gbc);
        gbc.gridx = 1;
        panel.add(plateField, gbc);

        gbc.gridy = row++;
        gbc.gridx = 0;
        panel.add(createLabel("Year:"), gbc);
        gbc.gridx = 1;
        panel.add(yearField, gbc);

        gbc.gridy = row++;
        gbc.gridx = 0;
        panel.add(createLabel("Price per day ($):"), gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        gbc.gridy = row++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        JButton addBtn = createStyledButton("Add Car", new Color(155, 89, 182));
        addBtn.setPreferredSize(new Dimension(200, 45));

        // Populate modelBox when make changes
// When make changes
        makeBox.addActionListener(e -> {
            modelBox.removeAllItems();

            Object sel = makeBox.getSelectedItem();
            CarMake selected = sel instanceof CarMake ? (CarMake) sel : null;

            if (selected != null) {
                if (selected == CarMake.Other) {
                    makeBox.setEditable(true);
                    modelBox.setEditable(true);
                    modelBox.addItem(""); // blank option for custom model
                } else {
                    makeBox.setEditable(false);

                    // Add all predefined models
                    for (String m : selected.getModels()) {
                        modelBox.addItem(m);
                    }

                    // Add blank at top so user can type a new model
                    modelBox.insertItemAt("", 0);
                    modelBox.setSelectedIndex(0);

                    // Editable only if blank selected
                    modelBox.setEditable(true);
                }
            } else {
                makeBox.setEditable(true);
                modelBox.setEditable(true);
                modelBox.addItem(""); // fallback
            }
        });

// Single listener for modelBox to lock typing on predefined models
        modelBox.addActionListener(e -> {
            Object item = modelBox.getSelectedItem();
            if (item != null && !"".equals(item.toString())) {
                modelBox.setEditable(false); // lock if not blank
            } else {
                modelBox.setEditable(true);  // editable if blank
            }
        });





        JTextField customMake = new JTextField(25);
        JTextField customModel = new JTextField(25);
        addBtn.addActionListener(e -> {
            if (!customerService.isLoggedIn()) {
                showError("Please login first to add a car!");
                handleLoginLogout();
                return;
            }

            try {
                Object sel = makeBox.getSelectedItem();
                String make = "";

                if (sel instanceof CarMake) {
                    make = ((CarMake) sel).toString();  // predefined enum
                } else if (sel != null) {
                    make = sel.toString();              // user-typed string
                }


                String model;
                if (modelBox.isEditable()) {
                    // user typed a custom model for "Other"
                    model = modelBox.getEditor().getItem().toString().trim();
                } else {
                    model = (String) modelBox.getSelectedItem();
                }

                String plate = plateField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                int price = Integer.parseInt(priceField.getText().trim());

                if (model == null || model.isEmpty() || plate.isEmpty()) {
                    showError("Model and Plate Number cannot be empty!");
                    return;
                }

                carService.addCar(make, model, plate, year, price, customerService.getLoggedInCustomer());

                // Reset after adding
                modelBox.removeAllItems();
                makeBox.setSelectedIndex(0);
                plateField.setText("");
                yearField.setText("");
                priceField.setText("");

                updateAllTabs();
                showSuccess("Car added successfully!");

            } catch (NumberFormatException ex) {
                showError("Please enter valid numbers for year and price!");
            }
        });

        panel.add(addBtn, gbc);

        return panel;
    }

    private JPanel createMyCarsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Cars I've Added");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        myCarsList.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(myCarsList);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton availableBtn = createStyledButton("Make Available", new Color(46, 204, 113));
        availableBtn.addActionListener(e -> handleMakeAvailable());

        JButton notAvailableBtn = createStyledButton("Make Not Available", new Color(241, 196, 15));
        notAvailableBtn.addActionListener(e -> handleMakeNotAvailable());

        JButton deleteBtn = createStyledButton("Delete", new Color(231, 76, 60));
        deleteBtn.addActionListener(e -> handleDeleteCar());

        buttonPanel.add(availableBtn);
        buttonPanel.add(notAvailableBtn);
        buttonPanel.add(deleteBtn);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void handleRentCar() {
        if (!customerService.isLoggedIn()) {
            showError("Please login first to rent a car!");
            handleLoginLogout();
            return;
        }

        int idx = rentCarList.getSelectedIndex();
        if (idx == -1) {
            showError("Please select a car to rent!");
            return;
        }

        try {
            java.util.List<Car> availableCars = carService.getAvailableCars();
            java.util.List<Car> rentableCars = new java.util.ArrayList<>();
            for (Car car : availableCars) {
                if (car.getOwner() == null ||
                        !car.getOwner().equals(customerService.getLoggedInCustomer())) {
                    rentableCars.add(car);
                }
            }

            if (idx >= rentableCars.size()) {
                showError("Invalid car selection!");
                updateAllTabs();
                return;
            }

            Car selectedCar = rentableCars.get(idx);

            if (!selectedCar.isAvailable()) {
                showError("This car is no longer available!");
                updateAllTabs();
                return;
            }

            int carIndex = carService.getAllCars().indexOf(selectedCar);

            Car rentedCar = carService.rentCar(carIndex, customerService.getLoggedInCustomer());

            rentalService.createRental(rentedCar, customerService.getLoggedInCustomer());

            updateAllTabs();
            showSuccess("Car rented successfully to " + customerService.getLoggedInCustomer().getFullName() + "!");
        } catch (CarNotAvailableException ex) {
            showError(ex.getMessage());
            updateAllTabs();
        } catch (Exception ex) {
            showError("Error renting car: " + ex.getMessage());
            ex.printStackTrace();
            updateAllTabs();
        }
    }

    private void handleReturnCar() {
        if (!customerService.isLoggedIn()) {
            showError("Please login first!");
            return;
        }

        int idx = returnCarList.getSelectedIndex();
        if (idx == -1) {
            showError("Please select a car to return!");
            return;
        }

        try {
            java.util.List<Rental> activeRentals = rentalService.getActiveRentalsByCustomer(
                    customerService.getLoggedInCustomer());
            Rental selectedRental = activeRentals.get(idx);

            rentalService.completeRental(selectedRental);
            updateAllTabs();
            showSuccess("Car returned successfully!");
        } catch (Exception ex) {
            showError("Error returning car: " + ex.getMessage());
        }
    }

    private void handleLoginLogout() {
        if (customerService.isLoggedIn()) {

            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                customerService.logout();
                updateUserLabel();
                updateAllTabs();
                loginBtn.setText("👤 Login");  // ← UPDATE BUTTON TEXT
                showSuccess("Logged out successfully!");
            }

        } else {
            LoginDialog dialog = new LoginDialog(this, customerService);
            dialog.setVisible(true);

            updateUserLabel();
            updateAllTabs();

            if (customerService.isLoggedIn()) {
                loginBtn.setText("🔓 Logout"); // ← UPDATE BUTTON TEXT
            }
        }
    }


    private void updateUserLabel() {
        if (customerService.isLoggedIn()) {
            Customer customer = customerService.getLoggedInCustomer();
            userLabel.setText("👤 " + customer.getFullName());
        } else {
            userLabel.setText("Guest");
        }
    }

    private void updateAllTabs() {
        updateRentCarTab();
        updateReturnCarTab();
        updateMyCarsTab();
    }

    private void updateRentCarTab() {
        rentListModel.clear();
        java.util.List<Car> availableCars = carService.getAvailableCars();

        java.util.List<Car> rentableCars = new java.util.ArrayList<>();
        for (Car car : availableCars) {
            if (!customerService.isLoggedIn() ||
                    car.getOwner() == null ||
                    !car.getOwner().equals(customerService.getLoggedInCustomer())) {
                rentableCars.add(car);
            }
        }

        for (int i = 0; i < rentableCars.size(); i++) {
            rentListModel.addElement((i + 1) + ". " + rentableCars.get(i));
        }

        if (rentableCars.isEmpty()) {
            rentListModel.addElement("No cars available for rent");
        }
    }

    private void updateReturnCarTab() {
        returnListModel.clear();

        if (!customerService.isLoggedIn()) {
            returnListModel.addElement("Please login to view your rented cars");
            return;
        }

        java.util.List<Rental> activeRentals = rentalService.getActiveRentalsByCustomer(
                customerService.getLoggedInCustomer());

        for (int i = 0; i < activeRentals.size(); i++) {
            Rental rental = activeRentals.get(i);
            returnListModel.addElement((i + 1) + ". " + rental.getCar().getMake() + " " + rental.getCar().getModel() +
                    " [" + rental.getCar().getPlateNumber() + "] - Rented on: " +
                    rental.getRentalDate());
        }

        if (activeRentals.isEmpty()) {
            returnListModel.addElement("You have no rented cars");
        }
    }

    private void updateMyCarsTab() {
        myCarsListModel.clear();

        if (!customerService.isLoggedIn()) {
            myCarsListModel.addElement("Please login to view your cars");
            return;
        }

        java.util.List<Car> myCars = carService.getCarsByOwner(customerService.getLoggedInCustomer());

        for (int i = 0; i < myCars.size(); i++) {
            myCarsListModel.addElement((i + 1) + ". " + myCars.get(i));
        }

        if (myCars.isEmpty()) {
            myCarsListModel.addElement("You haven't added any cars yet");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleMakeAvailable() {
        if (!customerService.isLoggedIn()) {
            showError("Please login first!");
            return;
        }

        int idx = myCarsList.getSelectedIndex();
        if (idx == -1) {
            showError("Please select a car to make available!");
            return;
        }

        java.util.List<Car> myCars = carService.getCarsByOwner(customerService.getLoggedInCustomer());
        if (idx >= myCars.size()) {
            showError("Invalid car selection!");
            return;
        }

        Car selectedCar = myCars.get(idx);

        if (selectedCar.getRenter() != null) {
            showError("Cannot change availability of a car that is currently rented!");
            return;
        }

        if (selectedCar.isAvailable()) {
            showError("This car is already available!");
            return;
        }

        carService.makeAvailable(selectedCar);
        updateAllTabs();
        showSuccess("Car is now available for rent!");
    }

    private void handleMakeNotAvailable() {
        if (!customerService.isLoggedIn()) {
            showError("Please login first!");
            return;
        }

        int idx = myCarsList.getSelectedIndex();
        if (idx == -1) {
            showError("Please select a car to make not available!");
            return;
        }

        java.util.List<Car> myCars = carService.getCarsByOwner(customerService.getLoggedInCustomer());
        if (idx >= myCars.size()) {
            showError("Invalid car selection!");
            return;
        }

        Car selectedCar = myCars.get(idx);

        if (selectedCar.getRenter() != null) {
            showError("Cannot change availability of a car that is currently rented!");
            return;
        }

        if (!selectedCar.isAvailable()) {
            showError("This car is already not available!");
            return;
        }

        carService.makeNotAvailable(selectedCar);
        updateAllTabs();
        showSuccess("Car is now not available for rent!");
    }

    private void handleDeleteCar() {
        if (!customerService.isLoggedIn()) {
            showError("Please login first!");
            return;
        }

        int idx = myCarsList.getSelectedIndex();
        if (idx == -1) {
            showError("Please select a car to delete!");
            return;
        }

        java.util.List<Car> myCars = carService.getCarsByOwner(customerService.getLoggedInCustomer());
        if (idx >= myCars.size()) {
            showError("Invalid car selection!");
            return;
        }

        Car selectedCar = myCars.get(idx);

        if (selectedCar.getRenter() != null) {
            showError("Cannot delete a car that is currently rented!");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this car?\n" + selectedCar.getMake() + " " + selectedCar.getModel(),
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            carService.removeCar(selectedCar);
            updateAllTabs();
            showSuccess("Car deleted successfully!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}