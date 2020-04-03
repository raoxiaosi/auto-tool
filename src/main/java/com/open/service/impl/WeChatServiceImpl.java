package com.open.service.impl;

import com.open.constant.WeChatContant;
import com.open.pojo.bo.ArticleItem;
import com.open.service.WeChatService;
import com.open.util.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author raojing
 * @data 2020/4/3 14:20
 */
@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {

    @Override
    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = WeChatUtil.parseXml(request);
            // 消息类型
            String msgType = (String) requestMap.get(WeChatContant.MsgType);
            log.info("解析的消息类型为：{}", msgType);
            String mes = null;
            // 文本消息
            if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_TEXT)) {
                mes = requestMap.get(WeChatContant.Content).toString();
                log.info("解析的消息为 mes:{}", mes);
                if (mes != null && mes.length() < 2) {
                    List<ArticleItem> items = new ArrayList<>();

                    ArticleItem item = new ArticleItem();
                    item.setTitle("照片墙");
                    item.setDescription("阿狸照片墙");
                    item.setPicUrl("http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/photowall");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("哈哈");
                    item.setDescription("一张照片");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/me.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/index");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("小游戏2048");
                    item.setDescription("小游戏2048");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/2048.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/game2048");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("百度");
                    item.setDescription("百度一下");

                    item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                    item.setUrl("http://www.baidu.com");
                    items.add(item);

                    respXml = WeChatUtil.sendArticleMsg(requestMap, items);
                } else if ("我的信息".equals(mes)) {
//                    Map<String, String> userInfo = getUserInfo(requestMap.get(WeChatContant.FromUserName));
//                    System.out.println(userInfo.toString());
//                    String nickname = userInfo.get("nickname");
//                    String city = userInfo.get("city");
//                    String province = userInfo.get("province");
//                    String country = userInfo.get("country");
//                    String headimgurl = userInfo.get("headimgurl");
//                    List<ArticleItem> items = new ArrayList<>();
//                    ArticleItem item = new ArticleItem();
//                    item.setTitle("你的信息");
//                    item.setDescription("昵称:" + nickname + " 地址:" + country + " " + province + " " + city);
//                    item.setPicUrl(headimgurl);
//                    item.setUrl("http://www.baidu.com");
//                    items.add(item);
//
//                    respXml = WeChatUtil.sendArticleMsg(requestMap, items);
                }
            }else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_IMAGE)) {
                // 图片消息
                respContent = "您发送的是图片消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_VOICE)) {
                // 语音消息
                respContent = "您发送的是语音消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_VIDEO)) {
                // 视频消息
                respContent = "您发送的是视频消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_LOCATION)) {
                // 地理位置消息
                respContent = "您发送的是地理位置消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_LINK)) {
                // 链接消息
                respContent = "您发送的是链接消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }else if (msgType.equals(WeChatContant.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                // 事件推送
                String eventType = (String) requestMap.get(WeChatContant.Event);
                if (eventType.equals(WeChatContant.EVENT_TYPE_SUBSCRIBE)) {
                    // 关注
                    respContent = "谢谢您的关注！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                }else if (eventType.equals(WeChatContant.EVENT_TYPE_UNSUBSCRIBE)) {
                    // 取消关注
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }else if (eventType.equals(WeChatContant.EVENT_TYPE_SCAN)) {
                    // 扫描带参数二维码
                    // TODO 处理扫描带参数二维码事件
                }else if (eventType.equals(WeChatContant.EVENT_TYPE_LOCATION)) {
                    // 上报地理位置
                    // TODO 处理上报地理位置事件
                }if (eventType.equals(WeChatContant.EVENT_TYPE_CLICK)) {
                    // 自定义菜单

                }
            }
            mes = mes == null ? "不知道你在干嘛" : mes;
            if (respXml == null) {
                respXml = WeChatUtil.sendTextMsg(requestMap, mes);
            }
            System.out.println(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
