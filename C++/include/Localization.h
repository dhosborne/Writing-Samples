/******************************************************************************

	Localization.h

	Best Team Ever
	Programmers: Fernando Cruz & Derek Osborne
	Date: November 21, 2016
	Programming Fundementals I
	Instructor: Korinne Caruso
	Standard: C++11

*******************************************************************************/
#ifndef Localization_H
#define Localization_H




/*LOCALIZATION PROTOTYPES*/



/******************************************************************************

	Account Menu Function

	Translates account menu to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printAccountMenu(int);


/******************************************************************************

	Language Menu and set function

	Will display the offered languages provided by the atm for a user session.
	Languages displayed are English, Spanish, French, and Chinese. Member will
    select a language and the language will be passed back to int main(), to be
    used throughout the rest of the program for all displayed outputs to the
    user.


	Return Value
	------------
	selection   int     will return the user language selection that will be used
                        for the user's session


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	selection   int     temp variable for user language menu selection
******************************************************************************/
int	 printLangMenu();

/******************************************************************************

	Choice Not Recognized Function

	Translates cout statement to the appropriate language selection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printFashCash(int);

/******************************************************************************

	Fash Cash Menu Function

	Translates fast cash menu to the appropriate language selection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printFastCashMenu(int);

/******************************************************************************

	Other Services Menu

	Translates other services menu options to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printOtherServicesMenu(int);

/******************************************************************************

	Retrieve Money Notice Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printWithdrawRetrieve(int);

/******************************************************************************

	Request Account Number Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printAccountNumberQuest(int);

/******************************************************************************

	Request Pin Number Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printPinNumberQuest(int);

/******************************************************************************

	Cannot Process Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printUnableToProcess(int);

/******************************************************************************

	ATM Can't Service Function

	Translates cout statement to the appropriate language selection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printCantServiceTransaction(int);

/******************************************************************************

	Fast Cash Amount Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printFastCashAmount(int);

/******************************************************************************

	Selection Invalid Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printSelectionInvalid(int);

/******************************************************************************

	Dollar Amount Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printDollarAmount(int);

/******************************************************************************

	Deposit Availability Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printDepositAvail(int);

/******************************************************************************

	Unable To Fill Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printUnableToFill(int);

/******************************************************************************

	Thank You Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printThankYouMsg(int);

/******************************************************************************

	Account/ Pin Fail Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printAccPinFAIL(int);

/******************************************************************************

	Account Suspension Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printAccSusp(int);

/******************************************************************************

	No Recognizable Account Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printNotRec(int);

/******************************************************************************

	Desired Account Menu Function

	Translates cout statement to the appropriate language slection choice made by
	the user at the start of their individual session. Depending on the integer passed,
	the cout output statement will translate into English, Spanish, French or Chinese.


	Return Value
	------------
	NONE


	Function Parameters
	-------------------
	language 	int  	 Value 	    the users language preference for translation


	Local Variables
	---------------
	NONE
******************************************************************************/
void printDesiredAcc(int);

#endif
