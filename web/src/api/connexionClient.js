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

        const methodsToBind = ['clientLoaded',
                               'getIdentity',
                               'login',
                               'logout',
                               'signUp',
                               'getProfile',
                               'getProfileByEmail',
                               'getConnexionProfile',
                               'getConnexions',
                               'updateUserProfile',
                               'getPresignedUrl',
                               'getPresignedDownloadUrl',
                               'getHobbiesList',
                               'getAllMessages',
                               'getMessagesWithUser',
                               'sendNewMessage',
                               'deleteMessages'];

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

    async signUp() {
        this.authenticator.signUp();
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
    async getProfile(token, errorCallback) {
     try {
          //const token = await this.getTokenOrThrow("Only authenticated users can view profiles");
          if (token == null) {
            throw new Error("Only authenticated users can view profiles");
          }

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
    * Gets the user for the given email.
    * @param userEmail email associated with user
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The user's metadata.
    */
    async getProfileByEmail(userEmail, errorCallback) {
     try {
          userEmail = encodeURIComponent(userEmail);
          const token = await this.getTokenOrThrow("Only authenticated users can view profiles");
          const response = await this.axiosClient.get(`/index/${userEmail}`, {
                headers: {
                  Authorization: `Bearer ${token}`
                }
            });
         return response.data.user;

       } catch(error) {
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
          const response = await this.axiosClient.get(`/index/${userId}`, {
                headers: {
                  Authorization: `Bearer ${token}`
                }
            });
         return response.data.userModel;

       } catch (error) {
         this.handleError(error, errorCallback)
       }
    }


   /**
    * Get the user's hobbies from DB.
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The list of hobbies.
    */
    async getHobbiesList(token, errorCallback) {
       try {
           if (token == null) {
            throw new Error("Only authenticated users can view hobbies");
           }

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
    * @param personalityType The personality type of the current user
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
    * @param userId The user ID of the current user.
    * @param firstName The name of the user to add to profile.
    * @param lastName The name of the user to add to profile.
    * @param birthMonth The birth month of the current user.
    * @param birthDay The birth day of the current user.
    * @param birthYear The birth year of the current user.
    * @param gender The gender of the current user.
    * @param city The city that the user lives in.
    * @param state The state that the user lives in.
    * @param country The country that the user lives in.
    * @param personalityType the personalityType of the user
    * @hobbies list of the user's hobbies
    * @connexions list of the user's connexions
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The updated user profile.
    */
    async updateUserProfile(userId, email, firstName, lastName, gender, birthMonth, birthDay, birthYear, city, state, country, personalityType, hobbies, aboutMe, connexions, token, errorCallback) {
        try {

            const response = await this.axiosClient.post(`/index/update/${userId}`, {
                id:  userId,
                email: email,
                firstName: firstName,
                lastName: lastName,
                gender: gender,
                birthMonth: birthMonth,
                birthDay: birthDay,
                birthYear: birthYear,
                city: city,
                state: state,
                country: country,
                personalityType: personalityType,
                hobbies: hobbies,
                aboutMe: aboutMe,
                connexions: connexions
            },{
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.user;
        } catch (error) {
            console.error(error.response.data);
            this.handleError(error, errorCallback)
        }
    }

   /**
    * Get pre-signed URL.
    * @param userId The user ID of the current user.
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns A pre-signed URL for user to upload photo with.
    */
    async getPresignedUrl(userId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can upload a photo.");
            const response = await this.axiosClient.get(`/index/${userId}/url`, {

                headers: {
                    Authorization: `Bearer ${token}`
                },
            });

            return response.data.result;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

   /**
    * Get pre-signed download URL.
    * @param userId The user ID of the current user.
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns A pre-signed URL for user to download photo from.
    */
    async getPresignedDownloadUrl(token, userId, errorCallback) {
        try {
            if(token == null) {
                throw new Error("Only authenticated users can download a photo.");
            }

            const response = await this.axiosClient.get(`/index/${userId}/downloadUrl`, {
                headers: {
                    Authorization: `Bearer ${token}`
                },
            });

            return response.data.downloadUrl;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Send a new message to a user.
     * @param recipientEmail The email of the receiving user.
     * @param messageContent The content of the message being sent.
     * @param dateTimeSent The date and time the message was sent.
     * @param messageContent The content of the message.
     * @param readStatus The status of the message
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
     async sendNewMessage(recipientEmail, messageContent, readStatus, errorCallback) {

         try {
            const token = await this.getTokenOrThrow("Only authenticated users can send messages.");
            const response = await this.axiosClient.post(`/inbox`, {

            recipientEmail: recipientEmail,
            messageContent: messageContent,
            readStatus: readStatus,
            },{
                 headers: {
                    Authorization: `Bearer ${token}`
                 }
             });

              return response.data.message;

          } catch (error) {
                this.handleError(error, errorCallback)
          }
     }


    /**
     * Get all messages with current user from inbox
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of most recent messages from each user.
     */
     async getAllMessages(errorCallback) {
         try {
                 const token = await this.getTokenOrThrow("Only authenticated users can view inbox.");
                 const response = await this.axiosClient.get(`/inbox`, {
                     headers: {
                         Authorization: `Bearer ${token}`
                     }
                 });
                 return response.data.messages;
             } catch (error) {
                 this.handleError(error, errorCallback)
             }
     }


    /**
     * Get all messages with a specified user from inbox.
     * @param otherUserEmail The email of the other user.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of messages with specified user.
     */
     async getMessagesWithUser(otherUserEmail, errorCallback) {
         try {
                 const token = await this.getTokenOrThrow("Only authenticated users can view inbox.");
                 const response = await this.axiosClient.get(`/inbox/${otherUserEmail}`, {
                     headers: {
                         Authorization: `Bearer ${token}`
                     }
                 });
                 return response.data.messages;
             } catch (error) {
                 this.handleError(error, errorCallback)
             }
     }

   /**
    * Delete messages from DB.
    * @param dateTimeSent The date and time a message was sent.
    * @param senderEmail The email of the sender.
    * @param errorCallback (Optional) A function to execute if the call fails.
    */
    async deleteMessages(dateTimeSent, senderEmail, errorCallback) {
       senderEmail = encodeURIComponent(senderEmail);
       try {
           const token = await this.getTokenOrThrow("Only authenticated users can delete messages.");
           const response = await this.axiosClient.delete(`/index/delete?dateTimeSent=${dateTimeSent}&senderEmail=${senderEmail}`,
            {
                       headers: {
                           Authorization: `Bearer ${token}`
                       }
                   });
                   return response.data.message;
               } catch (error) {
                   this.handleError(error, errorCallback)
               }
    }

    /**
     * Create profile for a new user.
     * @param userId The user ID of the current user.
     * @param firstName The name of the user to add to profile.
     * @param lastName The name of the user to add to profile.
     * @param birthMonth The birth month of the current user.
     * @param birthDay The birth day of the current user.
     * @param birthYear The birth year of the current user.
     * @param gender The gender of the current user.
     * @param city The city that the user lives in.
     * @param state The state that the user lives in.
     * @param country The country that the user lives in.
     * @param personalityType the personalityType of the user
     * @hobbies list of the user's hobbies
     * @connexions list of the user's connexions
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The new user profile.
     */
     async createNewUser(userId, email, firstName, lastName, gender, birthMonth, birthDay, birthYear, city, state, country, personalityType, hobbies, aboutMe, connexions, token, errorCallback) {
         try {
                  const response = await this.axiosClient.post(`/index/create/${userId}`, {
                    id:  userId,
                    email: email,
                    firstName: firstName,
                    lastName: lastName,
                    gender: gender,
                    birthMonth: birthMonth,
                    birthDay: birthDay,
                    birthYear: birthYear,
                    city: city,
                    state: state,
                    country: country,
                    personalityType: personalityType,
                    hobbies: hobbies,
                    aboutMe: aboutMe,
                    connexions: connexions
                },{
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                return response.data.user;
            } catch (error) {
                console.error(error.response.data);
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
