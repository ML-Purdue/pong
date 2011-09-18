
.PHONY:all clean

all: Vector

Vector:
	javac Vector.java

clean:
	rm -rf *.class
