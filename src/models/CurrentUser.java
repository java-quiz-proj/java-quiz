package models;

import utils.LoginHandler;

public class CurrentUser {
    private static CurrentUser instance;
    private LoginHandler.User user;
    private LoginHandler loginHandler;

    private CurrentUser() {}

    // Get the current user
    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    // Set the user and utils.LoginHandler instance
    public void setCurrentUser(LoginHandler.User user, LoginHandler loginHandler) {
        this.user = user;
        this.loginHandler = loginHandler;
    }

    public LoginHandler.User getUser() {
        return user;
    }

    public LoginHandler getLoginHandler() {
        return loginHandler;
    }
}
