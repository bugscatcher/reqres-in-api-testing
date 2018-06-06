package com.github.bugscatcher.data;

import org.testng.annotations.DataProvider;

public class ResourceData {
    @DataProvider(name = "listResources_200")
    public static Object[][] listResources_200() {
        return new Object[][]{
                {"1"},
                {"2"},
                {"22"}
        };
    }

    @DataProvider(name = "singleResource_200")
    public static Object[][] singleResource_200() {
        return new Object[][]{
                {"1"},
                {"2"}
        };
    }

    @DataProvider(name = "singleResource_404")
    public static Object[][] singleResource_404() {
        return new Object[][]{
                {"999"},
                {"88"}
        };
    }
}
