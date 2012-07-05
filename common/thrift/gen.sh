
echo "" 1>/dev/null
RED='\033[0;31m'
GREEN='\033[0;32m'
#clear
rm -rf gen-java
thrift --gen java classifier.thrift \
&& thrift --gen java kmiacServer.thrift \
&& thrift --gen java fileTransfer.thrift \
&& thrift --gen java libraryUpdater.thrift \
&& echo "${GREEN}`pwd` All sources successfully generated" \
|| echo "${RED}!!!ERROR!!! Sources generation failed"
tput sgr0

