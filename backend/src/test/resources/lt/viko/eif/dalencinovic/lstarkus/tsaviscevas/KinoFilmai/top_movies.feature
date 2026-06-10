Feature: Top movies
  User wants to see popular movies

  Scenario: Display top movies today
    Given movie service is running
    When user requests top movies today
    Then top movies response should not be empty

  Scenario: Display top movies this month
    Given movie service is running
    When user requests top movies this month
    Then top month movies response should not be empty