package com.nashss.se.connexionservice.activity.results;

import com.nashss.se.connexionservice.dynamodb.models.Message;

import java.util.List;

public class GetMessagesWithUserActivityResult {
    private final List<Message> messages;

    /**
     * Constructor for GetConnexionsActivityResult.
     * @param messages the list of connexions to convert
     */
    public GetMessagesWithUserActivityResult(List<Message> messages) {

        this.messages = messages;
    }

    public List<Message> getMessages() {

        return messages;
    }

    @Override
    public String toString() {
        return "GetMessagesFromUserActivityResult{" +
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

        public GetMessagesWithUserActivityResult build() {

            return new GetMessagesWithUserActivityResult(messages);
        }
    }
}
