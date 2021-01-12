package PizzaCalories;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] input = scanner.nextLine().split("\\s+");

        Pizza pizza = null;
        Dough dough;
        Topping topping;

        while (!input[0].equals("END")) {
            if (input[0].equals("Pizza")) {
                String nameOfThePizza = input[1];
                int numberOfToppings = Integer.parseInt(input[2]);

                try {
                    pizza = new Pizza(nameOfThePizza, numberOfToppings);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }
            } else if (input[0].equals("Dough")) {
                String typeOfDough = input[1];
                String bakingTechnique = input[2];
                double weight = Double.parseDouble(input[3]);

                try {
                    dough = new Dough(typeOfDough, bakingTechnique, weight);
                    pizza.setDough(dough);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }
            } else if (input[0].equals("Topping")) {
                String toppingType = input[1];
                double weight = Double.parseDouble(input[2]);

                try {
                    topping = new Topping(toppingType, weight);
                    pizza.addTopping(topping);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    return;
                }
            }

            input = scanner.nextLine().split("\\s+");
        }

        if (pizza != null){
            double calories = pizza.getOverallCalories();
            System.out.printf("%s - %.2f\n", pizza.getName(), calories);
        }

    }
}
