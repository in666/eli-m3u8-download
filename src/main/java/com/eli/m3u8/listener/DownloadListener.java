package com.eli.m3u8.listener;

public interface DownloadListener {

    void start();

    void process(String downloadUrl, int finished, int sum, int consume, float percent, String speed);

    void end();

}
