package com.cy.member.feign;

import com.cy.weixin.service.VerificaCodeService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "app-cy-weixin")
public interface VerificaCodeServiceFeign extends VerificaCodeService {

}
