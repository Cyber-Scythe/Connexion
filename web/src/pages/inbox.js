import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the inbox page of the website.
 */
export default class Inbox extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'populateInbox'], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        console.log("inbox constructor");
    }

    /**
     * Once the client is loaded, get the inbox metadata.
     */
    async clientLoaded() {
        console.log("inside clientLoaded()");

        const currUser = await this.client.getProfile((error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('currUser', currUser);


        const inbox = await this.client.getAllMessages((error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            });

        console.log("inbox: ", inbox);

        this.dataStore.set('inbox', inbox);

        this.populateInbox();
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new ConnexionClient();
        this.clientLoaded();
    }

   /*
    * Populate the inbox with message data
    */
    async populateInbox() {
        console.log("populateInbox");

        const currUser = this.dataStore.get('currUser');
        const recentMsgs = this.dataStore.get('inbox');

        console.log("currUser: ", currUser);
        console.log("Recent Messages: ", recentMsgs);

        var colDiv = document.getElementById('col-div');
        colDiv.className = 'col-12 col-lg-5 col-xl-3 border-right';

        if (recentMsgs.length == 0) {
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

        } else {

           for (var i = 0; i < recentMsgs.length; i++) {
                var parentDiv = document.createElement('div');
                parentDiv.className = 'list-group-item list-group-item-action border-0';
                parentDiv.id = 'parent-div' + i;

                console.log("sentBy: ", recentMsgs[i].sentBy);

                var sender = document.createElement('a');
                                sender.className = 'list-group-item list-group-item-action border-0';
                                sender.type = 'a';
                                sender.id = 'sender' + i;
                                sender.name = recentMsgs[i].sentBy;
                                sender.innerText = recentMsgs[i].sentBy;


                var div1 = document.createElement('div');
                div1.type = 'div';
                div1.className = 'badge bg-success float-right';
                div1.id = 'new-msg' + i;


                var div2 = document.createElement('div');
                div2.className = 'd-flex align-items-start';
                div2.type = 'div';
                div2.id = 'item-start-div';

                parentDiv.appendChild(div1);
                parentDiv.appendChild(div2);

                var senderPic = document.createElement('img');
                senderPic.className = 'rounded-circle mr-1';
                senderPic.id = 'sender-pic' + i;
                senderPic.name = 'sender-pic'
                senderPic.width = 40;
                senderPic.height = 40;
                senderPic.src = 'images/alien.png';


                div2.appendChild(senderPic);

                var div3 = document.createElement('div');
                div3.className = 'flex-grow-1 ml-3';

                console.log('received by: ', recentMsgs[i].receivedBy);

                if (recentMsgs[i].sentBy != currUser.email) {
                    div3.innerText = recentMsgs[i].sentBy;
                } else if (recentMsgs[i].receivedBy != currUser.email) {
                    div3.innerText = recentMsgs[i].receivedBy;
                }

                div2.appendChild(div3)

                var msgPreviewDiv = document.createElement('div');
                msgPreviewDiv.className = 'small';
                msgPreviewDiv.id = 'msg-preview' + i;

                var messageContent = recentMsgs[i].messageContent;
                let previewLength = 0;

                if (messageContent.length >= 2) {
                    previewLength = messageContent.length/2
                    previewLength = previewLength.toFixed(0);
                    msgPreviewDiv.innerText = messageContent.substring(0, previewLength);
                } else {
                    msgPreviewDiv.innerText = messageContent;
                }

                if (recentMsgs[i].sentBy != currUser.email) {
                    const otherUserEmail = recentMsgs[i].sentBy;
                    this.dataStore.set('otherUserEmail', otherUserEmail);
                } else {
                    const otherUserEmail = recentMsgs[i].sentBy;
                    this.dataStore.set('otherUserEmail', otherUserEmail);
                }

                let otherUser = this.dataStore.get('otherUserEmail');
                msgPreviewDiv.onclick = function (otherUser) {

                    location.href = '/view_message.html?otherUser=' + otherUser + '';
                };

                div3.appendChild(msgPreviewDiv);

                var br = document.createElement('br');
                sender.appendChild(br);

                var deleteCheckbox = document.createElement('input');
                                deleteCheckbox.className = 'container';
                                deleteCheckbox.type = 'checkbox';
                                deleteCheckbox.id = 'deleteCheckbox' + i;
                                deleteCheckbox.name = 'message';
                                deleteCheckbox.value = recentMsgs[i];

                parentDiv.appendChild(deleteCheckbox);
                colDiv.appendChild(parentDiv);

                var deleteButton = document.getElementById('delete-btn');

                deleteButton.addEventListener('click', async () => {

                    for (var i = 0; i < recentMsgs.length; i++) {
                        var deleteCheckbox = document.getElementById('deleteCheckbox' + i);

                        if(deleteCheckbox.checked) {
                            if (recentMsgs[i].sentBy != currUser.email) {
                               const otherUserEmail = recentMsgs[i].sentBy;

                               var dateTimeSent = recentMsgs[i].dateTimeSent

                               console.log('time sent: ', dateTimeSent);
                               console.log('otherUserEmail: ', otherUserEmail);

                               await this.client.deleteMessages(dateTimeSent, otherUserEmail);
                               const inbox = await this.client.getAllMessages((error) => {
                                           errorMessageDisplay.innerText = `Error: ${error.message}`;
                                           errorMessageDisplay.classList.remove('hidden');
                                           });

                               console.log("inbox: ", inbox);

                               this.dataStore.set('inbox', inbox);

                               this.populateInbox();
                            } else {
                               const otherUserEmail = recentMsgs[i].receivedBy;
                               const dateTimeSent = recentMsgs[i].dateTimeSent;
                               await this.client.deleteMessages(dateTimeSent, otherUserEmail);

                               const inbox = await this.client.getAllMessages((error) => {
                                           errorMessageDisplay.innerText = `Error: ${error.message}`;
                                           errorMessageDisplay.classList.remove('hidden');
                                           });

                               console.log("inbox: ", inbox);

                               this.dataStore.set('inbox', inbox);

                               this.populateInbox();
                            }
                        }
                    }
                });
           }
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
