package easterRaces.repositories.interfaces;

import easterRaces.entities.racers.Race;

import java.util.ArrayList;
import java.util.Collection;

public class RaceRepository<T extends Race> implements Repository<T> {
    private Collection<T> models;

    public RaceRepository() {
        models = new ArrayList<>();
    }


    @Override
    public T getByName(String name) {
        return models.stream().filter(d -> d.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Collection getAll() {
        return this.models;
    }

    @Override
    public void add(T model) {
        this.models.add(model);
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
