import com.appTest.pages.TaskScreen;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.appTest.base.Base;
import com.appTest.pages.LoginScreen;

import java.lang.reflect.Method;


public class LoginTest extends Base {
    LoginScreen loginPO ;
    TaskScreen taskScreen;

    @Test
    public void loginWithValidEmailValidPassword() {


        loginPO = new LoginScreen();
        //PageFactory.initElements(new AppiumFieldDecorator(driver),loginPO);

        taskScreen = loginPO.login(objTestData.getJSONObject("validUser").getString("email"), objTestData.getJSONObject("validUser").getString("password"));
        Assert.assertTrue(Boolean.FALSE);
    }
}
