package ShoppingSpree;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Person> people;
        Map<String, Product> products;

        try {
            people = readMapOfPeople(scanner);
            products = readMapOfProducts(scanner);
        } catch (IllegalArgumentException ex) {
            return;
        }

        String[] input = scanner.nextLine().split("\\s+");

        while (!input[0].equals("END")) {
            String name = input[0];
            String productToBuy = input[1];

            if (people.containsKey(name) && products.containsKey(productToBuy)) {
                people.get(name).buyProduct(products.get(productToBuy));
            }

            input = scanner.nextLine().split("\\s+");
        }

        for (Map.Entry<String, Person> entry : people.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

    private static Map<String, Product> readMapOfProducts(Scanner scanner) {
        String[] peopleAndMoney = scanner.nextLine().split("[;=]");
        Map<String, Product> map = new LinkedHashMap<>();

        for (int i = 0; i < peopleAndMoney.length; i += 2) {

            try {
                map.put(peopleAndMoney[i], new Product(peopleAndMoney[i], Double.parseDouble(peopleAndMoney[i+1])));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return map;
    }

    private static Map<String, Person> readMapOfPeople(Scanner scanner) {
        String[] peopleAndMoney = scanner.nextLine().split("[;=]");
        Map<String, Person> map = new LinkedHashMap<>();

        for (int i = 0; i < peopleAndMoney.length; i += 2) {

            try {
                map.put(peopleAndMoney[i], new Person(peopleAndMoney[i], Double.parseDouble(peopleAndMoney[i+1])));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return map;
    }
}
