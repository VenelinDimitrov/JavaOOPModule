package onlineShop.models.products.computers;

import onlineShop.models.products.BaseProduct;
import onlineShop.models.products.components.Component;
import onlineShop.models.products.peripherals.Peripheral;

import java.util.ArrayList;
import java.util.List;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.COMPUTER_COMPONENTS_TO_STRING;
import static onlineShop.common.constants.OutputMessages.COMPUTER_PERIPHERALS_TO_STRING;

public class BaseComputer extends BaseProduct implements Computer {
    private List<Component> components;
    private List<Peripheral> peripherals;

    protected BaseComputer(int id, String manufacturer, String model, double price, double overallPerformance) {
        super(id, manufacturer, model, price, overallPerformance);
        components = new ArrayList<>();
        peripherals = new ArrayList<>();
    }

    @Override
    public List<Component> getComponents() {
        return this.components;
    }

    @Override
    public List<Peripheral> getPeripherals() {
        return this.peripherals;
    }

    @Override
    public void addComponent(Component component) {
        if (components.contains(component)) {
            throw new IllegalArgumentException(String.format(EXISTING_COMPONENT, component.getClass().getName(), super.getClass().getName(), super.getId()));
        }
        this.components.add(component);
    }

    @Override
    public Component removeComponent(String componentType) {
        int index = -1;

        for (int i = 0; i < components.size(); i++) {
            Component current = components.get(i);
            if (current.getClass().getSimpleName().equals(componentType)) {
                index = i;
            }
        }

        if (components.isEmpty() || index == -1) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_COMPONENT, componentType, super.getClass().getName(), super.getId()));
        }

        return components.remove(index);
    }

    @Override
    public void addPeripheral(Peripheral peripheral) {
        if (peripherals.contains(peripheral)) {
            throw new IllegalArgumentException(String.format(EXISTING_PERIPHERAL, peripheral.getClass().getName(), super.getClass().getName(), super.getId()));
        }
        this.peripherals.add(peripheral);
    }

    @Override
    public Peripheral removePeripheral(String peripheralType) {
        int index = -1;
        for (int i = 0; i < peripherals.size(); i++) {
            Peripheral current = peripherals.get(i);
            if (current.getClass().getSimpleName().equals(peripheralType)) {
                index = i;
            }
        }

        if (peripherals.isEmpty() || index == -1) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_PERIPHERAL, peripheralType, super.getClass().getName(), super.getId()));
        }

        return peripherals.remove(index);
    }

    @Override
    public double getOverallPerformance() {
        if (components.isEmpty()) {
            return super.getOverallPerformance();
        }

        return super.getOverallPerformance() + components.stream().mapToDouble(Component::getOverallPerformance).average().orElse(0);
    }

    @Override
    public double getPrice() {
        double componentsPrice = components.stream().mapToDouble(Component::getPrice).sum();
        double peripheralsPrice = peripherals.stream().mapToDouble(Peripheral::getPrice).sum();

        return super.getPrice() + componentsPrice + peripheralsPrice;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(super.toString()).append(System.lineSeparator());
        builder.append(" ").append(String.format(COMPUTER_COMPONENTS_TO_STRING, components.size())).append(System.lineSeparator());

        for (Component component : components) {
            builder.append("  ").append(component.toString()).append(System.lineSeparator());
        }

        builder.append(" ").append(String.format(COMPUTER_PERIPHERALS_TO_STRING, peripherals.size(),
                peripherals.stream().mapToDouble(Peripheral::getOverallPerformance).average().orElse(0))).append(System.lineSeparator());

        for (Peripheral peripheral : peripherals) {
            builder.append("  ").append(peripheral.toString()).append(System.lineSeparator());
        }

        return builder.toString().trim();
    }
}
