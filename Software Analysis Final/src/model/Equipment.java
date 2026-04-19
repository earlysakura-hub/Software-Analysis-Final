package model;

public class Equipment {
    private int equipmentId;
    private String name;
    private String description;
    private double dailyRentalCost;
    private int categoryId;
    private boolean isRented;

    public Equipment() {
    }

    public Equipment(int equipmentId, String name, String description,
                     double dailyRentalCost, int categoryId, boolean isRented) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.description = description;
        this.dailyRentalCost = dailyRentalCost;
        this.categoryId = categoryId;
        this.isRented = isRented;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDailyRentalCost() {
        return dailyRentalCost;
    }

    public void setDailyRentalCost(double dailyRentalCost) {
        this.dailyRentalCost = dailyRentalCost;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }
}