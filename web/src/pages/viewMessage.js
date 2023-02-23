import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the chat page of the website.
 */
class ViewMessage extends BindingClass {

    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'getMessageData', 'populateChat'], this);

        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

        console.log("viewMessage constructor");
    }

   /**
    * Once the client is loaded, get the inbox metadata.
    */
    async clientLoaded() {
        console.log("inside clientLoaded()");

        const currUser = await this.client.getProfile();

        console.log('currUser: ', currUser);
        this.dataStore.set('currUser', currUser);
        this.getMessageData();
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
    * Get all of the messages between two users
    */
    async getMessageData() {

        const urlParams = new URLSearchParams(window.location.search);
        const otherUserFromURL = urlParams.get('otherUser');
        console.log("otherUserEmail: " + otherUserFromURL);
        this.dataStore.set('otherUserFromURL', otherUserFromURL);

        const msgWithUser = await this.client.getMessagesWithUser(otherUserFromURL);

        this.dataStore.set('msgWithUser', msgWithUser);
        console.log("messages with user: ", msgWithUser);

        await this.populateChat();
    }

    /*
     * Populate the chat page
     */
     async populateChat() {
            console.log("populateChat");

            const otherUserEmail = this.dataStore.get('otherUserFromURL');
            console.log('otherUserEmail: ', otherUserEmail);

            const msgWithUser = this.dataStore.get('msgWithUser');
            console.log('msgWithUser: ', msgWithUser);

            const chatMessages = document.getElementById('chat-messages');

            let currUser = this.dataStore.get('currUser');


            for (let i = 0; i < msgWithUser.length; i++) {
                console.log('sentBy: ', msgWithUser[i].sentBy);
                console.log('receivedBy: ', msgWithUser[i].receivedBy);

                if (msgWithUser[i].sentBy === currUser.email) {
                    let currUserDiv1 = document.createElement('div');
                    currUserDiv1.className = 'chat-message-right pb-4';
                    currUserDiv1.id = 'curr-user-msg' + i;

                    chatMessages.appendChild(currUserDiv1);

                    let div2 = document.createElement('div');
                    currUserDiv1.appendChild(div2);

                    let profilePic = document.createElement('img');
                    profilePic.className = 'rounded-circle mr-1';
                    profilePic.width = 40;
                    profilePic.height = 40;
                    profilePic.src = 'images/alien.png';

                    if (msgWithUser[i].sentBy === otherUserEmail) {
                        profilePic.alt = msgWithUser[i].sentBy;
                    } else {
                        profilePic.alt = msgWithUser[i].receivedBy;
                    }

                    let dateTimeDiv = document.createElement('div');
                    dateTimeDiv.className = 'text-muted small text-nowrap mt-2';
                    dateTimeDiv.id = 'dateTime';

                    dateTimeDiv.innerHTML = msgWithUser[i].dateTimeSent;

                    let userEmailDiv = document.createElement('div');
                    userEmailDiv.id = 'user-email';
                    userEmailDiv.innerHTML = msgWithUser[i].sentBy

                    div2.appendChild(profilePic);
                    div2.appendChild(userEmailDiv);
                    div2.appendChild(dateTimeDiv);

                    let contentDiv3 = document.createElement('div');
                    contentDiv3.className = 'flex-shrink-1 bg-light rounded py-2 px-3 mr-3';
                    contentDiv3.innerHTML = msgWithUser[i].messageContent;

                    currUserDiv1.appendChild(contentDiv3);

                    let youDiv = document.createElement('div');
                    youDiv.className = 'font-weight-bold mb-1';

                    contentDiv3.appendChild(youDiv);

                 } else {

                    let currUserDiv1 = document.createElement('div');
                    currUserDiv1.className = 'chat-message-left pb-4';
                    currUserDiv1.id = 'other-user-msg' + i;

                    chatMessages.appendChild(currUserDiv1);

                    let div2 = document.createElement('div');
                    div2.id = 'div2';
                    div2.className = 'chat-message-left pb-4';

                    currUserDiv1.appendChild(div2);

                    let profilePic = document.createElement('img');
                    profilePic.className = 'rounded-circle mr-1';
                    profilePic.width = 40;
                    profilePic.height = 40;
                    profilePic.src = 'images/alien.png';

                    if (msgWithUser[i].sentBy === otherUserEmail) {
                        profilePic.alt = msgWithUser[i].sentBy;
                    } else {
                        profilePic.alt = msgWithUser[i].receivedBy;
                    }

                    let dateTimeDiv = document.createElement('div');
                    dateTimeDiv.className = 'text-muted small text-nowrap mt-2';
                    dateTimeDiv.id = 'dateTime';

                    dateTimeDiv.innerHTML = msgWithUser[i].dateTimeSent;

                    let userEmailDiv = document.createElement('div');
                    userEmailDiv.id = 'user-email';
                    userEmailDiv.innerHTML = msgWithUser[i].sentBy


                    div2.appendChild(profilePic);
                    div2.appendChild(userEmailDiv);
                    div2.appendChild(dateTimeDiv);

                    let contentDiv3 = document.createElement('div');
                    contentDiv3.className = 'flex-shrink-1 bg-light rounded py-2 px-3 mr-3';
                    contentDiv3.innerHTML = msgWithUser[i].messageContent;

                    currUserDiv1.appendChild(contentDiv3);

                    let youDiv = document.createElement('div');
                    youDiv.className = 'font-weight-bold mb-1';

                    contentDiv3.appendChild(youDiv);
                 }
            }

            let messageBtn = document.getElementById('send-msg-btn');

            messageBtn.addEventListener('click', async () => {
                let recipientEmail = this.dataStore.get('otherUserFromURL');
                let messageContent = document.getElementById('message-content').value;
                let readStatus = false;

                console.log('recipient email: ', recipientEmail)
                console.log("messageContent: ", messageContent);

                await this.client.sendNewMessage(recipientEmail, messageContent, readStatus);
                location.reload();
            });
        }
}

/**
 * Main method to run when the page contents have loaded.
 */
 const main = async () => {
    const viewMessage = new ViewMessage();
    viewMessage.mount();
};

window.addEventListener('DOMContentLoaded', main);
