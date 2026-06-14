Feature: Create movie

  Scenario: Create new movie
    Given movie data is prepared
    When user creates new movie
    Then movie should be created