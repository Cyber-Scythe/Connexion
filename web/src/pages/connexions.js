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
        this.bindClassMethods(['clientLoaded', 'mount', 'addConnexionsToPage', 'getConnexionProfile'], this);

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
    * Get profile metadata for connexion
    */
    async getConnexionProfile(userId) {
        return await this.client.getConnexionProfile(userId, (error) => {
                            console.log(`Error: ${error.message}`);
                    });
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

            const user = await this.getConnexionProfile(userId);
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

            card.appendChild(div3);

            var spaceDiv = document.createElement('div');
            spaceDiv.type = 'div';
            spaceDiv.id = 'space-div' + i;

            var img = document.createElement('img');
            img.className = 'avatar avata-sm';
            img.type = 'img';
            img.id = 'profile-picture' + i;
            img.src = 'images/alien.png'

            card.appendChild(spaceDiv);
            spaceDiv.appendChild(img);

            var div4 = document.createElement('div');
            card.appendChild(div4);

            var span = document.createElement('span');
            span.className = 'h6 font-weight-bold mb-0';
            span.type = 'span';
            span.id = 'user-name' + i;
            span.value = user.name;
            span.innerHTML = user.name;

            div4.appendChild(span);

            var userLocation = user.city + ", " + user.state;
            var div5 = document.createElement('div');
            div5.type = 'div';
            div5.id = userLocation;
            div5.innerHTML = userLocation;

            card.appendChild(div5);

            var br = document.createElement('br');
            br.type = 'br';
            card.appendChild(br);

            var msgButton = document.createElement('button');
            msgButton.className = 'message-btn';
            msgButton.type = 'button';
            msgButton.id = 'message-btn-' + i;
            msgButton.innerHTML = "Message";

            card.appendChild(msgButton);
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
