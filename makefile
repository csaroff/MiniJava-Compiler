compile:
	/opt/jdk1.8.0/bin/javac src/*.java
	mv src/*.class .
	#javac -cp .:/usr/local/lib/* src/Minijava*.java
	#javac -cp .:/usr/local/lib/* src/*Listener*.java
	#javac -cp .:/usr/local/lib/* src/Main.java
aa:
	make compile
	make codeGenTest
all:
	make generateParser
	mv *.java src
	make aa
generateParser:
	/opt/jdk1.8.0/bin/java -jar ~/apps/Antlr4/* -visitor Minijava.g4
killParser:
	rm Minijava*.java
	rm Minijava*.tokens
clean:
	rm *.class
codeGenTest:
	#cd TestFiles/SourceCodeGeneration
	/opt/jdk1.8.0/bin/java Main TestFiles/SourceCodeGeneration/Main.java ;\
	java Main
test:
	for file in TestFiles/*.java ; do \
		echo "Begin " $$file " parsing" ; \
		echo "-----------------------------------------------------------" ; \
		/opt/jdk1.8.0/bin/java -ea -cp ~/apps/Antlr4/antlr-4.4-complete.jar:. Main $$file ; \
		echo "-----------------------------------------------------------" ; \
		echo "End " $$file " parsing" ; \
		#cat $$file | java Main ; \
		done
	#cat factorial.java | java Main
mirror:
	/opt/jdk1.8.0/bin/java org.antlr.v4.runtime.misc.TestRig Minijava goal factorial.java
testSyntax:
	for file in TestFiles/Errors/Syntax/*.java ; do \
		echo "Begin " $$file " parsing" ; \
		echo "-----------------------------------------------------------" ; \
		/opt/jdk1.8.0/bin/java -ea -cp ~/apps/Antlr4/antlr-4.4-complete.jar:. Main $$file ; \
		echo "-----------------------------------------------------------" ; \
		echo "End " $$file " parsing" ; \ 
	done
testSemantics:
	for file in TestFiles/Errors/Semantic/*.java ; do \
		echo "Begin " $$file " parsing" ; \
		echo "-----------------------------------------------------------" ; \
		/opt/jdk1.8.0/bin/java -ea -cp ~/apps/Antlr4/antlr-4.4-complete.jar:. Main $$file ; \
		echo "-----------------------------------------------------------" ; \
		echo "End " $$file " parsing" ; \
	done
