package com.eli.m3u8.controller;

import com.eli.m3u8.download.M3u8DownloadFactory;
import com.eli.m3u8.listener.DownloadListener;
import com.eli.m3u8.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author Eli
 * @date 2022/11/27 12:02
 */
@RestController
public class M3u8Controller {

    @Autowired
    private SimpMessagingTemplate wsTemplate;

    @PostMapping("/download")
    public void download(@RequestBody M3u8 m3u8) {
        M3u8DownloadFactory.M3u8Download m3u8Download = M3u8DownloadFactory.getInstance(m3u8.getFileUrl());
        //设置生成目录
        m3u8Download.setDir(m3u8.getFileDic());
        //设置视频名称
        m3u8Download.setFileName(m3u8.getFileName());
        //设置线程数
        m3u8Download.setThreadCount(100);
        //设置重试次数
        m3u8Download.setRetryCount(100);
        //设置连接超时时间（单位：毫秒）
        m3u8Download.setTimeoutMillisecond(10000L);
        //设置日志级别
        //可选值：NONE INFO DEBUG ERROR
        m3u8Download.setLogLevel(Constant.INFO);
        //设置监听器间隔（单位：毫秒）
        m3u8Download.setInterval(500L);
        //添加额外请求头
        //Map<String, Object> headersMap = new HashMap<>();
        //headersMap.put("Content-Type", "text/html;charset=utf-8");
        //m3u8Download.addRequestHeaderMap(headersMap);
        //如果需要的话设置http代理
        //m3u8Download.setProxy("172.50.60.3",8090);
        //添加监听器
        m3u8Download.addListener(new DownloadListener() {
            @Override
            public void start() {
                System.out.println("开始下载");
            }

            @Override
            public void process(String downloadUrl, int finished, int sum, int consume, float percent, String speed) {
                wsTemplate.convertAndSend("/topic/percent", new HashMap() {{
                    put("percent", percent);
                    put("speed", "分片：" + finished + "/" + sum + "\t用时：" + consume + "秒\t" + "速度：" + speed);
                }});
            }

            @Override
            public void end() {
                System.out.println("下载完毕");
            }
        });
        //开始下载
        m3u8Download.start();
    }

    static class M3u8 {
        private String fileDic;
        private String fileName;
        private String fileUrl;

        public String getFileDic() {
            return fileDic;
        }

        public void setFileDic(String fileDic) {
            this.fileDic = fileDic;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}
