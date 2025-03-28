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
        this.bindClassMethods(['clientLoaded', 'mount', 'getFromS3Bucket', 'addProfileInfoToPage', 'encode'], this);

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
    async addProfileInfoToPage() {
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
        const downloadUrl = await this.client.getPresignedDownloadUrl(user.id);
        this.dataStore.set('downloadUrl', downloadUrl);

        console.log('user.id: ', user.id);
        console.log('download url: ', downloadUrl);

        const key = user.id + 'profile-photo';
        console.log('key: ', key);

        this.dataStore.set('key', key);

        // THIS IS WHERE WE WILL TRY TO GET THE PHOTO FROM OUR S3 BUCKET
        const profilePic = await this.getFromS3Bucket(downloadUrl, key);
       // const profileImage = await this.encode(profilePic);

        document.getElementById('profile-picture').src = downloadUrl;
    }

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


    encode(data) {
        var pic = [];
        pic.push(data);
        return pic.join("");
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
