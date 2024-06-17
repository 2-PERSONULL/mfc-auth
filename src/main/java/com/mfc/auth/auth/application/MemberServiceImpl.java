package com.mfc.auth.auth.application;

import static com.mfc.auth.common.response.BaseResponseStatus.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.auth.auth.domain.Member;
import com.mfc.auth.auth.dto.resp.AuthInfoResponse;
import com.mfc.auth.auth.dto.kafka.DeleteProfileDto;
import com.mfc.auth.auth.dto.req.ModifyPasswordReqDto;
import com.mfc.auth.auth.dto.resp.MemberNameRespDto;
import com.mfc.auth.auth.infrastructure.MemberRepository;
import com.mfc.auth.common.exception.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final KafkaProducer producer;
	private final PasswordEncoder encoder;

	@Override
	public void resign(String uuid) {
		Member member = isExist(uuid);

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

	@Override
	public void modifyPassword(String uuid, ModifyPasswordReqDto dto) {
		Member member = isExist(uuid);

		memberRepository.save(Member.builder()
				.id(member.getId())
				.email(member.getEmail())
				.name(member.getName())
				.phone(member.getPhone())
				.password(encoder.encode(dto.getPassword()))
				.status(member.getStatus())
				.build()
		);
	}

	@Override
	public MemberNameRespDto getName(String uuid) {
		Member member = isExist(uuid);

		return MemberNameRespDto.builder()
				.name(member.getName())
				.build();
	}

	private Member isExist(String uuid) {
		return memberRepository.findByUuid(uuid)
				.orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));
	}

	@Override
	public AuthInfoResponse getBirthGender(String uuid) {
		Member member = memberRepository.findByUuid(uuid)
				.orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

		return AuthInfoResponse.builder()
				.userBirth(member.getBirth())
				.userGender(member.getGender())
				.build();
	}
}
