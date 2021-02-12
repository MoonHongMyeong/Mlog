# 포트폴리오용 블로그 토이 프로젝트

프론트 작업은 React, 백엔드 작업은 springboot를 이용해서 블로그 모양의 게시판을 만들어야겠다. <br/>
http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com/

참고 사이트 blex.me  
---
<br/>
<br/>

## 사용 기술 및 환경
|section|description|
|:--:|:--:|
|BACKEND|springboot 2.4.3(SNAPSHOT)<br/>java 11.0.9<br/>gradle 6.7.1<br/>spring data jpa<br/>Querydsl<br/>Lombok<br/>devtools|
|Testing<br/>tools|Junit5<br/>Google Truth<br/>TestRestTemplete
|DATABASE| H2database(dev only)<br/>MySQL 5.6|
|FRONTEND| React 17.0.1<br/>axios 0.21.1<br>react-router-dom 5.2.0<br/>styled-components 5.2.1|
|DEPLOY|Nginx<br/>EC2<br/>RDS|
---
<br/><br/>

## 아키텍쳐 의사결정 기록
### =프로그래밍 언어 : React, java, springboot
#### 국비지원 6개월 과정을 수료하고 나에게 가장 부족한 부분이 javascript를 다루는 것이라고 생각했고-jquery는 생각도 안했다.- 공부를 시작했다. 함수형 프로그래밍인 javascript는 java를 배울 때와는 다른 재미가 있었고 그러다 보니 리액트도 공부를 조금 했다. 처음에는 리액트만 활용한 프로젝트로 만들기 위해 시작했는데 그 동안 학원에서 배운 것도 써보고 싶었다. 학원에서는 java와 spring framework, mybatis를 배웠는데 더 공부를 하고 싶어서 springboot와 jpa를 선택했다.

### =데이터베이스 선정 : H2, MySQL
#### jpa를 공부하면서 H2를 알게 되었고 프로토 타입 제작용으로는 가벼워서 딱이라고 생각했다. H2는 실제 배포용으로는 잘 사용하지 않는다고 해서 Oracle과 MySQL 중에 고민을 했다. 오라클은 학원 수료과정 중 프로젝트 때 사용해 봤고 RDS에서 MariaDB나 MySQL을 더 사용한다고 했기 때문에 MySQL을 선택했다.

### =배포 : AWS
#### 처음 배포하는 방법을 공부할 때 AWS EC2 Linux AMI와 RDS를 활용한 방법이었고, 그대로 적용을 했다. 
#### *20210211 // Nginx로 무중단 배포는 되는 것 같은데, 미구현 한 것이 자동 배포하는 것을 못했다 아마 Travis를 사용하지 않을까 싶다.*

### =목표
#### 처음에는 리액트를 잘 다루고 싶어서 시작한 프로젝트였는데 JPA와 TDD에 흥미가 많이 생겨서 JPA를 사용하는 것과 junit테스트로 모든기능 100% 커버를 목표로 삼고있다.
#### 기능적인 목표는 markdown언어가 문서작업에 최적화 되어있는데 블로그에서는 글이 중심이니 깃허브의 markdown api를 가져오거나-이것은 횟수제한이 있다고 들었다- 직접 구현을 해보고싶다.
#### 리액트를 배우면서 만들었기 때문에 코드가 너무 지저분하고 프론트엔드도 테스팅 툴이 있다고 해서 그 부분도 나중에 리팩토링 할 때 적용할 예정이다.
---

<br/>
<br/>

## 생각중인 것들
#### 배포할 때는 회원기능이 개인정보관리 이런 것 때문에 문제 생길 것 같아서 구현할 생각을 안했었는데 막상 가진 부분이 게시판요소 밖에 없다보니 너무 심심해보여서 구현을 해야겠다고 생각중이다...
#### 일단은 블로그에서 필요한 가장 기본적인 기능 포스트 작성 읽기 수정 삭제, 댓글 작성 읽기 수정 삭제 기능은 완성을 했다고 생각한다. 
#### 처음 베이스는 리액트로 잡고 백엔드는 친숙한 것으로 하려고 하다보니 왜 리액트와 스프링부트를 같이 쓰는것이 비선호 하는지 알게 되었다. -서버사이드렌더링을 어떻게 구현해야 할지 감도 안온다- 이 포트폴리오를 완성하면 노드로 백엔드를 구성해서 새로운 프로젝트를 해봐야겠다.
#### 모든 작업들이 처음부터 공부를 하면서 작성했기 때문에 코드가 많이 지저분해 보인다. 이것들을 리팩토링할때 깔끔하게 바꾸고싶다.
#### 깃으로 작업을 해보는 것이 처음이었는데 브랜치도 관리전략이 있다는 것을 깨달았다..젠장...생각도 못했던 부분이다....
#### 회원기능 추가 부분 부터는 브랜치 관리에도 신경을 쓰면서 해야겠다.  현재 생각중인 방법은 git flow다.
<br/>

#### *20210211 // 처음에는 JPA를 공부하면서 했기 때문에 실제 블로그 서비스에서 사용하는 부분들을 상당 수 포기해야만 했다.-카테고리라던지 태그라던지...- 그래서 이번에 회원기능을 추가하고 이것 저것 필요한 테이블을 추가로 구성해서 작성해볼 생각이다. *
#### *회원기능은 비밀번호 암호화라던지 이런 쪽으로 생각하기보다는 OAUTH2.0과 Spring Security를 사용해서 구글이나 깃허브 로그인쪽으로 가닥을 잡았다.*
