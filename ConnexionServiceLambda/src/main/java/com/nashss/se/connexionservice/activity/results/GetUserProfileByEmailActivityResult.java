package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.models.UserModel;

public class GetUserProfileByEmailActivityResult {
    private final UserModel user;

    private GetUserProfileByEmailActivityResult(UserModel user) {

        this.user = user;
    }

    public UserModel getUser() {

        return user;
    }

    @Override
    public String toString() {
        return "GetUserProfileByEmailActivityResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {
        private UserModel user;

        public Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public GetUserProfileByEmailActivityResult build() {

            return new GetUserProfileByEmailActivityResult(user);
        }
    }
}
