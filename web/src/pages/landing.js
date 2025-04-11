import ConnexionClient from '../api/connexionClient';
import IndexHeader from '../components/indexHeader';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { Auth } from 'aws-amplify';


export default class Landing extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore();
        this.indexHeader = new IndexHeader(this.dataStore);

        console.log("index constructor");
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
    mount() {
        this.indexHeader.addHeaderToPage();
        this.client = new ConnexionClient();

    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const landing = new Landing();
        landing.mount();
};

window.addEventListener('DOMContentLoaded', main);
