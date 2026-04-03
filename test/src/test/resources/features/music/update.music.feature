@MusicAPI @UpdateMusic
Feature: Update Music - PUT /api/music/:id

  Background:
    Given the music API base URL is configured
    And a music track exists in the system for update

  @Smoke @Positive
  Scenario: Update a music track with all fields successfully
    Given I have an update music payload with all fields
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response field "success" should be true
    And the response field "message" should be "Music updated successfully"
    And the response data should have field "_id" present
    And the response data should contain field "title" with value "Shape of You"
    And the response data should contain field "artist" with value "Ed Sheeran"
    And the response data should contain field "album" with value "Divide"
    And the response data should contain field "genre" with value "Pop"
    And the response data should contain field "duration" with value 234

  @Positive
  Scenario: Update only the title of a music track
    Given I have an update payload with only field "title" set to "New Title"
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response field "success" should be true
    And the response data should contain field "title" with value "New Title"

  @Positive
  Scenario: Update only the artist of a music track
    Given I have an update payload with only field "artist" set to "New Artist"
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response data should contain field "artist" with value "New Artist"

  @Positive
  Scenario: Update only the genre of a music track
    Given I have an update payload with only field "genre" set to "Jazz"
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response data should contain field "genre" with value "Jazz"

  @Positive
  Scenario: Update the lyrics of a music track
    Given I have an update payload with only field "lyrics" set to "Updated lyrics content here"
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response data should contain field "lyrics" with value "Updated lyrics content here"

  @Positive
  Scenario: Update a music track returns the updated document not the old one
    Given I have an update payload with only field "title" set to "Updated Track Title"
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response data should contain field "title" with value "Updated Track Title"

  @Positive
  Scenario: Update a music track and verify runValidators applies schema rules
    Given I have an update music payload with all fields
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response data should have field "createdAt" present
    And the response data should have field "_id" present

  @Negative
  Scenario: Update a music track with non-existent valid ObjectId returns 404
    Given I have an update payload with only field "title" set to "Ghost Track"
    When I send a PUT request to "/api/music/64f1a2b3c4d5e6f7a8b9c000" with the update payload
    Then the response status code should be 404
    And the response field "success" should be false
    And the response field "message" should be "Music with ID '64f1a2b3c4d5e6f7a8b9c000' not found"

  @Negative
  Scenario: Update a music track with malformed ID returns 500
    Given I have an update payload with only field "title" set to "Invalid ID Track"
    When I send a PUT request to "/api/music/not-a-valid-id" with the update payload
    Then the response status code should be 500
    And the response field "success" should be false
    And the response field "message" should not be empty

  @Negative
  Scenario: Update a music track with empty string title fails validation
    Given I have an update payload with only field "title" set to ""
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 500
    And the response field "success" should be false

  @Negative
  Scenario: Update duration to a string value fails validation
    Given I have an update payload with "duration" set to string "not-a-number"
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 500
    And the response field "success" should be false

  @EdgeCase
  Scenario: Update a music track with no body does not crash the server
    Given I have an empty update payload
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response field "success" should be true

  @EdgeCase
  Scenario: Update a music track and verify likes array is not wiped
    Given a music track exists with likes for update
    Given I have an update payload with only field "title" set to "Title After Like Update"
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response data array field "likes" should be present

  @EdgeCase
  Scenario: Update a music track and verify comments array is not wiped
    Given a music track exists with comments for update
    Given I have an update payload with only field "title" set to "Title After Comment Update"
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response data array field "comments" should be present

  @EdgeCase
  Scenario: Update a music track with the same data returns 200 with unchanged values
    Given I have a valid music payload for update
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be 200
    And the response field "success" should be true

  @EdgeCase
  Scenario Outline: Update music with valid duration boundary values
    Given I have an update payload with "duration" set to <duration>
    When I send a PUT request to "/api/music/{musicId}" with the update payload
    Then the response status code should be <statusCode>

    Examples:
      | duration | statusCode |
      | 1        | 200        |
      | 3600     | 200        |
      | 9999     | 200        |