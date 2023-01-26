package com.nashss.se.connexionservice.activity.results;

public class CreateUserActivityResult {
    private final UserModel user;

    private CreateUserActivityResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "CreateUserActivityResults{" +
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

        public CreateUserActivityResult build() {
            return new CreateUserActivityResult(user);
        }
    }
}
