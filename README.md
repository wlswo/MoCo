# MoCo (모각코)
> [!NOTE]
>  2023.07.15 AWS 프리티어 종료로 인해 현재 배포 중단 상태입니다.

<br>


## 개요
MoCo는 마크다운 에디터 기반의 스터디 모집 게시판 웹 애플리케이션입니다. 표준적인 Client-Server 아키텍처를 기반으로 하며, 이더리움 네트워크를 연동하여 일부 콘텐츠에 Web3 기술을 적용한 DApp(Decentralized Application) 프로젝트입니다.

## 개발 환경 및 기술 스택

### 환경
- **OS**: macOS (M1)
- **IDE**: IntelliJ IDEA, VS Code
- **Container**: Docker
- **DB Tool**: DBeaver

## 사용 기술 
| 분류 | 기술 상세 |
| :--- | :--- |
| **Backend** | Java 11, Spring Boot 2.7.2, Spring Security 5, Spring Data JPA, QueryDSL 5.0 |
| **Frontend** | JavaScript, Thymeleaf, Bootstrap 5, HTML5/CSS3 |
| **Database** | MySQL 8.0 |
| **Infra** | AWS (EC2, S3, RDS, ALB, Route53), Nginx, Docker, GitHub Actions |
| **Blockchain** | Solidity (^0.8.0), Web3.js, Goerli Testnet, MetaMask |

**라이브러리**

 - [web3.js](https://web3js.readthedocs.io/en/v1.8.0/getting-started.html)
 - [openzeppelin-ERC20](https://docs.openzeppelin.com/contracts/4.x/erc20)
 - [tippy.js](https://atomiks.github.io/tippyjs/v6/getting-started/)
 - [toastui-editor](https://ui.toast.com/tui-editor)
 - [tagify](https://yaireo.github.io/tagify/)
 - [xeicon](http://xpressengine.github.io/XEIcon/started.html)
 - [fabric](http://fabricjs.com/docs/)
 - [copy-down](https://github.com/furstenheim/copy-down)
 - [kakaoMaps Api](https://apis.map.kakao.com/)
 - [java smtp](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-mail/1.2.0.RELEASE)

 

## 시스템 아키텍처
![시스템 아키텍처](https://user-images.githubusercontent.com/103496262/200223486-3f5407ab-cf76-4acf-acae-a5760c9af559.png)


## Server 아키텍처 
![아키텍처](https://user-images.githubusercontent.com/103496262/197343496-40279a8a-7ee3-4360-b1e5-679379bacd90.JPG)


## E-R 다이어그램
![ERD](https://user-images.githubusercontent.com/103496262/226931609-b5e1b7f3-ca6e-4328-bd86-b3224d8e95e9.png)


## Api 명세서
### [Api 명세서 보기](https://moco.site/swagger-ui/index.html)


##  MoCo 릴리즈 노트

<details>
<summary> 2022-10-26 MoCo v1.1.0 릴리즈 보기</summary>
<div markdown="1">       

![카카오맵](https://user-images.githubusercontent.com/103496262/197948429-3426db43-9b92-4d01-a120-ad81d15956b8.JPG)
<br><br>
😎 카카오맵 Api를 추가, 적용했습니다. <br>
- 만남 장소를 검색하실 수 있습니다.
- 지도에 표시된 마커를 선택해 만남 장소를 정하실수 있습니다.
</div>
</details>
<details>
<summary> 2022-11-01 MoCo v1.2.0 릴리즈 보기</summary>
<div markdown="1">       
<br><br>
😎 로그인화면 진입시 자동으로 로그인창 애니메이션이 작동합니다.
- 로그인 접근성을 높였습니다. <br>
</div>
</details>
<details>
<summary> 2022-11-07 MoCo v1.3.0 릴리즈 보기</summary>
<div markdown="1">       
<br><br>
😎 NginX을 도입하여 무중단 배포 환경으로 업데이트 하였습니다.
</div>
</details>
<details>
<summary> 2023-03-22 MoCo v2.0.0 릴리즈 보기</summary>
<div markdown="1">       
<br><br>
😎 Native Query -> QueryDsl 마이그레이션 하였습니다.<br>
😎 쿼리 최적화를 위해 ERD 구조를 반정규화 하였습니다. 
</div>
</details>
<br><br>



## 프로젝트 목적
실서비스가 가능한 웹 애플리케이션 구축을 중점으로 배포와 설계, 기술적고민을 경험해 보는 것을 목표로 시작했습니다.<br>
블록체인 네트워크 연동을 통해 Web3 서비스를 구현해 보면서 Dapp 개발의 전반적인 지식을 쌓으려고 했습니다.



## 화면 구성💻
|![첫페이지](https://user-images.githubusercontent.com/103496262/197594246-1b8f38d6-5629-452f-9e0d-65cdc6f2165d.JPG)|![메인페이지](https://user-images.githubusercontent.com/103496262/197594248-86b5fbc9-758e-4dd3-9928-47fc6267bbc8.JPG)| ![로그인](https://user-images.githubusercontent.com/103496262/197594244-12bcb35c-7aac-44dd-a429-d37be4129cd8.JPG) | ![회원가입](https://user-images.githubusercontent.com/103496262/197594262-c83cf72d-7681-4ee8-9b3d-6bf75f1a8e2e.JPG) |
| :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: |
|                                                      첫 페이지                                                      |                                                       메인 페이지                                                        |                                                      로그인                                                       |                                                     회원가입                                                      |

| ![글작성](https://user-images.githubusercontent.com/103496262/197594251-a6cb939c-5610-4f34-8fea-665866e2b1a4.JPG) | ![게시글 보기](https://user-images.githubusercontent.com/103496262/197594254-9aa7b7eb-e282-445b-a82b-a7645fdc8c0a.JPG) | ![출석체크보상 토큰받기](https://user-images.githubusercontent.com/103496262/197594242-c692895c-90f2-47cb-b3e3-30ddc58d100a.JPG) | ![설정페이지](https://user-images.githubusercontent.com/103496262/197594859-ead2455a-d3b1-4ec0-8ef4-19f1dff163b4.JPG) |
| :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: |
|                                                      글작성 페이지                                                      |                                                       게시글 보기                                                        |                                                      출석체크 보상                                                        |                                                     설정 페이지                                                      |

<br>

| ![도트맵](https://user-images.githubusercontent.com/103496262/197594258-1ed03775-c6ca-4b0d-bac4-0f1d4fe41abd.png) | ![땅구매 모달창](https://user-images.githubusercontent.com/103496262/197594259-241a9d1b-5463-40cb-8de2-1ad91b2ebe87.JPG) | ![구매된 땅 표시](https://user-images.githubusercontent.com/103496262/197594231-cd4a6cf7-40a4-468f-852f-75c7e0275a00.png) |
| :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: |
|                                                     도트맵 페이지                                                      |                                                       땅구매 모달창                                                        |                                                      구매된 땅 표시                                                      |

<br>

## 기능
### 로그인
- 소셜 로그인
    - 소셜 로그인 구현을 위해 스프링 시큐리티와, OAuth2 인증방식을 사용했으며, 엑세스 토큰으로 받아오는 유저 정보를 커스텀하여 사용하기 위해 Oauth2UserService 인터페이스를 상속받아 CustomOauth2UserService 클래스를 구현하였습니다. <br> [CustomOauth2UserService](https://github.com/JaeJinByun/MoCo/blob/986566e2fe78b7bab74394fa0f3650f85186adc2/src/main/java/com/board/board/service/user/CustomOAuth2UserService.java#L25)
- 일반 로그인
    -  자체 로그인 방식으로는 회원가입시 입력한 비밀번호를 해시 암호화 알고리즘을 적용하여 나온 해시값을 DB에 저장합니다.
    - 로그인시 사용한 해시 알고리즘을 찾아 비밀번호의 정합성을 검증합니다. <br> [UserService](https://github.com/wlswo/MoCo/blob/df49bb214d4e8429045f7a1b1f808d82c8189235/src/main/java/com/board/board/service/user/CustomOAuth2UserService.java#L24-L70)

### Toast Ui editor

&ensp;&ensp;**이미지 저장**
- 게시글 작성은 NHN에서 개발한 오픈소스로, 마크다운과 위지윅 방식을 지원하는 생산성있는 라이브러리를 이용했습니다.
- 게시글 작성시 이미지 삽입은 base64형식의 단점을 보완한 hooks 옵션을 사용했습니다.
- 이미지의 base64 인코딩의 단점은 해상도에 따라 글자수가 기하급수적으로 올라가기 때문에 DB저장시 문제가 발생합니다.
- 따라서 이미지의 저장된 주소값인 url을 저장하도록 했습니다.

1. 이미지 삽입시 hooks 옵션으로 blob 객체를 인터셉터합니다.
2. Ajax로 blob 객체를 백엔드 서버로 저장 요청을 보냅니다.
3. 백엔드 서버에서 blob 객체를 multipart로 받아 aws s3 버킷에 저장 요청을 보냅니다.
4. 저장에 성공시 업로드된 url을 반환합니다.

[이미지 업로드 요청](https://github.com/wlswo/MoCo/blob/df49bb214d4e8429045f7a1b1f808d82c8189235/src/main/resources/static/js/board/write.js#L11-L38)

[이미지 업로드 처리](https://github.com/wlswo/MoCo/blob/df49bb214d4e8429045f7a1b1f808d82c8189235/src/main/java/com/board/board/controller/AwsS3Controller.java#L18-L23)


### 게시글 CRUD

<div align="center">
<img src="https://user-images.githubusercontent.com/103496262/207412930-f2621329-1720-4f19-ab1e-9600b6e09139.gif" width="700" height="450"/>
</div>

&ensp;&ensp;**글작성**
- Toast Ui editor로 작성한 마크다운 문법을 html 로 파싱하여 저장하였습니다.
- 게시글 저장시 각 항목에 대한 유효성을 검사합니다. 에러 발생시 작성했던 내용들은 유지하면서 에러 내용또한 반환하도록 했습니다.
    - [게시글 저장](https://github.com/wlswo/MoCo/blob/4562d81e46760a2f5b0234e9f3629e65077abcdc/src/main/java/com/board/board/controller/BoardController.java#L164-L188)
- tagify 라이브러리를 이용하여 태그를 작성할수 있습니다.  태그들은 Json 포맷으로 받아오며 ArrayList 자료구조로 파싱하여 한개의 태그당 한개의 컬럼으로 DB에 저장합니다.
    - [태그 저장](https://github.com/wlswo/MoCo/blob/fc6398cb0ac90d1c976368061d1f1119985bce45/src/main/java/com/board/board/controller/BoardController.java#L224-L243)

<div align="center">
<img src="https://user-images.githubusercontent.com/103496262/208051706-149f1e1a-6bde-40f7-b7cf-32944f9c233b.gif" width="650"    height="250"/>
 </div>

&ensp;&ensp;**글 수정**
- 게시글 본문의 내용을 DB에 저장시 마크다운으로 저장해야 할지, Html태그로 변환후 저장해야 할지 고민이 많았습니다. 해당 이슈는 아래 링크에서 확인하실수 있습니다.
    -  [게시글 수정시 Toast editor에 Html 코드가 나오는 이슈](https://zinzae.notion.site/Html-daf08a98466842f1a7cc36929a0cc80a)
- 게시글 수정은 빈번하게 발생하는 작업이 아니기 때문에 html 태그로 저장후 게시글 수정시에 마크다운 문법으로 파싱하도록 구현하였습니다.
    - [Html -> MarkDown](https://github.com/wlswo/MoCo/blob/aac3cfc12037ee7ea8085600f39f324009ecfe1f/src/main/java/com/board/board/service/board/MarkDownService.java#L6-L14)
-  태그 수정시 삭제된 태그, 추가된 태그를 구분하기 위해 hashSet 자료구조의 차집합을 구할수 있는 removeAll() 메소드를 사용하였습니다.
    -   [태그 수정](https://github.com/wlswo/MoCo/blob/aac3cfc12037ee7ea8085600f39f324009ecfe1f/src/main/java/com/board/board/service/hashTag/HashTagService.java#L73-L93)

&ensp;&ensp;**글 삭제**
- [자신이 작성한 게시글만 삭제 가능하도록 세션을 이용해 검증하였습니다. ](https://github.com/JaeJinByun/MoCo/blob/fc6398cb0ac90d1c976368061d1f1119985bce45/src/main/java/com/board/board/controller/BoardController.java#L308)

&ensp;&ensp;**게시글 읽기**
- [조회수 중복 방지를 위해 쿠키를 사용하여 조회수 증가를 방지했습니다.](https://github.com/wlswo/MoCo/blob/aac3cfc12037ee7ea8085600f39f324009ecfe1f/src/main/java/com/board/board/service/board/CookieService.java#L15-L41)

### 댓글과 대댓글 구현

대댓글의 경우 엔티티 구조안에서 셀프조인을 참조하여 계층을 가지도록 구현했습니다.
- [대댓글 계층정렬](https://github.com/JaeJinByun/MoCo/blob/fc6398cb0ac90d1c976368061d1f1119985bce45/src/main/java/com/board/board/service/board/CommentService.java#L88)

### 에러처리

로그인, 회원가입시 에러가 나는경우 이를 처리하기위해 커스텀 클래스를 구현했으며,
로그인 관련 에러와 회원가입시 에러에 맞게 처리하였습니다.
발생한 에러들은 추적이 가능하도록 Log를 남겨 CloudWatch 모니터링 시스템을 이용하여 실시간으로 확인 가능하도록 처리했습니다.
![로그이미지](https://user-images.githubusercontent.com/103496262/197615356-d0dd6dc6-79a8-4706-ab62-fa12b38a24d5.JPG)

[CustomAuthFailureHandler](https://github.com/JaeJinByun/MoCo/blob/c93bdc6252c5580679b9a58b3bd5f5a4c2789990/src/main/java/com/board/board/config/auth/CustomAuthFailureHandler.java#L23)

### 페이징 처리
- QueryDsl를 사용하여 페이징을 구현하였습니다. 
    - 런타임 환경에서 발생할 수 있는 쿼리 오류 방지와, SQL 안티패턴인 서브쿼리 사용을 지양하기 위해 기존의 Native Query 를 QueryDsl로 마이그레이션 하였습니다. 
    - [기존의 Native Query](https://github.com/wlswo/MoCo/blob/986566e2fe78b7bab74394fa0f3650f85186adc2/src/main/java/com/board/board/repository/BoardRepository.java#L21-L84)
    - [QueryDsl](https://github.com/wlswo/MoCo/blob/df49bb214d4e8429045f7a1b1f808d82c8189235/src/main/java/com/board/board/repository/BoardRepositoryCustom.java#L21-L78)

- ajax를 이용한 무한스크롤
    - 편한 스크롤링으로 콘텐츠가 로드되는 방식으로 특히 모바일 환경에서 더 나은 사용자 경험을 주며,콘텐츠의 노출을 쉽게 접할수 있도록 일반적인 페이지네이션을 두고 무한스크롤을 구현했습니다.
    - [자바스크립트 무한스크롤](https://github.com/JaeJinByun/MoCo/blob/c93bdc6252c5580679b9a58b3bd5f5a4c2789990/minify%20%EC%A0%81%EC%9A%A9%EC%A0%84%20JS%20%ED%8C%8C%EC%9D%BC/js/board/list.js#L1)

- 스크롤 위치와 데이터 유지
    - 무한 스크롤의 단점인 렌더링된 게시글들과 스크롤링 했던 위치로 돌아올수 있도록 세션스토리지를 이용하였습니다.
    - 게시글 리스트페이지를 벗어날때 세션스토리지에 스크롤의 위치와 렌더링 정보를 저장후 해당 페이지에 돌아왔을때 세션 스토리지에 저장된 값을 렌더링하며 이전 스크롤의 위치로 이동하도록 구현했습니다.
    -  [렌더링, 스크롤 위치기억하기](https://github.com/JaeJinByun/MoCo/blob/c93bdc6252c5580679b9a58b3bd5f5a4c2789990/minify%20%EC%A0%81%EC%9A%A9%EC%A0%84%20JS%20%ED%8C%8C%EC%9D%BC/js/board/list.js#L169)
### 도트맵 렌더링
- 도트맵에서 구매된 땅을 탐색해 랜더링을 할수 있도록 자바스크립트로 구현하였습니다.
    - [자바스크립트 도트맵](https://github.com/JaeJinByun/MoCo/blob/ed6ded6a934558c857f09753683b0e040bb9667a/minify%20%EC%A0%81%EC%9A%A9%EC%A0%84%20JS%20%ED%8C%8C%EC%9D%BC/js/earth/earth.js#L1)

### 스마트 컨트랙트 & Web3
- openzeppelin 라이브러리를 이용하여 웹 애플리케이션에서 사용할수 있는 ERC-20 기반 토큰을 만들었습니다.
    - [스마트 컨트랙트](https://github.com/JaeJinByun/MoCo/blob/762a33a28dfa570ffbad86c4778efd070586a1d3/MyToken.sol#L5)

- Web3.js 라이브러리를 이용하여 배포한 컨트랙트를 호출하여 토큰을 받을 수 있습니다.
    - [자바스크립트 컨트랙트 호출](https://github.com/JaeJinByun/MoCo/blob/762a33a28dfa570ffbad86c4778efd070586a1d3/minify%20%EC%A0%81%EC%9A%A9%EC%A0%84%20JS%20%ED%8C%8C%EC%9D%BC/js/web3js/web3js.js#L533)

<div align="center">
<img src="https://user-images.githubusercontent.com/103496262/207412942-97a71539-5ba3-4b46-89d6-070c1f12778b.gif" width="700" height="450"/>
</div>

- 도트로 구성된 땅은 토큰으로 구매할수 있습니다. 도트는 지역별로 가격이 다르며 중앙에 위치할수록 비싸집니다.
    -  [스마트 컨트랙트의 구매 함수](https://github.com/JaeJinByun/MoCo/blob/ed6ded6a934558c857f09753683b0e040bb9667a/MyToken.sol#L70)
-  구매 과정은 구매 함수를 호출후 해당 트랜잭션이 상태가 pendding -> success  될때까지 1초마다 감지하도록 구현했습니다.
    - [트랜잭션 상태 감지](https://github.com/JaeJinByun/MoCo/blob/ed6ded6a934558c857f09753683b0e040bb9667a/minify%20%EC%A0%81%EC%9A%A9%EC%A0%84%20JS%20%ED%8C%8C%EC%9D%BC/js/web3js/web3js.js#L629)

<div align="center">
<img src="https://user-images.githubusercontent.com/103496262/207412856-ea55ff27-d3ca-48f7-969f-94adfd9837ae.gif" width="700" height="450"/>
</div>

- 출석체크 보상, 가입시 보상 모달창을 통해 모각코인을 메타마스크에 추가할수 있도록 구현했습니다.
    - [모각코인 추가](https://github.com/JaeJinByun/MoCo/blob/ed6ded6a934558c857f09753683b0e040bb9667a/minify%20%EC%A0%81%EC%9A%A9%EC%A0%84%20JS%20%ED%8C%8C%EC%9D%BC/js/web3js/web3js.js#L33)

<div align="center">
<img src="https://user-images.githubusercontent.com/103496262/207412824-c2afb1ac-ff3a-447b-b48d-627069290c48.gif" width="700" height="450"/>
</div>



### CI/CD
반복적인 프로세스를 줄이고 유지보수의 편리함을 위해 지속적 통합과 지속적 배포 파이프라인을 구축하였습니다.

깃 허브액션이 master 브랜치로 push된 code를 감지하여 생성한 스크립트 설정파일에 따라 jar 파일로 빌드후 도커 이미지화 시킵니다.

도커 이미지화된 도커파일은 도커 이미지 저장소인 도커 허브로 push 됩니다.

push가 완료되면 ec2 인스턴스가 해당 도커 파일을 pull 하게 되며 실행중이었던 스프링부트 도커파일을
정지 -> 컨테이너 삭제 -> 새로 내려받은 도커파일 실행 순으로 작업을 진행합니다.

### 무중단 배포

nginx를 도입하여 무중단 배포 환경을 구현하였습니다.
- 새로운 버전의 애플리케이션이 배포되면 사용하지 않는 포트를 확인하여 docker로 구동시킵니다.
    - ex)  8080포트가 사용중이면 8081 / 8081번 포트가 사용중이면 8080으로 구동시킵니다.
- 구동된 앱의 health 를 체크하여 서버의 상태를 확인합니다.
-  정상 배포가 확인되면 nginx의 리버스 프록시 대상을 신규 버전의 앱의 포트로 변경합니다.
-  사용중이던 컨테이너와 이미지를 삭제 하여 서버 교체를 마무리 합니다.

[배포 스크립트](https://github.com/JaeJinByun/MoCo/blob/a4cada0878e963cb8260effbf357f7eaced211cc/.github/workflows/gradle.yml#L59-L111)


### 도메인 https 적용
브라우저의 안전한 접속과 , 브라우저 사이의 민감한 정보를 주고받을때를 대비해 aws ACM인증서를 받아 SSL 을 적용하였습니다.
가비아에서 구매한 도메인과 aws Route53을 이용해 도메인을 연결하였습니다.

---
### UI/UX Reference

- [좋아요 버튼](https://codepen.io/aaroniker/pen/BaZJMjv)
- [슬라이드 효과](https://swiperjs.com/swiper-api)
- [로그인 효과](https://codepen.io/karlovidek/pen/aNYWKE)
- [소셜 로그인 아이콘](https://codepen.io/RajRajeshDn/pen/qXeJOG)
- [구매된 도트의 모달창](https://codepen.io/lamchang/pen/PQGQyR)
