RMDIR /S /Q gen-java
"..\..\..\..\bin\thrift.exe" --gen java "datard.thrift"
"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\kmiacServer.thrift"
"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\classifier.thrift"
pause
