import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view connexions page of the website.
 */
class Connexions extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addConnexionsToPage'], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        console.log("connexions constructor");
    }

    /**
     * Once the client is loaded, get the profile metadata.
     */
    async clientLoaded() {
        console.log("Inside clientLoaded");

        const currUser = await this.client.getProfile((error => {
            console.log(`Error: ${error.message}`);
        }));

        console.log("curr user: ", currUser);
        this.dataStore.set('currUser', currUser);

        console.log("personalityTYpe: ", currUser.personalityType);

        const connexions = await this.client.getConnexions(currUser.personalityType);
        console.log("curr user connexions: ", connexions);

        this.dataStore.set('connexions', connexions);
        this.addConnexionsToPage();
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
     * When the connexions are updated in the datastore, update the connexions metadata on the page.
     */
    async addConnexionsToPage() {
        console.log("addConnexionsToPage");

        const connexions = this.dataStore.get('connexions');
        console.log('Inside addConnexionsToPage() -> connexions: ', connexions);

        const currUser = this.dataStore.get('currUser');

        if (connexions == null) {
            return;
        }

        let currUserHobbies = currUser.hobbies;


        for (let i = 0; i < connexions.length; i++) {
            let count = 0;
            for (let a = 0; a < currUserHobbies.length; a++) {
                if (connexions[i].hobbies.includes(currUserHobbies[a])) {
                    count++;
               }
            }
            console.log('num common hobbies: ', count);
        }

        let rowRemovable = document.getElementById('row-removable');

        for (let i = 0; i < connexions.length; i++) {
            if (connexions[i].id !== currUser.id) {

                let userId = connexions[i].id;
                console.log("userID: ", userId);

                let div = document.createElement('div');
                                div.className = 'col-xl-3 col-md-6 mb-4';
                                div.type = 'div';
                                div.id = 'col' + i;

                rowRemovable.appendChild(div);

                let card = document.createElement('card');
                           card.className = 'card';
                           card.type = 'card';
                           card.id = 'card' + i;

                div.appendChild(card);

                let div2 = document.createElement('div');
                           div2.className = 'card-body p-3';
                           div2.type = 'card';
                           div2.id = 'card' + i;

                card.appendChild(div2);

                let div3 = document.createElement('div')
                div3.className = 'd-flex align-items-center';
                div3.type = 'div';
                div3.id = 'div' + i;

                card.appendChild(div3);

                let spaceDiv = document.createElement('div');
                spaceDiv.type = 'div';
                spaceDiv.id = 'space-div' + i;

                let img = document.createElement('img');
                img.className = 'avatar avata-sm';
                img.type = 'img';
                img.id = 'profile-picture' + i;
                img.src = 'images/alien.png'

                card.appendChild(spaceDiv);
                spaceDiv.appendChild(img);

                let div4 = document.createElement('div');
                card.appendChild(div4);

                let span = document.createElement('span');
                span.className = 'h6 font-weight-bold mb-0';
                span.type = 'span';
                span.id = 'user-name' + i;
                span.value = connexions[i].name;
                span.innerHTML = connexions[i].name;
                span.addEventListener('click', async() => {
                    location.href = '/view_profile.html?user=' + userId + '';
                });

                div4.appendChild(span);

                let userLocation = connexions[i].city + ", " + connexions[i].state;
                let div5 = document.createElement('div');
                div5.type = 'div';
                div5.id = userLocation;
                div5.innerHTML = userLocation;

                card.appendChild(div5);

                let br = document.createElement('br');
                br.type = 'br';
                card.appendChild(br);

                let msgButton = document.createElement('button');
                msgButton.className = 'message-btn';
                msgButton.type = 'button';
                msgButton.id = 'message-btn-' + i;
                msgButton.innerText = "Message";

                console.log('user.email: ', connexions[i].email);
                let email = connexions[i].email;
                msgButton.onclick = function (email) {
                         let encodedEmail = encodeURIComponent(connexions[i].email);
                         location.href = '/view_message.html?otherUser=' + encodedEmail + '';
                };

                card.appendChild(msgButton);
            }
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const connexions = new Connexions();
    connexions.mount();
};

window.addEventListener('DOMContentLoaded', main);
