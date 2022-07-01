package IO;

import java.io.IOException;
import java.io.InputStream;


public class SimpleDecompressorInputStream extends InputStream {
    InputStream in;
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }


    @Override
    public int read() throws IOException {
        return 0;
    }

    /**
     *
     * @param b byte[]
     * @return  indexArray
     * decompress the simple
     */
    @Override
    public int read(byte[] b){
        try {
            int indexArray = 0;
            int current = 1;
            byte[] compressedBytes = in.readAllBytes();

            int indexFirstBytes = 0;
            while(indexFirstBytes<6){
                indexArray = inReadBytes(compressedBytes,b,indexArray); // will write the out put of the byte to the OutputStream
                indexFirstBytes++;
            }
            int index = indexArray;
            while(index< compressedBytes.length){
                indexArray = inBoardReadBytes(b,compressedBytes[index],current,indexArray);
                index++;
                if (current == 0){
                    current = 1;
                }
                else{
                    current = 0;
                }
            }



        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return b.length;
    }

    private int inBoardReadBytes(byte[] b, byte compressedByte, int current, int indexArray) {
        int i = 0;
        while (i<Byte.toUnsignedInt(compressedByte)){
            b[indexArray] = (byte) current;
            i++;
            indexArray++;
        }

        return indexArray;
    }

    /**
     *
     * @param compressedBytes byte[]
     * @param b  byte[]
     * @param indexArray int
     * @return
     *  //know this method help us to convert the byte from the compress method to decompress
     *     // we will do just like the compress but upsite down each
     */

    private int inReadBytes(byte[] compressedBytes, byte[] b, int indexArray) {
        try{
            if (compressedBytes[indexArray] == 0){
                b[indexArray] = compressedBytes[indexArray];
                indexArray += 1;
                b[indexArray] = compressedBytes[indexArray];
                indexArray += 1;

            }

            else{
                while (compressedBytes[indexArray] != 0 ){
                    b[indexArray] = compressedBytes[indexArray];
                    indexArray += 1;
                }
                b[indexArray] = compressedBytes[indexArray];
                indexArray += 1;
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return indexArray;
    }

}
