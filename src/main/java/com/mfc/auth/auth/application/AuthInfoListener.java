package com.mfc.auth.auth.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfc.auth.auth.domain.Member;
import com.mfc.auth.auth.dto.kafka.AuthInfoResponse;
import com.mfc.auth.auth.dto.kafka.RequestAuthInfoDto;
import com.mfc.auth.auth.infrastructure.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInfoListener {

	private final ObjectMapper objectMapper;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final MemberRepository memberRepository;


	@KafkaListener(topics = "auth-info-request", groupId = "auth-info-group")
	public void handleAuthInfoRequest(String message) {
		try {
			RequestAuthInfoDto requestDto = objectMapper.readValue(message, RequestAuthInfoDto.class);
			String userId = requestDto.getUserId();

			Member member = memberRepository.findByUuid(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

			AuthInfoResponse response = AuthInfoResponse.builder()
				.userId(userId)
				.userGender(member.getGender())
				.userBirth(member.getBirth())
				.build();

			kafkaTemplate.send("auth-info-response", objectMapper.writeValueAsString(response));
		} catch (JsonProcessingException e) {
			log.error("Failed to parse JSON message: {}", message, e);
		}
	}
}
