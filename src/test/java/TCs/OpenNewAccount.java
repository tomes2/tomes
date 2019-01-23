package TCs;

import org.testng.annotations.Test;

public class OpenNewAccount extends BasicTest {


    @Test(dataProvider = "inputDataLogin")
    public void openNewAccount(
            String www,
            String login,
            String pwd
    ) throws InterruptedException {

        mp.start(www)
                .login(login, pwd)
                .openNewAccount()
                .setAccountType()
                .amountFromAccount()
                .clickOpenAccountBtn()
                .saveNewAccId()
                .openAccountsOverview()
                .openAccountDetails()
                // .test()
                // .assertAccountNumber();
                .assertCheck();


    }


}
