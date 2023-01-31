 import ConnexionClient from '../api/connexionClient';
 import Header from '../components/header';
 import BindingClass from '../util/bindingClass';
 import DataStore from '../util/DataStore';

/**
 * Logic needed for the create profile page of the website.
 */
 class CreateUserProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount','redirectToViewProfile'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewProfile);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the ConnexionClient
     */
     mount() {
        //document.getElementById('save').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new ConnexionClient();
     }

     /**
      * Method to run when the edit profile save button is pressed. Call the ConnexionService to create the
      * profile.
      */
     async submit(evt) {
         evt.preventDefault();

         const profile = await this.client.createUserProfile(userId, name, email, (error) => {
             createButton.innerText = origButtonText;
             errorMessageDisplay.innerText = `Error: ${error.message}`;
             errorMessageDisplay.classList.remove('hidden');

             const name = document.getElementById('user-name').value;
             const email = document.getElementById('user-email').value;
         });

         this.dataStore.set('profile', profile);
    }

     /**
      * When the profile is updated in the datastore, redirect to the view profile page.
      */
     redirectToViewProfile() {
         const profile = this.dataStore.get('profile');
         if (profile != null) {
             window.location.href = `/profile.html?id=${userId}`;
         }
     }
 }

 /**
  * Main method to run when the page contents have loaded.
  */
 const main = async () => {
     const createUserProfile = new CreateUserProfile();
     createUserProfile.mount();
 };

 window.addEventListener('DOMContentLoaded', main);
