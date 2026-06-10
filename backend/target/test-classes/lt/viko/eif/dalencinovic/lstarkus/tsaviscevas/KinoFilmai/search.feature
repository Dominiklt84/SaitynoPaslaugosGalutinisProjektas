Feature: Movie search
  User wants to search movies

  Scenario: Search existing movie
    Given movie service is running
    When user searches for "Batman"
    Then response should contain "Batman"

  Scenario: Search non existing movie
    Given movie service is running
    When user searches for "abcdefgh123"
    Then response status should be 404