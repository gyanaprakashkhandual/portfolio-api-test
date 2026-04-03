package com.framework.pojo.music;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MusicPojo {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("album")
    private String album;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("releaseDate")
    private String releaseDate;

    @JsonProperty("musicUrl")
    private String musicUrl;

    @JsonProperty("duration")
    private Number duration;

    @JsonProperty("coverImageUrl")
    private String coverImageUrl;

    @JsonProperty("lyrics")
    private String lyrics;

    @JsonProperty("likes")
    private List<String> likes;

    @JsonProperty("comments")
    private List<CommentPojo> comments;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}