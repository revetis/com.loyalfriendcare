package pages.admin_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class AdminDashboardPages {

    public AdminDashboardPages() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // =======================
    // ADMIN SIDEBAR - USERS MENU
    // =======================

    // Users menu item (li container)
    @FindBy(xpath = "//span[text()='Users']/ancestor::li")
    public WebElement adminSidebarUsersLi;

    // Users menu button/link
    @FindBy(xpath = "//span[text()='Users']/ancestor::a")
    public WebElement adminSidebarUsersButton;

    // All Users submenu links
    @FindBy(xpath = "//span[text()='Users']/ancestor::li//ul/li")
    public List<WebElement> adminSidebarUsersSubLinks;

    // Users list submenu
    @FindBy(xpath = "//a[normalize-space()='Users' and contains(@href,'Dashboard/Users')]")
    public WebElement usersListSubMenu;

    // Create User submenu
    @FindBy(xpath = "//a[normalize-space()='Create User' and contains(@href,'Users/create')]")
    public WebElement createUserSubMenu;

    // =======================
    // ADMIN SIDEBAR - MEDICINES MENU
    // =======================

    // Medicines menu item (li container)
    @FindBy(xpath = "//span[text()='Medicines']/ancestor::li")
    public WebElement adminSidebarMedicinesLi;

    // Medicines menu button/link
    @FindBy(xpath = "//span[normalize-space()='Medicines']/ancestor::a")
    public WebElement adminSidebarMedicinesButton;

    // All Medicines submenu links
    @FindBy(xpath = "//span[text()='Medicines']/ancestor::li//ul/li")
    public List<WebElement> adminSidebarMedicinesSubLinks;

    // Medicines list submenu
    @FindBy(xpath = "//span[normalize-space()='Medicines']/ancestor::li//a[normalize-space()='Medicines']")
    public WebElement medicinesListSubMenu;
}