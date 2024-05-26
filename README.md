# 뉴스 데이터 수집 서비스
뉴스 데이터 수집 서비스 설계 및 API 개발

---

## 📚 사용 스택
- Spring Boot 3.2.5
- Java (JDK17)
- Gradle
- Junit5
- H2 Database
- JPA
- Mockit

--- 

<details>
<summary>테이블</summary>
<div markdown="1">

#### 1. Users (유저)
| 컬럼명         | 데이터타입         | 설명          
|-------------|---------------|-------------|
| id          | pk            | primary key |
| username    | String        | 이름         |
| email        | String        | 이메일       |
| createdAt      | LocalDateTime | 생성일시    |
| updatedAt      | LocalDateTime | 최종 수정일시 |

#### 2. UserToken (유저 토큰)

| 컬럼명            | 데이터타입         | 설명          
|----------------|---------------|-------------|
| id             | pk            | primary key |
| accessToken    | string        | accesss 토큰          |
| accessExpiredAt| LocalDateTime          | access 토큰 만료일       |
| refreshToken   | string          | refresh 토큰      |
| refreshExpiredAt | LocalDateTime          | refresh 토큰 만료일       |
| userId      | Long          | 유저 id       |
| createdAt      | LocalDateTime | 생성일시        |
| updatedAt      | LocalDateTime | 최종 수정일시     |

#### 3. Article (뉴스 기사)
| 컬럼명         | 데이터타입         | 설명          
|-------------|---------------|-------------|
| id          | pk            | primary key |
| title    | String        | 제목         |
| description  | String        | 내용         |
| description  | String        | 링크         |
| keyword        | String        | 키워드       |
| articleSource        | Enum        | 뉴스사이트  |
| createdAt      | LocalDateTime | 생성일시    |
| updatedAt      | LocalDateTime | 최종 수정일시 |

#### 4. ArticleTarget (뉴스 수집 설정)
| 컬럼명         | 데이터타입         | 설명          
|-------------|---------------|-------------|
| id          | pk            | primary key |
| keyword        | String        | 키워드       |
| articleSource        | Enum        | 뉴스사이트  |
| createdAt      | LocalDateTime | 생성일시    |
| updatedAt      | LocalDateTime | 최종 수정일시 |

</div>
</details>

<details>
<summary>API</summary>
<div markdown="1">

#### 1. 뉴스 기사 목록 조회
##### 정보
- 뉴스 기사 목록을 조회한다.
##### 요청
```json
GET /api/v1/articles
```

##### 응답
```json
{
  "result": "SUCCESS",
  "data": [
    {
      "id" : 1,
      "title" : "뉴스 제목1",
      "description" : "뉴스 설명1",
      "link" : "https://news.com",
      "keyword" : "개발"
      "articleSource" : "NAVER"
    },
    {
      "id" : 2,
      "title" : "뉴스 제목2",
      "description" : "뉴스 설명2",
      "link" : "https://news.com",
      "keyword" : "개발"
      "articleSource" : "DAUM"
    },
  ],
  "error": null
}
```

<br>

#### 2. 뉴스 기사 데이터 수집
##### 정보
- 키워드와 뉴스사이트 정보를 받아 뉴스기사 데이터를 수집해서 저장한다.
##### 요청
```json
POST /api/v1/articles
{
  "keyword" : "개발",
  "articleSource": "NAVER"
}
```

##### 응답
```json
{
  "result": "SUCCESS",
  "data": [
    {
      "id" : 1,
      "title" : "뉴스 제목1",
      "description" : "뉴스 설명1",
      "link" : "https://news.com",
      "keyword" : "개발"
      "articleSource" : "NAVER"
    },
    {
      "id" : 2,
      "title" : "뉴스 제목2",
      "description" : "뉴스 설명2",
      "link" : "https://news.com",
      "keyword" : "개발"
      "articleSource" : "NAVER"
    },
  ],
  "error": null
}
```

<br>

#### 3. 뉴스 수집 설정 목록 조회
##### 정보
- 뉴스 수집 설정 목록을 조회한다.
##### 요청
```json
GET /api/v1/targets
```

##### 응답
```json
{
  "result": "SUCCESS",
  "data": [
    {
      "id" : 1,
      "keyword" : "개발"
      "articleSource" : "NAVER"
    },
    {
      "id" : 2,
      "keyword" : "코드"
      "articleSource" : "DAUM"
    },
  ],
  "error": null
}
```

<br>

#### 4. 뉴스 수집 설정 등록
##### 정보
- 키워드, 뉴스 사이트 정보를 받아 수집 설정으 등록한다.
- 중복된 키워드로 등록이 불가능하다.
##### 요청
```json
POST /api/v1/targets
{
  "keyword" : "개발",
  "articleSource": "NAVER"
}
```

##### 응답
```json
{
  "result": "SUCCESS",
  "data": {
      "id" : 1,
      "keyword" : "개발"
      "articleSource" : "NAVER"
  },
  "error": null
}
```

<br>

#### 5. 뉴스 수집 설정 키워드 삭제
##### 정보
- id를 받아 등록된 키워드를 삭제한다.
##### 요청
```json
DELETE /api/v1/targets/{targetId}
```

##### 응답
```json
{
  "result": "SUCCESS",
  "data": null,
  "error": null
}
```

<br>

#### 6. 유저토큰 재발급
##### 정보
- refresh 토큰을 받아 토큰을 재발급한다.
##### 요청
```json
GET /api/v1/refresh
{
   "headers": Cookie refreshToken
}
```

##### 응답
```json
{
    "result": "SUCCESS",
    "data": null,
    "error": null
}
```

<br>

#### 7. 로그인 callback
##### 정보
- GitHub OAuth 인증 후 유저를 저장하고 토큰 정보를 포함한 URL로 리다이렉트한다.
##### 요청
```json
GET /signin/callback?code=code
```

<br>

</div>
</details>

### 기능구현
- 회원
  -  회원가입 및 로그인 기능은 GitHub의 OAuth를 사용하여 구현하였습니다.
  -  로그인 시 유저 토큰을 발급합니다.

- 뉴스
  - 키워드 등록 시 뉴스 사이트 정보는 네이버, 다음 또는 모두를 등록할 수 있도록 구현했습니다.
  - ```@Scheduled``` 기능을 사용하여 등록된 수집 설정 바탕으로 매일 자정에 뉴스 데이터를 수집하도록 구현했습니다.
 
- 데이터베이스
  - H2 인메모리 디비를 사용하여 구현했습니다.
  - 접속주소: ```http://localhost:8080/h2-console```
  - JDBC URL: ```jdbc:h2:mem:~/newsApplication```
  - username: ```sa```

### 사이트 성능 올릴 수 있는 방안
- 매일 자정마다 데이터를 수집하도록 설정했기 때문에, 새로운 키워드가 추가되지 않는 한 데이터 갱신은 자주 발생하지 않습니다. 이를 통해 뉴스 데이터를 캐시에 저장함으로써 데이터베이스의 쓰기 부하를 줄이고 응답 시간을 향상시킬 수 있습니다.
- 대량의 뉴스 데이터를 데이터베이스에 비동기적으로 저장할 수 있습니다. 뉴스 크롤링 요청을 메시지 큐에 게시하고, 이 큐를 별도의 서비스나 스레드가 폴링하여 데이터베이스에 저장합니다. 이 방법은 애플리케이션의 응답성을 향상시키고, 대용량 데이터 처리 시 사용자 요청 스레드가 데이터베이스 작업에 의해 차단되지 않도록 합니다.  
