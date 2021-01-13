package Vehicles;

import java.text.DecimalFormat;

public class Car extends Vehicle {

    public Car(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        super(fuelQuantity, fuelConsumption + 0.9, tankCapacity);
    }

    @Override
    protected void drive(double distance) {
        if (super.fuelQuantity >= super.fuelConsumption * distance) {
            super.fuelQuantity -= super.fuelConsumption * distance;
            DecimalFormat distanceTraveled = new DecimalFormat("##.##");

            System.out.println("Car travelled " + distanceTraveled.format(distance) + " km");
        } else{
            System.out.println("Car needs refueling");
        }
    }

}
