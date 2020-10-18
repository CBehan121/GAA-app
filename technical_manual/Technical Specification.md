# Technical Specification #
# Contents 
## 1: Introduction
### 1.1: Overview
### 1.2: Glossary
## 2: System Architecture
## 3: High level design 
## 4: Problems and resolution
## 5: Installation Guide

### 1.	Introduction
#### 1.1	Overview
The purpose of this app is to make the organization of matches between GAA clubs easy and fast. It gives GAA clubs an app allowing them to arrange games without needing to source phone numbers or meet up. It will also keep track of your fixture schedule to make the app the hub for your club.
This app is intended to be used by managers of GAA teams. These managers will mostly be from the ages of minor and above. This is due to the fact that for these ages they are more well-structured than the juvenile levels.
This app was coded in Java and the design of each page done with XML. Java was chosen as we have history using Java but wanted to improve our knowledge of the language. XML seemed like an obvious choice as we used android studio to compile and run the app. We also employ the use of the Firebase Realtime database for storing all of our relevant information. 
Our goal was to design this app to be as user friendly as possible so that non-technical people would have no problem using it. It was designed to be as simple and effective as possible. To begin using the app you must first register an account with us by clicking the register button. After you have registered with us you will have full access to the application. The processes of challenging teams, creating broadcast and reviewing and acting on your and other games have all been streamlined to simple clicks. 

#### 1.2 Glossary
GAA - Gaelic Athletic Association
AWS - Amazon web services
API - Application programming interface
APK - Android application package
Android Activities - Android Screens
reff - refers to "FirebaseDatabase.getInstance().getReference()"
RTD - real time database
Pyrebase - Python wrapper for Firebase API
BeautifulSoup4 - A python library for web scraping HTML and XML files.
### 2. System Architecture
Our architecture involves a android front end consisting of twelve distinct screens and a firebase database back end. 
We decided on Firebase because it had a particularly useful feature, its real time database. This feature ensured we would be always up to date without much chance of teams overbooking events. Firebase enabled us to bypass a lot of difficulties such as creating users securely and holding their information correctly. To create a user with permission to login to our application we could call ".createUserWithEmailAndPassword" on the FirebaseAuth object and then set the values of the email and password. 

The front end android activities only need to call "FirebaseDatabase.getInstance().getReference()" to get access to the database and due to the rules we set up only authenticated users have access to our database.
Where ever possible we made sure to tailor the calls more by adding .child(X) onto the end where "X" could be any branch of the JSON tree for example "Club". This would then return all of the data located under the club header as a JSON object, that would need to be parsed. Our front end could push changes to the database by using .push()on the current reff, this would give it a head containing a unique ID while allowing us to append .child(X)'s onto the end to make it easier to organize our database.

Features that connect to our database but not aren’t located in android studio is our "CheckFixtures.py" and "CleanUp.py". 

"CheckFixtures.py" downloads all of the most recent fixtures from the GAA website using BeatifulSoup4 to scrape the website for the most recent fixtures. It then uses Pyrebase to pull all our clubs from the database. With this information it finds the correct ID for the teams and adds them adds games as active fixtures with those ID's.

"CleanUp.py" was created to solve a problem we were having. It communicates with the RTD using Pyrebase and pull every event we have currently stored in the database. It then sorts the events storing all of the ones that are out of date and then uses the .remove(user['idToken']) to purge them all from the database.
The user token is created from this call "auth.sign_in_with_email_and_password("****", "****")", I have edited out any passwords when uploading to Gitlab to be safe. But without the login the script will get an authorization error and not complete its task.

Below is an overview of our system architecture. 

![](https://i.imgur.com/wYYtFdf.png)

### 3. High-Level Design
Below is the app layout, here you can clearly see the flow. This was used while testing to try ensure optimal coverage of the application.

![](https://i.imgur.com/kWQJ6E2.jpg)


Below is our data flow diagram. It has six distinct sections Registration, Broadcast creation, Canceling game, Joining game, Challenge creation and back end. All of these are necessary events and all need specific data to complete. I've also split the Database into sections "A Clubs", "F Broadcasts", "D database", "A Active" and "C Challenges". These are not references to separate databases but are instead references to branches of our database to make the data easier to follow. "D Database" is used where information will be pulled/put into multiple part of the database. 

![](https://i.imgur.com/NjtlCAA.png)

* Sequence Diagram: 
    * In this diagram it shows The sequences that occur when operating the app.
    * To login you must first register.
    * You are then brought to the home screen where you can go to create a broadcast.
    * All the broadcasts will be stored in the games section which gets updated with the change of fixtures.

![](https://i.imgur.com/sR7AfHE.png)

* Component Diagram:
    * This diagram consists of the components related to our app.
    * In each component it shows the attributes of the component if applicable

![](https://i.imgur.com/VG99JyK.png)

### 4. Problems and Resolution
####    4.1 - Database storage
##### Problem
* We encountered a major issue when it came to storing and retrieving information from our database. We had originally thought to use amazon web services, and so after Cian set it up, Conner began to populate it with lambda functions restrictions and an API gateway. Problems began when we tried to generate an SDK and implement it into our application. I spent a week learning and setting up AWS and was then blocked by the SDK crashing the app. 
##### Solution
* After two days attempting to repair it Conner brought up the option of switching to firebase, the main reason behind this being time constraints. We were quite worried there would not be enough time to get everything implemented. Once we completely switched to firebase API data storage became a lot more straight forward and we began to populate the app with API calls. 
#### 4.2 -  Android studio
##### Problem
* In the beginning neither of us had any experience using android studio. We started playing around with it and trying to get features implemented. This took a little longer than we originally expected but through not both of us working on it we didn't waste too much time. 
##### Resolution
* Spent a weekend reading through the documentation to attempt to get a handle on it. After that weekend the development of the app was much smoother. It quickly got populated with all its screens and gave them a general design. This gave us a great starting point for the rest of the project.
#### 4.3 - Database holding outdated data
##### Problem 
* We noticed an issue where we could not remove old data from the database through the app without negatively impacting the apps performance.
##### Solution
* We created a python script that implemented Pyrebase which is just a simple wrapper for the firebase API. We used it to query the firebase database and remove all entries of Challenges, broadcasts and active games that were out of date. 
#### 4.4 Challenge overbooking
##### Problem
* When a user booked a challenged if they didn’t navigate back to the home page their time list would not update and so they could book multiple challenges on the same day.
##### Solution
* Once the challenge button was pressed an API call is started, which pulls all of the user’s games and checks to see if the current date is already taken.
#### 4.5 Database 
##### Problem
* We had no experience designing a database especially one that would be queried this often and require most of it to be called.
##### Solution
* We went through multiple different iterations of the database, This was quite time consuming having to delete all of the old entries manually. We settled on splitting it four ways Active games, Challenges, Broadcasts and Clubs. This ensured we couldn’t get broadcasts and challenges confused while reducing loading times for active games. There are things we would do differently now.
#### 4.6 Long loading times
##### Problems
* A lot of our screens needed to pull data from our RTD and this very quickly resulted in a lot of progress bars getting placed throughout the app making it look overall a lot more clunky.
##### Solution
* To attempt to combat this issue we added putExtra() commands anywhere it seemed like we could to pass information around that we have previously loaded. Sadly, due to the nature of our app we could not do this everywhere as it would mean everyone’s time tables wouldn’t be up to date.
#### 4.7 Match screen updates
##### Problem
* When joining or cancelling a game from the match screen an API call wasn’t sent because you were closing a screen and not starting the match screen. This meant if you joined a game, the pop-up screen would close and your game would still be on the join list.
##### Solution
* To combat this we made the startActivity on the on the match screen be a startActivityForResult() and if a user did join/cancel an event we would sent back the event and remove it from the list view.
#### 4.8 Screen layout 
##### Problem
* During intial development we were using constraint layouts. This allowed us to smply drag and drop items onto the screen. We stuck with it for a while before testing on a different screen size. Then noticed constraint layouts do not work on all screens and left our screens horribly designed.
##### Solution
* After some research it seemed Relative layouts are currently the strongest layout for different screen sizes. It cost us a day to repair all of the layouts but our app now works on all screen sizes.


### 5. Installation Guide
#### APK
For this app we will not be uploading it on to the play store. Instead we will be using an APK. This was created through android studio. We chose to do this as we do not have a license to put apps on to the play store. The process to download this app on to an android phone is extremely simple. 
* The first step is to find the APK file on our GitLab. The path for this code/myApplication/app/release. The name of this file is app-release.apk. 
* The next step is to download this file on to your android device. Before you complete this step, you will need to allow your phone to download apps that are not on the play store.
*  At the beginning of the downloading process you will be prompted with a pop-up question on your device asking if you want to download this file. Simply click the new download button on the pop up to begin the download.
![](https://i.imgur.com/trkWg0p.jpg)

*  Once the download has been completed you can then open the app either via the notification that the download has been completed or click on the app icon.
![](https://i.imgur.com/dLbNis1.jpg)


*  So the steps were find the file, allow your phone to download apps that are not from the app store and then download it. Once these steps are complete then the app is yours to use.
  
The minimum SDK version for this app is SDK 21 which means we support from the current version of android down to android version  5. This app is only available on Android devices and not other devices such as ios devices. The app is also compatible with each android device whether that is a tablet or a mobile device.
#### Emulate 
If you do not wish to use our APK you can instead follow these steps and emulate the app with android studio.
You are required to have android studio and java installed on your machine.
* You must create a folder for example "ClubHubFolder"
* Open your terminal
* Navigate to the chosen folder
* run the command git clone https://gitlab.computing.dcu.ie/behanc7/2020-ca326-cbehan-clubhub
* Once everything has finished downloading you open your android studio.

![](https://i.imgur.com/tVozNas.png)

* click the "Open an existing android project" button and navigate to where you cloned the repo. Click into 2020-CA326-cbehan-ClubHub/code and open MyApplication
* Upon lading you now have all the application code. Simply click “run” on the top bar and “run” in the drop down to run the app.
### 6 Testing
* Testing took a major role in the development of our application.  We took a continues testing approach, whenever a new feature was developed, we tested it within itself and cycled through it until all of the bugs were fixed. We then checked all other features that it touches to ensure it had not caused a crash elsewhere. Testing like this ensured we limited the amount of crashes in our app. While it also didn't slow development to drastically and instead saved us time as we rarely had to go back and fix features we'd already implemented. 
* We created a test document that we tried to keep up to date. It contained pre-conditions, how a feature should work and post-conditions. After any major updates to the app we would both run through the test sheet and ensure all the tests are working correctly. Before the app was near completion it seemed best to continue manual testing as we'd spot more issues such as text cursors overlapping, and buttons positioned incorrectly and other obvious UI issues.
* Once our app was near completion, we started putting research into automated testing, with android studio there was a lot of options, but we settled on using Espresso. Espresso is a test framework that aims to create concise and readable android tests. We then set up all the important unit tests on all the screens. It wasn’t too difficult to understand and so wasn’t a waste of time and ended up speeding up development quite a bit. Then we set up the integration tests to ensure no issues slipped by us.
* We then set out to get some user testing done. We knew some GAA managers so, generated an APK and created a survey for them to fill in after completion. They were happy to help us test our app and so we easily sourced ten testers. After allowing all of them to fill out our survey we set about making the app more user friendly with some of the comments they left. Overall this changed around three of our applications features as some of the issues were to big an ask with the time we had left.
* As one of our members is currently working as a mobile QA we have access to emulators this was extremely helpful as it allowed us to test our app on all of the current most popular phones.

