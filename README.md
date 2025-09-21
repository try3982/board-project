# 📝 게시판 프로젝트

## 개요
Spring Boot + JPA 기반으로 구현한 **RESTful 게시판 서비스**입니다.  

- **Use** : Spring Boot, JPA, MySQL, Java  
- **Goal** : 회원가입/로그인 후 게시글 작성, 댓글 작성, 좋아요 등 게시판의 핵심 기능을 학습 및 구현  

---

## 요구사항
- 회원가입, 로그인, 로그아웃 기능 제공
- 게시글 CRUD 기능 제공
- 특정 작성자의 게시글 조회 가능(이메일로 조회)
- 댓글 CRUD 기능 제공
- 게시글 좋아요/좋아요 취소 기능 제공

---

## 시나리오
"사용자가 자유롭게 게시판에 글을 작성하고, 댓글과 좋아요로 상호작용할 수 있는 서비스 개발"  

- 사용자는 회원가입 후 로그인한다.  
- 사용자는 게시글을 작성, 수정, 삭제할 수 있다. (본인 소유 게시글만)  
- 다른 사용자의 게시글을 조회하고 댓글을 작성할 수 있다.  
- 댓글은 작성자만 수정할 수 있고, 삭제는 댓글 작성자 또는 게시글 작성자가 할 수 있다.  
- 사용자는 게시글에 좋아요/좋아요 취소를 할 수 있으며, 좋아요는 중복 불가하다.  

---

### 회원
- [x] 회원가입 (이메일 + 비밀번호, 비밀번호 해싱 저장)  
- [x] 로그인  
- [x] 로그아웃  

### 게시글
- [x] 게시글 작성  
- [x] 게시글 수정 (작성자만 가능)  
- [x] 게시글 삭제 (작성자만 가능)  
- [x] 게시글 조회 (전체 조회, 작성자 이메일로 조회)  

### 댓글
- [x] 댓글 작성  
- [x] 댓글 조회 (게시글별, 삭제된 댓글 제외)  
- [x] 댓글 수정 (작성자만 가능)  
- [x] 댓글 삭제 (댓글 작성자 + 게시글 작성자 가능)  

### 좋아요
- [x] 좋아요  
- [x] 좋아요 취소 (회원당 게시글별 중복 불가)  

---

## API 명세서

---

## 회원 API

1. 회원가입 (POST `/members`)
   - **파라미터**  
     - email (이메일)  
     - password (비밀번호)  
   - **정책**  
     - 이메일 중복 시 실패  
   - **성공 응답 정보**  
     - id, email, createdAt  

2. 로그인 (POST `/members/login`)
   - **파라미터**  
     - email, password  
   - **정책**  
     - 이메일이 존재하지 않거나 비밀번호 불일치 시 실패  
   - **성공 응답 정보**  
     - id, email, message  

3. 로그아웃 (DELETE `/members`)
   - **정책**  
     - 세션 무효화  
   - **성공 응답 정보**  
     - message  

---

## 게시글 API

1. 게시글 작성 (POST `/posts?email={email}`)
   - **파라미터**  
     - title (제목)  
     - content (내용)  
   - **정책**  
     - 로그인된 사용자만 작성 가능  
   - **성공 응답 정보**  
     - postId, title, content, likeCount, commentCount, viewCount  

2. 게시글 전체 조회 (GET `/posts`)
   - **파라미터**  
     - page, size, sort (정렬 옵션)  
   - **성공 응답 정보**  
     - postId, title, content, likeCount, commentCount, viewCount  

3. 작성자 이메일로 게시글 조회 (GET `/posts/by-author?email={email}`)
   - **성공 응답 정보**  
     - postId, title, content, likeCount, commentCount, viewCount  

4. 게시글 수정 (PUT `/posts/{postId}?email={email}`)
   - **파라미터**  
     - title, content  
   - **정책**  
     - 작성자 본인만 수정 가능  
   - **성공 응답 정보**  
     - postId, title, content, modifiedAt  

5. 게시글 삭제 (DELETE `/posts/{postId}?email={email}`)
   - **정책**  
     - 작성자 본인만 삭제 가능  
   - **성공 응답 정보**  
     - message  

---

## 댓글 API

1. 댓글 작성 (POST `/comments?postId={postId}&email={email}`)
   - **파라미터**  
     - commentContent (댓글 내용)  
   - **정책**  
     - 로그인된 사용자만 작성 가능  
   - **성공 응답 정보**  
     - commentId, content, authorEmail, createdAt  

2. 게시글별 댓글 조회 (GET `/comments?postId={postId}`)
   - **성공 응답 정보**  
     - commentId, content, authorEmail, createdAt  

3. 댓글 수정 (PUT `/comments/{commentId}?email={email}`)
   - **파라미터**  
     - commentContent  
   - **정책**  
     - 댓글 작성자만 수정 가능  
   - **성공 응답 정보**  
     - commentId, content, modifiedAt  

4. 댓글 삭제 (DELETE `/comments/{commentId}?email={email}`)
   - **정책**  
     - 댓글 작성자 또는 게시글 작성자 가능  
   - **성공 응답 정보**  
     - message  

---

## 좋아요 API

1. 좋아요 (POST `/posts/{postId}/likes?email={email}`)
   - **정책**  
     - 동일 회원이 같은 게시글에 중복 좋아요 불가  
   - **성공 응답 정보**  
     - message, likeCount  

2. 좋아요 취소 (DELETE `/posts/{postId}/likes?email={email}`)
   - **성공 응답 정보**  
     - message, likeCount  

---
