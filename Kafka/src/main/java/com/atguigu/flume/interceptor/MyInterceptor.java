package com.atguigu.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;
import java.util.Map;

/**
 * @author yhm
 * @create 2021-02-23 15:50
 * 1. 实现拦截器接口
 * 2. 实现方法
 * 3. 静态内部类 实现接口 Builder
 */
public class MyInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        // 获取标志位
        byte b = event.getBody()[0];
        Map<String, String> headers = event.getHeaders();
        // 判断标志位的类型
        if ((b >= 'A' && b <= 'Z') || (b >= 'a' && b <= 'z')){
            // b为字母
            headers.put("topic","first");
        }else if ( b >= '0' && b <= '9'){
            // b 为数字
            headers.put("topic","second");
        }
        event.setHeaders(headers);
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        // 循环遍历list
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    @Override
    public void close() {

    }

    public static class MyBuilder implements Builder{
        @Override
        public Interceptor build() {
            return new MyInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
