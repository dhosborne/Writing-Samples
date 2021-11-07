/*

	Smart ATM Source

	Title			: main.cpp
	Programmers		: Fernando Cruz & Derek H Osborne (BEST TEAM EVER)
	Class 			: COSC1436.001FA
	Initial date	: 20161021
	Standard		: C++11


	Description:  Main file for the ATM application.

	Change log: see changelog.docx in documents folder
*/

#include <iostream>
#include <fstream>	// for read/write of files
#include <vector>	// auto resize arrays
#include <string>	// use of string and string functions
#include <iomanip>	// used to format io stream
#include <sstream>	// used to convert vars to strings, to_string not available with mingw compiler
#include <stdlib.h> // system, rand
#include <cmath>	// abs function

#include "include/Main.h"			// function prototypes & pre-processor directives
#include "include/User.cpp"			// defines the user class
#include "include/ATM.cpp"			// defines the atm class
#include "include/Localization.cpp" // translation functions for Localization

using namespace std;

// GLOBAL DATA PATH VARIABLES
const string DATA_PATH   = "data/";						// create a relative base path for data storage
const string USER_DIR    = DATA_PATH + "users/";		// user information storage
const string USER_LIST 	 = USER_DIR + "userdirectory";	// relative location of file that stores user creds
const string ATM_PATH 	 = DATA_PATH + "atmstatus";		// relative location of atm data file

// GLOBAL CONSTANT GLOBAL VARIABLES
const double STAMP_PRICE = 9.80;						// 20 stamps @ $0.49 per stamp
const int MAINTENANCE 	 = 111111111;					// default acct number of maintenance account

// GLOBAL CONASTANTS FOR LANGUAGES
const int   eng = 1,        //for english translations
            spa = 2,        //for spanish translations
            fre = 3,        //for french translations
            chi = 4;        //for chinese translations


// GLOBAL SYSTEM OPERATION VARIABLES
bool power_system_down = false;	// used to indicate if system is on/off
User* user;                     // place holder account object
ATM* atm;						// pointer for atm object
int language;					// place holder for ATM details

int main()
{

	// seed PRNG
	srand(time(NULL));
	
	vector< vector<int> > user_list;


	// create the ATM object
	atm = new ATM(ATM_PATH);


	//***********************************************
	//
	//				PRIMARY ATM LOOP
	//
	//**********************************************

	do
	{

		// Session variables
		int* credentials;					// placeholder for users credentials
		bool user_authenticated = false;	// indicates if user acct# & pin have been validated
		language = 0;						// reset language setting

		// get user info and authenticate, not to exceed three attempts in a session
		while (!(user_authenticated))
		{

			//**********************************************
			//
			//				START INDIVIDUAL SESSION
			//
			//**********************************************
			clearScreen();						 // setup new session
			printWelcomeScreen();				 // print out atm welcome screen
            language = printLangMenu();
            clearScreen();
			credentials = getUserCredentials(language);  // get user acct# and pin
			user_list = getUserList();


			// validate credentials against userdirectory
			if ((user_authenticated = authenticateUser(language, credentials, user_list)))
			{


				//**********************************************
				//
				//		MAINTENANCE INTERFACE MENU
				//	contains all the maintenance functions for
				//  the atm.  Also includes account resets and
				//  embezzlement function
				//
				//**********************************************
				if ((credentials[0]) == MAINTENANCE)
				{

					// enumerate menu choices
					const int show_atm_status	  = 1,
							  power_down 	  	  = 2,
							  service 		  	  = 3,
							  embezzle_O_matic    = 4,
							  catch_embezzler     = 5,
							  reset_user_accounts = 6,
							  exit_maint	  	  = 7;

					int menu_choice = 0;

					do
					{
						// show the user their options then get their choice
						clearScreen();
						printMaintenanceMenu();
						getValidMenuChoice(menu_choice, show_atm_status, exit_maint);


						switch(menu_choice)
						{
							// print the status of funds, deposits and stamps currently
							// loaded into atm class
							case show_atm_status :
							{
								clearScreen();
								printAtmStatus();
								break;
							}

							// set the power_system_down flag, exits the program
							case power_down :
							{
								clearScreen();
								power_system_down = powerDownAtm();
								break;
							}

							// resets the ATM variables to default and writes
							// the changes to file
							case service :
							{
								clearScreen();

								if(atm->service())
								{
									cout << "(MAINT) Process Complete" << endl;
								}
								else
								{
									cout << "(MAINT) Process Failed" << endl;
								}
								break;
							}

							// use PRNG to choose an account to "steal"(deduct) a penny
							// from.  The deducted money is not stored anywhere
							case embezzle_O_matic :
							{
								embezzleOmatic(user_list);
								break;
							}

							// find the account that was embezzled from and restore the money
							case catch_embezzler :
							{
								catchEmbezzler(user_list);
								break;
							}

							// reset all user files to the default checking and savings totals
							// as defined in the user_class
							case reset_user_accounts :
							{
								resetUserAccounts(user_list);
								break;
							}

							//  end the maintenance session
							case exit_maint :
							{
								break;
							}

							default :
							{
								cout << "(MAINT) Command not recognized" << endl;
								break;
							}

						}

						waitForInput(language); // pause to read the case output

					} while (menu_choice != exit_maint && !(power_system_down));

				} // END MAINTENANCE





				//**********************************************
				//
				//				CUSTOMER INTERFACE MENU
				//
				//  contains all the user oriented functions
				//  get cash, deposit cash, buy stamps, fast cash
				//
				//**********************************************

				else
				{
					// enumerate menu choices
					const int fast_cash = 1,
							  balances 	= 2,
							  withdraw 	= 3,
							  deposit  	= 4,
							  other    	= 5,
							  back     	= 6;

					int menu_choice = 0;


					// load user information into User pointer
					// pointers require the use of -> to access member functions
					user = new User(USER_DIR + stringifyInt(credentials[0]), language);
					user->loadAccountDetails(credentials[0]);

					do
					{
						// display account menu
						clearScreen();

						printAccountMenu(language);

						//get menu choice
						getValidMenuChoice(menu_choice, fast_cash, back);



						switch(menu_choice)
						{

							//***********************************************
							//
							//				FAST CASH
							//
							//  the user can elect to withdrawal predetermined
							//  denominations of money from checking account
							//
							//***********************************************
							case fast_cash :
							{
								// enumerate menu choices
								const int twenty   = 1,
										  forty    = 2,
										  one_hund = 3,
										  two_hund = 4,
										  back     = 5;

								// display fast cash menu
								clearScreen();
								printFastCashMenu(language);


								// get menu choice from user
								double money = 0.00;
								int choice   = 0;
								int account  = 1;	// default for checking
								getValidMenuChoice(choice, twenty, back);


								// choose value of money based on user menu choice above
								switch(choice)
								{
									case twenty :
									{
										money = -20.00;
										break;
									}
									case forty :
									{
										money = -40.00;
										break;
									}
									case one_hund :
									{
										money = -100.00;
										break;
									}
									case two_hund :
									{
										money = -200.00;
										break;
									}
									case back :
									{

										break;
									}
									default :
									{
                                            printFashCash(language);
										break;
									}
								}


								// if a valid value was chosen
								if (abs(money) > 0)
								{
									// check if atm has cash to service the request
									double atm_cash_available = atm->getCashAvailable();
									if ( atm_cash_available > 0 && atm_cash_available > abs(money))
									{
										// try to withdraw money from users account
										if (processTransaction(account, money, language))
										{
											// try to deduct money from the atm
											if (atm->processWithdrawl(money))
											{
												// all steps succeeded
												printWithdrawRetrieve(language);
											}
											else
											{
												printUnableToProcess(language);

												// failed to deduct money from atm balance
												// return money to user account
												processTransaction(account, abs(money), language);
											}
										}
										else
										{
											// withdrawal from user account failed
											printUnableToProcess(language);
										}
									}
									else
									{
										// atm has insufficient funds to service withdrawal
										printCantServiceTransaction(language);
									}
								}

								break;
							}





							//**********************************************
							//
							//				Account Balances
							//
							//  Display user account information stored in
							//  user class object
							//
							//**********************************************
							case balances :
							{
								clearScreen();
								printUserBalances(language);
								break;
							}




							//**********************************************
							//
							//				Withdraw Cash
							//
							// Withdraw cash from checking or savings account
							//
							//**********************************************
                            case withdraw :
                            {
                            	clearScreen();
                            	// get account for transaction
								int account = chooseAccount(language);

								// if account returns valid
								if (account > 0)
								{
									clearScreen();

									double dollars = 0.00;
									do
									{
										// dollar amount to withdraw, must be denominations of $20
										printFastCashAmount(language);
										dollars = getValidDouble();
									} while ((static_cast<int>(dollars) % 20) > 0);

									// check if atm has enough money to service transaction
									double atm_cash_available = atm->getCashAvailable();
									if ( atm_cash_available > 0 && atm_cash_available > abs(dollars))
									{
										// negate dollar amount if necessary
										if (dollars > 0.00)
										{
											dollars *= -1.00;
										}

										// withdraw money from users account
										if (processTransaction(account, dollars, language))
										{
											// deduct cash from atm total
											if (atm->processWithdrawl(dollars))
											{
												// all steps succeeded
												printWithdrawRetrieve(language);
											}
											else
											{
												printUnableToProcess(language);

												// failed to deduct money from atm balance
												// return money to user account
												processTransaction(account, abs(dollars), language);
											}
										}
										else
										{
											// failed to deduct money from users account
											printUnableToProcess(language);
										}
									}
									else
									{
										// there is not enough money in the atm to service the withdrawal
										printCantServiceTransaction(language);
									}

								}
								else
								{
									// an invalid account type was supplied
                                	printSelectionInvalid(language);
								}


                                break;
                            }





							//**********************************************
							//
							//				DEPOSIT CASH
							//
							//  deposit cash in account of users choice
							//  update the atm object with the deposit info
							//
							//**********************************************
							case deposit :
							{
								clearScreen();
								// select account to deduct from
								int account = chooseAccount(language);

								if (account > 0)
                                {
                                	clearScreen();
                                	// dollar amount to deduct
                                    printDollarAmount(language);
                                    double dollars = getValidDouble();

                                    // cannot deposit a negative amount, flip the sign
                                    if (dollars < 0.00)
                                    {
                                        dollars = abs(dollars);
                                    }

                                    // if transaction successful, update the ATM
                                    if (processTransaction(account, dollars, language))
                                    {
                                    	// add dollars to deposit totals
                                    	// add 1 to deposit count
                                    	if(atm->processDeposit(dollars))
                                    	{
                                    		// ATM update successful, transaction complete
                                    		printDepositAvail(language);
                                    	}
                                    	else
                                    	{
                                    		// ATM deposit failed, revert user account
                                    		// warn user
                                    		processTransaction(account, (dollars * -1.00), language);
                                    		printUnableToProcess(language);
                                    	}
                                    }
                                    else
                                    {
                                    	// could not deposit money in user account
                                    	printUnableToProcess(language);
                                    }
                                }
                                else
                                {
                                	// an invalid account type was supplied
                                	printSelectionInvalid(language);
                                }
								break;
							}





							//**********************************************
							//
							//				OTHER SERVICES
							//  ancillary user functions.
							//  purchase stamps, decucts from savings account
							//  show user historical logs
							//**********************************************
							case other :
							{
								const int buy_stamps = 1,
										  show_logs  = 2,
										  back       = 3;

								clearScreen();
								printOtherServicesMenu(language);

								int choice = 0;

								getValidMenuChoice(choice, buy_stamps, back);

								switch(choice)
								{
									case buy_stamps :
									{
										if (atm->getStampsAvailable() > 0)
										{
											// stamp purchase is default from checking account
											// stamp price negated to indicate withdrawal
											if (processTransaction(1, STAMP_PRICE * -1.00, language))
											{
												if (atm->processStampPurchase())
												{
													// success
													printWithdrawRetrieve(language);
												}
												else
												{
													// atm purchasefail, return money to user
													printUnableToProcess(language);
													processTransaction(1, STAMP_PRICE, language);
												}
											}
											else
											{
												// account transaction failed
												printUnableToProcess(language);
											}
										}
										else
										{
											// not enough stamps to process order
											printUnableToFill(language);
										}
										break;
									}


									case show_logs :
									{
										user->readLogFile();
										break;
									}
									case back:
									{
										break;
									}
									default :
									{
										printFashCash(language);
										break;
									}
								}
								// buy stamps
								break;
							}




							//**********************************************
							//
							//				GO BACK & DEFAULT
							//
							//**********************************************
							case back :
							{
								printThankYouMsg(language);
								break;
							}
							default:
							{
								printFashCash(language);
								break;
							}
						}

						// BREAK
						waitForInput(language);


					} while (menu_choice != back); // END INDIVIDUAL SESSION LOOP

					delete user; // free memory
				}
			}
			else
			{
				// authentication failed, one try then return to welcome menu
				break;
			}
		}

	} while(!(power_system_down));  // END PRIMARY ATM LOOP



	//**********************************************
	//
	//				CLEAN-UP
	//  free memory and return main
	//
	//**********************************************

	// free memory for atm object
	delete atm;


	cout << "(MAINT) System Powering Off" << endl;
	return 0;
} // END OF MAIN




//*****************************************************************************
//
//				FUNCTION SECTION
//  this section contains all the functions that
//  are not specific to class objects
//
//*****************************************************************************


// check user credentials against master list
bool authenticateUser(int language, int* credentials, vector< vector<int> >& user_list)
{
	// max amounts of log-in attempts
	const int MAX_ATTEMPTS = 3;

	// iterate over vector and search for our clients credentials
 	for (auto& user : user_list)
 	{

		// check if the line contains the credentials
		// supplied by user
		if (credentials[0] == user[0])
		{
			if (user[2] < MAX_ATTEMPTS)  // max attempts have not been exceeded in a previous session
			{
				if (credentials[1] == user[1])  // pin matches pin on file
				{

					// user was successfully authenticated before third attempt
					// reset count to 0 then write to file
					if (user[2] != 0)
					{
						user[2] = 0;
						writeUserList(user_list);

					}
					return true;
				}
				else
				{
					// found account number , but pin does not match
					// close file and return false

					printAccPinFAIL(language);

					// increment the login attempt count
					user[2]++;
					// write out file list with changes
					writeUserList(user_list);
					waitForInput(language); // pause so user can read
					return false;
				}
			}
			else
			{
				// attempts on file already 3, account was suspended previously
				printAccSusp(language);
					 waitForInput(language); // pause so user can read
				return false;
			}

		}
		else
		{
			// id not found, keep looking
			continue;
		}
  	}

	printNotRec(language);
	waitForInput(language); // pause so user can read
	return false; // account number was not found in user directory
}

// find account missing money and replace it
int catchEmbezzler(vector< vector<int> >& user_list)
{

	User* user;	// pointer for iterting through users

	for ( auto& account: user_list)
	{
		// skip over the maintenance account
		if(account[0] != 111111111)
		{
			cout << "Scanning  Account " << account[0] << endl;

			//  load individual user account from file
			user = new User(USER_DIR + stringifyInt(account[0]), 1);
			user->loadAccountDetails(account[0]);

			// load in users logs
			vector < Log > log_list = user->getAccountLogs();
			int log_list_size = log_list.size();

			// if there is more than one log, iterate over the list and
			// check the users account
			if (log_list_size > 0)
			{
				cout << "Building checking logs" << endl;
				vector<Log>::iterator it;

				// storage for checking log enteries
				vector<Log> checking_records;

				// iterate over user logs, if they are checking entries
				// add them to the checking records list
				for(it = log_list.begin(); it != log_list.end(); it++)
				{
					if (it->getAccount() == "checking")
					{
						Log log = *it;
						checking_records.push_back(log);
					}
				}

				// create a list to hold questionable records, will only be returned if
				// there are more then the default messages
				vector<string> audit;
				audit.push_back("Audit for account " + stringifyInt(account[0]) + "\n");
				audit.push_back("Checking list");

				cout << "Auditing checking logs" << endl;
				// iterate over the checking records list and check for accounting
				// discrepancies.  If caculations are incorrect, the game is afoot
				for(it = checking_records.begin(); it != checking_records.end(); it++)
				{
					// read all records stopping short of the last record
					// check the current record against the last records
					// final balances.  Last balance - current transaction =
					// current total.  Do this for all but the last record
					if ( it != checking_records.end() - 1 )
					{
						double previous_balance = it->getTotal();
						double change           = (it+1)->getAmount();
						double new_balance      = (it+1)->getTotal();


						if ((previous_balance += change) != new_balance)
						{

							string record = stringifyDouble(previous_balance);

							if (change > 0)
							{
								record += " + ";
							}

							record += stringifyDouble(change)
									  + " = "
									  + stringifyDouble(new_balance)
									  + " (Alert)";

							audit.push_back(record);
						}


					}
					else
					{
						// checks the last record against the current balance for discrepancy
						double current_balance = user->getCheckingBalance();
						double new_balance = it->getTotal();

						if (current_balance - new_balance != 0.00)
						{
							string record = "new balance : " + stringifyDouble(new_balance)
								+ " checking balance : " + stringifyDouble(current_balance)
								+ " (ALERT)\n" ;

							audit.push_back(record);

						}

					}
				}

				// if any suspect records were added to the audit list, iterate over it
				// and print out the record
				if (audit.size() > 2)
				{
					for (vector<string>::iterator i = audit.begin(); i != audit.end(); ++i)
					{
						cout << *i << endl;
					}
				}
				else
				{
					// nothing concerning was found
					cout << "No discrepancies found in " << account[0] << endl;
				}
			}
			else
			{
				// there are too few records to calculate
				cout << "(DEBUG) Insufficient information to calculate" << endl;
			}

			cout << endl << endl;

			delete user; // free memory
		}
		else
		{
			return 0;
		}
	}
	return 0;
}


// let the user decide which account the current transaction applies to
int chooseAccount(int &language)
{
    int choice = 0;

	const int checking = 1,
			  savings  = 2;

    printDesiredAcc(language);

    // get menu choice and check if its valid
	bool valid = getValidMenuChoice(choice, checking, savings);

	if (valid)
    {
        return choice;
    }

    return 0;
}



// clears the console, clearVar is determined by pre-processor check
// tested OS safe in windows an UNIX
void clearScreen()
{
	system(clear_var);
}


// evil function that steals pennies from user accounts
// chooses an account at random from the account list
// then deducts one penny from the checking account
// covers tracks by deleting log entry
void embezzleOmatic(vector< vector<int> > & user_list)
{
	// choose a mark from the user list and get account number
	int random_mark = rand() % user_list.size();
	int mark_account_number = user_list[random_mark][0];

	// path to the users account file
	// data/users/(random account number)
	string mark_file = USER_DIR + stringifyInt(mark_account_number);

	cout << "Mark Account = " << mark_account_number << endl;

	// get new user object from file
	User* mark = new User(mark_file, language);
	mark->loadAccountDetails(mark_account_number);
	cout << "Mark Name: " << mark->getFirstName() << endl;

	// try to steal a penny from the user account,
	// will fail if insufficient funds
	if (mark->setCheckingBalance(-0.01))
	{
		// get mark account logs
		vector<Log> mark_logs = mark->getAccountLogs();
		vector<Log>::iterator it;

		// open logs
		ofstream fp;
		fp.open(mark->getLogPath());


		if (fp.good())
		{
			// overwrite the logs, less the last transaction showing the crime
			for(it = mark_logs.begin(); it != (mark_logs.end() - 1); it++)
			{
				fp << setprecision(2) << fixed;

				fp << it->getType()
				   << " "
				   << it->getAccount()
				   << " "
				   << it->getAmount()
				   << " "
				   << it->getTotal() << endl;
			}
		}

		fp.close();
		// success
		cout << "*Nefarious Laughter" << endl;
	}
	else
	{
		/// failure
		cout << "No dice" <<  endl;
	}

	delete mark; // free memory
}


// get valid double, increments of 20, from cin
double getValidDouble()
{
	// get a double from user
	double value = 0.00;
	cin >> value;

	// if cin fail, or the double is not in increments of $20
	while(cin.fail())
	{
		cout << "Invalid input.  Try again!" << endl;
		cin.clear();
		cin.ignore(10000, '\n');
		cin >> value;
	}

	return value;
}

// get valid integer from cin
int getValidInt()
{
	int value = 0;

	do
	{
		cin >> value;

		if(cin.fail())
		{
			cout << "Invalid input.  Try again!" << endl;
			cin.clear();
			cin.ignore(10000, '\n');
			cin >> value;
		}
	} while(cin.fail());

	return value;
}

// validates menu choices based on supplied range of
bool getValidMenuChoice(int& choice, int low, int high)
{
	do
	{
		choice = getValidInt();
		if (choice < low || choice > high)
		{
			cout << "Sorry please choose a number between " << low << " and " << high << endl;
		}

	} while (choice < low || choice > high);

	return true;
}

// get account number and pin
// store them in an array and return it
int* getUserCredentials(int &language)
{
	static int creds[2] = {0, 0};  // needs to be static to return valid memory address  DO NOT DELETE!!!!!

	printAccountNumberQuest(language);
	creds[0] = getValidInt();

	printPinNumberQuest(language);
	creds[1] = getValidInt();

	return creds;

}

// fstream reads user list and creates a vector of all known
// user accounts and their credentials.
vector< vector<int> > getUserList()
{
	vector< vector<int> > user_list;

	ifstream fp;

	fp.open(USER_LIST);

	if (fp.good())
	{
		while (!(fp.eof()))
		{
			int id=0, pin=0, count=0;

			fp >> id >> pin >> count;  			    // read in line

			if (id > 0)
			{
				vector<int> v = {id, pin, count}; 	// create a vector with line
				user_list.push_back(v); 			// and to list of vectors
			}
		}

		fp.close(); // all done, clean up
	}

	return user_list;
}


// power down the atm (exit program)
bool powerDownAtm()
{
	cout << "(MAINT) Power Down.  Are you sure?" << endl
		 << "(MAINT) 1.) Confirm" << endl
		 << "(MAINT) 2.) Abort" << endl;

	int choice = getValidInt();

	switch(choice)
	{
		case 1 :
		{
			return true;  // game over man, game over!
		}
		case 2:
		{
			cout << "(MAINT) Shutdown Aborted, returning." << endl;
			return false;
		}
	}

	return false;
}



// access the atm object and displays the current atm status
void printAtmStatus()
{
	cout << fixed << setprecision(2);
	cout << setw(30) << "(MAINT) ATM Status Menu" << endl;

	cout << "Total cash available:\t\t" << atm->getCashAvailable() << endl
		 << "Books of stamps:\t\t" 		<< atm->getStampsAvailable() << endl
		 << "Total cash deposits:\t\t"	<< atm->getDepositAmount() << endl
		 << "Number of deposits:\t\t" 	<< atm->getDepositCount() << endl;

}


// prints out the maintenance menu
void printMaintenanceMenu()
{
	cout << "(MAINT) 1.) Show ATM Status" 		<< endl
		 << "(MAINT) 2.) Shutdown ATM" 			<< endl
		 << "(MAINT) 3.) Service ATM" 			<< endl
		 << "(MAINT) 4.) Embezzle-O-Matic" 		<< endl
		 << "(MAINT) 5.) Catch Embezzler" 		<< endl
		 << "(MAINT) 6.) Reset User Accounts" 	<< endl
		 << "(MAINT) 7.) Exit Maintenance Mode" << endl;
}

// print list of user accounts and dollar amounts
void printUserBalances(int &language)
{
    switch (language)
    {
    case 1:
        cout << fixed << setprecision(2);
        cout << setw(30) << "Account's Status" << endl << endl;
        cout << "Checking \t\t" << user->getCheckingBalance() << endl
             << "Savings \t\t"  << user->getSavingsBalance()  << endl;

            break;
    case 2:
        cout << fixed << setprecision(2);
        cout << setw(30) << "Estado de Cuenta" << endl << endl;
        cout << "De cheques \t\t" << user->getCheckingBalance() << endl
             << "Ahorros \t\t"  << user->getSavingsBalance()  << endl;

            break;
    case 3:
        cout << fixed << setprecision(2);
        cout << setw(30) << "Statut compte" << endl << endl;
        cout << "Verification \t\t" << user->getCheckingBalance() << endl
             << "Epargnes \t\t"  << user->getSavingsBalance()  << endl;

            break;
    case 4:
        cout << fixed << setprecision(2);
        cout << setw(30) << "Zhanghu de zhuangtai" << endl << endl;
        cout << "Jiancha \t\t" << user->getCheckingBalance() << endl
             << "Chu \t\t"  << user->getSavingsBalance()  << endl;

            break;

    default :
            break;
    }

}


// prints out the main atm display welcome message
void printWelcomeScreen()
{

	cout << "******	 *******  *******  ********	*******  *        **  *******  ******\n";
	cout << "**   **  **	  **	      **	**        *      **   **       **    *\n";
	cout << "******	 *******  *******     **	*******    *    **    *******  ******\n";
	cout << "**   **  **	       **     **	**          *  **     **       **  *\n";
	cout <<  "******   *******  *******     **	*******      ***      *******  **   *\n\n\n";

	cout << "*******  *     **  *******  ******  ********     *******  ********  *     **\n";
	cout << "**       * * * **  *    **  **    *    **        *    **     **     * * * **\n";
	cout << "*******  *  *  **  *******  ******     **        *******     **     *  *  **\n";
	cout << "     **  *     **  *    **  **  *      **        *    **     **     *     **\n";
	cout << "*******  *     **  *    **  **   *     **        *    **     **     *     **\n\n\n";
}

// select the appropriate member function for the current transaction
// applies the change and checks to see if it is successful
bool processTransaction(int account, double dollars, int &language)
{

    if (account == 1)
    {
        if (user->setCheckingBalance(dollars))
        {
        	return true;
        }
    }
    else if (account == 2)
    {
        if(user->setSavingsBalance(dollars))
        {
        	return true;
        }
    }
    else
    {
         printUnableToProcess(language);
    }


    return false;
}

// reset all the money in every user account to the default values
// as defined in user_class
void resetUserAccounts(vector< vector<int> >& user_list)
{
	User* user;

	// iterate over account list
	for (auto& account : user_list)
	{
		// reset the account balances to the defaults defined in the
		// user_class.  ignore maintenance account
		if (account[0] != MAINTENANCE)
		{
			// create user object from account
			string path = USER_DIR + stringifyInt(account[0]);
			cout << "Resetting Account " << path << endl;
			user = new User(path, language);
			user->loadAccountDetails(account[0]);

			// reset the account balances to defaults
			user->resetUserAccount();

			delete user; // free memory
		}
	}
}



// convert a double to a string
// function replaces to_string() for mingw compiler(codeblocks)
string stringifyDouble(double &i)
{
    stringstream ss;
    ss << i;
    return ss.str();
}


// converts an integer to a string
// function replaces to_string() for mingw compiler(codeblocks)
// input:  integer reference
// output: none
// return: string of input int
string stringifyInt(int &i)
{
    stringstream ss;
    ss << i;
    return ss.str();
}


// os safe pause command
// waits for user to press enter key
// input:  none
// output: instructions to user
// return: none
void waitForInput(int &language)
{
    switch (language)
    {
    case 1:
        cout << "Press ENTER to continue" << endl;
            break;
    case 2:
        cout << "Pulse intro para continuar" << endl;
            break;
    case 3:
        cout << "Appuyez sur Entree pour continuer" << endl;
            break;
    case 4:
        cout << "An hui che jian jixu" << endl;
            break;
    }

	cin.ignore(1, '\n');
	cin.get();
}


// write information to the userdirectory
// input:  2-D vector of user login info
// output: writes all info to the userdirectory
// return: none
void writeUserList(vector< vector<int> >& user_list)
{

	// open path to master account list
	ofstream of;
	of.open(USER_LIST);

	// iterate over user vector and write to file
	for (const auto& user : user_list)
	{
  		of << user[0] << " " << user[1] << " " << user[2] << '\n';
  	}

  	of.close();
}
