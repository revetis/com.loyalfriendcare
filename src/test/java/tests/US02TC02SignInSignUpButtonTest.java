package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US02TC02SignInSignUpButtonTest extends TestBaseRapor {

    Layout layout;

    @Test
    public void SignInSignUpButtonTest() {

        extentTest = extentReports.createTest("US_02_TC_02 - Sign In Sign Up Button Test",
                "Sign In ve Sign Up butonlarının görünürlüğünü, konumunu ve rengini doğrulama");

        Driver.getDriver().get(ConfigReader.getProperty("url"));
        ReusableMethods.waitForPageToLoad(15);
        layout = new Layout();

        // 1. Header bölümünün sağ tarafında Sign in ve Sign Up butonlarının olduğunu kontrol et
        Assert.assertTrue(layout.signInLink.isDisplayed());
        Assert.assertTrue(layout.signUpLink.isDisplayed());
        extentTest.pass("Sign In ve Sign Up butonları header'da bulundu");

        // 2. Sign In butonunun yeşil renkte ve görünür olduğunu doğrula
        ReusableMethods.waitForVisibility(layout.signInLink, 10);
        Assert.assertTrue(layout.signInLink.isDisplayed());

        String signInBackgroundColor = layout.signInLink.getCssValue("background-color");
        Assert.assertTrue(signInBackgroundColor.contains("0, 148, 49"),
                "Sign In butonu yeşil değil! Renk: " + signInBackgroundColor);
        extentTest.pass("Sign In butonu yeşil renkte ve görünür");

        // 3. Sign Up butonunun yeşil renkte ve görünür olduğunu doğrula
        ReusableMethods.waitForVisibility(layout.signUpLink, 10);
        Assert.assertTrue(layout.signUpLink.isDisplayed());

        String signUpBackgroundColor = layout.signUpLink.getCssValue("background-color");
        Assert.assertTrue(signUpBackgroundColor.contains("0, 148, 49"),
                "Sign Up butonu yeşil değil! Renk: " + signUpBackgroundColor);
        extentTest.pass("Sign Up butonu yeşil renkte ve görünür");

        // 4. Her iki butonun sağ tarafta yan yana konumlandığını doğrula
        int signInX = layout.signInLink.getLocation().getX();
        int signUpX = layout.signUpLink.getLocation().getX();
        int signInY = layout.signInLink.getLocation().getY();
        int signUpY = layout.signUpLink.getLocation().getY();

    } // main method sonu
}
