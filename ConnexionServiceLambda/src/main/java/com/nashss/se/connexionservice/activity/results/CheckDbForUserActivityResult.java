package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.models.UserModel;

public class CheckDbForUserActivityResult {
    private final UserModel user;

    private CheckDbForUserActivityResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "CheckDbForUserActivityResult{" +
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

        public CheckDbForUserActivityResult build() {
            return new CheckDbForUserActivityResult(user);
        }
    }
}

