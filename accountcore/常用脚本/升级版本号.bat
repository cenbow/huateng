echo off

echo [INFO]��������Ҫ�����İ汾:

set /p newVersion= 

echo [INFO]����%newVersion% ��ʼ�滻�汾
set var= ..\
cd %var%
call mvn clean versions:set -DnewVersion=%newVersion%

echo [INFO]������ģ�鶼�����汾�ɹ� �°汾Ϊ%newVersion%���Ƿ��ϴ�jar����˽��

pause

echo [INFO]��ʼ����ϴ�˽��...

call mvn clean deploy -DskipTests=true

echo [INFO]����ϴ�˽������

pause