RMDIR /S /Q gen-java
"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\classifier.thrift"
"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\kmiacServer.thrift"
"..\..\..\..\bin\thrift.exe" --gen java "vrachInfo.thrift"
pause
