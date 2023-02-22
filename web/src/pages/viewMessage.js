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

        var currUser = await this.client.getProfile();

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

            var otherUserEmail = this.dataStore.get('otherUserFromURL');
            console.log('otherUserEmail: ', otherUserEmail);

            var msgWithUser = this.dataStore.get('msgWithUser');
            console.log('msgWithUser: ', msgWithUser);

            var chatMessages = document.getElementById('chat-messages');

            var currUser = this.dataStore.get('currUser');


            for (var i = 0; i < msgWithUser.length; i++) {
                console.log('sentBy: ', msgWithUser[i].sentBy);
                console.log('receivedBy: ', msgWithUser[i].receivedBy);

                if (msgWithUser[i].sentBy === currUser.email) {
                    var currUserDiv1 = document.createElement('div');
                    currUserDiv1.className = 'chat-message-right pb-4';
                    currUserDiv1.id = 'curr-user-msg' + i;

                    chatMessages.appendChild(currUserDiv1);

                    var div2 = document.createElement('div');
                    currUserDiv1.appendChild(div2);

                    var profilePic = document.createElement('img');
                    profilePic.className = 'rounded-circle mr-1';
                    profilePic.width = 40;
                    profilePic.height = 40;
                    profilePic.src = 'images/alien.png';

                    if (msgWithUser[i].sentBy === otherUserEmail) {
                        profilePic.alt = msgWithUser[i].sentBy;
                    } else {
                        profilePic.alt = msgWithUser[i].receivedBy;
                    }

                    var dateTimeDiv = document.createElement('div');
                    dateTimeDiv.className = 'text-muted small text-nowrap mt-2';
                    dateTimeDiv.id = 'dateTime';

                    dateTimeDiv.innerHTML = msgWithUser[i].dateTimeSent;

                    var userEmailDiv = document.createElement('div');
                    userEmailDiv.id = 'user-email';
                    userEmailDiv.innerHTML = msgWithUser[i].sentBy

                    div2.appendChild(profilePic);
                    div2.appendChild(userEmailDiv);
                    div2.appendChild(dateTimeDiv);

                    var contentDiv3 = document.createElement('div');
                    contentDiv3.className = 'flex-shrink-1 bg-light rounded py-2 px-3 mr-3';
                    contentDiv3.innerHTML = msgWithUser[i].messageContent;

                    currUserDiv1.appendChild(contentDiv3);

                    var youDiv = document.createElement('div');
                    youDiv.className = 'font-weight-bold mb-1';

                    contentDiv3.appendChild(youDiv);

                 } else {

                    var currUserDiv1 = document.createElement('div');
                    currUserDiv1.className = 'chat-message-left pb-4';
                    currUserDiv1.id = 'other-user-msg' + i;

                    chatMessages.appendChild(currUserDiv1);

                    var div2 = document.createElement('div');
                    div2.id = 'div2';
                    div2.className = 'chat-message-left pb-4';

                    currUserDiv1.appendChild(div2);

                    var profilePic = document.createElement('img');
                    profilePic.className = 'rounded-circle mr-1';
                    profilePic.width = 40;
                    profilePic.height = 40;
                    profilePic.src = 'images/alien.png';

                    if (msgWithUser[i].sentBy === otherUserEmail) {
                        profilePic.alt = msgWithUser[i].sentBy;
                    } else {
                        profilePic.alt = msgWithUser[i].receivedBy;
                    }

                    var dateTimeDiv = document.createElement('div');
                    dateTimeDiv.className = 'text-muted small text-nowrap mt-2';
                    dateTimeDiv.id = 'dateTime';

                    dateTimeDiv.innerHTML = msgWithUser[i].dateTimeSent;

                    var userEmailDiv = document.createElement('div');
                    userEmailDiv.id = 'user-email';
                    userEmailDiv.innerHTML = msgWithUser[i].sentBy


                    div2.appendChild(profilePic);
                    div2.appendChild(userEmailDiv);
                    div2.appendChild(dateTimeDiv);

                    var contentDiv3 = document.createElement('div');
                    contentDiv3.className = 'flex-shrink-1 bg-light rounded py-2 px-3 mr-3';
                    contentDiv3.innerHTML = msgWithUser[i].messageContent;
                    contentDiv3.id = 'message-content';

                    currUserDiv1.appendChild(contentDiv3);

                    var youDiv = document.createElement('div');
                    youDiv.className = 'font-weight-bold mb-1';

                    contentDiv3.appendChild(youDiv);
                 }
            }

            var messageBtn = document.getElementById('send-msg-btn');

            messageBtn.addEventListener('click', async () => {
                var recipientEmail = this.dataStore.get('otherUserFromURL');
                var messageContent = document.getElementById('message-content').value;
                var readStatus = false;

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
