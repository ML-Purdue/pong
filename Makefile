.PHONY:all clean

all: Main

Main: Pong Main.java
	javac Main.java

Pong: ControlState Ball Paddle Pong.java
	javac Pong.java

Vector: Vector.java
	javac Vector.java

Ball: Vector Ball.java
	javac Ball.java

Paddle: Paddle.java
	javac Paddle.java

Key: Key.java
	javac Key.java

KeyReleaser: Key KeyReleaser.java
	javac KeyReleaser.java

Controls: Controls.java
	javac Controls.java

ControlState: Controls KeyReleaser ControlState.java
	javac ControlState.java

clean:
	rm -rf *.class
