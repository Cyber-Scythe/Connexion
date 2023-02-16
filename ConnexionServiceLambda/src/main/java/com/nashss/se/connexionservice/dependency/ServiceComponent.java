package com.nashss.se.connexionservice.dependency;

import com.nashss.se.connexionservice.activity.*;
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
     * @return AddSongToPlaylistActivity
     */
  //  AddSongToPlaylistActivity provideAddSongToPlaylistActivity();

    /**
     * Provides the relevant activity.
     * @return CheckDbForUserActivity
     */
    CheckDbForUserActivity provideCheckDbForUserActivity();


    /**
     * Provides the relevant activity.
     * @return GetPlaylistActivity
     */
    GetUserProfileActivity provideGetUserProfileActivity();

    /**
     * Provides the relevant activity.
     * @return GetPlaylistActivity
     */
    UpdateUserProfileActivity provideUpdateUserProfileActivity();

    /**
     * Provides the relevant activity.
     * @return GetPlaylistSongsActivity
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
     * @return GetConnexionProfileActivity
     */
    GetUserInboxActivity provideGetUserInboxActivity();

    /**
     * Provides the relevant activity.
     * @return GetConnexionProfileActivity
     */
    GetMessagesFromUserActivity provideGetMessagesFromUserActivity();

    /**
     * Provides the relevant activity.
     * @return SendNewMessageActivity
     */
    SendNewMessageActivity provideSendNewMessageActivity();
}
