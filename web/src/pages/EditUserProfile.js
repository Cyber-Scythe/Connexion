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

         const email = document.getElementById('user-email').value;
         const firstName = document.getElementById('user-first-name').value;
         const lastName = document.getElementById('user-last-name').value;
         const birthdate = document.getElementById('user-birthdate').value
         const location = document.getElementById('user-location').value;
         const personalityType = document.getElementById('user-personality-type').value;
         const hobbies = document.getElementById('user-hobbies').value;

         let hobbies;
         if (hobbies.length < 1) {
             tags = null;
         } else {
             tags = tagsText.split(/\s*,\s*/);
         }

         const profile = await this.client.createUserProfile(email, (error) => {
             createButton.innerText = origButtonText;
             errorMessageDisplay.innerText = `Error: ${error.message}`;
             errorMessageDisplay.classList.remove('hidden');
         });

         this.dataStore.set('profile', profile);
     }