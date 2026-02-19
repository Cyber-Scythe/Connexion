import * as session from 'express-session';
import ConnexionClient from '../api/connexionClient';
import IndexHeader from '../components/indexHeader';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from "../api/authenticator";
import { CognitoUserPool, CognitoUser, AuthenticationDetails } from "amazon-cognito-identity-js";
import { AuthFlowType, CognitoIdentityProviderClient, InitiateAuthCommand } from "@aws-sdk/client-cognito-identity-provider";
import { useState } from 'react';

export default class Landing extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'signIn', 'setUpSignInButton', 'setUpSignUpLink', 'confirmMatchingPass', 'sendChallengeResponse'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore();
        this.indexHeader = new IndexHeader(this.dataStore);

        console.log("index constructor");
    }

    // Function to initiate authentication
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

    async setUpSignInButton() {
        const signInButton = document.getElementById('sign-in-button');
        signInButton.textContent = "Sign In";

        signInButton.addEventListener('click', async() => {
            const email = document.getElementById('email-input').value;
            const password = document.getElementById('pass-input').value;

            const response = await this.signIn(email, password);

        if (response.AuthenticationResult) {
            // Authentication successful
            console.log("Authentication successful");
            console.log("response: " + response.AuthenticationResult.AccessToken);
            localStorage.setItem('token', response.AuthenticationResult.AccessToken);

            window.location.href = '/dashboard.html';

        } else if (response.ChallengeName == "NEW_PASSWORD_REQUIRED") {
            const challengeBox = document.getElementById('form-popup');
            challengeBox.style.display = 'block';

            const session = response.Session;

            const submitButton = document.getElementById('submit-button');
            submitButton.addEventListener('click', async() => {
                   const email = document.getElementById('email-challenge-input').value;
                   const newPass = document.getElementById('old-pass-input').value;
                   const newPass2 = document.getElementById('new-pass-input').value;

                   const matchingPass = this.confirmMatchingPass(newPass, newPass2);

                   if(!matchingPass) {
                        const invalidBox = document.getElementById('invalid-box2');
                        invalidBox.textContent = 'Passwords do not match';
                        return invalidBox;
                    }

                   const result = this.sendChallengeResponse(email, newPass2, session);
                   console.log("result: " + result);
                   localStorage.setItem('session', session);
                   window.location.href = '/dashboard.html';
                });
    // NEED TO ADD IF STATEMENTS FOR ALL CHALLENGE CASES!
         } else {
                    // Authentication failed
                    console.log("Authentication failed");
                    const invalidBox = document.getElementById('invalid-box2');
                    invalidBox.textContent = 'Invalid username or password';
                    return invalidBox;
                }
        });
    }


    setUpSignUpLink() {
        const signupLink = document.getElementById('signup-link');
        signupLink.href = '/welcome.html';
    }

    confirmMatchingPass(newPass, newPass2) {
        if (newPass === newPass2) {
            return true;
        } else {
            return false;
        }
    }

    async sendChallengeResponse(username, newPass2, session) {
        const AWS = require('aws-sdk');
        const userPoolId = 'us-east-2_D6wRTbWHw';
        const clientId = 'ubklaouam3eupblmrptdd1bn0';
        const region = 'us-east-2';

        const client = new CognitoIdentityProviderClient({ region: region });

        const cognitoidentityserviceprovider = new AWS.CognitoIdentityServiceProvider({
            region: region
        });

        //const respondToAuthChallenge = async (session, username, newPass2) => {
            const params = {
                ChallengeName: 'NEW_PASSWORD_REQUIRED',
                ClientId: 'ubklaouam3eupblmrptdd1bn0',
                ChallengeResponses: {
                    USERNAME: username,
                    NEW_PASSWORD: newPass2
                },
                Session: session
            };


            try {
                const result = await cognitoidentityserviceprovider.respondToAuthChallenge(params).promise();
                console.log('Password successfully updated and user signed in:', result);
                return result;
            } catch (error) {
                if(error.code == "InvalidPasswordException") {
                    const invalidBox = document.getElementById('invalid-box');
                    invalidBox.textContent = 'Password does not meet requirements';
                    return invalidBox;
                } else {

                    console.error('Error responding to auth challenge:', error);
                    throw error;
                }
            }
        }


    /**
     * Add the header to the page and load the ConnexionClient.
     */
    mount() {
        this.indexHeader.addHeaderToPage();
        this.client = new ConnexionClient();

        this.setUpSignInButton();
        this.setUpSignUpLink;
    }
}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const landing = new Landing();
        landing.mount();
};

window.addEventListener('DOMContentLoaded', main);
