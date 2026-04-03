@MusicAPI @CreateMusic
Feature: Create Music - POST /api/music

  Background:
    Given the music API base URL is configured

  @Smoke @Positive
  Scenario: Create a music track with all required fields
    Given I have a valid music payload
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 201
    And the response field "success" should be true
    And the response field "message" should be "Music created successfully"
    And the response data should contain field "title" with value "Blinding Lights"
    And the response data should contain field "artist" with value "The Weeknd"
    And the response data should contain field "album" with value "After Hours"
    And the response data should contain field "genre" with value "Pop"
    And the response data should contain field "musicUrl" with value "https://cdn.music.com/blinding-lights.mp3"
    And the response data should contain field "coverImageUrl" with value "https://cdn.music.com/blinding-lights.jpg"
    And the response data should contain field "duration" with value 200
    And the response data should have field "_id" present
    And the response data should have field "createdAt" present

  @Positive
  Scenario: Create a music track with optional lyrics field
    Given I have a valid music payload with lyrics
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 201
    And the response field "success" should be true
    And the response data should contain field "lyrics" with value "I said ooh I'm blinded by the lights"
    And the response data should have field "_id" present

  @Positive
  Scenario: Create a music track and verify likes and comments default to empty arrays
    Given I have a valid music payload
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 201
    And the response data array field "likes" should be empty
    And the response data array field "comments" should be empty

  @Negative
  Scenario: Create a music track without title field
    Given I have a music payload missing the "title" field
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false
    And the response field "message" should not be empty

  @Negative
  Scenario: Create a music track without artist field
    Given I have a music payload missing the "artist" field
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @Negative
  Scenario: Create a music track without album field
    Given I have a music payload missing the "album" field
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @Negative
  Scenario: Create a music track without genre field
    Given I have a music payload missing the "genre" field
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @Negative
  Scenario: Create a music track without releaseDate field
    Given I have a music payload missing the "releaseDate" field
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @Negative
  Scenario: Create a music track without musicUrl field
    Given I have a music payload missing the "musicUrl" field
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @Negative
  Scenario: Create a music track without duration field
    Given I have a music payload missing the "duration" field
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @Negative
  Scenario: Create a music track without coverImageUrl field
    Given I have a music payload missing the "coverImageUrl" field
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @Negative
  Scenario: Create a music track with an empty request body
    Given I have an empty music payload
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @EdgeCase
  Scenario: Create a music track with duration as zero
    Given I have a music payload with "duration" set to 0
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @EdgeCase
  Scenario: Create a music track with negative duration
    Given I have a music payload with "duration" set to -1
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 500
    And the response field "success" should be false

  @EdgeCase
  Scenario: Create a music track with duplicate titles from different artists
    Given I have a valid music payload
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 201
    Given I have a music payload with same title but different artist
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 201
    And the response field "success" should be true

  @EdgeCase
  Scenario Outline: Create music tracks with multiple valid genres
    Given I have a music payload with "genre" set to "<genre>"
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 201
    And the response data should contain field "genre" with value "<genre>"

    Examples:
      | genre      |
      | Pop        |
      | Rock       |
      | Jazz       |
      | Hip-Hop    |
      | Classical  |
      | Electronic |

  @EdgeCase
  Scenario: Create a music track and verify versionKey (__v) is not returned
    Given I have a valid music payload
    When I send a POST request to "/api/music" with the music payload
    Then the response status code should be 201
    And the response data should not have field "__v"