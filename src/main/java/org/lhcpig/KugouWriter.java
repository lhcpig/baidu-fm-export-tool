package org.lhcpig;

import com.google.common.collect.Lists;
import org.lhcpig.xml.Favorites;
import org.lhcpig.xml.FileElement;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Created by liuhengchong on 2015/7/13.
 */
public class KugouWriter {

    public void write(List<Song> songs) {
        Favorites favorites = build(songs);
        try {
            File file = new File(getFilePath().orElse("./baiduFM.kgl"));
            JAXBContext jaxbContext = JAXBContext.newInstance(Favorites.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(favorites, file);
            jaxbMarshaller.marshal(favorites, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    protected Optional<String> getFilePath() {

        Properties p = new Properties();
        try {
            p.load(Main.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            return Optional.empty();

        }
        return Optional.ofNullable(p.getProperty("output"));
    }

    protected Favorites build(List<Song> songs) {
        if (songs == null || songs.isEmpty()) {
            return new Favorites(Collections.<FileElement>emptyList());
        }
        List<FileElement> fileElementList = Lists.newArrayListWithCapacity(songs.size());
        for (Song song : songs) {
            fileElementList.add(new FileElement(convertSong(song)));
        }
        return new Favorites(fileElementList);
    }

    protected String convertSong(@Nonnull Song song) {
        return String.format("%s - %s.mp3", song.getSingger(), song.getSongName());
    }
}
