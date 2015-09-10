run: Calculator
	java Calculator
Calculator: Calculator_java
	javac *.java
Calculator_java: calculator.jj
	javacc calculator.jj
clean:
	rm -f *.class *.java
