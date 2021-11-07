/******************************************************************************

	Main.h

	Best Team Ever
	Programmers	: Fernando Cruz & Derek Osborne
	Date		: November 21, 2016
	Class 		: Programming Fundementals I
	Instructor	: Korinne Caruso
	Standard 	: C++11

*******************************************************************************/
#ifndef Main_H
#define Main_H

#include <vector>
#include <string>

using namespace std;

// PPD to determine OS and set appropriate clear command
#ifdef _WIN32
	char buffer[4] = "cls";
	const char* clear_var = buffer;
#else
	char buffer[6] = "clear";
	const char* clear_var = buffer;
#endif


/******************************************************************************

	Authenticate User Function

	Checks supplied credentials agains the master user account list. If user
	authentication fails, program keep track of login attempts.  3 failed attemps
	returns a false, or if the master account list shows 3 previous attempts
	occured.  

	Return Value
	------------
	bool true		reutrns true if user account and pin found in master list


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation
	credentials int* 	 pointer    list of credentials supplied by user (acct# & pin#)
	user_list	2DVector reference  list of user account number & pin pairs

	Local Variables
	---------------
	money 		double		temp  to hold money read in from master doc
	stamps 		int 		temp to hold stamps read in from master doc
	deposits 	int 		temp to hold deposts read in from master doc
	deposts_cash double		temp to hold deposit value read in from master doc
******************************************************************************/
bool authenticateUser(int, int*, vector< vector<int> >&);

/******************************************************************************

	Catch Embezzler Function

	Searches users account logs for accounting discrepancies.  Alerts admin
	if suspicious activites are detected

	Return Value
	------------
	


	Function Parameters
	-------------------
	user_list	2DVector reference  list of user account number & pin pairs

	Local Variables
	---------------
	user  		     User* 		  pointer for referencing current User object during 
							      inspection
	log_list	     vector<Log>  a list of users transaction logs
	log_list_size	 int          holds size of list, used to check list is empty
	it               *iterator    iterator pointer for reading log list
	checking_records vector<Log>  a list to hold checking records
	audit            vector<Log<  a list of flagged logs
	previous_balance Double       holds records info for calculations
	change			 Double       holds records info for calculations
	new_balance      Double       holds records info for calculations
	current_balance  Double       holds records info for calculations
******************************************************************************/
int	catchEmbezzler(vector< vector<int> >&);


/******************************************************************************

	Chose Account Function

	Get the account that the user wants to perform an acount on.
	1 = checking
	2 = savings

	Return Value
	------------
	choice  int  numerical representation for the account the user has chosen


	Function Parameters
	-------------------
	language 	int  	 reference  the users language preference for translation	

	Local Variables
	---------------
	checking const int   checking account = 1
	savings  const int   savings account  = 2
	choice         int   holds the users choice
	valid          bool  true if user chooses a valid menu selection
******************************************************************************/
int chooseAccount(int&);

/******************************************************************************

	Clear Screen Function

	Clears the screen using command based on the OS specific command as chosen
	by the preprocessor.

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	NONE
******************************************************************************/
void clearScreen();


/******************************************************************************

	Embezzle-O-Matic Function

	Evil function that steals pennies from user accounts chooses an account at 
	random from the account list then deducts one penny from the checking account.
	The covers it's tracks by overwriting the users logs without the log showing
	it nefarious deed.

	Return Value
	------------
	EVIL VOID

	Function Parameters
	-------------------
	user_list  2DVector<int>  reference   EVIL list of user account and pin #'s	

	Local Variables
	---------------
	random_mark          int 	choose a random number in the range of users list 
								length
	mark_account__number int    hold account number of mark
	mark_file            string path to marks account file
	mark                 User*  pointer to User object for mark
	mark_logs      Vector<Log>	list of user logs
	it             *iterator    iterator for user log list
	fp             osstream     object for reading log file

******************************************************************************/
void embezzleOmatic(vector< vector<int> >&);


/******************************************************************************

	Get Valid Double Function

]	Get a validated input of Double type from user.

	Return Value
	------------
	value  Double  the validated double provided by user

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	value  double    placeholder for user provided double
******************************************************************************/
double getValidDouble();

/******************************************************************************

	Get Valid Integer Function

]	Get a validated input of integer type from user.

	Return Value
	------------
	value  int  the validated integer provided by user

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	value  int    placeholder for user provided integer
******************************************************************************/
int getValidInt();


/******************************************************************************

	Get Valid Menu Choice Function

	gets a users input choice and checks that it is in valid range of a set
	of provided values, representing menu choices.  Will repeatedly ask for new
	input if the value provided is not in range.

	Return Value
	------------
	bool  true   if value is in range.

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	NONE
******************************************************************************/
bool getValidMenuChoice(int&, int, int);


/******************************************************************************

	Get User Credentials Function

	Gets an array of user credentials and returns a pointer to it

	Return Value
	------------
	int*  pointer    pointer to an array of int, containing user creds

	Function Parameters
	-------------------
	language int value  the users chosen language
	low      int value  the low choice for menu
	high     int value  the high choice for menu

	Local Variables
	---------------
	creds  static int []  an array of ints contaning the users credentials
******************************************************************************/
int* getUserCredentials(int&);

/******************************************************************************

	Get User List Function

	Creates and 2D Vector array with all the users accounts and credentials
	from the master user list

	Return Value
	------------
	user_list vector< vector<int> >		list of all the users from master list

	Function Parameters
	-------------------
	language  int   reference  users chosen langugae preference	

	Local Variables
	---------------
	fp  	  ifstream 			file pointer object for reading in user list
	user_list 2D Vector<int>	list of users in the master user list
******************************************************************************/
vector< vector<int> >   getUserList();

/******************************************************************************

	Power Down ATM Function

	This is ad-hoc function for exiting this program,  upon affirmitive 
	verification the bool returned should be used to set the power_system_down
	global variable; thereby simulating a shutdown of the ATM.


	Return Value
	------------
	bool true			user confirms that they want to shutdown the system

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	choice int  user choice from confirmation dialog
******************************************************************************/
bool powerDownAtm();

/******************************************************************************

	Print ATM Status Function

	Maintenance function to print the ATM object information to the screen

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	NONE
******************************************************************************/
void printAtmStatus();


/******************************************************************************

	Print Maintenance Function

	Maintenance function to print the maintenance function menu

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	NONE
******************************************************************************/
void printMaintenanceMenu();

/******************************************************************************

	Print User Balance Function

	Print the User object information to the screen

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	language  int  value  users language preference selection

	Local Variables
	---------------
	NONE
******************************************************************************/
void printUserBalances(int &language);


/******************************************************************************

	Print Welcome Screen Function

	Print the welcome graphic to the screen

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	NONE
******************************************************************************/
void printWelcomeScreen();



/******************************************************************************

	Process Transaction Function

	Process transactions from the users account based on the account selection
	provided

	Return Value
	------------
	bool true   if associated User member function successful

	Function Parameters
	-------------------
	account  int    value      determines the account the user wants to act on
	dollars  double value      the cash amount to +/- from the account selected
	language int    reference  the users selected language preference

	Local Variables
	---------------
	NONE
******************************************************************************/
bool processTransaction(int, double, int&);




/******************************************************************************

	Reset User Accounts Function

	Returns all user accounts to a defualt initial state as defined in User
	class.

	Return Value
	------------
	bool true	if ATM member function is successful

	Function Parameters
	-------------------
	user_list  2DVector<int>  reference   list of all user accounts and credentials
	language   int            reference   users chosen language prefernce

	Local Variables
	---------------
	user  User*   pointer for use in loading user accounts
******************************************************************************/
void resetUserAccounts(vector< vector<int> > & );






/******************************************************************************

	Stringify Double Function

	Uses stringstream to convert a double to a string.  Complier safe (curse you mingw).

	Return Value
	------------
	ss  string  the converted string

	Function Parameters
	-------------------
	i  double  reference  reference to the double to be converted

	Local Variables
	---------------
	ss stringstream  object to stream the double for conversion
******************************************************************************/
string stringifyDouble(double&);






/******************************************************************************

	Stringify INt Function

	Uses stringstream to convert an int to a string.  Complier safe (curse you mingw).

	Return Value
	------------
	ss  string  the converted string

	Function Parameters
	-------------------
	i  int  reference  reference to the double to be converted

	Local Variables
	---------------
	ss stringstream  object to stream the int for conversion
******************************************************************************/
string stringifyInt(int&);

/******************************************************************************

	Wait For Input Function

	Os safe pausing method.  Function will sit and wait for user to press enter.
	Used in to prevent print screen from clearing data from the screen before
	the user has time to see it.

	Return Value
	------------
	language int reference  users language preference selection

	Function Parameters
	-------------------
	NONE	

	Local Variables
	---------------
	NONE
******************************************************************************/
void waitForInput(int&);

/******************************************************************************

	Write User List Function

	User to write changes to the master users list.  primarily used by
	authenticate user function to track unsuccessful login attemps to an 
	account

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	user_list  2D Vector<int>  master list of all users with desired changes
								already made.	

	Local Variables
	---------------
	of  ofstream   outfile stream to write user list
******************************************************************************/
void writeUserList(vector< vector<int> >&);
#endif



































