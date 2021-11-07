/******************************************************************************

	Log.h

	Best Team Ever
	Programmers: Fernando Cruz & Derek Osborne
	Date: November 21, 2016
	Programming Fundementals I
	Instructor: Korinne Caruso
	Standard: C++11

*******************************************************************************/
#ifndef Log_H
#define Log_H

#include <string>

using namespace std;

class Log
{
private:
	string type 		= "";	// action type of transaction deposit/withdrawal
	string account_name = "";	// name of account
	double amount 		= 0.00;	// amout of transaction processed
	double total  		= 0.00;	// total in account after transaction
public:

/******************************************************************************

	Log Default Constructor Function

	This function is the "recipie" for creating the Log object.

	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	trans_type   string  value  type of transaction (deposit/withdrawal)
	acct_type 	 string  value  type of account     (checking/savings)
	trans_amount double  value  amount of transaction negative or positive
	acct_type 	 double  value  amount in account after transaction

	Local Variables
	---------------
	type 		 string private member	action type of transaction deposit/withdrawal
	account_name string private member	name of account
	amount       double private member	amount of transaction processed
	total 		 double private member	total in account after transaction
******************************************************************************/
	Log(string trans_type, string acct_type, double trans_amount, double acct_total);
	~Log();

/******************************************************************************

	Log Get Type Function

	get the value of memeber variable type

	Return Value
	------------
	type string 	type of transaction


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	type string private member	action type of transaction deposit/withdrawal
******************************************************************************/
	string getType();

/******************************************************************************

	Log Get Account Function

	get the value of memeber variable account_name

	Return Value
	------------
	account_name string 	name of account


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	account_name  string  private member	name of account
******************************************************************************/
	string getAccount();

/******************************************************************************

	Log Get Amount Function

	get the value of memeber variable amount

	Return Value
	------------
	amount double 	amount of transaction

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	amount   double private member	 amount of transaction processed
******************************************************************************/
	double getAmount();

/******************************************************************************

	Log Get Total Function

	get the value of memeber variable total

	Return Value
	------------
	total double 	amount of transaction

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	total   double private member	 total in account after transaction
******************************************************************************/
	double getTotal();


/******************************************************************************

	Log Print Log Function

	Prints the information in a Log object to the console

	Return Value
	------------
	NONE

	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	NONE
******************************************************************************/
	void printLog();
};
#endif
