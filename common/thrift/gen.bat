@ECHO off

RMDIR /S /Q gen-java

"..\..\..\bin\thrift.exe" --gen java "classifier.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\bin\thrift.exe" --gen java "kmiacServer.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\bin\thrift.exe" --gen java "fileTransfer.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\bin\thrift.exe" --gen java "libraryUpdater.thrift"
IF NOT %errorlevel% == 0 GOTO :end

:end
IF "%batch_gen%" == "" pause
