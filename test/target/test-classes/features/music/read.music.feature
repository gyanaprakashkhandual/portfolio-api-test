@MusicAPI @ReadMusic
Feature: Read Music - GET /api/music and GET /api/music/:id

  Background:
    Given the music API base URL is configured

  @Smoke @Positive
  Scenario: Get all music tracks returns 200 with success true
    When I send a GET request to "/api/music"
    Then the response status code should be 200
    And the response field "success" should be true
    And the response field "message" should be "Music fetched successfully"
    And the response should contain field "total"
    And the response should contain a list under "data"

  @Positive
  Scenario: Get all music tracks returns sorted newest first
    Given at least two music tracks exist in the system
    When I send a GET request to "/api/music"
    Then the response status code should be 200
    And the response data list should be sorted by "createdAt" descending

  @Positive
  Scenario: Get all music tracks total matches data array length
    When I send a GET request to "/api/music"
    Then the response status code should be 200
    And the response field "total" should equal the size of the "data" array

  @Smoke @Positive
  Scenario: Get a music track by valid ID
    Given a music track exists in the system
    When I send a GET request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response field "success" should be true
    And the response field "message" should be "Music fetched successfully"
    And the response data should have field "_id" present
    And the response data should have field "title" present
    And the response data should have field "artist" present
    And the response data should have field "album" present
    And the response data should have field "genre" present
    And the response data should have field "releaseDate" present
    And the response data should have field "musicUrl" present
    And the response data should have field "duration" present
    And the response data should have field "coverImageUrl" present
    And the response data array field "likes" should be present
    And the response data array field "comments" should be present

  @Positive
  Scenario: Get a music track by ID and verify versionKey is absent
    Given a music track exists in the system
    When I send a GET request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response data should not have field "__v"

  @Positive
  Scenario: Get a music track by ID and verify all field types
    Given a music track exists in the system
    When I send a GET request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response data field "title" should be of type String
    And the response data field "artist" should be of type String
    And the response data field "album" should be of type String
    And the response data field "genre" should be of type String
    And the response data field "duration" should be of type Number
    And the response data field "likes" should be of type List
    And the response data field "comments" should be of type List

  @Negative
  Scenario: Get a music track by non-existent valid ObjectId returns 404
    When I send a GET request to "/api/music/64f1a2b3c4d5e6f7a8b9c000"
    Then the response status code should be 404
    And the response field "success" should be false
    And the response field "message" should be "Music with ID '64f1a2b3c4d5e6f7a8b9c000' not found"

  @Negative
  Scenario: Get a music track by malformed ID returns 500
    When I send a GET request to "/api/music/invalid-id-123"
    Then the response status code should be 500
    And the response field "success" should be false
    And the response field "message" should not be empty

  @Negative
  Scenario: Get a music track by empty string ID returns 404 or 500
    When I send a GET request to "/api/music/ "
    Then the response status code should be in [404, 500]

  @EdgeCase
  Scenario: Get all music tracks when database has no records returns empty list
    Given the music collection is empty
    When I send a GET request to "/api/music"
    Then the response status code should be 200
    And the response field "success" should be true
    And the response field "total" should be 0
    And the response should contain an empty list under "data"

  @EdgeCase
  Scenario: Get a music track by ID with extra path segments returns 404
    When I send a GET request to "/api/music/64f1a2b3c4d5e6f7a8b9c001/extra"
    Then the response status code should be 404

  @EdgeCase
  Scenario: Get a music track by ID returns lyrics as null when not set
    Given a music track exists without lyrics
    When I send a GET request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response data field "lyrics" should be null or absent

  @EdgeCase
  Scenario: Get all music returns correct data structure for each track
    Given at least one music track exists in the system
    When I send a GET request to "/api/music"
    Then the response status code should be 200
    And every item in the "data" array should have fields "title", "artist", "album", "genre", "duration", "musicUrl", "coverImageUrl", "likes", "comments"

  @EdgeCase
  Scenario: Get a music track by ObjectId with all lowercase hex characters
    When I send a GET request to "/api/music/aabbccddeeff001122334455"
    Then the response status code should be 404
    And the response field "success" should be false
