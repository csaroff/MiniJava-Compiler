all:
	java -jar /usr/local/lib/antlr-4.4-complete.jar Minijava.g4
compile:
	javac -cp .:/usr/local/lib/* Minijava*.java
	javac -cp .:/usr/local/lib/* *Listener*.java
	javac -cp .:/usr/local/lib/* Main.java
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
