@ECHO off

RMDIR /S /Q gen-java

"..\..\..\..\bin\thrift.exe" --gen java "auth.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\kmiacServer.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\fileTransfer.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\libraryUpdater.thrift"
IF NOT %errorlevel% == 0 GOTO :end

:end
IF "%batch_gen%" == "" pause
