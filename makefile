all:
	make generateParser
	mv *.java src
	make compiler
generateParser:
	/opt/jdk1.8.0/bin/java -jar ~/apps/Antlr4/* -visitor Minijava.g4
compiler:
	/opt/jdk1.8.0/bin/javac src/*.java
	mv src/*.class .
killParser:
	rm Minijava*.java
	rm Minijava*.tokens
jar:
	jar cf minijavac.jar *.class
clean:
	rm *.class
mirror:
	/opt/jdk1.8.0/bin/java org.antlr.v4.runtime.misc.TestRig Minijava goal TestFiles/Main.java
