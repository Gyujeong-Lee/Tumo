#!/bin/bash

REPOSITORY=/home/ubuntu/tumo
PROJECT_NAME=S05P13A302

cd $REPOSITORY/$PROJECT_NAME/

cd frontend

echo "> Vue ���� ����"
npm install
npm run build
sudo cp -r dist /var/www/html

echo "> nginx �����"
sudo service nginx restart

cd ../backend

CURRENT_PID=$(pgrep -f .*.jar)
echo "���� �������� ���ø����̼� pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> ���� �������� ���ø����̼��� �����Ƿ� �������� �ʽ��ϴ�."
else
	echo "> kill -9 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 5
fi

echo "> SpringBoot ���� ����"
chmod +x mvnw
./mvnw clean package

cd target

JAR_NAME=$(ls -tr | grep jar | tail -n 1)

echo ">JAR ����: $JAR_NAME"

nohup java -jar $JAR_NAME > nohup.out 2>&1 &