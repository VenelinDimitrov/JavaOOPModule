package easterRaces.entities.cars;

import static easterRaces.common.ExceptionMessages.*;

public class SportsCar extends BaseCar{
    private final double CUBIC_CENTIMETERS = 3000;

    public SportsCar(String model, int horsePower) {
        super.setModel(model);
        this.setHorsePower(horsePower);
        super.setCubicCentimeters(CUBIC_CENTIMETERS);
    }

    @Override
    protected void setHorsePower(int horsePower) {
        if (horsePower < 250 || horsePower > 450) {
            throw new IllegalArgumentException(String.format(INVALID_HORSE_POWER, horsePower));
        }
        super.setHorsePower(horsePower);
    }
}
