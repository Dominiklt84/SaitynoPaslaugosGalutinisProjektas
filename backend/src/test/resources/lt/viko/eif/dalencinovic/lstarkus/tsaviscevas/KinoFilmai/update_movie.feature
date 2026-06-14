Feature: Update movie

  Scenario: Update existing movie
    Given existing movie
    When user updates movie
    Then updated movie should be returned