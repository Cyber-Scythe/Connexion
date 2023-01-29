 var params = {
  Body: <Binary String>,
  Bucket: "examplebucket",
  Key: "HappyFace.jpg"
 };
 s3.putObject(params, function(err, data) {
   if (err) console.log(err, err.stack); // an error occurred
   else     console.log(data);           // successful response
   /*
   data = {
    ETag: "\"6805f2cfc46c0f04559748bb039d69ae\"",
    VersionId: "tpf3zF08nBplQK1XLOefGskR7mGDwcDk"
   }
   */
 });

 import ConnexionClient from '../api/connexionClient';
 import Header from '../components/header';
 import BindingClass from '../util/bindingClass';
 import DataStore from '../util/DataStore';

 /**
  * Logic needed for the create playlist page of the website.
  */
 class CreatePlaylist extends BindingClass {
     constructor() {
         super();
         this.bindClassMethods(['mount', 'submit', 'redirectToViewPlaylist'], this);
         this.dataStore = new DataStore();
         this.dataStore.addChangeListener(this.redirectToViewPlaylist);
         this.header = new Header(this.dataStore);
     }

     /**
      * Add the header to the page and load the MusicPlaylistClient.
      */
     mount() {
         document.getElementById('create').addEventListener('click', this.submit);

         this.header.addHeaderToPage();

         this.client = new MusicPlaylistClient();
     }

/**
 * Logic needed for the create profile page of the website.
 */
 class CreateEditUserProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'save', 'edit' 'redirectToViewProfile', 'redirectToEditProfile'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewProfile);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the ConnexionClient
     */
     mount() {
        document.getElementById('save').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new ConnexionClient();
     }
 }

     /**
      * Method to run when the edit profile save button is pressed. Call the ConnexionService to create the
      * profile.
      */
     async submit(evt) {
         evt.preventDefault();

         const errorMessageDisplay = document.getElementById('error-message');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         const saveButton = document.getElementById('save-button');
         const origButtonText = createButton.innerText;
         createButton.innerText = 'Saving...';

         const profileName = document.getElementById('profile-name').value;
         const profileEmail = document.getElementById('user-email').value;
         const firstName = document.getElementById('user-first-name').value;
         const lastName = document.getElementById('user-last-name').value;
         const location = document.getElementById('user-location').value;
         const personalityType = document.getElementById('user-personality-type').value;
         const hobbies = document.getElementById('user-hobbies').value;

         let hobbies;
         if (hobbies.length < 1) {
             tags = null;
         } else {
             tags = tagsText.split(/\s*,\s*/);
         }

         const profile = await this.client.createUserProfile(profileName, profileEmail, firstName, lastName, location,
          personalityType, hobbies, (error) => {
             createButton.innerText = origButtonText;
             errorMessageDisplay.innerText = `Error: ${error.message}`;
             errorMessageDisplay.classList.remove('hidden');
         });

         this.dataStore.set('profile', profile);
     }

     /**
      * When the profile is updated in the datastore, redirect to the view profile page.
      */
     redirectToViewPlaylist() {
         const playlist = this.dataStore.get('profile');
         if (profile != null) {
             window.location.href = `/profile.html?id=${user.id}`;
         }
     }
 }

 /**
  * Main method to run when the page contents have loaded.
  */
 const main = async () => {
     const createEditUserProfile = new CreateEditUserProfile();
     createEditUserProfile.mount();
 };

 window.addEventListener('DOMContentLoaded', main);
