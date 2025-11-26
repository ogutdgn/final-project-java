import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarManagerFrontEnd extends JFrame {
    private CarManagerSystem system = new CarManagerSystem();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> carList = new JList<>(listModel);

    public CarManagerFrontEnd() {
        setTitle("Rental Car Service");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton displayBtn = new JButton("Display Cars");
        JButton rentBtn = new JButton("Rent Car");
        JButton returnBtn = new JButton("Return Car");
        JButton addBtn = new JButton("Add Car");

        buttonPanel.add(displayBtn);
        buttonPanel.add(rentBtn);
        buttonPanel.add(returnBtn);
        buttonPanel.add(addBtn);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(carList), BorderLayout.CENTER);

        displayBtn.addActionListener(e -> updateDisplay());
        rentBtn.addActionListener(e -> {
            int idx = carList.getSelectedIndex();
            system.rentCar(idx);
            updateDisplay();
        });
        returnBtn.addActionListener(e -> {
            int idx = carList.getSelectedIndex();
            system.returnCar(idx);
            updateDisplay();
        });
        addBtn.addActionListener(e -> {
            JTextField model = new JTextField();
            JTextField year = new JTextField();
            JTextField price = new JTextField();

            Object[] fields = {
                "Model:", model,
                "Year:", year,
                "Price:", price
            };

            int result = JOptionPane.showConfirmDialog(null, fields, "Add Car", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                system.addCar(model.getText(),
                        Integer.parseInt(year.getText()),
                        Integer.parseInt(price.getText()));
                updateDisplay();
            }
        });

        system.addCar("Toyota Corolla", 2020, 60);
        system.addCar("Honda Civic", 2019, 55);
        updateDisplay();

        setVisible(true);
    }

    public void updateDisplay() {
        listModel.clear();
        for (int i = 0; i < system.getCars().size(); i++) {
            listModel.addElement(i + ". " + system.getCars().get(i));
        }
    }

    public static void main(String[] args) {
        new CarManagerFrontEnd();
    }
}
