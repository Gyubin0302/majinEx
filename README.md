# majinEx
수정중입니다 밑에는 참고용 By.휘

한규빈, 김학영, 임대혁, 안휘
'한국마사회’을 벤치마킹한 경마 예측사이트
(일반회원, 관리자 중점으로 기능 분담)
- 스프링 시큐리티/Oauth2를 활용한 소셜 로그인
- 딥러닝을 이용한 검색어 추천(DL4J, Word2Vec)
- 딥러닝을 이용한 경마 순위 예측 (Keras Sequential)
- 커뮤니티 : 게시판(네이버에디터, 작성,수정,삭제)
- 일반회원 : 비밀번호 변경, 회원탈퇴, 예측지시청
- 관리자 : 회원추방, 게시글 작성 및 수정,삭제, 경기별 데이터 관리 

사용기술
- Front_End : HTML5,CSS3, JavaScript
JQuery, AJAX, Bootstrap, Thymleaf

- Back_End : Java, Python, Spring-Security,
MySQL, JPA(Java Persistence API), Maven ,
Hikari, Apache Tomcat

- Open Api : Naver Smart Editor , 
Naver ID Login, Kakao Login, Google Login,
DL4J(딥러닝), Tensor Flow, Keras 
 
- Tool : Spring Boot 4.7.2, Visual Studio, GitHub, Anaconda, MySQL Workbench, Google Drive, Notion, StarUML

주의사항 -추천트레이닝파일에 없는 상품 클릭후 메인페이지로 나올시 500에러 ex)신규상품, 상품모델명 수정, 상품삭제시 -->오류발생시 clickproduct테이블에서 해당 상품 삭제하면 해결됨 -->트레이닝을 원할경우 상품 등록후 clickproduct 테이블에 비슷한 상품이랑 함께 넣어 데이터 만든후 관리자-회원관리-추천트레이닝 접속후 트레이닝하기 클릭하면 해결됨.
