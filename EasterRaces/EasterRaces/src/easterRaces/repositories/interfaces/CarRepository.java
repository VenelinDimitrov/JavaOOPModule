package easterRaces.repositories.interfaces;

import easterRaces.entities.cars.Car;

import java.util.ArrayList;
import java.util.Collection;

public class CarRepository<T extends Car> implements Repository<T>{
    private Collection<T> models;

    public CarRepository() {
        models = new ArrayList<>();
    }


    @Override
    public T getByName(String name) {
        return models.stream().filter(d -> d.getModel().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Collection getAll() {
        return this.models;
    }

    @Override
    public void add(T model) {
        models.add(model);
    }

    @Override
    public boolean remove(T model) {
        if (models.contains(model)) {
            models.remove(model);
            return true;
        }
        return false;
    }
}
