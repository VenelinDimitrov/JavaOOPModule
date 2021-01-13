package Vehicles;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] carInput = scanner.nextLine().split("\\s+");
        Car car = new Car(Double.parseDouble(carInput[1]), Double.parseDouble(carInput[2]), Double.parseDouble(carInput[3]));
        String[] truckInput = scanner.nextLine().split("\\s+");
        Truck truck = new Truck(Double.parseDouble(truckInput[1]), Double.parseDouble(truckInput[2]), Double.parseDouble(carInput[3]));
        String[] busInput = scanner.nextLine().split("\\s+");
        Bus bus = new Bus(Double.parseDouble(busInput[1]), Double.parseDouble(busInput[2]), Double.parseDouble(busInput[3]));

        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            String[] tokens = scanner.nextLine().split("\\s+");
            String action = tokens[0];
            String vehicleType = tokens[1];
            double distance = Double.parseDouble(tokens[2]);

            if (action.equals("Drive")) {
                switch (vehicleType) {
                    case "Car":
                        car.drive(distance);
                        break;
                    case "Truck":
                        truck.drive(distance);
                        break;
                    case "Bus":
                        bus.driveWithPeople(distance);
                }
            } else if (action.equals("Refuel")) {
                double fuelAmount = Double.parseDouble(tokens[2]);
                switch (vehicleType) {
                    case "Car":
                        car.refuel(fuelAmount);
                        break;
                    case "Truck":
                        truck.refuel(fuelAmount);
                        break;
                    case "Bus":
                        bus.refuel(fuelAmount);
                        break;
                }
            } else if (action.equals("DriveEmpty")) {
                bus.drive(distance);
            }
        }

        System.out.printf("Car: %.2f%n", car.fuelQuantity);
        System.out.printf("Truck: %.2f%n", truck.fuelQuantity);
        System.out.printf("Bus: %.2f%n", bus.fuelQuantity);
    }
}
