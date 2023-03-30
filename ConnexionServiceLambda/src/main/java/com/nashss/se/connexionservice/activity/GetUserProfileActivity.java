package com.nashss.se.connexionservice.activity;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.nashss.se.connexionservice.activity.requests.GetUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserProfileActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;


/**
 * Implementation of the GetPlaylistActivity for the MusicPlaylistService's GetPlaylist API.
 *
 * This API allows the customer to get one of their saved playlists.
 */
public class GetUserProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new GetUserProfileActivity object.
     *
     * @param userDao UserDao to access the users table.
     */
    @Inject
    public GetUserProfileActivity(UserDao userDao) {

        this.userDao = userDao;
    }

    /**
     * This method handles the incoming request by retrieving the user from the database.
     * <p>
     * It then returns the user.
     * <p>
     * @param getUserProfileActivityRequest request object containing the user's email
     * @return getUserProfileActivityResult result object containing the API defined {@link UserModel}
     */
    public GetUserProfileActivityResult handleRequest(final GetUserProfileActivityRequest
                                                              getUserProfileActivityRequest) {

        log.info("Received GetUserProfileActivityRequest {}", getUserProfileActivityRequest);

        String requestedId = getUserProfileActivityRequest.getId();
        User user = userDao.getUser(requestedId);
        UserModel userModel;

        if (user != null) {
            userModel = new ModelConverter().toUserModel(user);
        } else {

            User newUser = new User();
            newUser.setEmail(getUserProfileActivityRequest.getEmail());
            newUser.setName(getUserProfileActivityRequest.getName());
            newUser.setId(getUserProfileActivityRequest.getId());

            userDao.saveUser(newUser);
            userModel = new ModelConverter().toUserModel(newUser);

            createS3Bucket(getUserProfileActivityRequest.getId());
        }

        return GetUserProfileActivityResult.builder()
                .withUser(userModel)
                .build();
    }

    private void createS3Bucket(String userId) {

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.DEFAULT_REGION)
                .withCredentials(new ProfileCredentialsProvider(userId))
                .build();

        if (!s3.doesBucketExistV2(userId)) {
            try {
                    // Because the CreateBucketRequest object doesn't specify a region, the
                    // bucket is created in the region specified in the client.
                    s3.createBucket(new CreateBucketRequest(userId));

                    // Verify that the bucket was created by retrieving it and checking its location.
                    String bucketLocation = s3.getBucketLocation(new GetBucketLocationRequest(userId));
                    System.out.println("Bucket location: " + bucketLocation);
            } catch (AmazonServiceException e) {
                // The call was transmitted successfully, but Amazon S3 couldn't process
                // it and returned an error response.
                e.printStackTrace();
            } catch (SdkClientException e) {
                // Amazon S3 couldn't be contacted for a response, or the client
                // couldn't parse the response from Amazon S3.
                e.printStackTrace();
            }
        }
    }

}


