Feature: User is able to do E2E validation for GoogleMap Place API
  @AddPlace @Regression
  Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
  Given Add Place Payload with "<name>" "<language>" "<address>"
  When user calls "AddPlaceAPI" with "Post" http request
  Then The API call is success with status code 200
  And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_id created maps to "<name>" using "GetPlaceAPI"

  Examples:
    |name      |language  |address   |
    |AAhouse   | French   | Vikhroli |
    |BBHouse   | English  | Pune     |

  @DeletePlace @Regression
  Scenario: Verify if the Delete Place functionality is working
    Given Delete place Payload
    When user calls "DeletePlaceAPI" with "Delete" http request
    Then the API call get sucess with status code 200
    And "status" in response body is "OK"
