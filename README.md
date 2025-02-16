# 🛠 Spring Boot 기반 REST API 프로젝트

이 프로젝트는 **Spring Boot + REST API** 기반의 인증 시스템을 구현하기 위한 개인 공부용 프로젝트입니다.

## 📌 프로젝트 폴더 설명
- **OAuthJWT**: OAuth + JWT 기반 인증 및 토큰 발급 처리
- **OAuthSession**: 세션 기반 OAuth 관리
- **SessionSecurity**: 보안 관련 기능 (토큰 검증 및 세션 유지)
- **jwtMember**: JWT 기반 사용자 관리
- **board**: 공통 응답 및 예외 처리, JSON 응답 데이터 `null` 값을 빈 문자열 (`""`)로 변환

---


## ⚙️ 예외 처리
공통 예외 처리를 위해 `@RestControllerAdvice`를 활용합니다.
