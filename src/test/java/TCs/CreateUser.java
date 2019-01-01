package TCs;

import TCs.BasicTest;
import org.testng.annotations.Test;

public class CreateUser extends BasicTest {


    public CreateUser() throws Exception {
    }

    @Test(dataProvider = "inputData")
    public void CreateAccount(
            String www,
            String firstnameVal,
            String lastnameVal,
            String addressVal,
            String cityVal,
            String stateVal,
            String zipVal,
            String phoneVal,
            String ssnVal,
            String unVal,
            String pass1Val,
            String pass2Val


    ) throws InterruptedException {


        mp.start(www)
                .enterReg()
                .enterValues(firstnameVal, lastnameVal, addressVal, cityVal, stateVal, zipVal, phoneVal, ssnVal, unVal, pass1Val, pass2Val)
                .createAccount();

    }

}
