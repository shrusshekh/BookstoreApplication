package coe528.project;
//importing all of the necessary java fx libraries
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.io.*;
//imports for file writing
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
//import for checkbox
import javafx.scene.control.CheckBox;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private Customer currentCustomer;
    private Stage primaryStage;

    // creating observable list for books for the manager and customer tables and for the customer table
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private ObservableList<Book> booksCustomer = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    // creating list of customer
    private List<Customer> customerList = new ArrayList<>();

    TableView<Book> booksTable; //table of Book called booksTable
    TableView<Customer> customersTable; //table of Customer called customersTable


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Bookstore");

        // Load data from files when the app starts.
        loadBooks();
        loadCustomers();

        Scene loginScene = LoginScene();
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // When the user clicks the exit button, save the current data.
        primaryStage.setOnCloseRequest(e -> {
            saveBooks();
            saveCustomers();
        });
    }

    private void saveBooks() {
    try (FileWriter fw = new FileWriter("books.txt")) { // overwrites the file
        for (Book book : books) {
            // Format: bookName,bookPrice
            fw.write(book.getBookName() + "," + book.getBookPrice() + "\n");
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

private void saveCustomers() {
    try (FileWriter fw = new FileWriter("customers.txt")) { // overwrites the file
        for (Customer customer : customers) {
            // Format: username,password,points,status
            fw.write(customer.getUsername() + "," + customer.getPassword() + "," 
                     + customer.getPoints() + "," + customer.getStatus() + "\n");
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}


private void loadBooks() {
    books.clear();
    try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                books.add(new Book(name, price));
            }
        }
    } catch (IOException ex) {
        // File may not exist the first time the app is run.
    }
}

private void loadCustomers() {
    customers.clear();
    try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String username = parts[0];
                String password = parts[1];
                double points = Double.parseDouble(parts[2]);
                // creating customer with the given username, password, points, and points
                customers.add(new Customer(username, password, points));
            }
        }
    } catch (IOException ex) {
    }
}


    private Scene LoginScene() {
        // button to login
        // username, password field 
        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(200);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        // Layout for Login Screen
        VBox layoutLogin = new VBox(10);
        layoutLogin.setAlignment(Pos.CENTER);

        Label loginErrorLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // if the username and password are "admin" send to owner scene
            if (username.equals("admin") && password.equals("admin")) {
                primaryStage.setScene(ownerScene());
                return;
            }


            Customer foundCustomer = null;
            for (Customer c : customers) {

                if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                    foundCustomer = c; //getting username and password for customer and having that as found customer
                    break;
                }
            }

            if (foundCustomer != null) { //if the found customer is not null
                currentCustomer = foundCustomer; //and the current customer matches to the found customer
                primaryStage.setScene(customerStartScene());//send to the customer scene
            } else {
                loginErrorLabel.setText("Incorrect password or username."); //if not equal to found customer credentials then incorrect
            }
        });


        layoutLogin.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, loginErrorLabel); //adding everything to the layout scene
        return new Scene(layoutLogin, 700, 500);
    }

    // ownerScene
    private Scene ownerScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Hello Owner");

        Button booksButton = new Button("Books");
        booksButton.setOnAction(e -> {
            primaryStage.setScene(ownerBooksScreen());
        });

        Button customersButton = new Button("Customers");
        customersButton.setOnAction(e -> {
           primaryStage.setScene(manageCustomersScene());
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> primaryStage.setScene(LoginScene()));

        layout.getChildren().addAll(welcomeLabel, booksButton, customersButton, logoutButton);
        return new Scene(layout, 700, 500);
    }


// Books Management Screen
private Scene ownerBooksScreen() {
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);

    // Table for Books
    TableColumn<Book, String> bookNameColumn = new TableColumn<>("Book Name"); //creating a new column called book name
    bookNameColumn.setMinWidth(200); //setting width of column
    bookNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName")); //setting the cells to be the values of bookName

    TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Book Price");
    bookPriceColumn.setMinWidth(100);
    bookPriceColumn.setCellValueFactory(new PropertyValueFactory<Book, Double>("bookPrice"));

    booksTable = new TableView<>();
    booksTable.setItems(books);
    booksTable.getColumns().addAll(bookNameColumn, bookPriceColumn);//adding the columns to the table

    layout.getChildren().addAll(booksTable);

    Button addBookButton = new Button("Add Book");
    addBookButton.setOnAction(e -> {
        primaryStage.setScene(addBookScene()); // Navigate to Add Book screen
    });

    Button removeBookButton = new Button("Remove Book");
    removeBookButton.setOnAction(e -> {
        primaryStage.setScene(deleteBookScene()); // Navigate to Delete Book screen
    });


    Button backButton = new Button("Back");
    backButton.setOnAction(e -> primaryStage.setScene(ownerScene())); // Go back to owner scene

    // Add buttons to layout
    layout.getChildren().addAll(addBookButton, removeBookButton, backButton); // Add removeBookButton here

    return new Scene(layout, 700, 600);
}



    private Scene deleteBookScene(){
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Label bookNameLabel = new Label("Book Name:");
        TextField bookNameField = new TextField();

        Label bookPriceLabel = new Label("Book Price:");
        TextField bookPriceField = new TextField();    

        Button deleteBookButton = new Button("Delete Book");

        deleteBookButton.setOnAction(e -> {
            String bookName = bookNameField.getText();
            String bookPriceString = bookPriceField.getText();
        try {
            double bookPrice = Double.parseDouble(bookPriceString); // Parse the price to double

            // Iterate through the ObservableList to find the book
            Book bookToDelete = null;
            for (Book book : books) {
                if (book.getBookName().equals(bookName) && book.getBookPrice() == bookPrice) {
                    bookToDelete = book;
                    break;
                }
            }

            if (bookToDelete != null) {
                // If a matching book is found, remove it from the list
                books.remove(bookToDelete);
                Label bookRemoveSuccess = new Label();
                bookRemoveSuccess.setText("Book deleted: " + bookName);
            } else {
                Label bookRemoveNotSuccess = new Label();
                bookRemoveNotSuccess.setText("Book not found.");
            }

            // Clear text fields after deletion
            bookNameField.clear();
            bookPriceField.clear();

        } catch (NumberFormatException ex) {
                Label invalidPrice = new Label();
                invalidPrice.setText("Invalid price input.");
        }
    });

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> primaryStage.setScene(ownerBooksScreen())); // Go back to owner scene

    layout.getChildren().addAll(bookNameLabel, bookNameField, bookPriceLabel, bookPriceField, deleteBookButton, backButton);

    return new Scene(layout, 700, 500);
}

    private Scene addBookScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Create input fields for book details
        Label bookNameLabel = new Label("Book Name:");
        TextField bookNameField = new TextField();

        Label bookPriceLabel = new Label("Book Price:");
        TextField bookPriceField = new TextField();

        Button addBookButton = new Button("Add Book");

        // Add action for the Add Book button
        addBookButton.setOnAction(e -> {
            String bookName = bookNameField.getText();
            String bookPriceString = bookPriceField.getText();

            try {
                double bookPrice = Double.parseDouble(bookPriceString); // Parse the price
                Book newBook = new Book(bookName, bookPrice);

                // Add new book to the ObservableList (this will update the table automatically)
                books.add(newBook);

                // Clear text fields after adding book
                bookNameField.clear();
                bookPriceField.clear();
            } catch (NumberFormatException ex) {
                Label invalidPrice = new Label();
                invalidPrice.setText("Invalid price input.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(ownerBooksScreen())); // Go back to owner scene

        layout.getChildren().addAll(bookNameLabel, bookNameField, bookPriceLabel, bookPriceField, addBookButton, backButton);

        return new Scene(layout, 700, 500);
    }

    // manageCustomersScene
private Scene manageCustomersScene() {
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);

    // TableView to display customers
    TableView<Customer> customersTable = new TableView<>();
    customersTable.setItems(customers); 

    // columns for the username password and points
    TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
    usernameColumn.setMinWidth(200);
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
    passwordColumn.setMinWidth(200);
    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

    TableColumn<Customer, Double> pointsColumn = new TableColumn<>("Points");
    pointsColumn.setMinWidth(100);
    pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

    customersTable.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);

    layout.getChildren().add(customersTable);

    // Define buttons after adding the table
    Button addCustomer = new Button("Add Customer");
    addCustomer.setOnAction(e -> {
        primaryStage.setScene(addCustomerScene()); // Navigate to Add Customer scene
    });

    Button removeCustomer = new Button("Remove Customer");
    removeCustomer.setOnAction(e -> {
        primaryStage.setScene(deleteCustomerScene()); // Navigate to Delete Customer scene
    });

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> primaryStage.setScene(ownerScene())); // Go back to owner scene

    // Add buttons to layout
    layout.getChildren().addAll(addCustomer, removeCustomer, backButton);

    return new Scene(layout, 700, 600); // Return scene at the end
}

    // add customer scene
    private Scene addCustomerScene() {
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);

    // Username input
    Label usernameLabel = new Label("Username:");
    TextField usernameInput = new TextField();
    usernameInput.setPromptText("Enter Username");

    // Password input
    Label passwordLabel = new Label("Password:");
    TextField passwordInput = new TextField();
    passwordInput.setPromptText("Enter Password");

    // Add button
    Button addButton = new Button("Add");
    addButton.setOnAction(e -> {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        // Input validation
        if (username.isEmpty() || password.isEmpty()) {
                Label notFound = new Label();
                notFound.setText("Username and Password cannot be empty!");
            return;
        }

        // Add new customer with default points = 0
        Customer newCustomer = new Customer(username, password, 0.0);
        customers.add(newCustomer); 


        try (FileWriter fw = new FileWriter("customers.txt", true)) { //file writer to write to the customer.txt file
                fw.write(newCustomer.getUsername() + "," + newCustomer.getPassword() + ","
             + newCustomer.getPoints() + "," + newCustomer.getStatus() + "\n");


        } catch (IOException ex) {
                Label error = new Label();
                error.setText("Error while writing file");
                }

        Label addCustomerSuccess = new Label();
        addCustomerSuccess.setText("Customer added successfully.");
        primaryStage.setScene(manageCustomersScene()); // Navigate back to the table
    });

    // Back button
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> primaryStage.setScene(manageCustomersScene())); // Go back to owner scene

    // Layout setup
    layout.getChildren().addAll(
        usernameLabel, usernameInput,
        passwordLabel, passwordInput,
        addButton, backButton
    );

    return new Scene(layout, 700, 500);
}

    // delete customer scene
    private Scene deleteCustomerScene() {
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);

    Label instructionLabel = new Label("Select a customer and click Delete:");

    // TableView to display customers
    TableView<Customer> deleteTable = new TableView<>();
    deleteTable.setItems(customers); 

    // columsn for the username, password and points
    TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
    usernameColumn.setMinWidth(200);
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
    passwordColumn.setMinWidth(200);
    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

    TableColumn<Customer, Double> pointsColumn = new TableColumn<>("Points");
    pointsColumn.setMinWidth(100);
    pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

    deleteTable.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);

    // Delete Button
    Button deleteButton = new Button("Delete");
    deleteButton.setOnAction(e -> {
        Customer selectedCustomer = deleteTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            customers.remove(selectedCustomer); // Remove from the ObservableList
            Label deleteCustomerSuccess = new Label();
            deleteCustomerSuccess.setText("Customer deleted successfully." + selectedCustomer.getUsername());           
        } else {
            Label deleteCustomerNotSuccess = new Label();
            deleteCustomerNotSuccess.setText("Customer not deleted.");
        }
    });

    // Back Button
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> primaryStage.setScene(manageCustomersScene())); // Go back to owner scene

    // Layout setup
    layout.getChildren().addAll(instructionLabel, deleteTable, deleteButton, backButton);

    return new Scene(layout, 700, 500);
}


// customer screen
private Scene customerStartScene() {
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);

    Label welcomeLabel = new Label("Hello " + currentCustomer.getUsername() + ". You have " 
            + currentCustomer.getPoints() + " points. Your status is " + currentCustomer.getStatus() + ".");

    // Display a table of available books (using the books list)
    TableView<Book> booksTableCustomer = new TableView<>();
    booksTableCustomer.setItems(books);
    TableColumn<Book, String> bookNameColumn = new TableColumn<>("Book Name");
    bookNameColumn.setMinWidth(200);
    bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
    TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Book Price");
    bookPriceColumn.setMinWidth(100);
    bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
    TableColumn<Book, CheckBox> selectColumn = new TableColumn<>("Select"); //adding the check box column
    selectColumn.setMinWidth(100);
    selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
    booksTableCustomer.getColumns().addAll(bookNameColumn, bookPriceColumn, selectColumn);

    Button buyBooksButton = new Button("Buy");
    buyBooksButton.setOnAction(e -> {
        currentCustomer.shoppingCart.clear();
        // Add all selected books into the shopping cart
        for (Book book : books) {
            if (book.getSelect().isSelected()) {
                currentCustomer.addToCart(book);
            }
        }
        currentCustomer.redeemPoints = false;//redeem points is false
        currentCustomer.Purchase(); //call purchase method for buy not redeem and buy 
        primaryStage.setScene(customerCostScene());
    });

    Button redeemPointsAndBuyButton = new Button("Redeem Points and Buy"); //similar to before but now when calling purchase it will run redeem and buy
    redeemPointsAndBuyButton.setOnAction(e -> {
        currentCustomer.shoppingCart.clear();
        // Add all selected books into the shopping cart
        for (Book book : books) {
            if (book.getSelect().isSelected()) {
                currentCustomer.addToCart(book);
            }
        }
        currentCustomer.redeemPoints = true; //redeem points is true
        currentCustomer.Purchase();  //run purchase method for redeem and buy
        primaryStage.setScene(customerCostScene());
    });

    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(e -> primaryStage.setScene(LoginScene()));

    layout.getChildren().addAll(welcomeLabel, booksTableCustomer, buyBooksButton, redeemPointsAndBuyButton, logoutButton);
    return new Scene(layout, 700, 600);
}

// This is the customerCostScene method that creates the new scene after a purchase.
private Scene customerCostScene() {
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);

    double totalCost = currentCustomer.getLastTransactionCost();
    double points = currentCustomer.getPoints();
    String status = currentCustomer.getStatus();

    Label totalCostLabel = new Label("Total Cost: " + totalCost);
    Label infoLabel = new Label("Points: " + points + ", Status: " + status);

    Button backButton = new Button("Back"); 
    backButton.setOnAction(e -> primaryStage.setScene(customerStartScene()));

    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(e -> primaryStage.setScene(LoginScene()));

    layout.getChildren().addAll(totalCostLabel, infoLabel, logoutButton, backButton);
    return new Scene(layout, 700, 600);
}


//adding book class to run in main
public class Book {
    private String bookName;
    private double bookPrice;
    private CheckBox select;

    public Book(String bookName, double bookPrice) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.select = new CheckBox();
    }

    public String getBookName() {
        return bookName;
    }

    public double getBookPrice() {
        return bookPrice;
    }

          // Getter for the CheckBox
  public CheckBox getSelect() {
        return select;
    }

    // Setter for the CheckBox
  public void setSelect(CheckBox select) {
        this.select = select;
    } 



}

//adding customer class to run in main
public class Customer extends User {
    // Creating all the variables
    private double points;
    private String status;
    public List<Book> shoppingCart = new ArrayList<>();
    public List<Book> purchased = new ArrayList<>();
    protected State customerState; // assuming CustomerState is an interface or class
    private boolean redeemPoints; // boolean for redeeming points 
    private double lastTransactionCost;  
    private double pointsAfterRedeem;

    // Creating the customer constructor
  public Customer(String username, String password, double points) {
        super(username, password);
        this.points = 0;
        this.status = "Silver";
        this.customerState = new SilverState();
    }
  private void updateStatus(double points) 
  {
        if (points >= 1000) {
            status = "Gold";
            customerState = new GoldState(); 
        } else {
            status = "Silver";
            customerState = new SilverState();
        }
    }
  public double getPoints() 
  {
    return points;
  }

  public String getStatus() 
  {
    return status;
  }

  public void setStatus(State newState) {
    this.customerState = newState;
    this.status = newState.getStatus();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public void addToCart(Book book) {
        shoppingCart.add(book);
    }

    public void viewTransactions() {
        for (Book book : purchased) {
            System.out.println("Here is your transaction history: \n" 
                + book.getBookName() + " " + book.getBookPrice() + "\n");
        }
    }

    public void getCustomer(String username, String password, double points)
    {

    this.username = username;
    this.password = password;
    this.points= points;
    }


    public void Purchase() {
        double totalCost = 0;
        for (Book book : shoppingCart) {
            totalCost += book.getBookPrice();
        }

        if (redeemPoints) {
            double pointsToCash = points / 100.0;
            double newCost = totalCost - pointsToCash;
            if (newCost < 0) {
                newCost = 0;
            }
            points = 0; // Redeem all current points.

            points += newCost * 10; // 10 points / $1
            // Use the net cost as the total cost for this transaction.
            totalCost = newCost;
        } else {
            // only buying, no redeeming
            points += totalCost * 10;
        }

        updateStatus(points);
        purchased.addAll(shoppingCart);
        shoppingCart.clear();

        
        lastTransactionCost = totalCost;
        pointsAfterRedeem = points - (totalCost*10);
    }

    public double getLastTransactionCost() {
        return lastTransactionCost;
    }

    public double pointsAfterRedeem(){
        return pointsAfterRedeem;
    }
}
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

}
