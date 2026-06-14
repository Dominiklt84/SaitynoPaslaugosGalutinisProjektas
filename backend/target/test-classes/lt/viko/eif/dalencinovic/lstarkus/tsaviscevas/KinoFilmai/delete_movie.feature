Feature: Delete movie

  Scenario: Delete existing movie
    Given existing movie
    When user deletes movie
    Then response status should be 204