// src/RecipeManager.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecipeManager {
    private List<User> users;          // List to store registered users
    private List<Recipe> recipes;      // List to store recipes
    private Scanner scanner;           // Scanner for user input
    private User loggedInUser;         // Currently logged-in user

    public RecipeManager() {
        users = new ArrayList<>();     // Initialize user list
        recipes = new ArrayList<>();   // Initialize recipe list
        scanner = new Scanner(System.in); // Initialize scanner
    }

    public void start() {
        while (true) {
            System.out.println("Recipe Manager");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    if (login()) {
                        userMenu();
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User newUser = new User(username, password);
        users.add(newUser);
        System.out.println("User registered successfully!");
    }

    private boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful!");
                return true;
            }
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    private void userMenu() {
        while (true) {
            System.out.println("User Menu");
            System.out.println("1. Add Recipe");
            System.out.println("2. View Recipes");
            System.out.println("3. Delete Recipe");
            System.out.println("4. Search Recipe");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    addRecipe();
                    break;
                case 2:
                    viewRecipes();
                    break;
                case 3:
                    deleteRecipe();
                    break;
                case 4:
                    searchRecipe();
                    break;
                case 5:
                    loggedInUser = null;
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
   
        
            }}
        }
    private void addRecipe() {
        System.out.print("Enter recipe title: ");
        String title = scanner.nextLine();
        System.out.print("Enter ingredients: ");
        String ingredients = scanner.nextLine();
        System.out.print("Enter instructions: ");
        String instructions = scanner.nextLine();

        Recipe recipe = new Recipe(title, ingredients, instructions);
        recipes.add(recipe);
        System.out.println("Recipe added successfully!");
    }

    private void viewRecipes() {
        if (recipes.isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }

        // Show only recipe titles
        System.out.println("Available Recipes:");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getTitle());
        }

        // Allow user to select a recipe
        System.out.print("Select a recipe number to view details or 0 to go back: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        if (choice > 0 && choice <= recipes.size()) {
            Recipe selectedRecipe = recipes.get(choice - 1);
            System.out.println(selectedRecipe); // Display the full details of the selected recipe
        } else if (choice == 0) {
            System.out.println("Going back to the user menu.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
    private void deleteRecipe() {
        if (recipes.isEmpty()) {
            System.out.println("No recipes available to delete.");
            return;
        }

        // Show only recipe titles
        System.out.println("Available Recipes to Delete:");
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getTitle());
        }

        // Allow user to select a recipe to delete
        System.out.print("Select a recipe number to delete or 0 to go back: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        if (choice > 0 && choice <= recipes.size()) {
            Recipe removedRecipe = recipes.remove(choice - 1);
            System.out.println("Recipe '" + removedRecipe.getTitle() + "' deleted successfully!");
        } else if (choice == 0) {
            System.out.println("Going back to the user menu.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
    private void searchRecipe() {
        System.out.print("Enter the recipe title to search: ");
        String searchTitle = scanner.nextLine();
        boolean found = false;

        // Search for the recipe by title
        for (Recipe recipe : recipes) {
            if (recipe.getTitle().equalsIgnoreCase(searchTitle)) {
                System.out.println("Recipe found:");
                System.out.println(recipe);
                found = true;
                break; // Exit loop after finding the first match
            }
        }

        if (!found) {
            System.out.println("Recipe with title '" + searchTitle + "' not found.");
        }
    }

    public static void main(String[] args) {
        RecipeManager manager = new RecipeManager();
        manager.start();
    }
}
