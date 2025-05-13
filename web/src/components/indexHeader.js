import ConnexionClient from '../api/connexionClient';
import BindingClass from "../util/bindingClass";


/**
 * The header component for the website.
 */
export default class IndexHeader extends BindingClass {
    constructor() {
        super();

        const methodsToBind = ['addHeaderToPage'];
        this.bindClassMethods(methodsToBind, this);

        this.client = new ConnexionClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();
    }
}

