import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";
import { uuid } from 'uuid';

/**
 * Client to call the ConnexionService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class ConnexionClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'createUserProfile', 'getPlaylist', 'getPlaylistSongs', 'createPlaylist'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the profile for the given user ID.
     * @param id Unique identifier for a profile
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The profile's metadata.
     */
    async getProfile(userId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`home/${userId}`);
            return response.data.profile;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Get the songs on a given playlist by the playlist's identifier.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of songs on a playlist.
     */
    async getPlaylistSongs(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`playlists/${id}/songs`);
            return response.data.songList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create a new user profile owned by the current user.
     * all information fields will be empty until the user
     * edits their profile.
     * @param email: The email address of the profile to create.
     */
     async createUserProfile(email, errorCallback) {

        const { uuid } = require('uuid');
        const userId = uuid();


        const token = await this.getTokenOrThrow("Only authenticated users can create a profile.");
        const response = await this.axiosClient.post(`${userId}`, {
            userId: userId,
            email: email
        }, {
            headers: {
                Authorization: `Bearer ${token}`
            }
       });
            return response.data.userId;
        }

    /**
     * Edit the user profile.
     * @param id The id of the profile to edit.
     * @param email The email of the user who owns the profile.
     * @param firstName The first name of the profile owner
     * @param lastName The last name of the profile owner
     * @param location The location of the profile owner
     * @param birthdate The birthdate of the profile owner
     * @param personalityType The personality type of the profile owner
     * @param hobbies The hobbies of the profile owner.
     * @returns The updated user profile.
     */
    async editUserProfile(email, firstName, lastName, location, birthdate, personalityType, hobbies, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a song to a playlist.");
            const response = await this.axiosClient.post(`home/${userId}/edit-profile`, {
                email: email,
                firstName: firstName,
                lastName: lastName,
                location: location,
                birthdate: birthdate,
                personalityType: personalityType,
                hobbies: hobbies
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.profile;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Search for a soong.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The playlists that match the search criteria.
     */
    async search(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`playlists/search?${queryString}`);

            return response.data.playlists;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
