package com.mfc.auth.auth.application;

import com.mfc.auth.auth.dto.req.ModifyPasswordReqDto;
import com.mfc.auth.auth.dto.resp.MemberNameRespDto;

public interface MemberService {
	void resign(String uuid);
	void modifyPassword(String uuid, ModifyPasswordReqDto dto);
	MemberNameRespDto getName(String uuid);
}
