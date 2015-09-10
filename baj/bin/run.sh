#!/bin/sh

# 该文件是一个shell脚本文件的示例，用于调用实验平台。你需要根据需要做相应的修改。

# 以下9个变量一般情况下无需修改

ROOT_DIR=../../
PLATFORM_DIR=${ROOT_DIR}/platform
PLATFORM_LIB_DIR=${PLATFORM_DIR}/lib
PLATFORM_LIB=${PLATFORM_LIB_DIR}/edu.ustc.cs.compile.platform.core.jar:\
${PLATFORM_LIB_DIR}/edu.ustc.cs.compile.platform.interfaces.jar:\
${PLATFORM_LIB_DIR}/edu.ustc.cs.compile.platform.util.jar

AST_DIR=${ROOT_DIR}/lib/AST/3.1.2
AST_LIB=${AST_DIR}/org.eclipse.core.resources_3.1.2.jar:\
${AST_DIR}/org.eclipse.core.runtime_3.1.2.jar:\
${AST_DIR}/org.eclipse.jdt.core_3.1.2.jar:\
${AST_DIR}/org.eclipse.jdt.ui_3.1.2.jar

JAVACC_DIR=${ROOT_DIR}/tools/javcc
JAVACC_RUNTIME_LIB=${JAVACC}/javacc.jar

SELF_DIR=../classes/

# 以上9个变量一般情况下无需修改

BE_LIB=../../lib/edu.ustc.cs.compile.generator.x86.tyf.forgcc4.2.3.jar
ARCH_LIB=../../lib/edu.ustc.cs.compile.arch.jar

# 你需要根据实际需要修改变量CLASSPATH。CLASSPATH中必须包含运行实验平台、前端、后端所需要
# 的所有类的位置。

CLASSPATH=${PLATFORM_LIB}:${AST_LIB}:${JAVACUP_RUNTIME_LIB}:${PARSER_LIB}:${SELF_DIR}:${BE_LIB}:${ARCH_LIB}

#ant clean && ant javacc
#./link.sh
ant build || exit 0

# 你需要将..\config\demo.xml修改为自己的实验平台配置文件
if [ $# -lt 1 ]; then
    java -classpath ${CLASSPATH} \
         edu.ustc.cs.compile.platform.Main \
         --cfg-file ../config/configure.xml       
else
    java -classpath ${CLASSPATH} \
         edu.ustc.cs.compile.platform.Main $*
fi
