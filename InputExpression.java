package SimpleCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class InputExpression {

    private StringBuilder str = new StringBuilder();  //write from buffer

   
    public String input() throws IOException {

        try (RandomAccessFile accessFile = new RandomAccessFile("calculator.txt", "rw");
                FileChannel channel = accessFile.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(25);   //buffer can get 25 byte
            int byteRead = channel.read(buffer); //reads a sequence of bytes
            if (byteRead < 0) {
                throw new IOException();
            }
            while (byteRead > 0) {
                buffer.flip();                       //change mode to read
                while (buffer.hasRemaining()) {
                    str.append((char) buffer.get());  //append simbol in str,while buffer is not empty
                }
                buffer.clear();                      //clear buffer and reads a sequence of bytes again
                byteRead = channel.read(buffer);
            }

        }
      
        return str.toString();
    }
    
     
}
