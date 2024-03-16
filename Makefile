CC = gcc
CFLAGS = -Wall
TARGET = maze

SRCS = main.c graf.c wczytaj_labirynt.c
OBJS = $(SRCS:.c=.o)

.PHONY: clean

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c pomoc.h graf.h wczytaj_labirynt.h
	$(CC) $(CFLAGS) -c -o $@ $<

clean:
	rm $(OBJS)