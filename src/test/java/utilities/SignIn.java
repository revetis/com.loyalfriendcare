package utilities;

import pages.common_pages.Layout;
import pages.common_pages.LoginPage;

public class SignIn {

    private static void performSignIn(String emailKey, String passwordKey) {
        Layout layout = new Layout();
        layout.signInLink.click();

        LoginPage loginPage = new LoginPage();

        String email = ConfigReader.getProperty(emailKey);
        String password = ConfigReader.getProperty(passwordKey);

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("HATA: configuration.properties dosyasında '" + emailKey + "' veya '" + passwordKey + "' eksik!");
        }

        loginPage.emailAddressInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.signInButton.click();
    }

    public static void signInUser() {
        performSignIn("user_email", "user_password");
    }

    public static void signInAdmin() {
        performSignIn("admin_email", "admin_password");
    }
}