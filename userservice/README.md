# MSA - User Server

---
### 패키지 구조
* DDD(Domain Driven Design) Layer
```bash
└─── UserService
            ├─── application # service
            │       └─── dto # Service 계층까지 사용하기에 해당 폴더에 넣어둠
            │             ├─── request # 요청 DTO
            │             └─── response # 응답 DTO
            ├─── domain # repository + entity
            │       ├─── annotation # Custom Annotation
            │       ├─── code # enum class : 각 SuccessCode 와 ErrorCode 명시
            │       └─── exception # 세부 exception
            ├─── infrastructure
            └─── presentation # 전역 예외처리 핸들러 + controller
                      ├─── dto # 최종 응답 DTO
                      └─── exception # 예외처리 관련 파일

```