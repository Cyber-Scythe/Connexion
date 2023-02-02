package com.nashss.se.connexionservice.dependency;

import com.nashss.se.connexionservice.activity.CheckDbForUserActivity;
import com.nashss.se.connexionservice.activity.GetUserProfileActivity;
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
   // SearchPlaylistsActivity provideSearchPlaylistsActivity();

    /**
     * Provides the relevant activity.
     * @return GetPlaylistSongsActivity
     */
   // GetPlaylistSongsActivity provideGetPlaylistSongsActivity();

    /**
     * Provides the relevant activity.
     * @return UpdatePlaylistActivity
     */
   // UpdatePlaylistActivity provideUpdatePlaylistActivity();

}
