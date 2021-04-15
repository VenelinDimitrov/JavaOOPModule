package robotService.models.procedures.interfaces;

import robotService.models.robots.interfaces.Robot;

import java.util.ArrayList;

import static robotService.common.ExceptionMessages.ALREADY_REPAIRED;
import static robotService.common.ExceptionMessages.INSUFFICIENT_PROCEDURE_TIME;

public class Repair extends BaseProcedure{
    @Override
    public void doService(Robot robot, int procedureTime) {
        if (robot.getProcedureTime() >= procedureTime) {

            if (robot.isRepaired()) {
                throw new IllegalArgumentException(String.format(ALREADY_REPAIRED, robot.getName()));
            }

            robot.setHappiness(robot.getHappiness() - 5);
            robot.setRepaired(true);

            robot.setProcedureTime(robot.getProcedureTime() - procedureTime);
            super.getRobots().putIfAbsent(super.getClass().getSimpleName(),new ArrayList<>());
            super.getRobots().get(super.getClass().getSimpleName()).add(robot);
        } else {
            throw new IllegalArgumentException(INSUFFICIENT_PROCEDURE_TIME);
        }
    }
}
