package com.example;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParsedQuiz {
	// String subject;
	String question; //문제
	String choices; //객관식 보기
	String answer; //딥뱐
	String commentary; //해설

	public static ParsedQuiz parseQuiz(Element box) {
		ParsedQuiz pq = new ParsedQuiz();

		String rawTitle = box.selectFirst("p.exam-title").text();
		pq.question = rawTitle.replaceFirst("^\\d+\\.", "").trim();

		Elements liItems = box.select("ol.circlednumbers > li");
		for (int i = 0; i < liItems.size(); i++) {
			String choiceText = liItems.get(i).text();
			pq.choices += choiceText;
			if (liItems.get(i).hasClass("correct")) {
				pq.answer = (i + 1) + "." + choiceText;
			}
		}

		Elements replies = box.select("li.reply-item");
		for (Element reply : replies) {
			if (reply.attr("data-info").contains("depth:0")) {
				Element comment = reply.selectFirst("div.reply-comment");
				if (comment != null) {
					pq.commentary = comment.text();
					break;
				}
			}
		}

		return pq;
	}

	// public void printQuestion(){
	// 	System.out.println("문제 : " + this.question);
	// 	System.out.println("보기 : " + this.choices);
	// 	System.out.println("정답 : " + this.answer);
	// 	System.out.println("해설 : " + this.commentary);
	// 	System.out.println("=================================");
	// }

}
