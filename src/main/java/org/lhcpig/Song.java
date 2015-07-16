package org.lhcpig;

/**
 * Created by lhcpig on 2015/7/13.
 */
public class Song {
    private String singger;
    private String songName;

    public Song(String singger, String songName) {
        this.singger = singger;
        this.songName = songName;
    }

    public String getSingger() {
        return singger;
    }

    public String getSongName() {
        return songName;
    }
}
