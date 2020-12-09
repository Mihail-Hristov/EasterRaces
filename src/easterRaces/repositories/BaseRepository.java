package easterRaces.repositories;

import easterRaces.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRepository<T> implements Repository {
    private List<T> models;

    protected BaseRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Object getByName(String name) {
        return null;
    }

    @Override
    public Collection getAll() {
        return null;
    }

    @Override
    public void add(Object model) {

    }

    @Override
    public boolean remove(Object model) {
        return false;
    }
}
