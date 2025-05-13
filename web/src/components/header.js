import ConnexionClient from '../api/connexionClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createNavigationButtons', 'createProfilePicDropdown'];

        this.bindClassMethods(methodsToBind, this);

        this.client = new ConnexionClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const navigationButtons = this.createNavigationButtons();
        const profilePicDropdown = this.createProfilePicDropdown();
        const siteTitle = this.createSiteTitle();
       // const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');

        header.appendChild(siteTitle);
        header.appendChild(navigationButtons);
        header.appendChild(profilePicDropdown);

        //header.appendChild(userInfo);
    }


    createSiteTitle() {
        const logoImage = document.createElement('img');
        logoImage.src = 'images/logo-transparent-bg.png';
        logoImage.classList.add('header_home');
        logoImage.height = 150;
        logoImage.width = 150;
        logoImage.align = 'left';
/*
        const profilePic = document.createElement('img');
                profilePic.src = 'images/profile-picture.png';
                profilePic.classList.add('rounded-circle');
                profilePic.height = 50;
                profilePic.width = 50;
                profilePic.align = 'right';
                profilePic.id = 'header-profile-picture';

        const dropdown = document.createElement('div');
        dropdown.classList.add('dropdown');

        const dropdownContent = document.createElement('div');
        dropdownContent.classList.add('dropdown-content');

        const dropdownButton1 = document.createElement('a');
                dropdownButton1.id = 'drop-dwn-1';
                dropdownButton1.href = '/user_profile_settings.html';
                dropdownButton1.innerText = 'Settings & Privacy';

                const dropdownButton2 = document.createElement('a');
                dropdownButton2.id = 'drop-dwn-2';
                dropdownButton2.href = '/dashboard.html';
                dropdownButton2.innerText = 'Dashboard';

                const dropdownButton3 = document.createElement('a');
                dropdownButton3.id = 'drop-dwn-3';
                dropdownButton3.href = '/view_profile.html';
                dropdownButton3.innerText = 'Profile';

                const dropdownButton4 = document.createElement('a');
                dropdownButton4.id = 'drop-dwn-4';
                dropdownButton4.href = '#';
                dropdownButton4.innerText = 'Log Out';
                dropdownButton4.addEventListener('click', this.client.logout);
                dropdownButton4.href = '/index.html';

        dropdown.appendChild(profilePic);
        dropdown.appendChild(dropdownContent);

        dropdownContent.appendChild(dropdownButton1);
        dropdownContent.appendChild(dropdownButton2);
        dropdownContent.appendChild(dropdownButton3);
        dropdownContent.appendChild(dropdownButton4);
*/
        const siteTitleDiv = document.createElement('div');

        siteTitleDiv.classList.add('site-title');
        siteTitleDiv.appendChild(logoImage);
        //siteTitleDiv.appendChild(dropdown);

        return siteTitleDiv;
    }


    createNavigationButtons() {
        const navigationButtons = document.createElement('div');
        navigationButtons.classList.add('navigation-buttons');

        const connexionsImg = document.createElement('img');
        const inboxImg = document.createElement('img');
        const notificationImg = document.createElement('img');

        connexionsImg.src = 'images/connexions.png';
        connexionsImg.height = 45;
        connexionsImg.width = 45;

        inboxImg.src = 'images/messages.png';
        inboxImg.height = 45;
        inboxImg.width = 45;

        notificationImg.src = 'images/notifications.png';
        notificationImg.height = 45;
        notificationImg.width = 45;

        const connexionsButton = document.createElement('a');
        connexionsButton.classList.add('buttn');
        connexionsButton.href = '/view_connexions.html';
        connexionsButton.width = 30;
        connexionsButton.height = 30;
        connexionsButton.appendChild(connexionsImg);

        const inboxButton = document.createElement('a');
        inboxButton.classList.add('buttn');
        inboxButton.href = '/userInbox.html';
        inboxButton.width = 30;
        inboxButton.height = 30;
        inboxButton.appendChild(inboxImg);

        const notificationsButton = document.createElement('a');
        notificationsButton.classList.add('buttn');
        notificationsButton.href = '/notifications.html';
        notificationsButton.width = 30;
        notificationsButton.height = 30;
        notificationsButton.style.marginRight = '100px';
        notificationsButton.appendChild(notificationImg);

        navigationButtons.appendChild(notificationsButton);
        navigationButtons.appendChild(inboxButton);
        navigationButtons.appendChild(connexionsButton);

        return navigationButtons;
    }

    createProfilePicDropdown() {
        const profilePicDropdown = document.createElement('div');
        profilePicDropdown.classList.add('dropdown');

        const profilePic = document.createElement('img');
        profilePic.src = 'images/profile-picture.png';
        profilePic.classList.add('rounded-circle');
        profilePic.height = 70;
        profilePic.width = 70;
        profilePic.align = 'right';
        profilePic.id = 'header-profile-picture';
        profilePic.style.right = '10px';

        const dropdownContent = document.createElement('div');
        dropdownContent.classList.add('dropdown-content');

        const dropdownButton1 = document.createElement('a');
        dropdownButton1.id = 'drop-dwn-1';
        dropdownButton1.href = '/user_profile_settings.html';
        dropdownButton1.innerText = 'Settings & Privacy';

        const dropdownButton2 = document.createElement('a');
        dropdownButton2.id = 'drop-dwn-2';
        dropdownButton2.href = '/dashboard.html';
        dropdownButton2.innerText = 'Dashboard';

        const dropdownButton3 = document.createElement('a');
        dropdownButton3.id = 'drop-dwn-3';
        dropdownButton3.href = '/view_profile.html';
        dropdownButton3.innerText = 'Profile';

        const dropdownButton4 = document.createElement('a');
        dropdownButton4.id = 'drop-dwn-4';
        dropdownButton4.href = '#';
        dropdownButton4.innerText = 'Log Out';
        dropdownButton4.addEventListener('click', this.client.logout);
        dropdownButton4.href = '/index.html';

        dropdownContent.appendChild(dropdownButton1);
        dropdownContent.appendChild(dropdownButton2);
        dropdownContent.appendChild(dropdownButton3);
        dropdownContent.appendChild(dropdownButton4);

        profilePicDropdown.appendChild(profilePic);
        profilePicDropdown.appendChild(dropdownContent);

        return profilePicDropdown;
    }
/*
    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfo.appendChild(childContent);

        return userInfo;
    }


    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }
*/
}
