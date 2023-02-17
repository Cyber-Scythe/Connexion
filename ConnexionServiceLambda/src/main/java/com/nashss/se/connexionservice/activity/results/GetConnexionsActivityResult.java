package com.nashss.se.connexionservice.activity.results;

import java.util.List;

public class GetConnexionsActivityResult {
    private final List<String> connexionsList;

    /**
     * Constructor for GetConnexionsActivityResult.
     * @param connexionsList the list of connexions to convert
     */
    public GetConnexionsActivityResult(List<String> connexionsList) {
        this.connexionsList = connexionsList;
    }

    public List<String> getConnexions() {
        return connexionsList;
    }

    @Override
    public String toString() {
        return "GetConnexionsActivityResult{" +
                "connexionsList=" + connexionsList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private List<String> connexionsList;

        public Builder withConnexionsList(List<String> connexionsList) {
            this.connexionsList = connexionsList;
            return this;
        }

        public GetConnexionsActivityResult build() {

            return new GetConnexionsActivityResult(connexionsList);
        }
    }
}
