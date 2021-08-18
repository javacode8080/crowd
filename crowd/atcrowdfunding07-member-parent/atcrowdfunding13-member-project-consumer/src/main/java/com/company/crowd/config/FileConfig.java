package com.company.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件路径与虚拟机路径:这里是为了解决img图片显示无法访问本地文件【因为img默认src路径头为http://localhost/】
 *
 * @author jingshiyu
 * @Description
 * @date 2019/5/15 17:07
 * @param
 * @return
 */

@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 文件磁盘url 映射
        // 配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        //此处将  /PlaybackData/  映射为右侧的路径
        registry.addResourceHandler("/JAVA/**").addResourceLocations("file:///D:/JAVA/");//linux,注意Linux中不要有//
        //若是java:"file:E:\\\\wetemHeadUrlbProject\\FileWebsite\\PlaybackData\\"  (其中\\可以换为/)
        //http://localhost:13389/PlaybackData/434524.txt
        //映射为:/home/javauser/project1/logs/files/flydata/PlaybackData/434524.txt
    }
}
