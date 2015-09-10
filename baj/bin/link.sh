#!/bin/bash

cd ../src/edu/ustc/cs/compile/skipoominijool/

for i in ../../../../../../config/JJ/src/*; do
	ln -s $i ` basename $i`
done

exit 0
