package com.mfc.auth.auth.application;

import static com.mfc.auth.common.response.BaseResponseStatus.*;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.auth.auth.domain.Member;
import com.mfc.auth.auth.dto.kafka.ProfileDto;
import com.mfc.auth.auth.dto.req.SignUpReqDto;
import com.mfc.auth.auth.dto.req.SmsReqDto;
import com.mfc.auth.auth.infrastructure.MemberRepository;
import com.mfc.auth.auth.infrastructure.SmsRepository;
import com.mfc.auth.common.exception.BaseException;
import com.mfc.auth.common.jwt.JwtTokenProvider;
import com.mfc.auth.common.sms.SmsUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
	private final MemberRepository memberRepository;
	private final SmsRepository smsRepository;

	private final SmsUtil smsUtil;

	private final BCryptPasswordEncoder encoder;

	private final KafkaProducer producer;

	@Override
	public void sendSms(SmsReqDto dto) {
		String to = dto.getPhone();
		if(isDuplicate(to)) {
			throw new BaseException(DUPLICATED_MEMBERS);
		}

		int random = (int) (Math.random() * 900000) + 1000;
		String code = String.valueOf(random);

		smsUtil.sendSms(to, code); // 문자 전송
		smsRepository.createSmsCode(to, code); // 인증번호 redis에 저장
	}

	@Override
	public void verifyCode(SmsReqDto dto) {
		if(!isVerify(dto)) {
			throw new BaseException(MESSAGE_VALID_FAILED);
		}

		smsRepository.removeSmsCode(dto.getPhone());
	}

	@Override
	public String signUp(SignUpReqDto dto) {
		Member member = createMember(dto);
		producer.sendProfile(ProfileDto.toBuild(dto, member.getUuid()));
		return member.getUuid();
	}

	@Override
	public boolean verifyEmail(String email) {
		return memberRepository.findByEmail(email).isEmpty();
	}

	// 중복 회원 검증 : 탈퇴 회원 포함 x
	private boolean isDuplicate(String phone) {
		return memberRepository.findByActivePhone(phone).isPresent();
	}

	// 인증번호 검증
	private boolean isVerify(SmsReqDto dto) {
		return smsRepository.hasKey(dto.getPhone()) &&
				smsRepository.getSmsCode(dto.getPhone())
						.equals(dto.getCode());
	}

	// 회원 공통 정보 저장 (유저, 파트너)
	private Member createMember(SignUpReqDto dto) {

		Optional<Member> member = memberRepository.findByPhone(dto.getPhone());

		if(member.isPresent()) {
			return memberRepository.save(Member.builder()
					.id(member.get().getId())
					.uuid(member.get().getUuid())
					.email(dto.getEmail())
					.password(encoder.encode(dto.getPassword()))
					.name(dto.getName())
					.birth(dto.getBirth())
					.phone(dto.getPhone())
					.gender(dto.getGender())
					.status((short)1)
					.build());
		}

		return memberRepository.save(Member.builder()
				.email(dto.getEmail())
				.password(encoder.encode(dto.getPassword()))
				.name(dto.getName())
				.birth(dto.getBirth())
				.phone(dto.getPhone())
				.gender(dto.getGender())
				.uuid(UUID.randomUUID().toString())
				.status((short)1)
				.build());
	}
}
