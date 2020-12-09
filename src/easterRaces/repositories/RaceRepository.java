package easterRaces.repositories;

import easterRaces.entities.drivers.Driver;
import easterRaces.entities.racers.Race;
import easterRaces.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RaceRepository implements Repository {
    private List<Race> models;

    public RaceRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Object getByName(String name) {
        Race race = null;

        for (Race r : models) {
            if (r.getName().equals(name)) {
                race = r;
                break;
            }
        }
        return race;
    }

    @Override
    public Collection getAll() {
        return this.models;
    }

    @Override
    public void add(Object model) {
        this.models.add((Race) model);
    }

    @Override
    public boolean remove(Object model) {
        return models.remove(model);
    }
}
