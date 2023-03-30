package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;

import java.util.List;

public class GetConnexionsActivityResult {
    private final List<UserModel> connexionsList;

    /**
     * Constructor for GetConnexionsActivityResult.
     * @param connexionsList the list of connexions to convert
     */
    public GetConnexionsActivityResult(List<UserModel> connexionsList) {

        this.connexionsList = connexionsList;
    }

    public List<UserModel> getConnexions() {

        return connexionsList;
    }

    @Override
    public String toString() {
        return "GetConnexionsActivityResult{" +
                "connexionsList=" + connexionsList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<UserModel> connexionsList;

        public Builder withConnexionsList(List<UserModel> connexionsList) {
            this.connexionsList = connexionsList;
            return this;
        }

        public GetConnexionsActivityResult build() {

            return new GetConnexionsActivityResult(connexionsList);
        }
    }
}
