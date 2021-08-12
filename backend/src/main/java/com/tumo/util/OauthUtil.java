package com.tumo.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OauthUtil {
	
	// 구글 로그인 id_token 유효성 검증
	public String tokenVerify(String idToken) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id_token", idToken);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo", map, String.class);
		
		JSONObject oauthInfo = new JSONObject(result.getBody());
		
		boolean emailVerified = "true".equals(oauthInfo.getString("email_verified"))? true:false;
		if (!emailVerified) {
			// 토큰 유효성 검증 실패
			// postman 또는 swagger를 이용해서 jwt 형태인 id_token을 임의로 변경했을 경우 발생할 수 있는 오류
			return null;			
		}
		
		String email = oauthInfo.getString("email");
		return email;
	}
	
}
