package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.dynamodb.models.Hobby;

import java.util.List;

public class GetHobbiesFromDbActivityResult {
    private final List<String> hobbyList;

    /**
     * Constructor for GetHobbiesFromDbActivityResult.
     * @param hobbyList the list of hobbies to convert
     */
    public GetHobbiesFromDbActivityResult(List<String> hobbyList) {

        this.hobbyList = hobbyList;
    }

    public List<String> getHobbies() {

        return hobbyList;
    }

    @Override
    public String toString() {
        return "GetHobbiesFromDbActivityResult{" +
                "hobbies=" + hobbyList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {
        private List<String> hobbyStringList;

        public Builder withHobbies(List<String> hobbies) {
            hobbyStringList = hobbies;
            return this;
        }

        public GetHobbiesFromDbActivityResult build() {

            return new GetHobbiesFromDbActivityResult(hobbyStringList);
        }
    }
}

