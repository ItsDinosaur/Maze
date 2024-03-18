CC = gcc
CFLAGS = -Wall -std=c99
TARGET = maze

SRCS = main.c graf.c wczytaj_labirynt.c znajdz_rozwiazanie.c rozwiazanie.c
OBJS = $(SRCS:.c=.o)

.PHONY: clean

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c pomoc.h graf.h wczytaj_labirynt.h znajdz_rozwiazanie.h rozwiazanie.h
	$(CC) $(CFLAGS) -c -o $@ $<

clean:
	rm $(OBJS) $(TARGET)