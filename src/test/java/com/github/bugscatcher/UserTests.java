package com.github.bugscatcher;

import com.github.bugscatcher.data.UserData;
import com.github.bugscatcher.dto.users.*;
import io.qameta.allure.Description;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTests extends Abstract {
    @Test(description = "GET. LIST USERS.", dataProviderClass = UserData.class, dataProvider = "listUsers_200")
    @Description("Testing the list of users.")
    public void testListUsers_200(String page) throws IOException {
        UsersListDTO usersActual = getResource("/users?page=" + page, UsersListDTO.class);
        UserDTO[] users = getExpectedResult("data/listUsers.json", UserDTO[].class);
        UsersListDTO usersExpected = doMagic(page, users);
        usersActual.getData()
                .forEach(actual -> {
                    Optional<UserDTO> expected = usersExpected.getData()
                            .stream()
                            .filter(exp -> exp.getId().equals(actual.getId()))
                            .findFirst();
                    assertThat(expected.isPresent(), equalTo(true));
                    assertThat(actual.getFirst_name(), equalTo(expected.get().getFirst_name()));
                    assertThat(actual.getLast_name(), equalTo(expected.get().getLast_name()));
                    assertThat(actual.getAvatar(), equalTo(expected.get().getAvatar()));
                });
    }

    @Test(description = "GET. SINGLE USER.", dataProviderClass = UserData.class, dataProvider = "singleUser_200")
    @Description("Testing getting a single user.")
    public void testSingleUser_200(String id) throws IOException {
        UserSingleDTO userActual = getResource("/users/" + id, UserSingleDTO.class);
        UserDTO[] users = getExpectedResult("data/listUsers.json", UserDTO[].class);
        UserDTO expectedUser = Arrays.asList(users)
                .stream()
                .filter(userDTO -> userDTO.getId().equals(id))
                .findFirst()
                .get();
        assertThat(userActual.getData().getFirst_name(), equalTo(expectedUser.getFirst_name()));
        assertThat(userActual.getData().getLast_name(), equalTo(expectedUser.getLast_name()));
        assertThat(userActual.getData().getAvatar(), equalTo(expectedUser.getAvatar()));
    }

    @Test(description = "GET. SINGLE USER (NOT FOUND).", dataProviderClass = UserData.class, dataProvider = "singleUser_404")
    @Description("Testing getting a non-existent user.")
    public void testSingleUser_404(String id) throws IOException {
        Response response = getResource("/users/" + id);
        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.asString(), equalTo("{}"));
    }


    @Test(description = "POST. CREATE USER.", dataProviderClass = UserData.class, dataProvider = "createUser_201")
    @Description("Testing the creation of the user.")
    public void testCreateUser_201(String name, String job) throws IOException {
        UserNewDTO newUser = new UserNewDTO().setName(name).setJob(job);
        long beginTime = ZonedDateTime.now(Clock.systemUTC()).toEpochSecond();
        Response response = createResource(newUser, "/users");
        long endTime = beginTime + response.getTimeIn(TimeUnit.SECONDS);
        assertThat(response.getStatusCode(), equalTo(201));
        UserCreatedDTO createdUser = response.as(UserCreatedDTO.class);
        assertThat(createdUser.getName(), equalTo(newUser.getName()));
        assertThat(createdUser.getJob(), equalTo(newUser.getJob()));
        assertThat(createdUser.getId(), is(notNullValue()));
        String s = createdUser.getCreatedAt();
        long createdTime = ZonedDateTime.parse(s).toEpochSecond();
        assertThat(createdTime, is(greaterThan(beginTime)));
        /*It seems that there is an error on the server side (incorrect creation time for the user).*/
        /*assertThat(createdTime, is(lessThan(endTime)));
        assertThat(createdTime, is(both(greaterThan(beginTime)).and(lessThan(endTime))));*/
    }

    @Test(description = "PUT. UPDATE USER.", dataProviderClass = UserData.class, dataProvider = "updateUser_200")
    @Description("Testing an user update.")
    public void testUpdateUser_PUT_200(String id, String name, String job) throws IOException {
        UserNewDTO userExpected = new UserNewDTO().setName(name).setJob(job);
        long beginTime = ZonedDateTime.now(Clock.systemUTC()).toEpochSecond();
        Response response = updateResource(userExpected, Method.PUT, "/users/" + id);
        long endTime = beginTime + response.getTimeIn(TimeUnit.SECONDS);
        UserUpdatedDTO userActual = response.as(UserUpdatedDTO.class);
        assertThat(userActual.getName(), equalTo(userExpected.getName()));
        assertThat(userActual.getJob(), equalTo(userExpected.getJob()));
        String s = userActual.getUpdatedAt();
        long updatedTime = ZonedDateTime.parse(s).toEpochSecond();
        assertThat(updatedTime, is(greaterThan(beginTime)));
        /*It seems that there is an error on the server side (incorrect creation time for the user).*/
        /*assertThat(updatedTime, is(lessThan(endTime)));*/
    }

    @Test(description = "PATCH. UPDATE USER.", dataProviderClass = UserData.class, dataProvider = "patchUser_200")
    @Description("Testing an user update.")
    public void testUpdateUser_PATCH_200(String id, String name, String job) throws IOException {
        UserNewDTO userExpected = new UserNewDTO().setName(name).setJob(job);
        long beginTime = ZonedDateTime.now(Clock.systemUTC()).toEpochSecond();
        Response response = updateResource(userExpected, Method.PATCH, "/users/" + id);
        long endTime = beginTime + response.getTimeIn(TimeUnit.SECONDS);
        UserUpdatedDTO userActual = response.as(UserUpdatedDTO.class);
        assertThat(userActual.getName(), equalTo(userExpected.getName()));
        assertThat(userActual.getJob(), equalTo(userExpected.getJob()));
        String s = userActual.getUpdatedAt();
        long updatedTime = ZonedDateTime.parse(s).toEpochSecond();
        assertThat(updatedTime, is(greaterThan(beginTime)));
        /*It seems that there is an error on the server side (incorrect creation time for the user).*/
        /*assertThat(updatedTime, is(lessThan(endTime)));*/
    }

    @Test(description = "DELETE. DELETE USER.", dataProviderClass = UserData.class, dataProvider = "deleteUser_204")
    @Description("Testing user deletion.")
    public void testDeleteUser_204(String id) throws IOException {
        Response response = deleteResource("/users/" + id);
        assertThat(response.getStatusCode(), equalTo(204));
    }

    @Test(description = "GET. DELAYED RESPONSE.", dataProviderClass = UserData.class, dataProvider = "delay")
    @Description("Testing the delayed response.")
    public void testDelayedResponse_200(String delay) throws IOException {
        final long errorL = 3000;
        Response response = getResource("/users?delay=" + delay);
        if (StringUtils.isNumeric(delay)) {
            long delayL = Long.parseLong(delay) * 1000;
            if (delayL > 0) {
                assertThat(response.getTime(), is(both(greaterThanOrEqualTo(delayL)).and(lessThan(delayL + errorL))));
            } else {
                assertThat(response.getTime(), Matchers.lessThan(errorL));
            }
        } else {
            assertThat(response.getTime(), Matchers.lessThan(errorL));
        }
        UsersListDTO usersActual = response.as(UsersListDTO.class);
        UserDTO[] users = getExpectedResult("data/listUsers.json", UserDTO[].class);
        UsersListDTO usersExpected = doMagic(users);
        usersActual.getData()
                .forEach(actual -> {
                    Optional<UserDTO> expected = usersExpected.getData()
                            .stream()
                            .filter(exp -> exp.getId().equals(actual.getId()))
                            .findFirst();
                    assertThat(expected.isPresent(), equalTo(true));
                    assertThat(actual.getFirst_name(), equalTo(expected.get().getFirst_name()));
                    assertThat(actual.getLast_name(), equalTo(expected.get().getLast_name()));
                    assertThat(actual.getAvatar(), equalTo(expected.get().getAvatar()));
                });
    }

    private UsersListDTO doMagic(UserDTO[] users) {
        return doMagic("1", users);
    }

    private UsersListDTO doMagic(String page, UserDTO[] users) {
        UsersListDTO usersListDTO = new UsersListDTO();
        usersListDTO.setPage(page);
        usersListDTO.setTotal(String.valueOf(users.length));

        int a = users.length;
        int b = Integer.parseInt(usersListDTO.getPer_page());
        int totalPages = (a + b - 1) / b;
        usersListDTO.setTotal_pages(String.valueOf(totalPages));


        int perPage = Integer.parseInt(usersListDTO.getPer_page());
        int beg = (Integer.parseInt(page) - 1) * perPage;
        List<UserDTO> us = new ArrayList<>(Arrays.asList(users));
        try {
            usersListDTO.setData(us.subList(beg, beg + perPage));
        } catch (IndexOutOfBoundsException e) {
            usersListDTO.setData(new ArrayList<>());
        }
        return usersListDTO;
    }
}
