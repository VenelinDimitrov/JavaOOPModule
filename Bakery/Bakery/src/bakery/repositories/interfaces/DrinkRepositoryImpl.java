package bakery.repositories.interfaces;

import bakery.entities.drinks.interfaces.BaseDrink;
import bakery.entities.drinks.interfaces.Drink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrinkRepositoryImpl<T extends Drink> implements DrinkRepository<T>{
    private List<T> drinks;

    public DrinkRepositoryImpl () {
        this.drinks = new ArrayList<>();
    }

    @Override
    public Collection<T> getAll() {
        return this.drinks;
    }

    @Override
    public void add(T t) {
        this.drinks.add(t);
    }

    @Override
    public T getByNameAndBrand(String drinkName, String drinkBrand) {
        return this.drinks.stream().filter(d -> d.getName().equals(drinkName) && d.getBrand().equals(drinkBrand)).findFirst().orElse(null);
    }
}
