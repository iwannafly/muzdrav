RMDIR /S /Q gen-java
"..\..\..\bin\thrift.exe" --gen java "classifier.thrift"
"..\..\..\bin\thrift.exe" --gen java "kmiacServer.thrift"
"..\..\..\bin\thrift.exe" --gen java "fileTransfer.thrift"
"..\..\..\bin\thrift.exe" --gen java "libraryUpdater.thrift"
pause
