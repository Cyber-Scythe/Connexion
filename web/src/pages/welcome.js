import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { CognitoIdentityProviderClient, SignUpCommand } from "@aws-sdk/client-cognito-identity-provider";

/**
 * Logic needed for the welcome page of the website.
 */
class Welcome extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'calculateAge', 'signUserUp'], this);

        // Create a new datastore
        this.dataStore = new DataStore();
        //this.header = new Header(this.dataStore);

        console.log("welcomeSignUp constructor");
    }


    /**
     * Once the client is loaded, get the user metadata.
     * Get all user data and call ClassMethods here
     */
     async clientLoaded() {
        console.log("clientLoaded method");

        const signUpButton = document.getElementById("signup-button");
        signUpButton.addEventListener('click', this.signUserUp);

        const alreadyHaveAccntLink = document.getElementById('alreadyHaveAccntLink--0');
        alreadyHaveAccntLink.addEventListener('click', () => {
            window.location.href = '/index.html';
        });
     }


    /**
     * Add the header to the page and load the ConnexionClient.
     */
     mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.

        //this.header.addHeaderToPage();

        this.client = new ConnexionClient();
        this.clientLoaded();

    }


    /**
    *  Define Class Methods Below.
    */

    /**
     * Sign new user up
     */
     async signUserUp() {
        console.log("signUserUp method");

        const firstName = document.getElementById("firstNameInput--0").value;
        console.log(firstName);
        const lastName = document.getElementById("lastNameInput--0").value;
        const name =  firstName + " " + lastName;

        const birthMonth = document.getElementById("birthMonth--0").value;
        const birthDay = document.getElementById("birthDay--0").value;
        const birthYear = document.getElementById("birthYear--0").value;

        if (firstName === '' || lastName === '' || birthMonth === '' || birthDay === '' || birthYear === '') {
            const invalidBox = document.getElementById('invalid-box');
            invalidBox.textContent = 'Please fill out all fields';
            return invalidBox;

        }

        const age = this.calculateAge(birthYear, birthMonth, birthDay);

        if (age < 13) {
            window.location.href = '/ageError.html'
        }

        var gender = '';

        if (document.getElementById("femaleCheckbox--0").checked) {
            gender = 'female';
        } else if (document.getElementById("maleCheckbox--0").checked) {
            gender = 'male';
        } else if (document.getElementById("customCheckbox--0").checked) {
            gender = 'custom';
        }

        if (gender == '') {
            const invalidGenderBox = document.getElementById('invalid-box');
            invalidGenderBox.textContent = 'Please select a gender.';
            return invalidGenderBox;
        }

        const email = document.getElementById("emailInput--0").value;
        console.log("email: " + email);

        const password = document.getElementById("passwordInput--0").value;

        const userPoolId = 'us-east-2_D6wRTbWHw';
        const clientId = 'ubklaouam3eupblmrptdd1bn0';
        const region = 'us-east-2';

        const cognitoIdentityProviderClient = new CognitoIdentityProviderClient({region});
        const command = new SignUpCommand({
                            "ClientId": clientId,
                            "Password": password,
                            "UserAttributes": [
                               {
                                  "Name": 'name',
                                  "Value": name
                               },
                               {
                                  "Name": 'email',
                                  "Value": email
                               }
                            ],
                            "Username": email
         });

        try {
              const response = await cognitoIdentityProviderClient.send(command);
              console.log("response: " + response);

              if (response.UserSub) {
                    const userId = response.UserSub;
                    console.log("userId: " + userId);

                    localStorage.setItem('userId', userId);
                    localStorage.setItem('name', name);
                    localStorage.setItem('firstName', firstName);
                    localStorage.setItem('lastName', lastName);
                    localStorage.setItem('birthMonth', birthMonth);
                    localStorage.setItem('birthDay', birthDay);
                    localStorage.setItem('birthYear', birthYear);
                    localStorage.setItem('age', age);
                    localStorage.setItem('gender', gender);
                    localStorage.setItem('email', email);
                    localStorage.setItem('password', password);

                    window.location.href = '/confirm_signup.html';
              }

        } catch (error) {
              if(error.code == "UsernameExistsException") {
                    const invalidBox = document.getElementById('invalid-box');
                    invalidBox.textContent = 'An account is already associated with this email';
                    return invalidBox;
              }
              throw error;
        }
    }

   /**
    * Calculate age from birthdate
    */
    calculateAge(birthYear, birthMonth, birthDay) {
        const today = new Date();
        const birthDate = new Date(birthYear, birthMonth, birthDay);
        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDiff = today.getMonth() - birthDate.getMonth();

        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }

        return age;
    }
}

    /**
     * Main method to run when the page contents have loaded.
     */
const main = async () => {
    const welcome = new Welcome();
    welcome.mount();
};

window.addEventListener('DOMContentLoaded', main);
