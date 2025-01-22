package models;

import utils.LoginHandler;

public class CurrentUser {
    private static CurrentUser instance;
    private LoginHandler.User user;

    private CurrentUser() {}

    // Get the current user
    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    // Set the user and utils.LoginHandler instance
    public void setCurrentUser(LoginHandler.User user) {
        this.user = user;
    }

    public LoginHandler.User getUser() {
        return user;
    }
}
