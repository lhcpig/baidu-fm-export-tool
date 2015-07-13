package org.lhcpig;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gson.*;
import org.jsoup.Jsoup;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by liuhengchong on 2015/7/13.
 */
public class BaiduFmTaker {

    private int pageLen = 10;
    private String bduss;

    public BaiduFmTaker(@Nonnull String bduss) {
        this.bduss = bduss;
    }

    private int getUserCount() throws IOException {
        String body = Jsoup.connect("http://fm.baidu.com/dev/api/?tn=usercounts")
                .cookie("BDUSS", bduss)
                .ignoreContentType(true)
                .execute()
                .body();

        JsonElement parse = new JsonParser().parse(body);
        JsonObject asJsonObject = parse.getAsJsonObject();
        JsonObject counts = asJsonObject.getAsJsonObject("counts");
        JsonPrimitive like_songs = counts.getAsJsonPrimitive("like_songs");
        return like_songs.getAsInt();
    }

    private List<Integer> getSongIds(int pageNo, int pageLen) throws IOException {
        String body = Jsoup.connect("http://fm.baidu.com/dev/api/?tn=songlist&songtype=0&pageno=" + pageNo + "&pagelen=" + pageLen)
                .cookie("BDUSS", bduss)
                .ignoreContentType(true)
                .execute()
                .body();
        JsonArray list = new JsonParser().parse(body).getAsJsonObject().getAsJsonArray("list");
        List<Integer> ids = Lists.newArrayListWithCapacity(list.size());
        for (JsonElement jsonElement : list) {
            int id = jsonElement.getAsJsonObject().getAsJsonPrimitive("id").getAsInt();
            ids.add(id);
        }
        return ids;
    }

    private List<Song> getSongList(List<Integer> songIds) throws IOException {
        if(songIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Song> result = Lists.newArrayListWithCapacity(songIds.size());
        String songIdsString = Joiner.on(",").join(songIds);
        String body = Jsoup.connect("http://fm.baidu.com/data/music/songinfo?songIds=" + songIdsString)
                .header("User-Agent","Mozilla/5.0")
                .ignoreContentType(true)
                .execute()
                .body();
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        if(jsonObject.getAsJsonPrimitive("errorCode").getAsInt() != 22000){
            System.out.println(body);
            return Collections.emptyList();
        }
        JsonArray songJsonArray = jsonObject.getAsJsonObject("data").getAsJsonArray("songList");
        for (JsonElement songJsonElement : songJsonArray) {
            JsonObject songJsonObject = songJsonElement.getAsJsonObject();
            String songName = songJsonObject.getAsJsonPrimitive("songName").getAsString();
            String artistName = songJsonObject.getAsJsonPrimitive("artistName").getAsString();
            result.add(new Song(artistName, songName));
        }
        return result;
    }

    public List<Song> getAllSong() throws IOException {
        int songCount = getUserCount();
        if (songCount <= 0) {
            return Collections.emptyList();
        }
        List<Song> result = Lists.newArrayListWithCapacity(songCount);
        int pageCount = songCount / pageLen + 1;
        for (int page = 1; page <= pageCount; page++) {
            List<Integer> songIds = getSongIds(page, pageLen);
            List<Song> songList = getSongList(songIds);
            result.addAll(songList);
        }
        return result;
    }

}
