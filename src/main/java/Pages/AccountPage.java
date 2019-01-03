package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;

public class AccountPage extends CommonPage{


    private SelenideElement title = $(By.xpath("//h1[@class='title']"));
    private SelenideElement openNewAccount = $(By.xpath("//a[contains(@href,'/parabank/openaccount.htm')]"));


    public void assertAccount() {   //assertion for login

        Assert.assertEquals(title.waitUntil(Condition.visible, 30000, 5).getText(), "Accounts Overview");
    }


    public OpenNewAccountPage openNewAccount() {

        openNewAccount.waitUntil(Condition.visible, 20000, 4).click();

        return new OpenNewAccountPage();
    }


}
