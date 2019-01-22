package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.sun.org.apache.xpath.internal.FoundIndex;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FindTransPage extends CommonPage {


    private SelenideElement tranID = $(By.id("criteria.transactionId"));
    private ElementsCollection findBtn = $$(By.xpath("//*[@type='submit']"));


    public SearchTranResult setTranID(String tid) {

        tranID.waitUntil(Condition.visible, 30000, 5).setValue(tid);
        findBtn.get(0).click();
        return new SearchTranResult();
    }








}
