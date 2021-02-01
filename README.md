# 포트폴리오용 블로그 토이 프로젝트

프론트 작업은 react
백엔드 작업은 springboot


backend 
java 11.0.9
springboot 2.4.3(SNAPSHOT)

spring data jpa hibernate
Lombok
Junit5
Google truth
TestRestTemplete

H2database dev only
MySQL database 

남은부분 2020 01 22
검색 구현  queryDSL or JPQL 작성자+제목+내용

//0130
백엔드 api 구현 끝났고
프론트 작업중인데 포스트 리스트, 기본 ui, 뷰 포스트 까지는 구현 아 코맨트폼도
안되는 부분 포스트 수정화면 하고 댓글 리스트 안불러와짐 

//0201
백엔드 api 수정
파일이 들어가야 해서 테스트 다시작성함
포스트 작성은 작성 완료후에 다시 목록으로 가거나 해당 포스트로 이동시켜버리기 떄문에
백엔드에서 아무데이터나 넘겨도 됐지만
댓글리스트는 한 페이지내에서 작성이 완료되면 바로 랜더링 되야되기 때문에
ResponseEntity로 작성을 해야할 필요성을 느낌
ResponseEntity로 전달해 줘야 할 상황은 success와 input한 data
