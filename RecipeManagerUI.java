import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeManagerUI {
    private static final String USERS_FILE = "users.txt";
    private static final String RECIPES_FILE = "recipes.txt";
    
    private List<User> users;          // List to store registered users
    private List<Recipe> recipes;      // List to store recipes
    private User loggedInUser;         // Currently logged-in user
    private JFrame loginFrame;         // Login window
    private JFrame registerFrame;      // Registration window
    private JFrame recipeFrame;        // Recipe management window

    public RecipeManagerUI() {
        users = new ArrayList<>();
        recipes = new ArrayList<>();
        loggedInUser = null;

        // Load users and recipes from file
        loadUsersFromFile();
        loadRecipesFromFile();

        createLoginWindow();
    }

    // Load users from a file
    private void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("No users found, starting fresh.");
        }
    }

    // Save users to a file
    private void saveUsersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                bw.write(user.getUsername() + "," + user.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load recipes from a file
    private void loadRecipesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(RECIPES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    recipes.add(new Recipe(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No recipes found, starting fresh.");
        }
    }

    // Save recipes to a file
    private void saveRecipesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RECIPES_FILE))) {
            for (Recipe recipe : recipes) {
                bw.write(recipe.getTitle() + ";" + recipe.getIngredients() + ";" + recipe.getInstructions());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createLoginWindow() {
        loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(new JLabel("Username: "));
        panel.add(usernameField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginFrame.add(panel);
        loginFrame.setVisible(true);

        // Action listener for login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            login(username, password);
        });

        // Action listener for register button
        registerButton.addActionListener(e -> {
            createRegisterWindow();
            loginFrame.setVisible(false);
        });
    }

    private void createRegisterWindow() {
        registerFrame = new JFrame("Register");
        registerFrame.setSize(300, 250);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JTextField registerUsernameField = new JTextField();
        JPasswordField registerPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        panel.add(new JLabel("Username: "));
        panel.add(registerUsernameField);
        panel.add(new JLabel("Password: "));
        panel.add(registerPasswordField);
        panel.add(new JLabel("Confirm Password: "));
        panel.add(confirmPasswordField);
        panel.add(registerButton);
        panel.add(backButton);

        registerFrame.add(panel);
        registerFrame.setVisible(true);

        // Action listener for register button
        registerButton.addActionListener(e -> {
            String username = registerUsernameField.getText();
            String password = new String(registerPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            register(username, password, confirmPassword);
        });

        // Action listener for back button
        backButton.addActionListener(e -> {
            registerFrame.dispose();
            loginFrame.setVisible(true);
        });
    }

    private void createRecipeWindow() {
        recipeFrame = new JFrame("Recipe Manager");
        recipeFrame.setSize(400, 300);
        recipeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recipeFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search Recipe: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addRecipeButton = new JButton("Add Recipe");
        JButton viewRecipesButton = new JButton("View Recipes");

        buttonPanel.add(addRecipeButton);
        buttonPanel.add(viewRecipesButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        recipeFrame.add(mainPanel);
        recipeFrame.setVisible(true);

        // Action listeners for recipe buttons
        addRecipeButton.addActionListener(e -> openAddRecipeFrame());
        viewRecipesButton.addActionListener(e -> viewRecipes());

        // Action listener for search button
        searchButton.addActionListener(e -> searchRecipes(searchField.getText()));
    }

    private void openAddRecipeFrame() {
        JFrame addRecipeFrame = new JFrame("Add Recipe");
        addRecipeFrame.setSize(400, 300);
        addRecipeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addRecipeFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JTextField titleField = new JTextField();
        JTextArea ingredientsArea = new JTextArea(3, 20);
        JTextArea instructionsArea = new JTextArea(3, 20);
        JButton addButton = new JButton("Add Recipe");
        JTextArea feedbackArea = new JTextArea(5, 20);
        feedbackArea.setEditable(false);

        panel.add(new JLabel("Recipe Title: "));
        panel.add(titleField);
        panel.add(new JLabel("Ingredients: "));
        panel.add(new JScrollPane(ingredientsArea));
        panel.add(new JLabel("Instructions: "));
        panel.add(new JScrollPane(instructionsArea));
        panel.add(addButton);
        panel.add(new JScrollPane(feedbackArea));

        addRecipeFrame.add(panel);
        addRecipeFrame.setVisible(true);

        addButton.addActionListener(e -> addRecipe(titleField, ingredientsArea, instructionsArea, feedbackArea));
    }

    private void viewRecipes() {
        JFrame recipeListFrame = new JFrame("View Recipes");
        recipeListFrame.setSize(400, 300);
        recipeListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        recipeListFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(recipes.size() + 1, 1)); // +1 for header

        panel.add(new JLabel("Select a Recipe:"));

        // Create a button for each recipe
        for (Recipe recipe : recipes) {
            JButton recipeButton = new JButton(recipe.getTitle());
            recipeButton.addActionListener(e -> showRecipeDetails(recipe));
            panel.add(recipeButton);
        }

        recipeListFrame.add(panel);
        recipeListFrame.setVisible(true);
    }

    private void showRecipeDetails(Recipe recipe) {
        JFrame recipeDetailFrame = new JFrame("Recipe Details");
        recipeDetailFrame.setSize(400, 300);
        recipeDetailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        recipeDetailFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea recipeDetailsArea = new JTextArea();
        recipeDetailsArea.setText("Title: " + recipe.getTitle() + "\nIngredients: " + recipe.getIngredients() + "\nInstructions: " + recipe.getInstructions());
        recipeDetailsArea.setEditable(false);

        panel.add(new JScrollPane(recipeDetailsArea), BorderLayout.CENTER);

        recipeDetailFrame.add(panel);
        recipeDetailFrame.setVisible(true);
    }

    private void login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                loginFrame.dispose();
                createRecipeWindow();
                return;
            }
        }
        JOptionPane.showMessageDialog(loginFrame, "Invalid login credentials!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void register(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerFrame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(registerFrame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        saveUsersToFile(); // Save updated user list to file
        JOptionPane.showMessageDialog(registerFrame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        registerFrame.dispose();
        loginFrame.setVisible(true);
    }

    private void addRecipe(JTextField titleField, JTextArea ingredientsArea, JTextArea instructionsArea, JTextArea feedbackArea) {
        String title = titleField.getText();
        String ingredients = ingredientsArea.getText();
        String instructions = instructionsArea.getText();

        if (title.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            feedbackArea.setText("All fields are required.");
            return;
        }

        Recipe newRecipe = new Recipe(title, ingredients, instructions);
        recipes.add(newRecipe);
        saveRecipesToFile();  // Save recipes to file

        feedbackArea.setText("Recipe added successfully.");
        titleField.setText("");
        ingredientsArea.setText("");
        instructionsArea.setText("");
    }

    private void searchRecipes(String searchQuery) {
        List<Recipe> searchResults = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (recipe.getTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(recipe);
            }
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(recipeFrame, "No recipes found for the search query.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JFrame searchResultFrame = new JFrame("Search Results");
            searchResultFrame.setSize(400, 300);
            searchResultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            searchResultFrame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(searchResults.size() + 1, 1)); // +1 for header

            panel.add(new JLabel("Search Results:"));

            for (Recipe recipe : searchResults) {
                JButton recipeButton = new JButton(recipe.getTitle());
                recipeButton.addActionListener(e -> showRecipeDetails(recipe));
                panel.add(recipeButton);
            }

            searchResultFrame.add(panel);
            searchResultFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecipeManagerUI::new);
    }
}
