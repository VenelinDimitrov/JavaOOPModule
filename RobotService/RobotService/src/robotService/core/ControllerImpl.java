package robotService.core;

import robotService.core.interfaces.Controller;
import robotService.models.garages.interfaces.Garage;
import robotService.models.garages.interfaces.GarageImpl;
import robotService.models.procedures.interfaces.Charge;
import robotService.models.procedures.interfaces.Procedure;
import robotService.models.procedures.interfaces.Repair;
import robotService.models.procedures.interfaces.Work;
import robotService.models.robots.interfaces.Cleaner;
import robotService.models.robots.interfaces.Housekeeper;
import robotService.models.robots.interfaces.Robot;

import static robotService.common.ExceptionMessages.INVALID_ROBOT_TYPE;
import static robotService.common.ExceptionMessages.NON_EXISTING_ROBOT;
import static robotService.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private Garage garage;
    private Procedure charge;
    private Procedure repair;
    private Procedure work;

    public ControllerImpl() {
        this.garage = new GarageImpl();
        this.charge = new Charge();
        this.repair = new Repair();
        this.work = new Work();
    }

    @Override
    public String manufacture(String robotType, String name, int energy, int happiness, int procedureTime) {
        Robot robot;

        switch (robotType) {
            case "Cleaner":
                robot = new Cleaner(name, energy, happiness, procedureTime);
                break;
            case "Housekeeper":
                robot = new Housekeeper(name, energy, happiness, procedureTime);
                break;
            default:
                throw new IllegalArgumentException(String.format(INVALID_ROBOT_TYPE, robotType));
        }

        this.garage.manufacture(robot);

        return String.format(ROBOT_MANUFACTURED, name);
    }

    @Override
    public String repair(String robotName, int procedureTime) {
        checkIfRobotExists(robotName);

        repair.doService(this.garage.getRobots().get(robotName), procedureTime);

        return String.format(REPAIR_PROCEDURE, robotName);
    }

    @Override
    public String work(String robotName, int procedureTime) {
        checkIfRobotExists(robotName);

        work.doService(this.garage.getRobots().get(robotName), procedureTime);

        return String.format(WORK_PROCEDURE, robotName, procedureTime);
    }

    @Override
    public String charge(String robotName, int procedureTime) {
        checkIfRobotExists(robotName);

        charge.doService(this.garage.getRobots().get(robotName), procedureTime);

        return String.format(CHARGE_PROCEDURE, robotName);
    }

    @Override
    public String sell(String robotName, String ownerName) {
        checkIfRobotExists(robotName);

        garage.sell(robotName, ownerName);

        return String.format(SELL_ROBOT, ownerName, robotName);
    }

    @Override
    public String history(String procedureType) {

        if (procedureType.equals("Charge")) {
            return charge.history();
        } else if (procedureType.equals("Repair")) {
            return repair.history();
        } else {
            return work.history();
        }
    }

    private void checkIfRobotExists(String robotName) {
        if (!this.garage.getRobots().containsKey(robotName)) {
            throw new IllegalArgumentException(String.format(NON_EXISTING_ROBOT, robotName));
        }
    }
}
