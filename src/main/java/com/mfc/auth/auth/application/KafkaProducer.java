package com.mfc.auth.auth.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.auth.auth.dto.kafka.ProfileDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendProfile(ProfileDto dto) {
		kafkaTemplate.send("profile", dto);
	}
}
