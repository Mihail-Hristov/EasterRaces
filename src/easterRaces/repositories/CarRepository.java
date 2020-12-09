package easterRaces.repositories;

import easterRaces.entities.cars.Car;
import easterRaces.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CarRepository implements Repository {
    private List<Car> models;

    public CarRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Object getByName(String name) {
        Car car = null;

        for (Car c : models) {
            if (c.getModel().equals(name)) {
                car = c;
                break;
            }
        }
        return car;
    }

    @Override
    public Collection getAll() {
        return this.models;
    }

    @Override
    public void add(Object model) {
        this.models.add((Car) model);
    }

    @Override
    public boolean remove(Object model) {
        return this.models.remove(model);
    }
}
