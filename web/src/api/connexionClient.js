import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";


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

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getProfile', 'updateUserProfile', 'getHobbiesList', 'getMessages', 'getConnexions'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();
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
     * Gets the user for the given ID.
     * @param id Unique identifier for a user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user's metadata.
     */
    async getProfile(errorCallback) {
     try {
          const token = await this.getTokenOrThrow("Only authenticated users can view profiles");
          const response = await this.axiosClient.get(`/index`, {
                headers: {
                  Authorization: `Bearer ${token}`
                }
            });
         return response.data.user;

       } catch (error) {
         this.handleError(error, errorCallback)
       }
    }


    /**
     * Gets the user for the given ID.
     * @param id Unique identifier for a user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user's metadata.
     */
    async getConnexionProfile(userId, errorCallback) {
     try {
          const token = await this.getTokenOrThrow("Only authenticated users can view profiles");
          const response = await this.axiosClient.get(`/index/{userId}`,{
                headers: {
                  Authorization: `Bearer ${token}`
                }
            });
         return response.data.user;

       } catch (error) {
         this.handleError(error, errorCallback)
       }
    }


    /**
     * Get the hobbies from DB.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of hobbies.
     */
    async getHobbiesList(errorCallback) {
       try {
           const token = await this.getTokenOrThrow("Only authenticated users can retrieve hobbies from db.");
           const response = await this.axiosClient.get(`/index/hobbies`, {
                       headers: {
                           Authorization: `Bearer ${token}`
                       }
                   });
                   return response.data.hobbies;
               } catch (error) {
                   this.handleError(error, errorCallback)
               }
    }

    /**
     * Get connexions for the current user.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns List of connexions for the user.
     */
    async getConnexions(personalityType, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view connexions.");
            const response = await this.axiosClient.get(`/connexions/${personalityType}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.connexions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Update the user's profile.
     * @param name The name of the user to add to profile.
     * @param city The city that the user lives in.
     * @param state The state that the user lives in.
     * @param personalityType the personalityType of the user
     * @hobbies list of the user's hobbies
     * @connexions list of the user's connexions
     * @returns The list of songs on a playlist.
     */
    async updateUserProfile(name, age, city, state, personalityType, hobbies, connexions, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can edit their profile.");
            const response = await this.axiosClient.post(`/index`, {
                name: name,
                age: age,
                city: city,
                state: state,
                personalityType: personalityType,
                hobbies: hobbies,
                connexions: connexions
            },{
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.user;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
     async getMessages(errorCallback) {
         try {
                 const token = await this.getTokenOrThrow("Only authenticated users can view inbox.");
                 const response = await this.axiosClient.get(`/inbox`, {
                     headers: {
                         Authorization: `Bearer ${token}`
                     }
                 });
                 return response.data.user;
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
