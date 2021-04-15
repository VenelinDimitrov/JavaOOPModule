package bakery.repositories.interfaces;

import bakery.entities.tables.interfaces.BaseTable;
import bakery.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TableRepositoryImpl<T extends Table> implements TableRepository<T> {
    private List<T> tables;

    public TableRepositoryImpl() {
        this.tables = new ArrayList<>();
    }

    @Override
    public Collection<T> getAll() {
        return this.tables;
    }

    @Override
    public void add(T t) {
        this.tables.add(t);
    }

    @Override
    public T getByNumber(int number) {
        return this.tables.stream().filter(t -> t.getTableNumber() == number).findFirst().orElse(null);
    }
}
