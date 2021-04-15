package bakery.entities.tables.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.BaseDrink;
import bakery.entities.drinks.interfaces.Drink;

import java.util.ArrayList;
import java.util.Collection;

import static bakery.common.ExceptionMessages.*;

public class BaseTable implements Table{
    private Collection<BakedFood> foodOrders;
    private Collection<Drink> drinkOrders;
    private int tableNumber;
    private int capacity;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReserved;
    private double price;

    protected BaseTable(int tableNumber, int capacity, double pricePerPerson) {
        this.foodOrders = new ArrayList<>();
        this.drinkOrders = new ArrayList<>();
        this.setTableNumber(tableNumber);
        this.setCapacity(capacity);
        this.setPricePerPerson(pricePerPerson);
    }


    private void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    private void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(INVALID_TABLE_CAPACITY);
        }
        this.capacity = capacity;
    }

    private void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople < 0) {
            throw new IllegalArgumentException(INVALID_NUMBER_OF_PEOPLE);
        }
        this.numberOfPeople = numberOfPeople;
    }

    private void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    private void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getTableNumber() {
        return this.tableNumber;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getNumberOfPeople() {
        return this.numberOfPeople;
    }

    @Override
    public double getPricePerPerson() {
        return this.pricePerPerson;
    }

    @Override
    public boolean isReserved() {
        return this.isReserved;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void reserve(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
        this.setReserved(true);
    }

    @Override
    public void orderFood(BakedFood food) {
        this.foodOrders.add(food);
    }

    @Override
    public void orderDrink(Drink drink) {
        this.drinkOrders.add(drink);
    }

    @Override
    public double getBill() {
        double foodBill = 0;

        for (BakedFood foodOrder : foodOrders) {
            foodBill += foodOrder.getPrice();
        }

        double drinkBill = 0;

        for (Drink drinkOrder : drinkOrders) {
            drinkBill += drinkOrder.getPrice();
        }

        return foodBill + drinkBill;
    }

    @Override
    public void clear() {
        this.foodOrders = new ArrayList<>();
        this.drinkOrders = new ArrayList<>();
        this.setNumberOfPeople(0);
        this.setPrice(0);
    }

    @Override
    public String getFreeTableInfo() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("Table: %d", this.getTableNumber())).append(System.lineSeparator());
        builder.append(String.format("Type: %s", this.getClass().getSimpleName())).append(System.lineSeparator());
        builder.append(String.format("Capacity: %d", this.getCapacity())).append(System.lineSeparator());
        builder.append(String.format("Price per Person: %.2f%n", this.getPricePerPerson()));

        return builder.toString();
    }
}
