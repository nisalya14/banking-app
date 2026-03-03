package com.banking.util;

public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        return email != null &&
               email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidPassword(String password) {
        return password != null &&
               password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }

    public static boolean isValidName(String name) {
        return name != null &&
               name.matches("^[A-Za-z ]{3,30}$");
    }
}