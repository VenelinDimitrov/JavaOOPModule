package NeedForSpeed;

public class Vehicle {
    protected final static double DEFAULT_FUEL_CONSUMPTION = 1.25;
    protected double fuelConsumption;
    protected double fuel;
    protected int horsePower;

    public Vehicle(double fuel, int horsePower) {
        this.fuel = fuel;
        this.horsePower = horsePower;
        this.fuelConsumption = DEFAULT_FUEL_CONSUMPTION;
    }

    public void drive(double kilometers) {
        if (this.fuel >= this.fuelConsumption * kilometers){
            this.fuel = this.fuel - (this.fuelConsumption * kilometers);
        }
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public double getFuelConsumption() {
        return this.fuelConsumption;
    }
    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }


}
