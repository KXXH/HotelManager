package com.shixi.hotelmanager.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.DTO.DefaultReturnDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.*;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.exception.*;
import com.shixi.hotelmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    private static String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi4rMalBhqMu6aslBjznhU4D/TfEbyVVEUoPCFclJSC5Ndamb4LMEE88yCr7kjlbRGQhsBhgot8FxprlV6IilHNSQ+vvHQImWGT9L8TE8uHVQljT16y2L7kaPw5DDUoRwdV8Z2NUtz55NoJPYDMCBvVremuk26/pGeexpzKpLAAxubL2tinB/VzrDrawNQiIrJHnRghUsCT+NrlsAZWedcdLRO/PSqR0BGbcpc4sigfreW9w8dPAPqqjQN2Z3pZ/Ho9i7CLgss1W47xnE1kqVFEyL/fMNs4T73xFyeIpSYjLMkEqwnWH+1NIjzyVrI3yBFMW6xMJM/06PqoDWbay8VQIDAQAB";

    @Autowired
    private OrderService orderService;

    

    @RequestMapping("/createOrder")
    @ResponseBody
    DefaultReturnDTO createOrder(@Valid CreateOrderDTO dto, BindingResult result) throws javax.xml.bind.ValidationException, HotelRoomInsufficientException {
        if(result.hasErrors()) throw new ValidationException(result.getAllErrors().iterator().next().getDefaultMessage());
        try {
            Order order=orderService.createOrder(dto);
            QueryWrapper<Order> wrapper=new QueryWrapper<>();
            wrapper.eq("uuid",order.getUuid());
            order=order.selectOne(wrapper);
            return new CreateOrderSuccessDTO("success",order.getId());
        } catch (ParseException e) {
            throw new ValidationException("日期格式错误");
        }
    }

    @RequestMapping("/payOrder")
    @ResponseBody String payOrder(@Valid PayOrderDTO dto, BindingResult result) throws OrderNotFoundException, UserNotFoundException, OrderStatusException, AlipayApiException, OrderPaymentAlreadySuccessException {
        if(result.hasErrors()) throw new ValidationException(result.getAllErrors().iterator().next().toString());
        return orderService.payOrder(dto.getId());

    }


    @RequestMapping("/refund")
    public String refund(Order order,Model model) throws AlipayApiException, OutdatedOrdersException {
        try {
            if(orderService.makeFundOrder(order)){
                model.addAttribute("message","退款成功！");
                return "paymentComplete";
            }

            else{
                model.addAttribute("message","退款失败，请咨询客服！");
                return "paymentComplete";
            }
        } catch (OrderNotFoundException e) {
            model.addAttribute("message","订单未找到！");
            return "paymentComplete";
        } catch (RefundFailException e) {
            model.addAttribute("message","退款失败，请咨询客服！");
            return "paymentComplete";
        } catch (OutdatedOrdersException e) {
            model.addAttribute("message","退款失败，请咨询客服！");
            return "paymentComplete";
        }
    }


    @RequestMapping("/CallBack/return")
    //@ResponseBody
    public String returnPage(HttpServletRequest request, Model model) throws AlipayApiException, OrderNotFoundException {
        Map<String,String[]> map1 = request.getParameterMap();
        Enumeration<String> names=request.getParameterNames();
        Map<String,String> map=new HashMap<>();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            map.put(name, map1.get(name)[0]);
        }
        System.out.println("收到支付宝通知！！");
        boolean signVerified = AlipaySignature.rsaCheckV1(map, ALIPAY_PUBLIC_KEY, "UTF-8", "RSA2");
        if(signVerified){
            System.out.println("通知校验成功！！");
            System.out.println("map:"+map.toString());
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            //orderService.payOrderComplete(Long.parseLong(map.get("out_trade_no")),map.get("trade_no"));
            orderService.checkPaymentStatus(Long.parseLong(map.get("out_trade_no")));
            model.addAttribute("message","支付成功!");
            return "paymentComplete";
        }else{
            System.out.println("通知校验失败！！");
            model.addAttribute("message","支付失败，请重试!");
            return "paymentComplete";
            // TODO 验签失败则记录异常日志，并在response中返回failure.
        }
    }

    @RequestMapping("/evaluate")
    @ResponseBody
    public String makeEvaluate(OrderEvaluateDTO orderEvaluateDTO){
        System.out.println(orderEvaluateDTO.getOrderId()+","+orderEvaluateDTO.getEvaluate());
        try {
            if(orderService.makeEvaluate(orderEvaluateDTO.getEvaluate(), orderEvaluateDTO.getOrderId()))
                return "success";
            else
                return "fail";
        } catch (OrderNotFoundException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping("/checkStatus")
    @ResponseBody
    public OrderStatusDTO checkStatus(PayOrderDTO dto) throws OrderNotFoundException, AlipayApiException {
        String status=orderService.checkPaymentStatus(dto.getId());
        OrderStatusDTO returnDTO=new OrderStatusDTO();
        returnDTO.setStatus(status);
        return returnDTO;
    }
}
