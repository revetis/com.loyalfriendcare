package pages.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v141.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentsPage {
    public AppointmentsPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//div[@class='pull-bottom bottom-left m-b-40 sm-p-l-15']")
    public WebElement pageTitle;

    @FindBy(xpath = "//div[@class='day']/div")
    public List<WebElement> appointments;

    public Map<String,WebElement> getAppointment(int row){
        Map<String,WebElement> appointment = new HashMap<>();
        appointment.put("card-header",appointments.get(row).findElement(By.xpath(".//div[@class='card-header clearfix']")));
        appointment.put("card-description",appointments.get(row).findElement(By.xpath(".//div[@class='card-description ']")));
        return appointment;
    }

}
