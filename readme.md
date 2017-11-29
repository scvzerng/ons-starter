### ons starter

- @EnableOns 启用
- 在字段上标注@OnsProducer注入模板发送消息
- 在方法上标注@OnsListener监听指定topic
- 消息体多个参数的情况下在参数上标注@MessageBody会把消息转换成对应的对象
- 目前只支持String类型的或者JSON字符串格式的消息
- MessageBuilder链式构建消息
#### example

```java
@RestController
@RequestMapping("/message")
public class TestComponent {
    @OnsProducer(producerId = "${provider.test}")
    private ONSTemplate template;

   @GetMapping("/{text}")
    public void sendMessage(@PathVariable String text){
       template.send("crm_test",text);
   }

   @OnsListener(topic = "${topic.test}",consumerId = "${consumer.test}")
    public void test(ConsumeContext context, @MessageBody Person person, Message message){
       System.out.println(JSON.toJSON(person));
   }
}
```

#### 拓展 实现接口后直接注入

- 消息解码 `MessageDecode 默认FastJsonMessageDecode`
