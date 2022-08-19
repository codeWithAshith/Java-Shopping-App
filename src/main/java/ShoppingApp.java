import model.Product;
import model.User;
import service.Auth;
import service.MainMenu;
import utils.ProductUtils;
import utils.Utils;

public class ShoppingApp {
    public static void main(String[] args) {
        Utils.welcome();
        ProductUtils productUtils = new ProductUtils();
        productUtils.loadProduct();

        Auth auth = new Auth();
        auth.printAuthMenu();

        User loggedInUser = auth.getLoggedInUser();
        Utils.print("Login Successful");
        MainMenu mainMenu = new MainMenu(auth, loggedInUser, productUtils);
        mainMenu.printMainMenu();


    }
}
