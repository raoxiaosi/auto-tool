package com.open.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author raojing
 * @data 2020/4/3 14:20
 */
public interface WeChatService {

    String processRequest(HttpServletRequest request);

}
