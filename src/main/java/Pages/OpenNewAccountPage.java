package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class OpenNewAccountPage extends CommonPage{

   // public String newAccNr;

    private SelenideElement accountType = $(By.id("type"));
    private SelenideElement openBtn = $(By.xpath("//input[@value='Open New Account']"));
    private SelenideElement fromAccount = $(By.id("fromAccountId"));
    private SelenideElement accNr = $(By.id("newAccountId"));
    private SelenideElement openOverview = $(By.xpath("//a[contains(@href,'/parabank/overview.htm')]"));




    public OpenNewAccountPage setAccountType() throws InterruptedException {


        accountType.waitUntil(Condition.visible, 2000, 4).selectOptionContainingText("SAVINGS");
        //  accountType.selectOptionByValue("SAVINGS");


        //Thread.sleep(5000);
        return this;
    }


    public OpenNewAccountPage amountFromAccount() {


        fromAccount.waitUntil(Condition.visible, 20000, 4).selectOptionContainingText("13566");
        return this;
    }


    public OpenNewAccountPage clickOpenAccountBtn() {

        openBtn.waitUntil(Condition.visible, 20000, 4).click();

        return this;
    }


    public OpenNewAccountPage saveNewAccId(){


        newAccNr=accNr.waitUntil(Condition.visible,20000,4).getText();
      //  System.out.println(newAccNr);

        return this;
    }



    public AccountsOverviewPage openAccountsOverview()
    {
        openOverview.waitUntil(Condition.visible,20000,4).click();
        return new AccountsOverviewPage();
    }


}