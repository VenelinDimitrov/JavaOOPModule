package easterRaces.entities.cars;

import static easterRaces.common.ExceptionMessages.*;

public class MuscleCar extends BaseCar{
    private final double CUBIC_CENTIMETERS = 5000;

    public MuscleCar(String model, int horsePower) {
        super.setModel(model);
        this.setHorsePower(horsePower);
        super.setCubicCentimeters(CUBIC_CENTIMETERS);
    }

    @Override
    protected void setHorsePower(int horsePower) {
        if (horsePower < 400 || horsePower > 600) {
            throw new IllegalArgumentException(String.format(INVALID_HORSE_POWER, horsePower));
        }
        super.setHorsePower(horsePower);
    }
}
