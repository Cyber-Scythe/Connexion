import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view profile page of the website.
 */
class ViewProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'getFromS3Bucket', 'addProfileInfoToPage'], this);

        this.dataStore = new DataStore();

        this.header = new Header(this.dataStore);

        console.log("viewProfile constructor");
    }

    /**
     * Once the client is loaded, get the profile metadata.
     */
    async clientLoaded() {
        console.log("Inside clientLoaded");

        const urlParams = new URLSearchParams(window.location.search);
        const userFromURL = urlParams.get('user');
        console.log("otherUser: " + userFromURL);

        let user;
        if (userFromURL !== null) {
            user = await this.client.getConnexionProfile(userFromURL);
        } else {
            user = await this.client.getProfile();
        }

        this.dataStore.set('userModel', user);
        this.addProfileInfoToPage();
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new ConnexionClient();
        this.clientLoaded();
    }

    /**
     * When the profile is updated in the datastore, update the profile metadata on the page.
     */
    addProfileInfoToPage() {
        console.log('inside addProfileInfoToPage()');

        let user = this.dataStore.get('userModel');
        console.log('user: ', user);

        if (user == null) {
            return;
        }

        document.getElementById('user-name').innerHTML = user.name;
        document.getElementById('user-age').innerHTML = ", " + user.age ;
        document.getElementById('user-personality-type').innerHTML = user.personalityType;

        const location = user.city + ", " + user.state;
        document.getElementById('user-location').innerHTML = location;
        document.getElementById('hobbies-list').innerHTML = user.hobbies;

       // Code to get profile picture from S3 bucket and set it as value
       // Get S3 download URL
        const downloadUrl = this.axios.getPresignedDownloadUrl(user.id);
        const key = user.id + 'profile-photo';

        const profilePic = await this.getFromS3Bucket(downloadUrl, key);
        document.getElementById('profile-picture').value = profilePic;
    }

    async getFromS3Bucket(downloadUrl, key) {
        const axios = require('axios');

        axios.get(downloadUrl, key)
            .then(response => {
              console.log(response.data.url);
              console.log(response.data.explanation);
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
    const viewProfile = new ViewProfile();
    viewProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);
