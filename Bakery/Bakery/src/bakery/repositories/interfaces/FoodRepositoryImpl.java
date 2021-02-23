package bakery.repositories.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.bakedFoods.interfaces.BaseFood;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FoodRepositoryImpl<T extends BakedFood> implements FoodRepository<T>{
    private List<T> foodList;

    public FoodRepositoryImpl() {
        this.foodList = new ArrayList<>();
    }

    @Override
    public Collection<T> getAll() {
        return this.foodList;
    }

    @Override
    public void add(T t) {
        this.foodList.add(t);
    }

    @Override
    public T getByName(String name) {
        return this.foodList.stream().filter(f -> f.getName().equals(name)).findFirst().orElse(null);
    }
}
