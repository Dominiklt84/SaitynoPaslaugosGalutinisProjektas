Feature: Update movie

  Scenario: Update existing movie
    Given existing movie
    When user updates movie
    Then updated movie should be returned

  Scenario: Update non-existing movie
    When user updates movie with id 999999
    Then response status should be 404