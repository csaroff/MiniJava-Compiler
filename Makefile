compile:
	/opt/jdk1.8.0_25/bin/javac -cp .:/usr/local/lib/* src/*.java
	mv src/*.class .
	#javac -cp .:/usr/local/lib/* src/Minijava*.java
	#javac -cp .:/usr/local/lib/* src/*Listener*.java
	#javac -cp .:/usr/local/lib/* src/Main.java
all:
	make generateParser
	mv *.java src
	make compile
	make test
generateParser:
	/opt/jdk1.8.0_25/bin/java -jar /usr/local/lib/antlr-4.4-complete.jar -visitor Minijava.g4
killParser:
	rm Minijava*.java
	rm Minijava*.tokens
clean:
	rm *.class
test:
	for file in TestFiles/*.java ; do \
	  echo "Begin " $$file " parsing" ; \
	  echo "-----------------------------------------------------------" ; \
	  /opt/jdk1.8.0_25/bin/java Main $$file ; \
	  echo "-----------------------------------------------------------" ; \
	  echo "End " $$file " parsing" ; \
	  #cat $$file | java Main ; \
	done
	#cat factorial.java | java Main
mirror:
	/opt/jdk1.8.0_25/bin/java org.antlr.v4.runtime.misc.TestRig Minijava goal factorial.java
