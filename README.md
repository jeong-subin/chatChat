# chatChat(회원채팅홈페이지)
#### 개인프로젝트
  
  
  
  
<img src="https://user-images.githubusercontent.com/36976081/109260669-e8455f80-7841-11eb-90d8-cbe5f8ef703d.png" width="500">



---
### 설명

 jsp, ajax를 이용하여 로그인한 회원끼리 채팅을 할 수 있는 홈페이지입니다. 
 XMLHttpRequest 객체를 이용해서 화면의 전체 갱신없이 내부에서 서버와 통신을 하기 위해 ajax를 사용하였습니다.
 서버에서 받은 데이터 값을 json타입으로 받아 javascript에서 ui조작을 하여 전체갱신없이 ui의 일부분만 동적인 변경이 가능하게 하였습니다.
 예를들어 친구 찾기 기능에서  ajax로 findID 값을 가져와 UserFindServlet으로 post타입으로 통신하여 servlet에서 db처리, 유요한 id인지, null값인지 등등 검사후
 success 시에 검색결과를 json으로 parse 해서 html태그에 결과값을 동적으로 변경하여  하였습니다.
 
 
 
 

### 프로젝트 기간  
  2021.01.20 ~ 2021.02.22
  
  
  
  
  

### 주요기능
1. 로그인  
2. 회원가입, 수정  
3. 프로필 수정, 프로필 사진 업로드
4. 회원 검색 , 메시지 보내기
5. 메시지함 ,안읽은 메시지 출력, 메시지 전송  
6. 자유게시판 CRUD, 파일업로드/다운로드, 댓글/대댓글, 조회수
7. 유효성검사  


### 사용스킬
- Java
- JavaScript
- Ajax
- Jsp
- Html
- css

### 시연 동영상 링크

https://youtu.be/OlnqI5vF9tE



