import { ResendConfirmationCodeCommand } from '@aws-sdk/client-cognito-identity-provider';
import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { CognitoIdentityProviderClient, ConfirmSignUpCommand } from "@aws-sdk/client-cognito-identity-provider";
import { CognitoUserSession, CognitoAccessToken } from 'amazon-cognito-identity-js';
 /**
  * Logic needed for the confirmSignUp page of the website.
  */
 class ConfirmSignUp extends BindingClass {
     constructor() {
         super();

         this.bindClassMethods(['mount', 'confirmSignUp', 'resendConfirmationCode', 'updateUserInfo'], this);

         // Create a new datastore
         this.dataStore = new DataStore();
         //this.header = new Header(this.dataStore);

        const submitButton = document.getElementById('confirmationSubmitButton--0');
        submitButton.onclick = this.confirmSignUp;

        const resendLink = document.getElementById('resendButtonDiv');
        resendLink.onclick = this.resendConfirmationCode;

        console.log("ConfirmSignUp constructor");
     }

      /**
      * Add the header to the page and load the ConnexionClient.
      */
      mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.

        //this.header.addHeaderToPage();

        this.client = new ConnexionClient();
      }

    /**
    *  Define Class Methods Below.
    */

    async confirmSignUp() {

        const clientId = 'ubklaouam3eupblmrptdd1bn0';
        const region = 'us-east-2';

        const username = localStorage.getItem('email');
        const confirmationInput = document.getElementById('confirmationNumInput--0');
        const confirmationCode = confirmationInput.value;

        if (!confirmationInput) {
            const invalidBox = document.getElementById('invalid-box');
            invalidBox.textContent = 'Please enter a valid confirmation number';
            return invalidBox;
        }

        const client = new CognitoIdentityProviderClient({region});

        const input = { // ConfirmSignUpRequest
            ClientId: clientId, // required
            Username: username, // required
            ConfirmationCode: confirmationCode, // required
        };

        const command = new ConfirmSignUpCommand(input);

        try {
            const response = await client.send(command);

            if (response.Session) {
                console.log("session: " + response.Session);
                localStorage.setItem('session', response.Session);

                const token = response.Session;



                console.log("Successfully confirmed signup!")

                const userId = localStorage.getItem('userId');
                const email = localStorage.getItem('email');
                const firstName = localStorage.getItem('firstName');
                const lastName = localStorage.getItem('lastName');
                const gender = localStorage.getItem('gender');
                const birthMonth = localStorage.getItem('birthMonth');
                const birthDay = localStorage.getItem('birthDay');
                const birthYear = localStorage.getItem('birthYear');


                 var personalityType = null;
                 var aboutMe = null;
                 var hobbies = null;
                 var connexions = null;
                 var city = null;
                 var state = null;
                 var country = null;

                 const updatedUser = this.updateUserInfo(userId,
                                                email,
                                                firstName,
                                                lastName,
                                                gender,
                                                birthMonth,
                                                birthDay,
                                                birthYear,
                                                city,
                                                state,
                                                country,
                                                personalityType,
                                                hobbies,
                                                aboutMe,
                                                connexions,
                                                token);

                console.log("updatedUser: " + updatedUser);

                if(updatedUser) {
                    console.log("user updated");
                    // window.location.href = '/edit_profile.html';
                }
            }
        } catch (error) {
            if (error.code == "CodeMismatchException") {
                const invalidBox = document.getElementById('invalid-box');
                invalidBox.textContent = 'Invalid confirmation code';
                return invalidBox;
            } else if (error.code == "ExpiredCodeException") {
                const invalidBox = document.getElementById('invalid-box');
                invalidBox.textContent = 'Confirmation code has expired.';
                return invalidBox;
            }
            throw error;
        }
     }

     async resendConfirmationCode() {
        console.log("Inside resendConfirmationCode()");

        const clientId = 'ubklaouam3eupblmrptdd1bn0';
        const region = 'us-east-2';

        const username = localStorage.getItem('email');
        console.log("username: " + username);

        const client = new CognitoIdentityProviderClient({region});

        const input = { // ConfirmSignUpRequest
            ClientId: clientId, // required
            Username: username, // required
        };

        const command = new ResendConfirmationCodeCommand(input);

        try {
            const response = await client.send(command);
            console.log('response: ' + response.$metadata.httpResponseCode);

            if (response.session) {
                console.log("session: " + response.session);
                console.log("Successfully resent confirmation code!");
            }
        } catch (error) {
            console.log("error: " + error);
            throw error;
        }
     }

     /**
         * Function updates user's info in the database with the information provided by the user during sign up.
         * @param userId The user ID of the current user.
         * @param firstName The first name of the current user.
         * @param lastName The last name of the current user.
         * @param gender The gender of the current user.
         * @param birthMonth The birth month of the current user.
         * @param birthDay The birth day of the current user.
         * @param birthYear The birth year of the current user.
         * @param city The city of the current user.
         * @param state The state of the current user.
         * @param country The country of the current user.
         * @param personalityType The personality type of the current user.
         * @param aboutMe The about me section of the current user.
         * @param hobbies The hobbies of the current user.
         * @param connexions The connexions of the current user.
         * @returns The updated user.
         */
         async updateUserInfo(userId,
                              email,
                              firstName,
                              lastName,
                              birthMonth,
                              birthDay,
                              birthYear,
                              gender,
                              city,
                              state,
                              country,
                              personalityType,
                              aboutMe,
                              hobbies,
                              connexions,
                              token) {

             const user = await this.client.updateUserProfile(userId,
                                                             email,
                                                             firstName,
                                                             lastName,
                                                             birthMonth,
                                                             birthDay,
                                                             birthYear,
                                                             gender,
                                                             city,
                                                             state,
                                                             country,
                                                             personalityType,
                                                             aboutMe,
                                                             hobbies,
                                                             connexions,
                                                             token);

             return user;
         }
}

/**
* Main method to run when the page contents have loaded.
*/
const main = async () => {
    const confirmSignUp = new ConfirmSignUp();
    confirmSignUp.mount();
};

window.addEventListener('DOMContentLoaded', main);