Feature: Top movies
  User wants to see popular movies

  Scenario: Display top movies today
    Given existing movie
    When user requests movie with created id
    And user requests top movies today
    Then response status should be 200
    And top movies response should not be empty

  Scenario: No top movies today
    Given no movies exist
    When user requests top movies today
    Then response status should be 404

  Scenario: Display top movies this month
    Given existing movie
    When user requests movie with created id
    And user requests top movies this month
    Then response status should be 200
    And top movies response should not be empty

  Scenario: No top movies this month
    Given no movies exist
    When user requests top movies this month
    Then response status should be 404