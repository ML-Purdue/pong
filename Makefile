.PHONY: all

all: Main

Main: Main.java
	javac Main.java

clean:
	rm *.class
