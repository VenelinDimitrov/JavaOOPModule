package easterRaces.core;

import easterRaces.core.interfaces.Controller;
import easterRaces.entities.cars.BaseCar;
import easterRaces.entities.cars.Car;
import easterRaces.entities.cars.MuscleCar;
import easterRaces.entities.cars.SportsCar;
import easterRaces.entities.drivers.Driver;
import easterRaces.entities.drivers.DriverImpl;
import easterRaces.entities.racers.Race;
import easterRaces.entities.racers.RaceImpl;
import easterRaces.repositories.interfaces.Repository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static easterRaces.common.ExceptionMessages.*;
import static easterRaces.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Driver> drivers;
    private Repository<Car> cars;
    private Repository<Race> races;

    public ControllerImpl(Repository<Driver> riderRepository,Repository<Car> motorcycleRepository,Repository<Race> raceRepository) {
        this.drivers = riderRepository;
        this.cars = motorcycleRepository;
        this.races = raceRepository;
    }

    @Override
    public String createDriver(String driver) {
        if (drivers.getByName(driver) != null) {
            throw new IllegalArgumentException(String.format(DRIVER_EXISTS,driver));
        }

        Driver currentDriver = new DriverImpl(driver);
        drivers.add(currentDriver);
        return String.format(DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        if (cars.getByName(model) != null) {
            throw new IllegalArgumentException(String.format(CAR_EXISTS,model));
        }

        Car car;
        switch (type) {
            case "Muscle":
                car = new MuscleCar(model, horsePower);
                break;
            case "Sports":
                car = new SportsCar(model, horsePower);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        cars.add(car);
        return String.format(CAR_CREATED,type, model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        if (drivers.getByName(driverName) == null) {
            throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND, driverName));
        }
        if (cars.getByName(carModel) == null) {
            throw new IllegalArgumentException(String.format(CAR_NOT_FOUND, carModel));
        }

        Driver driver = drivers.getByName(driverName);
        Car car = cars.getByName(carModel);
        driver.addCar(car);
        return String.format(CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        if (drivers.getByName(driverName) == null) {
            throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND, driverName));
        }
        if (races.getByName(raceName) == null) {
            throw new IllegalArgumentException(String.format(RACE_NOT_FOUND, raceName));
        }

        races.getByName(raceName).addDriver(drivers.getByName(driverName));
        return String.format(DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {
        if (races.getByName(raceName) == null) {
            throw new IllegalArgumentException(String.format(RACE_NOT_FOUND, raceName));
        }
        if (races.getByName(raceName).getDrivers().size() < 3) {
            throw new IllegalArgumentException(String.format(RACE_INVALID, raceName, 3));
        }

        Race race = races.getByName(raceName);

        Map<Driver, Integer> driversAndRacePoints = new LinkedHashMap<>();

        for (Driver driver: drivers.getAll()) {
            driver.getCar().calculateRacePoints(race.getLaps());
        }

        List<Driver> fastest = drivers.getAll().stream()
                .limit(3).collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();

        builder.append(String.format(DRIVER_FIRST_POSITION, fastest.get(0).getName(), raceName))
                .append(System.lineSeparator());
        builder.append(String.format(DRIVER_SECOND_POSITION, fastest.get(1).getName(), raceName))
                .append(System.lineSeparator());
        builder.append(String.format(DRIVER_THIRD_POSITION, fastest.get(2).getName(), raceName));

        return builder.toString().trim();
    }

    @Override
    public String createRace(String name, int laps) {
        if (races.getByName(name) != null) {
            throw new IllegalArgumentException(String.format(RACE_EXISTS,name));
        }

        Race race = new RaceImpl(name, laps);
        races.add(race);

        return String.format(RACE_CREATED, name);
    }
}
