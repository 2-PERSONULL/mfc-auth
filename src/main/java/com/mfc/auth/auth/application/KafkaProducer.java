package com.mfc.auth.auth.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.auth.auth.dto.kafka.DeleteProfileDto;
import com.mfc.auth.auth.dto.kafka.InsertProfileDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendProfile(InsertProfileDto dto) {
		kafkaTemplate.send("profile-insert", dto);
	}

	public void deleteProfile(DeleteProfileDto dto) {
		kafkaTemplate.send("profile-delete", dto);
	}
}
