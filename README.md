# 🎬 cinepointer

TMDb API를 활용한 영화 추천 및 리뷰 플랫폼 **cinepointer**에 오신 것을 환영합니다!  
이 프로젝트는 사용자 맞춤형 영화 추천과 커뮤니티 기능을 제공하는 **Spring Boot 기반 웹 애플리케이션**입니다.

---

## 📌 주요 기능

### 🏠 메인 페이지
- 인기 영화 리스트
- 최신 사용자 리뷰/댓글 조회

### 🎥 영화 상세 페이지
- 영화 포스터, 제목, 개봉일, 태그, 촬영지, 평점
- 출연진 및 제작진 정보
- 유사/추천 영화 표시

### 🙋‍♂️ 사용자 기능
- 회원가입 / 로그인 / 로그아웃
- 마이페이지
  - 프로필 수정
  - 관심 영화 보관
  - 좋아하는 태그 관리
  - 작성한 리뷰 목록 확인

### 📝 커뮤니티
- 게시판 목록 및 작성 기능
- 댓글 작성 및 조회

### ⭐ 리뷰 및 별점
- 영화 리뷰 작성 및 평점 등록
- 좋아요 기능 포함

### ⚙️ 관리자 페이지
- 영화 데이터 관리
- 사용자 활동 모니터링

---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| **Backend** | Spring Boot, MyBatis, Java, Oracle |
| **Frontend** | HTML, CSS, JavaScript (Vanilla) |
| **API** | TMDb API |
| **Build & Deploy** | WAR 파일 배포 |
| **Tool** | IntelliJ, Git, GitHub |

---

## 🗂 디렉토리 구조
cinepointer/
├── controller/
│ ├── userController.java
│ ├── movieController.java
│ └── boardController.java
├── dto/
│ ├── usersDto.java
│ ├── movieDto.java
│ ├── boardDto.java
│ ├── reviewDto.java
│ ├── genreDto.java
│ └── likesDto.java
├── dao/
│ └── cinepointerDao.java
├── service/
│ └── cinepointerService.java
├── resources/
│ └── mybatis/mapper/MyBatis.xml
├── static/
│ ├── css/style.css
│ ├── js/
│ │ ├── headerJs.js
│ │ └── footerJs.js
│ └── img/
├── templates/
│ ├── header.html
│ ├── footer.html
│ ├── mainpage.html
│ ├── moviepage.html
│ ├── mypage.html
│ ├── adminpage.html
│ ├── boardlistpage.html
│ └── boardpage.html

---

## 📈 향후 개선 사항 (To-do)
- ✅ OAuth 로그인 연동
- ⬜ 반응형 UI 적용
- ⬜ 영화 기반 추천 알고리즘 고도화
- ⬜ 사용자 통계 대시보드 추가

---

## 📩 문의
프로젝트에 대한 궁금한 점이나 제안이 있다면 언제든지 연락주세요!  
📧 **Email:** ibo12224@naver.com  
📍 **Location:** Busan, South Korea

---



