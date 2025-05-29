package com.example;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
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

			for (String examUrl : examUrls) {
				Document examPage = Jsoup.connect(examUrl).get();

				Elements questions = examPage.select("div.exam-box");

				for (Element question : questions) {
					// 문제 제목
					String fullTitle = question.selectFirst("p.exam-title").text();
					String title = fullTitle.replaceFirst("^\\d+\\.", "").trim();

					// 보기들
					Elements choices = question.select("ol.circlednumbers > li");
					String[] options = new String[choices.size()];
					String answer = "";
					int answerIndex = -1;

					for (int i = 0; i < choices.size(); i++) {
						Element choice = choices.get(i);
						options[i] = choice.text();
						if (choice.hasClass("correct")) {
							answer = i + "번 " + choice.text();
						}
					}

					String explanation = "";
					Elements replies = question.select("li.reply-item");
					for (Element reply : replies) {
						String info = reply.attr("data-info");
						if (info.contains("depth:0")) {
							Element comment = reply.selectFirst("div.reply-comment");
							if (comment != null) {
								explanation = comment.text();
								break; // 첫 번째 해설만 사용
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}