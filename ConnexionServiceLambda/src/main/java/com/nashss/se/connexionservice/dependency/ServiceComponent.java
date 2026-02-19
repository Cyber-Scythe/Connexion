package com.nashss.se.connexionservice.dependency;

import com.nashss.se.connexionservice.activity.*;

import com.nashss.se.connexionservice.activity.results.GetPresignedDownloadUrlActivityResult;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Connexion Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return GetUserProfileActivity
     */
    GetUserProfileActivity provideGetUserProfileActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateUserProfileActivity
     */
    UpdateUserProfileActivity provideUpdateUserProfileActivity();

    /**
     * Provides the relevant activity.
     * @return GetHobbiesFromDbActivity
     */
    GetHobbiesFromDbActivity provideGetHobbiesFromDbActivity();

    /**
     * Provides the relevant activity.
     * @return GetConnexionsActivity
     */
    GetConnexionsActivity provideGetConnexionsActivity();

    /**
     * Provides the relevant activity.
     * @return GetConnexionProfileActivity
     */
    GetConnexionProfileActivity provideGetConnexionProfileActivity();

    /**
     * Provides the relevant activity.
     * @return GetUserInboxActivity
     */
    GetUserInboxActivity provideGetUserInboxActivity();

    /**
     * Provides the relevant activity.
     * @return GetMessagesWithUserActivity
     */
    GetMessagesWithUserActivity provideGetMessagesWithUserActivity();

    /**
     * Provides the relevant activity.
     * @return SendNewMessageActivity
     */
    SendNewMessageActivity provideSendNewMessageActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteMessagesActivity
     */
    DeleteMessagesActivity provideDeleteMessagesActivity();

    /**
     * Provides the relevant activity.
     * @return GetPresignedUrlActivity
     */
    GetPresignedUrlActivity provideGetPresignedUrlActivity();

    /**
     * Provides the relevant activity
     * @return GetPresignedDownloadUrl
     */
    GetPresignedDownloadUrlActivity provideGetPresignedDownloadUrlActivity();

    /**
     * Provides the relevant activity.
     * @return CreateNewUserActivity
     */
    CreateNewUserActivity provideCreateNewUserActivity();
}
