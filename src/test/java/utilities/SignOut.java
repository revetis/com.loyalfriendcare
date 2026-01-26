package utilities;

import pages.common_pages.Layout;

public class SignOut {

    public static void signOutUser(){
        Layout layout = new Layout();
        try {
            ReusableMethods.waitForVisibility(layout.headerAuthUserSignOutButton,2);
            layout.headerAuthUserSignOutButton.click();
        } catch (Exception e) {
            System.out.println("Sign out olurken hata olustu: "+e.getMessage());
        }
    }

    public static void signOutAdmin(){
        Layout layout = new Layout();
        try {
            ReusableMethods.waitForVisibility(layout.headerAuthAdminSignOutButton,2);
            layout.headerAuthAdminSignOutButton.click();
        } catch (Exception e) {
            System.out.println("Sign out olurken hata olustu: "+e.getMessage());
        }
    }
}
