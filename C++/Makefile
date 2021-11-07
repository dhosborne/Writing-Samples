# ATM Project Makefile
# Standard: C++11

CXX = g++
CXXFLAGS= -Wall -std=c++11
BIN = Main
DATA_PATH = data/users/
INCLUDE_PATH = include/

all: atm

atm: main.cpp
	$(CXX) $(CXXFLAGS) -I$(INCLUDE_PATH) main.cpp -o $(BIN)

# deletes all .o files, executables
clean:
	rm -f *.o $(BIN)  *.exe $(INCLUDE_PATH)*.o

# delete all user account logs
cleanLogs:
	rm -f $(DATA_PATH)*.log
