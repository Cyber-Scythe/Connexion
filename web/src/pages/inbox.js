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

        const currUser = await this.client.getProfile((error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('currUser', currUser);


        const inbox = await this.client.getAllMessages((error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            });

        this.dataStore.set('inbox', inbox);

        this.populateInbox();
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
    mount() {
        //document.getElementById('send-msg-btn').addEventListener('click', this.sendNewMessage);

        this.header.addHeaderToPage();
        this.client = new ConnexionClient();
        this.clientLoaded();
    }

   /*
    * Populate the inbox with message data
    */
    async populateInbox() {
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

            return;
        }

       for (var i = 0; i < recentMsgs.length; i++) {

            var sender = document.createElement('a');
                            sender.className = 'list-group-item list-group-item-action border-0';
                            sender.type = 'a';
                            sender.id = 'sender' + i;
                            sender.name = recentMsgs[i].sentBy;
                            //sender.href = '';

            var div1 = document.createElement('div');
            div1.className = 'badge bg-success float-right';
            div1.id = 'new-msg' + i;

            var div2 = document.createElement('div');
            div2.className = 'd-flex align-items-start';
            div2.id = 'item-start-div';

            sender.appendChild(div1);
            sender.appendChild(div2);

            var senderPic = document.createElement('img');
            senderPic.className = 'rounded-circle mr-1';
            senderPic.id = 'sender-pic' + i;
            senderPic.name = 'sender-pic'
            senderPic.width = 40;
            senderPic.height = 40;
            senderPic.src = 'images/alien.png';
            //senderPic.href = '';


            div2.appendChild(senderPic);

            var div3 = document.createElement('div');
            div3.className = 'flex-grow-1 ml-3';

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
                msgPreviewDiv.onClick = this.populateChat(recentMsgs[i].sentBy);
            } else {
                msgPreviewDiv.onClick = this.populateChat(recentMsgs[i].receivedBy);
            }

            div3.appendChild(msgPreviewDiv);

            var br = document.createElement('br');
            sender.appendChild(br);

            var deleteCheckbox = document.createElement('input');
                            deleteCheckbox.className = 'container';
                            deleteCheckbox.type = 'checkbox';
                            deleteCheckbox.id = 'deleteCheckbox' + i;
                            deleteCheckbox.name = 'message';
                            deleteCheckbox.value = message;

            sender.appendChild(deleteCheckbox);
        }
    }


   /*
    * Populate the chat page
    */
    async populateChat(otherUserEmail) {

        const messages = await this.client.getMessagesFromUser(otherUserEmail, (error) => {
                    errorMessageDisplay.innerText = `Error: ${error.message}`;
                    errorMessageDisplay.classList.remove('hidden');
                    });

        this.dataStore.set('messages', messages);

        var chatMessages = document.getElementById('chat-messages').value;

        for (var i = 0; i < messages.length; i++) {
            if (messages[i].sender == currUser.email) {
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

                if (messages[i].sentBy != currUser.email) {
                    profilePic.alt = messages[i].sentBy;
                } else {
                    profilePic.alt = messages[i].receivedBy;
                }

                var dateTimeDiv = document.createElement('div');
                dateTimeDiv.className = 'text-muted small text-nowrap mt-2';
                dateTimeDiv.id = 'dateTime';

                var dateTime = Date.parse(msg.dateTimeSent);
                dateTimeDiv.innerHTML = dateTime;

                div2.appendChild(profile-pic);
                div2.appendChild(dateTimeDiv);

                var contentDiv3 = document.createElement('div');
                contentDiv3.className = 'flex-shrink-1 bg-light rounded py-2 px-3 mr-3';
                contentDiv3.innerHTML = messages[i].messageContent;

                currUserDiv1.appendChild(div3);

                var youDiv = document.createElement('div');
                youDiv.className = 'font-weight-bold mb-1';

                contentDiv3.appendChild(youDiv);
             } else {

                var currUserDiv1 = document.createElement('div');
                currUserDiv1.className = 'chat-message-left pb-4';
                currUserDiv1.id = 'other-user-msg' + i;

                chatMessages.appendChild(currUserDiv1);

                var div2 = document.createElement('div');
                currUserDiv1.appendChild(div2);

                var profilePic = document.createElement('img');
                profilePic.className = 'rounded-circle mr-1';
                profilePic.width = 40;
                profilePic.height = 40;
                profilePic.src = 'images/alien.png';

                if (messages[i].sentBy != currUser.email) {
                    profilePic.alt = messages[i].sentBy;
                } else {
                    profilePic.alt = messages[i].receivedBy;
                }

                var dateTimeDiv = document.createElement('div');
                dateTimeDiv.className = 'text-muted small text-nowrap mt-2';
                dateTimeDiv.id = 'dateTime';

                var dateTime = Date.parse(msg.dateTimeSent);
                dateTimeDiv.innerHTML = dateTime;

                div2.appendChild(profile-pic);
                div2.appendChild(dateTimeDiv);

                var contentDiv3 = document.createElement('div');
                contentDiv3.className = 'flex-shrink-1 bg-light rounded py-2 px-3 mr-3';
                contentDiv3.innerHTML = messages[i].messageContent;

                currUserDiv1.appendChild(div3);

                var youDiv = document.createElement('div');
                youDiv.className = 'font-weight-bold mb-1';

                contentDiv3.appendChild(youDiv);
             }
        }

        var messageBtn = document.getElementById('send-msg-btn');
        messageBtn.href = this.sendNewMessage(otherUserEmail);
    }

   /*
    * Send new message data to API endpoint
    */
    async sendNewMessage(otherUserEmail) {
        var recipientEmail = otherUserEmail;

        var currentDate = new Date();
        var dateTime =  currentDate.getDate() + "/"
                        + (currentDate.getMonth()+1)  + "/"
                        + currentDate.getFullYear() + " @ "
                        + currentDate.getHours() + ":"
                        + currentDate.getMinutes() + ":"
                        + currentDate.getSeconds();

        var dateTimeSent = dateTime.toString();
        var messageContent = document.getElementById('message-content').innerHTML;
        var readStatus = Boolean(0);

        const newMessage = await this.client.sendNewMessage(recipientEmail,
                                                            dateTimeSent,
                                                            messageContent,
                                                            readStatus,
                                                            (error) => {
                    errorMessageDisplay.innerText = `Error: ${error.message}`;
                    errorMessageDisplay.classList.remove('hidden');
                    });


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
