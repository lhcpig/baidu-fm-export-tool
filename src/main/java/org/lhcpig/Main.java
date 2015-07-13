package org.lhcpig;

import com.google.common.collect.Lists;

import java.io.IOException;

/**
 * Created by lhcpig on 2015/7/11.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        new KugouWriter().write(Lists.newArrayList(new Song("singger1", "songName1"), new Song("singger2", "songName2")));
    }
}
