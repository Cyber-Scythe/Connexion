import BindingClass from "../util/bindingClass";
import { Auth } from 'aws-amplify';

export default class Authenticator extends BindingClass {
    constructor() {
        super();

        const methodsToBind = ['getCurrentUserInfo'];
        this.bindClassMethods(methodsToBind, this);

        this.configureCognito();
    }

    async getCurrentUserInfo() {
        const cognitoUser = await Auth.currentAuthenticatedUser();
        const { email, name } = cognitoUser.signInUserSession.idToken.payload;
        return { email, name };
    }

    async isUserLoggedIn() {
        try {
            await Auth.currentAuthenticatedUser();
            return true;
        } catch {
            return false;
        }
    }

    async getUserToken() {
        const cognitoUser = await Auth.currentAuthenticatedUser();
        return cognitoUser.signInUserSession.idToken.jwtToken;
    }

    async login() {
        await Auth.federatedSignIn();
    }

    async logout() {
        await Auth.signOut();
    }

    async signUp() {
        await Auth.signUp();
    }

    configureCognito() {
        Auth.configure({
            userPoolId: process.env.COGNITO_USER_POOL_ID,
            userPoolWebClientId: process.env.COGNITO_USER_POOL_CLIENT_ID,
            oauth: {
                domain: process.env.COGNITO_DOMAIN,
                redirectSignIn: process.env.COGNITO_REDIRECT_SIGNIN,
                redirectSignOut: process.env.COGNITO_REDIRECT_SIGNOUT,
                redirectSignUp: process.env.COGNITO_REDIRECT_SIGNUP,
                region: 'us-east-2',
                scope: ['email', 'openid', 'phone', 'profile'],
                responseType: 'code'
            }
        });
    }
}
