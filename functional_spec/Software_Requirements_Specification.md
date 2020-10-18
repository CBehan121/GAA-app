# Software Requirements Specification
**for
ClubHub
Prepared by Conner Behan/Cian Peppard Leonard
02/December**
* Introduction
1.1 Overview
1.2 Glossary
1.3 Intended Audience and Reading Suggestions 
1.4 Product scope
1.4.1 What we will implement
1.4.2 What we hope to implement
1.4.3 If we finish early
1.5 Business Context

* Section 2: General Description
2.1 Product perspective
2.2 User Characteristics and Objectives
2.3 Product Functions
2.3.1 Sign in/register
2.3.2 Club search
2.3.3 Challenge
2.3.4 Broadcast
2.3.5 Calendar view
2.3.6 Messaging service
2.4 Operational scenarios
2.4.1 Registration
2.4.2 Open app while logged in
2.4.3 Open app while logged out
2.4.4 Club search
2.4.5 Challenge request
2.4.6 Broadcast
2.4.7 Send message
2.5 Implementation Constraints
2.5.1 Designs
2.5.2 User requirements
2.5.3 Financial constraints
2.5.4 Criticality
2.5.5 Reliability

* Section 3: Functional Requirements
3.1 User Interfaces
3.1.1 User registration
3.1.2 Home pages
3.1.3 Availability Webscrape
3.1.4 Search screen
3.1.5 Broadcast request
3.1.6 Challenge request
3.1.7 Calendar view
3.1.8 League table Webscrape
3.1.9 League table view
3.1.10 Messaging service

* Section 4: System Architecture
4.1 fig System Architecture Diagram
4.2 fig 4.2 Overview Diagram
4.3 Frontend
4.4 Server
4.5 Webscraping
4.6 PostgresSQL database

* Section 5: High-Level Design
5.0 High level design diagram
5.0.1 Concept class diagrams
* 5.1 Account
5.1.1 getClub()
5.1.2 getAgeGroup()
5.1.3 getDivision()
5.1.4 getID()
5.1.5 getSchedule()
5.1.6 SchdeuleCheck()

* 5.2 Game
5.2.1 getGameDate()
5.2.2 getGameDivision()
5.2.3 getGameAgeGroup()
5.2.4 checkDate()
5.2.5 getState()
* 5.3 Calendar
5.3.1 getSchedule()
5.3.2 getAccountID()
* 5.4 Broadcast
5.4.1 terminateBroadcast()
5.4.2 getBroadcastID()
5.4.3 getExpirationDate()
* 5.5 Classname
5.5.1 terminateChallenge()
5.5.2 getChallengeID()
5.5.3 getExpirationDate()
* 5.6 fig 5.2 DFD

* Section 6: Preliminary Schedule
6.1 Gantt Chart





## 1. Introduction
### 1.1 Overview 
We’ve decided to design a game scheduler for GAA teams. The app is designed with simplicity in mind. We want it to be as simple as possible for the users to navigate their way around the app. They will be able to request games off specific teams and broadcast games globally to their division/age group. This will be extremely helpful in the off season when fixtures aren't set up by the GAA board. While it will still remain useful all year round specifically for teams that don't consist of dual players. This is due to the fact that the two sports rotate on a weekly basis so teams would like to consistently play week in week out. This app would give the teams this opportunity at the touch of a button which would disregard all of the other hassles in organizing a match via contacting the opposition one on one. It aims to take into account current fixtures only allowing you to book around them. If time allows users we plan to enable users to message each other through the app. The messaging service would provide more levels of depth for what the matches would involve as the coaches could agree on special terms between each other i.e. Young players, unregistered players, length of each half etc.


### 1.2 Glossary
All double point headers eg 1.1.1 will be sub points of the above headers.
DB = Database
GAA = Gaelic Athletic Association
GDPR = General Data Protection Regulation


### 1.3 Intended Audience and Reading Suggestions
The intended audience will be users aged eighteen and above only, simply because the majority of GAA managers are above eighteen. We have been in contact with some local clubs in regards to helping test out this app in order to discover if there are any particular errors or if there are aspects we can improve. This feedback is pivotal to us for creating an app that appeals to GAA clubs. Managers will want straight forward simplistic app. Their user experience is what we are catering to, if they would like to have any features added we will happily discuss it with them. 
The rest of this document contains all the necessary information about our app.

### 1.4 Product Scope
#### **1.4.1 What we will implement:**
*   Registration - see 3.1.1
*   Home pages - see 3.1.2
*   Challenges -  see 3.1.5
*   Broadcasts -see  3.1.4
*   Calendars - see 3.1.7

We decided the above based on it is the main functionality of the app while it also contains a sizable workload.

#### **1.4.2 What we hope to implement:**
*    A safe encryption for or users logins 
*    Real time updates 

After spending time researching we found that the above could cause issues when being implemented. The encryption will hopefully be done with pgcrypto and real-time updates will need some research before we put forward a suggestion. This is vital for us as we can't have our user's data being tampered with and need to protect their personal information. To do this we will ensure to follow GDPR guidelines. 


#### **1.4.3 If we finish early:**
* League table view - see 3.1.8
* Messaging service for users - see 3.1.9
* Multiple sports 


This section will only be attempted if we finish early.
The messaging service will be very complex to implement and isn't necessary for the app. The benefits of the messaging service have been described above in 1.1. We do hope to have the time capability to do this, we feel that it could take this app to the next level for both the quality of the app and user experience. The league table view would just be a nice quality of life update so users don’t have to boot up a different app or check online. This would help our app become the most convenient app and it would seriously rival our competition to a point where we feel the users would rather our app due to how convenient it would be rather than having to go to a different app/site.
### 1.5 Business context
Currently no organizations have expressed a want to sponsor our application. Also there has been no discussions about hosting advertisements on our platform. Possibly if our product is successful we could search for sponsors or offer to host advertisements on our app.
## 2. Overall Description
### 2.1 Product Perspective
This product aims to be a self-contained, currently there is no specific way for managers to easily organize fixtures between other clubs outside of scheduled fixtures by the GAA. They must attempt to collect numbers off each other and organize it by contacting one another personally which can prove difficult as certain managers don't see eye to eye due to previous encounters and also being difficult to deal. With our app the difficulty is preventing any bad feelings between managers over scheduling conflicts. So our app is one of a kind with what we are attempting to do, a simple friendly organizer.

### 2.2 User Characteristics and objectives
As the system is app-based it is available to anyone with an android phone. Most managers can be up to any years old but are usually above eighteen, so our target audience is eighteen and above.  We don't expect our users to have technical backgrounds and so we plan to make the app as simple as possible for our older users, while also ensuring it does enough to keep our younger members interested. It is to be designed that the user experience is satisfied no matter what age or technical background. Once logged in the user will have access to all aspects of our application regardless of age or club. The end goal is for our app to be the home of friendly organization for Dublin GAA. Then possibly GAA friendlies in general. We believe that we have seen a gap in the market and that our app will make a massive difference for GAA clubs once it is fully implemented and tested. 


### 2.3 Product Functions
The product must simply but efficiently allow users to set up challenge matches between each other. These functions can be broken down easily.
* **2.3.1 Sign in/register**
Users must be able to register for the app and then login with those same credentials that they entered. When signing in a user must enter the correct email and password for their account.

    ![](https://i.imgur.com/ZcpuDyk.png)
    
    
    ![](https://i.imgur.com/4ah9wSi.png)



* **2.3.2 Club search**
Users will be able to search our database for all opposing clubs within the same age group and division. Clubs will be displayed as long as the users input is a substring of a club name in our DB.

* **2.3.3 Challenge**
Here a user can navigate to another club and initiate a challenge. Once the other user accepts it will become permanent.
The challenger will receive a push notification that they have been challenged.
The host will then receive a notification to indicate whether or not their challenge has been accepted or rejected.

    ![](https://i.imgur.com/xGS8nm5.png)


* **2.3.4 Broadcast**
Users will be able to broadcast their availability for a day.
All teams of that same age group will be able to view this broadcast and whoever accepts this open invitation first will be the challenging team.


    ![](https://i.imgur.com/6FFxDdb.png)
    
    ![](https://i.imgur.com/oVF6voE.png)

* **2.3.5 Calendar view**
The calendar will be used by the managers to track their “pending” and permanent game schedule.
When viewing the calendar you will be able to see a coloured circle over a date where a match is scheduled or has been played. This will give the manager a friendly experience when navigating through the year for future and previous dates.

    ![](https://i.imgur.com/TfFvKOt.png)
* **2.3.6 Messaging service**
Users can send messages to one another but only if their profiles match. For example, a minor manager will not be able to message a senior team manager. 
Chats will be saved and you will be able to search back through the chats if you wish to. You also have the option to delete the chat.

![](https://i.imgur.com/3ybS0i0.png)



### 2.4 Operational scenarios
* **2.4.1 Registration**
Acting as - Manager
Pre condtion - User does not currently have a login.
the user will open the app and press the register button shown on the top right of the screen. This will redirect them to a screen where they must fill in their information.
They must fill in their email address, password, number, club, age group and divison. They will then press the "continue" button and be redirected to the home screen.
Post condition - The managers information is now stored in the database and it should be possible for them to login.
* **2.4.2 Open app while logged in**
Acting as - Manager
Precondition - User has previously signed in and did not sign out.
The user will open the app and be greeted with their landing the screen(their club home screen)
Post condition - The user is now on their home screen
* **2.4.3 Open app while logged out**
Acting as - Manager
Precondition - User has logged out of the app.
The user will open the app and be shown a login page. There will be an input box for an email and a second box for their password.They will be redirected to their club home screen if their login credentials are in our DB. Also if a user has forgotten their password there will be an option to retrieve it.
Post condition - The user either logged in or has submitted a request for a password reset.
* **2.4.4 Club search**
Acting as - Manager
Precondition - User has logged in successfully
from the home screen, the user will press the "club search" button located at the bottom of the screen. From there they will be redirected to the search screen which only contains a search bar. They will tap the search bar to prompt a keyboard. Once they have searched for a club, if the club exists and is using the app a clickable name will appear. Upon pressing that name they will be redirected to chosen teams challenge screen.
Post condition - The manager will now be looking at an opposing teams challenge screen.
* **2.4.5 Challenge request** 
Acting as - Manager
Precondition - User has logged in successfully, is on the home screen and has completed the club search scenario.
From the opposing team's page, they can press the "initiate challenge" button located in the middle of the screen. This will then prompt them to enter a location and time for the challenge. Once filled out they can press the "continue" button and the challenge will be sent to the opposing club. While the request is pending the user will be able to see it on their calendar as "pending". When the opposing team accepts this request the user will be notified and the game will show on their calendar as "agreed".
Post condition - The challenge game has been created and the opposing manager has the pending request which he can accept or reject.
* **2.4.6 Broadcast**
Acting as - Manager
Precondition - User has logged in successfully and is on the home screen.
The user will press the "broadcast game" button. This will redirect them to a setup screen. Here they will enter all the relevant information such as the time, date and location of their game. When finished the user's presses the "broadcast" button, the game will show on the user's calendar as "pending" but once accepted will change to "agreed".
Post condition - A broadcast game has been created and all relevant managers have the pending request on their calendar.
* **2.4.7 Send message**
Acting as - Manager
Precondition - User has signed in and followed the club search scenario.
The user will now press the chat icon on on the opposing teams home screen. A new screen will be opened which is a empty lobby containing only the two users. To send a message the user must tap on the empty bar and type what they would like to send.
Post condition - User B has received a message and a chat window is open
### 2.5 Implementation Constraints
* **2.5.1 Designs**
We will need to ensure the designs are solid within the first few interactions. Time will not be set aside for re-implementing new designs. Since that the application will be based around Dublin GAA we will be using colours associated with Dublin and the GAA so it shows the connection between them.
* **2.5.2 User requirements**
We hope that after presenting the app to our target audience they will begin using it for their future friendlies. We will welcome all feedback. If their feedback is helpful we will consider implementing it into our application. We want the user experience to be as satisfying as possible so that they will want to continue to use our app.
* **2.5.3 Financial constraints**
If the application grows as big as we hope. We will run into issues with how much traffic we are allowed to run through our server. This will require us to get sponsored or start setting a price for the application in order to pay for the server. If this fails we could also try ads, premium edition, in-app purchases etc.
* **2.5.4 Criticality**
The development is critical because we will use it as our CA326 project. The project is graded and must be passed to pass the year.
We will send out our product to be tested. Once the app has then been reviewed and critiqued we will fix them problems and take the criticism from the testers into account on whether or not to implement the comments made. These decisions will be decided in meetings.
* **2.5.5 Reliability**
In order for managers to continue using our application, it must be reliable. All information must be correct and the app shouldn't have any downtime. If however, we do experience some errors we will send out the information to the users with a simple email. We believe communication is key between the creators and the users, so that the users believe that they can trust us and our app.

## 3 Functional requirements
### 3.1 User Interfaces
#### 3.1.1 User registration
* **Description:**
On initial boot of the application, the user is greeted with a login page. Since it is their first time booting up the app they will not have a login. Therefore they will have to press the register button in the top right hand corner. Here they will input their club name, age group, division and manager details. They will also be asked to create a secure username and password. On the home page there will also be a forget password button in case the login details go missing. This feature will be tested to make sure it complies to the most up to date version of android software and will also include previous versions (no lower than android 6) to cater for phones which aren't updated to the newest software.
* **Criticality:**
The user registration is critical to the app. Without it, there will be no way of showing the user which teams they can challenge and will stop them from being challenged. The information they enter will tailor what opposing teams they are shown and ensure they can only interact with them. You may only challenge clubs that have been registered to the app as we will have no ability to contact unregistered teams. This would also encourage clubs to register with us when they hear of the benefits. We feel that the app will spread by word of mouth between managers of opposing clubs.
* **Technical issues:**
The hardest part of this is to ensure we store the username and passwords correctly. Through some research we found that pgcrypto can do this nicely. We hope to have it so you only need to login once and then it will be cache for a smoother experience. With encryption out of the way, we then have to make sure we cant have user login duplicates and that when a user logs in they are brought to their account, not someone elses. We also need to make sure that all of our users data is protected and only revealed to that user in order to comply with GDPR. This is extremely important because we want our users to trust us with their data. If users can't trust us they won't use our app.
* **Dependencies with other requirements:**
This process will grant access to the app, without it users will not be able to use the app. 


#### 3.1.2 Home pages
* **Description:**
Users will have a customizable home page, this will show all of their contact and club information if they wish. From here they will be able to navigate to other screens such as the calendar, challenge and broadcast screens. To make sure that the layout of the home screen is up to standards, we will ask testers non bias questions about it. This will help is help us design our app with user design with help from our users.
* **Criticality:**
Home pages aim to be the landing screen of the app, because of this it is essential we have it performing correctly. Without it most of our other features would be inaccessible. It is the screen users will see the most, they see it when they login, want to navigate somewhere, want to challenge a team its very constant. Thus meaning that it needs to grab the attention of the user so that they feel comfortable using the app. In order to achieve this our colour scheme will match that ambition rather than being abstract and an eye sore and pushing users away from the app.
* **Technical issues:**
There will be many paths leading to and from this screen. It may be smart to make it so pressing “back” from this screen will close the app.This means we won't end up with hundreds of screens held at the same time because whenever we reach the landing screen we can close all other rendered screens. When viewing an opposing teams home screen a query will need to be sent to our postgres DB to get the team in questions relevant information. This will then all clearly be shown to the user.
* **Dependencies with other requirements:**
Since this is the landing screen the only requirement is that the user has logged in.


![](https://i.imgur.com/XbDLMFz.png)


#### 3.1.3 Availability Webscrape
* **Description**
This will be a back end feature, it will be the main way we update the DB on team availability. We plan to have it automatically run multiple times a week to ensure we have the correct information. We are looking for the updates to take place on a Monday and a Thursday due to the fact that match days are usually Saturday, Sunday and Wednesday.
* **Criticality** 
This feature is very important to the app, without it teams will be able to challenge on a day they have a league game planned. This will serve as a guideline to help teams work around their schedule in case on the off chance that a match is double booked.
* **Technical issues**
Due to the layout of the webpage "https://www.dublingaa.ie/fixtures", the web scarping shouldn't be to difficult. It will be harder to get the information passed through to our DB. We must ensure the dates are passed correctly and added to the   correct tables. They will be sent using our ORM which will hopefully simplify this process.
* **Dependencies with other requirements**
This feature will only rely on back end features working correctly such as Django and the DB itself. It will also need Dublin GAA to not refactor their HTML.


#### 3.1.4 Search screen
* **Description**
This screen plans on being a straight forward search screen. Users will be prompted with a keyboard and empty type box to search for a club. Once searched they can press the club name to be taken to their home screen. Clubs will be displayed as long as the user's input is a sub string of a club name in our DB.
* **Criticality**
This will be the main route our users use for challenging and messages other managers. Will need the search screen to function correctly. Without it these features will not be accessible. This increases its criticality.
* **Technical issues**
This feature will need to query the DB for teams matching the already typed characters. I think if possible we will limit the search to in order prefix/exact matches of what the user has typed. Which then must be outputted to the screen. The links must redirect to the correct home pages.
* **Dependencies with other requirements**
This feature will rely heavily on the user logins and home pages and so both must be working correctly.

#### 3.1.5 Broadcast request
* **Description:**
When creating a broadcast clubs agree that they will be the ones hosting the game on their grounds. The user will broadcast they are open for any challenge on the chosen dates. This will result in the user creating a pending request, any team that can challenge them may accept this request. Once accepted the event's status will be updated as permanent and both teams will be notified.
* **Criticality:**
this features criticality depends on what feature users prefer Broadcast or challenge. We must ensure its logic is straightforward and is simple to navigate.
* **Technical issues:**
We will need to store this with a temporary tag in the DB until it is accepted. For this we plan on giving all events a `temp = True/False` to make things easier. Once accepted the temporary flag will be set to False. This will need to access multiple tables in the DB and update it in real-time to ensure teams cannot get booked twice. If a temporary flag is set for to long the event will be removed and the team will be notified.
* **Dependencies with other requirements:**
As stated above this, feature will change a lot of the DB. The user registration must be working correctly.

#### 3.1.6 Challenge request
* **Description:** 
A user will navigate to a teams “page” from here they will click the challenge button. 
Only users of the correct division and age group will be visible.
* **Criticality:**
This will be one of the main features used in the app. It could be used to set up over 60% of the games depending on which system the teams gravitate towards. We will need to ensure users can easily find clubs they want to challenge and will understand how it works.
* **Technical issues:**
While initiating a challenge the aggressor will pick a date, a query will be sent to the DB to ensure the opposing team isn't busy on the chosen date. We will need to store the challenge as a temporary event i.e. `temp = True` in the DB, While temp = True it will show in calendars with a tag on it making it clearly visible. Once accepted by the opposing team it will become permanent `temp = False`. The calendar temp tags will be removed. Depending on the opposing team's decision a notification will be received for a “Accepted” “Rejected” or “Ignored” request. Once a response is received the DB will be updated with the relevant information, are the teams busy on those days or not.
* **Dependencies with other requirements:**
 This feature heavily depends on the user logins and search feature's working correctly. While it may be possible to use some of the broadcast logic to help create this feature.

#### 3.1.7 Calendar view
* **Description:**
The user will navigate to their calendar. Here all of their important information will be shown. Such as all relevant broadcasts/challenge requests. We plan to make broadcast requests shown here be buttons, This ensures they can be easily accepted.
* **Criticality:**
This will be crucial to the app, it hopes to be the main feature for users to keep track of their planned games.
* **Technical issues:**
This will need to be updated in real time, to achieve this we plan to refresh it every time the user opens it. The calendar must check for temporary and permanent entries and display them to the screen correctly with temp and permanent tags. We plan to cache the data to avoid load times getting to long.
* **Dependencies with other requirements**:
Once a user has logged in the only other information this feature requires to correctly access the DB and show information only applicable to them.
![](https://i.imgur.com/tpGxuz3.png)
#### 3.1.8 League table Webscrape
* **Description**
This algorithm will be the main feature used for updating 3.1.9 the league table. It will scrape its information off the GAA website which will then be sent to the DB.
* **Criticality**
This feature is only critical to 3.1.9 and is not required for any other part of the app.
* **Technical issues**
Due to the design of the GAA website selenium will need to be incorporated inside this function to open pop-ups on the website for the results of games. This could make this feature a lot slower and so we may have to find a better way of running this. The information will need to be formatted correctly and sent to the DB using our set up ORM.
* **Dependencies with other requirements**
This feature only relies on the back end of our software. Our server, Django and the DB must be working correctly.
#### 3.1.9 League table view
* **Description**
This screen will show the current users league time table. It will not be interactable but aims to be updated in real-time.
* **Criticality**
The feature is not critical, No other aspects of the app will rely on it. It's only included because we will already be tracking a lot of the information for other purposes. 
* **Technical issues**
This feature will use the current users league to query the correct table. The hardest part of this feature will be designing the table to be shown in the app. We plan to have it update whenever the user opens it. This could cause the app to slow down so other options may be better.
* **Dependencies with other requirements**
It will rely on the user's login to show the correct league table.

#### 3.1.10 Messaging service
* **Description**
Users will be able to open a private channel to converse with each other. A user will navigate to an opposing managers home screen and press the "message" button. 
* **Criticality**
This feature isn't very critical, It is in no way necessary for the app. No other features will rely on it.
* **Technical issues**
This will involve us learning more about web sockets and will have to have the phones speaking to each other rather than just querying our DB. This feature is pushed to the very end of our scope simple because it requires so much more to implement. 
* **Dependencies with other requirements**
This will rely heavily on user logins to ensure the communications are sent and received correctly.


## 4 System Architecture
### fig 4.1 System architecture diagram
![](https://i.imgur.com/PyMqKaF.png)
### fig 4.2 Overview Diagram
![](https://i.imgur.com/lm6eWF6.png)

As fig 4.1 shows Django will be the main form of communication for front end to back end communications. The Front end will be written in java/XMl and the back end plans to be written in python.
As shown we plan on using Django ORM for interacting with our DB. This will be harder to learn than basic SQL commands but we feel it's the better option. When our webscraping algorithms are ran they will send their information to our back end where it will in turn be fed through Django to update the DB.

### 4.3 Frontend
As previously stated our front end will primarily be wrote in Java and XML. We plan on using Android studio, because of this it will be the least technical part of our project.

### 4.4 Server
Our server is going to be taking a lot of information from different places. It will be the most complex part of our project. The webscraping results and DB update requests from the front end will both pass through it. While it will also be sending responses back to the front end very regularly. With how much traffic it will get we will need to make it as efficient as possible. Currently we plan to host it on AWS(Amazon web services). 

### 4.5 Webscraping
The webscraping algorithms will be written in python using beautiful soup and selenium if necessary. We have been granted permission to access the GAA website "https://www.dublingaa.ie/fixtures" that currently holds all of the fixtures. Scraping this website means we will always have the correct information. The website has been around long enough that we don't expect the HTML layout to be changed.


### 4.6 PostgresSQL database
The database will except queries from the server and store the data where necessary. Many of the user actions and the webscraping will be editing our DB very often, because of this we thought it would be best to use Django to query it. The ORM (once set up) will give us great routes for editing the information. 
We aren't certain and will have to move things around when creating it but currently storing each league in their own table seems like the best way to keep track of information.


## 5 High level design
**fig 5.0 High level design Diagram**

![](https://i.imgur.com/YvLxJZ8.png)

**fig 5.0.1**Concept class diagrams (will be subject to change)



![](https://i.imgur.com/fqPXmlU.png)



### 5.1 Account
#### 5.1.1 getClub()
* Get club is returning the name of the GAA club. The name of the club is entered and stored as a string when the user is first creating the account for his/her GAA club. This is important as it is what other users will be mostly searching for when they know what specific team they want to challenge to a friendly.

#### 5.1.2 getAgeGroup()
* This function will be returning the age group associated with the team that was registered. Currently we are only dealing with two different age groups. Those age groups are adult and minor. It is important that the age group is entered to ensure that when creating a fixture that a minor team can't play against an adult team due to safety reasons. The age group is entered as a string as either "Adult" or "Minor".

#### 5.1.3 getDivision()
* By calling getDivision it returns the division of the requested team. This is entered as an integer. The reason it is entered as an integer and not a string is because even though the level changes from Senior to Intermediate the division number increases and doesn't matter what level you play at. This will be used when managers are looking to set up a challenge match. They will have the option to broadcast a challenge to teams from certain divisions.

#### 5.1.4 getID()
* This will be used to return the ID of a team. We have decided to implement this to make the searching and matching much easier specifically for clubs with multiple teams. It will be used in the data base to give each team a unique ID.
#### 5.1.5 getSchedule()
* We will be implementing get_Schedule() when a user is looking to view their own schedule or when they are looking to find out an oppositions schedule so that they can challenge them on a day that suits both sides.

#### 5.1.6 SchdeuleCheck()
* This function is pivotal to creating a challenge match. It will be called every time a match is being created to check that there isn't a scheduling conflict for either team so that the match can go ahead as originally planned.

### 5.2 Game

#### 5.2.1 getGameDate()
* Here is where we request the date in which a game is being played. This will be used to show the fixtures and results on the calendar.

#### 5.2.2 getGameDivision()
* This request will return the division in which the game being played is in. For friendly matches there will be no division and instead will say friendly as it will not be registered as a divisions game.

#### 5.2.3 getGameAgeGroup()
* When this is called it will return the age group of the match that is to be played. There are only two different age groups that can be returned which are "minor" and "adult".

#### 5.2.4 checkDate()
* This will check the date that the match is trying to be scheduled on. This will be implemented to make sure that a team isn't being double booked for a fixture. If this returns that the date is available then the game will be booked but otherwise the booking will fail.

#### 5.2.5 getState()
* The state will be if the game is available or already booked. This will play a key part when two teams try to book a game at the same time. Once one team has booked the state will instantly change to booked and reject the other team from being booked in for the match.

### 5.3 Calendar
#### 5.3.1 getSchedule()
* We will be implementing get_Schedule() when a user is looking to view their own schedule or when they are looking to find out an oppositions schedule so that they can challenge them on a day that suits both sides. As illustrated above in 5.1.5.

#### 5.3.2 getAccountID()
* Every account will have a specific ID. This ID will be returned when this function is called. We have this to make searching through the different clubs much easier and each ID will be unique to that account.

### 5.4 Broadcast
#### 5.4.1 terminateBroadcast()
* This will be called once game requests have gone past their expiry date i.e. Game is meant to be played on the 25/1/20 so it will be terminated 26/1/20. By terminating these broadcasts it will help keep the server from becoming heavy and provide a better user experience.

#### 5.4.2 getBroadcastID()
* Each broadcast will have an ID so that it will be unique and much easier to distinguish between other broadcasts. The ID will become extremely useful when we are using SQL.

#### 5.4.3 getExpirationDate()
* By calling this function it will return the expiration date of the broadcast that is entered. We will use this to know whether to use terminateBroadcast() or just pass through to the next part of the code.

### 5.5 Classname
#### 5.5.1 terminateChallenge()
* This is the extremely similar to terminateBroadcast 5.4.1. This will be called when a challenge has gone past its challenge date. The difference between a challenge and a broadcast is that a challenge is sent directly in private where as a broadcast is sent out in the open publicly.

#### 5.5.2 getChallengeID()
* Once again this is similar to getBroadcastID() in 5.4.2. It will return the unique ID of a challenge which will make it much easier to find and navigate for other parts of the coding process.

#### 5.5.3 getExpirationDate()
* By calling this function it will return the expiration date of the challenge that is entered. We will use this to know whether to use terminateChallenge() or just pass through to the next part of the code.

### 5.6 **fig 5.2 DFD**


![](https://i.imgur.com/UJ6hHHG.png)

 



## 6 Preliminary Schedule
Below is a Gantt chart for a visual representation of how we plan to order the development of our software. There are many separate aspects of this development. There will be two of us working on the app so tasks may not be completed chronologically, It depends on how quickly each of us can complete our tasks.

**fig 6.1 Gantt Chart**

![](https://i.imgur.com/LkxtW8y.png)







