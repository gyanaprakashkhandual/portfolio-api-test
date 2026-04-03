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
public class MusicResponse {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("total")
    private Integer total;

    public MusicPojo getDataAsSingleMusic() {
        if (data == null) return null;
        return new com.fasterxml.jackson.databind.ObjectMapper()
                .convertValue(data, MusicPojo.class);
    }

    public List<MusicPojo> getDataAsMusicList() {
        if (data == null) return java.util.Collections.emptyList();
        return new com.fasterxml.jackson.databind.ObjectMapper()
                .convertValue(data,
                        new com.fasterxml.jackson.core.type.TypeReference<List<MusicPojo>>() {});
    }
}