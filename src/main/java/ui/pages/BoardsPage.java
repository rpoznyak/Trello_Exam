package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ui.core.BrowserFactory;
import ui.core.Elem;

public class BoardsPage extends BrowserFactory {
    private static final String PATH = "ruslan58200195/boards";

    public Elem boardByUrlName(String urlName){
        return new Elem(By.cssSelector(".board-tile[href*='"+urlName.substring(18)+"']"), urlName);
    }

    @Step("Open board")
    public void openBoard(String urlName){
        boardByUrlName(urlName).click();
    }



}
