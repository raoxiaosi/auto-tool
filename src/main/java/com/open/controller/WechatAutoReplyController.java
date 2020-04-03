package com.open.controller;

import com.open.service.WeChatService;
import com.open.util.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 微信公众号自动回复-前端控制器
 *
 * @author raojing
 * @data 2020/4/3 14:06
 */
@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatAutoReplyController {

    @Resource
    private WeChatService weChatService;

    @GetMapping(value = "/auto_reply")
    public String validate(@RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) {
        log.info("微信平台校验服务器接口状态, 参数为u ---> signature:{}, timestamp:{}, nonce:{}, echostr:{}", signature, timestamp, nonce, echostr);
        String content = WeChatUtil.checkSignature(signature, timestamp, nonce) ? echostr : null;
        log.info("微信平台校验服务器接口状态, 结果为:{}", content);
        return content;
    }


    @PostMapping(value = "/auto_reply")
    public String processMsg(HttpServletRequest request) {
        // 调用核心服务类接收处理请求
        String content = weChatService.processRequest(request);
        log.info("自动回复结果为:{}", content);
        return content;
    }

}
