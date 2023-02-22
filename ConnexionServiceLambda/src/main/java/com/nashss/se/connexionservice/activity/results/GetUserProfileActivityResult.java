package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.models.UserModel;

public class GetUserProfileActivityResult {
    private final UserModel user;

    private GetUserProfileActivityResult(UserModel user) {

        this.user = user;
    }

    public UserModel getUser() {

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
        private UserModel user;

        public Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public GetUserProfileActivityResult build() {

            return new GetUserProfileActivityResult(user);
        }
    }
}
