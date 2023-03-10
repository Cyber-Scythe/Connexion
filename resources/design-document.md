# Design Document

## Connexion Design

## 1. Problem Statement

There are many ways today in which we can connect with new people, but something is missing from the equation. 
People have a deep desire for genuine connection. Connexion is a service that matches users that are most compatible.
Customers have the ability to send an invitation to connect with the desired user(s) and, if the invitation is
accepted, can exchange messages.

---

## 2. Top Questions to Resolve in Review

_List the most important questions you have about your design, or things that you are still debating internally that 
 you might like help working through._

1. Should users be filtered by their set location or by their current location?
2. How should the issues of a user not wanting to use their location be dealt with?
3. How should the issue of blank "about me" sections be dealt with?
 

---

## 3. Use Cases

<b>U1.</b> As a Connexion customer, I want to be able to create a user profile. The benefit of this is being able to get matched
            with and connect with other users

<b>U2.</b> As a Connexion customer, I want to be able to update my profile information. The benefit of this is that any changes
            in the user's information can be reflected in their profile.

<b>U3.</b> As a Connexion customer, I want to be able to view a list of the most compatible users within a certain 
           distance from me and that I have not previously connected. The benefit of this is that the user will be able to
            connect with new users who are local. 

<b>U4.</b> As a Connexion customer, I want to be able to view a list of all the most compatible users that I have not
           previously connected with. The benefit of this is that the user will be able to see all possible connections.

<b>U5.</b> As a Connexion customer, I want to be able to send connection invitations to other users. The benefit of this is
           being able to initiate new connections with other users.

<b>U6.</b> As a Connexion customer, I want to be able to receive connection invitations from other users. The benefit of this
           is that others can initiate new connections with the user.

<b>U7.</b> As a Connexion customer, I want to be able to send and receive private messages. The benefit of this is being able
           to communicate with users

<b>U8.</b> As a Connexion customer, I want to be able to delete private messages. The benefit of this is that the inbox will
          be more manageable.

<b>U9.</b> As a Connexion customer, I want to be able to block specific users from contacting me. The benefit of this is that
           it prevents unwanted contact.
 

---

## 4. Project Scope

_Clarify which parts of the problem you intend to solve. It helps reviewers know what questions to ask to make sure 
 you are solving for what you say and stops discussions from getting sidetracked by aspects you do not intend to handle
 in your design._

### 4.1. In Scope

* creating and updating a user profile
* returning a list of most compatible users that have not been previously connected with
* sending and receiving private messages
* deleting private messages


### 4.2. Out of Scope

* blocking a user
* Sending/receiving connection invitations
* invitations to connect expiring after 24 hours
* returning a list of most compatible users that have not been previously connected with and that have not been 
  declined, or themselves declined, an invitation to connect
* returning a list of most compatible users based on the user's current location

---

# 5. Proposed Architecture Overview

This initial iteration will provide the minimum lovable product (MLP) including creating and updating a user profile,
returning a list of most compatible users within a given distance, and the ability to send, receive, and delete
private messages.

We will use API Gateway and Lambda to create eight endpoints ('LogIn', 'CreateProfile', 'UpdateProfile', 'GetSimilarUsers', 
'CreateNewMessage', 'GetInboxMessages', and 'DeleteMessages') that will handle the creation and updating of user profiles,
the retrieval of most compatible users, the creation of new messages, the retrieval of received messages, and the 
deletion of messages to satisfy our requirements.

We will store user's and their profile information in a table in DynamoDB. Messages will also be stored in DynamoDB. 
For retrieval of users that have not previously been connected with, we will store connections of each user in the 
"Connections" table.

Connexion will also provide a web interface for users to manage their profile. A main page providing a list view of 
all the most compatible users will let them send private messages to the user(s) of their choosing. An inbox page will
provide a list view of all received messages. 

---

# 6. API

## 6.1. Public Models

```
// UserModel

String id;
String email;
String name;
String birthdate;
String location;
String profilePicture;
String personalityType;
List<String> hobbies;
List<String> connections;
```

```
// MessageModel

String id;
String sentBy;
String receivedBy;
String dateTimeSent;
String messageContents;
String readStatus;
```


## 6.2. Log In Endpoint

* Accepts `GET` request to `/users/:id`
* Accepts an email address and password and returns the user's profile.
* For security concerns, we will validate that the provided email is of the correct format and that the password
  is correct.
* If email address/password combination is incorrect, will throw `InvalidEmailPasswordCombinationException`

![Client chooses to login with credentials or sign-up. Login page sends request to LoginActivity.
LoginActivity checks credentials and if they're correct takes user to website homepage. If user 
chooses 'sign-up' a new user ID will be created for the user and saved to DynamoDB](images/LogInSD.png)

## 6.3. Create Profile Endpoint

* Accepts `POST` requests to `/users`
* Accepts data to create a new user profile with an id, a provided email, name, birthdate, location, about me, and an 
  optional personality type. Returns the new user, including a unique user ID assigned by the Connexion Service.
* For security concerns, we will validate the provided user information does not contain any invalid characters: 
  `" ' \`
* If information fields contain any of the invalid characters, will throw an `InvalidAttributeValueException`.
* If email address is already in use, will throw `ExistingAccountException`

![...](images/CreateProfileSD.png)


## 6.4 Update Profile Endpoint

* Accepts `PUT` requests to `/users/:id`
* Accepts data to update a user profile, including an email address, name, birthdate, location, list of hobbies, and 
  optional personality type. Returns the updated user profile.
* If the user ID is not found, will throw a `UserNotFoundException`
* For security concerns, we will validate the provided user information does not contain any invalid characters:
  `" ' \`
* If information fields contain any of the invalid characters, will throw an `InvalidAttributeValueException`.

![...](images/CreateProfileSD.png)



## 6.5 Get Similar Users Endpoint

* Accepts `GET` requests to `/users/:id/similar-users`
* Accepts a user ID and returns a list of most similar users.
    * If the optional `personalityType` parameter is provided, this API will 
      return the list of similar users filtered by most compatible personality
      types, based on the value of `personalityType`
        * Analysts:
          * INTJ
          * INTP
          * ENTJ
          * ENTP
        * Diplomats:
          * INFJ
          * INFP
          * ENFJ
          * ENFP
        * Sentinels:
          * ISTJ
          * ISFJ
          * ESTJ
          * ESFJ
        * Explorers:
          * ISTP
          * ISFP
          * ESTP
          * ESFP
* If the given user ID is not found, will throw a `UserNotFoundException`

![...](images/GetSimilarUsersSD.png)

## 6.6. Create New Message Endpoint

* Accepts `POST` requests to `/users/:id/inbox/sent`
* Accepts data to create a new private message, including a message ID, date and time sent, recipient, and message 
  content.
* If body of message is blank, will throw `EmptyMessageException`

![...](images/CreateNewMessageSD.png)

## 6.7. Get Inbox Messages Endpoint

* Accepts `GET` request to `/users/:id/inbox`
* Accepts a user ID and returns a list of messages
* If a give user ID is not found, will throw `UserNotFoundException`

![...](images/GetMessagesSD.png)


## 6.8 Delete Messages Endpoint

* Accepts `PUT` request to `users/:id/inbox`
* Accepts list of message IDs to delete, returns updated list of messages
* If a message ID is not found, will throw `MessageNotFoundException`

![...](images/DeleteMessagesSD.png)
---

# 7. Tables

### 7.1. `users`

```
id // string
name // string
email // string, [partition key]
birthdate // string
location // string
profilePicture // string
personalityType // string
hobbies // stringSet
connections // stringSet
```

### 7.2. `inbox`

```
messageId // string
dateTimeSent // string
sentBy // string 
receivedBy // string [partition key]
messageContent // string
readStatus // boolean
```

### 7.3. `hobbies` 
```
hobbyName // string
```

### 7.4. `GET_EMAILS_FROM_USER_INDEX`

```
messageId // string
dateTimeSent // string
sentBy // string [sort key]
receivedBy // string [partition key]
messageContent // string
readStatus // boolean
```

---

# 8. Class Diagram
@startuml
'https://plantuml.com/class-diagram'

LoginLambda -- LoginActivity
LoginActivity -- LoginDao
CreateProfileLambda -- CreateProfileActivity
CreateProfileActivity -- UserDao
UpdateProfileLambda -- UpdateProfileActivity
UpdateProfileActivity -- UserDao
GetSimilarUsersLambda -- GetSimilarUsersActivity
GetSimilarUsersActivity -- UserDao
CreateNewMessageLambda -- CreateNewMessageActivity
CreateNewMessageActivity -- MessageDao
GetInboxMessagesLambda -- GetInboxMessagesActivity
GetInboxMessagesActivity -- MessageDao
GetMessagesFromUserLambda -- GetMessagesFromUserActivity
GetMessagesFromUserActivity -- MessageDao
DeleteMessagesLambda -- DeleteMessagesActivity
DeleteMessagesActivity -- MessageDao



class LoginDao {
    DynamoDbMapper

    logUserIn(String email, String password): UserModel
}

class MessageDao {
    DynamoDbMapper

    +getAllMessages(String email): List<MessageModel>
    createNewMessage(MessageModel)
    deleteMessages(List<MessageModel>)
}

class UserDao {
    DynamoDbMapper

    UserModel createProfile(UserModel)
    +getUser(String email): UserModel
    List<UserModel> getSimilarUsers(UserModel)
}

class MessageModel {
    -String dateTimeSent
    -String sentBy
    -String receivedBy
    -String messageContent
    -boolean readStatus

    +getDateTimeSent(): String
    +setDateTimeSent(): void
    +getSender(): String
    +setSender(): void
    +getRecipient(): String
    +setRecipient(): void
    +getMessageContent(): String
    +setMessageContent(): void
    +getReadStatus(): boolean
    +setReadStatus(): void
}
class UserModel {
    -String email
    -String name
    -String birthdate
    -String location
    -String profilePicture
    -String personalityType
    -List<String> hobbies

    +setEmail(): void
    +setName(): void
    +setBirthdate(): void
    +setLocation(): void
    +setProfilePicture(): void
    +setPersonalityType(): void
    +setHobbies(): void

    +getEmail(): String
    +getName(): String
    +getBirthdate(): String
    +getLocation(): String
    +getProfilePicture(): String
    +getPersonalityType(): String
    +getHobbies(): List<String>
}
class LoginActivity {
    LoginDao

    handleRequest(LoginRequest request): LoginActivityResult
}
class LoginActivityRequest {
    - email: String
    - password: String

    -LoginActivityRequest(String email, String password)
    +getEmail(): String
    +getPassword(): String
    +toString(): String
    +builder(): Builder
}

class LoginActivityResult {
    -UserModel userProfile

    -LoginActivityResult(UserModel userProfile)
    +getUserProfile(): UserModel
    +toString(): String
    +builder(): Builder
}

class LoginLambda {
    String email[primary key]
    String password

    LambdaResponse handleRequest(AuthenticatedLambdaRequest<LoginActivityRequest>, Context)
}

class CreateProfileActivity {
    UserDao

    handleRequest(CreateProfileActivityRequest request): CreateProfileActivityResult
}

class CreateProfileActivityRequest {
    -String email
    -String name
    -String birthdate
    -String location
    -String profilePicture
    -String personalityType
    -List<String> hobbies

    +getEmail(): String
    +getName(): String
    +getBirthdate(): String
    +getLocation(): String
    +getProfilePicture(): String
    +getPersonalityType(): String
    +getHobbies(): List<String>
    +toString(): String
    +builder(): Builder

    CreateProfileActivityRequest(String email, String name, String birthdate, String location, String profilePicture,
                            String personalityType, List<String> hobbies): CreateProfileActivityResult
}

class CreateProfileActivityResult {
    -UserModel userProfile

    -CreateProfileActivityResult(UserModel userProfile)
    +getUserProfile(): UserModel
    +toString(): String
    +builder(): Builder
}

class CreateProfileLambda {
    UserModel userProfile

    LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateProfileActivityRequest>, Context)
}

class UpdateProfileActivity {
    -UserDao userDao

    +UpdateProfileActivity()
    +handleRequest(UpdateProfileActivityRequest request): UpdateProfileActivityResult
}

class UpdateProfileActivityRequest {
   -String email
   -String name
   -String birthdate
   -String location
   -String profilePicture
   -String personalityType
   -List<String> hobbies

    UpdateProfileActivityRequest(String email, String name, String birthdate, String location, String profilePicture,
                            String personalityType, List<String> hobbies): UpdateProfileActivityResult
   +getEmail(): String
   +getName(): String
   +getBirthdate(): String
   +getLocation(): String
   +getProfilePicture(): String
   +getPersonalityType(): String
   +getHobbies(): List<String>
   +toString(): String
   +builder(): Builder
}

class UpdateProfileActivityResult {
    -UserModel userProfile

    -UpdateProfileActivityResult(UserModel userProfile)

    +getUserProfile(): UserModel
    +toString(): String
    +builder(): Builder
}

class UpdateProfileLambda {
    UserModel userProfile
    LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateProfileActivityRequest>, Context)
}

class GetSimilarUsersActivity {
    UserDao

    handleRequest(GetSimilarUsersActivityRequest request): GetSimilarUsersActivityResult
}

class GetSimilarUsersActivityRequest {
    -String personalityType
    -List<String> hobbies

    GetSimilarUsersActivityRequest(String personalityType, List<String> hobbies)

    +getPersonalityType(): String
    +getHobbies(): List<String>
    +toString(): String
    +builder(): Builder
}

class GetSimilarUsersActivityResult {
    List<UserModel> similarUsers

    -GetSimilarUsersActivityResult(List<UserModel> similarUsers)
    +getUsers(): List<UserModel>
    +toString(): String
    +builder(): Builder

}

class GetSimilarUsersLambda {
    List<UserModel> similarUsers

    LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetSimilarUsersActivityRequest>, Context)
}

class CreateNewMessageActivity {
    -MessageDao

    +CreateNewMessageActivity(MessageDao messageDao)
    +handleRequest(CreateNewMessageActivityRequest request): CreateNewMessageActivityResult
}

class CreateNewMessageActivityRequest {
    -String dateTimeSent
    -String sentBy
    -String receivedBy
    -String messageContent
    -boolean readStatus

    CreateNewMessageActivityRequest(String, dateTimeSent, String sentBy, String receivedBy,
                                    String messageContent, boolean readStatus)

    +getDateTimeSent(): String
    +getSender(): String
    +getRecipient(): String
    +getMessageContent(): String
    +getReadStatus(): boolean
    +toString(): String
    +builder(): Builder
}

class CreateNewMessageActivityResult {
    -MessageModel message

    -CreateNewMessageActivityResult(MessageModel message)
    +getMessage(): MessageModel
    +toString(): String
    +builder(): Builder
}

class CreateNewMessageLambda {
    MessageModel message

    LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateNewMessageActivityRequest>, Context)
}

class GetInboxMessagesActivity{
    -MessageDao messageDao

    +GetInboxMessagesActivity((MessageDao messageDao)
    +handleRequest(GetInboxMessagesActivityRequest request): GetInboxMessagesActivityResult
}

class GetInboxMessagesActivityRequest {
    -String receivedBy

    GetInboxMessagesActivityRequest(String receivedBy)

    +getSender(): String
    +getRecipient(): String
}

class GetInboxMessagesActivityResult {
    -List<MessageModel> messages

    +GetInboxMessagesActivityResult(String receivedBy)

    +getMessages(): List<MessageModel>
    +toString(): String
    +builder(): Builder
}

class GetInboxMessagesLambda {
    String receivedBy

    LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetInboxMessagesActivityRequest>, Context)
}

class GetMessagesFromUserActivity {
    MessageDao messageDao
    
    handleRequest(GetMessagesFromUserActivityRequest request): GetMessagesFromUserActivityResult
}

class GetMessagesFromUserActivityRequest {
    String sentBy
    String receivedBy
    
    GetMessagesFromUserActivityRequest(receivedBy, sentBy)
    
    getMessagesFromUser(): List<MessageModel>
}

class GetMessagesFromUserActivityResult {
    List<MessageModel> messages
    
    GetMessagesFromUserActivityResult(List<MessageModel> messages)
    
    List<MessageModel> getMessagesFromUser()
    String toString()
    Builder builder()
    
}

class GetMessagesFromUserLambda {
    List<MessageModel> messages

    LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetMessagesFromUserActivityRequest>, Context)
}

class DeleteMessagesActivity {
    -MessageDao messageDao

    +DeleteMessagesActivity((MessageDao messageDao)
    +handleRequest(DeleteMessagesActivityRequest request): DeleteMessagesActivityResult
}

class DeleteMessagesActivityRequest {
    -List<MessageModel> messages

    DeleteMessagesActivityRequest(List<MessageModel> messages)

    +getMessages(): List<MessageModel>
}

class DeleteMessagesActivityResult {
    List<MessageModel> messages

    DeleteMessagesActivityResult(List<MessageModel> messages)

    List<MessageModel> getMessages()
    String toString()
    Builder builder()
}

class DeleteMessagesLambda {
    List<MessageModel> messages

    LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteMessagesActivityRequest>, Context)
}
@enduml
---


# 9. Pages


![Login/sign-up page has options to either login or sign-up](images/Connexion_loginSignup.png)

![The create profile page has a circular profile picture and an edit button to 
change the photo, text boxes to edit all information, and a save button to save 
changes.](images/Connexion_createProfile.png)

![The homepage has a header that reads "Connexion" It also displays the
user's name. Each page has this header. The header also contains a profile picture 
icon that will take the user to edit their profile and a message icon that opens
the user's inbox.](images/Connexion_homePage.png)


![The user profile page displays a user's profile picture and their information.
Below the user's profile picture is a message icon that will allow a user to 
send the user whom the profile belongs to a message.](images/Connexion_UserProfile.png)


