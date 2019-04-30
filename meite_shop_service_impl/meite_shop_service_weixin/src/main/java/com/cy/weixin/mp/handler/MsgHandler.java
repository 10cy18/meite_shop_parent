package com.cy.weixin.mp.handler;

import com.cy.core.utils.RedisUtil;
import com.cy.core.utils.RegexUtils;
import com.cy.weixin.mp.builder.TextBuilder;
import com.cy.constants.Constants;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    /**
     * 发送验证码消息
     */
    @Value("${cy.weixin.registration.code.message}")
    private String registrationCodeMessage;
    /**
     * 默认回复消息
     */
    @Value("${cy.weixin.default.registration.code.message}")
    private String defaultRegistrationCodeMessage;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //1、获取微信客户端发送的消息
        String fromContent = wxMessage.getContent();
        //2、使用正则表达式验证消息是不是手机号格式
        if(RegexUtils.checkMobile(fromContent)){
            //3、如果是手机号码格式，就随机产生4位数字注册码
            int registCode = registCode();
            String content = String.format(registrationCodeMessage, registCode);
            //将注册码存入在redis中,key为手机号码
            redisUtil.setString(Constants.WEIXINCODE_KEY +fromContent ,registCode+"",Constants.WEIXINCODE_TIMEOUT);
            return new TextBuilder().build(content,wxMessage,weixinService);
        }
        //返回默认消息
        return new TextBuilder().build(defaultRegistrationCodeMessage,wxMessage,weixinService);
        //TODO 组装回复消息
        //String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
        //String content = "哈哈哈哈哈哈啊哈哈哈";
        //return new TextBuilder().build(content, wxMessage, weixinService);

    }

    // 获取注册码
    private int registCode() {
        int registCode = (int) (Math.random() * 9000 + 1000);
        return registCode;
    }

}
