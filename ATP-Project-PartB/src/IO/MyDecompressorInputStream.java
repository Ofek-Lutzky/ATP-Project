package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b){
        try {
            int indexArray = 0;
            byte[] compressedBytes = in.readAllBytes();

            int indexFirstBytes = 0;
            while(indexFirstBytes<6){
                indexArray = inReadBytes(compressedBytes,b,indexArray); // will write the out put of the byte to the OutputStream
                indexFirstBytes++;
            }

            int index = indexArray;
            while(indexArray< compressedBytes.length){

                int number = (int)compressedBytes[indexArray];
                if(number < 0){
                    byte temp = (byte) compressedBytes[indexArray];
                    number = (int) Byte.toUnsignedInt(temp);
                }
                String binaryS = Integer.toBinaryString(number);
                //padding for numbers that the value of them iis less then 8 digits (byte)
                // beside the last one that can have less then 8 digits in it's binary represent && indexArray != compressedBytes.length -1

                if (binaryS.length() < 8){
                    int size = (8 - binaryS.length());
                    for (int i = 0; i< size;i++){
                        binaryS = "0" + binaryS;
                    }
                }


                int indexBinary = 0;
                while (indexBinary < binaryS.length()){

                    if (index == b.length)
                        break;

                    b[index] = (byte) Integer.parseInt(String.valueOf(binaryS.charAt(indexBinary)));

                    indexBinary++;
                    index++;
                }
                indexArray++;

            }



        }
        catch (Exception e){
            System.out.println(e.getMessage() + " Shit");
        }
        return b.length;
    }


    //know this method help us to convert the byte from the compress method to decompress
    // we will do just like the compress but upsite down each
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
