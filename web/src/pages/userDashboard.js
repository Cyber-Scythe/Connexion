import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view profile page of the website.
 */
class UserDashboard extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'populateDashboard'], this);

        // Create a new datastore
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        console.log("userDashboard constructor");
    }


    /**
     * Once the client is loaded, get the user metadata.
     */
     async clientLoaded() {

        const user = await this.client.getProfile((error) => {
                          console.log(`Error: ${error.message}`);
                          });
        console.log("user: ", user);
         this.dataStore.set('user', user);

        const hobbies = await this.client.getHobbiesList((error) => {
            console.log(`Error: ${error.message}`)
            });
        console.log('hobbies: ', hobbies);
        this.dataStore.set('hobbies', hobbies);

        this.populateDashboard();
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
     mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.

        this.header.addHeaderToPage();

        this.client = new ConnexionClient();
        this.clientLoaded();
    }

    /**
    *
    *
    *
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
        const location = city + ", " + state;

        document.getElementById('user-location').innerHTML = location;
        document.getElementById('hobbies-list').innerHTML = user.hobbies;

        const editProfileButton = document.getElementById('edit-profile-btn');
        editProfileButton.addEventListener('click', async() => {
            window.location.href = '/edit_profile.html';
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