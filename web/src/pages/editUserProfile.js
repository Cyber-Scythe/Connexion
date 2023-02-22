import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";


/**
 * Logic needed for the view profile page of the website.
 */
class EditUserProfile extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'populateHobbiesList', 'prepopulateProfile', 'updateProfile', 'redirectToViewProfile'], this);

        // Create a new datastore
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.redirectToViewProfile);

        console.log("editUserProfile constructor");
    }


    /**
     * Once the client is loaded, get the user metadata.
     */
     async clientLoaded() {
        console.log("clientLoaded method");

        const currUser = await this.client.getProfile();
        this.dataStore.set('currUser', currUser);

        const hobbies = await this.client.getHobbiesList();
        this.dataStore.set('hobbies', hobbies);

        this.populateHobbiesList();
        this.prepopulateProfile();
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
     mount() {
        // Wire up the button's 'click' event to the updateProfile  method.
        document.getElementById('save-btn').addEventListener('click', this.updateProfile);

        this.header.addHeaderToPage();

        this.client = new ConnexionClient();
        this.clientLoaded();
    }

  /**
   * Populate the list of hobbies
   **/
   populateHobbiesList() {
        const jsonHobbyList = this.dataStore.get('hobbies');

        console.log("jsonHobbyList: " + jsonHobbyList);

           if (jsonHobbyList.length == 0) {
               document.getElementById("hobbies-list").innerHTML = "Return list is empty."
           }

           for (var i = 0; i < jsonHobbyList.length; i++) {

                var hobby = jsonHobbyList[i];

                if (hobby != null) {
                    document.getElementById("hobbies-list").innerHTML += hobby + "<br>";
                }

                var checkbox = document.createElement('input');
                checkbox.className = 'container';
                checkbox.type = 'checkbox';
                checkbox.id = 'hobbyCheckbox' + i;
                checkbox.name = 'hobby';
                checkbox.value = hobby;

                var label = document.createElement('label')
                label.htmlFor = 'selected-hobby';
                label.appendChild(document.createTextNode('My Hobby'));

                var br = document.createElement('br');

                var container = document.getElementById('hobbies-list');
                container.appendChild(checkbox);
                container.appendChild(br);
            }
    }

   /*
    * Pre-populate user's profile with data already stored in the database
    */
    prepopulateProfile() {
        const user = this.dataStore.get('currUser');

        let username = document.getElementById('input-name');
        username.value = user.name;

        if (user.age !== null) {
            let age = document.getElementById('input-age');
            age.value = user.age;
        }

        if (user.personalityType !== null) {
            let personalityType = document.getElementById('input-personality-type');
            personalityType.value = user.personalityType;
        }

        if (user.city !== null) {
            let city = document.getElementById('input-city');
            city.value = user.city;
        }

        if (user.state !== null) {
            let state = document.getElementById('input-state');
            state.value = user.state;
        }

        let hobbyList = this.dataStore.get('hobbies');
        for (let a = 0; a < user.hobbies.length; a++) {
            for (let i = 0; i < hobbyList.length; i++) {
                if (user.hobbies[a] === hobbyList[i]) {
                    let checkbox = document.getElementById('hobbyCheckbox' + i);
                    checkbox.checked = true;
                }
            }
        }

        let connexions = null;
    }

   /*
    * Updates a user's profile information when the save button is clicked
    */
    async updateProfile(evt) {
         var username = document.getElementById('input-name').value;
         var age = document.getElementById('input-age').value;
         var personalityType = document.getElementById('input-personality-type').value;
         var city = document.getElementById('input-city').value;
         var state = document.getElementById('input-state').value;
         var connexions = null;

         const hobbyList = this.dataStore.get('hobbies');

         if (hobbyList.length == 0) {
                    document.getElementById("hobbies-list").innerHTML = "Return list is empty."
         }

        var userHobbies = [];

        for (var i = 0; i < hobbyList.length; i++) {
            var checkbox = document.getElementById('hobbyCheckbox' + i);

            if(checkbox.checked) {
                console.log("Checked box");
                userHobbies.push(checkbox.value);
                console.log("Type: ", checkbox.value.type);
            }
            console.log("Unchecked box");
         }

        console.log("userHobbies: ", userHobbies);

        const profile =  await this.client.updateUserProfile(username, age, city, state, personalityType, userHobbies, connexions);
        console.log("Profile: ", profile);
        this.dataStore.set('profile', profile);

        this.redirectToViewProfile();
    }

    /**
     * When the profile is updated in the datastore, redirect to the view playlist page.
     */
      redirectToViewProfile() {
        const profile = this.dataStore.get('profile');

        if (profile != null) {
            window.location.href = `/view_profile.html?id=${profile.id}`;
        }
      }
}

/**
 * Main method to run when the page contents have loaded.
 */
 const main = async () => {
    const editUserProfile = new EditUserProfile();
    editUserProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);