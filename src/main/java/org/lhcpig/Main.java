package org.lhcpig;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Created by lhcpig on 2015/7/11.
 */
public class Main {
    private static String outputPath;
    private static String BDUSS;
    private static Properties p = new Properties();
    static {
        try {
            p.load(Main.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputPath = Optional.ofNullable(p.getProperty("output")).orElse("./baiduFM.kgl");
        BDUSS = Optional.ofNullable(p.getProperty("BDUSS")).get();
    }
    public static void main(String[] args) throws IOException {
        BaiduFmTaker baiduFmTaker = new BaiduFmTaker(BDUSS);
        List<Song> allSong = baiduFmTaker.getAllSong();
        new KugouWriter(outputPath).write(allSong);

    }

}
