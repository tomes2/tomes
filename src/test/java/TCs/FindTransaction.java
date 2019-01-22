package TCs;

import Pages.CommonPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FindTransaction extends BasicTest {


    @Test(dataProvider = "inputFindTran")
    public void openNewAccount(
            String www,
            String login,
            String pwd,
            String tranid
    ) throws InterruptedException {

        mp.start(www)
                .login(login, pwd)
                .openFindTrans()
                .setTranID(tranid)
                .tranResults(tranid);





    }


}