package com.shixi.hotelmanager.Query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {
    @Autowired
    private UserMapper mapper;

    public List<User> findUsers() {
        List<User> userList = new ArrayList<User>();
        userList = mapper.selectList(null);
        return userList;
    }
}
