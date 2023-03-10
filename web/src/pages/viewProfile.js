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
        this.bindClassMethods(['clientLoaded', 'mount', 'addProfileInfoToPage'], this);

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

        this.dataStore.set('user', user);
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
        const user = this.dataStore.get('user');

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
