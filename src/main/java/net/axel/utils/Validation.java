package net.axel.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validation {


    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }


    public static boolean isValidPhone(String phone) {
        String phonePattern = "\\d{10}";
        return isNotEmpty(phone) && Pattern.matches(phonePattern, phone);
    }


    public static boolean isValidDate(String dateInput) {
        try {
            LocalDate.parse(dateInput);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidBoolean(String input) {
        return input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no");
    }

}
