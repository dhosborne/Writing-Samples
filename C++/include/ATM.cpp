/******************************************************************************

	ATM.cpp

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
#include "ATM.h"



ATM::ATM(string file)
{
	setFilePath(file);
	loadATMDetails();
}
ATM::~ATM(){}



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





void ATM::setCashAvailable(double money)
{
	cash_available += money;
}


double ATM::getCashAvailable()
{
	return cash_available;
}


void ATM::setStampsAvailable(int stamps)
{
	stamps_available += stamps;
}


int ATM::getStampsAvailable()
{
	return stamps_available;
}


void ATM::setDepositCount(int deposit)
{
	deposit_count += deposit;
}


int ATM::getDepositCount()
{
	return deposit_count;
}


void ATM::setDepositAmount(double deposit)
{
	deposit_amount += deposit;
}


double ATM::getDepositAmount()
{
	return deposit_amount;
}


void ATM::setFilePath(string file)
{
	file_path = file;
}


string ATM::getFilePath()
{
	return file_path;
}





bool ATM::service()
{
	// store current deposit count for processing verification
	int current_deposit_count  	  = getDepositCount();
	double current_deposit_amount = getDepositAmount();
	double current_cash           = getCashAvailable();
	int    current_stamps         = getStampsAvailable();

	// inform user
	cout << "(Maint) Processing deposits ... " << endl;

	// clear deposits
	deposit_count  = 0;
	deposit_amount = 0.0;


	cout << "(Maint) Processing cash ... " << endl;

	cash_available = MAX_CASH;

	cout << "(Maint) Processing stamps ... " << endl;

	stamps_available = MAX_STAMPS;

	// try to commit changes, if failure revert to count and amount back, warn user
	if (commit())
	{
		return true;
	}
	else
	{
		// revert atm to last good state
		deposit_count    = current_deposit_count;
		deposit_amount   = current_deposit_amount;
		cash_available   = current_cash;
		stamps_available = current_stamps;

		// check if reversion was good
		if (getDepositCount()    == current_deposit_count &&
			getDepositAmount()   == current_deposit_amount &&
			getCashAvailable()   == current_cash &&
			getStampsAvailable() == current_stamps)
		{
			cout << "(Maint) Reversion Successful" << endl;
		}
		else
		{
			cout << "(Maint) Reversion failed, manual override required" << endl
				 << "Cash on hand =\t" << current_cash << endl
				 << "Number of deposits =\t " << current_deposit_count << endl
				 << "Deposit Amount =\t" << current_deposit_amount << endl;
		}

		return false;
	}

	// nothing went wrong, return true
	return true;
}


bool ATM::processDeposit(double dollars)
{
	setDepositCount(1);
	setDepositAmount(dollars);

	if (commit())
	{
		return true;
	}
	else
	{
		// commit failed, revert local deposit variables
		setDepositCount(-1);
		setDepositAmount(dollars * -1.00);
		return false;
	}
}


bool ATM::processWithdrawl(double dollars)
{
	double current_cash = getCashAvailable();

	setCashAvailable(dollars);

	// if commit successful inform user, else revert cash and warn user
	if (commit())
	{
		return true;
	}
	else
	{
		// revert local available cash variable
		cash_available = current_cash;
		return false;
	}
}


// handles the purchasing of stamps from the atm
// input: none
// output: update memeber variables
// return: true if successful
bool ATM::processStampPurchase()
{
	int current_stamps = getStampsAvailable();

	setStampsAvailable(-1);

	if (commit())
	{
		// success
		return true;
	}
	else
	{
		// write failed, revert local stamp count
		setStampsAvailable(current_stamps);
		return false;
	}
}


void ATM::loadATMDetails()
{
	ifstream fp;

	fp.open(getFilePath());

	if (fp.good())
	{
		double money         = 0.00;
		int    stamps        = 0;
		int    deposits      = 0;
		double deposit_cash  = 0.00;

		fp >> money
		   >> stamps
		   >> deposits
		   >> deposit_cash;



		setCashAvailable(money);
		setStampsAvailable(stamps);
		setDepositCount(deposits);
		setDepositAmount(deposit_cash);
	}
}

//***********************************************************************************
//																					*
//								****WARNING****										*
//																					*
//																					*
//			Usage of this function will cause a permanent changes	 				*
//			to the ATM data file.					 								*
//																					*
//			Call this function ONLY WHEN YOU ARE READY to make your Local 			*
//			account changes permanent.												*
//																					*
//***********************************************************************************


bool ATM::commit()
{
	ofstream fp;

	fp.open(getFilePath());

	if (fp.good())
	{
		fp << fixed << setprecision(2);

		fp << cash_available
		   << " "
		   << stamps_available
		   << " "
		   << deposit_count
		   << " "
		   << deposit_amount;
	}
	else
	{
		return false;
	}


	fp.close();

	return true;
}