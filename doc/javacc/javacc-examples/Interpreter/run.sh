#!/bin/bash

jjtree SPL.jjt
javacc SPL.jj
javac *.java
java SPL fact.spl
java SPL sqrt.spl
java SPL odd.spl

