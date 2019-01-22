package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SearchTranResult extends CommonPage {


    public SearchTranResult tranResults(String tid) {
        String link;
        link = "//a[contains(@href,'/parabank/transaction.htm?id=" + tid + "')]";
        SelenideElement account = $(By.xpath(link));
        account.waitUntil(Condition.visible, 20000, 4).click();

        return this;
    }


}
