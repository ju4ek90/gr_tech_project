# gr_tech_project
Testing OpenBrewerydb API

For open testing project, please clone this repository.
At SearchBreweriesTests you can find 8 scenarios with different testCases:
 - testPagination 
   Description: Validation that for Endpoints we can choose how many entities would like to see per a page
   ! not working for Autocomplete Endpoint
_ testLimitPerPage
   Description: Validation that for Autocomplete Endpoints limit entities per a page no more than 15
- testJsonSchemaForEndpoint
  Description: Json schema validation for both endpoints
- testIncorrectEndpointName
  Description: Negative scenario for incorrect Endpoint name
  ! need to discuss Case-Sensitivity
- testHeadersAdded
  Description: Verification that all expected headers added and not null or empty
- testHeaderValues  
  Description: Validation of headers value (for some of them)
  ! need to discuss what headers are more important
- testValidInQuerySearchEndpoint
  Description: Validate that search accept expected characters, and return correct results
  ! found unexpected behaviour
-testDataFilteringAutocompleteEndpoint
  Description: Validate that search accept expected characters, and return correct results in scope of filtering 
  ! found unexpected behaviour. Need to understand behaviour more
  
I would like to mansion that API is not stable all time. Sometimes I got error 522 (Error 522 is an HTTP status 
code that indicates a connection issue where the origin server doesn't reply, often seen with CDN services like 
Cloudflare. It happens when Cloudflare can't get a response from your site within the expected timeframe.)

Project designed for possibility ru tests remote as well. Used DDT pattern. I will be glad to answer on your questions
and discuss remarks.

Scenarios that also should be included to test suit:

- An incorrect version in URL path
- Multi slashes in URL path
- Case-Sensitivity in URL path
- More cases for unexpected characters (fot this I need review more documentation for understand requirements)
- testDataSearchAutocompleteEndpoint (the same as for Search Endpoint)
- HTTP requests for Endpoints
- What will be if we will send unexpected request (as example POST)
- Send request with unexpected headers or header Values
- Send request with employ/null/incorrect query parameters
- Send request with unexpected query parameters name (as example not per_page, but perpage)


  
