import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private CustomerService customerService;
    private Customer loggedInCustomer = null;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField licenseField;
    
    private JPanel loginPanel;
    private JPanel registerPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public LoginDialog(Frame parent, CustomerService customerService) {
        super(parent, "Login / Sign Up", true);
        this.customerService = customerService;
        
        setSize(500, 650);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(500, 70));
        JLabel titleLabel = new JLabel("🔐 Account Access");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createLoginPanel();
        createRegisterPanel();
        
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        togglePanel.setBackground(new Color(245, 245, 245));
        JButton toggleBtn = new JButton("Don't have an account? Sign Up");
        toggleBtn.setForeground(new Color(41, 128, 185));
        toggleBtn.setBorderPainted(false);
        toggleBtn.setContentAreaFilled(false);
        toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        
        toggleBtn.addActionListener(e -> {
            if (toggleBtn.getText().contains("Sign Up")) {
                cardLayout.show(mainPanel, "REGISTER");
                toggleBtn.setText("Already have an account? Login");
            } else {
                cardLayout.show(mainPanel, "LOGIN");
                toggleBtn.setText("Don't have an account? Sign Up");
            }
        });
        
        togglePanel.add(toggleBtn);
        add(togglePanel, BorderLayout.SOUTH);
    }
    
    private void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 30, 15, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(createLabel("Username:"), gbc);
        
        gbc.gridy = 1;
        loginPanel.add(usernameField, gbc);
        
        gbc.gridy = 2;
        loginPanel.add(createLabel("Password:"), gbc);
        
        gbc.gridy = 3;
        loginPanel.add(passwordField, gbc);
        
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 30, 10, 30);
        JButton loginBtn = createStyledButton("Login", new Color(46, 204, 113));
        loginBtn.addActionListener(e -> handleLogin());
        loginPanel.add(loginBtn, gbc);
        
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        loginPanel.add(Box.createVerticalStrut(20), gbc);
    }
    
    private void createRegisterPanel() {
        registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        
        JTextField regUsernameField = new JTextField(20);
        JPasswordField regPasswordField = new JPasswordField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        licenseField = new JTextField(20);
        
        int row = 0;
        gbc.gridy = row++;
        registerPanel.add(createLabel("Username:"), gbc);
        gbc.gridy = row++;
        registerPanel.add(regUsernameField, gbc);
        
        gbc.gridy = row++;
        registerPanel.add(createLabel("Password:"), gbc);
        gbc.gridy = row++;
        registerPanel.add(regPasswordField, gbc);
        
        gbc.gridy = row++;
        registerPanel.add(createLabel("First Name:"), gbc);
        gbc.gridy = row++;
        registerPanel.add(firstNameField, gbc);
        
        gbc.gridy = row++;
        registerPanel.add(createLabel("Last Name:"), gbc);
        gbc.gridy = row++;
        registerPanel.add(lastNameField, gbc);
        
        gbc.gridy = row++;
        registerPanel.add(createLabel("Email:"), gbc);
        gbc.gridy = row++;
        registerPanel.add(emailField, gbc);
        
        gbc.gridy = row++;
        registerPanel.add(createLabel("Phone:"), gbc);
        gbc.gridy = row++;
        registerPanel.add(phoneField, gbc);
        
        gbc.gridy = row++;
        registerPanel.add(createLabel("License Number:"), gbc);
        gbc.gridy = row++;
        registerPanel.add(licenseField, gbc);
        
        gbc.gridy = row++;
        gbc.insets = new Insets(20, 30, 20, 30);
        JButton signUpBtn = createStyledButton("Sign Up", new Color(155, 89, 182));
        signUpBtn.addActionListener(e -> handleRegister(
            regUsernameField.getText(),
            new String(regPasswordField.getPassword()),
            firstNameField.getText(),
            lastNameField.getText(),
            emailField.getText(),
            phoneField.getText(),
            licenseField.getText()
        ));
        registerPanel.add(signUpBtn, gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 1.0;
        registerPanel.add(Box.createVerticalStrut(20), gbc);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
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
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        try {
            loggedInCustomer = customerService.login(username, password);
            JOptionPane.showMessageDialog(this, 
                "Welcome back, " + loggedInCustomer.getFullName() + "!",
                "Login Successful", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleRegister(String username, String password, String firstName, 
                               String lastName, String email, String phone, String license) {
        try {
            customerService.registerCustomer(username, password, firstName, 
                                            lastName, email, phone, license);
            loggedInCustomer = customerService.login(username, password);
            JOptionPane.showMessageDialog(this, 
                "Account created successfully!\nWelcome, " + loggedInCustomer.getFullName() + "!",
                "Registration Successful", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
}
