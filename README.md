```
__________.__                .__                                
\______   \  |   ____   ____ |  |__   ____  __ __  ______ ____  
 |    |  _/  | _/ __ \_/ __ \|  |  \ /  _ \|  |  \/  ___// __ \ 
 |    |   \  |_\  ___/\  ___/|   Y  (  <_> )  |  /\___ \\  ___/ 
 |______  /____/\___  >\___  >___|  /\____/|____//____  >\___  >
        \/          \/     \/     \/                  \/     \/ 
        
```

## About
Cerberus는 JWT(JSON Web Tokens)와 Spring Security를 사용하는 완전히 상태 비저장(stateless) RESTful 기반의 인증 시스템입니다.

## 주요 특징
- Spring Boot 기반의 RESTful API 서버
- JWT 기반의 상태 비저장 인증 시스템
- Spring Security를 활용한 역할 기반 접근 제어
- 파일 업로드/다운로드 기능 지원
- 사용자 관리 및 인증 시스템
- 테스트 코드 포함

## 기술 스택
- Java 8 이상
- Spring Boot
- Spring Security
- Spring Data JPA
- JSON Web Token (JWT)
- Maven

## 시작하기
### 요구사항
- Java 8 이상
- Maven 3.x
- Redis (세션 관리용)

### 설치 및 실행
1. 프로젝트 클론
```bash
git clone https://github.com/bleehouse/Bleehouse-Cerberus.git
cd Bleehouse-Cerberus
```

2. 애플리케이션 실행
```bash
mvn spring-boot:run
```
서버는 `http://localhost:8080/api/`에서 실행됩니다.

## API 엔드포인트
### 인증 관련
- `POST /api/auth` - 로그인 및 토큰 발급
- `GET /api/auth/refresh` - 토큰 갱신
- `POST /api/auth/register` - 새 사용자 등록

### 보호된 리소스
- `GET /api/protected` - 관리자 전용 접근
- `GET /api/test` - 테스트 엔드포인트
- `POST /api/files` - 파일 업로드
- `GET /api/files/{fileName}` - 파일 다운로드

### 사용 예시
1. 인증 토큰 받기:
```bash
curl -i -H "Content-Type: application/json" -X POST -d '{"username":"admin","password":"admin"}' http://localhost:8080/api/auth
```

2. 보호된 리소스 접근:
```bash
curl -i -H "Content-Type: application/json" -H "X-Auth-Token: [your-token]" -X GET http://localhost:8080/api/protected
```

## 보안 정책
- 토큰은 7일 후 만료됩니다.
- 만료되지 않은 토큰은 `/api/auth/refresh`를 통해 갱신할 수 있습니다.
- 모바일 디바이스용 토큰은 만료 후에도 갱신이 가능합니다.

## 테스트
테스트 실행:
```bash
mvn clean package
```

## 기여하기
프로젝트에 기여하고 싶으시다면 `CONTRIBUTING.md` 문서를 참고해 주세요.
