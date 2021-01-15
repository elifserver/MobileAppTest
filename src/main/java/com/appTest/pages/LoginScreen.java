package com.appTest.pages;

import com.appTest.base.Base;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.testng.Assert;

public class LoginScreen extends Base {

    @iOSXCUITFindBy(accessibility = "email")
    @AndroidFindBy(accessibility = "email")
    public MobileElement email;

    @iOSXCUITFindBy(accessibility = "password")
    @AndroidFindBy(accessibility = "password")
    public MobileElement password;

    @iOSXCUITFindBy(accessibility = "login")
    @AndroidFindBy(accessibility = "login")
    public MobileElement loginButton;

    public TaskScreen login(String userName, String pwd) {
        email.sendKeys(userName);
        password.sendKeys(pwd);
        loginButton.click();
        return new TaskScreen();

    }

}
