package model;

public class RentalItem {
    private int rentalItemId;
    private int rentalId;
    private int equipmentId;
    private double costOfRental;

    public RentalItem() {
    }

    public RentalItem(int rentalItemId, int rentalId, int equipmentId, double costOfRental) {
        this.rentalItemId = rentalItemId;
        this.rentalId = rentalId;
        this.equipmentId = equipmentId;
        this.costOfRental = costOfRental;
    }

    public int getRentalItemId() {
        return rentalItemId;
    }

    public void setRentalItemId(int rentalItemId) {
        this.rentalItemId = rentalItemId;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public double getCostOfRental() {
        return costOfRental;
    }

    public void setCostOfRental(double costOfRental) {
        this.costOfRental = costOfRental;
    }
}