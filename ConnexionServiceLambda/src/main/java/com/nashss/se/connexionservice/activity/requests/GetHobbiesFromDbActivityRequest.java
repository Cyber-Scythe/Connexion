package com.nashss.se.connexionservice.activity.requests;

public class GetHobbiesFromDbActivityRequest {

    private GetHobbiesFromDbActivityRequest() {

    }

    /*
    @Override
    public String toString() {
        return "GetHobbiesFromDbActivityRequest{"'\'' + '}';
    }
     */

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        public GetHobbiesFromDbActivityRequest build() {
            return new GetHobbiesFromDbActivityRequest();
        }
    }
}

