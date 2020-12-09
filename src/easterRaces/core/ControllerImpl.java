package easterRaces.core;

import easterRaces.common.ExceptionMessages;
import easterRaces.common.OutputMessages;
import easterRaces.core.interfaces.Controller;
import easterRaces.entities.cars.Car;
import easterRaces.entities.cars.MuscleCar;
import easterRaces.entities.cars.SportsCar;
import easterRaces.entities.drivers.Driver;
import easterRaces.entities.drivers.DriverImpl;
import easterRaces.entities.racers.Race;
import easterRaces.entities.racers.RaceImpl;
import easterRaces.repositories.interfaces.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private Repository drivers;
    private Repository cars;
    private Repository races;

    public ControllerImpl(Repository<Driver> drivers, Repository<Race> races, Repository<Car> cars) {
        this.drivers = drivers;
        this.cars = cars;
        this.races = races;
    }

    @Override
    public String createDriver(String driver) {
        Driver currentDriver = new DriverImpl(driver);
        for (Object d : drivers.getAll()) {
            Driver searchableDriver = (Driver) d;
            if (searchableDriver.getName().equals(currentDriver.getName())) {
                throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_EXISTS, driver));
            }
        }
        drivers.add(currentDriver);

        return String.format(OutputMessages.DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        Car car = null;
        switch (type) {
            case "Muscle":
                car = new MuscleCar(model, horsePower);

                break;
            case "Sports":
                car = new SportsCar(model, horsePower);

                break;
        }

        for (Object c : cars.getAll()) {
            Car searchableCar = (Car) c;
            if (searchableCar.getModel().equals(car.getModel())) {
                throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_EXISTS, model));
            }
        }

        cars.add(car);

        return String.format(OutputMessages.CAR_CREATED, car.getClass().getSimpleName(), model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        Car car =(Car) cars.getByName(carModel);
        Driver driver = (Driver) drivers.getByName(driverName);

        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND,driverName));
        }else if (car == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_NOT_FOUND,carModel));
        }

        driver.addCar(car);
        return String.format(OutputMessages.CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        Race race =(Race) races.getByName(raceName);
        Driver driver = (Driver) drivers.getByName(driverName);

        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND,driverName));
        }else if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND,raceName));
        }

        race.addDriver(driver);
        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {
        Race race = (Race) races.getByName(raceName);

        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND,raceName));
        }else if(race.getDrivers().size() < 3) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_INVALID,raceName, 3));
        }

        List<Driver> racing = race.getDrivers().stream().sorted((d1, d2) -> {
            return Double.compare(d2.getCar().calculateRacePoints(race.getLaps()), d1.getCar().calculateRacePoints(race.getLaps()));
        }).collect(Collectors.toList());

        races.remove(race);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(OutputMessages.DRIVER_FIRST_POSITION, racing.get(0).getName(), raceName)).append(System.lineSeparator());
        sb.append(String.format(OutputMessages.DRIVER_SECOND_POSITION, racing.get(1).getName(), raceName)).append(System.lineSeparator());
        sb.append(String.format(OutputMessages.DRIVER_THIRD_POSITION, racing.get(2).getName(), raceName)).append(System.lineSeparator());

        return sb.toString().trim();
    }

    @Override
    public String createRace(String name, int laps) {
        Race race = new RaceImpl(name, laps);

        for (Object r : races.getAll()) {
            Race currentRace = (Race) r;
            if (currentRace.getName().equals(name)) {
                throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS,name));
            }
        }
        races.add(race);

        return String.format(OutputMessages.RACE_CREATED, name);
    }
}
