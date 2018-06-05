package com.github.bugscatcher;

import com.github.bugscatcher.dto.resources.ResourceDTO;
import com.github.bugscatcher.dto.resources.ResourceSingleDTO;
import com.github.bugscatcher.dto.resources.ResourcesListDTO;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResourceTests extends Abstract {
    @Test(description = "GET. LIST RESOURCES")
    @Description("Testing the list of resources.")
    public void testListResource_200() throws IOException {
        ResourcesListDTO resourcesActual = getResource("/unknown", ResourcesListDTO.class);
        ResourcesListDTO resourcesExpected = getExpectedResult("data/listResources_200.json", ResourcesListDTO.class);
        resourcesActual.getData()
                .forEach(actual -> {
                    Optional<ResourceDTO> expected = resourcesExpected.getData()
                            .stream()
                            .filter(exp -> exp.getId().equals(actual.getId()))
                            .findFirst();
                    assertThat(expected.isPresent(), equalTo(true));
                    assertThat(actual.getName(), equalTo(expected.get().getName()));
                    assertThat(actual.getYear(), equalTo(expected.get().getYear()));
                    assertThat(actual.getColor(), equalTo(expected.get().getColor()));
                    assertThat(actual.getPantone_value(), equalTo(expected.get().getPantone_value()));
                });
    }

    @Test(description = "GET. SINGLE RESOURCE")
    @Description("Testing getting a single resource.")
    public void testSingleResource_200() throws IOException {
        ResourceSingleDTO resourceActual = getResource("/unknown/2", ResourceSingleDTO.class);
        ResourceSingleDTO resourceExpected = getExpectedResult("data/singleResource_200.json", ResourceSingleDTO.class);
        assertThat(resourceActual.getData().getId(), equalTo(resourceExpected.getData().getId()));
        assertThat(resourceActual.getData().getName(), equalTo(resourceExpected.getData().getName()));
        assertThat(resourceActual.getData().getYear(), equalTo(resourceExpected.getData().getYear()));
        assertThat(resourceActual.getData().getColor(), equalTo(resourceExpected.getData().getColor()));
        assertThat(resourceActual.getData().getPantone_value(), equalTo(resourceExpected.getData().getPantone_value()));
    }

    @Test(description = "GET. SINGLE RESOURCE (NOT FOUND)")
    @Description("Testing getting a non-existent resource.")
    public void testSingleResource_404() throws IOException {
        Response response = getResource("/unknown/23");
        assertThat(response.getStatusCode(), equalTo(404));
        assertThat(response.asString(), equalTo("{}"));
    }
}
