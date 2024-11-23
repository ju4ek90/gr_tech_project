package api.tests.api;


public enum GREndpoints {

    SEARCH("search"),
    AUTOCOMPLETE("autocomplete");

    private final String value;

    GREndpoints(String val) {
        this.value = val;
    }

    public String getValue() {
        return value;
    }

}
