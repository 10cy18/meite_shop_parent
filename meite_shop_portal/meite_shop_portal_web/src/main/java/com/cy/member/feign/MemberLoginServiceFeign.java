package com.cy.member.feign;

import com.cy.member.service.MemberLoginService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-cy-member")
public interface MemberLoginServiceFeign extends MemberLoginService {

}
