import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the inbox page of the website.
 */
class Inbox extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'populateInbox', 'sendNewMessage'], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.sendNewMessage);
        console.log("inbox constructor");
    }

    /**
     * Once the client is loaded, get the inbox metadata.
     */
    async clientLoaded() {
        console.log("inside clientLoaded()");

        const inbox = await this.client.getAllMessages((error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            });

        // set the dataStore
        this.dataStore.set('inbox', inbox);

        this.populateInbox();
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
    mount() {
        document.getElementById('save-msg-btn').addEventListener('click', this.sendNewMessage);

        this.header.addHeaderToPage();
        this.client = new ConnexionClient();
        this.clientLoaded();
    }

    async populateInbox() {
        const messages = this.dataStore.get('inbox');

        console.log("Messages: ", messages);

        var colDiv = document.getElementById('col-div');
        colDiv.className = 'col-12 col-lg-5 col-xl-3 border-right';

        if (messages.length == 0) {
            var emptyInboxDiv = document.createElement('div');
            emptyInboxDiv.className = 'empty-inbox-card';
            emptyInboxDiv.id = 'empty-inbox-card'

            var emptyInboxContent = document.createElement('div');
            emptyInboxContent.className = 'empty-inbox-card__content';

            emptyInboxDiv.appendChild(emptyInboxContent);

            var textDiv = document.createElement('div');
            textDiv.className = 'row justify-content-center';
            textDiv.innerText = 'Inbox is empty';

            colDiv.appendChild(emptyInboxDiv);

            emptyInboxContent.appendChild(textDiv);

            return;
        }

        for (var i = 0; i < messages.length; i++) {

            var sender = document.createElement('a');
                            sender.className = 'list-group-item list-group-item-action border-0';
                            sender.type = 'a';
                            sender.id = 'sender' + i;
                            sender.name = 'sender';
                            //sender.href = '';

            var div1 = document.createElement('div');
            div.className = 'badge bg-success float-right';
            div.id = 'new-msg' + i;

            var div2 = document.createElement('div');
            div.className = 'd-flex align-items-start';
            div.id = 'item-start-div';

            sender.appendChild(div1);
            sender.appendChild(div2);

            var senderPic = document.createElement('img');
            senderPic.className = 'rounded-circle mr-1';
            senderPic.id = 'sender-pic' + i;
            senderPic.name = 'sender-pic'
            senderPic.width = 40;
            senderPic.height = 40;
            senderPic.src = 'images/alien.png'


            div2.appendChild(senderPic);

            var div3 = document.createElement('div');
            div.className = 'flex-grow-1 ml-3';

            if ()
            div.innerText = messages[i].senderEmail;

            div2.appendChild(div3)

            var msgPreviewDiv = document.createElement('div');
            msgPreviewDiv.className = 'small';
            msgPreviewDiv.id = 'msg-preview' + i;
            //msgPreviewDiv.href = 'assets/view_message.html

            var messageContent = messages[i].messageContent;
            let previewLength = 0;

            if (messageContent.length >= 2) {
                previewLength = messageContent.length/2
                previewLength = previewLength.toFixed(0);
                messagePreviewDiv.innerText = messageContent.substring(0, previewLength);
            } else {
                msgPreviewDiv.innerText = messageContent;
            }

            div3.appendChild(msgPreviewDiv);

            var br = document.createElement('br');
            sender.appendChild(br);
        }
    }

    async sendNewMessage() {
        var messageContent = document.getElementById('message-content').innerHTML;

    }

    /**
     * When the playlist is updated in the datastore, redirect to the view playlist page.
     */
     redirectToViewMessage(evt) {
        const playlist = this.dataStore.get('playlist');

        if (playlist != null) {
            window.location.href = `/playlist.html?id=${playlist.id}`;
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
