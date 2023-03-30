package com.nashss.se.connexionservice.activity.results;

public class GetPresignedUrlActivityResult {
    private final String result;

    /**
     * Constructor for UpdateUserPhotoActivityResult.
     * @param result the result of put request.
     */
    public GetPresignedUrlActivityResult(String result) {

        this.result = result;
    }

    public String getResult() {

        return result;
    }

    @Override
    public String toString() {
        return "UpdateUserPhotoActivityResult{" +
                "result=" + result +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {
        private String putResult;

        public Builder withPutResult(String putResult) {
            this.putResult = putResult;
            return this;
        }

        public GetPresignedUrlActivityResult build() {

            return new GetPresignedUrlActivityResult(putResult);
        }
    }
}
