@ECHO off

RMDIR /S /Q gen-java

"..\..\..\..\bin\thrift.exe" --gen java "viewselect.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "lab.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "reception.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "medication.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "operation.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "diary.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\kmiacServer.thrift"
IF NOT %errorlevel% == 0 GOTO :end

"..\..\..\..\bin\thrift.exe" --gen java "..\..\..\common\thrift\classifier.thrift"
IF NOT %errorlevel% == 0 GOTO :end

:end
IF "%batch_gen%" == "" pause
