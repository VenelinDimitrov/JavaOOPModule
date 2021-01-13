package Vehicles;

import java.text.DecimalFormat;

public class Bus extends Vehicle {

    public Bus(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        super(fuelQuantity, fuelConsumption, tankCapacity);
    }

    @Override
    protected void drive(double distance) {
        if (super.fuelQuantity >= super.fuelConsumption * distance) {
            super.fuelQuantity -= super.fuelConsumption * distance;
            DecimalFormat distanceTraveled = new DecimalFormat("##.##");

            System.out.println("Bus travelled " + distanceTraveled.format(distance) + " km");
        } else{
            System.out.println("Bus needs refueling");
        }
    }

    protected void driveWithPeople(double distance) {
        if (super.fuelQuantity >= (super.fuelConsumption + 1.4) * distance) {
            super.fuelQuantity -= (super.fuelConsumption + 1.4) * distance;
            DecimalFormat distanceTraveled = new DecimalFormat("##.##");

            System.out.println("Bus travelled " + distanceTraveled.format(distance) + " km");
        } else{
            System.out.println("Car needs refueling");
        }
    }
}
