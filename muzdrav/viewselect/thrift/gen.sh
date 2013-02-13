
echo "" 1>/dev/null
#CUR_THRIFT_FILE=`find *.thrift`
RED='\033[0;31m'
GREEN='\033[0;32m'
#clear
rm -rf gen-java
#thrift --gen java $CUR_THRIFT_FILE \
thrift --gen java lab.thrift \
&& thrift --gen java viewselect.thrift \
&& thrift --gen java reception.thrift \
&& thrift --gen java operation.thrift \
&& thrift --gen java medication.thrift \
&& thrift --gen java lab.thrift \
&& thrift --gen java diary.thrift \
&& thrift --gen java ../../../common/thrift/classifier.thrift \
&& thrift --gen java ../../../common/thrift/kmiacServer.thrift \
&& echo "${GREEN}$CUR_THRIFT_FILE sources successfully generated" \
|| echo "${RED}!!!ERROR!!! $CUR_THRIFT_FILE sources generation failed"
tput sgr0

