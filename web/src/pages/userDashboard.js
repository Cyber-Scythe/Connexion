import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { AuthFlowType, CognitoIdentityProviderClient, GetUserCommand} from "@aws-sdk/client-cognito-identity-provider";

/**
 * Logic needed for the view profile page of the website.
 */
class UserDashboard extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'populateDashboard', 'getFromS3Bucket'], this);

        // Create a new datastore
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        console.log("userDashboard constructor");
    }


    /**
     * Once the client is loaded, get the user metadata.
     */
     async clientLoaded() {
        console.log("clientLoaded method");

        const token = localStorage.getItem('token');

        /*
        console.log("token: " + token);

        const input =
            {
                AccessToken: token
            };

        const command = new GetUserCommand(input);
        const user = await this.cognitoIdentityProviderClient.send(command);
        */
        const user = await this.client.getProfile(token);
        console.log("user: ", user);
        this.dataStore.set('user', user);


        const hobbies = await this.client.getHobbiesList(token);
        console.log('hobbies: ', hobbies);
        this.dataStore.set('hobbies', hobbies);

        this.populateDashboard();

        const downloadUrl = this.client.getPresignedDownloadUrl(user.name, token);
        console.log('download url: ', downloadUrl);

        const key = user.name + 'profile-photo';
        console.log('key: ', key);

        const profilePic = await this.getFromS3Bucket(downloadUrl, key);

        if (profilePic) {
            document.getElementById('profile-picture').src = profilePic;
            document.getElementById('profile-picture').style.borderRadius = '50%';
        } else {
            document.getElementById('profile-picture').src = 'images/alien.png'
            document.getElementById('profile-picture').style.borderRadius = '50%';
        }
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
     mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.

        this.header.addHeaderToPage();

        this.client = new ConnexionClient();
        this.cognitoIdentityProviderClient = new CognitoIdentityProviderClient({ region: 'us-east-2' });

        this.clientLoaded();
    }

    /**
    * Populate dashboard with user data
    */
    populateDashboard() {
       console.log("populateDashboard");

       const user = this.dataStore.get('user');

       if (user == null) {
            return;
       }

        console.log("name: " + user.name);

        document.getElementById('user-name').innerHTML = user.name;
        document.getElementById('user-age').innerHTML = ", " + user.age;

        document.getElementById('user-personality-type').innerHTML = user.personalityType;

        const city = user.city;
        const state = user.state;
        const country = user.country;
        const location = city + ", " + state + " " + country;

        document.getElementById('user-location').innerHTML = location;
        document.getElementById('hobbies-list').innerHTML = user.hobbies;

        const editProfileButton = document.getElementById('edit-profile-btn');
        editProfileButton.addEventListener('click', async() => {
            window.location.href = '/edit_profile2.html'; // CHANGED TO EDIT_PROFILE2.HTML
        });
    }

    /*
    * Get profile picture from S3 bucket
    */
    async getFromS3Bucket(downloadUrl) {
        const axios = require('axios');

        axios.get(downloadUrl)
            .then(response => {
              const bodyContents = response.Body;
              console.log(response.data.url);
              console.log(response.data.explanation);
              console.log('data :' + response.request.data);

              return response.request.data;
            })
            .catch(error => {
               console.log(error);
            });
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
 const main = async () => {
    const userDashboard = new UserDashboard();
    userDashboard.mount();
};

window.addEventListener('DOMContentLoaded', main);