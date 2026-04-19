package service;

import file.FileManager;
import model.Customer;
import model.Equipment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalService {

    private static final String FILE_PATH = "Software Analysis Final/data/rentals.csv";

    private FileManager fileManager = new FileManager();
    private CustomerService customerService = new CustomerService();
    private EquipmentService equipmentService = new EquipmentService();

    public boolean processRental(int rentalId, int customerId, int equipmentId,
                                 String rentalDate, String returnDate) {

        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return false;
        }

        if (customer.isBanned()) {
            System.out.println("Customer is banned and cannot rent equipment.");
            return false;
        }

        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        if (equipment == null) {
            System.out.println("Equipment not found.");
            return false;
        }

        if (equipment.isRented()) {
            System.out.println("Equipment is already rented.");
            return false;
        }

        double totalCost = calculateRentalCost(
                equipment.getDailyRentalCost(),
                rentalDate,
                returnDate
        );

        String currentDate = LocalDate.now().toString();

        String line = rentalId + ","
                + currentDate + ","
                + customerId + ","
                + equipmentId + ","
                + rentalDate + ","
                + returnDate + ","
                + totalCost;

        fileManager.appendLine(FILE_PATH, line);
        equipmentService.updateEquipmentRentalStatus(equipmentId, true);

        System.out.println("Rental processed successfully.");
        return true;
    }

    public double calculateRentalCost(double dailyRentalCost, String rentalDate, String returnDate) {
        LocalDate startDate = LocalDate.parse(rentalDate);
        LocalDate endDate = LocalDate.parse(returnDate);

        long days = ChronoUnit.DAYS.between(startDate, endDate);

        if (days <= 0) {
            days = 1;
        }

        return dailyRentalCost * days;
    }

    public double getRentalTotalCost(int rentalId) {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);

        for (String[] row : rows) {
            if (Integer.parseInt(row[0]) == rentalId) {
                return Double.parseDouble(row[6]);
            }
        }

        return 0.0;
    }
    
    public int generateRentalId() {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);
        int maxId = 999;

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
