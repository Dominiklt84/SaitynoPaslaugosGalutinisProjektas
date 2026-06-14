Feature: Delete movie

  Scenario: Delete existing movie
    Given existing movie
    When user deletes movie
    Then response status should be 204

  Scenario: Delete non existing movie
    When user deletes movie with id 999999
    Then response status should be 404