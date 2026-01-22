package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common_pages.Layout;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;

public class US07 extends TestBaseRapor {

    @Test
    public void tc01(){
        extentTest = extentReports.createTest(  "Register sayfasina erisim",
                                            "Kullanici kayit sayfasina ve formuna erisim saglayabilmeli");


        Driver.getDriver().get(ConfigReader.getProperty("url"));

        Layout layout = new Layout();
        Assert.assertTrue(layout.signUpLink.isDisplayed());
        Assert.assertTrue(layout.signUpLink.isEnabled());


        layout.signUpLink.click();

        String expectedUrlIcerik = "register";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expectedUrlIcerik));

    }
}
