/******************************************************************************

	Log.cpp

	Best Team Ever
	Programmers: Fernando Cruz & Derek Osborne
	Date: November 21, 2016
	Programming Fundementals I
	Instructor: Korinne Caruso
	Standard: C++11

*******************************************************************************/
#include "Log.h"

// default constructor and destructor for class
Log::Log(string trans_type, string acct_type, double trans_amount, double acct_total)
{
	type 		 = trans_type;
	account_name = acct_type;
	amount 		 = trans_amount;
	total 		 = acct_total;
}
Log::~Log(){}

// public get/set functions for member vars
string Log::getType()
{
	return type;
}

string Log::getAccount()
{
	return account_name;
}
double Log::getAmount()
{
	return amount;
}

double Log::getTotal()
{
	return total;
}

// handles printing logs to console
void Log::printLog()
{
	cout << setprecision(2) << fixed;
	cout << " | " << setw(10) << left << type
		 << " | " << setw(10) << left << account_name
		 << " | " << setw(10) << right << amount
		 << " | " << setw(10) << right << total
		 << " | " << endl;
}
