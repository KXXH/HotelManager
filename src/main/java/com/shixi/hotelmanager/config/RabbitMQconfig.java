package com.shixi.hotelmanager.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQconfig {
    /**
     * 订单死信队列交换机标识符  属性值不能改，写死
     */
    private static final String ORDER_DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 订单死信队列交换机绑定键 标识符  属性值不能改，写死
     */
    private static final String ORDER_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    //----------------------------订单死信定义------------------------------
    // 订单过期流程： 消息（创建的订单号）---》发送到订单死信队列，不消费（设置过期时间）---》（超过设定的过期时间）根据ORDER_DEAD_LETTER_QUEUE_KEY路由死信交换机 ---》重新消费，根据ORDER_DEAD_LETTER_ROUTING_KEY转发到转发队列（取出消息订单号查找订单，假如仍然未支付就取消订单）---》end

    /**
     * orderDeadLetterExchange（direct类型交换机）
     */
    @Bean("orderDeadLetterExchange")
    public Exchange orderDeadLetterExchange() {
        return ExchangeBuilder.directExchange("ORDER_DL_EXCHANGE").durable(true).build();
    }

    /**
     * 声明一个订单死信队列.
     * x-dead-letter-exchange   对应  死信交换机
     * x-dead-letter-routing-key  对应 死信队列
     */
    @Bean("orderDeadLetterQueue")
    public Queue orderDeadLetterQueue() {
        // 参数
        Map<String, Object> args = new HashMap<>(2);
        // 出现dead letter之后将dead letter重新发送到指定exchange
        args.put(ORDER_DEAD_LETTER_QUEUE_KEY, "ORDER_DL_EXCHANGE");
        // 出现dead letter之后将dead letter重新按照指定的routing-key发送
        args.put(ORDER_DEAD_LETTER_ROUTING_KEY, "RED_KEY");
        // name队列名字  durable是否持久化，true保证消息的不丢失, exclusive是否排他队列，如果一个队列被声明为排他队列，该队列仅对首次申明它的连接可见，并在连接断开时自动删除, autoDelete如果该队列没有任何订阅的消费者的话，该队列是否会被自动删除, arguments参数map
        return new Queue("ORDER_DL_QUEUE",true,false,false, args);
    }

    /**
     * 定义订单死信队列转发队列.
     */
    @Bean("orderRedirectQueue")
    public Queue orderRedirectQueue() {
        return new Queue("ORDER_REDIRECT_QUEUE",true,false,false);
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到订单死信队列上.
     */
    @Bean
    public Binding orderDeadLetterBinding() {
        return new Binding("ORDER_DL_QUEUE", Binding.DestinationType.QUEUE, "ORDER_DL_EXCHANGE", "DL_KEY", null);

    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到订单转发队列上.
     */
    @Bean
    public Binding orderRedirectBinding() {
        return new Binding("ORDER_REDIRECT_QUEUE", Binding.DestinationType.QUEUE, "ORDER_DL_EXCHANGE", "RED_KEY", null);
    }
}
