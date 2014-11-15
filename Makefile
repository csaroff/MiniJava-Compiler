compile:
	javac -cp .:/usr/local/lib/* src/*.java
	mv src/*.class .
	#javac -cp .:/usr/local/lib/* src/Minijava*.java
	#javac -cp .:/usr/local/lib/* src/*Listener*.java
	#javac -cp .:/usr/local/lib/* src/Main.java
generateParser:
	java -jar /usr/local/lib/antlr-4.4-complete.jar Minijava.g4
killParser:
	rm Minijava*.java
	rm Minijava*.tokens
clean:
	rm *.class
test:
	for file in TestFiles/* ; do \
	  cat $$file | java Main ; \
	done
	#cat factorial.java | java Main
mirror:
	java org.antlr.v4.runtime.misc.TestRig Minijava goal factorial.java
