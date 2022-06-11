package org.walker.tprDataConverter.workers;

import java.io.File;
import java.io.FilenameFilter;

public class Filter implements FilenameFilter {

    String fileName;

    public Filter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean accept(File dir, String file) {
        // TODO Auto-generated method stub
        return file.equalsIgnoreCase(fileName+"_res.xml");
    }

}
