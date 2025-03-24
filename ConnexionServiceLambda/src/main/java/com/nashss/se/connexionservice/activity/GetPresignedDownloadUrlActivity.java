package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetPresignedDownloadUrlActivityRequest;
import com.nashss.se.connexionservice.activity.requests.GetPresignedUrlActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetPresignedDownloadUrlActivityResult;
import com.nashss.se.connexionservice.activity.results.GetPresignedUrlActivityResult;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import com.nashss.se.connexionservice.s3.PhotoDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.net.URL;

public class GetPresignedDownloadUrlActivity {
    private final Logger log = LogManager.getLogger();
    private final PhotoDao photoDao;
    private final MetricsPublisher metricsPublisher;
    /**
     * Instantiates a new UpdateUserProfileActivity object.
     *
     * @param photoDao PhotoDao to access the users photo.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public GetPresignedDownloadUrlActivity(PhotoDao photoDao, MetricsPublisher metricsPublisher) {
        this.photoDao = photoDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the user, updating, and persisting the user.
     * <p>
     * It then returns the updated user.
     * <p>
     * @param getPresignedDownloadUrlActivityRequest 
     * @return getPresignedDownloadUrlActivityResult result object containing the pre-signed URL to
     *      *                                       upload to an S3 bucket
     */
    public GetPresignedDownloadUrlActivityResult handleRequest(final GetPresignedDownloadUrlActivityRequest
                                                               getPresignedDownloadUrlActivityRequest) {

        log.info("Received GetPresignedDownloadUrlRequest {}", getPresignedDownloadUrlActivityRequest);

        String id = getPresignedDownloadUrlActivityRequest.getId();
        String key = id + "profile-photo";

        URL presignedUrl = photoDao.generatePresignedDownloadUploadUrl("connexionphotos", key);
        String downloadUrl = presignedUrl.toString();

        return GetPresignedDownloadUrlActivityResult.builder()
                .withDownloadUrl(downloadUrl)
                .build();
    }
}
