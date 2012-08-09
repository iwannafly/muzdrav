@ECHO off
CLS

SET home_dir="%~p0
SET batch_gen=1

CALL :generateJavaSource common\thrift"
CALL :generateJavaSource muzdrav\auth\thrift"
CALL :generateJavaSource muzdrav\lds\thrift"
CALL :generateJavaSource muzdrav\mss\thrift"
CALL :generateJavaSource muzdrav\osm\thrift"
CALL :generateJavaSource muzdrav\regPatient\thrift"
CALL :generateJavaSource muzdrav\admin\thrift"
CALL :generateJavaSource muzdrav\viewselect\thrift"
CALL :generateJavaSource muzdrav\genTalons\thrift"
CALL :generateJavaSource muzdrav\outputInfo\thrift"
CALL :generateJavaSource muzdrav\reception\thrift"

COLOR 2
ECHO All sources successfully generated
PAUSE
EXIT

:generateJavaSource
	PUSHD %home_dir%%1
	ECHO Executing script %cd%\gen.bat
	CALL gen.bat
	IF NOT %errorlevel% == 0 (
		COLOR 4
		ECHO Generation failed with code %errorlevel%
		PAUSE
		EXIT 1
	)
	POPD
	EXIT /B
