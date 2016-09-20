cd ../
echo [INFO]开始打包上传私服...

call mvn clean deploy -DskipTests=true

echo [INFO]打包上传私服结束

pause