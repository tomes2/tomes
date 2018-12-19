package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class MainPage {


    //---register
    //http://parabank.parasoft.com/parabank/index.htm
    private SelenideElement reg = $(By.linkText("Register"));


    // ----login
    private SelenideElement login = $(By.xpath("//input[@name='username']"));
    private SelenideElement pwd = $(By.xpath("//input[@name='password']"));
    private SelenideElement logBtn = $(By.xpath("//input[@value='Log In']"));


    public MainPage start(String www) {

        clearBrowserCache();
        open(www);
        return this;
    }


    public RegPage enterReg() throws InterruptedException {

        reg.waitUntil(Condition.visible, 40000, 5).click();

        return new RegPage();
    }


    public AccountPage login(String log, String pass) {
        login.waitUntil(Condition.visible, 25000, 4).setValue(log);
        pwd.setValue(pass);
        logBtn.click();

        return new AccountPage();
    }


}
