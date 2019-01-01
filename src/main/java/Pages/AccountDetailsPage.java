package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;

public class AccountDetailsPage extends CommonPage {


    private SelenideElement createdAccNumber = $(By.id("accountId"));


    public AccountDetailsPage test() throws InterruptedException {
        System.out.println(createdAccNumber.waitUntil(Condition.visible, 30000, 5).getText());
        System.out.println("zmienna "+newAccNr);
        Thread.sleep(5000);
        return this;
    }

    public void assertAccountNumber() {   //assertion for created account

        Assert.assertEquals(createdAccNumber.waitUntil(Condition.visible, 30000, 5).getText(), newAccNr);
    }


}
