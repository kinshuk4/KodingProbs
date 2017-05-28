package com.vaani.downloader.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.transform.stream.StreamSource;

import com.vaani.downloader.config.AppConfig;
import com.vaani.downloader.model.DownloadQueue;
import com.vaani.downloader.mq.DownloadManager;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.Unmarshaller;

public class DownloadApp {

    final static Logger LOGGER = LoggerFactory.getLogger(DownloadApp.class);

    private Unmarshaller unmarshaller;

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public DownloadQueue loadQueue(String filePath) throws FileNotFoundException, IOException {
        FileInputStream fis = null;
        DownloadQueue downloadQueue = null;
        try {
            fis = new FileInputStream(filePath);
            downloadQueue = (DownloadQueue) this.unmarshaller.unmarshal(new StreamSource(fis));
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        return downloadQueue;
    }

    public static void main(String[] args) throws IOException, Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        DownloadApp application = (DownloadApp) context.getBean(DownloadApp.class);

        AppConfig applicationConfig = (AppConfig) context.getBean(AppConfig.class);
        DownloadQueue downloadQueue = application.loadQueue(applicationConfig.getDownloadQueueFile());
        if (applicationConfig.getMaxThreads() < downloadQueue.getNumDownloads()) {
            throw new Exception(String.format("Maximum Download is %d %n. Please reduce number of downloads.", applicationConfig.getMaxThreads()));
        }
        LOGGER.info(String.format("Found %d download job", downloadQueue.getNumDownloads()));

        DownloadManager controller = context.getBean(DownloadManager.class);
        controller.setMaxThreads(applicationConfig.getMaxThreads());
        downloadQueue.getDownloadList().stream().forEach((downloadResource) -> {
            LOGGER.info(downloadResource.toString());
            controller.queueDownload(downloadResource);
        });

        LOGGER.info("Starting all download.");
        controller.start();
        
        for (;;) {
            boolean hasActiveSession = controller.hasActiveDownload();
            if (!hasActiveSession) {
                LOGGER.info("All Download has finished.");
                controller.stop();
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                LOGGER.warn(ex.getMessage(), ex);
            }
        }
    }
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
