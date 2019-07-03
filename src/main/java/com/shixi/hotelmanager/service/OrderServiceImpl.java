package com.shixi.hotelmanager.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchConditionType;
import com.shixi.hotelmanager.domain.*;
import com.shixi.hotelmanager.exception.HotelRoomInsufficientException;
import com.shixi.hotelmanager.exception.OrderNotFoundException;
import com.shixi.hotelmanager.exception.OrderStatusException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.mapper.HotelRoomMapper;
import com.shixi.hotelmanager.mapper.HotelStatusMapper;
import com.shixi.hotelmanager.mapper.OrderMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    private String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCl4aRDCJQkS9611Txk0DyNndjgyNOQhhW40je4UUp149WdPz4q1l8u45vzNCbHjLCUWD5QwqEQi5H56ChBGU3/+obt1aStF0WZF5kJAU4RyAX4S4oFQRCRZvg4Jy2ilib3FjrTF+0XX1OC02NafpIccv/WzoWV7zmCo4H3WdJ5cwbod6VukGR3x/shX9JyBaKoO/8NwRp3Yoo44yjNaPvxpimudcSlnAhMrq52ZmzwPM+r5ZSsGeJ+4LmUMUb2qqKdd9wgI/OhDXCKO+rzrdVx4BMva1Tbr4jadirsCQ5Q+meGmwxC4askzsEIfIjgDjhaghSSDQE38biWN7q1sq+tAgMBAAECggEBAIvdNzz2DMKV3hB+3M877PKTNvxBGHFxPPt69FRK5neEROazHl3MJrFIZIOpY1E5xOEvjktV76wdolWOc/J/vY6p0/7Q9mqjhqFQjk5TdVn0x2PVfWh0td2DbqMaFZZS+EO50JuQPu5ICAf06H6y3ctzA1hBBc2nyVvnNXwzlg2jmULnyZp2Doi3zmgjkqRu/u2qARfdJsgePSDUxq14CKxbLDg8Eju3oqfd5tvcRK2S6At2mAuHuNp4VXKCC//WiBToG++iSwCojaPl1XEqiE1WBhkJWiwiuV9FwE1aEgAX2LyEv1oxW5HZzaF0r/txb76f82TH/tSJ0603MHDIooECgYEA8Vgc0bdu8GzfWqMna2Pv8ENkgm3ez7TMx7BKG0X1YYOVU7VpKS1O9xm3N24dPGhFyj+DmbrfZXeEli2xEviJQfhWimzYzuho2vnAZFn3eOM4aAZ97hrR/wea7nIccql9e9uuvvGhXLSPSl+J3T5sg+67R2DhT37/3X/a++CEgHECgYEAr/RmcZm5IIJn6bN18PSPQ8vcp015bhDF3HtNWH8EPTAprv6/CuY6otkIKuN35SWOf1a525PcHb/aAMCXw2NYewbhsou/LIccZYw9xgAgHNQxJTOo1IQwncNnx3u9FA9wky3ixK6eHNo9hSeip7O/p7CTRBqOXZ2p6D+9Eo6bwP0CgYEArug0uqg99nBwzrc/ckzTL0UoKn6F4/IcFvxkOK/SzgEWz7vBot37RImWhs1+0rCfI5w0O8166YZcyJoEosMMdosL7PZFim5Uz54BGLk66JmD36AU0+MMHc/dMMHybAb5sjHbyvZDA3S4BCaJO5Zp/pOdlnVX1M0tkdF/Wtu0K4ECgYAWf63JwNpHKeWXoHboRJ09Egg47FMmm8ZxFuMg+bzVBh+OXMyY3C+LOy0sLsHZ7x91cOV7CkEPHMUHa5j8Ruu9b3fUmMHtM6mR4ojTlJiGlythkmV4Jx8ATUgr3cqjkgXXC/r/I0Tcc5uCNzs5LmbHTnDGOI8TsWFUbTID+XA5EQKBgCAukf992DiMLQbm5liGDQnbiN38TpTfyCHl14omHAE+uX6Vf7TW4YYfghznWu00moTRZ3GYywFP0+q7ss+v2fsVOz0lH/3wosJGxm5YHrRfH2CrHafwQnAKOnp4vgNIkPZ+2N8NCYTIbdoeqSNRpOLaLUectbRAxzc/9IReEkDn";
    private String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi4rMalBhqMu6aslBjznhU4D/TfEbyVVEUoPCFclJSC5Ndamb4LMEE88yCr7kjlbRGQhsBhgot8FxprlV6IilHNSQ+vvHQImWGT9L8TE8uHVQljT16y2L7kaPw5DDUoRwdV8Z2NUtz55NoJPYDMCBvVremuk26/pGeexpzKpLAAxubL2tinB/VzrDrawNQiIrJHnRghUsCT+NrlsAZWedcdLRO/PSqR0BGbcpc4sigfreW9w8dPAPqqjQN2Z3pZ/Ho9i7CLgss1W47xnE1kqVFEyL/fMNs4T73xFyeIpSYjLMkEqwnWH+1NIjzyVrI3yBFMW6xMJM/06PqoDWbay8VQIDAQAB";
    private AlipayClient alipayClient=new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2016101100658761", PRIVATE_KEY,"json","UTF-8", ALIPAY_PUBLIC_KEY,"RSA2");

    @Resource
    private HotelStatusMapper hotelStatusMapper;

    @Resource
    private HotelRoomMapper hotelRoomMapper;

    @Override
    @Transactional(rollbackFor = {HotelRoomInsufficientException.class})
    public boolean createOrder(CreateOrderDTO dto) throws HotelRoomInsufficientException, ParseException {
        Order order=new Order();
        //填写订单基本信息
        order.setOrderRoomId(dto.getOrderRoomId());
        order.setRoomCount(dto.getRoomCount());
        order.setDateStart(dto.getDateStart());
        order.setDateEnd(dto.getDateEnd());
        order.setTelephone(dto.getTelephone());
        order.setPersonName(dto.getPersonName());
        order.setPeopleCount(dto.getPeopleCount());
        order.setUuid(UUID.randomUUID().toString());
        //找到操作用户并将用户和订单关联
        User opUser= ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        order.setOrderUserId(opUser.getId());


        //找到对应酒店房间并关联
        HotelRoom room=new HotelRoom();
        room=room.selectById(order.getOrderRoomId());

        order.setBreakfast(room.getBreakfast());
        order.setWindows(room.getWindows());

        Hotel hotel=new Hotel();
        QueryWrapper<HotelStatus> wrapper=new QueryWrapper<>();
        wrapper.eq("hotel_id",room.getTheRoomHotelId());
        hotel=hotel.selectOne((Wrapper)wrapper);
        //hotel=hotel.selectById(room.getTheRoomHotelId());

        order.setHotelName(hotel.getHotelName());
        order.setHotelId(hotel.getHotelId());
        //设置订单当前状态为未支付
        order.setStatus("UNPAID");

        //生成订单其他信息
        order.setCreateTime(new Date());


        //TODO:为房间状态和酒店状态数据表加锁

        HotelStatus hotelStatus=new HotelStatus();
        RoomStatus roomStatus=new RoomStatus();

        int select_count=0;
        //写酒店状态
        QueryWrapper<HotelStatus> hotelStatusQueryWrapper=new QueryWrapper<>();
        hotelStatusQueryWrapper
                .eq("hotel_id",order.getHotelId())
                .le("record_for_date",order.getDateEnd())
                .ge("record_for_date",order.getDateStart());
        QueryWrapper<RoomStatus> roomStatusQueryWrapper=new QueryWrapper<>();
        roomStatusQueryWrapper
                .eq("room_id",order.getOrderRoomId())
                .le("record_for_date",order.getDateEnd())
                .ge("record_for_date",order.getDateStart());

        List<HotelStatus> hotelStatusList=hotelStatus.selectList(hotelStatusQueryWrapper);
        List<HotelStatus> editedHotelStatusList=new ArrayList<>();
        List<RoomStatus> roomStatusList=roomStatus.selectList(roomStatusQueryWrapper);
        List<RoomStatus> editedRoomStatusList=new ArrayList<>();
        Period period=Period.between(
                LocalDate.parse(order.getDateStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(order.getDateEnd(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        if(order.getRoomCount()>room.getCount()) throw new HotelRoomInsufficientException();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        order.setPrice(room.getPrice()*order.getRoomCount()*(period.getDays()+1));
        //保存订单
        save(order);
        //先假设所有记录都不存在，则每个房间订购记录都为该用户订购的房间数
        for(int i=0;i<=period.getDays();i++){
            HotelStatus hotelStatus1=new HotelStatus();
            RoomStatus roomStatus1=new RoomStatus();
            hotelStatus1.setHotelId(order.getHotelId());
            roomStatus1.setRoomId(order.getOrderRoomId());
            Date d;
            d=df.parse(order.getDateStart());
            hotelStatus1.setRecordForDate(new Date(d.getTime()+i*24*60*60*1000));
            roomStatus1.setRecordForDate(new Date(d.getTime()+i*24*60*60*1000));
            hotelStatus1.setHotelRoomOrdered(order.getRoomCount());
            hotelStatus1.setHotelId(room.getTheRoomHotelId());
            roomStatus1.setRoomNum(order.getRoomCount());
            editedHotelStatusList.add(hotelStatus1);
            editedRoomStatusList.add(roomStatus1);
        }
        for(HotelStatus status:hotelStatusList){
            period=Period.between(
                    LocalDate.parse(order.getDateStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    DateToLocaleDate(status.getRecordForDate())
            );
            status.setHotelRoomOrdered(status.getHotelRoomOrdered()+order.getRoomCount());
            if(status.getHotelRoomOrdered()>room.getCount()){
                throw new HotelRoomInsufficientException();
            }
            editedHotelStatusList.set(period.getDays(),status);
        }
        for(RoomStatus status:roomStatusList){
            period=Period.between(
                    LocalDate.parse(order.getDateStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    DateToLocaleDate(status.getRecordForDate())
            );
            status.setRoomNum(status.getRoomNum()+order.getRoomCount());
            if(status.getRoomNum()>room.getCount()){
                throw new HotelRoomInsufficientException();
            }
            editedRoomStatusList.set(period.getDays(),status);
        }
        for(RoomStatus status:editedRoomStatusList){
            status.insertOrUpdate();
        }

        for(HotelStatus status:editedHotelStatusList){
            status.insertOrUpdate();
        }
        return true;
    }

    @Override
    public String payOrder(Long orderId) throws OrderNotFoundException, UserNotFoundException, OrderStatusException {
        User user=((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if(user==null) throw new UserNotFoundException();
        //用户只能支付自己创建的订单
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",orderId).eq("order_user_id",user.getId());
        Order order=getOne(queryWrapper);
        if(order==null) throw new OrderNotFoundException();

        //只有未付款订单可以付款
        if(!order.getStatus().equals("UNPAID")) throw new OrderStatusException();

        HotelRoom room=new HotelRoom();
        room=room.selectById(order.getOrderRoomId());
        Hotel hotel = new Hotel();
        QueryWrapper<Hotel> wrapper=new QueryWrapper<>();
        wrapper.eq("hotel_id",order.getHotelId());
        hotel=hotel.selectOne((Wrapper)wrapper);



        AlipayTradePagePayRequest request=new AlipayTradePagePayRequest();
        request.setReturnUrl("http://localhost:8280/pay/CallBack/return");
        request.setNotifyUrl("http://localhost:8280/pay/CallBack/notify");//在公共参数中设置回跳和通知地址

        request.setBizContent("{" +
                "    \"out_trade_no\":\""+order.getId()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+order.getPrice()+"," +
                "    \"subject\":\""+hotel.getHotelName()+" Order\"," +
                "    \"body\":\""+"HotelManager.Inc"+"\"," +
                "    \"timeout_express\":\"5m\","+
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            AlipayTradePagePayResponse response=alipayClient.pageExecute(request);
            form = response.getBody(); //调用SDK生成表单
            //orderMapper.insert(order);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    @Override
    public boolean payOrderComplete(Long orderId,String tradeNo) throws OrderNotFoundException, OrderStatusException {
        Order order=getById(orderId);
        if(order==null){
            throw new OrderNotFoundException();
        }
        if(!order.getStatus().equals("UNPAID")) throw new OrderStatusException();
        order.setOrderId(tradeNo);
        order.setStatus("PAID");
        saveOrUpdate(order);
        return false;
    }

    @Override
    @Transactional
    public boolean refundOrder(Long orderId) {

        System.out.println("==============================");
        //根据订单号获取订单
        Order order = new Order();
        QueryWrapper<Order> query = new QueryWrapper<>();
        query.eq("order_id",orderId);
        order = order.selectOne(query);
        //得到开始时间和结束时间
        String dateStart = order.getDateStart();
        String dateEnd = order.getDateEnd();

        Date start = null,end=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sdf.parse(dateStart);
            end = sdf.parse(dateEnd);
            System.out.println(start+"->"+end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (start.getTime()==end.getTime())
            updateDB(order,dateStart);
        else{
            for(Date date = start; date.getTime() != end.getTime(); date = dateAdd(date)){
                String useDate = sdf.format(date);
                System.out.println("date:"+useDate);

                updateDB(order,useDate);

            }
        }

        return true;
    }

    @Override
    public String checkPaymentStatus(Long orderId) throws OrderNotFoundException, AlipayApiException {
        User user=((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("id",orderId).eq("order_user_id",user.getId());
        Order order=getOne(wrapper);
        //order不能为空
        if(order==null) throw new OrderNotFoundException();
        //如果order状态是已经完成则不必查询
        switch(order.getStatus()){
            case "PAID": case "REFUND": case "CANCEL": return order.getStatus();
        }
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        request.setBizContent("{" +
                "    \"out_trade_no\":\""+order.getId()+"\"" +
                "  }");

        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            order.setOrderId(response.getTradeNo());
            response.getBuyerLogonId();
            String status=response.getTradeStatus();
            switch (status){
                case "WAIT_BUYER_PAY":
                    order.setStatus("UNPAID");
                    break;
                case "TRADE_SUCCESS": case "TRADE_FINISHED":
                    try{
                        payOrderComplete(orderId,order.getOrderId());
                        order.setStatus("PAID");
                        order.setBuyerAlipay(response.getBuyerLogonId());
                        order.insertOrUpdate();
                    } catch (OrderStatusException ignored) {
                        ;
                    }
                    break;
            }
        }
        return order.getStatus();
    }

    public Date dateAdd(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        return date;
    }

    public void updateDB(Order order,String useDate){
        //得到酒店ID
        int hotelId = order.getHotelId();
        //根据酒店Id得到实例
        HotelStatus hotelStatus = new HotelStatus();
        QueryWrapper<HotelStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("hotel_id",hotelId).eq("record_for_date",useDate);
        hotelStatus = hotelStatus.selectOne(queryWrapper);
        hotelStatus.setHotelRoomOrdered(hotelStatus.getHotelRoomOrdered()-order.getRoomCount());
        hotelStatus.updateById();

        //得到房间ID
        int theRoomId = order.getOrderRoomId();
        RoomStatus roomStatus = new RoomStatus();

    }

    protected  LocalDate DateToLocaleDate(Date date) {

        Instant instant = date.toInstant();

        ZoneId zoneId  = ZoneId.systemDefault();

        return instant.atZone(zoneId).toLocalDate();

    }

    @Override
    public List<Order> searchOrder(int currentPage, int size, OrderSearchConditionType condition) {
        Page<Order> p=new Page<>(currentPage,size);
        return page(p,buildWrapper(condition)).getRecords();
    }

    private QueryWrapper<Order> setCondition(OrderSearchConditionType conditionType, QueryWrapper<Order> wrapper){
        if(conditionType==null) return wrapper;
        wrapper.le(conditionType.getHigh()!=null,conditionType.getTarget(),conditionType.getHigh());
        wrapper.ge(conditionType.getLow()!=null,conditionType.getTarget(),conditionType.getLow());
        wrapper.like(conditionType.getLike()!=null,conditionType.getTarget(),conditionType.getLike());
        wrapper.eq(conditionType.getEq()!=null,conditionType.getTarget(),conditionType.getEq());
        wrapper.in(conditionType.getIn()!=null,conditionType.getTarget(),conditionType.getIn());

        wrapper.orderByAsc(conditionType.getOrderByAsc()!=null,conditionType.getOrderByAsc());
        wrapper.orderByDesc(conditionType.getOrderByDesc()!=null,conditionType.getOrderByDesc());

        return wrapper;
    }


    private Wrapper<Order> buildWrapper(OrderSearchConditionType conditionType){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        if(conditionType==null) return wrapper;
        wrapper=setCondition(conditionType,wrapper);
        while(conditionType.getAnd()!=null||conditionType.getOr()!=null){
            if(conditionType.getOr()!=null){
                conditionType = conditionType.getOr();
                wrapper.or();
                wrapper=setCondition(conditionType,wrapper);
            }else{
                conditionType = conditionType.getAnd();
                wrapper=setCondition(conditionType,wrapper);
            }
        }
        return wrapper;
    }
}