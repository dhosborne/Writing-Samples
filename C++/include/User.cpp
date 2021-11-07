/******************************************************************************

	User.cpp

	Best Team Ever
	Programmers: Fernando Cruz & Derek Osborne
	Date: November 21, 2016
	Programming Fundementals I
	Instructor: Korinne Caruso
	Standard: C++11

*******************************************************************************/

#include <string>
#include <iostream>
#include <fstream>
#include <iomanip>
#include <cmath>	//needed for abs function
#include "User.h"

using namespace std;


// defaults  values for account resets
const double DEFAULT_CHECKING_BALANCE = 1000.00;
const double DEFAULT_SAVINGS_BALANCE  = 2000.00;

// default constructor and destructor functions
User::User(string file, int language_pref)
{
	language = language_pref;
	setFilePath(file);
	string log_path = file + ".log";
	setLogPath(log_path);
}
User::~User(){}


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


// gets account details from a file named after the account number of user
void User::loadAccountDetails(int account)
{
	// open instream object to load user data
	ifstream fp;

	fp.open(getFilePath());

	if (fp.good())
	{
		int i = 0;
		fp >> i;  // read in the account number from file

		// if the account number read in matches the requested
		// account number proceed,  else fail and return
		if ( i == account )
		{
			string fname = "";  	// first name
			string lname = "";  	// last name
			double check = 0.00;   	// checking balance
			double save  = 0.00;	// savings balance

			fp	>> fname
				>> lname
				>> check
				>> save;

			// populate the rest of the account information
			// from infile
			account_number 		= account;
			first_name 			= fname;
			last_name 			= lname;
			checking_balance 	= check;
			savings_balance 	= save;

		}
		else
		{
			cout << "(DEBUG) Account Number invalid, please try again" << endl;
		}

		fp.close();
	}
	else
	{
		cout << "(DEBUG) Account Not Found, check credentials" << endl;
	}
}

// get and set local account number information
void User::setAccountNumber(int number)
{
	account_number = number;
}
int User::getAccountNumber()
{
	return account_number;
}

// get and set local last name variable
void User::setLastName(string newName)
{
	last_name = newName;
}
string User::getLastName()
{
	return last_name;
}

void User::setLogPath(string file)
{
	log_path = file;
}

string User::getLogPath()
{
	return log_path;
}


// get and set local first name variable
void User::setFirstName(string newName)
{
	first_name = newName;
}
string User::getFirstName()
{
	return first_name;
}

// update the class variable checking
// uses addition, if removing money, float must be negative, calls commit to update
//  user file and logs the transaction if commit is successful
bool User::setCheckingBalance(double money)
{

    // store old checking balance in case commit fails
    double old_checking_balance = getCheckingBalance();

    // check if funds are available in users account, if so proceed with
    // transaction
    if (fundsAvailable(old_checking_balance, money))
    {
        // update checking balance in memory
        checking_balance += money;

        // if write successful log, else warn and revert checking balance
        if (commit())
        {
            logTransaction(transType(money), "checking", money, getCheckingBalance());
            return true;
        }
        else
        {
            cout << "Unable to process your transaction at this time" << endl
                 << "We are sorry for any inconvenience this may have caused" << endl;
            // not processed, restore old balance
            setCheckingBalance(old_checking_balance);
        }
    }
    else
    {
        cout << "Insufficient funds to process this transaction" << endl;
    }

    return false;
}


// get the balance of users checking account from class var int
double User::getCheckingBalance()
{
	return checking_balance;
}



// get and set savings account balance variable
bool User::setSavingsBalance(double money)
{
    // store old balance in case commit fails
    double old_savings_balance = getSavingsBalance();

    if(fundsAvailable(old_savings_balance, money))
    {
        savings_balance += money;

        if (commit())
        {
            logTransaction(transType(money), "savings", money, getSavingsBalance());
            return true;
        }
        else
        {
        	// warn user and undo changes to local account variable
            cout << "Unable to process your transaction at this time" << endl
                 << "We are sorry for any inconvenience this may have caused" << endl;
            setSavingsBalance(old_savings_balance);
        }
    }
    else
    {
        cout << "Insufficient funds to process this transaction" << endl;
    }

    return false;

}
double User::getSavingsBalance()
{
	return savings_balance;
}


// modify file path variable
void User::setFilePath(string file)
{
	file_path = file;
}
string User::getFilePath()
{
	return file_path;
}

// determine transaction type for logging
string User::transType(double money)
{
	string type;

	if (money < 0.00)
	{
		type = "withdrawal";
	}
	else
	{
		type = "deposit";
	}

	return type;
}

bool User::fundsAvailable(double funds_available, double requested_funds)
{

	// if requested funds is negative the transaction type is
	// a withdrawal and must be tested
	if (requested_funds < 0)
	{
		// check if there is enough money in the account
		if (funds_available < 0 || funds_available < abs(requested_funds) )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		// transaction type was a deposit and this test is not applicable
		return true;
	}
}

void User::resetUserAccount()
{
	checking_balance = DEFAULT_CHECKING_BALANCE;
	savings_balance  = DEFAULT_SAVINGS_BALANCE;
	commit();
}





// write transaction to users personal transaction file
void User::logTransaction(string type, string account,  double amount, double total)
{
	ofstream fp;

	fp.open(getLogPath(), ios::app);

    fp  << setprecision(2) << fixed;  // change precision for writing double

	if(fp.good())
	{
		fp << type
		   << " "
		   << account
		   << " "
		   << amount
		   << " "
		   << total 
		   << "\n";
	}

	fp.close();
}

// create a list contaning all account logs pertaining
// to users account
vector< Log > User::getAccountLogs()
{
	// list to hold account logs
	vector< Log > log_list;


	ifstream fp;
	fp.open(getLogPath());

	if (fp.good())
	{
		string type    = "";
		string account = "";
		double	amount = 0.00;
		double total   = 0.00;


		// load log_list with log lines
		while(!(fp.eof()))
		{
			fp >> type >> account >> amount >> total;

			if (type != "")
			{
				Log new_log(type, account, amount, total);

				log_list.push_back(new_log);
			}

			// reset valuse after each log
			type    = "";
			account = "";
			amount  = 0.00;
			total   = 0.00;

		}
	}

	fp.close();

	return log_list;

}

// iterate over the log file list and print log of recent transactions
void User::readLogFile()
{

	vector< Log > log_list = getAccountLogs();

	cout << "\t\t Recent Transactions" << endl;
	cout << " _____________________________________________________" << endl;

	for ( auto& log : log_list )
	{
		log.printLog();
	}

	cout << " _____________________________________________________" << endl;
}

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

// commit account changes made in local variables, to the account data file
bool User::commit()
{
	ofstream fp;

	fp.open(getFilePath());

	if (fp.good())
	{
		// writes whitespace separated values to user account
		fp  << account_number
			<< " "
			<< last_name
			<< " "
			<< first_name
			<< " ";

		fp  << setprecision(2) << fixed;  // change precision writing floats

		fp	<< checking_balance
			<< " "
			<< savings_balance;

	}
	else
	{
		cout << "(DEBUG) Could not access the database, please try again" << endl;
		return false;
	}

	fp.close();


	loadAccountDetails(getAccountNumber());

	return true;

}