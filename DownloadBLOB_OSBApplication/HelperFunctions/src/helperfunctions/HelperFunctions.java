package helperfunctions;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;


public class HelperFunctions {
    
    public HelperFunctions() {
        super();
    }

   

    public static DataSource decode64ToStream(String b64Document) throws IOException {
        final BASE64Decoder decoder = new BASE64Decoder();
        final InputStream inputStream = new ByteArrayInputStream(b64Document.getBytes());
        byte[] decodedBytes = decoder.decodeBuffer(inputStream);

        MyDataSource mds = new MyDataSource();
        mds.setData(decodedBytes);
        return mds;
    }

    public static void main(String args[]) throws IOException {



        File file = new File("sample.pdf");
        byte[] bytes = loadFile(file);
        final BASE64Encoder encoder = new BASE64Encoder();
        String encodedString = encoder.encodeBuffer(bytes);
       
 
        InputStream str = decode64ToStream(encodedString).getInputStream();
        

        OutputStream out = new FileOutputStream("sampleregenerated.pdf");
        byte[] filecontent = new byte[bytes.length];
        str.read(filecontent);
        out.write(filecontent);
        out.close();

    }

    @SuppressWarnings("oracle.jdeveloper.java.nested-assignment")
    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large

        }
        byte[] bytes = new byte[(int) length];

        int offset;
        offset = 0;
        int numRead;
        numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}
