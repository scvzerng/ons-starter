package com.yazuo.test;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.yazuo.intelligent.ons.OnsOperations;
import com.yazuo.intelligent.ons.annotation.MessageBody;
import com.yazuo.intelligent.ons.annotation.OnsProducer;
import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class TestComponent {

   @GetMapping("/{text}")
   @SendTo(producerId = "${provider.test}",topic = "crm_test")
    public Person sendMessage(@PathVariable String text){

       return JSON.parseObject(text,Person.class);
   }

   @OnsListener(topic = "${topic.test}",consumerId = "${consumer.test}")
    public void test(ConsumeContext context, @MessageBody Person person, Message message){
       System.out.println(JSON.toJSON(person));
   }
}
