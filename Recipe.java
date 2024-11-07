// src/Recipe.java
public class Recipe {
    private String title;        // Title of the recipe
    private String ingredients;  // Ingredients needed for the recipe
    private String instructions; // Instructions to prepare the recipe

    // Constructor to initialize the recipe
    public Recipe(String title, String ingredients, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Getter for the title of the recipe
    public String getTitle() {
        return title;
    }

    // Getter for the ingredients of the recipe
    public String getIngredients() {
        return ingredients;
    }

    // Getter for the instructions of the recipe
    public String getInstructions() {
        return instructions;
    }

    // Override the toString method for displaying the recipe
    @Override
    public String toString() {
        return "Title: " + title + "\n" +
               "Ingredients: " + ingredients + "\n" +
               "Instructions: " + instructions + "\n";
    }
}
