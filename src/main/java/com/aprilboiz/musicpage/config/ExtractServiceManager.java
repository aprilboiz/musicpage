package com.aprilboiz.musicpage.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ExtractServiceManager {
    private static final Logger logger = LoggerFactory.getLogger(ExtractServiceManager.class);
    private Process extractService;
    private final String serviceName = "Video metadata extract service";

    @PostConstruct
    public void startExtractService() {
        try {
            logger.info("Starting {}", serviceName);
            File logFile = new File("extract_service.log");
            if (!logFile.exists()){
                if (!logFile.createNewFile()){
                    logger.error("Failed to create log file for {}", serviceName);
                }
            }

            ProcessBuilder pb = new ProcessBuilder("python", "metadata_extract_service.py");
            pb.redirectOutput(logFile);
            pb.redirectErrorStream(true);

            extractService = pb.start();
            logger.info("{} started with PID: {}", serviceName, extractService.pid());
        } catch (IOException e) {
            logger.error("Failed to start {}", serviceName, e);
        }
    }

    @PreDestroy
    public void stopExtractService() {
        if (extractService != null && extractService.isAlive()) {
            logger.info("Stopping {}", serviceName);
            extractService.destroy();
            try {
                extractService.waitFor();
                logger.info("{} stopped", serviceName);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Failed to stop {}",serviceName, e);
            }
        }
    }
}
