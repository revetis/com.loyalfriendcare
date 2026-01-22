package pages.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentsPage {

    public DepartmentsPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//h3[contains(text(),'Departments')]")
    public WebElement departmentsHeader;

    @FindBy(xpath = "//a[contains(@class,'btn-success')]")
    public WebElement addDepartmentsButton;

    @FindBy(id = "search-table")
    public WebElement searchBox;

    @FindBy(xpath = "//table[@id='tableWithSearch']/tbody/tr")
    public List<WebElement> allRows;

    @FindBy(xpath = "//table[@id='tableWithSearch']/tbody/tr/td[3]/p")
    public List<WebElement> departmentNames;

    @FindBy(xpath = "//a[contains(text(),'Next')]")
    public WebElement nextButton;

    public Map<String, WebElement> getTableRowMap(int row) {
        List<WebElement> cells = allRows.get(row).findElements(By.tagName("td"));
        Map<String, WebElement> rowElements = new HashMap<>();

        try {
            rowElements.put("image", cells.get(0).findElement(By.tagName("img")));
            rowElements.put("order", cells.get(1).findElement(By.tagName("p")));
            rowElements.put("name", cells.get(2).findElement(By.tagName("p")));
            rowElements.put("color", cells.get(3).findElement(By.tagName("div")));

            if (cells.size() > 4 && !cells.get(4).findElements(By.tagName("a")).isEmpty()) {
                rowElements.put("editButton", cells.get(4).findElement(By.tagName("a")));
            }

            if (cells.size() > 5 && !cells.get(5).findElements(By.tagName("button")).isEmpty()) {
                rowElements.put("deleteButton", cells.get(5).findElement(By.tagName("button")));
            }

        } catch (Exception e) {
            System.out.println("Sıra " + row + " için elementler bulunamadı: " + e.getMessage());
        }

        return rowElements;
    }
}