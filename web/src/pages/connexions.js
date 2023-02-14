import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
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
        console.log("personalityTYpe: ", currUser.personalityType);

        const connexions = await this.client.getConnexions(currUser.personalityType, (error) => {
            console.log(`Error: ${error.message}`);
        });
        console.log("connexions: ", connexions[0]);

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
     * When the profile is updated in the datastore, update the profile metadata on the page.
     */
    async addConnexionsToPage() {
        console.log("addConnexionsToPage");

        const connexions = this.dataStore.get('connexions');

        if (connexions == null) {
            return;
        }

        var rowRemovable = document.getElementById('row-removable');

        for (var i = 0; i < connexions.length; i++) {
            console.log("connexion: ", connexions[i]);

            var userId = connexions[i];
            const user = await this.client.getConnexionProfile(userId, (error) => {
                    console.log(`Error: ${error.message}`);
            });

            console.log("user: ", user);

            var div = document.createElement('div');
                            div.className = 'col-xl-3 col-md-6 mb-4';
                            div.type = 'div';
                            div.id = 'col' + i;

            rowRemovable.appendChild(div);

            var card = document.createElement('card');
                       card.className = 'card';
                       card.type = 'card';
                       card.id = 'card' + i;

            div.appendChild(card);

            var div2 = document.createElement('div');
                       div2.className = 'card-body p-3';
                       div2.type = 'card';
                       div2.id = 'card' + i;

            card.appendChild(div2);

            var div3 = document.createElement('div')
            div3.className = 'd-flex align-items-center';
            div3.type = 'div';
            div3.id = 'div' + i;

            div2.appendChild(div3);

            var spaceDiv = document.getElementById('space-div');

            var profileImage = document.createElement('img');
            profileImage.className = 'avatar avata-sm';
            profileImage.type = 'img';
            profileImage.id = 'profile-picture' + i;
            profileImage.src = 'images/alien.png'

            spaceDiv.appendChild(profileImage);

            var div4 = document.createElement('div');
            spaceDiv.appendChild(div4);

            var span = document.createElement('span');
            span.className = 'h6 font-weight-bold mb-0';
            span.type = 'span';
            span.id = 'user-name' + i;
            span.value = user.name;

            spaceDiv.appendChild(span);

            var userLocation = user.city + ", " + user.state;
            var div5 = document.createElement('div');
            div5.type = 'div';
            div5.id = userLocation;

            spaceDiv.appendChild(div5);

            var msgButton = document.createElement('button');
            msgButton.className = 'fa fa-edit-profile';
            msgButton.type = 'button';
            msgButton.id = 'message-btn-' + i;

            spaceDiv.appendChild(msgButton);
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
