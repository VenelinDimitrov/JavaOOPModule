package robotService.models.procedures.interfaces;

import robotService.models.robots.interfaces.Robot;

import java.util.ArrayList;

import static robotService.common.ExceptionMessages.INSUFFICIENT_PROCEDURE_TIME;

public class Work extends BaseProcedure{
    @Override
    public void doService(Robot robot, int procedureTime) {
        if (robot.getProcedureTime() >= procedureTime) {
            robot.setEnergy(robot.getEnergy() - 6);
            robot.setHappiness(robot.getHappiness() + 12);

            robot.setProcedureTime(robot.getProcedureTime() - procedureTime);
            super.getRobots().putIfAbsent(super.getClass().getSimpleName(),new ArrayList<>());
            super.getRobots().get(super.getClass().getSimpleName()).add(robot);
        } else {
            throw new IllegalArgumentException(INSUFFICIENT_PROCEDURE_TIME);
        }
    }
}
