package com.github.bugscatcher.data;

import org.testng.annotations.DataProvider;

public class UserData {
    @DataProvider(name = "createUser_201")
    public static Object[][] createUser_201() {
        return new Object[][]{
                {"Ainur", "Team Leader"},
                {"Jacob", ""},
                {"Michael", null},
                {"", "Software Architect"},
                {null, "UX Designer"}
        };
    }

    @DataProvider(name = "updateUser_200")
    public static Object[][] updateUser_200() {
        return new Object[][]{
                {"1", "Ainur", "Chairman"},
                {"2", "Jacob", null},
                {"3", "Michael", ""},
                {"4", null, "Software Architect"},
                {"5", "", "UX Designer"}
        };
    }

    @DataProvider(name = "patchUser_200")
    public static Object[][] patchUser_200() {
        return new Object[][]{
                {"1", "Ainur", "CEO"},
                {"2", "Alexander", null},
                {"3", "Nicholas", ""},
                {"4", null, "Mobile Developer"},
                {"5", "", "Software Engineer"}
        };
    }

    @DataProvider(name = "registerUser")
    public static Object[][] registerUser() {
        return new Object[][]{
                {"inurrick@gmail.com", "c7NPkr0VSh8tdKxo3qx3"},
                {"inurrick", "nzkg8vGdbBmePJvEtm8I"}
        };
    }

    @DataProvider(name = "delay")
    public static Object[][] delay() {
        return new Object[][]{
                {"5"},
                {"10"},
                {"-999"},
                {"aaaa"}
        };
    }

    @DataProvider(name = "singleUser_200")
    public static Object[][] singleUser_200() {
        return new Object[][]{
                {"1"},
                {"5"}
        };
    }

    @DataProvider(name = "singleUser_404")
    public static Object[][] singleUser_404() {
        return new Object[][]{
                {"99"},
                {"-100"}
        };
    }

    @DataProvider(name = "deleteUser_204")
    public static Object[][] deleteUser_204() {
        return new Object[][]{
                {"1"},
                {"100"}
        };
    }

    @DataProvider(name = "listUsers_200")
    public static Object[][] listUsers_200() {
        return new Object[][]{
                {"2"},
                {"3"},
                {"100"}
        };
    }
}
