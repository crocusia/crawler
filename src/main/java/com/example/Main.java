package com.example;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) {

		//문제은행 도메인 주소
		final String domain = "https://cbtbank.kr";
		//문제은행에서 정보처리기사 문제 목록이 있는 페이지
		final String listCbtUrl = domain + "/category/정보처리기사";

		try {

			//시험 회차별 링크 수집
			Document listPage = Jsoup.connect(listCbtUrl).get();
			Elements links = listPage.select("a[href^=/exam/]");
			List<String> examUrls = new ArrayList<>();
			for (Element link : links) {
				String relativeUrl = link.attr("href");
				String fullUrl = domain + relativeUrl;
				examUrls.add(fullUrl);
			}

			List<ParsedQuiz> quizzes = new ArrayList<>();

			for (String examUrl : examUrls) {
				Document examPage = Jsoup.connect(examUrl).get();
				Elements elements = examPage.select(".exam-class-title, .exam-box");

				String currentSubject = "과목";
				for (Element el : elements) {
					if (el.hasClass("exam-class-title")) {
						currentSubject = el.text().trim();
					} else if (el.hasClass("exam-box")) {
						ParsedQuiz quiz = ParsedQuiz.parseQuestion(el);
						quiz.subject = currentSubject;
						quizzes.add(quiz);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}