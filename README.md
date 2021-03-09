# 포트폴리오용 블로그 토이 프로젝트

프론트 작업은 React, 백엔드 작업은 springboot를 이용해서 블로그 모양의 게시판을 만들어야겠다. <br/>
http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com/ <=확인가능<br/>
의식의 흐름대로 쓰는 포트폴리오 개발일지<br/>
https://www.notion.so/ded2d634878840fb9228282e22b17ad8?v=2499368d40e845da8dfb5b2cd23283aa

참고 사이트 blex.me  
---
<br/>
<br/>

## 사용 기술 및 환경
|section|description|
|:--:|:--:|
|BACKEND|springboot 2.4.3(SNAPSHOT)<br/>java 11.0.9<br/>gradle 6.7.1<br/>spring data jpa<br/>Querydsl 4.4.0<br/>Lombok<br/>OAuth2.0<br/>spring-session-jdbc<br/>spring-cloud-starter-aws 2.2.6|
|Testing<br/>tools|Junit5<br/>Google Truth<br/>Mockito
|DATABASE| H2database(dev only)<br/>MySQL 5.6|
|FRONTEND| React 17.0.1<br/>axios 0.21.1<br>react-router-dom 5.2.0<br/>styled-components 5.2.1|
|DEPLOY|Nginx<br/>EC2<br/>RDS<br/>S3|
---
<br/><br/>

## 아키텍쳐 의사결정 기록
### =프로그래밍 언어 : React, java, springboot
#### 국비지원 6개월 과정을 수료하고 나에게 가장 부족한 부분이 javascript를 다루는 것이라고 생각했고-jquery는 생각도 안했다.- 공부를 시작했다. 함수형 프로그래밍인 javascript는 java를 배울 때와는 다른 재미가 있었고 그러다 보니 리액트도 공부를 조금 했다. 처음에는 리액트만 활용한 프로젝트로 만들기 위해 시작했는데 그 동안 학원에서 배운 것도 써보고 싶었다. 학원에서는 java와 spring framework, mybatis를 배웠는데 더 공부를 하고 싶어서 springboot와 jpa를 선택했다.

### =데이터베이스 선정 : H2, MySQL
#### jpa를 공부하면서 H2를 알게 되었고 프로토 타입 제작용으로는 가벼워서 딱이라고 생각했다. H2는 실제 배포용으로는 잘 사용하지 않는다고 해서 Oracle과 MySQL 중에 고민을 했다. 오라클은 학원 수료과정 중 프로젝트 때 사용해 봤고 RDS에서 MariaDB나 MySQL을 더 사용한다고 했기 때문에 MySQL을 선택했다.

### =배포 : AWS, RDS, S3
#### 처음 배포하는 방법을 공부할 때 AWS EC2 Linux AMI와 RDS를 활용한 방법이었고, 그대로 적용을 했다. 


### =목표
#### 처음에는 리액트를 잘 다루고 싶어서 시작한 프로젝트였는데 JPA와 TDD에 흥미가 많이 생겨서 JPA를 사용하는 것과 junit테스트로 모든기능 100% 커버를 목표로 삼고있다.
#### 기능적인 목표는 markdown언어가 문서작업에 최적화 되어있는데 블로그에서는 글이 중심이니 깃허브의 markdown api를 가져오거나-이것은 횟수제한이 있다고 들었다- 직접 구현을 해보고싶다.
---

