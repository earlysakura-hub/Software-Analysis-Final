package service;

import file.FileManager;
import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private static final String FILE_PATH = "Software Analysis Final/data/customers.csv";
    private FileManager fileManager = new FileManager();

    public boolean addCustomer(Customer customer) {
        try {
            String note = customer.getNote() == null ? "" : customer.getNote();

            String line = customer.getCustomerId() + ","
                    + customer.getLastName() + ","
                    + customer.getFirstName() + ","
                    + customer.getContactPhone() + ","
                    + customer.getEmail() + ","
                    + note + ","
                    + customer.isBanned();

            fileManager.appendLine(FILE_PATH, line);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer getCustomerById(int customerId) {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);

        for (String[] row : rows) {
            if (Integer.parseInt(row[0]) == customerId) {
                String note = row.length > 5 ? row[5] : "";
                boolean isBanned = row.length > 6 && Boolean.parseBoolean(row[6]);

                return new Customer(
                        Integer.parseInt(row[0]),
                        row[2],
                        row[1],
                        row[3],
                        row[4],
                        note,
                        isBanned
                );
            }
        }

        return null;
    }

    public List<Customer> getAllCustomers() {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);
        List<Customer> customerList = new ArrayList<Customer>();

        for (String[] row : rows) {
            String note = row.length > 5 ? row[5] : "";
            boolean isBanned = row.length > 6 && Boolean.parseBoolean(row[6]);

            Customer customer = new Customer(
                    Integer.parseInt(row[0]),
                    row[2],
                    row[1],
                    row[3],
                    row[4],
                    note,
                    isBanned
            );
            customerList.add(customer);
        }

        return customerList;
    }

    public boolean isCustomerBanned(int customerId) {
        Customer customer = getCustomerById(customerId);
        return customer != null && customer.isBanned();
    }
    
    public int generateCustomerId() {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);
        int maxId = 1000;

        for (String[] row : rows) {
            if (row.length < 1) {
                continue;
            }

            int currentId = Integer.parseInt(row[0].trim());
            if (currentId > maxId) {
                maxId = currentId;
            }
        }

        return maxId + 1;
    }
}
