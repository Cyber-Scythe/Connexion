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

         this.populateDashboard();
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
     mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        document.getElementById('edit-profile-btn').addEventListener('click', this.editProfile);

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

        document.getElementById('user-name').innerHTML = user.name;
        document.getElementById('user-age').innerHTML = ", " + user.age;

        document.getElementById('user-personality-type').innerHTML = user.personalityType;

        const city = user.city;
        const state = user.state;
        const location = city + ", " + state;

        document.getElementById('user-location').innerHTML = location;
        document.getElementById('hobbies-boxes').innerHTML = user.hobbies;
    }

   /*
    *
    *
    */
    async editUserProfile() {
        const user = this.dataStore.get('userData');
                if (user == null) {
                    return;
                }


         var username = document.getElementById('user-name').innerHTML.value;
         var age = document.getElementById('age').innerHTML.value;
         var personalityType = document.getElementById('user-personality-type').innerHTML.value;
         var location = document.getElementById('user-location').innerHTML.value;
         var cityStop = location.indexOf(", ");
         var city = location.substring(0, cityStop);
         var end = location.length();
         var State = location.substring(cityStop, end);
         var hobbies = document.getElementById('hobbies').innerHTML.value;


         const updatedProfile = await this.client.updateUserProfile(userData.id, (error) => {
                    errorMessageDisplay.innerText = `Error: ${error.message}`;
                    errorMessageDisplay.classList.remove('hidden');
                });

                this.dataStore.set('userData', updatedProfile);
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