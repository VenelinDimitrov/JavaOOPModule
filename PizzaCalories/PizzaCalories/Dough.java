package PizzaCalories;

import java.util.Map;

public class Dough {
    private final Map<String, Double> TYPES_OF_FLOUR = Map.of("White", 1.5, "Wholegrain", 1.0);
    private final Map<String, Double> BAKING_TECHNIQUES = Map.of("Crispy", 0.9, "Chewy", 1.1, "Homemade", 1.0);
    private String flourType;
    private String bakingTechnique;
    private double weight;

    public Dough(String flourType, String bakingTechnique, double weight) {
        this.setFlourType(flourType);
        this.setBakingTechnique(bakingTechnique);
        this.setWeight(weight);
    }

    private void setWeight(double weight) {
        if (weight < 1 || weight > 200){
            throw new IllegalArgumentException("Dough weight should be in the range [1..200].");
        }
        this.weight = weight;
    }

    private void setFlourType(String flourType) {
        if (!TYPES_OF_FLOUR.containsKey(flourType)) {
            throw new IllegalArgumentException("Invalid type of dough.");
        }
        this.flourType = flourType;
    }

    private void setBakingTechnique(String bakingTechnique) {
        if (!BAKING_TECHNIQUES.containsKey(bakingTechnique)) {
            throw new IllegalArgumentException("Invalid type of dough.");
        }
        this.bakingTechnique = bakingTechnique;
    }

    public double calculateCalories() {
        return (2 * this.weight) * TYPES_OF_FLOUR.get(this.flourType) * BAKING_TECHNIQUES.get(this.bakingTechnique);
    }
}
