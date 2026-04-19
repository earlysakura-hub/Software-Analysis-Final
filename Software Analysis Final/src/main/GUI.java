package main;

import model.Customer;
import model.Equipment;
import service.*;

import javax.swing.*;
import java.awt.*;
// GUI for the service
public class GUI {
    private JFrame frame;
    private JLabel label;
    private JTextArea textArea;
    private JPanel panel;
    private JButton viewEquipmentButton, viewCustomersButton, addEquipmentButton, deleteEquipmentButton, addCustomerButton, processRentalButton, homeButton, exitButton;
    private JScrollPane scrollPane;

    EquipmentService equipmentService = new EquipmentService();
    CustomerService customerService = new CustomerService();
    RentalService rentalService = new RentalService();

    // GUI
    private GUI () {
        // Creates the frame of the GUI
        frame = new JFrame();
        // Initiates some of the buttons and what they do
        viewEquipmentButton = new JButton("View All Equipment");
        viewEquipmentButton.addActionListener(click -> showEquipment());
        viewCustomersButton = new JButton("View All Customers");
        viewCustomersButton.addActionListener(click -> showCustomers());
        addEquipmentButton = new JButton("Add Equipment");
        addEquipmentButton.addActionListener(click -> showAddEquipment());
        deleteEquipmentButton = new JButton("Delete Equipment");
        deleteEquipmentButton.addActionListener(click -> showDeleteEquipment());
        addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(click -> showAddCustomer());
        processRentalButton = new JButton("Process Rental");
        processRentalButton.addActionListener(click -> showProcessRental());
        homeButton = new JButton("Home");
        homeButton.addActionListener(click -> showMainMenu());
        exitButton = new JButton("Exit");
        exitButton.addActionListener(click -> System.exit(0));
        // Adds a label when needed as title
        label = new JLabel(" ");
        // Initiates the inner panel inside the frame
        panel = new JPanel();
        // Text area box used for certain areas as needed
        textArea = new JTextArea(15,50);
        textArea.setEditable(false);
        // Makes the text area scrollable
        scrollPane = new JScrollPane(textArea);

        // Panel settings
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Displays the main menu
        showMainMenu();
        // Sets up the Frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Software Analysis Final");
        frame.pack();
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setSize(900, 650);
        frame.setVisible(true);
    }
    // Runs the GUI when program is run
    public static void main(String[] args) {
        new GUI();
    }
    // Displays the homepage menu
    private void showMainMenu() {
        // Gets rid of any previous elements inside the panel
        panel.removeAll();

        panel.add(viewEquipmentButton);
        panel.add(viewCustomersButton);
        panel.add(addEquipmentButton);
        panel.add(deleteEquipmentButton);
        panel.add(addCustomerButton);
        panel.add(processRentalButton);
        panel.add(exitButton);
        panel.revalidate(); // Figures out the layout again
        panel.repaint(); // Refreshes the GUI to ensure previous page is gone
    }
    // Displays all the equipment to the user
    private void showEquipment(){
        panel.removeAll();
        // Home button to go back
        panel.add(homeButton);
        // Label to indicate which page they're on
        panel.add(label);
        label.setText("=== All Equipment ===");
        panel.add(scrollPane);
        textArea.setText("");
        for (Equipment equipment : equipmentService.getAllEquipment()) {
            textArea.append(equipment.getEquipmentId() + " - "
                    + equipment.getName() + " - "
                    + equipment.getDailyRentalCost() + " - Rented: "
                    + (equipment.isRented() ? "Yes" : "No") + "\n"
            );
        }

        panel.revalidate();
        panel.repaint();
    }
    // Displays all the customers to the user
    private void showCustomers() {
        panel.removeAll();
        panel.add(homeButton);
        panel.add(label);
        panel.add(scrollPane);
        textArea.setText("");
        label.setText("=== All Customers ===");
        for (Customer customer : customerService.getAllCustomers()) {
            textArea.append(
                    customer.getCustomerId() + " - "
                            + customer.getFirstName() + " "
                            + customer.getLastName()
                            + " - Phone: " + customer.getContactPhone()
                            + " - Email: " + customer.getEmail()
                            + " - Banned: " + (customer.isBanned() ? "Yes" : "No") + "\n"
            );
        }

        panel.revalidate();
        panel.repaint();
    }
    // Show add Equipment form
    private void showAddEquipment() {
        panel.removeAll();
        panel.add(homeButton);
        panel.add(label);
        label.setText("=== Add Equipment ===");
        // Text input fields
        JTextField catIdField = new JTextField(20);
        JTextField equipIdField = new JTextField(20);
        JTextField equipNameField = new JTextField(20);
        JTextField descField = new JTextField(100);
        JTextField dailyRentalField = new JTextField(20);

        equipIdField.setEditable(false);
        // Updates the equipmentID upon entering categoryId and unfocusing on it
        catIdField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    int categoryId = Integer.parseInt(catIdField.getText().trim());
                    int equipmentId = equipmentService.generateEquipmentId(categoryId);
                    equipIdField.setText(String.valueOf(equipmentId));
                } catch (NumberFormatException ex) { // Sets id as nothing if category id is invalid
                    equipIdField.setText("");
                }
            }
        });
        // Sets the field box sizes
        catIdField.setMaximumSize(new Dimension(200, 20));
        equipIdField.setMaximumSize(new Dimension(200, 20));
        equipNameField.setMaximumSize(new Dimension(200, 20));
        descField.setMaximumSize(new Dimension(200, 20));
        dailyRentalField.setMaximumSize(new Dimension(200, 20));
        // Creates a separate panel to make it look more like a form
        JPanel formPanel = new JPanel();
        // Sets the layout to have every addition to be below the next
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        // Adds the labels and fields
        formPanel.add(new JLabel("Category Id:"));
        formPanel.add(catIdField);
        formPanel.add(new JLabel("Equipment Id:"));
        formPanel.add(equipIdField);
        formPanel.add(new JLabel("Equipment Name:"));
        formPanel.add(equipNameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);
        formPanel.add(new JLabel("Daily Rental Cost:"));
        formPanel.add(dailyRentalField);
        // Creates a submit button
        JButton submitButton = new JButton("Submit");
        // Tells the submit button to go to the createEquipment method on click
        submitButton.addActionListener(click -> createEquipment(
                // Passes the required parameter info to the method
                Integer.parseInt(catIdField.getText().trim()),
                Integer.parseInt(equipIdField.getText().trim()),
                equipNameField.getText(),
                descField.getText(),
                Double.parseDouble(dailyRentalField.getText().trim())
        ));
        // Adds the submit button to the form
        formPanel.add(submitButton);
        // Adds the form to the main panel
        panel.add(formPanel);

        panel.revalidate();
        panel.repaint();
    }
    // Creates the equipment and redirects user off of the page.
    private void createEquipment(int catId, int equipId, String equipName, String desc, double dailyRental) {
        Equipment newEquipment = new Equipment(
                equipId,
                equipName,
                desc,
                dailyRental,
                catId,
                false
        );
        // Provides the user at the bottom of the GUI that it was created or not
        if (equipmentService.addEquipment(newEquipment)) {
            showEquipment();
            panel.add(new JLabel("Equipment added successfully."));
        } else {
            showEquipment();
            panel.add(new JLabel("Failed to add equipment."));
        }

    }
    // Shows the delete equipment form
    private void showDeleteEquipment() {
        panel.removeAll();
        panel.add(homeButton);
        panel.add(label);
        label.setText("=== Delete Equipment ===");

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JTextField deleteIdField = new JTextField(20);
        deleteIdField.setMaximumSize(new Dimension(200, 20));
        formPanel.add(new JLabel("Equipment Id to delete: "));
        formPanel.add(deleteIdField);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(click -> deleteEquipment(Integer.parseInt(deleteIdField.getText())));
        formPanel.add(submitButton);

        panel.add(formPanel);
        panel.revalidate();
        panel.repaint();

    }
    // Deletes the equipment and redirects user, provides succession message and failure
    private void deleteEquipment(int deleteId) {
        if (equipmentService.deleteEquipment(deleteId)) {
            showEquipment();
            panel.add(new JLabel("Equipment deleted successfully."));
        } else {
            showEquipment();
            panel.add(new JLabel("Failed to delete equipment."));
        }
    }
    // Shows the add customer form
    private void showAddCustomer() {
        panel.removeAll();
        panel.add(homeButton);
        panel.add(label);
        label.setText("=== Add Customer ===");

        JTextField customerIdField = new JTextField(20);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        customerIdField.setMaximumSize(new Dimension(200, 20));
        firstNameField.setMaximumSize(new Dimension(200, 20));
        lastNameField.setMaximumSize(new Dimension(200, 20));
        phoneField.setMaximumSize(new Dimension(200, 20));
        emailField.setMaximumSize(new Dimension(200, 20));

        customerIdField.setEditable(false);
        customerIdField.setText(String.valueOf(customerService.generateCustomerId()));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.add(new JLabel("Customer Id:"));
        formPanel.add(customerIdField);
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(click -> createCustomer(
                Integer.parseInt(customerIdField.getText()),
                firstNameField.getText(),
                lastNameField.getText(),
                phoneField.getText(),
                emailField.getText()
        ));
        formPanel.add(submitButton);
        panel.add(formPanel);

        panel.revalidate();
        panel.repaint();

    }
    // Creates the customer, redirects user to another GUI page, and provides sucess message
    private void createCustomer(int customerId, String firstName, String lastName, String phone, String email) {
        Customer newCustomer = new Customer(
                customerId,
                firstName,
                lastName,
                phone,
                email,
                "",
                false
        );
        if (customerService.addCustomer(newCustomer)) {
            showCustomers();
            panel.add(new JLabel("Customer added successfully."));
        } else {
            showCustomers();
            panel.add(new JLabel("Failed to add customer."));
        }
    }
    // Shows the user process rental form
    private void showProcessRental(){
        panel.removeAll();
        panel.add(homeButton);
        panel.add(label);
        label.setText("=== Process Rental ===");

        JTextField rentalIdField = new JTextField(20);
        JTextField customerIdField = new JTextField(20);
        JTextField equipIdField = new JTextField(20);
        JTextField rentalDateField = new JTextField(20);
        JTextField returnDateField = new JTextField(20);

        rentalIdField.setMaximumSize(new Dimension(200, 20));
        customerIdField.setMaximumSize(new Dimension(200, 20));
        equipIdField.setMaximumSize(new Dimension(200, 20));
        rentalDateField.setMaximumSize(new Dimension(200, 20));
        returnDateField.setMaximumSize(new Dimension(200, 20));

        rentalIdField.setEditable(false);
        rentalIdField.setText(String.valueOf(rentalService.generateRentalId()));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.add(new JLabel("Rental Id:"));
        formPanel.add(rentalIdField);
        formPanel.add(new JLabel("Customer Id:"));
        formPanel.add(customerIdField);
        formPanel.add(new JLabel("Equipment Id:"));
        formPanel.add(equipIdField);
        formPanel.add(new JLabel("Rental Date: (yyyy-MM-dd)"));
        formPanel.add(rentalDateField);
        formPanel.add(new JLabel("Expected Return Date: (yyyy-MM-dd)"));
        formPanel.add(returnDateField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(click -> createRental(
                Integer.parseInt(rentalIdField.getText()),
                Integer.parseInt(customerIdField.getText()),
                Integer.parseInt(equipIdField.getText()),
                rentalDateField.getText(),
                returnDateField.getText()
        ));
        formPanel.add(submitButton);
        panel.add(formPanel);

        panel.revalidate();
        panel.repaint();
    }
    // Creates a rental and redirects user off page and provides a sucess message and cost
    private void createRental(int rentalId, int rentCustomerId, int rentEquipmentId, String rentalDate, String returnDate) {
        boolean rentalSuccess = rentalService.processRental(
                rentalId,
                rentCustomerId,
                rentEquipmentId,
                rentalDate,
                returnDate
        );
        panel.removeAll();
        panel.add(homeButton);
        panel.add(label);

        if (rentalSuccess) {
            panel.add(new JLabel("Rental completed. Total cost: $" + Math.round(rentalService.getRentalTotalCost(rentalId)*100.00)/100.00));
        } else {
            panel.add(new JLabel("Rental Failed."));
        }

        panel.revalidate();
        panel.repaint();
    }
}
