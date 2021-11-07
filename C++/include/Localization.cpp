/******************************************************************************

	Localization.cpp

	Best Team Ever
	Programmers: Fernando Cruz & Derek Osborne
	Date: November 21, 2016
	Programming Fundementals I
	Instructor: Korinne Caruso
    Standard: C++11

*******************************************************************************/


#include "Localization.h"

#include <string>

using namespace std;

/* ALL FUNCTIONS FOLLOWING THIS COMMENT LINE ARE FOR LOCALIZATION
    TRANSLATIONS. NO OTHER FUNCTIONS SHOULD BE INPUT BETWEEN OR
        AFTER THESE FUNCTIONS. THIS IS TO MAKE THEM EASIER TO
            ORGANIZE AND EDIT!

                THESE LINES HAVE BEEN COMMENTED OUT TIL
                    TRANSLATIONS ARE FINISHED

                                cout << "";
*/


// print list of user menu choices, what the user can
// do with this program
// input: none
// output: print user menu
// return: none
void printAccountMenu(int language)
{
   switch (language)
   {

    case 1:
            cout  << "1.) Fast Cash"        << endl
                  << "2.) Show Balances"    << endl
                  << "3.) Withdraw"         << endl
                  << "4.) Deposit"          << endl
                  << "5.) Other Services"   << endl
                  << "6.) End Session"      << endl;
        break;
    case 2:
            cout  << "1.) Dinero facil"        << endl
                  << "2.) Mostrar saldos"    << endl
                  << "3.) Retirar"         << endl
                  << "4.) Depositar"          << endl
                  << "5.) Otros servicios"   << endl
                  << "6.) Terminar sesion"      << endl;
        break;
    case 3:
            cout  << "1.) Argent facile"        << endl
                  << "2.) Afficher les soldes"    << endl
                  << "3.) Se desister"         << endl
                  << "4.) Depot"          << endl
                  << "5.) Autres services"   << endl
                  << "6.) Fin de session"      << endl;
        break;
    case 4:
            cout  << "1.) Kuaisu xianjin"        << endl
                  << "2.) Xianshi yu'e"    << endl
                  << "3.) Shouhui"         << endl
                  << "4.) Cunkuan"          << endl
                  << "5.) Qita fuwu"   << endl
                  << "6.) Jieshu huihua"      << endl;
        break;

    default :
        break;
   }
}

// print list of user menu choices, what language the
// user can view in
// input: none
// output: print language menu
// return: user selected choice
int printLangMenu()
{
    int selection;

    cout << "Please select desired language"        << endl
         << "\t1.) English"                         << endl
         << "\t2.) Espanol"                         << endl
         << "\t3.) Francais"                        << endl
         << "\t4.) Zhongwen"                        << endl;

    cin >> selection;

    return selection;
}

// print choice not recognized
// input: none
// output: print out statement
// return: none
void printFashCash(int language)
{
    switch (language)
    {

        case 1:
            cout << "Choice not recognized\n";
                break;
        case 2:
            cout << "Opcion no reconocida\n";
                break;
        case 3:
            cout << "Choix non reconnu\n";
                break;
        case 4:
            cout << "Xuan zhai bu bei shibie\n";
                break;

            default :
                break;
    }
}

// prints fast cash menu
// input: none
// output: print fast cash choices
// return: none
void printFastCashMenu(int language)
{
    cout << "1.)  $20" << endl
         << "2.)  $40" << endl
         << "3.) $100" << endl
         << "4.) $200" << endl;
    switch (language)
    {

    case 1:
            cout  << "5.) Go back to previous menu\n";
        break;
    case 2:
            cout << "5.) Regresar al menu anterior\n";
        break;
    case 3:
            cout << "5.) Revenir au menu precedent\n";
        break;
    case 4:
            cout << "5.) Fanhui shang yiji caidan\n";
        break;

    default :
        break;
    }
}

// print menu with choices to buy stamps and check
// account records
// input: none
// output: print menu
// return: none
void printOtherServicesMenu(int language)
{
    switch (language)
    {
    case 1:
            cout  << "1.) Purchase Stamps"             << endl
                  << "2.) List Recent Transactions"    << endl
                  << "3.) Go back to previous menu"    << endl;
        break;
    case 2:
            cout  << "1.) Sellos de compra"             << endl
                  << "2.) Lista de transacciones recientes"    << endl
                  << "3.) Vuelve al menu anterior"    << endl;
        break;
    case 3:
            cout  << "1.) Achat Timbres"             << endl
                  << "2.) Liste Transactions recentes"    << endl
                  << "3.) Retour au menu precedent"    << endl;
        break;
    case 4:
            cout  << "1.) Goumai youpiao"             << endl
                  << "2.) Zuijin lie chu jiaoyi"    << endl
                  << "3.) Fanhui shang yiji caidan"    << endl;
        break;

    default :
        break;
    }
}

// print to take take money and thank
// input: none
// output: print take and thank
// return: none
void printWithdrawRetrieve(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Please retrieve money below" << endl
                  << "Thank you for your business" << endl;
        break;
    case 2:
            cout  << "Por favor, recupere dinero abajo" << endl
                  << "Gracias por hacer negocios" << endl;
        break;
    case 3:
            cout  << "Veuillez recuperer de l'argent ci-dessous" << endl
                  << "Merci pour votre entreprise" << endl;
        break;
    case 4:
            cout  << "Qing zai xiamian lingqu kuanxiang" << endl
                  << "Ganxie nin de yewu" << endl;
        break;

    default :
        break;
    }
}

// print user to enter account number
// input: none
// output: print request for user to enter account number
// return: none
void printAccountNumberQuest(int language)
{
    switch (language)
    {

    case 1:
            cout  << "Please enter account number\n";
        break;
    case 2:
            cout << "Introduzca el numero de cuenta\n";
        break;
    case 3:
            cout << "Veuillez saisir le numero de compte\n";
        break;
    case 4:
            cout << "Qing shuru zhanghao\n";
        break;

    default :
        break;
    }
}

// print user top enter their pin
// input: none
// output: print request enter pin
// return: none
void printPinNumberQuest(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Please enter your pin\n";
        break;
    case 2:
            cout << "Por favor ingrese su PIN\n";
        break;
    case 3:
            cout << "Veuillez entrer votre broche\n";
        break;
    case 4:
            cout << "Qing shuru nin de PIN ma\n";
        break;

    default :
        break;
    }
}

// print can't process at this time
// input: none
// output: print can't process
// return: none
void printUnableToProcess(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Could not process transaction at this time\n";
        break;
    case 2:
            cout << "No se pudo procesar la transaccion en este momento.\n";
        break;
    case 3:
            cout << "Impossible de traiter la transaction en ce moment\n";
        break;
    case 4:
            cout << "Muqian wufa chuli jiaoyì\n";
        break;

    default :
        break;
    }
}

// print can't service
// input: none
// output: can't service due to ATM funds
// return: none
void printCantServiceTransaction(int language)
{
    switch (language)
    {
    case 1:
            cout  << "ATM cannot service this transaction,"                  << endl
                  << "withdrawal amount exceeds current ATM funds"           << endl
                  << "Please contact branch customer service for assistance" << endl;
        break;
    case 2:
            cout  << "ATM no puede dar servicio a esta transaccion,"                  << endl
                  << "El monto del retiro excede los fondos actuales del ATM"           << endl
                  << "Póngase en contacto con el servicio de atencion al cliente de sucursales para obtener asistencia" << endl;
        break;
    case 3:
            cout  << "ATM ne peut pas traiter cette transaction,"                  << endl
                  << "Le montant du retrait depasse les fonds ATM actuels"           << endl
                  << "Veuillez contacter le service clientele de la succursale pour obtenir de l'aide." << endl;
        break;
    case 4:
            cout  << "ATM buneng fuwu zhege jiaoyi,"                  << endl
                  << "Ti kuan jin'e chaoguo dang qian ATM zijin"           << endl
                  << "Qing lianxi fenzhi kehu fuwu yi huode bangzhu" << endl;
        break;

    default :
        break;
    }
}

// print enter dollar amount
// input: none
// output: fast cash dollars
// return: none
void printFastCashAmount(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Please Enter Dollar Amount (Increments of $20 only)\n";
        break;
    case 2:
            cout << "Por favor ingrese el monto en dolares (incrementos de $ 20 solamente)\n";
        break;
    case 3:
            cout << "Veuillez entrer le montant en dollars (augmentations de 20 $ seulement)\n";
        break;
    case 4:
            cout << "Qing shuru meiyuan jin'e (jin zengjia 20 meiyuan)\n";
        break;

    default :
        break;
    }
}

// print selection invalid
// input: none
// output: selection invalid
// return: none
void printSelectionInvalid(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Account selection invalid.  Try Again\n";
        break;
    case 2:
            cout << "La seleccion de la cuenta no es valida. Intentalo de nuevo\n";
        break;
    case 3:
            cout << "La selection du compte n'est pas valide. Reessayer\n";
        break;
    case 4:
            cout << "Zhanghu xuanze wuxiao. Zaishì yicì\n";
        break;

    default :
        break;
    }
}

// print request dollar amounts
// input: none
// output: print request for dollars
// return: none
void printDollarAmount(int language)
{
    switch (language)
    {

    case 1:
            cout  << "Please Enter Dollar Amount\n";
        break;
    case 2:
            cout << "Por favor ingrese la cantidad del dolar\n";
        break;
    case 3:
            cout << "Veuillez entrer le montant du dollar\n";
        break;
    case 4:
            cout << "Qing shuru meiyuan jin'e\n";
        break;

    default :
        break;
    }
}

// print deposit availability
// input: none
// output: print deposit availability
// return: none
void printDepositAvail(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Deposits are available immediately. Thank you for your business\n";
        break;
    case 2:
            cout << "Los depositos estan disponibles inmediatamente. Gracias por hacer negocios\n";
        break;
    case 3:
            cout << "Les depots sont disponibles immediatement. Merci pour votre entreprise\n";
        break;
    case 4:
            cout << "Ke liji cun ru cunkuun. Ganxie nin de yewu\n";
        break;

    default :
        break;
    }
}

// print can't fill
// input: none
// output: unable to fulfill order
// return: none
void printUnableToFill(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Unable to fulfill order at this time\n";
        break;
    case 2:
            cout << "No se puede cumplir el pedido en este momento\n";
        break;
    case 3:
            cout << "Impossible de remplir la commande en ce moment\n";
        break;
    case 4:
            cout << "Muqian wufa wancheng dingdan\n";
        break;

    default :
        break;
    }
}

// print thank you message
// input: none
// output: Print thank you message
// return: none
void printThankYouMsg(int language)
{
    switch (language)
    {

    case 1:
            cout  << "Thank you for your business, logging out!\n";
        break;
    case 2:
            cout << "Gracias por su negocio, cierre de sesion!\n";
        break;
    case 3:
            cout << "Merci pour votre entreprise, deconnexion!\n";
        break;
    case 4:
            cout << "Ganxie nin de yewu, tuichu!\n";
        break;

    default :
        break;
    }
}

// print incorrect info
// input: none
// output: print error with pin and act
// return: none
void printAccPinFAIL(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Your account number or pin were incorrect," << endl
                  << "Please try again"                                 << endl;
        break;
    case 2:
            cout  << "Su numero de cuenta o pin fueron incorrectos," << endl
                  << "Por favor, inténtelo de nuevo"                                 << endl;
        break;
    case 3:
            cout  << "Votre numéro de compte ou votre broche étaient incorrects," << endl
                  << "Veuillez reessayer"                                 << endl;
        break;
    case 4:
            cout  << "Nin de zhanghao huo PIN bu zhengque," << endl
                  << "Qing zaishi yici"                                 << endl;
        break;

    default :
        break;
    }
}

// print account suspended message
// input: none
// output: print account suspended message
// return: none
void printAccSusp(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Account has been suspended."                         << endl
                  << "Please contact customer service for assistance"       << endl
                  << "We apologize for any inconvenience"                   << endl;
        break;
    case 2:
            cout  << "Cuenta-ha sido suspendido."                                            << endl
                  << "Póngase en contacto con el servicio al cliente para obtener ayuda."    << endl
                  << "Nos disculpamos por cualquier inconveniente."                          << endl;
        break;
    case 3:
            cout  << "Compte a été suspendu."                                               << endl
                  << "S'il vous plaît contacter le service à la clientèle pour assistance." << endl
                  << "Nous nous excusons pour tout inconvénient."                           << endl;
        break;
    case 4:
            cout  << "Zhànghù yǐ bèi zàntíng."                                              << endl
                  << "Xúnqiú bāngzhù qǐng liánxì kèfù."                                     << endl
                  << "Wǒmen de rènhé bùbiàn biǎoshì qiànyì."                                << endl;
        break;

    default :
        break;
    }
}

// print account not recognized
// input: none
// output: print Account not rec
// return: none
void printNotRec(int language)
{
    switch (language)
    {
        case 1:
            cout  << "Account Number is not recognized\n";
                break;
        case 2:
            cout  << "Numero de cuenta no es reconnu\n";
                break;
        case 3:
            cout  << "Numero de compte est non reconnu\n";
                break;
        case 4:
            cout  << "Zhanghao shi wufa shibie\n";
            break;

        default :
                break;
    }
}

// print choose what account
// input: none
// output: print desired account menu
// return: none
void printDesiredAcc(int language)
{
    switch (language)
    {
    case 1:
            cout  << "Please Choose Desired Account"    << endl
                  << "1.) Checking"                     << endl
                  << "2.) Savings"                      << endl;
        break;
    case 2:
            cout  << "Por favor, elija cuenta deseada"       << endl
                  << "1.) De cheques"                        << endl
                  << "2.) Ahorros"                           << endl;
        break;
    case 3:
            cout  << "S'il vous plait Choisir compte souhaite"    << endl
                  << "1.) Verification"                           << endl
                  << "2.) Epargnes"                               << endl;
        break;
    case 4:
            cout  << "Qing xuanze suo xu de zhanghu"    << endl
                  << "1.) Jiancha"                      << endl
                  << "2.) Chu"                          << endl;
        break;

    default :
        break;
    }
}
