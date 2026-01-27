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

    @FindBy(xpath = "//div[@class='day']/div[@data-social='item']")
    public List<WebElement> appointments;

    public Map<String, Boolean> checkLabelsInAppointment(int row) {
        Map<String, Boolean> labelStatus = new HashMap<>();
        WebElement card = appointments.get(row);

        labelStatus.put("SerialLabel", !card.findElements(By.xpath(".//h6[contains(text(), 'Serial')]")).isEmpty());
        labelStatus.put("DeptLabel", !card.findElements(By.xpath(".//h6[contains(text(), 'Departments')]")).isEmpty());
        labelStatus.put("DoctorLabel", !card.findElements(By.xpath(".//h6[contains(text(), 'Doctor')]")).isEmpty());

        return labelStatus;
    }

    public Map<String, String> getAppointmentData(int row) {
        Map<String, String> data = new HashMap<>();
        WebElement card = appointments.get(row);

        data.put("Date", card.findElement(By.xpath(".//h5")).getText());
        data.put("Serial", card.findElement(By.xpath(".//h6[contains(text(), 'Serial')]")).getText().replace("Serial ", ""));

        List<WebElement> doc = card.findElements(By.xpath(".//h6[contains(text(), 'Doctor')]"));
        data.put("Doctor", doc.isEmpty() ? "N/A" : doc.get(0).getText().replace("Doctor ", ""));

        return data;
    }

    public String getAppointmentDescription(int row) {
        WebElement card = appointments.get(row);
        try {
            return card.findElement(By.xpath(".//div[contains(@class,'card-description')]/p")).getText();
        } catch (Exception e) {
            return "";
        }
    }
}