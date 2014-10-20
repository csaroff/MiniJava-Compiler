all:
	java -jar /usr/local/lib/antlr-4.4-complete.jar Minijava.g4
compile:
	javac -cp .:/usr/local/lib/* Minijava*.java
	javac -cp .:/usr/local/lib/* *Listener.java
killParser:
	rm Minijava*.java
	rm Minijava*.tokens
clean:
	rm *.class
test:
	java org.antlr.v4.runtime.misc.TestRig Minijava goal factorial.java
