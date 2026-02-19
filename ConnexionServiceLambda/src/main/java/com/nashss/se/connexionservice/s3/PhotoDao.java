package com.nashss.se.connexionservice.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.io.*;
import java.net.URL;
import java.time.Instant;

@Singleton
public class PhotoDao {
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a UserDao object.
     *
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public PhotoDao(MetricsPublisher metricsPublisher) {
        this.metricsPublisher = metricsPublisher;
    }

    public AmazonS3 setUpS3Client() {
        Regions clientRegion = Regions.US_EAST_2;

        return AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build();
    }

    public URL generatePresignedUploadUrl(String bucketName, String objectKey) {

        try {
           AmazonS3 s3Client = setUpS3Client();

            // Set the pre-signed URL to expire after one hour.
//            java.util.Date expiration = new java.util.Date();
//            long expTimeMillis = Instant.now().toEpochMilli();
//            expTimeMillis += 1000 * 60 * 60;
//            expiration.setTime(expTimeMillis);

            // Generate the pre-signed URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.PUT);
                            //.withContentType("image/*");
                            //.withExpiration(expiration);

            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            System.out.println("Pre-Signed URL: " + url.toString());
            return url;

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }

        return null;
    }

    public URL generatePresignedDownloadUploadUrl(String bucketName, String objectKey) {

        try {
            AmazonS3 s3Client = setUpS3Client();

            // Generate the pre-signed URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET);

            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            System.out.println("Pre-Signed download URL: " + url.toString());
            return url;

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }

        return null;
    }
}
