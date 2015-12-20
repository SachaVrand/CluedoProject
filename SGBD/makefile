CC=gcc
CFLAGS=-Wall -ansi -pedantic
EXEC=sgbd
SRC= $(wildcard *.c)
OBJ= $(SRC:.c=.o)

all: $(EXEC)

sgbd: $(OBJ)
	$(CC) $(CFLAGS) -o $@ $^

main.o: myString.h global.h liste.h typeElement.h donnee.h tuple.h colonne.h table.h
colonne.o: myString.h global.h liste.h typeElement.h donnee.h tuple.h colonne.h table.h
donnee.o: myString.h global.h liste.h typeElement.h donnee.h tuple.h colonne.h table.h
liste.o: global.h liste.h typeElement.h donnee.h tuple.h colonne.h table.h
myString.o: myString.h
table.o: myString.h global.h liste.h typeElement.h donnee.h tuple.h colonne.h table.h
tuple.o: myString.h global.h liste.h typeElement.h donnee.h tuple.h colonne.h table.h
typeElement.o: myString.h liste.h typeElement.h colonne.h

%.o: %.c
	$(CC) $(CFLAGS) -c $<

.PHONY: clean mrproper

clean:
	rm -rf *.o

mrproper: clean
	rm -rf $(EXEC)