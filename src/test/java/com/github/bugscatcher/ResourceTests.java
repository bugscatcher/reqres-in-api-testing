package com.github.bugscatcher;

import com.github.bugscatcher.data.ResourceData;
import com.github.bugscatcher.dto.resources.ResourceDTO;
import com.github.bugscatcher.dto.resources.ResourceSingleDTO;
import com.github.bugscatcher.dto.resources.ResourcesListDTO;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResourceTests extends Abstract {
    @Test(description = "GET. LIST RESOURCES.", dataProviderClass = ResourceData.class, dataProvider = "listResources_200")
    @Description("Testing the list of resources.")
    public void testListResource_200(String page) throws IOException {
        ResourcesListDTO resourcesActual = getResource("/unknown?page=" + page, ResourcesListDTO.class);
        ResourceDTO[] resources = getExpectedResult("data/listResources.json", ResourceDTO[].class);
        ResourcesListDTO resourcesExpected = doMagic(page, resources);
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

    @Test(description = "GET. SINGLE RESOURCE.", dataProviderClass = ResourceData.class, dataProvider = "singleResource_200")
    @Description("Testing getting a single resource.")
    public void testSingleResource_200(String id) throws IOException {
        ResourceSingleDTO resourceActual = getResource("/unknown/" + id, ResourceSingleDTO.class);
        ResourceDTO[] resources = getExpectedResult("data/listResources.json", ResourceDTO[].class);
        ResourceDTO resourceExpected = Arrays.asList(resources)
                .stream()
                .filter(resourceDTO -> resourceDTO.getId().equals(id))
                .findFirst()
                .get();
        assertThat(resourceActual.getData().getName(), equalTo(resourceExpected.getName()));
        assertThat(resourceActual.getData().getYear(), equalTo(resourceExpected.getYear()));
        assertThat(resourceActual.getData().getColor(), equalTo(resourceExpected.getColor()));
        assertThat(resourceActual.getData().getPantone_value(), equalTo(resourceExpected.getPantone_value()));
    }

    @Test(description = "GET. SINGLE RESOURCE (NOT FOUND).", dataProviderClass = ResourceData.class, dataProvider = "singleResource_404")
    @Description("Testing getting a non-existent resource.")
    public void testSingleResource_404(String id) throws IOException {
        Response response = getResource("/unknown/" + id);
        assertThat(response.getStatusCode(), equalTo(404));
        assertThat(response.asString(), equalTo("{}"));
    }

    private ResourcesListDTO doMagic(ResourceDTO[] res) {
        return doMagic("1", res);
    }

    private ResourcesListDTO doMagic(String page, ResourceDTO[] res) {
        ResourcesListDTO resListDTO = new ResourcesListDTO();
        resListDTO.setPage(page);
        resListDTO.setTotal(String.valueOf(res.length));

        int a = res.length;
        int b = Integer.parseInt(resListDTO.getPer_page());
        int totalPages = (a + b - 1) / b;
        resListDTO.setTotal_pages(String.valueOf(totalPages));


        int perPage = Integer.parseInt(resListDTO.getPer_page());
        int beg = (Integer.parseInt(page) - 1) * perPage;
        List<ResourceDTO> us = new ArrayList<>(Arrays.asList(res));

        try {
            resListDTO.setData(us.subList(beg, beg + perPage));
        } catch (IndexOutOfBoundsException e) {
            resListDTO.setData(new ArrayList<>());
        }
        return resListDTO;
    }
}
