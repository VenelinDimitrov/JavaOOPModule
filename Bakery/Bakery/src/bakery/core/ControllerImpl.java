package bakery.core;

import bakery.core.interfaces.Controller;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.bakedFoods.interfaces.Bread;
import bakery.entities.bakedFoods.interfaces.Cake;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.drinks.interfaces.Tea;
import bakery.entities.drinks.interfaces.Water;
import bakery.entities.tables.interfaces.InsideTable;
import bakery.entities.tables.interfaces.OutsideTable;
import bakery.entities.tables.interfaces.Table;
import bakery.repositories.interfaces.*;

import static bakery.common.ExceptionMessages.*;
import static bakery.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private FoodRepository<BakedFood> foodRepository;
    private DrinkRepository<Drink> drinkRepository;
    private TableRepository<Table> tableRepository;
    private double totalIncome;

    public ControllerImpl(FoodRepository<BakedFood> foodRepository, DrinkRepository<Drink> drinkRepository, TableRepository<Table> tableRepository) {
        this.foodRepository = foodRepository;
        this.drinkRepository = drinkRepository;
        this.tableRepository = tableRepository;
    }

    public double calculatedTotalIncome() {
        return this.totalIncome;
    }

    private void setTotalIncome(double income) {
        this.totalIncome = income;
    }

    @Override
    public String addFood(String type, String name, double price) {

        BakedFood food;

        switch (type) {
            case "Bread":
                food = new Bread(name, price);
                break;
            case "Cake":
                food = new Cake(name, price);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        if (this.foodRepository.getAll().contains(food)) {
            throw new IllegalStateException(String.format(FOOD_OR_DRINK_EXIST, type, name));
        }

        this.foodRepository.add(food);

        return String.format(FOOD_ADDED, name, type);
    }

    @Override
    public String addDrink(String type, String name, int portion, String brand) {

        Drink drink;

        switch (type) {
            case "Tea":
                drink = new Tea(name, portion, brand);
                break;
            case "Water":
                drink = new Water(name, portion, brand);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        if (drinkRepository.getAll().contains(drink)) {
            throw new IllegalStateException(FOOD_OR_DRINK_EXIST);
        }

        drinkRepository.add(drink);

        return String.format(DRINK_ADDED, name, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {

        Table table;

        switch (type) {
            case "InsideTable":
                table = new InsideTable(tableNumber, capacity);
                break;
            case "OutsideTable":
                table = new OutsideTable(tableNumber, capacity);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        if (tableRepository.getAll().contains(table)) {
            throw new IllegalStateException(String.format(TABLE_EXIST, tableNumber));
        }

        tableRepository.add(table);

        return String.format(TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserveTable(int numberOfPeople) {

        int tableNumber = -1;

        for (Table table : tableRepository.getAll()) {
            if (!table.isReserved()) {
                if (table.getCapacity() >= numberOfPeople) {
                    tableNumber = table.getTableNumber();
                    break;
                }
            }
        }

        if (tableNumber == -1) {
            return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }

        tableRepository.getByNumber(tableNumber).reserve(numberOfPeople);

        return String.format(TABLE_RESERVED, tableNumber, numberOfPeople);
    }

    @Override
    public String orderFood(int tableNumber, String foodName) {

        if (tableRepository.getByNumber(tableNumber) == null ||
                !this.tableRepository.getByNumber(tableNumber).isReserved()) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }

        if (foodRepository.getByName(foodName) == null) {
            return String.format(NONE_EXISTENT_FOOD, foodName);
        }

        BakedFood food = foodRepository.getByName(foodName);

        tableRepository.getByNumber(tableNumber).orderFood(food);

        return String.format(FOOD_ORDER_SUCCESSFUL, tableNumber, foodName);
    }

    @Override
    public String orderDrink(int tableNumber, String drinkName, String drinkBrand) {

        if (tableRepository.getByNumber(tableNumber) == null ||
                !this.tableRepository.getByNumber(tableNumber).isReserved()) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }

        if (drinkRepository.getByNameAndBrand(drinkName, drinkBrand) == null) {
            return String.format(NON_EXISTENT_DRINK, drinkName, drinkBrand);
        }

        Drink drink = this.drinkRepository.getByNameAndBrand(drinkName, drinkBrand);

        tableRepository.getByNumber(tableNumber).orderDrink(drink);

        return String.format(DRINK_ORDER_SUCCESSFUL, tableNumber, drinkName, drinkBrand);

    }

    @Override
    public String leaveTable(int tableNumber) {

        //Table table = tableRepository.getByNumber(tableNumber);

        double bill = tableRepository.getByNumber(tableNumber).getBill();
        this.setTotalIncome(this.calculatedTotalIncome() + bill);
        tableRepository.getByNumber(tableNumber).clear();

        StringBuilder builder = new StringBuilder();

        builder.append(String.format(BILL,tableNumber, bill));

        return builder.toString().trim();
    }

    @Override
    public String getFreeTablesInfo() {

        StringBuilder builder = new StringBuilder();

        for (Table table : tableRepository.getAll()) {
            if (table.getNumberOfPeople() <= 0){
                builder.append(table.getFreeTableInfo());
            }
        }

        return builder.toString().trim();
    }

    @Override
    public String getTotalIncome() {

        return String.format(TOTAL_INCOME, this.calculatedTotalIncome());
    }
}
