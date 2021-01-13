package Vehicles;

import java.text.DecimalFormat;

public class Truck extends Vehicle{

    public Truck(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        super(fuelQuantity, fuelConsumption + 1.6, tankCapacity);
    }

    @Override
    protected void drive(double distance) {
        if (super.fuelQuantity >= super.fuelConsumption * distance) {
            super.fuelQuantity -= super.fuelConsumption * distance;
            DecimalFormat distanceTraveled = new DecimalFormat("##.##");

            System.out.println("Truck travelled " + distanceTraveled.format(distance) + " km");
        } else {
            System.out.println("Truck needs refueling");
        }
    }

    @Override
    protected void refuel(double liters) {
        super.refuel(liters * 0.95);
    }
}
