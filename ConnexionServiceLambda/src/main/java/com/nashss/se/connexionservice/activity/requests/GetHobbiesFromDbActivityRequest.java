package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetHobbiesFromDbActivityRequest.Builder.class)
public class GetHobbiesFromDbActivityRequest {

    private GetHobbiesFromDbActivityRequest() {}

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        public GetHobbiesFromDbActivityRequest build() {

            return new GetHobbiesFromDbActivityRequest();
        }
    }
}

