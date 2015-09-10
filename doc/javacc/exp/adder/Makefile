run: Adder
	java Adder < input.txt
Adder: Adder_java
	javac *.java
Adder_java: adder.jj
	javacc adder.jj
clean:
	rm -f *.class *.java
