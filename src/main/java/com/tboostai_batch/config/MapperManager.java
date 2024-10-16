package com.tboostai_batch.config;


import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperManager {

    private final ApplicationContext context;

    @Autowired
    public MapperManager(ApplicationContext context) {
        this.context = context;
    }

    // 使用泛型方法来动态获取特定的 Mapper
    public <T> T getMapper(Class<T> mapperClass) {
        return context.getBean(mapperClass);  // 通过类型获取 Mapper
    }
}