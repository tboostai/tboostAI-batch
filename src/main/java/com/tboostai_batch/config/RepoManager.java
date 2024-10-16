package com.tboostai_batch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Component
public class RepoManager {

    private final ApplicationContext context;

    @Autowired
    public RepoManager(ApplicationContext context) {
        this.context = context;
    }

    // 使用泛型方法来动态获取特定的 Repository
    public <T> T getRepo(Class<T> repoClass) {
        return context.getBean(repoClass);  // 通过类型获取 Repository
    }
}
