package com.mfc.auth.auth.application;

import static com.mfc.auth.common.response.BaseResponseStatus.*;

import org.springframework.stereotype.Service;

import com.mfc.auth.auth.dto.req.SmsReqDto;
import com.mfc.auth.auth.infrastructure.MemberRepository;
import com.mfc.auth.auth.infrastructure.SmsRepository;
import com.mfc.auth.common.exception.BaseException;
import com.mfc.auth.common.sms.SmsUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final MemberRepository memberRepository;
	private final SmsRepository smsRepository;

	private final SmsUtil smsUtil;

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
}
