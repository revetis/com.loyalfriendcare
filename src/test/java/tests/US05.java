package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.user_pages.HomePageBodySection;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class US05 extends TestBaseRapor {

    @Test
    public void US_005_TC01_BodySectionTitlesVisible() {
        extentTest = extentReports.createTest("US_05_TC01_BodySectionTitlesVisible","Anasayfa body bölümündeki başlıkların görünürlüğünü doğrular");

        Driver.getDriver().get(ConfigReader.getProperty("url"));

        HomePageBodySection homesection = new HomePageBodySection();

        ReusableMethods.waitForVisibility(homesection.departmentsTitle, 5);
        ReusableMethods.scrollToElement(homesection.departmentsTitle);
        Assert.assertTrue(homesection.departmentsTitle.isDisplayed());
        extentTest.pass("US_05_TC01: Anasayfa body bölümündeki başlıkların görünürlüğü başarıyla doğrulandı.");



    }
}
