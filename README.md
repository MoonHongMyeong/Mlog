# 포트폴리오용 블로그 토이 프로젝트

프론트 작업은 React, 백엔드 작업은 springboot를 이용해서 블로그 모양의 게시판을 만들어야겠다. <br/>
~~http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com/ <=확인가능<br/>~~
현재 비용 때문에 정지


참고 사이트 [blex.me](https://blex.me). [velog.io](https://velog.io)
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
추가사항 wiki
---
실제 사용 예
 - 비회원 포스트 목록 조회<br>
 ![view_postlist](https://user-images.githubusercontent.com/69945734/116980805-a6cda680-ad01-11eb-8803-998f5ab273db.gif)

- 회원 로그인<br>
 ![login](https://user-images.githubusercontent.com/69945734/116980769-9a494e00-ad01-11eb-9789-14979cd37b7d.gif)

- 회원 포스트 등록 삭제<br>
 ![post_add_delete](https://user-images.githubusercontent.com/69945734/116980941-d8df0880-ad01-11eb-9c54-855e29178aa6.gif)

 - 회원 포스트 수정<br>
 ![post_update](https://user-images.githubusercontent.com/69945734/116980964-e0061680-ad01-11eb-8a22-3d829b4beb8e.gif)

 - 회원 카테고리 생성 삭제<br>
 ![category_delete_add](https://user-images.githubusercontent.com/69945734/116980967-e1cfda00-ad01-11eb-89d2-65043b105e43.gif)

 - 포스트 목록<br>
 ![postlist_kind](https://user-images.githubusercontent.com/69945734/116980977-e4323400-ad01-11eb-9d67-50b2a6360e7f.gif)

 - 포스트 검색<br>
 ![search_post](https://user-images.githubusercontent.com/69945734/116980982-e6948e00-ad01-11eb-93f0-9eb01883c9a8.gif)

 - 회원 로그아웃<br>
 ![logout](https://user-images.githubusercontent.com/69945734/116980992-e98f7e80-ad01-11eb-9a73-f536e42ec9c0.gif)
