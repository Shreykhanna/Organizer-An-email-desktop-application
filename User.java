package model;

import java.util.ArrayList;

public class User {
    private String userName, password;
    
    public User(String userName, String password){
        this.userName=userName;
        this.password=password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class UserAccounts{
    private String userName;
    private ArrayList<User> accounts;
    
    UserAccounts(String userName, ArrayList<User> accounts){
        this.userName=userName;
        this.accounts=accounts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<User> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<User> accounts) {
        this.accounts = accounts;
    }
    
}