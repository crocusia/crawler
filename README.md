## 정보처리기사 CBT 문제 크롤러

- 출처: [CBT 문제은행 - 정보처리기사 기출문제](https://www.cbtbank.kr/category/%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC%EA%B8%B0%EC%82%AC)
- 사용 기술: Java, Jsoup, Jackson
- 주요 기능:
  - Jsoup을 통해 문제 및 선택지를 크롤링
  - 정제된 문제 데이터를 Jackson을 이용해 JSON 파일로 저장

- 크롤링 데이터:
  - quizzes.json : 8개의 회차에 대한 문제 (800개)
  - cleaned_quizees.json : ai가 중복(54개)을 제거한 문제(746개)
