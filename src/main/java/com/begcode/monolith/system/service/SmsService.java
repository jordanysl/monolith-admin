package com.begcode.monolith.system.service;

import com.begcode.monolith.config.Constants;
import com.begcode.monolith.domain.enumeration.SmsProvider;
import java.util.LinkedHashMap;
import java.util.List;
import javax.cache.CacheManager;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.callback.CallBack;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.comm.enumerate.SupplierType;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final CacheManager cacheManager;

    public SmsService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * 发送固定消息模板短信
     *
     * @param phone
     * @param message
     * @return
     */
    public Boolean sendMessage(String phone, String message, SmsProvider smsProvider) {
        SmsResponse response = getSmsBlend(smsProvider).sendMessage(phone, message);
        return StringUtils.isBlank(response.getErrorCode());
    }

    /**
     * 使用自定义模板发送短信
     *
     * @param phone
     * @param templateId
     * @param messages
     * @return
     */
    public Boolean sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages, SmsProvider smsProvider) {
        SmsResponse response = getSmsBlend(smsProvider).sendMessage(phone, templateId, messages);
        return StringUtils.isBlank(response.getErrorCode());
    }

    /**
     * 群发短信，固定模板短信
     *
     * @param phones
     * @param message
     * @return
     */
    public Boolean massTexting(List<String> phones, String message, SmsProvider smsProvider) {
        SmsResponse response = getSmsBlend(smsProvider).massTexting(phones, message);
        return StringUtils.isBlank(response.getErrorCode());
    }

    /**
     * 使用自定义模板群发短信
     *
     * @param phones
     * @param templateId
     * @param messages
     * @return
     */
    public Boolean massTexting(List<String> phones, String templateId, LinkedHashMap<String, String> messages, SmsProvider smsProvider) {
        SmsResponse response = getSmsBlend(smsProvider).massTexting(phones, templateId, messages);
        return StringUtils.isBlank(response.getErrorCode());
    }

    /**
     * 异步发送固定模板短信
     *
     * @param phone
     * @param message
     * @param callBack
     */
    public void sendMessageAsync(String phone, String message, CallBack callBack, SmsProvider smsProvider) {
        getSmsBlend(smsProvider).sendMessageAsync(phone, message, callBack);
    }

    public void sendMessageAsync(
        String phone,
        String templateId,
        LinkedHashMap<String, String> messages,
        CallBack callBack,
        SmsProvider smsProvider
    ) {
        getSmsBlend(smsProvider).sendMessageAsync(phone, templateId, messages, callBack);
    }

    /**
     * 使用固定模板发送延时短信
     *
     * @param phone
     * @param message
     * @param delayedTime
     */
    public void delayedMessage(String phone, String message, Long delayedTime, SmsProvider smsProvider) {
        getSmsBlend(smsProvider).delayedMessage(phone, message, delayedTime);
    }

    /**
     * 群发固定模板延迟短信
     *
     * @param phones
     * @param message
     * @param delayedTime
     */
    public void delayMassTexting(List<String> phones, String message, Long delayedTime, SmsProvider smsProvider) {
        getSmsBlend(smsProvider).delayMassTexting(phones, message, delayedTime);
    }

    /**
     * 群发自定义模板延迟短信
     *
     * @param phones
     * @param templateId
     * @param messages
     * @param delayedTime
     */
    public void delayMassTexting(
        List<String> phones,
        String templateId,
        LinkedHashMap<String, String> messages,
        Long delayedTime,
        SmsProvider smsProvider
    ) {
        getSmsBlend(smsProvider).delayMassTexting(phones, templateId, messages, delayedTime);
    }

    public Boolean sendValidate(String phone) {
        String code = String.valueOf(RandomUtils.nextInt(100000, 999999));
        if (sendMessage(phone, code, SmsProvider.ALIBABA)) {
            cacheManager.getCache(Constants.CAPTCHA_KEY).put(Constants.CAPTCHA_KEY + phone, code);
            return true;
        }
        return false;
    }

    public boolean validateMessage(String mobile, String code) {
        String cache = (String) cacheManager.getCache(Constants.CAPTCHA_KEY).get(Constants.CAPTCHA_KEY + mobile);
        if (StringUtils.isNotBlank(code) && StringUtils.equalsIgnoreCase(cache, code)) {
            cacheManager.getCache(Constants.CAPTCHA_KEY).remove(Constants.CAPTCHA_KEY + mobile);
            return true;
        }
        return false;
    }

    private SmsBlend getSmsBlend(SmsProvider smsProvider) {
        switch (smsProvider) {
            case ALIBABA:
                return SmsFactory.createSmsBlend(SupplierType.ALIBABA);
            case TENCENT:
                break;
            case HUAWEI:
                break;
        }
        throw new RuntimeException("不支持的短信供应商");
    }
}
