from bs4 import BeautifulSoup
from urllib.request import Request, urlopen
import pyrebase
from datetime import datetime
def main():
	config = {
	  "apiKey": "AIzaSyDPcc3w3O9zUsRA599WpGh8LcAZmW9kb4Y",
	  "authDomain": "my-application-a29f5.firebaseapp.com ",
	  "databaseURL": "https://my-application-a29f5.firebaseio.com/",
	  "storageBucket": "my-application-a29f5.appspot.com",
	  "serviceAccount": "/home/conner/Documents/clubhub-270013-9a21263094f2.json" #Not puttin on github
	}
	
	firebase = pyrebase.initialize_app(config)
	auth = firebase.auth()
	user = auth.sign_in_with_email_and_password("****", "****") # Not putting on github
	db = firebase.database()
	eventList = getTable()
	eventListAcc = eventList[0]
	Team1, Team2 = getTeamIds(eventListAcc, db, user)
	AddToActive(Team1, Team2, eventListAcc, user, db)


def getTable():
	req = Request('https://www.dublingaa.ie/competitions/fixtures/125275', headers={'User-Agent': 'Mozilla/5.0'}) 
	webpage = urlopen(req).read() #
	soup = BeautifulSoup(webpage, 'html.parser') 
	events = [] ## create list of events 
	for div in soup.find_all('div', class_ = 'listing_row'): # navigating to the correct div
		subevent = []
		for subdiv in div.find_all('div', class_ = ('date','time','home','away','venue')) : # extracting only the correct information
			subevent.append(subdiv.string)
			
		events.append(subevent)



	return events
def getTeamIds(eventList, db, user):
	
	team1 = event[3]
	team2 = event[4]
	
	clubs = db.child("Club").get(user['idToken']).val()
	teamAndId = ["TeamDoesNotOwnTheApp", "empty"]
	team2AndId = ["TeamDoesNotOwnTheApp", "empty"]
	for item in clubs:
		inner  = clubs[item]
		if inner['club'] == team1:
			teamAndId = [team1, item]
		elif inner['club'] == team2:
			team2AndId = [team2, item]
	return (teamAndId, team2AndId)
def AddToActive(team1, team2, event, user, db):
	
	date = event[0]
	date = date.split(" ")
	month = monthToNumber(date[0])
	dateCom = [month]
	dateCom.append(date[1])
	dateCom.append(date[2])
	dateCom.join(" ", "/")
	print(Datecom)

	
	location = event[2]
	team1Name = team1[0]
	team1Id = team1[1]
	team2Name = team2[0]
	team2id = team2[1]
	#clubs = db.child("Active").push({"date": date, "teamOneID": team1Id, "teamOneName": team1Name, "teamTwoID": team2id, "teamTwoName": team2Name, "venue": location}, user['idToken'])

def monthToNumber(month):

	Dict = {'Jan' : 1,'Feb' : 2,'Mar' : 3,'Apr' : 4,'May' : 5,'Jun' : 6,'Jul' : 7,'Aug' : 8,'Sep' : 9, 'Oct' : 10,'Nov' : 11,'Dec' : 12
	}
	return Dict[month]

if _name_ == "_main_":
    main()