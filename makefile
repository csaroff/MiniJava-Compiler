all:
	make parser
	mv *.java src
	make compiler
compiler:
	/opt/jdk1.8.0/bin/javac src/*.java
	mv src/*.class .
parser:
	/opt/jdk1.8.0/bin/java -jar ~/apps/Antlr4/* -visitor Minijava.g4
killParser:
	rm Minijava*.tokens
	#rm Minijava*.java
jar:
	jar cf minijavac.jar *.class
clean:
	make killParser
	rm *.class
mirror:
	/opt/jdk1.8.0/bin/java org.antlr.v4.runtime.misc.TestRig Minijava goal TestFiles/Main.java
