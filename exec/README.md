##  개발환경

- **JVM, Maven**

  - openjdk version "1.8.0_192"  

    OpenJDK Runtime Environment (Zulu 8.33.0.1-win64) (build 1.8.0_192-b01)
    
  - Apache Maven 3. 6. 3 (AWS EC2 설치)
  
- **IDE**

  - BE - Spring Tool Suite 3.9.14
    - Maven 3.6.3 (STS Embeded)
  - FE - VS code 1.59.0
    - Node.js 14.17.5 LTS (includes npm 6.14.14)
    - yarn 1.22.10
    - Vue-CLI 4.5.13

- **DB**

  - MariaDB - 10.5.11
  - Default Schema - tumo

- **SERVER**

  - AWS EC2 
    - Ubuntu 20.04 LTS
    - SSH 접속 계정 - ubuntu@i5a302.p.ssafy.io
  - MariaDB
    - Hostname - i5a302.p.ssafy.io:3306
    - Username - root
    - password - tumo_A302
  - Web Server
    - nginx 1.18.0



## DB, 이메일 전송 서비스 설정 파일

- jdbc.properties 파일을 S05P13A302\backend\src\main\resources 경로로 이동  

  ~~~  properties
  #MariaDB Database setting
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.url=jdbc:mysql://i5a302.p.ssafy.io:3306/tumo?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8&allowMultiQueries=true
  spring.datasource.username=root
  spring.datasource.password=tumo_A302
  
  
  # mailjet apikey (200 mail per day)
  mail.useremail = ssafytumo@gmail.com
  mail.mailjet.api-key = [mailjet API KEY]
  mail.mailjet.secret-key = [mailjet SECRET KEY]
  
  # exec 폴더 첨부파일 jdbc.properties 기준 9월 1일 만료 api key가 기입됐습니다.
  ~~~

- exec 폴더의 "tumo dump.sql" DB 덤프 파일 실행

- jdbc.properties 첨부 파일의 메일 전송 서버 api key 만료시 https://www.mailjet.com/ 로그인 후 api-key 및 이메일 도메인 설정



## 소셜 로그인 정보

- Google Oauth

  - 로컬 FE 서버를 포트번호 3000으로 실행
  - EC2 nginx 포트 포워딩 3000으로 설정 https://i5a302.p.ssafy.io

  ![](img\구글 Oauth.PNG)



## 로컬 환경 빌드, 실행 방법

- BE - SpringBoot

  > 포트번호 8080으로 실행 http://localhost:8080 

  STS 3 - Run > Run As > Spring Boot App

- FE - Vue.js

  > yarn install
  >
  > yarn serve --port 3000
  >
  > 포트번호 3000으로 실행 http://localhost:3000



## AWS EC2 환경 빌드, 실행 방법

- BE - SpringBoot

  > ./mvnw clean package
  >
  > java -jar [생성된 jar파일].jar

- FE - Vue.js

  > npm install
  >
  > npm run build
  >
  > 빌드된 dist 디렉터리를 nginx에서 설정한 경로로 이동

- BE, FE 배포 스크립트 (exec 폴더 deploy.sh)

  ~~~ sh
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
  ~~~

   

