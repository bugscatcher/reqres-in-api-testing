package com.github.bugscatcher;

import com.github.bugscatcher.data.UserData;
import com.github.bugscatcher.dto.register.TokenDTO;
import com.github.bugscatcher.dto.register.UserRegisterDTO;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginTests extends Abstract {
    @Test(description = "POST. LOGIN (SUCCESSFUL).", dataProviderClass = UserData.class, dataProvider = "registerUser")
    @Description("Testing the logon.")
    public void testLogin_200(String email, String password) throws IOException {
        UserRegisterDTO newUser = new UserRegisterDTO().setEmail(email).setPassword(password);
        Response response = createResource(newUser, "/login");
        assertThat(response.getStatusCode(), equalTo(200));
        TokenDTO token = response.as(TokenDTO.class);
        assertThat(token.getToken(), is(notNullValue()));
    }

    @Test(description = "POST. LOGIN (UNSUCCESSFUL).", dataProviderClass = UserData.class, dataProvider = "registerUser")
    @Description("Testing the logon.")
    public void testLogin_400_1(String email, String password) throws IOException {
        UserRegisterDTO newUser = new UserRegisterDTO().setEmail(email);
        Response response = createResource(newUser, "/login");
        assertThat(response.getStatusCode(), equalTo(400));
        assertThat(response.asString(), Matchers.containsString("Missing password"));
    }

    @Test(description = "POST. LOGIN (UNSUCCESSFUL).", dataProviderClass = UserData.class, dataProvider = "registerUser")
    @Description("Testing the logon.")
    public void testLogin_400_2(String email, String password) throws IOException {
        UserRegisterDTO newUser = new UserRegisterDTO().setPassword(password);
        Response response = createResource(newUser, "/login");
        assertThat(response.getStatusCode(), equalTo(400));
        assertThat(response.asString(), Matchers.containsString("Missing email or username"));
    }
}
