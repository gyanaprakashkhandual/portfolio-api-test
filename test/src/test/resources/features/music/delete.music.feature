@MusicAPI @DeleteMusic
Feature: Delete Music - DELETE /api/music/:id

  Background:
    Given the music API base URL is configured
    And a music track exists in the system

  @Smoke @Positive
  Scenario: Delete a music track by valid ID returns 200
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response field "success" should be true
    And the response field "message" should be "Music deleted successfully"
    And the response field "data" should not be empty

  @Positive
  Scenario: Delete a music track and verify it no longer exists
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 200
    When I send a GET request to "/api/music/{musicId}"
    Then the response status code should be 404
    And the response field "success" should be false

  @Positive
  Scenario: Delete a music track and verify the deleted document is returned in response data
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response data should have field "_id" present
    And the response data should have field "title" present
    And the response data should have field "artist" present
    And the response data should have field "album" present

  @Positive
  Scenario: Delete a music track and verify total count in GET all decreases
    Given the total music count is recorded
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 200
    When I send a GET request to "/api/music"
    Then the response field "total" should be decremented by 1

  @Negative
  Scenario: Delete a music track with non-existent valid ObjectId returns 404
    When I send a DELETE request to "/api/music/64f1a2b3c4d5e6f7a8b9c000"
    Then the response status code should be 404
    And the response field "success" should be false
    And the response field "message" should be "Music with ID '64f1a2b3c4d5e6f7a8b9c000' not found"

  @Negative
  Scenario: Delete a music track with malformed ID returns 500
    When I send a DELETE request to "/api/music/bad-id-format"
    Then the response status code should be 500
    And the response field "success" should be false
    And the response field "message" should not be empty

  @Negative
  Scenario: Delete the same music track twice returns 404 on second attempt
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 200
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 404
    And the response field "success" should be false

  @Negative
  Scenario: Delete without providing an ID returns 404 or 405
    When I send a DELETE request to "/api/music/"
    Then the response status code should be in [404, 405]

  @EdgeCase
  Scenario: Delete a music track that has likes and verify full document returned
    Given a music track exists with likes
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response field "success" should be true
    And the response data array field "likes" should be present

  @EdgeCase
  Scenario: Delete a music track that has comments and verify full document returned
    Given a music track exists with comments
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response field "success" should be true
    And the response data array field "comments" should be present

  @EdgeCase
  Scenario: Delete a music track with ObjectId containing all zeros returns 404
    When I send a DELETE request to "/api/music/000000000000000000000000"
    Then the response status code should be 404
    And the response field "success" should be false

  @EdgeCase
  Scenario: Delete a music track and verify versionKey not present in returned data
    When I send a DELETE request to "/api/music/{musicId}"
    Then the response status code should be 200
    And the response data should not have field "__v"