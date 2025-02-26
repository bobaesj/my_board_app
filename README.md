# MyBoardApp

## 1. 개요

지난 프로젝트에서 내가 담당한 부분뿐만 아니라, **전체적인 시스템에 대한 이해**가 필요하다고 느껴 학습을 시작하게 되었다.  
특히, 게시판의 백엔드 구현을 담당하며 **협업 환경**을 고려하여 브랜치를 다음과 같이 나누어 관리하며 개발을 진행했다:


## 2. 파일 구조

```
src/main/java
└── com
    └── bit
        └── myboardapp
            ├── config                // 보안 시스템 설정
            │   └── SecurityConfiguration.java    // 보안 및 인증 설정 (JWT 등)
            ├── controller            // REST API 컨트롤러 (사용자 요청 처리)
            │   ├── BoardController.java          // 게시판 관련 API
            │   ├── NotificationController.java   // 실시간 알림 API
            │   └── UserController.java           // 사용자 관련 API
            ├── dto                   // 데이터 전송 객체 (DTO)
            │   ├── BoardDto.java                 // 게시판 DTO
            │   ├── BoardFileDto.java             // 게시판 파일 DTO
            │   ├── CommentDto.java               // 댓글 DTO
            │   ├── NotificationDto.java          // 알림 DTO
            │   ├── ResponseDto.java              // 공통 응답 DTO
            │   └── UserDto.java                  // 사용자 정보 DTO
            ├── entity                // JPA 엔티티 (DB 테이블 매핑)
            │   │   └── converter     // enum, DB 간 변환 처리
            │   │   └── enums         // enum 타입 관리
            │   ├── Board.java                    // 게시판 엔티티
            │   ├── BoardFile.java                // 게시판 파일 엔티티
            │   ├── Comment.java                  // 댓글 엔티티
            │   ├── CustomUserDetails.java        // 사용자 인증 정보 엔티티
            │   ├── Notification.java             // 알림 엔티티
            │   └── User.java                     // 사용자 엔티티
            ├── exception              // 예외 처리 관리
            ├── jwt                    // JWT 인증 관련 폴더
            ├── repository             // JPA를 활용한 데이터 접근 계층 관리
            │   ├── BoardFileRepository.java       // 게시판 파일 레포지토리
            │   ├── BoardRepository.java           // 게시판 레포지토리
            │   ├── CommentRepository.java         // 댓글 레포지토리
            │   ├── NotificationRepository.java    // 알림 레포지토리
            │   └── UserRepository.java            // 사용자 레포지토리
            ├── service                // 서비스 계층 (비즈니스 로직)
            │   │   └── impl (각 서비스의 구현 클래스)
            │   ├── BoardService.java             // 게시판 관련 서비스
            │   ├── NotificationService.java      // 알림 관련 서비스
            │   └── UserService.java              // 사용자 관련 서비스

src/main/resources
└── application.properties           // 애플리케이션 설정 파일 (gitignore로 인해 숨겨짐)

```

## 3. 브랜치 구조

**`main` 브랜치**
   -  **검증된 최종 코드를 병합하여 배포하는 브랜치**

**`develop` 브랜치**
   -  **개발 중인 코드와 기능 브랜치를 병합하는 중심 브랜치**

**`feature` 브랜치**
   -  **실제 개발을 진행하며 완료 시 develop에 병합하는 브랜치**

무엇을
왜
어떻게
차이점
느낀점
