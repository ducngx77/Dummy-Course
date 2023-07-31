package security.utils;

public class Utility {
    public static final String INVALID_PASSWORD_MESSAGE = "Password must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 number.";
    public static final String INVALID_EMAIL_MESSAGE = "Invalid email address";
    public static final String INVALID_USERNAME_MESSAGE = "Username must contain at least 5 characters. Only letters, spaces and numbers and '_' or '*' are allowed.";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "Invalid phone number. Phone number must start with 0 or +84 and have 10 digits (+84 is one digit) eg: <br> 0123456789 <br> +84123456789.";
    public static final String INVALID_ADDRESS_MESSAGE = "Address must contain at least 5 characters. Only letters, spaces and numbers and '_' or '*' are allowed.";

    public static String randomString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 6; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public static boolean checkNotNull(String... args) {
        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean emailValidation(String email) {
        return email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
    }

    public static boolean passwordValidation(String password) {
        return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
    }

    public static boolean usernameValidation(String username) {
        return username.matches("^[ a-zA-Z0-9_*]{5,}$");
    }

    public static boolean phoneNumberValidation(String phoneNumber) {
        return phoneNumber.matches("((\\+84)|(0))\\d{9}");
    }

    public static boolean addressValidation(String address) {
        return address.matches("^[a-zA-Z0-9_ ]{5,}$");
    }
}
