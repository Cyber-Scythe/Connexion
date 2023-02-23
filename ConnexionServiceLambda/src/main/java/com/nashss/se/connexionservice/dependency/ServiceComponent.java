package com.nashss.se.connexionservice.dependency;

import com.nashss.se.connexionservice.activity.CheckDbForUserActivity;
import com.nashss.se.connexionservice.activity.DeleteMessagesActivity;
import com.nashss.se.connexionservice.activity.GetConnexionProfileActivity;
import com.nashss.se.connexionservice.activity.GetConnexionsActivity;
import com.nashss.se.connexionservice.activity.GetHobbiesFromDbActivity;
import com.nashss.se.connexionservice.activity.GetMessagesWithUserActivity;
import com.nashss.se.connexionservice.activity.GetUserInboxActivity;
import com.nashss.se.connexionservice.activity.GetUserProfileActivity;
import com.nashss.se.connexionservice.activity.SendNewMessageActivity;
import com.nashss.se.connexionservice.activity.UpdateUserProfileActivity;

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
     * @return CheckDbForUserActivity
     */
    CheckDbForUserActivity provideCheckDbForUserActivity();


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
}
