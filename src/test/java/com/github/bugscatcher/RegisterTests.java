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

public class RegisterTests extends Abstract {
    @Test(description = "POST. REGISTER (SUCCESSFUL).", dataProviderClass = UserData.class, dataProvider = "registerUser")
    @Description("Testing the registration of a new user.")
    public void testRegister_201(String email, String password) throws IOException {
        UserRegisterDTO newUser = new UserRegisterDTO().setEmail(email).setPassword(password);
        Response response = createResource(newUser, "/register");
        assertThat(response.getStatusCode(), equalTo(201));
        TokenDTO token = response.as(TokenDTO.class);
        assertThat(token.getToken(), is(notNullValue()));
    }

    @Test(description = "POST. REGISTER (UNSUCCESSFUL).", dataProviderClass = UserData.class, dataProvider = "registerUser")
    @Description("Testing the registration of a new user.")
    public void testRegister_400_1(String email, String password) throws IOException {
        UserRegisterDTO newUser = new UserRegisterDTO().setEmail(email);
        Response response = createResource(newUser, "/register");
        assertThat(response.getStatusCode(), equalTo(400));
        assertThat(response.asString(), Matchers.containsString("Missing password"));
    }

    @Test(description = "POST. REGISTER (UNSUCCESSFUL).", dataProviderClass = UserData.class, dataProvider = "registerUser")
    @Description("Testing the registration of a new user.")
    public void testRegister_400_2(String email, String password) throws IOException {
        UserRegisterDTO newUser = new UserRegisterDTO().setPassword(password);
        Response response = createResource(newUser, "/register");
        assertThat(response.getStatusCode(), equalTo(400));
        assertThat(response.asString(), Matchers.containsString("Missing email or username"));
    }
}
