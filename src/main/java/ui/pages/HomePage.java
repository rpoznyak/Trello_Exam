package ui.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import ui.core.BrowserFactory;
import ui.core.Elem;

public class HomePage extends BrowserFactory {

    public Elem moreMenu(){
        return new Elem(By.cssSelector("[data-test-id='header-member-menu-button']"));
    }

    public Elem loggingOut(){
        return new Elem(By.cssSelector("[data-test-id='header-member-menu-logout']"));
    }

public void logout(){
    driver.findElement(By.cssSelector(".icon-lg.icon-close.dialog-close-button.js-close-window")).click();
    moreMenu().click();
    loggingOut().click();
    Assert.assertEquals(driver.getCurrentUrl(), "https://trello.com/logged-out");
}

}
