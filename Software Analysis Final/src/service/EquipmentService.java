package service;

import file.FileManager;
import model.Equipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentService {

    private static final String FILE_PATH = "Software Analysis Final/data/equipment.csv";
    private FileManager fileManager = new FileManager();

    public boolean addEquipment(Equipment equipment) {
        try {
            String line = equipment.getEquipmentId() + ","
                    + equipment.getCategoryId() + ","
                    + equipment.getName() + ","
                    + equipment.getDescription() + ","
                    + equipment.getDailyRentalCost() + ","
                    + equipment.isRented();

            fileManager.appendLine(FILE_PATH, line);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEquipment(int equipmentId) {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);
        List<String> lines = new ArrayList<String>();
        boolean deleted = false;

        lines.add("equipment_id,category_id,name,description,daily_rate,is_rented");

        for (String[] row : rows) {
            if (row.length < 6) {
                continue;
            }

            int currentId = Integer.parseInt(row[0].trim());

            if (currentId == equipmentId) {
                deleted = true;
                continue;
            }

            lines.add(row[0] + "," + row[1] + "," + row[2] + "," + row[3] + ","
                    + row[4] + "," + row[5]);
        }

        fileManager.writeCsv(FILE_PATH, lines);
        return deleted;
    }

    public Equipment getEquipmentById(int equipmentId) {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);

        for (String[] row : rows) {
            if (row.length < 6) {
                continue;
            }

            if (Integer.parseInt(row[0].trim()) == equipmentId) {
                return new Equipment(
                        Integer.parseInt(row[0].trim()),
                        row[2].trim(),
                        row[3].trim(),
                        Double.parseDouble(row[4].trim()),
                        Integer.parseInt(row[1].trim()),
                        Boolean.parseBoolean(row[5].trim())
                );
            }
        }

        return null;
    }

    public List<Equipment> getAllEquipment() {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);
        List<Equipment> equipmentList = new ArrayList<Equipment>();

        for (String[] row : rows) {
            if (row.length < 6) {
                continue;
            }

            Equipment equipment = new Equipment(
                    Integer.parseInt(row[0].trim()),
                    row[2].trim(),
                    row[3].trim(),
                    Double.parseDouble(row[4].trim()),
                    Integer.parseInt(row[1].trim()),
                    Boolean.parseBoolean(row[5].trim())
            );
            equipmentList.add(equipment);
        }

        return equipmentList;
    }

    public boolean updateEquipmentRentalStatus(int equipmentId, boolean isRented) {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);
        List<String> lines = new ArrayList<String>();
        boolean updated = false;

        lines.add("equipment_id,category_id,name,description,daily_rate,is_rented");

        for (String[] row : rows) {
            if (row.length < 6) {
                continue;
            }

            if (Integer.parseInt(row[0].trim()) == equipmentId) {
                row[5] = String.valueOf(isRented);
                updated = true;
            }

            lines.add(row[0] + "," + row[1] + "," + row[2] + "," + row[3] + ","
                    + row[4] + "," + row[5]);
        }

        fileManager.writeCsv(FILE_PATH, lines);
        return updated;
    }

    public int generateEquipmentId(int categoryId) {
        List<String[]> rows = fileManager.readCsv(FILE_PATH);
        int maxSuffix = 0;

        for (String[] row : rows) {
            if (row.length < 6) {
                continue;
            }

            int currentCategoryId = Integer.parseInt(row[1].trim());
            int currentEquipmentId = Integer.parseInt(row[0].trim());

            if (currentCategoryId == categoryId) {
                int suffix = currentEquipmentId % 10;
                if (suffix > maxSuffix) {
                    maxSuffix = suffix;
                }
            }
        }

        return categoryId * 10 + (maxSuffix + 1);
    }
}
