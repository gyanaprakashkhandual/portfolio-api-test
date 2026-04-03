package com.framework.constants;

public class MusicConstants {

    private MusicConstants() {
    }

    public static final String BASE_MUSIC_ENDPOINT = "/api/music";
    public static final String MUSIC_BY_ID_ENDPOINT = "/api/music/{musicId}";
    public static final String MUSIC_LIKE_ENDPOINT = "/api/music/{musicId}/like";
    public static final String MUSIC_LIKES_ENDPOINT = "/api/music/{musicId}/likes";
    public static final String MUSIC_COMMENTS_ENDPOINT = "/api/music/{musicId}/comments";
    public static final String MUSIC_COMMENT_BY_ID = "/api/music/{musicId}/comments/{commentId}";

    public static final String PATH_PARAM_MUSIC_ID = "musicId";
    public static final String PATH_PARAM_COMMENT_ID = "commentId";

    public static final String FIELD_ID = "_id";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_ARTIST = "artist";
    public static final String FIELD_ALBUM = "album";
    public static final String FIELD_GENRE = "genre";
    public static final String FIELD_RELEASE_DATE = "releaseDate";
    public static final String FIELD_MUSIC_URL = "musicUrl";
    public static final String FIELD_DURATION = "duration";
    public static final String FIELD_COVER_IMAGE_URL = "coverImageUrl";
    public static final String FIELD_LYRICS = "lyrics";
    public static final String FIELD_LIKES = "likes";
    public static final String FIELD_COMMENTS = "comments";
    public static final String FIELD_CREATED_AT = "createdAt";
    public static final String FIELD_UPDATED_AT = "updatedAt";
    public static final String FIELD_SUCCESS = "success";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_DATA = "data";
    public static final String FIELD_TOTAL = "total";
    public static final String FIELD_VERSION_KEY = "__v";

    public static final String MSG_MUSIC_FETCHED = "Music fetched successfully";
    public static final String MSG_MUSIC_CREATED = "Music created successfully";
    public static final String MSG_MUSIC_UPDATED = "Music updated successfully";
    public static final String MSG_MUSIC_DELETED = "Music deleted successfully";

    public static final String TEST_DATA_DIR = "src/test/resources/testdata/music/";
    public static final String CREATE_MUSIC_JSON = "create_music.json";
    public static final String CREATE_MUSIC_WITH_LYRICS = "create_music_with_lyrics.json";
    public static final String UPDATE_MUSIC_JSON = "update_music.json";

    public static final String NON_EXISTENT_ID = "64f1a2b3c4d5e6f7a8b9c000";
    public static final String MALFORMED_ID = "bad-id-format";
    public static final String ALL_ZEROS_ID = "000000000000000000000000";
    public static final String ALL_LOWERCASE_HEX_ID = "aabbccddeeff001122334455";
}