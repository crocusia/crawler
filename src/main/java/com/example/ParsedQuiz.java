package com.example;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParsedQuiz {
	String subject;        // 예: "1과목: 소프트웨어 설계"
	String title;          // 문제 제목
	List<String> choices;  // 보기들
	int correctIndex;      // 정답 번호 (1~4)
	String correctAnswer;  // 정답 텍스트
	String explanation;    // 해설

	public static ParsedQuiz parseQuestion(Element box) {
		ParsedQuiz pq = new ParsedQuiz();

		// 제목
		String rawTitle = box.selectFirst("p.exam-title").text();
		pq.title = rawTitle.replaceFirst("^\\d+\\.", "").trim();

		// 보기 + 정답
		Elements liItems = box.select("ol.circlednumbers > li");
		pq.choices = new ArrayList<>();
		for (int i = 0; i < liItems.size(); i++) {
			String choiceText = liItems.get(i).text();
			pq.choices.add(choiceText);
			if (liItems.get(i).hasClass("correct")) {
				pq.correctIndex = i + 1;
				pq.correctAnswer = choiceText;
			}
		}

		// 해설
		Elements replies = box.select("li.reply-item");
		for (Element reply : replies) {
			if (reply.attr("data-info").contains("depth:0")) {
				Element comment = reply.selectFirst("div.reply-comment");
				if (comment != null) {
					pq.explanation = comment.text();
					break;
				}
			}
		}

		return pq;
	}

}
