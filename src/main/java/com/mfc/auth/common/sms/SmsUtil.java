package com.mfc.auth.common.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import jakarta.annotation.PostConstruct;

@Component
public class SmsUtil {
	@Value("${coolsms.api.key}")
	private String apiKey;

	@Value("${coolsms.api.secret}")
	private String secretKey;

	@Value("${coolsms.api.sender}")
	private String sender;

	DefaultMessageService messageService;

	@PostConstruct
	private void init() {
		this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, "https://api.coolsms.co.kr");
	}

	// 인증번호 전송 : 단일 메세지
	public SingleMessageSentResponse sendSms(String receiver, String code) {
		Message message = new Message();
		message.setFrom(sender); // 보내는 사람 : 관리자
		message.setTo(receiver); // 받는 사람 : 유저
		message.setText("[MFC] 본인 확인 인증번호 : [" + code + "]\n인증번호는 5분간 유효합니다."); // 전송 내용

		return messageService.sendOne(new SingleMessageSendingRequest(message));
	}
}

