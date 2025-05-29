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
			Document listPage = Jsoup.connect(listCbtUrl).get();
			Elements links = listPage.select("a[href^=/exam/]");
			List<String> examUrls = new ArrayList<>();
			for (Element link : links) {
				String relativeUrl = link.attr("href");
				String fullUrl = domain + relativeUrl;
				examUrls.add(fullUrl);
				System.out.println("찾은 시험 링크: " + fullUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}