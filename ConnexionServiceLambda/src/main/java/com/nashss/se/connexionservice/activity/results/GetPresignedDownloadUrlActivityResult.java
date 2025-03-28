package com.nashss.se.connexionservice.activity.results;

public class GetPresignedDownloadUrlActivityResult {
    private final String downloadUrl;

    /**
     * Constructor for UpdateUserPhotoActivityResult.
     * @param downloadUrl the result of put request.
     */
    public GetPresignedDownloadUrlActivityResult(String downloadUrl) {

        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {

        return downloadUrl;
    }

    @Override
    public String toString() {
        return "GetPresignedDownloadUrlActivityResult{" +
                "downloadUrl=" + downloadUrl +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {
        private String dldUrl;

        public Builder withDownloadUrl(String dldUrl) {
            this.dldUrl = dldUrl;
            return this;
        }

        public GetPresignedDownloadUrlActivityResult build() {

            return new GetPresignedDownloadUrlActivityResult(dldUrl);
        }
    }
}
