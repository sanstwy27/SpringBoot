package com.atkjs927.amqp;

import com.atkjs927.amqp.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringBoot02AmqpApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void createExchange() {
		amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
		System.out.println("創建完成");

		amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));

        //創建綁定規則
		amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.haha",null));

        //amqpAdmin.de~
    }

    @Test
    void contextLoads() {
        //Message需要自己構造一個;定義消息體內容和消息頭
        //rabbitTemplate.send(exchage,routeKey,message);

        //object默認當成消息體，只需要傳入要發送的對象，自動序列化發送給rabbitmq；
        //rabbitTemplate.convertAndSend(exchage,routeKey,object);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","這是第一個消息");
        map.put("data", Arrays.asList("helloworld",123,true));
        //對像被默認序列化以後發送出去
        rabbitTemplate.convertAndSend("exchange.direct","atkjs927.news", new Book("西遊記","吳承恩"));
    }

    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("atkjs927.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    public void broadcast(){
        rabbitTemplate.convertAndSend("exchange.fanout","",new Book("紅樓夢","曹雪芹"));
    }
}
