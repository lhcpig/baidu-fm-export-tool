package org.lhcpig.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by liuhengchong on 2015/7/13.
 */
@XmlRootElement(name = "List")
public class FileElement {

    String fileName;

    private FileElement() {
    }

    public FileElement(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @XmlElement(name = "FileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
