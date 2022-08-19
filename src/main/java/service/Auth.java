package service;

import model.User;
import utils.ReadInput;
import utils.UsersUtils;

import java.util.ArrayList;

import static utils.Utils.print;

public class Auth implements AuthInterface {

    private final UsersUtils usersUtils = new UsersUtils();
    private ArrayList<User> users;

    private User loggedInUser;

    public Auth() {
        users = usersUtils.getUsers();
    }


    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void printAuthMenu() {

        while (true) {
            print("1. Login");
            print("2. Register");
            print("3. Exit");
            print("Enter your choice");

            int choice = ReadInput.getScanner().nextInt();

            if (choice == 1) {
                print("UserName:");
                String userName = ReadInput.getScanner().next();
                print("Password:");
                String password = ReadInput.getScanner().next();

                boolean isUserExists = users.stream().anyMatch(user ->
                        (user.getUsername().equals(userName)) && (user.getPassword().equals(password))
                );

                if (isUserExists) {
                    loggedInUser = users.stream().filter(user ->
                                    (user.getUsername().equals(userName)) && (user.getPassword().equals(password)))
                            .findFirst()
                            .get();
                    break;
                } else {
                    print("Invalid user!!!");
                }

            } else if (choice == 2) {
                print("UserName:");
                String userName = ReadInput.getScanner().next();
                print("Email:");
                String email = ReadInput.getScanner().next();
                print("Password:");
                String password = ReadInput.getScanner().next();

                boolean isUserExists = users.stream().anyMatch(user ->
                        (user.getUsername().equals(userName)) && (user.getEmail().equals(email))
                );

                if (isUserExists) {
                    print("User already exists");
                } else {
                    User newUser = new User();
                    newUser.setId(users.size() + 1);
                    newUser.setEmail(email);
                    newUser.setUsername(userName);
                    newUser.setPassword(password);
                    users.add(newUser);

                    usersUtils.setUsers(users);
                    print("Registration Successful!");
                }


            } else if (choice == 3) {
                System.exit(0);
            } else {
                print("Invalid choice");
            }
        }
    }
}
