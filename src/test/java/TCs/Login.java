package TCs;

import org.testng.annotations.Test;

public class Login extends BasicTest {


    @Test(dataProvider = "inputDataLogin")
    public void Login(
            String www,
            String login,
            String pwd
    ){
//test
        mp.start(www)
                .login(login,pwd)
                .assertAccount();


    }










}
