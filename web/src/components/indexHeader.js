import * as cognitoClient from 'aws-sdk/clients/cognitoidentityserviceprovider';
import { CognitoIdentityProviderClient, InitiateAuthCommand } from "@aws-sdk/client-cognito-identity-provider";
import { useState } from 'react';
import ConnexionClient from '../api/connexionClient';
import BindingClass from "../util/bindingClass";


/**
 * The header component for the website.
 */
export default class IndexHeader extends BindingClass {
    constructor() {
        super();

        const methodsToBind = ['addHeaderToPage', 'handleLogin', 'createSignInButton'];
        this.bindClassMethods(methodsToBind, this);

        this.client = new ConnexionClient();

        const config = { region:  'us-east-2' }
        const cognitoClientIdentityProvider = new cognitoClient(config);

        console.log("indexHeader constructor");

    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const signInButton = this.createSignInButton();
        const signUpLink = document.getElementById('sign-up-link');
        signUpLink.addEventListener('click', window.location.href = '/welcome.html')
    }

    async handleLogin() {
        const username = document.getElementById('email-input').value;
        const password = document.getElementById('pass-input').value;
        const clientId = "ubklaouam3eupblmrptdd1bn0";

        //const initiateAuth = ({ username, password, clientId }) => {
        const client = new CognitoIdentityProviderClient({region:  'us-east-2'});

        const command = new InitiateAuthCommand({
            AuthFlow: "USER_PASSWORD_AUTH" || "USER_SRP_AUTH" || "CUSTOM_AUTH" || "REFRESH_TOKEN_AUTH",
            AuthParameters: {
              USERNAME: username,
              PASSWORD: password,
            },
            ClientId: clientId,
          });

            const response = await client.send(command);
            console.log(response);

            if(response.AuthenticationResult.AccessToken != null) {
                window.location.href = '/dashboard.html';
            }

            return response;
    }

    createSignInButton() {
        const signInButton = document.getElementById('sign-in-button');
        signInButton.textContent = 'Sign In';
        signInButton.addEventListener('click', this.handleLogin);

        return signInButton;
    }
}

