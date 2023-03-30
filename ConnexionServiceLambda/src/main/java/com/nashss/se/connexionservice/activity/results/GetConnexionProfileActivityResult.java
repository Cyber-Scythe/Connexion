package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.models.UserModel;

public class GetConnexionProfileActivityResult {
    private final UserModel user;

    private GetConnexionProfileActivityResult(UserModel user) {

        this.user = user;
    }

    public UserModel getUserModel() {

        return user;
    }

    @Override
    public String toString() {
        return "GetUserProfileActivityResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {
        private UserModel userModel;

        public Builder withUserModel(UserModel userModel) {
            this.userModel = userModel;
            return this;
        }

        public GetConnexionProfileActivityResult build() {

            return new GetConnexionProfileActivityResult(userModel);
        }
    }
}
