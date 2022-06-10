package SimpleCalculator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class OutputSolution {

    private final String result;

    public OutputSolution(String result) {
        this.result = result;
        output();
    }

    private void output() {
        try (RandomAccessFile file = new RandomAccessFile("solution.txt", "rw");
                FileChannel channel = file.getChannel();) {
            file.seek(file.length());              //moved to the end of the file
            ByteBuffer buffer = ByteBuffer.wrap((result+"\n").getBytes());  //write array of bytes to buffer
            
            channel.write(buffer); // written from buffer to byte

        } catch (FileNotFoundException fne) {
            System.out.println("chek file " + fne.getStackTrace());
        } catch (IOException ioe) {
            System.out.println("chek file " + ioe.getStackTrace());
        }
    }
    

}
