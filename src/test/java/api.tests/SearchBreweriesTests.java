package api.tests;

import api.tests.test.data.SearchTestData;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static api.tests.api.GRApiClient.getResponseForEndpoint;
import static api.tests.api.GRApiClient.*;
import static api.tests.api.GREndpoints.AUTOCOMPLETE;
import static api.tests.api.GREndpoints.SEARCH;
import static api.tests.test.data.SearchTestData.*;
import static api.tests.test.data.StatusCodesAndMessages.*;
import static api.tests.utils.JsonOperations.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.*;

public class SearchBreweriesTests {

    @Test(dataProvider = "paginationTestData", dataProviderClass = SearchTestData.class)
    public void testPagination(String endpoint, String perPage) {

        // Get Endpoint with pagination
        Response response = getResponseForEndpointWithQueryParams(endpoint, QUERY, ALL, PER_PAGE, perPage, SUCCESS_STATUS_CODE);

        // Validate that we have expected count of entities in response
        Assert.assertEquals(response.jsonPath().getList("$").size(), Integer.parseInt(perPage),
                "The number of items per page does not match the expected count.");
    }

    @Test(dataProvider = "limitPerPageTestData", dataProviderClass = SearchTestData.class)
    public void testLimitPerPage(String endpoint, String limitPerPage) {

        // Get Endpoint with pagination
        Response response = getResponseForEndpointWithQuery(endpoint, QUERY, ALL, SUCCESS_STATUS_CODE);

        // Validate that we have expected count of entities in response
        Assert.assertEquals(response.jsonPath().getList("$").size(), Integer.parseInt(limitPerPage),
                "The number of items per page does not match the expected count.");
    }

    @Test(dataProvider = "schemaTestData", dataProviderClass = SearchTestData.class)
    public void testJsonSchemaForEndpoint(String endpoint, String schemaPath) {

        // Get response from Endpoint
        Response response = getResponseForEndpointWithQuery(endpoint, QUERY, ALL, SUCCESS_STATUS_CODE);

        // Validate JSON Schema
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(getPeopleApiSchema(schemaPath)));
    }

    @Test(dataProvider = "incorrectEndpointNameTestData", dataProviderClass = SearchTestData.class)
    public void testIncorrectEndpointName(String endpointName) {

        // Get response from Incorrect Endpoint
        Response response = getResponseForEndpoint(endpointName, NOT_FOUND_STATUS_CODE);

        // Validate that  response as expected
        response.then().assertThat().body("message", equalTo(NOT_FOUND_MESSAGE));

    }

    @Test(dataProvider = "headerAddedTestData", dataProviderClass = SearchTestData.class)
    public void testHeadersAdded(String endpoint, List headerNames) {

        // Get response from Endpoint
        Response response = getResponseForEndpoint(endpoint, SUCCESS_STATUS_CODE);

        // Validate that all expected Headers present in response and not null or empty
        for (Object header : headerNames) {
            String headerValue = response.getHeader((String) header);
            Assert.assertNotNull(headerValue, "Header " + header + " is missing or null");
            Assert.assertFalse(headerValue.isEmpty(), "Header " + header + " is empty");

        }
    }

    @Test(dataProvider = "headerValueTestData", dataProviderClass = SearchTestData.class)
    public void testHeaderValues(String endpoint, Map<String, String> expectedHeaders) {
        // Get response from Endpoint
        Response response = getResponseForEndpoint(endpoint, SUCCESS_STATUS_CODE);

        // Validate header values
        for (Map.Entry<String, String> header : expectedHeaders.entrySet()) {
            response.then()
                    .assertThat()
                    .header(header.getKey(), equalTo(header.getValue()));
        }
    }

    @Test(dataProvider = "validRequestInQueryTestData", dataProviderClass = SearchTestData.class)
    public void testValidInQuerySearchEndpoint(String queryValue, String expectedValue) {

        // Get response from Endpoint
        Response response = getResponseForEndpointWithQuery(SEARCH.getValue(), QUERY, queryValue, SUCCESS_STATUS_CODE);

        // Validate that response as expected
        // Get Json response into List
        List<Map<String, Object>> entities = response.jsonPath().getList("$");

        for (Map<String, Object> entity : entities) {
            boolean containsSanDiego = entity.values().stream()
                    .filter(value -> value != null)
                    .anyMatch(value -> value.toString().contains(expectedValue));

            assertTrue(containsSanDiego, "Entity does not contain searched data: " + queryValue + entity);
        }
    }

    @Test(dataProvider = "validRequestInQueryTestData", dataProviderClass = SearchTestData.class)
    public void testDataFilteringAutocompleteEndpoint(String queryValue, String expectedValue) {

        // Get response from Endpoint
        Response responseSearch = getResponseForEndpointWithQuery(SEARCH.getValue(), QUERY, queryValue, SUCCESS_STATUS_CODE);
        Response responseAutocomplete = getResponseForEndpointWithQuery(AUTOCOMPLETE.getValue(), QUERY, queryValue, SUCCESS_STATUS_CODE);

        // Validate that response as expected
        // Get Json response into List
        List<String> entitiesIdFromSearchEndpoint = responseSearch.jsonPath().getList("id");
        List<String> entitiesIdFromAutocompleteEndpoint = responseAutocomplete.jsonPath().getList("id");

        for (String id : entitiesIdFromAutocompleteEndpoint) {
            Assert.assertTrue(
                    entitiesIdFromSearchEndpoint.contains(id),
                    "ID " + id + " from autocomplete endpoint is not present in search endpoint"
            );
        }
    }
}

