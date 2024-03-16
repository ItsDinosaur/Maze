CFLAGS = -Wall

all: final

final: main.o graf.o wczyt.o
	gcc $(CFLAGS) main.o graf.o 

main.o: main.c
	gcc $(CFLAGS) main.c

graf.o: graf.c
	gcc $(CFLAGS) graf.c

wczyt.o: wczytaj_labirynt.c
	gcc $(CFLAGS) wczytaj_labirynt.c

clean:
	rm main.o graf.o final