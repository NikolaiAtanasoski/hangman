package hangman.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public abstract class JSONHelper {

    private static final String WORK_DIR = System.getProperty("user.dir");
    protected String jsonFile;

    public JSONHelper(String jsonFile){
        this.jsonFile =  WORK_DIR + File.separator + jsonFile;
    }
    public void writeToFile(String json) throws IOException {
        File file = new File(jsonFile);
        OutputStream fileout = new FileOutputStream(file);
        Writer writer = new BufferedWriter(new OutputStreamWriter(fileout, "UTF-8"));
        writer.write(json);
        writer.close();
        fileout.close();

    }
}
