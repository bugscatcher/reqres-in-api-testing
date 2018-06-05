package com.github.bugscatcher.data;

import org.testng.annotations.DataProvider;

public class UserData {
    @DataProvider(name = "createUser_201", parallel = true)
    public static Object[][] createUser_201() {
        return new Object[][]{
                {"Ainur", "Team Leader"},
                {"Jacob", ""},
                {"Michael", null},
                {"", "Software Architect"},
                {null, "UX Designer"}
        };
    }

    @DataProvider(name = "updateUser_200", parallel = true)
    public static Object[][] updateUser_200() {
        return new Object[][]{
                {"Ainur", "Chairman"},
                {"Jacob", null},
                {"Michael", ""},
                {null, "Software Architect"},
                {"", "UX Designer"}
        };
    }

    @DataProvider(name = "patchUser_200", parallel = true)
    public static Object[][] patchUser_200() {
        return new Object[][]{
                {"Ainur", "CEO"},
                {"Alexander", null},
                {"Nicholas", ""},
                {null, "Mobile Developer"},
                {"", "Software Engineer"}
        };
    }

    @DataProvider(name = "registerUser", parallel = true)
    public static Object[][] registerUser() {
        return new Object[][]{
                {"inurrick@gmail.com", "c7NPkr0VSh8tdKxo3qx3"},
                {"inurrick", "nzkg8vGdbBmePJvEtm8I"}
        };
    }

    @DataProvider(name = "delay", parallel = true)
    public static Object[][] delay() {
        return new Object[][]{
                {"5"},
                {"10"},
                {"-999"},
                {"aaaa"}
        };
    }
}
