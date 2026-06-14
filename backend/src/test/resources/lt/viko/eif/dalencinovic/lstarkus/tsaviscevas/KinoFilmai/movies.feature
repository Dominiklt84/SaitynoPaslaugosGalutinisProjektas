Feature: Movies API
  User wants to access movie information

  Scenario: Display all movies
    Given existing movie
    When user requests all movies
    Then response status should be 200
    And movie list should not be empty

  Scenario: No movies exist
    Given no movies exist
    When user requests all movies
    Then response status should be 404

  Scenario: Display existing movie by id
    Given existing movie
    When user requests existing movie
    Then response status should be 200

  Scenario: Display non existing movie by id
    Given movie service is running
    When user requests movie with id 999999
    Then response status should be 404
