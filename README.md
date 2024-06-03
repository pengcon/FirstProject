# **모두를 위한 최고의 관광 앱, 퍼스트를 소개합니다.**

## **🌱 프로젝트 개요**

- 서비스명 : **퍼스트**
- 주제 :  소상공인 및 지역 산업 홍보를 위한 관광 스마트폰 애플리케이션
 

## 🌱 팀원

| 이름   | 담당 영역                       | Github                       |
| ------ | ------------------------------- | ---------------------------- |
| 이훈 | 안드로이드, 기획, 디자인, 백엔드(Django)       | https://github.com/pengcon |
| 이채진 | 백엔드(Django), 데이터 분석, 추천 알고리즘 구성 | |



## 🌱 서비스 화면 및 기능 설명



### 로그인 / 회원가입
로그인 화면 |회원가입 화면|
|------|---|
<img src="https://github.com/pengcon/FirstProject/assets/88936374/b47910b2-708b-40b7-b5ec-95a4a870fd31a" width="195" heigth="1200">|<img src="https://github.com/pengcon/FirstProject/assets/88936374/27215bde-4745-4c7d-9e95-5269a8299007" width="200" heigth="1200">

- 회원가입 양식을 제출하는 **회원가입 레이아웃**을 구현함.
- **Firebase**를 활용하여 , DB에 정보 저장 후 로그인할 수 있도록 구성함.
- **카카오 SDK**를 활용하여 카카오 ID로 간편 로그인 기능을 구현함.


### 관광데이터 API 기반 관광 정보 제공
내 위치 주변 관광정보|검색 위치 주변 관광정보|홈 화면 관광정보|
|------|---|---|
|<img src="https://github.com/pengcon/FirstProject/assets/88936374/2e9197cc-7dd5-4f10-b702-1b7b5e95d581" width="195" heigth="1200">|<img src="https://github.com/pengcon/FirstProject/assets/88936374/401d1235-f651-4984-99b6-3863b0df7f75" width="195" heigth="1200">|<img src="https://github.com/pengcon/FirstProject/assets/88936374/ea26e54e-d990-436f-baad-d4b581b5c82e" width="195" heigth="1200">|

- **Google Maps Platform**의 지도 SDK를 사용해 프로젝트에 구글 지도 구현.
- **국문 관광 정보 API**를 통해 위치기반 관광 정보 요청 URL 구성.
- XML 응답 메시지를 **XmlPullParser**로 안드로이드 환경으로 불러오기.
- **RecyclerView**로 관광 정보를 지도 하단 목록으로 제공.
- 지도에 불러온 관광 정보의 **좌표를 기반**으로 마커 표시.
- 필터 버튼으로 API 요청 메시지를 수정해 **음식점, 카페 등의 정보**만 제공.
- **소상공인 상권정보 API를** 추가로 사용해 소상공인 사업체 정보 제공 필터 버튼 추가.


### 사용자 추천 기반 여행지 추천 
사용자 정보 입력|사용자 취향 입력|맞춤형 여행지 추천|
|------|---|---|
|<img src="https://github.com/pengcon/FirstProject/assets/88936374/e03735fa-853d-4384-8149-a308090aff53" width="195" heigth="1200">|<img src="https://github.com/pengcon/FirstProject/assets/88936374/e3cd5061-cc6e-41c4-8647-6ddc84fbd906" width="195" heigth="1200">|<img src="https://github.com/pengcon/FirstProject/assets/88936374/f5acc234-a695-4600-af0f-04bd57a8bf33" width="195" heigth="1200">|

- **AI 모델을 활용한 여행지 추천 기능 구현**을 위해 백엔드 서버를 구축한 후 API 통신을 활용하기로 함.
- **AI 허브**의 **국내 여행로그 데이터** 를 활용하여 AI 여행 추천 모델을 구현함.
- **Django**로 AI 여행 추천 모델을 내장한 백엔드 서버를 구축함.
- **Retrofit**을 사용하여 안드로이드와 서버 간의 HTTP 통신을 테스트하고 정상적으로 통신하는 것을 확인함.
- 무중단 배포를 위해 **Google Cloud Platform**을 활용하여 서버를 배포함.
- **NGINX**와 **GUNICORN**을 사용하여 서버의 안정성을 확보함.


