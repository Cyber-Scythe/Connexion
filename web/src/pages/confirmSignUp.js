import { ResendConfirmationCodeCommand } from '@aws-sdk/client-cognito-identity-provider';
import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { CognitoIdentityProviderClient, ConfirmSignUpCommand, AuthFlowType, InitiateAuthCommand } from "@aws-sdk/client-cognito-identity-provider";



 /**
  * Logic needed for the confirmSignUp page of the website.
  */
 class ConfirmSignUp extends BindingClass {
     constructor() {
         super();

         this.bindClassMethods(['mount', 'confirmSignUp', 'resendConfirmationCode', 'signIn'], this);

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

                console.log("Successfully confirmed signup!")

                const userId = localStorage.getItem('userId');
                const email = localStorage.getItem('email');
                const pass = localStorage.getItem('password');
                const firstName = localStorage.getItem('firstName');
                const lastName = localStorage.getItem('lastName');
                const gender = localStorage.getItem('gender');
                const birthMonth = localStorage.getItem('birthMonth');
                const birthDay = localStorage.getItem('birthDay');
                const birthYear = localStorage.getItem('birthYear');


                 const personalityType = "empty";
                 const aboutMe = "empty";
                 const hobbies = [];
                 hobbies.push("empty");

                 const connexions = [];
                 connexions.push("empty");

                 const city = "empty";
                 const state = "empty";
                 const country = "empty";

               const signInResponse = await this.signIn(email, pass);

               if (signInResponse.AuthenticationResult) {
                   // Authentication successful
                   console.log("New User Authentication successful");
                   console.log("Sign In Response: " + signInResponse.AuthenticationResult.AccessToken);
                   localStorage.setItem('token', signInResponse.AuthenticationResult.AccessToken);
                   //localStorage.setItem('refreshToken', signInResponse.AuthenticationResult.RefreshToken);

                   const createNewUserResponse = await this.client.createNewUser(userId, email, firstName, lastName, gender, birthMonth, birthDay, birthYear, city, state, country, personalityType, hobbies, aboutMe, connexions, signInResponse.AuthenticationResult.AccessToken);

                   if (createNewUserResponse) {
                       console.log("Successfully created new user!");
                       window.location.href = '/edit_profile2.html';
                   }

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

            if (response.Session) {
                console.log("session: " + response.Session);
                console.log("Successfully resent confirmation code!");
            }
        } catch (error) {
            console.log("error: " + error);
            throw error;
        }
     }


     // Function to initiate authentication for the new user
     async signIn(username, password) {
             const userPoolId = 'us-east-2_D6wRTbWHw';
             const clientId = 'ubklaouam3eupblmrptdd1bn0';
             const region = 'us-east-2';

             const input = {
               AuthFlow: "USER_PASSWORD_AUTH",
               AuthParameters: {
                 PASSWORD: password,
                 USERNAME: username
               },
               ClientId: clientId,
              };

             const client = new CognitoIdentityProviderClient({ region: region });

             const command = new InitiateAuthCommand(input);
             const response = await client.send(command);
             console.log(response);

             return response;
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