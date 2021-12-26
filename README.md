# CFM Address Book
 Using the MVC design to make a functioning address book in Java
### Project Overview
---
- Console

Manages all of the user interface with the database.
- Backend

Controls all of the interactions with the database
- Database

Stores all information for the application (Addresses and Users)

### Setup & Usage
---
##### Console
1. If you would like to connect to a database on your local machine: make sure that you have mongodb running on your localhost. Directions for installing mongodb can be found [here](https://docs.mongodb.com/manual/installation/). If you would rather connect to a remote database, make sure to have your connection string ready
2. Download the latest release containing the CFM.jar file or clone the repo and build the project
3. Once you have a runnable jar, enter the command ```java -jar CFM.jar``` into your command line or terminal window
4. The application will now take you through the steps of creating an account and connecting to a database either on your localhost or on a remote server
5. You are now able to run the application, and it will remember your connection preferences for next time.
##### Website Concept
1. Clone the repository
2. Open the index.html file
3. Look around
### Troubleshooting
---
If there is any error connecting to the database try the following:
1. Check your internet connection
2. Make sure you have mongodb installed (if you are using local machine for database)
3. Make sure that the connection string is correct
4. Make sure the connection string folder is not corrupted (can be found under Documents/CFM-Address-Book)
