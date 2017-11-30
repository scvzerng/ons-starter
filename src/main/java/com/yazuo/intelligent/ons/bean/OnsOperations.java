package com.yazuo.intelligent.ons.bean;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;


public interface OnsOperations {
    SendResult syncSend(Message message);
}
