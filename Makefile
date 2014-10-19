all:
	java -jar /usr/local/lib/antlr-4.4-complete.jar Minijava.g4
killParser:
	rm *.java
	rm *.tokens
clean:
	rm *.class
test:
	java -jar /usr/local/lib/antlr-4.4-complete.jar Minijava.g4
