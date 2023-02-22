package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.models.MessageModel;

public class DeleteMessagesActivityResult {
    private final MessageModel message;
    private DeleteMessagesActivityResult(MessageModel message) {

        this.message = message;
    }

    public MessageModel getMessage() {

        return message;
    }

    @Override
    public String toString() {
        return "DeleteMessagesActivityResult{" +
                "message=" + message +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {
        private MessageModel message;

        public Builder withMessage(MessageModel message) {
            this.message = message;
            return this;
        }
        public DeleteMessagesActivityResult build() {

            return new DeleteMessagesActivityResult(message);
        }
    }
}
