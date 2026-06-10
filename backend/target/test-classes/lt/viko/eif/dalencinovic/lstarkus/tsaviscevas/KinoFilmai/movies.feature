Feature: Movies API
  User wants to access movie information

  Scenario: Display all movies
    Given movie service is running
    When user requests all movies
    Then movie list should not be empty

  Scenario: Display existing movie by id
    Given movie service is running
    When user requests movie with id 1
    Then response status should be 200

  Scenario: Display non existing movie by id
    Given movie service is running
    When user requests movie with id 999999
    Then response status should be 404
