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

        this.bindClassMethods(['mount', 'populateHobbiesList', 'updateProfile', 'redirectToViewProfile'], this);

        // Create a new datastore
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        console.log("editUserProfile constructor");
    }


    /**
     * Once the client is loaded, get the user metadata.
     */
     async clientLoaded() {
        console.log("clientLoaded method");

        const hobbies = await this.client.getHobbiesList((error) => {
            console.log(`Error: ${error.message}`)
            });
        console.log('hobbies: ', hobbies);
        this.dataStore.set('hobbies', hobbies);

        this.populateHobbiesList();
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
     mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        document.getElementById('edit-btn').addEventListener('click', this.updateProfile);

        this.header.addHeaderToPage();

        this.client = new ConnexionClient();
        this.clientLoaded();
    }


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
    *
    *
    */
    async updateProfile(evt) {
         var username = document.getElementById('input-name').innerHTML.value;
         var age = document.getElementById('input-age').innerHTML.value;
         var personalityType = document.getElementById('input-personality-type').innerHTML.value;
         var city = document.getElementById('input-city').innerHTML.value;
         var state = document.getElementById('input-state').innerHTML.value;
         var connections = null;

         const hobbyList = this.dataStore.get('hobbies');

         if (hobbyList.length == 0) {
                    document.getElementById("hobbies-list").innerHTML = "Return list is empty."
         }

//         for (var i = 0; i < hobbyList.length; i++) {
//
//            var hobby = hobbyList[i];
//
//            if (hobby != null) {
//                document.getElementById("hobbies-list").innerHTML += "<br>"+ hobby;
//            }
//         }

        var userHobbies = [];

        for (var i = 0; i < hobbyList.length; i++) {
            var checkbox = document.getElementById('hobbyCheckbox' + i);

            if(checkbox.checked) {
                console.log("Checked box");
                userHobbies.push(checkbox.value);
            }
            console.log("Unchecked box");
         }

         var listOfHobbies = [];
         userHobbies.forEach(function(entry) {
                                         var singleObj = {};
                                         singleObj['type'] = 'String';
                                         singleObj['value'] = entry;
                                         listOfHobbies.push(singleObj);
                                     });

        console.log("userHobbies: ", userHobbies);
        console.log("listOfHobbies: ", listOfHobbies);

        const profile =  await this.client.updateUserProfile(username, age, city, state, personalityType, listOfHobbies, connections, (error) => {
//                    errorMessageDisplay.innerText = `Error: ${error.message}`;
//                    errorMessageDisplay.classList.remove('hidden');
                      console.log("Error: " + error.message);
                });

        this.dataStore.set('profile', profile);
    }

    /**
     * When the profile is updated in the datastore, redirect to the view playlist page.
     */
     async redirectToViewProfile() {
        const user = await this.client.getProfile((error) => {
                                  console.log(`Error: ${error.message}`);
                                  });
        console.log("user: ", user);
        this.dataStore.set('user', user);

        if (user != null) {
            window.location.href = `/view_profile.html?id=${user.id}`;
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