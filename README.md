# Project Setting

## Pull Request
- feature 브랜치 작성 및 remote 생성
- push하고 이를 Pull request 생성
- Approve 되면 Merge 하기

## swagger
- http://localhost:8080/swagger-ui/index.html

## h2
- jdbc:h2:mem:mydb
- http://localhost:8080/h2-console/

## 빌드
- ./gradlew -x test build
- admin/build/libs/admin-0.0.1-SNAPSHOT.jar를 수행 java -jar admin-0.0.1-SNAPSHOT.jar

## 인증/인가

- login용 기본 계정 제공 (admin/admin) access token 을 발급 받고 
- http://localhost:8080/swagger-ui/index.html 에서  자물쇠 모양을 누르고 Bearer + 한칸 뛰고 + access token 으로 로그인 수행 (귀찮다면 debug를 입력)
