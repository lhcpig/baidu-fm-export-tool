package org.lhcpig.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by liuhengchong on 2015/7/13.
 */
@XmlRootElement(name = "List")
public class Favorites {
    String listName = "百度FM";
    List<FileElement> fileElements;

    private Favorites() {
    }

    public Favorites(List<FileElement> fileElements) {
        this.fileElements = fileElements;
    }

    public String getListName() {
        return listName;
    }

    @XmlAttribute(name = "ListName")
    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<FileElement> getFileElements() {
        return fileElements;
    }

    @XmlElement(name = "File")
    public void setFileElements(List<FileElement> fileElements) {
        this.fileElements = fileElements;
    }
}
