package onlineShop.core;

import onlineShop.core.interfaces.Controller;
import onlineShop.models.products.Product;
import onlineShop.models.products.components.*;
import onlineShop.models.products.computers.Computer;
import onlineShop.models.products.computers.DesktopComputer;
import onlineShop.models.products.computers.Laptop;
import onlineShop.models.products.peripherals.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.*;

public class ControllerImpl implements Controller {
    private List<Computer> computers;
    private List<Component> components;
    private List<Peripheral> peripherals;


    public ControllerImpl() {
        computers = new ArrayList<>();
        components = new ArrayList<>();
        peripherals = new ArrayList<>();
    }

    @Override
    public String addComputer(String computerType, int id, String manufacturer, String model, double price) {
        for (Computer computer : computers) {
            if (computer.getId() == id) {
                throw new IllegalArgumentException(EXISTING_COMPUTER_ID);
            }
        }

        Computer computer;

        switch (computerType) {
            case "DesktopComputer":
                computer = new DesktopComputer(id, manufacturer, model, price);
                break;
            case "Laptop":
                computer = new Laptop(id, manufacturer, model, price);
                break;
            default:
                throw new IllegalArgumentException(INVALID_COMPUTER_TYPE);
        }

        computers.add(computer);
        return String.format(ADDED_COMPUTER, id);
    }

    @Override
    public String addPeripheral(int computerId, int id, String peripheralType, String manufacturer, String model, double price, double overallPerformance, String connectionType) {
        checkIfComputerExists(computerId);

        for (Peripheral peripheral : peripherals) {
            if (peripheral.getId() == id) {
                throw new IllegalArgumentException(EXISTING_PERIPHERAL_ID);
            }
        }

        Peripheral peripheral;
        switch (peripheralType) {
            case "Headset":
                peripheral = new Headset(id,manufacturer,model,price,overallPerformance,connectionType);
                break;
            case "Keyboard":
                peripheral = new Keyboard(id,manufacturer,model,price,overallPerformance,connectionType);
                break;
            case "Monitor":
                peripheral = new Monitor(id,manufacturer,model,price,overallPerformance,connectionType);
                break;
            case "Mouse":
                peripheral = new Mouse(id,manufacturer,model,price,overallPerformance,connectionType);
                break;
            default:
                throw new IllegalArgumentException(INVALID_PERIPHERAL_TYPE);
        }

        for (Computer computer : computers) {
            if (computer.getId() == computerId) {
                computer.addPeripheral(peripheral);
            }
        }

        peripherals.add(peripheral);

        return String.format(ADDED_PERIPHERAL, peripheralType, id, computerId);
    }

    @Override
    public String removePeripheral(String peripheralType, int computerId) {
        checkIfComputerExists(computerId);

        int index = -1;
        for (int i = 0; i < computers.size(); i++) {
            if (computers.get(i).getId() == computerId) {

                for (int j = 0; j < computers.get(i).getPeripherals().size(); j++) {
                    if (computers.get(i).getPeripherals().get(j).getClass().getSimpleName().equals(peripheralType)) {
                        computers.get(i).removePeripheral(peripheralType);
                        index = j;
                    }
                }
            }
        }

        if (index >= 0) {
            int id = peripherals.remove(index).getId();
            return String.format(REMOVED_PERIPHERAL, peripheralType, id);
        } else {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_PERIPHERAL, peripheralType, super.getClass().getName(), computerId));
        }
    }

    @Override
    public String addComponent(int computerId, int id, String componentType, String manufacturer, String model, double price, double overallPerformance, int generation) {
        checkIfComputerExists(computerId);

        for (Component component : components) {
            if (component.getId() == id) {
                throw new IllegalArgumentException(EXISTING_COMPONENT_ID);
            }
        }

        Component component;
        switch (componentType) {
            case "CentralProcessingUnit":
                component = new CentralProcessingUnit(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "Motherboard":
                component = new Motherboard(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "PowerSupply":
                component = new PowerSupply(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "RandomAccessMemory":
                component = new RandomAccessMemory(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "SolidStateDrive":
                component = new SolidStateDrive(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "VideoCard":
                component = new VideoCard(id, manufacturer, model, price, overallPerformance, generation);
                break;
            default:
                throw new IllegalArgumentException(INVALID_COMPONENT_TYPE);
        }

        for (Computer computer : computers) {
            if (computer.getId() == computerId) {
                computer.addComponent(component);
            }
        }

        components.add(component);

        return String.format(ADDED_COMPONENT, componentType, id, computerId);
    }

    @Override
    public String removeComponent(String componentType, int computerId) {
        checkIfComputerExists(computerId);

        int componentIndex = -1;
        int computerIndex = -1;
        boolean componentExists = false;
        for (int i = 0; i < computers.size(); i++) {
            if (computers.get(i).getId() == computerId) {
                computerIndex = i;
                for (int j = 0; j < computers.get(i).getComponents().size(); j++) {
                    if (computers.get(i).getComponents().get(j).getClass().getSimpleName().equals(componentType)) {
                        computers.get(i).removeComponent(componentType);
                        componentExists = true;
                        componentIndex = j;
                        break;
                    }
                }
            }
        }

        if (componentExists){
            int id = components.remove(componentIndex).getId();
            return String.format(REMOVED_COMPONENT, componentType, id);
        } else {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_COMPONENT, componentType,
                    computers.get(computerIndex).getClass().getSimpleName(), computerId));
        }
    }
    
    @Override
    public String buyComputer(int id) {
        checkIfComputerExists(id);

        int index = -1;
        for (int i = 0; i < computers.size(); i++) {
            if (computers.get(i).getId() == id) {
                index = i;
            }
        }

        if (index >=0){
            return computers.remove(index).toString();
        }
        return null;
    }

    @Override
    public String BuyBestComputer(double budget) {
        computers = computers.stream().filter(c -> c.getPrice() <= budget)
                .sorted((p1, p2) -> Double.compare(p2.getOverallPerformance(), p1.getOverallPerformance())).collect(Collectors.toList());

        if (computers.isEmpty()){
            throw new IllegalArgumentException(String.format(CAN_NOT_BUY_COMPUTER, budget));
        }

        return computers.remove(0).toString();
    }

    @Override
    public String getComputerData(int id) {
        checkIfComputerExists(id);

        int index = -1;
        for (int i = 0; i < computers.size(); i++) {
            if (computers.get(i).getId() == id) {
                index = i;
            }
        }

        return computers.get(index).toString();
    }

    private boolean checkIfComputerExists(int computerId) {
        for (Computer currentComp : computers) {
            if (currentComp.getId() == computerId) {
                return true;
            }
        }

        throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
    }
}
