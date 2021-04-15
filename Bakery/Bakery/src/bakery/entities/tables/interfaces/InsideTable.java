package bakery.entities.tables.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.Drink;

public class InsideTable extends BaseTable{
    private static final double PRICE_PER_PERSON = 2.50;
    public InsideTable(int tableNumber, int capacity) {
        super(tableNumber, capacity, PRICE_PER_PERSON);
    }

    @Override
    public double getBill() {
        return super.getBill() + PRICE_PER_PERSON * super.getNumberOfPeople();
    }
}
