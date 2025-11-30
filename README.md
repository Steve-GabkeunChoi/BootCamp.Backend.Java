# Spring Boot Signup Application

Java Spring Boot, Tailwind CSS, MariaDB를 사용한 회원가입 페이지 애플리케이션입니다.

## 기술 스택

- **Backend**: Java Spring Boot 3.1.5
- **Database**: MariaDB
- **Frontend**: Tailwind CSS (CDN)
- **ORM**: Spring Data JPA / Hibernate
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Java Version**: 17+

## 프로젝트 구조

```
signup-app/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── SignupApplication.java       # 메인 애플리케이션
│   │   │   ├── controller/
│   │   │   │   └── SignupController.java    # 회원가입 컨트롤러
│   │   │   ├── service/
│   │   │   │   └── UserService.java         # 사용자 서비스
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java      # JPA 리포지토리
│   │   │   └── model/
│   │   │       └── User.java                # 사용자 모델
│   │   └── resources/
│   │       ├── application.properties        # 애플리케이션 설정
│   │       ├── templates/
│   │       │   ├── index.html               # 메인 페이지
│   │       │   ├── signup.html              # 회원가입 페이지
│   │       │   └── success.html             # 완료 페이지
│   │       └── static/
│   └── test/
├── pom.xml                                   # Maven 설정
└── README.md
```

## 설치 및 실행

### 사전 요구사항 확인

#### Java 설치 및 검증

```bash
# Java 설치 확인
java -version

# 출력 예시:
# openjdk version "17.0.1" 2021-10-19
# OpenJDK Runtime Environment (build 17.0.1+12-39)

# Java를 설치하지 않았다면:
# macOS: brew install openjdk@17
# Windows: https://adoptium.net/
# Linux: sudo apt install openjdk-17-jdk
```

#### Maven 설치 및 검증

```bash
# Maven 버전 확인
mvn -v

# 출력 예시:
# Apache Maven 3.8.1
# Maven home: /usr/local/opt/maven/libexec
# Java version: 17.0.1

# Maven을 설치하지 않았다면:
# macOS: brew install maven
# Windows/Linux: https://maven.apache.org/download.cgi
```

### 1. Java 17+ 설치

**macOS (Homebrew):**
```bash
brew install openjdk@17
# 또는 구글의 openjdk
brew install temurin17
```

**검증:**
```bash
java -version
javac -version
```

### 2. Maven 설치

**macOS (Homebrew):**
```bash
brew install maven
```

**검증:**
```bash
mvn -version
```

### 3. MariaDB 실행

```bash
# macOS (Homebrew)
brew services start mariadb

# 또는 Docker
docker run -d -p 3306:3306 --name mariadb \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=signup_db \
  mariadb:latest
```

**검증:**
```bash
mariadb -u root -p -e "SELECT 1;" && echo "✅ MariaDB 연결 성공"
```

### 4. 데이터베이스 및 사용자 생성

```bash
mariadb -u root -p
# 프롬프트에서:
CREATE DATABASE IF NOT EXISTS signup_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'signup'@'localhost' IDENTIFIED BY 'signup_pass';
GRANT ALL PRIVILEGES ON signup_db.* TO 'signup'@'localhost';
FLUSH PRIVILEGES;
```

### 5. 프로젝트 빌드

```bash
# 프로젝트 디렉터리에서:
cd /path/to/project

# 빌드 실행
mvn clean install

# 출력 예시:
# [INFO] BUILD SUCCESS
```

**검증:**
```bash
ls -la target/signup-app-*.jar && echo "✅ 빌드 성공"
```

### 6. 애플리케이션 실행

```bash
mvn spring-boot:run
```

또는 JAR 파일로 실행:

```bash
java -jar target/signup-app-1.0.0.jar
```

**출력 예시:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.5)

2025-11-30T20:57:37.579+09:00  INFO ... Tomcat started on port(s): 8080 (http)
2025-11-30T20:57:37.584+09:00  INFO ... Started SignupApplication in 1.566 seconds
```

### 7. 애플리케이션 검증

브라우저 또는 curl로 검증하세요:

**브라우저:**
```
http://localhost:8080
```

**curl 검증:**
```bash
# 메인 페이지 접근 확인
curl -I http://localhost:8080/

# 출력:
# HTTP/1.1 200 OK

# 회원가입 페이지 접근
curl -I http://localhost:8080/signup

# API 테스트 (회원가입)
curl -X POST http://localhost:8080/api/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "테스트사용자",
    "email": "test@example.com",
    "password": "password123",
    "phone": "010-1234-5678",
    "age": 25,
    "agreeToTerms": true
  }'
```

## 기능

- ✅ 회원가입 폼 (이름, 이메일, 비밀번호, 전화번호, 나이)
- ✅ 클라이언트/서버 유효성 검사
- ✅ MariaDB에 사용자 데이터 저장 (JPA/Hibernate)
- ✅ 중복 이메일 검사
- ✅ 비밀번호 6자 이상 검증
- ✅ 약관 동의 확인
- ✅ 반응형 디자인 (Tailwind CSS)

## API 엔드포인트

### GET /
메인 페이지

### GET /signup
회원가입 페이지

### POST /api/signup
JSON 형식의 회원가입 요청
```json
{
    "name": "홍길동",
    "email": "example@email.com",
    "password": "password123",
    "phone": "010-1234-5678",
    "age": 25,
    "agreeToTerms": true
}
```

### GET /success
회원가입 완료 페이지

## 유효성 검사 규칙

- **이름**: 필수
- **이메일**: 필수, 유효한 이메일 형식, 중복 불가
- **비밀번호**: 필수, 최소 6자 이상
- **전화번호**: 필수, 올바른 형식 (예: 010-1234-5678)
- **나이**: 필수, 18세 이상 120세 이하
- **약관동의**: 필수

## 설정 파일

`application.properties`에서 MariaDB 연결 설정을 변경할 수 있습니다:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/signup_db
spring.datasource.username=signup
spring.datasource.password=signup_pass
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
```

## 라이선스

MIT License

## 개발자

Created with Spring Boot, Tailwind CSS, and MariaDB
