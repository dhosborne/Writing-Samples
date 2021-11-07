/******************************************************************************

	User.h

	Best Team Ever
	Programmers: Fernando Cruz & Derek Osborne
	Date: November 21, 2016
	Programming Fundementals I
	Instructor: Korinne Caruso
	Standard: C++11

*******************************************************************************/
#ifndef User_H
#define User_H

#include <string>
#include <vector>
#include "Log.cpp"

class User
{
private:
	int 	account_number 		= 0;
	string 	last_name 			= "";
	string 	first_name 			= "";
	double 	checking_balance 	= 0.0;
	double 	savings_balance 	= 0.0;
	string 	file_path 			= "";
	string 	log_path			= "";
	int     language            = 0;

public:


//***********************************************************************************
//																					*
//						Member Data manipulation functions							*
//																					*
//		No changes made with these functions will have any effect on the database.	*
//																					*
//		To make changes permanent call the commit() function immediately following	*
//		usage of these functions.													*
//																					*
//***********************************************************************************
/******************************************************************************

	User Default Constructor Function

	This function is the basic "recipe" for creation of the User class object.

	Return Value
	------------
	Creates an object of User type


	Function Parameters
	-------------------
	file 	       string 	  value 		file path to the User record file
	language_pref  int        value

	Local Variables
	---------------
	log path     string  append .log suffix to file, to make log path
******************************************************************************/
	User(string file, int language_pref);
	~User();

/******************************************************************************

	User Load Account Details Function

	Opens the users record file and loads details to local variables

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	file 	string 	value 		file path to the ATM master log

	Local Variables
	---------------
	fname    string   temp variable for user first name
	lname    string   temp variable for user last  name
	check    double   temp variable for user check account balance
	save     double   temp variable for user savings account balance
******************************************************************************/
	void loadAccountDetails(int account);

/******************************************************************************

	User Set Account Number Function

	Set the user account number in member Variables

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	Number 	int 	value  the new account number to be stored

	Local Variables
	---------------
	NONE
******************************************************************************/
	void setAccountNumber(int number);

/******************************************************************************

	User Get Account Number Function

	Get the user account number in member Variables

	Return Value
	------------
	account_number int  value 	the current user account number

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	NONE
******************************************************************************/
	int getAccountNumber();

	/******************************************************************************

	User Set Last Name Function

	Set the user last name in member Variables

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	newName	string 	value  the new last name to be stored

	Local Variables
	---------------
	NONE
******************************************************************************/
	void setLastName(string newName);


/******************************************************************************

	User Get Last Name Function

	return the user last name in member Variables

	Return Value
	------------
	last_name  string 	value  the last name in member variables

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	NONE
******************************************************************************/
	string getLastName();

/******************************************************************************

	User Set Log Path Function

	Set the path to the users log file in member variables

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	file  string 	value  relative path to the users log file

	Local Variables
	---------------
	NONE
******************************************************************************/
	void setLogPath(string file);



/******************************************************************************

	User Get Log Path Function

	return the log path to the users log file

	Return Value
	------------
	log_path  string  value  path to the users log file

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	NONE
******************************************************************************/
	string getLogPath();

/******************************************************************************

	User Set First Name Function

	Set the users first name in member variables

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	newName  string 	value  new fist name of user

	Local Variables
	---------------
	NONE
******************************************************************************/
	void setFirstName(string newName);


/******************************************************************************

	User Get First Name Function

	get the users first name in member variables

	Return Value
	------------
	first_name  string  value  user first name in variables

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	NONE
******************************************************************************/
	string getFirstName();


/******************************************************************************

	User Set Checking Balance Function

	Update the users checking balance by the value of money.  If the number
	is negative (ie., withdrawal) function will check to see if the account
	has enough money to facilitate the transaction.  Deposits are streamlined.

	Successful completion of this function is dependant on the commit function
	returning true, if commit is successful the transaction is logged.  Else
	the transaction is reversed in the users account that this function returns
	false

	Return Value
	------------
	bool true   if there is enough money in users account, and commit is successful

	Function Parameters
	-------------------
	money 	double 		value   the amount of money +/- to process

	Local Variables
	---------------
	old_checking_balance    double   the balance of the checking account prior
										to attempting the transaction
******************************************************************************/
	bool setCheckingBalance(double money);

/******************************************************************************

	User Get Checking Balance Function

	get the users current checking balance

	Return Value
	------------
	checking_balance   double   the users current checking balance

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	NONE
******************************************************************************/
	double getCheckingBalance();



/******************************************************************************

	User Set Savings Balance Function

	Update the users savings balance by the value of money.  If the number
	is negative (ie., withdrawal) function will check to see if the account
	has enough money to facilitate the transaction.  Deposits are streamlined.

	Successful completion of this function is dependant on the commit function
	returning true, if commit is successful the transaction is logged.  Else
	the transaction is reversed in the users account that this function returns
	false

	Return Value
	------------
	bool true   if there is enough money in users account, and commit is successful

	Function Parameters
	-------------------
	money 	double 		value   the amount of money +/- to process

	Local Variables
	---------------
	old_checking_balance    double   the balance of the savings account prior
										to attempting the transaction
******************************************************************************/
	bool setSavingsBalance(double money);


/******************************************************************************

	User Get Checking Balance Function

	get the users current checking balance

	Return Value
	------------
	savings_balance   double   the users current savings balance

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	NONE
******************************************************************************/
	double getSavingsBalance();


/******************************************************************************

	User Set File Path Function

	sets the file path of the users account file in member variables

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	file  	string   value  the new path to the user account file

	Local Variables
	---------------
	NONE
******************************************************************************/
	void setFilePath(string file);


/******************************************************************************

	User Get File Path Function

	gets the file path of the users account file in member variables

	Return Value
	------------
	file  	string   value  the new path to the user account file

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	NONE
******************************************************************************/
	string getFilePath();


/******************************************************************************

	User Transaction Type Function

	Determines the type of transaction based on the sign of the double money
	provided as arg.

	Return Value
	------------
	type  	string  value  the type of transaction detected

	Function Parameters
	-------------------
	money   double  value   amount of money being processed

	Local Variables
	---------------
	type  	string  	the value to be returned
******************************************************************************/
	string transType(double money);


/******************************************************************************

	User Funds Available Function

	test account balance against dollar amount to determine if the account has
	enough funds to service the request

	Return Value
	------------
	bool	true	if the account is positive and the funds do not exceed that
					account amount

	Function Parameters
	-------------------
	funds_available  double  value  the amount of money currently in the account
	requested_funds  double  value  the amount of money being requested

	Local Variables
	---------------
	NONE
******************************************************************************/
	bool fundsAvailable(double funds_available, double requested_funds);

/******************************************************************************

	User Reset User Account Function

	resets the users accoun to the defined default Values

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
	void resetUserAccount();

	/******************************************************************************

	User Log Transaction Function

	writes the provided transaction detals to the users log account

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	type 	string   value  the type of transaction (deposit/ withdrawal)
	account string   value  the account being acted on (checking/savings)
	amount  double   value  the amount being +/- from account
	total   double   value  the total left in the account after transaction

	Local Variables
	---------------
	NONE
******************************************************************************/
	void logTransaction(string type, string account,  double amount, double total);

/******************************************************************************

	User Get Account Logs Function

	Reads the users account logs in by line, first to a Log object, then the log
	object is added to a vetor of logs.  Then the log list is returned

	Return Value
	------------
	log_ list    vector< Log >  value  the list of all log objects created during
										the read process

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	type 	string   value  the type of transaction (deposit/ withdrawal)
	account string   value  the account being acted on (checking/savings)
	amount  double   value  the amount being +/- from account
	total   double   value  the total left in the account after transaction
******************************************************************************/
	vector< Log > getAccountLogs();


/******************************************************************************

	User Read Log File Function

	Iterates over aquired log object list and calls the printLog member function.
	Process creates a print out of the users transaction at the console

	Return Value
	------------
	VOID

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	log_ list    vector< Log >  value  the list of all log objects created during
										the read process
******************************************************************************/
	void readLogFile();

//***********************************************************************************
//																					*
//								****WARNING****										*
//																					*
//																					*
//			Usage of this function will cause a permanent changes	 				*
//			to the users account data file.			 								*
//																					*
//			Call this function ONLY WHEN YOU ARE READY to make your Local 			*
//			account changes permanent.												*
//																					*
//***********************************************************************************


/******************************************************************************

	User Commit Function

	writes whatever is in ALL of the User object member variables, into the
	users account log file.  This is a final commital of any changes since
	the last write, this process cannot be reversed.

	Return Value
	------------
	bool  true   returns true if a successful hadle to the user log file is
								established, after writing has occurred

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	fp   ofstream      file pointer for the users account file
******************************************************************************/
	bool commit();

};

#endif
