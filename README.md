<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/Cyber-Scythe/Connexion">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Connexion</h3>

  <p align="center">
    Social media has done so much to bring people together, but it is missing an integral part that allows people to make meaningful, genuine connections.
    According to Myers Briggs, personality compatibility is what lays the foundation for meaningful relationships. Connexion is a social media platform, in
    it's infancy, that matches users based on their personality type and ranks them by the hobbies they have in common. Unlike traditional social media       platforms, users can only see other users that they have matched with. This does away with the issue of "friend collecting".
  </p>
  <p>
    <br />
    <a href="[https://github.com/github_username/repo_name](https://github.com/Cyber-Scythe/Connexion)"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="[https://github.com/github_username/repo_name](https://github.com/Cyber-Scythe/Connexion)">View Demo</a>
    ·
    <a href="[https://github.com/github_username/repo_name/issues](https://github.com/Cyber-Scythe/Connexion/issues)">Report Bug</a>
    ·
    <a href="[https://github.com/github_username/repo_name/issues](https://github.com/Cyber-Scythe/Connexion/issues)">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
![Connexion-Screenshot](https://user-images.githubusercontent.com/10260229/224134983-d7376fb2-9da4-4003-aac4-f926de455a83.jpg)
<br>
<br>
  Connexion is a social media platform in it's infancy. Connexion matches users based on the Myers Briggs personality compatability model and ranks tham
  by common hobbies/interest. A user can only see and interact with users that they have matched with, doing away with the issue of "friend collecting"     that we so often see on today's popular social media platforms. Connexion's aim is to give users the ability to make new and meaningful connections with
  people similar to themselves.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* <img alt="LinkedIn" src="https://img.shields.io/badge/-Java-red"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-Javascript-orange"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-HTML-blue"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-CSS-green"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-AWS%20Lambda-9cf"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-AWS%20DynamoDB-ff69b4"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-AWS%20CloudFront-yellowgreen"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-AWS%20CloudFormation-purple"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-AWS%20S3-blueviolet"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-AWS%20Cognito-brightgreen"></a>
* <img alt="LinkedIn" src="https://img.shields.io/badge/-faiss-blue"></a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Setup

1. Use an Amazon AWS account.
2. Install AWS CLI. [Documentation](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
3. Install AWS SAM CLI. [Documentation](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html)
4. Install NodeJS to run the npm commands below.

- For Windows / WSL users:
```shell
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash - &&\
sudo apt-get install -y nodejs
```
- For Mac users:
```shell
brew install node
```

5. Install Docker <br><br>
   [Linux](https://docs.docker.com/desktop/install/) <br>
   [Mac](https://docs.docker.com/desktop/install/mac-install/) <br>
   [Windows](https://docs.docker.com/desktop/install/windows-install/) <br>

### Backend Setup via Lambda Service
1. Build the Java code: `sam build`
2. Create an S3 bucket: `aws s3 mb s3://YOUR_BUCKET`
3. Deploy the SAM template: `sam deploy --s3-bucket BUCKET_FROM_ABOVE --parameter-overrides S3Bucket=BUCKET_FROM_ABOVE FrontendDeployment=local`
   > **NOTE:** _Yes you have to provide the same S3 bucket name twice. Yes this is annoying._
4. Run the local API: `sam local start-api --warm-containers LAZY`


### Frontend Setup
1. CD into the web directory: `cd web`
2. Install dependencies : `npm install`
3. Run the local server: `npm run run-local`


After the steps above, you will have a server running on port `8000` - you can access it via [http://localhost:8000](http://localhost:8000) in your web browser.


### Installation

1. Clone the repo
   ```bash
   git clone https://github.com/Cyber-Scythe/DigitalNomad.git
   ```
2. Install NPM packages
   ```bash
   npm install
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Feature 1
- [ ] Feature 2
- [ ] Feature 3
    - [ ] Nested Feature

See the [open issues](https://github.com/github_username/repo_name/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Erika Smith:  erikadaniellesmith@gmail.com
<br>
<a href="https://www.linkedin.com/in/erika-smith-dev/">
<img alt="LinkedIn" src="https://img.shields.io/badge/-LinkedIn-brightgreen"></a>
<br>
<a href="https://github.com/Cyber-Scythe/">
<img alt="GitHub" src="https://img.shields.io/badge/-GitHub-blueviolet"></a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[product-screenshot]: images/screenshot.png

