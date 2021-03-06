# Makefile for toy Calc language scanner

# Here is how you can use this file:
#
# make
#   generate everything that needs to be generated and compiler everything
#   that needs to be compiled.
#
# make test
#   run all test cases
#
# make clean
#   remove all generated files and backup files, keep only handwritten files.


# -- jikes is faster than javac --
JAVACOMPILER = javac
#JAVACOMPILER = jikes

# -- Name of the main program --
MAIN=CalcDumpTree

# -- Name of the .jj file --
JJFILE=calc

# -- Name of the parser in the .jj file --
PARSERNAME=CalcParser

# -- files generated by JavaCC --
GENERATED_jj_FILES = \
	$(PARSERNAME).java \
	$(PARSERNAME)Constants.java \
	$(PARSERNAME)TokenManager.java \
	ParseException.java \
	Token.java \
	TokenMgrError.java \
	SimpleCharStream.java

# -- files generated by JJTree --
GENERATED_jjt_FILES = \
	Node.java \
	SimpleNode.java \
	$(PARSERNAME)TreeConstants.java \
	JJT$(PARSERNAME)State.java

# -- Compile --
$(MAIN).class:	$(MAIN).java $(GENERATED_jj_FILES)
	$(JAVACOMPILER) \
		$(MAIN).java \
		$(GENERATED_jj_FILES) \
		$(GENERATED_jjt_FILES)

# -- Run parser generator --
$(PARSERNAME).java:	$(JJFILE).jj
	javacc $(JJFILE).jj

# -- Run jjtree preprocessor --
$(JJFILE).jj:	$(JJFILE).jjt
	jjtree $(JJFILE).jjt

# -- Run test cases --
test:	
	runtests

# -- Clean up by removing generated files and backup files from text editors --
clean:
	/bin/rm -f \
		$(GENERATED_jj_FILES) \
		$(JJFILE).jj \
		$(GENERATED_jjt_FILES) \
		*.class *%

