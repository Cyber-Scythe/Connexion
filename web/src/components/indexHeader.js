import ConnexionClient from '../api/connexionClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class IndexHeader extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['addHeaderToPage',
                               'createLoginButton'], this);

        this.client = new ConnexionClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        //const siteTitle = this.createSiteTitle();
        //console.log("siteTitle: " + siteTitle);

        const loginButton = this.createLoginButton(currentUser);

        const header = document.getElementById('header');
        const headerHome = document.getElementById('header_home');

        //headerHome.appendChild(siteTitle);
        headerHome.appendChild(loginButton);

        header.appendChild(headerHome);
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

    createSiteTitle() {
        const logoImage = document.createElement('img');
        logoImage.src = 'images/logo-transparent-bg.png';
        logoImage.classList.add('header_home');
        logoImage.height = 150;
        logoImage.width = 150;
        logoImage.align = 'left';

        const siteTitleDiv = document.createElement('div');
        siteTitleDiv.classList.add('site-title');
        siteTitleDiv.appendChild(logoImage);
        //siteTitleDiv.appendChild(dropdown);

        return siteTitleDiv;
    }
*/

    createLoginButton(currentUser) {
        const loginButtonDiv = document.createElement('div');
        loginButtonDiv.classList.add('header_home');

        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = 'Log In';
        button.id = 'login-button';
        button.float = 'right';
        button.addEventListener('click', this.client.login);

        loginButtonDiv.appendChild(button);
        return loginButtonDiv;
    }
/*
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
