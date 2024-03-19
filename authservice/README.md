# Auth Server

## 서버 설명 
Spring Boot 기반으로 Spring Security 라이브러리를 사용하여 동작하는 **인증 서버**입니다.

**인증 서버**는 사용자를 식별하고 인증/인가 작업을 집중적으로 수행합니다. 사용자의 인증 정보(email, pw), 권한 및 토큰들을 저장하기 위한 Database를 하나 사용합니다. 

사용자 상태 유지를 위해서 SESSION 로그인 방식이 아닌 JWT 인증 방식을 채택하였고, 그 과정에 대해서는 아래 링크에서 배경을 설명해 놨습니다.

## 패키징 구조 

```
eventty
└── authservice
    ├── api  # Server API 통신 
    │   ├── config  # 구성 설정
    │   ├── dto
    │   │   ├── request
    │   │   └── response
    │   ├── exception  # Custom API Exception
    │   ├── interceptor  # Custom Request Interceptor
    │   └── utils  
    ├── applicaiton  # 사용자나 외부 시스템에 제공하는 
    │   ├── dto  # Business Logic 내에서만 활용되는 dto
    │   └── service  # 사용자가 요청한 기능을 수행
    │       ├── Facade  # 사용자가 요청한 기능을 수행하는 상위 객체
    │       ├── subservices  # 세부 기능을 수행하는 하위 객체의 집합
    │       │   └── factory  # 팩토리 패턴에서 사용되는 객체들의 집합
    │       │       └── config  # 팩토리 패턴에서 적용되는 설정
    │       └── utils  # 서비스들의 보조 작업을 수행하는 보조 서비스
    │           └── token  # 토큰 관련 작업을 수행하는 서비스들의 집합
    ├── domain  # 도메인과 관련된 패키지
    │   ├── Enum  # 도메인 관련 처리를 위한 Enum 객체
    │   ├── entity  # 도메인 Entity (고유 식별자를 갖는 객체)
    │   ├── exception  # 도메인 처리 로직에서 발생한 Custom Exception
    │   ├── model  # 도메인 Model
    │   └── repository  # Domain 모델의 영속성 관리 (Interface)
    ├── global  # 전역적으로 사용되는 객체들을 위한 패키지 (API Call, Business Logic)
    │   ├── Enum  # Error Code, Success Code 
    │   ├── exception  # Auth Exception (최상위 Custom Exception)
    │   ├── response  # 전역적으로 사용되는 Response 객체 집합
    │   └── utils  
    ├── infrastructure  #  외부 시스템과의 연동, 데이터베이스, 요청/응답 처리, ... 등과 관련된 코드의 집합체
    │   ├── Filter  # 요청/응답 처리 및 후속 처리
    │   ├── advice  # 최종 Request Entity 처리 영역
    │   ├── annotation  # Custom Annotation
    │   ├── config  # Server에서 사용되는 전반적인 설정
    │   ├── context  # Cotnext 객체
    │   ├── contextholder  # Context 객체를 저장해두는 Holder
    │   └── utils  # Business Logic에서 사용되는 전반적인 보조 서비스의 집합
    └── presentation  # 사용자의 인터페이스(UI)나 API 엔드포인트를 관리하는 패키지
        └── dto
        │   ├── request
        │   └── response
        └── controller
```

## 적용한 디자인 패턴 

- **싱글톤 패턴**(Singleton Pattern)
- **파사드 패턴**(Facade Pattern)
- **프록시 패턴**(Proxy Pattern)
- **팩토리 패턴**(Factory Pattern)
- **전략 패턴**(Strategy Pattern)

## 주요 작업 

- 서버간의 REST API 통신 → RestTemplate
- RestTemplate Interceptor
- Global Interceptor, AOP
- Spring Security
- Thread Local(User Context)
- Token (JWT, Refresh Token, CSRF Token)
- Response Format 통일
- Custom Converter, Mapper 적용
- Social Login
- 회원 관련 기능들

## 자세한 내용 

- [Eventty: Auth Server](https://www.notion.so/Auth-Server-4c8f679eda524741be1f463399b18f17)