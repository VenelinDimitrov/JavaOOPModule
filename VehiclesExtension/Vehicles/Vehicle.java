package Vehicles;

public abstract class Vehicle {
    //fuel quantity, fuel consumption in liters per km and can be driven given distance and refueled with given liters
    protected double fuelQuantity;
    protected double fuelConsumption;
    protected double tankCapacity;

    public Vehicle(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        this.fuelQuantity = fuelQuantity;
        this.fuelConsumption = fuelConsumption;
        this.tankCapacity = tankCapacity;
    }

    protected abstract void drive(double distance);

    protected void refuel(double liters) {
        if (this.fuelQuantity + liters <= this.tankCapacity && liters > 0) {
            this.fuelQuantity += liters;
        } else if (liters <= 0) {
            System.out.println("Fuel must be a positive number");
        } else {
            System.out.println("Cannot fit fuel in tank");
        }
    }
}
