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

## 게시판 기능 (Bulletin Board)

이번 업데이트로 게시판 기능이 추가되었습니다. 주요 기능과 사용법은 아래와 같습니다.

- **기능 요약**:
  - 게시글 목록 조회, 작성, 보기, 수정, 삭제
  - 페이징(Page) 및 검색(제목/작성자) 지원
  - REST API (JSON) 제공
  - 서버사이드 렌더링 템플릿(Thymeleaf) 제공

- **주요 엔드포인트 (템플릿/브라우저)**:
  - `GET /posts` : 게시글 목록 (쿼리: `page`, `size`, `q`)
  - `GET /posts/new` : 새 글 작성 폼
  - `POST /posts` : 새 글 생성 (폼 제출)
  - `GET /posts/{id}` : 게시글 보기
  - `GET /posts/{id}/edit` : 게시글 수정 폼
  - `POST /posts/{id}` : 게시글 업데이트
  - `POST /posts/{id}/delete` : 게시글 삭제

- **REST API (JSON)**:
  - `GET /api/posts?page=0&size=10` : 게시글 페이징 리스트
  - `GET /api/posts?q=검색어&page=0&size=10` : 검색(제목/작성자)
  - `GET /api/posts/{id}` : 단건 조회
  - `POST /api/posts` : 게시글 생성 (JSON body)
  - `PUT /api/posts/{id}` : 게시글 수정 (JSON body)
  - `DELETE /api/posts/{id}` : 게시글 삭제

- **템플릿 파일 (Thymeleaf)**:
  - `src/main/resources/templates/posts.html` — 게시글 목록
  - `src/main/resources/templates/new_post.html` — 새 글 작성
  - `src/main/resources/templates/post.html` — 게시글 보기
  - `src/main/resources/templates/edit_post.html` — 게시글 수정

- **주요 서버 코드 파일**:
  - `src/main/java/com/example/model/Post.java` — JPA 엔티티
  - `src/main/java/com/example/repository/PostRepository.java` — JPA 리포지토리 (검색/페이징 메서드 포함)
  - `src/main/java/com/example/service/PostService.java` — 게시글 비즈니스 로직 (페이징/검색 포함)
  - `src/main/java/com/example/controller/PostController.java` — 템플릿 핸들러 (브라우저)
  - `src/main/java/com/example/controller/PostRestController.java` — JSON REST API 핸들러

- **간단한 사용 예시 (curl)**:

  - 게시글 생성 (JSON):
    ```bash
    curl -X POST http://localhost:8080/api/posts \
      -H "Content-Type: application/json" \
      -d '{"title":"테스트 글","content":"본문 내용","author":"작성자"}'
    ```

  - 게시글 목록 조회 (페이징):
    ```bash
    curl 'http://localhost:8080/api/posts?page=0&size=10'
    ```

  - 게시글 검색:
    ```bash
    curl 'http://localhost:8080/api/posts?q=검색어&page=0&size=10'
    ```

이 변경사항은 저장소에 커밋되어 있으며, 서버를 재시작하면 바로 확인하실 수 있습니다.

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

## 최종 작업 현황 (2025-11-30)

### 완료된 작업 사항

1. **프로젝트 초기 구성**
   - Spring Boot 3.1.5 기반 회원가입 애플리케이션 구축
   - Tailwind CSS (CDN) 기반 반응형 UI
   - Thymeleaf 템플릿 엔진 통합

2. **데이터베이스 마이그레이션**
   - MongoDB에서 MariaDB로 완전 전환
   - 관계형 데이터베이스 구조 설계
   - Spring Data JPA + Hibernate ORM 통합
   - HikariCP 커넥션 풀 구성

3. **회원 관리 시스템**
   - 회원가입 폼 (이름, 이메일, 비밀번호, 전화, 나이, 약관동의)
   - 유효성 검사 규칙 적용
   - User 엔티티 및 UserRepository 구현
   - UserService 비즈니스 로직

4. **게시판 기능 (포스트)**
   - Post 엔티티 설계 (id, title, content, author, createdAt)
   - CRUD 작업: 생성, 조회, 수정, 삭제
   - 페이징 및 검색 기능 (제목/작성자 기반)
   - 2가지 인터페이스 제공:
     - HTML 템플릿 기반 웹 UI (`/posts` 경로)
     - REST JSON API (`/api/posts` 경로)

5. **템플릿 및 UI**
   - 게시판 목록: posts.html (페이지네이션, 검색, 삭제 버튼)
   - 게시글 상세보기: post.html
   - 게시글 작성: new_post.html
   - 게시글 수정: edit_post.html
   - 모든 템플릿 Thymeleaf 문법 에러 수정

6. **버그 수정**
   - Thymeleaf 템플릿 파싱 에러 해결 (posts.html, post.html)
   - 복잡한 날짜 포맷팅 표현식 단순화
   - 조건부 표현식 문법 수정

7. **문서화**
   - 상세한 설치 및 실행 가이드
   - Java, Maven, MariaDB 설치 및 검증 절차
   - 게시판 엔드포인트 및 사용 예제
   - REST API 명세 문서

8. **버전 관리**
   - GitHub 리포지토리 (Steve-GabkeunChoi/BootCamp.Backend.Java)
   - 총 10개 커밋 완료
   - 모든 변경사항 동기화됨

### 서버 상태 ✓

- **포트**: 8080
- **데이터베이스**: MariaDB (signup_db)
- **상태**: 정상 실행 중
- **테스트됨**: 모든 주요 엔드포인트 작동 확인

### 엔드포인트 요약

| 기능 | 메서드 | 경로 |
|------|--------|------|
| 회원가입 | GET/POST | /signup |
| 게시판 목록 | GET | /posts |
| 게시글 작성 폼 | GET | /posts/new |
| 게시글 작성 | POST | /posts |
| 게시글 상세보기 | GET | /posts/{id} |
| 게시글 수정 폼 | GET | /posts/{id}/edit |
| 게시글 수정 | POST | /posts/{id} |
| 게시글 삭제 | POST | /posts/{id}/delete |
| API 목록 | GET | /api/posts?page=0&size=10&q= |
| API 상세보기 | GET | /api/posts/{id} |

## 라이선스

MIT License

## 개발자

Created with Spring Boot, Tailwind CSS, and MariaDB
