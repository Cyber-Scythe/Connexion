package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.dynamodb.models.Message;

import java.util.List;

public class GetUserInboxActivityResult {
    private final List<Message> messages;

    /**
     * Constructor for GetUserInboxActivityResult.
     * @param messages the list of messages to return as result
     */
    public GetUserInboxActivityResult(List<Message> messages) {

        this.messages = messages;
    }

    public List<Message> getMessages() {

        return messages;
    }

    @Override
    public String toString() {
        return "GetUserInboxActivityResult{" +
                "messages=" + messages +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder(); }

    public static class Builder {
        private List<Message> messages;

        public Builder withMessages(List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public GetUserInboxActivityResult build() {

            return new GetUserInboxActivityResult(messages);
        }
    }
}
