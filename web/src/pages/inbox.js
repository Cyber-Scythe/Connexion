import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class Inbox extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'getMessages'], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        console.log("inbox constructor");
    }

    /**
     * Once the client is loaded, get the inbox metadata.
     */
    async clientLoaded() {
        // Api call to get all inbox messages
        const inbox = await this.client.getMessages((error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            });

        // set the dataStore
        this.dataStore.set('inbox', inbox);
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
    mount() {
//        document.getElementById('drp-dwn-1').addEventListener('click', this.getConnexions);
//        document.getElementById('drp-dwn-2').addEventListener('click', this.editProfile);

        this.header.addHeaderToPage();
        this.client = new ConnexionClient();
        this.clientLoaded();
    }

    async getInbox() {
        const messages = this.dataStore.get('messages');

        if (messages == null) {
            document.getElementById("message-list").innerHTML = "Inbox is empty."
            return;
        }

        for (var i = 0; i < messages.length; i++) {
            var messageListItem = document.createElement('li');
                            messageListItem.className = 'list-group-item border-0 d-flex align-items-center';
                            //message.type = 'checkbox';
                            messageListItem.id = 'message' + i;
                            messageListItem.name = 'message';
                            //messageListItem.value = message;

            var div = document.createElement('div');
            div.className = 'avatar me-3';
            div.id = 'sender-avatar' + i;

            messageListItem.appendChild(div);

            var senderPic = document.createElement('img');
            senderPic.className = 'border-radius-lg shadow'
            senderPic.id = 'sender-pic' + i;
            senderPic.name = 'sender-pic'
            //senderPic.value = [S3 bucket of user's picture

            div.appendChild(senderPic);

            var msgDiv = document.createElement('div');
            msgDiv.className = 'd-flex align-items-start flex-column justify-content-center';
            msgDiv.id = 'sender-info' + i;

            messageListItem.appendChild(msgDiv);

            var sender = document.createElement('h6');
            sender.class = 'mb-0 text-sm';
            sender.id = 'sender-name' + i;
            //sender.value = messages[i].senderName;

            var p = document.createElement('p');
            p.className = 'mb-0 text-xs';
            p.id = 'msg-preview' + i;
            // *** A SUB-STRING OF THE MESSAGE CONTENT SHOULD BE STORE IN VALUE HERE
            //p.value = messages[i].messageContent;

            msgDiv.appendChild(sender);
            msgDiv.appendChild(p);

            var a = document.createElement('a');
            a.className = 'mb-0 text-xs float-right';
            a.id = 'time-sent' + i;
            // *** CHECK IF TIME SENT IS < 12AM, IF SO USE DATE HERE ***
            //a.value = messages[i].timeSent;

            messageListItem.appendChild(a);
        }


    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const inbox = new Inbox();
    inbox.mount();
};

window.addEventListener('DOMContentLoaded', main);
