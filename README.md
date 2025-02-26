# MyBoardApp

## 1. 개요

지난 프로젝트에서 내가 담당한 부분뿐만 아니라, **전체적인 시스템에 대한 이해**가 필요하다고 느껴 학습을 시작하게 되었다. 특히, 게시판의 백엔드 구현을 담당하며 **협업 환경**을 고려하여 브랜치를 다음과 같이 나누어 관리하며 개발을 진행했다.


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

```
main                  # 최종 배포 브랜치
│
└── develop           # 모든 기능이 병합되는 개발 브랜치
   │         
   ├── feature/user             #  유저 관련 기능 개발 브랜치
   │   
   ├── feature/board            #  게시판 관련 기능 개발 브랜치
   │   
   └── feature/notification     #  알림 관련 기능 개발 브랜치

```

## 4. 사용기술/개발환경

**`ERD Cloud`**
   -  **ERD Cloud활용한 간단한 DB설계**

![ERD 다이어그램](https://drive.google.com/uc?export=view&id=1OZp78weXdnxBW8vrNe2dfx6jgKhPQ_M2)

**`MySQL Workbench`**
   -  **로컬 데이터베이스는 MySQL Workbench를 사용하여 관리**

**`Postman`**
   -  **기능 구현 테스트는 Postman을 활용**

**`Sourcetree`**
   -  **Git 작업은 Sourcetree를 이용**

**`Jwt`**
   -  **인증 및 보안 관리는 JWT를 사용**
   
## 5. 마무리하면서

MVC 아키텍처 및 계층형 구조의 장점과 중요성을 느낄 수 있었습니다. 특히, Controller, Service, Repository로 역할을 분리함으로써 유지보수가 쉽고 구조적으로 명확한 코드를 작성할 수 있었습니다.

또한, Spring Framework의 DI(의존성 주입)을 활용해 객체 간의 의존성을 효율적으로 관리하고 생산성을 높일 수 있었습니다. Spring Data JPA를 사용하면서 데이터 관리를 이해하는데 많은 도움이 되었습니다.

이번 기회를 통해 개발의 기본 원칙을 학습했고, 협업 환경에서의 효율적인 브랜치 전략(Git Flow)과 구조적인 설계의 중요성을 깨달을 수 있었습니다. 앞으로 더 나은 설계와 구현을 위해 이번 경험을 잘 활용하고 싶습니다.
