package tests;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.common_pages.Layout;
import utilities.*;

import java.util.List;

public class US27 extends TestBaseRapor {

    @BeforeMethod
    public void setupSteps(){
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        Layout layout = new Layout();
        SignIn.signInAdmin();
        ReusableMethods.waitForClickability(layout.headerAuthAdminDashboardButton,Integer.parseInt(ConfigReader.getProperty("timeout"))).click();
    }

    @AfterMethod
    public void teardownTests(ITestResult result){
       if (result.isSuccess()){
           Driver.getDriver().get(ConfigReader.getProperty("url"));
           SignOut.signOutAdmin();
       }
    }

    @Test
    public void TC_01_YonetimPanelindeSidebarMenuKontrolu() throws InterruptedException {
        extentTest = extentReports.createTest("TC_01", "TC01 - Yonetim Panelinde Sidebar Menu Kontrolu");
        SoftAssert softAssert = new SoftAssert();
        Integer timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));

        Layout layout = new Layout();

        Point hoverdanOnceSidebarXKonumu = layout.adminSidebar.getLocation();
        ReusableMethods.hover(layout.adminSidebar);
        Point hoverdanSonraSidebarXKonumu = layout.adminSidebar.getLocation();
        softAssert.assertTrue(hoverdanSonraSidebarXKonumu.getX() >= hoverdanOnceSidebarXKonumu.getX(), "Sidebar Genislemedi veya hareket etmedi");

        softAssert.assertFalse(layout.adminSidebarAdminToolList.isEmpty(), "Sidebarda Yonetim Araclari Yok!");

        for (int i = 0; i < layout.adminSidebarAdminToolList.size(); i++) {
            WebElement toolWrapper = layout.adminSidebarAdminToolList.get(i);

            if (!toolWrapper.isDisplayed() || toolWrapper.getAttribute("class").contains("d-none")) {
                continue;
            }

            WebElement tool = layout.getTool(i);
            try {
                ReusableMethods.waitForVisibility(tool, timeout);
                softAssert.assertTrue(tool.isDisplayed(), i + ". sıradaki ana menü görünür değil.");
                ReusableMethods.waitForClickability(tool, timeout);
            } catch (TimeoutException e) {
                softAssert.fail(i + ". siradaki eleman (" + tool.getText() + ") etkileşime hazır değil.");
            }
        }

        for (int i = 0; i < layout.adminSidebarAdminToolList.size(); i++) {
            WebElement toolWrapper = layout.adminSidebarAdminToolList.get(i);

            if (!toolWrapper.isDisplayed() || toolWrapper.getAttribute("class").contains("d-none")) continue;

            List<WebElement> subList = layout.getToolSubList(i);
            if (subList.isEmpty()) continue;

            WebElement tool = layout.getTool(i);

            ReusableMethods.hover(layout.adminSidebar);
            ReusableMethods.waitForClickability(tool, timeout);

            try {
                tool.click();
            } catch (Exception e) {
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(300);
                tool.click();
            }

            try {
                ReusableMethods.waitForVisibility(subList.getFirst(), timeout);
            } catch (Exception ignored) {}

            for (int j = 0; j < subList.size(); j++) {
                WebElement subItem = subList.get(j);
                String errorMsg = "Sidebar menü: " + tool.getText() + " -> Alt link " + j + " (" + subItem.getText() + ") görünür/tıklanabilir değil.";

                softAssert.assertTrue(subItem.isDisplayed(), errorMsg);
                try {
                    ReusableMethods.waitForClickability(subItem, 2);
                } catch (Exception e) {
                    softAssert.fail(errorMsg + " [Tıklanabilirlik Hatası]");
                }
            }
        }

        softAssert.assertAll();
    }

}
