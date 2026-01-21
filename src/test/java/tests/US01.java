package tests;

import org.testng.annotations.Test;
import utilities.TestBaseRapor;

public class US01 extends TestBaseRapor {

    @Test
    public void test01(){
        extentTest = extentReports.createTest("DashboardTest");

    }
}
