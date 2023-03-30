import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import Authenticator from '../api/authenticator';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import AWS from 'aws-sdk';
import https from "https";
import { HttpRequest } from "@aws-sdk/protocol-http";
import * as fs from 'fs';

/**
 * Logic needed for the view profile page of the website.
 */
class EditUserProfile extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount',
                               'populateHobbiesList',
                               'prepopulateProfile',
                               'previewImage',
                               'putInS3Bucket'], this);

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
        const fileUpload = document.getElementById('file-upload').addEventListener("change", this.previewImage);

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

           for (let i = 0; i < jsonHobbyList.length; i++) {

                let hobby = jsonHobbyList[i];

                if (hobby != null) {
                    document.getElementById("hobbies-list").innerHTML += hobby + "<br>";
                }

                let checkbox = document.createElement('input');
                checkbox.className = 'container';
                checkbox.type = 'checkbox';
                checkbox.id = 'hobbyCheckbox' + i;
                checkbox.name = 'hobby';
                checkbox.value = hobby;

                let label = document.createElement('label')
                label.htmlFor = 'selected-hobby';
                label.appendChild(document.createTextNode('My Hobby'));

                let br = document.createElement('br');

                let container = document.getElementById('hobbies-list');
                container.appendChild(checkbox);
                container.appendChild(br);
            }
    }

   /*
    * Pre-populate user's profile with data already stored in the database
    */
    async prepopulateProfile() {
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
        if (user.hobbies !== null) {
            for (let a = 0; a < user.hobbies.length; a++) {
                for (let i = 0; i < hobbyList.length; i++) {
                    if (user.hobbies[a] === hobbyList[i]) {
                        let checkbox = document.getElementById('hobbyCheckbox' + i);
                        checkbox.checked = true;
                    }
                }
            }
        }

        let connexions = null;

        let saveButton = document.getElementById('save-btn');
        saveButton.addEventListener('click', async (evt) => {

             evt.preventDefault();

             const curUser = this.dataStore.get('currUser');
             const userId = curUser.id;

             const username = document.getElementById('input-name').value;
             const age = document.getElementById('input-age').value;
             const personalityType = document.getElementById('input-personality-type').value;
             const city = document.getElementById('input-city').value;
             const state = document.getElementById('input-state').value;
             const connexions = null;

             const hobbyList = this.dataStore.get('hobbies');

             if (hobbyList.length === 0) {
                 document.getElementById("hobbies-list").innerHTML = "Return list is empty."
             }

            let userHobbies = [];

            for (let i = 0; i < hobbyList.length; i++) {
                let checkbox = document.getElementById('hobbyCheckbox' + i);

                if(checkbox.checked) {
                    console.log("Checked box");
                    userHobbies.push(checkbox.value);
                    console.log("Type: ", checkbox.value.type);
                }
                console.log("Unchecked box");
             }

            console.log("userHobbies: ", userHobbies);
            const currUser = this.dataStore.get('currUser');
            const photoData = this.dataStore.get('photoData');
            const presignedUrl = this.dataStore.get('presignedUrl');

            // *** PUT PHOTO IN S3 BUCKET ***
            await this.putInS3Bucket(presignedUrl, photoData);

            await this.client.updateUserProfile(currUser.id, username, age, city, state, personalityType, userHobbies, connexions);

            const user = this.dataStore.get('currUser');
            console.log('user.id: ', user.id);

           // location.href = '/view_profile.html?user=' + user.id + '';
        });
    }

     /**
     * Create an arrow function that will be called when an image is selected.
     */
     async previewImage(evt) {
        let imageFiles = event.target.files;
        let imageFilesLength = imageFiles.length;

        let imageSrc;

        if (imageFilesLength > 0) {

            imageSrc = URL.createObjectURL(imageFiles[0]);

            // *** GET PRE-SIGNED URL ***
            const user = this.dataStore.get('currUser');
            const presignedUrl = await this.client.getPresignedUrl(user.id);
            console.log('Pre-signed URL: ', presignedUrl);
            this.dataStore.set('presignedUrl', presignedUrl);

            const imagePreviewElement = document.querySelector("#preview-selected-image");
            imagePreviewElement.src = imageSrc;
            imagePreviewElement.style.display = "block";

            const imageName = imageFiles[0].name;
            const fileType = imageFiles[0].type;

            console.log('IMAGE FILE: ', imageFiles[0]);
            console.log("FILE TYPE: ", fileType);
            console.log("IMAGE SRC: ", imageSrc);

            this.dataStore.set('photoData', imageFiles[0]);
        }
    }

    async putInS3Bucket(url, data) {
          const axios = require('axios');

          axios.put(url, { data: data })
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
export const main = async () => {
    const editUserProfile = new EditUserProfile();
    editUserProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);