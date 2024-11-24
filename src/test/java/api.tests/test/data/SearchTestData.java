package api.tests.test.data;

import org.testng.annotations.DataProvider;

import java.util.*;

import static api.tests.api.GREndpoints.*;


public class SearchTestData {

    public static final String QUERY = "query";
    public static final String ALL = "*";
    public static final String PER_PAGE = "per_page";
    public static final List<String> headerNamesList = Arrays.asList("Date", "Content-Type", "Transfer-Encoding", "Connection",
            "Report-To", "Reporting-Endpoints", "Nel", "X-Frame-Options", "X-Xss-Protection", "X-Content-Type-Options",
            "X-Download-Options", "X-Permitted-Cross-Domain-Policies", "Referrer-Policy", "Vary", "Cache-Control", "Etag",
            "X-Request-Id", "X-Runtime", "Strict-Transport-Security", "Via", "CF-Cache-Status", "Age", "Server", "CF-RAY",
            "Content-Encoding", "alt-svc", "server-timing");

    @DataProvider(name = "paginationTestData")
    public static Object[][] createPaginationTestData() {
        return new Object[][]{
                {SEARCH.getValue(), "5"},
                {AUTOCOMPLETE.getValue(), "10"}
        };
    }
    @DataProvider(name = "limitPerPageTestData")
    public static Object[][] createLimitPerPageTestData() {
        return new Object[][]{
                {AUTOCOMPLETE.getValue(), "15"}
        };
    }

    @DataProvider(name = "schemaTestData")
    public static Object[][] createSchemaValidationTestData() {
        return new Object[][]{
                {SEARCH.getValue(), "src/test/java/api.tests/test/data/search-schema.json"},
                {AUTOCOMPLETE.getValue(), "src/test/java/api.tests/test/data//autocomplete-schema.json"}
        };
    }

    @DataProvider(name = "headerAddedTestData")
    public static Object[][] createHeaderAddedTestData() {

        return new Object[][]{
                {SEARCH.getValue(), headerNamesList},
                {AUTOCOMPLETE.getValue(), headerNamesList}
        };
    }

    @DataProvider(name = "headerValueTestData")
    public static Object[][] createHeaderValueTestData() {
        return new Object[][]{
                {
                        SEARCH.getValue(),
                        Map.of(
                                "Content-Type", "application/json; charset=utf-8",
                                "Cache-Control", "public, max-age=86400",
                                "Via", "1.1 vegur",
                                "X-Content-Type-Options", "nosniff",
                                "CF-Cache-Status", "HIT"
                        )
                },
                {
                        AUTOCOMPLETE.getValue(),
                        Map.of(
                                "Content-Type", "application/json; charset=utf-8",
                                "Cache-Control", "public, max-age=86400",
                                "X-Download-Options","noopen",
                                "Vary", "Accept, Origin, Accept-Encoding",
                                "X-Frame-Options", "SAMEORIGIN"
                        )
                }
        };
    }


    @DataProvider(name = "incorrectEndpointNameTestData")
    public static Object[] createIncorrectEndpointNameTestData() {
        return new Object[]{
                SEARCH.getValue() + "Incorrect",
                "Incorrect" + AUTOCOMPLETE.getValue(),
                "IncorrectEndpoint",
                SEARCH.getValue().toUpperCase(),
                AUTOCOMPLETE.getValue().toUpperCase()
        };
    }

    @DataProvider(name = "validRequestInQueryTestData")
    public static Object[][] createExpectedCharactersInQueryTestData() {
        return new Object[][]{{"san%20diego", "San Diego"},
                {"san_diego", "San Diego"},
                {"sandiego", "San Diego"},
                {"diego", "diego"},
                {"385a2831-a7d5-4d07-bcff-d9d3312c571c", "385a2831-a7d5-4d07-bcff-d9d3312c571c"},
                {"a7d5", "a7d5"}
        };
    }
}
