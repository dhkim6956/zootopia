package com.ssafy.rentserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis.cluster")
public class RedisClusterConfig {

    private List<String> nodes;

    public void setNodes(List<String> nodes){
        this.nodes = nodes;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(redisClusterConfiguration());
    }

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        nodes.forEach(hostAndPort -> {
            String[] args = hostAndPort.split(":");
            RedisNode node = new RedisNode(args[0], Integer.parseInt(args[1]));
            redisClusterConfiguration.addClusterNode(node);
        });
        return redisClusterConfiguration;
    }




}
