package model;

public class Rental {
    private int rentalId;
    private String currentDate;
    private int customerId;
    private String rentalDate;
    private String returnDate;
    private double totalFinalCost;

    public Rental() {
    }

    public Rental(int rentalId, String currentDate, int customerId,
                  String rentalDate, String returnDate, double totalFinalCost) {
        this.rentalId = rentalId;
        this.currentDate = currentDate;
        this.customerId = customerId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalFinalCost = totalFinalCost;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalFinalCost() {
        return totalFinalCost;
    }

    public void setTotalFinalCost(double totalFinalCost) {
        this.totalFinalCost = totalFinalCost;
    }
}