import ConnexionClient from '../api/connexionClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'editProfile', 'viewInbox', 'viewMatches'], this);

        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.editProfile);
        this.dataStore.addChangeListener(this.viewInbox);

        this.header = new Header(this.dataStore);

        console.log("viewProfile constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get('email');

        const profile = await this.client.getProfile(email);
        this.dataStore.set('profile', profile);
    }

    /**
     * Add the header to the page and load the ConnexionClient.
     */
    mount() {
        document.getElementById('drp-dwn-1').addEventListener('click', this.getInbox);
        document.getElementById('drp-dwn-2').addEventListener('click', this.getConnexions);
        //this.header.addHeaderToPage();

        this.client = new ConnexionClient();
        this.clientLoaded();
    }

    async getInbox() {

    }

    async getConnexions() {

    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addProfileInfoToPage() {
        const profile = this.dataStore.get('profile');
        if (profile == null) {
            return;
        }

        document.getElementById('user-name').innerText = profile.name;
        document.getElementById('user-age').innerText = profile.birthdate;
        document.getElementById('user-personality-type').innerText = profile.personalityType;

        const location = profile.city + ", " + profile.state;
        document.getElementById('user-location').innerText = location;
        document.getElementById('hobbies').innerText = profile.hobbies;

       // Code to get profile picture from S3 bucket and set it as value
       // of 'profile-picture'
    }

    /**
     * When the songs are updated in the datastore, update the list of songs on the page.
     */
    addSongsToPage() {
        const songs = this.dataStore.get('songs')

        if (songs == null) {
            return;
        }

        let songHtml = '';
        let song;
        for (song of songs) {
            songHtml += `
                <li class="song">
                    <span class="title">${song.title}</span>
                    <span class="album">${song.album}</span>
                </li>
            `;
        }
        document.getElementById('songs').innerHTML = songHtml;
    }

    /**
     * Method to run when the add song playlist submit button is pressed. Call the MusicPlaylistService to add a song to the
     * playlist.
     */
    async addSong() {

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const playlist = this.dataStore.get('playlist');
        if (playlist == null) {
            return;
        }

        document.getElementById('add-song').innerText = 'Adding...';
        const asin = document.getElementById('album-asin').value;
        const trackNumber = document.getElementById('track-number').value;
        const playlistId = playlist.id;

        const songList = await this.client.addSongToPlaylist(playlistId, asin, trackNumber, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('songs', songList);

        document.getElementById('add-song').innerText = 'Add Song';
        document.getElementById("add-song-form").reset();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewPlaylist = new ViewPlaylist();
    viewPlaylist.mount();
};

window.addEventListener('DOMContentLoaded', main);
