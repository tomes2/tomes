package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;

public class AccountPage {



    private SelenideElement title = $(By.xpath("//h1[@class='title']"));



    public void assertAccount(){

        Assert.assertEquals(title.waitUntil(Condition.visible,30000,5).getText(),"Accounts Overview");
    }







}
