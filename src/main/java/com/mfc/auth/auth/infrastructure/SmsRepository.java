package com.mfc.auth.auth.infrastructure;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SmsRepository {
	private final StringRedisTemplate redisTemplate;

	private final String PREFIX = "sms:";
	private final int LIMIT_TIME = 5 * 60;

	public void createSmsCode(String phone, String code) {
		redisTemplate.opsForValue()
				.set(PREFIX + phone, code, Duration.ofSeconds(LIMIT_TIME));
	}

	public String getSmsCode(String phone) {
		return redisTemplate.opsForValue().get(PREFIX + phone);
	}

	public void removeSmsCode(String phone) {
		redisTemplate.delete(PREFIX + phone);
	}

	public boolean hasKey(String phone) {
		return redisTemplate.hasKey(PREFIX + phone);
	}
}
