package service;

import model.BookingHistory;
import model.Cart;
import model.Product;
import model.User;
import utils.ProductUtils;
import utils.ReadInput;

import java.util.ArrayList;
import java.util.Date;

import static utils.Utils.print;

public class MainMenu implements MainMenuInterface {
    private User loggedInUser;
    private Auth auth;
    private ProductUtils productUtils;

    public MainMenu(Auth auth, User loggedInUser, ProductUtils productUtils) {
        this.auth = auth;
        this.loggedInUser = loggedInUser;
        this.productUtils = productUtils;
    }

    @Override
    public void printMainMenu() {
        print("1. Products");
        print("2. View History");
        print("3. View Points");
        print("4. Cart");
        print("5. Logout");
        print("Enter your choice:");
        int choice = ReadInput.getScanner().nextInt();

        switch (choice) {
            case 1 -> printProducts();
            case 2 -> printHistory();
            case 3 -> {
                print("Points - " + auth.getLoggedInUser().getPoint());
                print("99. Back");
                print("Enter your choice:");
                int pointChoice = ReadInput.getScanner().nextInt();
                if (pointChoice == 99) {
                    printMainMenu();
                }
            }
            case 4 -> printCart();
            case 5 -> auth.printAuthMenu();
            default -> print("Invalid Main Menu Choice");
        }
    }

    @Override
    public void printProducts() {
        productUtils.getProducts().forEach(product -> print(product.getId() + " - " + product.getName() + " - " + product.getPrice()));
        print("99. Back");
        print("Enter your choice:");
        int productChoice = ReadInput.getScanner().nextInt();
        if (productChoice == 99) {
            printMainMenu();
        }

        boolean isProductExits = productUtils.getProducts().stream().anyMatch(product -> product.getId() == productChoice);

        if (isProductExits) {
            Product product = productUtils.getProducts().get(productChoice - 1);
            ArrayList<Product> productsCart = loggedInUser.getCart().getProducts();
            productsCart.add(product);

            int amount = 0;
            for (Product pro : productsCart) {
                amount = amount + pro.getPrice();
            }

            Cart cart = loggedInUser.getCart();
            cart.setAmount(amount);
            cart.setProducts(productsCart);
            loggedInUser.setCart(cart);
            print("Product added to cart!!!");
        } else {
            print("Invalid Product Choice");
        }
        printProducts();
    }

    @Override
    public void printCart() {
        print("Cart Items!!!");
        print("---------");
        loggedInUser.getCart().getProducts().forEach(product -> {
            print(product.getId() + ". " + product.getName() + " - " + product.getPrice());
        });
        print("Total - " + loggedInUser.getCart().getAmount());
        print("97. Checkout");
        print("98. Delete Carts");
        print("99. Back");
        print("Enter your choice:");
        int cartChoice = ReadInput.getScanner().nextInt();
        if (cartChoice == 99) {
            printMainMenu();
        } else if (cartChoice == 98) {
            print("Enter the id to be deleted");
            int itemToBeDeleted = ReadInput.getScanner().nextInt();

            ArrayList<Product> cartProducts = loggedInUser.getCart().getProducts();

            for (Product product : cartProducts) {
                if (product.getId() == itemToBeDeleted) {
                    cartProducts.remove(product);
                    print("Product deleted");
                    break;
                }
            }
            printCart();
        } else if (cartChoice == 97) {

            BookingHistory bookingHistory = new BookingHistory();
            bookingHistory.setDate(new Date());
            bookingHistory.setProducts(loggedInUser.getCart().getProducts());

            ArrayList<BookingHistory> bookingHistories = loggedInUser.getBookingHistories();
            bookingHistories.add(bookingHistory);
            loggedInUser.setBookingHistories(bookingHistories);

            int points = loggedInUser.getPoint();
            int newPoints = (int) (points + (loggedInUser.getCart().getAmount() * 0.1));
            loggedInUser.setPoint(newPoints);
            print("Total to be paid -" + loggedInUser.getCart().getAmount());
            print("Point - " + newPoints);
            print("Thank you for shopping with us");
            printMainMenu();
        }
    }

    @Override
    public void printHistory() {
        print("History");
        print("---------");
        for (BookingHistory bookingHistory : loggedInUser.getBookingHistories()) {
            print(bookingHistory.getDate().toString());
            int total = 0;
            for (Product product : bookingHistory.getProducts()) {
                print(product.getName() + " - " + product.getPrice());
                total = total + product.getPrice();
            }
            print("Total - " + total);
            print("---------");
        }
        print("99. Back");
        print("Enter your choice:");
        int cartChoice = ReadInput.getScanner().nextInt();
        if (cartChoice == 99) {
            printMainMenu();
        }
    }
}
