package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetPresignedUrlActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetPresignedUrlActivityResult;

import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import com.nashss.se.connexionservice.s3.PhotoDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.net.URL;

public class GetPresignedUrlActivity {
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
    public GetPresignedUrlActivity(PhotoDao photoDao, MetricsPublisher metricsPublisher) {
        this.photoDao = photoDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the user, updating, and persisting the user.
     * <p>
     * It then returns the updated user.
     * <p>
     * @param getPresignedUrlActivityRequest request object containing the id of the current user
     * @return getPresignedUrlActivityResult result object containing the pre-signed URL to
     *      *                                       upload to an S3 bucket
     */
    public GetPresignedUrlActivityResult handleRequest(final GetPresignedUrlActivityRequest
                                                               getPresignedUrlActivityRequest) {

        log.info("Received UpdateUserPhotoActivityRequest {}", getPresignedUrlActivityRequest);

        String id = getPresignedUrlActivityRequest.getId();
        String key = id + "profile-photo";

        URL presignedUrl = photoDao.generatePresignedUploadUrl("connexionphotos", key);
        String result = presignedUrl.toString();

        return GetPresignedUrlActivityResult.builder()
                .withPutResult(result)
                .build();
    }
}
