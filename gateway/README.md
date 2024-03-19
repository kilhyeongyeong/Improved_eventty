# Gateway Server 

## 서버 설명
게이트웨이 서버는 웹서버와 서비스 서버들의 중간에서 통신을 전달해주는 역할을 하는 서버입니다.

이 과정에서 요청의 유효성 검증을 통해 진입점 역할을 수행하여 보안을 더욱 강화시켜 줍니다.

현 프로젝트에서 Eventty 웹 서버는 내부 통신에선 오로지 게이트웨이 서버하고만 통신하고 있기 때문에 서비스 서버들을 클라이언트로부터 숨겨주는 역할도 수행합니다.

현재 로드 밸런싱 역할을 수행하는 유레카 서버의 도입은 하지 않은 상태지만, 개발 환경에서 도커 DNS를 이용하여 작업을 하였기 때문에 언제든지 도입이 가능한 상태입니다.


## 패키징 구조

```
gateway
└── src
    └── main
        ├── java
        │   └── com
        │       └── eventty
        │           └── gateway
        │               ├── api  # Server API 통신
        │               │   ├── config  # 구성 설정 
        │               │   ├── dto  # Request, Response
        │               │   ├── exception  # Custom Exception
        │               │   └── utils  # API Service의 보조 
        │               ├── filter  # Filter 설정 및 사용하는 Custom Filter
        │               ├── global  # 전역적으로 사용되는 패키지
        │               │   ├── config  # 전역 설정
        │               │   ├── dto  # 전역적으로 사용되는 응답 Format 객체
        │               │   └── exception  # Global Exception 패키지
        │               │       └── auth  # JwtAuthenticationFilter에서 발생하는 Custom Exception
        │               └── utils  # 각종 보조 서비스 (Cookie Creator, Custom Mapper, ...)
        └── resources
            └── application.yml  # 게이트웨이의 설정 파일
            
```

## Basic Flow Chart

![Gateway Flow Chart](../../images/gateway_flowchart.png)

## Routing

![Gateway Rewrite Path](../../images/gateway_rewrite_path.png)

## Custom Filter

![Custom Filter](../../images/gateway_custom_filter.png)


## 주요 기능 

- Web Flux를 활용한 토큰 업데이트
- 하나의 요청에 분산 추적을 위한 고유 Tracking ID 부여
- Rewirte Path를 통해 각 서비스 서버에서 직관성 있는 REST API URL 사용
- 검증 API 요청을 통한 진입점 역할 수행

## 자세한 내용 

- [Eventty: Gateway Server](https://www.notion.so/Gateway-Server-2a2e134d4e6745399d50fa6a144f0d13)