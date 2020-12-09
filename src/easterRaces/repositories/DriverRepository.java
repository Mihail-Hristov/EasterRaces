package easterRaces.repositories;

import easterRaces.entities.cars.Car;
import easterRaces.entities.drivers.Driver;
import easterRaces.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DriverRepository implements Repository {
    private List<Driver> models;

    public DriverRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Object getByName(String name) {
        Driver driver = null;

        for (Driver d : models) {
            if (d.getName().equals(name)) {
                driver = d;
                break;
            }
        }
        return driver;
    }

    @Override
    public Collection getAll() {
        return this.models;
    }

    @Override
    public void add(Object model) {
        this.models.add((Driver) model);
    }

    @Override
    public boolean remove(Object model) {
        return this.models.remove(model);
    }
}
