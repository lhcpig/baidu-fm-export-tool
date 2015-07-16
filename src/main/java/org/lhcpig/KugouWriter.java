package org.lhcpig;

import com.google.common.collect.Lists;
import org.lhcpig.xml.Favorites;
import org.lhcpig.xml.FileElement;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by lhcpig on 2015/7/13.
 */
public class KugouWriter {
    private String outputPath;

    public KugouWriter(@Nonnull String outputPath) {
        this.outputPath = outputPath;
    }

    public void write(List<Song> songs) {
        Favorites favorites = build(songs);
        try {
            File file = new File(outputPath);
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
