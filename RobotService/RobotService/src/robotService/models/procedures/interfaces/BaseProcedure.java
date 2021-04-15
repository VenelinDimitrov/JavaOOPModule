package robotService.models.procedures.interfaces;

import robotService.models.robots.interfaces.Robot;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseProcedure implements Procedure{
    private Map<String, List<Robot>> robots;

    public BaseProcedure() {
        this.robots = new LinkedHashMap<>();
    }

    public Map<String, List<Robot>> getRobots() {
        return this.robots;
    }

    @Override
    public String history() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getClass().getSimpleName()).append(System.lineSeparator());

        for (String procedure : robots.keySet()) {
            if (procedure.equals(this.getClass().getSimpleName())) {
                List<Robot> robots = this.robots.get(procedure);
                for (Robot robot : robots) {
                    builder.append(" ");
                    builder.append(robot.toString()).append(System.lineSeparator());
                }
            }
        }

        return builder.toString().trim();
    }

    @Override
    public void doService(Robot robot, int procedureTime) {

    }
}
