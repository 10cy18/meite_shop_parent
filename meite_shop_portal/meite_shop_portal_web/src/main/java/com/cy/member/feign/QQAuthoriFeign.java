package com.cy.member.feign;

import com.cy.member.service.QQAuthoriService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-cy-member")
public interface QQAuthoriFeign extends QQAuthoriService {

}
