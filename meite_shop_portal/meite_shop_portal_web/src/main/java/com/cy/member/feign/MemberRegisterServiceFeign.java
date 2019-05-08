package com.cy.member.feign;

import com.cy.member.service.MemberRegisterService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-cy-member")
public interface MemberRegisterServiceFeign extends MemberRegisterService {

}
