@UserAPI @Regression
Feature: User API - CRUD Operations

  Background:
    Given the API base URL is configured

  @Smoke @GetUser
  Scenario: Get all users successfully
    When I send a GET request to "/users"
    Then the response status code should be 200
    And the response body should contain a list of users

  @Smoke @CreateUser
  Scenario: Create a new user with valid data
    Given I have the following user details
      | name        | email                | role  |
      | John Smith  | john.smith@test.com  | admin |
    When I send a POST request to "/users" with the user body
    Then the response status code should be 201
    And the response body should contain "name" as "John Smith"
    And the response body should contain "email" as "john.smith@test.com"

  @Regression @GetUser
  Scenario Outline: Get user by ID
    When I send a GET request to "/users/<userId>"
    Then the response status code should be <statusCode>

    Examples:
      | userId | statusCode |
      | 1      | 200        |
      | 9999   | 404        |

  @Regression @UpdateUser
  Scenario: Update an existing user
    Given a user exists with ID 1
    When I send a PUT request to "/users/1" with updated name "Jane Doe"
    Then the response status code should be 200
    And the response body should contain "name" as "Jane Doe"

  @Regression @DeleteUser
  Scenario: Delete an existing user
    Given a user exists with ID 1
    When I send a DELETE request to "/users/1"
    Then the response status code should be 204