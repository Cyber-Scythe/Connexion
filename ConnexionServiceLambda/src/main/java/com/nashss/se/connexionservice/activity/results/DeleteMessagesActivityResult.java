package com.nashss.se.connexionservice.activity.results;

public class DeleteMessagesActivityResult {
    private final boolean result;
    private DeleteMessagesActivityResult(boolean result) {

        this.result = result;
    }

    public boolean getResult() {

        return result;
    }

    @Override
    public String toString() {
        return "DeleteMessagesActivityResult{" +
                "result=" + result +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {
        private boolean result;

        public Builder withResult(boolean result) {
            this.result = result;
            return this;
        }
        public DeleteMessagesActivityResult build() {

            return new DeleteMessagesActivityResult(result);
        }
    }
}
