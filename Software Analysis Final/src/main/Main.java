package main;

import model.Customer;
import model.Equipment;
import service.CustomerService;
import service.EquipmentService;
import service.RentalService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EquipmentService equipmentService = new EquipmentService();
        CustomerService customerService = new CustomerService();
        RentalService rentalService = new RentalService();

        int choice = 0;

        while (choice != 7) {
            System.out.println("\n=== Village Rentals Menu ===");
            System.out.println("1. View All Equipment");
            System.out.println("2. View All Customers");
            System.out.println("3. Add Equipment");
            System.out.println("4. Delete Equipment");
            System.out.println("5. Add Customer");
            System.out.println("6. Process Rental");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("\n=== All Equipment ===");
                    for (Equipment equipment : equipmentService.getAllEquipment()) {
                        System.out.println(
                                equipment.getEquipmentId() + " - "
                                        + equipment.getName() + " - "
                                        + equipment.getDailyRentalCost() + " - Rented: "
                                        + (equipment.isRented() ? "Yes" : "No")
                        );
                    }
                    break;

                case 2:
                    System.out.println("\n=== All Customers ===");
                    for (Customer customer : customerService.getAllCustomers()) {
                        System.out.println(
                                customer.getCustomerId() + " - "
                                        + customer.getFirstName() + " "
                                        + customer.getLastName()
                                        + " - Phone: " + customer.getContactPhone()
                                        + " - Email: " + customer.getEmail()
                                        + " - Banned: " + (customer.isBanned() ? "Yes" : "No")
                        );
                    }
                    break;

                case 3:
                    System.out.print("Enter category id: ");
                    int categoryId = Integer.parseInt(scanner.nextLine());

                    int equipmentId = equipmentService.generateEquipmentId(categoryId);
                    System.out.println("Generated equipment id: " + equipmentId);

                    System.out.print("Enter equipment name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter daily rental cost: ");
                    double dailyRentalCost = Double.parseDouble(scanner.nextLine());

                    Equipment newEquipment = new Equipment(
                            equipmentId,
                            name,
                            description,
                            dailyRentalCost,
                            categoryId,
                            false
                    );

                    if (equipmentService.addEquipment(newEquipment)) {
                        System.out.println("Equipment added successfully.");
                    } else {
                        System.out.println("Failed to add equipment.");
                    }
                    break;

                case 4:
                    System.out.print("Enter equipment id to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());

                    if (equipmentService.deleteEquipment(deleteId)) {
                        System.out.println("Equipment deleted successfully.");
                    } else {
                        System.out.println("Failed to delete equipment.");
                    }
                    break;

                case 5:
                	int customerId = customerService.generateCustomerId();
                	System.out.println("Generated customer id: " + customerId);

                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Enter contact phone: ");
                    String phone = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

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
                        System.out.println("Customer added successfully.");
                    } else {
                        System.out.println("Failed to add customer.");
                    }
                    break;

                case 6:
                	int rentalId = rentalService.generateRentalId();
                	System.out.println("Generated rental id: " + rentalId);

                    System.out.print("Enter customer id: ");
                    int rentCustomerId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter equipment id: ");
                    int rentEquipmentId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter rental date (yyyy-MM-dd): ");
                    String rentalDate = scanner.nextLine();

                    System.out.print("Enter expected return date (yyyy-MM-dd): ");
                    String returnDate = scanner.nextLine();

                    boolean rentalSuccess = rentalService.processRental(
                            rentalId,
                            rentCustomerId,
                            rentEquipmentId,
                            rentalDate,
                            returnDate
                    );

                    if (rentalSuccess) {
                        System.out.println("Rental completed.");
                        System.out.println("Total cost: " + rentalService.getRentalTotalCost(rentalId));
                    } else {
                        System.out.println("Rental failed.");
                    }
                    break;

                case 7:
                    System.out.println("Program closed.");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}