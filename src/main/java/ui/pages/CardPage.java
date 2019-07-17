package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import ui.core.BrowserFactory;
import ui.core.Elem;

public class CardPage extends BrowserFactory {
    private By saveChecklist = By.cssSelector(".primary.wide.confirm.js-add-checklist");
    private By checklistName = By.cssSelector(".editable.non-empty.checklist-title>.current.hide-on-edit");
    private By closeChecklistItem = By.cssSelector(".icon-lg.icon-close.dark-hover.cancel.js-cancel-checklist-item");
    private By addChecklistItem = By.cssSelector(".js-new-checklist-item-button.button.subtle.hide-on-edit");
    private By saveCheckItem = By.cssSelector(".primary.confirm.mod-submit-edit.js-add-checklist-item");
    private By newCard = By.xpath("//*[@class='list-cards u-fancy-scrollbar u-clearfix js-list-cards js-sortable ui-sortable']/a[2]");
    private By selectCheckItem = By.cssSelector(".checklist-item-row.js-checkitem-row.current.hide-on-edit");


    public Elem cardByUrlName(String urlName){
        return new Elem(By.cssSelector("[href='"+urlName.substring(18)+"']"), urlName);
    }

    public Elem openChecklist(){
        return new Elem(By.cssSelector("[title='Checklist']"));
    }

    public Elem inputChecklistName(){
        return new Elem(By.cssSelector("#id-checklist"));
    }

    public Elem addCheckItem(){
        return new Elem(addChecklistItem);
    }

    public Elem inputCheckItemName(){
        return new Elem(By.cssSelector(".edit.field.checklist-new-item-text.js-new-checklist-item-input"));
    }

    public Elem convertToCard(){
        return new Elem(By.cssSelector(".js-convert-to-card"));
    }

    public Elem convertNewCard(){
        return new Elem(newCard);
    }

    public Elem openCheckItem(){
        return new Elem(selectCheckItem);
    }

    public Elem actionItemMenu(){
        return new Elem(By.cssSelector(".button.subtle.hide-on-edit.js-confirm-delete"));
    }

    public Elem deleteCheckItem(){
        return new Elem(By.cssSelector(".js-confirm.full.negate"));
    }

    @Step("Open card")
    public void openCard(String urlName){
        cardByUrlName(urlName).click();
    }


    @Step
    public void addChecklist(){
        openChecklist().click();
        inputChecklistName().type("test_checklist_name");
        driver.findElement(saveChecklist).click();
        driver.findElement(closeChecklistItem).click();

    }

    @Step
    public void addChecklistItem() throws InterruptedException {
        addCheckItem().click();
        inputCheckItemName().type("test_check_item_name");
        driver.findElement(saveCheckItem).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector(".checklist-item-menu-wrapper.hide-on-edit")).click();
        convertToCard().click();
        driver.findElement(By.cssSelector(".icon-lg.icon-close.dialog-close-button.js-close-window")).click();
        convertNewCard().find();
        Assert.assertEquals("test_check_item_name", driver.findElement(newCard).getText());
    }

    @Step
    public void deleteCheckList(){
        actionItemMenu().click();
        deleteCheckItem().click();
    }

    @Step
    public String getChecklist(){
        return driver.findElement(checklistName).getText();
    }

}

