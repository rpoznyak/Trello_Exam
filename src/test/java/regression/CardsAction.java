package regression;

import api.TrelloRestClient;
import api.models.*;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import ui.core.BrowserFactory;
import ui.pages.BoardsPage;
import ui.pages.CardPage;
import ui.pages.HomePage;
import ui.pages.LoginPage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;

@Epic("Regression")
public class CardsAction extends BrowserFactory {

    public TrelloRestClient client = new TrelloRestClient();

    TrelloList listAction = new TrelloList();
    Board createdBoard = new Board();
    Card card = new Card("Test_Card_"+new Date().getTime());
    Checklist checklist = new Checklist();

    public LoginPage loginPage = new LoginPage();
    public BoardsPage boardsPage = new BoardsPage();
    public CardPage cardPage = new CardPage();
    public HomePage homePage = new HomePage();


    @BeforeSuite
    public void prepareData() throws IOException {
        createdBoard = client.boardsService.createBoard("Test_Board_"+new Date().getTime()).execute().body();
        listAction = client.listsService.createList(createdBoard.id, "Test_List_"+new Date().getTime()).execute().body();
        card = client.cardsService.createCard(listAction.id, card).execute().body();
    }

    //@AfterSuite
    public void clearData() throws IOException {
        client.boardsService.deleteBoard(createdBoard.id).execute();
    }
    @Feature("Preconditions")
    @Story("Login user")
    @Test(priority = 1)
    public void login(){
        loginPage.open();
        loginPage.login("test.qa9966@gmail.com", "test_Qa9966$");
        new WebDriverWait(driver, 10)
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.urlToBe("https://trello.com/ruslan58200195/boards"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://trello.com/ruslan58200195/boards");
    }


    @Feature("Preconditions")
    @Story("Open card created by API")
    @Test(dependsOnMethods = "login")
    @Severity(SeverityLevel.BLOCKER)
    public void openCard(){
        boardsPage.openBoard(createdBoard.url);
        cardPage.openCard(card.url);
    }


    @Feature("Add action")
    @Story("Add checklist")
    @Test(priority = 5)
    @Owner("Pozniak Ruslan")
    public void addChecklist() throws IOException {
        cardPage.addChecklist();
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500))
                .until(webdriver -> {
                    try {
                        card = client.cardsService.getCard(card.id).execute().body();
                        if(card.idChecklists[0].isEmpty()){
                            return false;}
                        else{
                            return card;}
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return false;
                    }
                });
        checklist = client.checklistService.getChecklist(card.idChecklists[0]).execute().body();
        Assert.assertEquals(cardPage.getChecklist(),checklist.name);
    }

    @Test(priority = 6)
    public void ConvertCheckItemToCard() throws InterruptedException {
        cardPage.addChecklistItem();
    }

    @Test(priority = 7)
    public void DeleteCardCheckList(){
        cardPage.openCard(card.url);
        cardPage.deleteCheckList();
    }

    @Test (priority = 8)
    public void logout(){
        homePage.logout();
    }
}
