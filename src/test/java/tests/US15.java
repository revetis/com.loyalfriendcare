package tests;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.common_pages.Layout;
import pages.common_pages.LoginPage;
import pages.common_pages.RegisterPage;
import pages.user_pages.UserPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US15 extends TestBaseRapor {

    Layout layout = new Layout();
    RegisterPage registerPage = new RegisterPage();
    LoginPage loginPage = new LoginPage();
    Actions actions = new Actions(Driver.getDriver());
    UserPage userPage = new UserPage();
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void tc01(){

        extentTest = extentReports.createTest("Departments sayfası kontrolü",
                                            "");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        layout.signInLink.click();
        loginPage.emailAddressInput.sendKeys(ConfigReader.getProperty("user_email"));
        loginPage.passwordInput.sendKeys(ConfigReader.getProperty("user_password"));
        loginPage.signInButton.click();

        actions.moveToElement(layout.headerDepartmentsLink).perform();

        ReusableMethods.bekle(1);

        Assert.assertTrue(userPage.userDepartmentsInHeaderDropdownMenu.isDisplayed());

        String expectedDepartmentsList = userPage.departmentsList.getText();
        String actualDepartmentsList = userPage.userDepartmentsInHeaderDropdownMenu.getText();

        softAssert.assertEquals(actualDepartmentsList,expectedDepartmentsList,
                "Body bölümünde bulunan departman listesi ile header'da bulunan departman listesi eşleşmiyor");

        ReusableMethods.bekle(2);

        layout.headerDepartmentsLink.click();

        String expectedUrlIcerik = "Departments";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertEquals(actualUrl,expectedUrlIcerik);










        softAssert.assertAll();
    }







}
