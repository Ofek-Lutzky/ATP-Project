package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {

    }

    /**
     *
     * @param b byte[]
     * @throws IOException
     * after thought i deciede to convert the number that represent the maze from binary to integers and then pass it -> 101 = 5
     * but the max is 256 so the maxx digits in the binary is 8 -> 11111111 = 255
     */

    @Override
    public void write(byte[] b) throws IOException {
        try{
        //at start it like the simpleCompress just convert the numbers from in to byte
        int indexArray = 0;
        //the places 0-5 are the simple number bytes and after the board will care of
        int indexFirstBytes = 0;
        while(indexFirstBytes<6){
            indexArray = outWriteBytes(b,indexArray); // will write the out put of the byte to the OutputStream
            indexFirstBytes++;
        }

        //special case
//        if (b[indexArray] == 0){
//            this.out.write(0);
//        }

        while (indexArray < b.length){

            String binaryToInt = "";

            for (int i = 0; i < 8; i++){
                //this if help us to deal with the last convert that might be less then 8
                // and adding 0 from right for the decompress will be easy
                if (indexArray + i >= b.length){
                    int size = 8- binaryToInt.length();
                    for (int j = 0; j < size; j++){
                        binaryToInt = binaryToInt + "0";
                    }
                    break;
                }
                else{
                    binaryToInt = binaryToInt + Byte.toString(b[indexArray + i]);
                }

            }


            this.out.write(Integer.parseUnsignedInt(binaryToInt,2));
            indexArray += 8;
            binaryToInt = "";

        }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     *
     * @param b byte[]
     * @param indexA int
     * @return indexA
     * will convert and write the byte as int in the OutputStream
     */
    // will convert and write the byte as int in the OutputStream
    private int outWriteBytes(byte[] b, int indexA){
        try{
            if (b[indexA] == 0){
                //can happen just in case of 0 and the 0 of the sperate int the end return the index of the start of the next bytes we want to write
                this.out.write(0);
                indexA ++;
                this.out.write(0);
                indexA ++;

            }
            else{
                while (b[indexA] !=  0){
                    this.out.write(b[indexA]);
                    indexA++;
                }
                //got to zero what mean it is the end of this number so we will return the index of the start of the next bytes we want to write
                this.out.write(b[indexA]);
                indexA++;
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return indexA;// maybe dont need becuase it is in the scope of the func
    }
}
