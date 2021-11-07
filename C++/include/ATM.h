/******************************************************************************

	ATM.h

	Best Team Ever
	Programmers: Fernando Cruz & Derek Osborne
	Date: November 21, 2016
	Programming Fundementals I
	Instructor: Korinne Caruso
	Standard: C++11

*******************************************************************************/
#ifndef ATM_H
#define ATM_H

#include <string>
using namespace std;

// defaul "full" values for the ATM
const int 	 MAX_STAMPS	= 50;
const double MAX_CASH 	= 200000.00;


class ATM
{
private:
	double cash_available   = 0.00;	// cash levels of ATM
	int    stamps_available = 0.00;	// books of stamps in ATM
	int    deposit_count    = 0;	// number of deposits made
	double deposit_amount   = 0;	// total of deposits made
	string file_path        = "";	// path to ATM master file

public:
/******************************************************************************

	ATM Default Constructor Function

	This function is the basic "recipe" for creation of the ATM class object.

	Return Value
	------------
	Creates an object of ATM type


	Function Parameters
	-------------------
	file 	string 	value 		file path to the ATM master log

	Local Variables
	---------------
	NONE
******************************************************************************/
	ATM(string file);
	~ATM();

/******************************************************************************

	ATM Load ATM Details Function

	Reads in the ATM's details from the ATM master file. 

	Return Value
	------------
	VOID


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	money 		double		temp  to hold money read in from master doc
	stamps 		int 		temp to hold stamps read in from master doc
	deposits 	int 		temp to hold deposts read in from master doc
	deposts_cash double		temp to hold deposit value read in from master doc
******************************************************************************/
	void 	loadATMDetails();

/******************************************************************************

	ATM Set Cash Available Function

	This functions updates the amount of cash available in the ATM object.

	Return Value
	------------
	VOID


	Function Parameters
	-------------------
	money 	double  	value 		amount of money to +/- the total cash by

	Local Variables
	---------------
	private cash_available			ATM member variable cash_available
******************************************************************************/
	void 	setCashAvailable(double money);

/******************************************************************************

	ATM GET Cash Available Function

	This functions returns the amount of cash available in the ATM object.

	Return Value
	------------
	cash_available    double	Value 	amount of money available in the ATM obj


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	private  double cash_available			ATM member variable cash_available
******************************************************************************/
	double 	getCashAvailable();

	/******************************************************************************

	ATM Set Stamps Available Function

	This functions updates the amount of stamps available in the ATM object.

	Return Value
	------------
	VOID


	Function Parameters
	-------------------
	stamps 	int  	value 			number of stamp books to +/- from the total

	Local Variables
	---------------
	private int stamps_available	ATM member variable total stamps available
******************************************************************************/
	void 	setStampsAvailable(int stamps);

/******************************************************************************

	ATM Get Stamps Available Function

	This functions updates the amount of stamps available in the ATM object.

	Return Value
	------------
	stamps 	int  	value 			total number of stamp books available


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	private int stamps_available	ATM member variable total stamps available
******************************************************************************/	
	int  	getStampsAvailable();

/******************************************************************************

	ATM Set Deposit Count Function

	This functions updates the amount of deposits made to the ATM object.

	Return Value
	------------
	VOID


	Function Parameters
	-------------------
	deposit 	int  	value 		number of deposits to +/- from the total

	Local Variables
	---------------
	private int deposit_count		ATM member variable total deposits made
******************************************************************************/
	void 	setDepositCount(int deposit);


/******************************************************************************

	ATM Get Deposit Count Function

	This functions updates the amount of deposits made to the ATM object.

	Return Value
	------------
	deposit 	int  	value 		number of deposits to +/- from the total 


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	private int deposit_count		ATM member variable total deposits made
******************************************************************************/	
	int  	getDepositCount();

/******************************************************************************

	ATM Set Deposit Amount Function

	This functions updates the amount of a depoist made to the ATM object.

	Return Value
	------------
	VOID


	Function Parameters
	-------------------
	deposit   double  	value 		amount of deposit to +/- from the total

	Local Variables
	---------------
	private double deposit_amount	ATM member variable total deposits amount
******************************************************************************/
	void 	setDepositAmount(double deposit);


/******************************************************************************

	ATM Get Deposit Amount Function

	This functions returns the amount of depoists made to the ATM object.

	Return Value
	------------
	double deposit_amount 			dolar amount of depoists in the ATM


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	private double deposit_amount	ATM member variable total deposits amount
******************************************************************************/
	double 	getDepositAmount();

/******************************************************************************

	ATM Set File Path Function

	This functions updates the path to the ATM Master record file

	Return Value
	------------
	VOID


	Function Parameters
	-------------------
	file 	string 	value 		relative path to the atm master file

	Local Variables
	---------------
	private string 	file_path	ATM member variable 
******************************************************************************/
	void 	setFilePath(string file);

/******************************************************************************

	ATM Set File Path Function

	This functions returns the path to the ATM Master record file

	Return Value
	------------
	file 	string 	value 		relative path to the atm master file


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	private string 	file_path	ATM member variable 
******************************************************************************/	
	string 	getFilePath();
	

/******************************************************************************

	ATM Service Function

	Zeroize the deposit amount and count, and reset the Cash and stamps, 
	available amounts to that of the constants defined in ATM.h.
	If, this function fails, all values are reverted to last value.

	Return Value
	------------
	bool true		true if writing the master file is successful.


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	current_deposit_count 	int 	the deposit cout before reset attempted
	current_deposit_amount	double	the cash amount of deposits before attempt
	current_cash 			double	the amount of cash in ATM before attempt
	current_stamps 			int 	the amount of stamps in ATM before attempt

******************************************************************************/
	bool 	service();

/******************************************************************************

	ATM Process Deposit Function

	Handles the addition of a deposit to the count and amount member functions.
	If process fails, reverts count and amount to previous values.
	All deposits are made to checking account.  Only one deposit may be made
	at a time.

	Return Value
	------------
	bool true				true if writing the master file is successful.


	Function Parameters
	-------------------
	dollars double value 	the amount of a single deposit being made

	Local Variables
	---------------
	NONE


******************************************************************************/	
	bool 	processDeposit(double dollars);

/******************************************************************************

	ATM Process Withdrawal Function

	Handles the despensing of funds from the ATM. If master document writing 
	fails, revert to last amount. 

	Return Value
	------------
	bool true		true if writing the master file is successful.


	Function Parameters
	-------------------
	dollars	double 	value 		the amount of cash to be removed from ATM

	Local Variables
	---------------
	current_cash  double  		the amount of cash before attempting removal

******************************************************************************/	
	bool 	processWithdrawl(double dollars);

/******************************************************************************

	ATM Process Stamp Purchase Function

	Handles the purchase of stamps from the ATM.  If ATM master document update
	fails, stamps reverted to previous amount.

	Return Value
	------------
	bool true		true if writing the master file is successful.


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	current_stamps  int  		the amount of stamps before attempting removal

******************************************************************************/
	bool 	processStampPurchase();

/******************************************************************************

	ATM Commit Function

	Afraid of commitment?  This is not the function for you.
	This function handles writing the Private ATM member variables to the 
	ATM master record.  This process simulates commiting a transaction. 

	Return Value
	------------
	bool	true		returns true if writing file is successful


	Function Parameters
	-------------------
	NONE

	Local Variables
	---------------
	ofstream  fp   		file pointer object for opening and writing the master
						file.
******************************************************************************/
	bool 	commit();

};

#endif
