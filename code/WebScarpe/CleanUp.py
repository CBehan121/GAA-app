import pyrebase
from datetime import datetime
def main():
	config = {
	  "apiKey": "AIzaSyDPcc3w3O9zUsRA599WpGh8LcAZmW9kb4Y",
	  "authDomain": "my-application-a29f5.firebaseapp.com ",
	  "databaseURL": "https://my-application-a29f5.firebaseio.com/",
	  "storageBucket": "my-application-a29f5.appspot.com",
	  "serviceAccount": "/home/conner/Documents/clubhub-270013-9a21263094f2.json" #Not putting on github
	}
	todaysDate = datetime.now()
	firebase = pyrebase.initialize_app(config)
	auth = firebase.auth()
	user = auth.sign_in_with_email_and_password("****", "****") ##Not putting on github
	db = firebase.database()
	cleanActive()
	cleanBroadcasts()
	cleanchallenges()
def cleanActive():
	listOfActive = db.child("Active").get(user['idToken']).val()
	pastGames = []
	for item in listOfActive:
		
		inner = listOfActive[item]
		gamedate = inner['date']
		if gamedate!= "":
			datetime_object = datetime.strptime(gamedate, '%d/%m/%Y')
			if (datetime_object< todaysDate):

				pastGames.append(item)

	for item in pastGames:
		db.child("Active").child(item).remove(user['idToken'])
def cleanchallenges():
	pastChalls = []
	challenges = db.child("Challenges").get(user['idToken']).val()
	for item in challenges:
		inner = challenges[item]
		for ids in inner:
			params = inner[ids]
			challDate = params['date']
			if challDate!= "":
				datetime_object = datetime.strptime(challDate, '%d/%m/%Y')
				if (datetime_object< todaysDate):

					pastChalls.append(item)
	for item in pastChalls:
	db.child("Challenges").child(item).remove(user['idToken'])
def cleanBroadcasts():
	pastBroadcasts = []
	broadcasts = db.child("Broadcasts").get(user['idToken']).val()
	for item in broadcasts:
		inner = broadcasts[item]
		for ids in inner:
			params = inner[ids]
			broadDate = params['date']
			if broadDate!= "":
				datetime_object = datetime.strptime(broadDate, '%d/%m/%Y')
				if (datetime_object< todaysDate):
					pastBroadcasts.append(item)
	for item in pastBroadcasts:
	db.child("Broadcasts").child(item).remove(user['idToken'])


if _name_ == "_main_":
    main()