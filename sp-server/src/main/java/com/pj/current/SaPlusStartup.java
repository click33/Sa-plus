package com.pj.current;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * springboot启动之后 
 */
@Component
public class SaPlusStartup implements CommandLineRunner {
    
	@Value("${server.port:8080}")
    private String port;

    @Value("${server.servlet.context-path:}")
    private String path;

    @Override
    public void run(String... args) throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        String str = "\n------------- sa-plus 启动成功 -------------\n" + 
                "\t- Local:   http://localhost:" + port + path + "\n" +
                "\t- Local2:  http://127.0.0.1:" + port + path + "\n" +
                "\t- Network: http://" + ip + ":" + port + path + "\n";
        System.out.println(str);
    }


}

