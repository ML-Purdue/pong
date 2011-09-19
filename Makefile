
.PHONY:all clean

all: Vector Ball Round Pong

Vector: Vector.java
	javac Vector.java

Ball: Ball.java
	javac Ball.java

GameState: GameState.java
	javac GameState.java

Round: GameState Ball Round.java
	javac Round.java

Pong: Round Pong.java
	javac Pong.java

clean:
	rm -rf *.class
