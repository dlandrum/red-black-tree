# Written by Don Landrum on October 11, 2017 for personal use

# This makefile can be used to test the authenticity of the Red-Black tree.
# This entire project was written on a Mac that runs macOS 10.12.6. The .java
#   files can certainly be compiled and run from any environment, and I would
#   imagine that the Makefile will work on any Mac or Linux machine, though I am
#   not sure about Windows computers. If this Makefile doesn't work, feel free
#   to manipulate the driver and/or type in the command-line arguments to run
#   the project.
# Typing 'make random' in the command line will populate the tree with a large
#   amount of randomly generated integers.
# Typing 'make ascending' in the command line will populate the tree with ints
#   in ascending order to a specified threshold.
# Typing 'make descending' in the command line will begin at a specified
#   threshold and populate the tree with ints in descending order to zero.
# All three tests also delete random numbers and give the user an opportunity
#   to query for numbers of their choosing.

JFLAGS = -g
JC = javac
JR = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Driver.java \
        Tree.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

random: classes
	$(JR) Driver 0

ascending: classes
	$(JR) Driver 1

descending: classes
	$(JR) Driver 2
