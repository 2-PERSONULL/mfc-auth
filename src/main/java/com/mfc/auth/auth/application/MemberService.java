package com.mfc.auth.auth.application;

import com.mfc.auth.auth.dto.req.ModifyPasswordReqDto;

public interface MemberService {
	void resign(String uuid);
	void modifyPassword(String uuid, ModifyPasswordReqDto dto);
}
