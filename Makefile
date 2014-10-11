all:
	java -jar /usr/local/lib/antlr-4.4-complete.jar Minijava.g4
clean:
	rm *.java
	rm *.tokens
test:
	java -jar /usr/local/lib/antlr-4.4-complete.jar Minijava.g4
