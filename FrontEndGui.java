import javax.swing.*;
import java.awt.*;


public class FrontEndGui
{

    public static void main(String[] args)
    {
        // Create the main frame
        JFrame frame = new JFrame("Rental Car Service");
        frame.setSize(700, 700);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        JLabel nameLabel = new JLabel("Welcome to Rental Car Service!");
        nameLabel.setBounds(700, 700, 1000, 100);
        frame.add(nameLabel);

        // Create a "File" menu
        JMenu fileMenu = new JMenu("File");

        // Create menu items
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add actions to the menu items
        openItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Open selected"));
        saveItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Save selected"));
        exitItem.addActionListener(e -> System.exit(0));

        // Add items to the File menu
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Add File menu to menu bar
        menuBar.add(fileMenu);

        // Attach menu bar to the frame
        frame.setJMenuBar(menuBar);

        // Show the frame
        frame.setVisible(true);
    }
}
