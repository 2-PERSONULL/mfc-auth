package com.mfc.auth.auth.application;

import static com.mfc.auth.common.response.BaseResponseStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.auth.auth.domain.Member;
import com.mfc.auth.auth.dto.kafka.DeleteProfileDto;
import com.mfc.auth.auth.infrastructure.MemberRepository;
import com.mfc.auth.common.exception.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final KafkaProducer producer;

	@Override
	public void resign(String uuid) {
		Member member = memberRepository.findByUuid(uuid)
				.orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

		memberRepository.save(Member.builder()
				.id(member.getId())
				.email(member.getEmail())
				.name(member.getName())
				.phone(member.getPhone())
				.password(member.getPassword())
				.status((short)0)
				.build());

		producer.deleteProfile(DeleteProfileDto.builder()
				.uuid(uuid)
				.build());
	}
}
