package com.tumo.util;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
	
	/*
	 * Mailjet을 이용한 일 200회 무료 메일 전송 기능 신용 카드 정보가 입력되지 않았기 때문에 일 200회 초과시 추가 결제 없이 메일
	 * 전송 기능 정지
	 */

	@Value("${mail.useremail}")
	private String from;
	@Value("${mail.mailjet.api-key}")
	private String apiKey;
	@Value("${mail.mailjet.secret-key}")
	private String secretKey;
		
	public String getTempPassword() {
		StringBuilder password = new StringBuilder();
		
		Random random = new Random();
		
		// 50% 확률로 영어 또는 숫자로 구성된 임시 비밀번호 8자리 생성
		for (int i=0; i<8; i++) {
			if (random.nextBoolean()) {
				char c = (char)((int)(random.nextInt(26))+65); // A-Z 대문자 알파벳
				password.append(c);
			} else {
				int num = random.nextInt(10); // 0-9 정수
				password.append(num);
			}
		}
		
		return password.toString();
	}

	public String findPassword(String email, String nickname, String password)
			throws MailjetException, MailjetSocketTimeoutException {
		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		
		client = new MailjetClient(apiKey, secretKey, new ClientOptions("v3.1"));
		request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, new JSONArray().put(new JSONObject()
				.put(Emailv31.Message.FROM, new JSONObject().put("Email", from).put("Name", "tumo-noReply"))
				.put(Emailv31.Message.TO,
						new JSONArray().put(new JSONObject().put("Email", email).put("Name", nickname)))
				.put(Emailv31.Message.SUBJECT, "tumo " + nickname + " 회원님의 비밀번호 찾기 내역입니다.")
				.put(Emailv31.Message.TEXTPART, "텍스트파트")
				.put(Emailv31.Message.HTMLPART,
						"<a href='https://www.ssafy.com/'><img src=\"https://edu.ssafy.com/asset/images/logo.png\"></a></br><h3><a href='https://www.ssafy.com/'>tumo 투자를 모으다</a>!</h3><br />"
								+ nickname + "님의 임시비밀번호는 " + password + "입니다.<br />" + "본 메일은 발신전용으로 답장을 받지않습니다.<br />")
				.put(Emailv31.Message.CUSTOMID, "커스텀 아이디")));
		response = client.post(request);

		// 메일 보내는데 성공하면 200 반환
		// 하루 발신 가능한 개수가 200개이므로 초과될 경우 메일 전송 실패
		return Integer.toString(response.getStatus());
	}
}
