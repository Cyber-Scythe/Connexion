package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.dynamodb.models.User;

import java.util.List;

public class GetConnexionsActivityResult {
    private final List<User> connexions;

    /**
     * Constructor for GetConnexionsActivityResult.
     * @param connexions the list of connexions to convert
     */
    public GetConnexionsActivityResult(List<User> connexions) {
        this.connexions = connexions;
    }

    public List<User> getConnexions() {
        return connexions;
    }

    @Override
    public String toString() {
        return "GetConnexionsActivityResult{" +
                "connexions=" + connexions +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private List<User> connexionsList;

        public Builder withConnexions(List<User> connexions) {
            connexionsList = connexions;
            return this;
        }

        public GetConnexionsActivityResult build() {

            return new GetConnexionsActivityResult(connexionsList);
        }
    }
}
