package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class AccountsOverviewPage extends CommonPage {


    public AccountDetailsPage openAccountDetails() throws InterruptedException {


        //  System.out.println(newAccNr);

        String link;
        link = "//a[contains(@href,'/parabank/activity.htm?id=" + newAccNr + "')]";
        //  System.out.println(link);
        SelenideElement account = $(By.xpath(link));
        account.waitUntil(Condition.visible, 20000, 4).click();


        return new AccountDetailsPage();
    }


}
