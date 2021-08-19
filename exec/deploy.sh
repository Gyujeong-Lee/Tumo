#!/bin/bash

REPOSITORY=/home/ubuntu/tumo
PROJECT_NAME=S05P13A302

cd $REPOSITORY/$PROJECT_NAME/

cd frontend

echo "> Vue 빌드 시작"
npm install
npm run build
sudo cp -r dist /var/www/html

echo "> nginx 재시작"
sudo service nginx restart

cd ../backend

CURRENT_PID=$(pgrep -f .*.jar)
echo "현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
	echo "> kill -9 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 5
fi

echo "> SpringBoot 빌드 시작"
chmod +x mvnw
./mvnw clean package

cd target

JAR_NAME=$(ls -tr | grep jar | tail -n 1)

echo ">JAR 실행: $JAR_NAME"

nohup java -jar $JAR_NAME > nohup.out 2>&1 &