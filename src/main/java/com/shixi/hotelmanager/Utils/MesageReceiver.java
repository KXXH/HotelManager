package com.shixi.hotelmanager.Utils;

import com.rabbitmq.client.Channel;
import com.shixi.hotelmanager.exception.RefundFailException;
import com.shixi.hotelmanager.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class MesageReceiver {

    @Autowired
    private OrderService orderService;

    /**
     * 监听转发队列  走逻辑判断，尚未支付且超过过期时间的订单号设置为失效订单
     * @param message 信息包装类
     * @param channel 通道
     */
    @RabbitListener(queues = {"ORDER_REDIRECT_QUEUE"})
    public void redirect(Message message, Channel channel) throws IOException {
        // 从队列中取出订单号
        byte[] body = message.getBody();
        String orderNo = new String(body,"UTF-8");
        System.out.println(new Date() +  "消费消息，订单号为" + orderNo);
        try {
            orderService.refundOrder(Long.valueOf(orderNo),"CANCEL");
        } catch (RefundFailException e) {
            e.printStackTrace();
        }
        // 确认消息有没有被收到,false表示手动确认  在处理完消息时，返回应答状态
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
