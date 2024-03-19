<div align="center">
<img src="https://github.com/jeongbeomSeo/eventty/assets/90774229/e31ea0a7-6705-4579-b641-f062ed810606" width="300">

# eventty

<img src="https://img.shields.io/badge/React-18.2.0-61DAFB?logo=react"/>
<img src="https://img.shields.io/badge/TypeScript-4.9.5-3178C6?logo=typescript"/>
<img src="https://img.shields.io/badge/Platform-web-red"/>
</div>

## UI

### 메인
![eventty_main](https://github.com/jeongbeomSeo/eventty/assets/90774229/e170a2a0-c842-457c-94ca-34a348d66253)
- 로딩 시 Skeleton 구현
- Carousel 구현
- 조회수 기준 인기 행사
- 생성일 기준 신규 행사
- 마감일 기준 마감 임박 행사

### 로그인 & 소셜 로그인
|  ![eventty_login](https://github.com/jeongbeomSeo/eventty/assets/90774229/ec16566f-4969-4ce9-bfa3-9f08885afc2f)  |  ![eventty_google](https://github.com/jeongbeomSeo/eventty/assets/90774229/435238d4-ac02-49dd-8c2a-53033c7c2926)  |
|:----------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------:|
|                                                       로그인                                                        |                                                      구글 로그인                                                       |

### 카테고리 조회
![eventty_category](https://github.com/jeongbeomSeo/eventty/assets/90774229/20309df2-cf4e-42a7-903f-d697a7e11eed)

### 키워드 검색
![eventty_keyword](https://github.com/jeongbeomSeo/eventty/assets/90774229/7a1affdb-0535-4913-bee0-73fb0cfa60f0)
- 최근 검색어 기능

### 행사 등록
| ![eventty_write](https://github.com/jeongbeomSeo/eventty/assets/90774229/170923ca-3f7c-416d-9d2a-25163c12fe2f)  |  ![eventty_event](https://github.com/jeongbeomSeo/eventty/assets/90774229/b79c8911-eb53-4193-b35f-84f4f77af6ae)  |
|:---------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------:|
|                                                      행사 등록                                                      |                                                    등록한 행사 조회                                                     |

### 행사 신청
![eventty_booking](https://github.com/jeongbeomSeo/eventty/assets/90774229/94bac571-fb67-4997-baec-42f5e103197e)

### 행사 신청 내역
![eventty_applices](https://github.com/jeongbeomSeo/eventty/assets/90774229/1257dde0-4b20-4105-a8c4-f0bc7b9e9c0d)
- 필터링 기능

### Responsive
![eventty_responsive](https://github.com/jeongbeomSeo/eventty/assets/90774229/28ba69c6-2ca4-41da-96c1-a9385f1948e8)
- media query를 이용한 반응형 웹
- 모바일 전용 UI

## 사용한 기술

### TypeScript
<img src="https://img.shields.io/badge/TypeScript-4.9.5-3178C6?logo=typescript"/>
JavaScript의 단점을 보완하기 위한 정적 타입 언어
<br>
기존 JavaScript의 기능을 모두 사용 할 수 있으며 컴파일 과정을 거친다. 
<br>
JavaScript와 달리 런타임 전에 에러가 발생되므로 사전에 잘못된 함수나 값이 들어가는 것을 예방할 수 있다.
<br>
또한 IDE에서 코드 가이드와 자동완성 기능이 지원되어 개발 생산성이 올라가는 효과도 있다.

### Mantine
<img src="https://img.shields.io/badge/Mantine-6.0.18-339AF0?logo=mantine"/>
반응형 웹 구성을 하기 위한 UI 라이브러리
<br>
내장된 media query를 이용하여 모바일 환경에서도 대응할 수 있다.

### Styled Component
<img src="https://img.shields.io/badge/styled%20components-6.0.7-DB7093?logo=styledcomponents"/>
CSS를 컴포넌트화 시켜 React 내에서 사용 가능하게 하는 라이브러리
<br>
별도의 CSS 파일 작성 없이 TypeScript 내에서 컴포넌트 단위로 개발 할 수 있다. 

### React Router
<img src="https://img.shields.io/badge/React%20Router-6.14.2-CA4245?logo=reactrouter"/>
페이지 이동 처리를 위한 라이브러리
<br>
새로운 페이지를 반환 하지 않고 한 페이지 내에서 필요한 데이터와 컴포넌트만 변경되면서 렌더링 한다.  

### Recoil
<img src="https://img.shields.io/badge/Recoil-0.7.7-3578E5?logo=recoil"/>
상태 관리 라이브러리
<br>
atom을 통해 상태를 정의하며, 이는 하나의 컴포넌트에 귀속되지 않고 모든 컴포넌트에서 접근 가능하다. 
atom의 값이 set 되면 해당 atom을 구독하고 있던 모든 컴포넌트가 리렌더링 된다.

### React Hook Form
<img src="https://img.shields.io/badge/React%20Hook%20Form-7.45.4-EC5990?logo=reacthookform"/>
유효성 검사와 폼 관리를 위한 라이브러리
<br>
유효성 검사를 위한 코드를 단순화 시킬 수 있다. 별 다른 이벤트 핸들러 없이 내장된 Hook을 이용하여
데이터를 추적하고 관리할 수 있다.

### Quill
<img src="https://img.shields.io/badge/Quill-1.3.6-1D1E30"/>
WYSIWYG HTML 에디터
<br>
간편하게 사용할 수 있는 Rich Text Editor 이며, 하이퍼 링크, 글자 크기, 색상 등을 쉽게 구성할 수 있다.
