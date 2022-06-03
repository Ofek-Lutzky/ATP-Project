package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out ) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {

    }

    @Override
    public void write(byte[] b) throws IOException {
        try{

            int indexArray = 0;

            //todo i think we need to start in zero not one not sure
            int current = 1; // will represent the maze number we want to count now

            //the places 0-5 are the simple number bytes and after the board will care of
            int indexFirstBytes = 0;
            while(indexFirstBytes<6){
                indexArray = outWriteBytes(b,indexArray); // will write the out put of the byte to the OutputStream
                indexFirstBytes++;
            }
            //special case
            if (b[indexArray] == 0){
                this.out.write(0);
            }
            //here is the part of the byte array that all the data of the board it self is find
            // do now we will start the compress by the method inn way of the instractor says
            while(indexArray < b.length -1){
                if (current != b[indexArray]){
                    if (current == 0){
                        current = 1;
                    }
                    else{
                        current = 0;
                    }

                }
                else{
                    indexArray = outWriteBoardBytes(b,current,indexArray);//write maze;
                }
            }

            if (b[b.length-2] != 1 && b[b.length-1] == 1){
                this.out.write(1);
            }



        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


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

    // will convert and write the board bytes as counter of each in the OutputStream
    //the algo get check if we are on the same number on the board .
    // if yes: we will count the appearances of the number.
    // if the appearances > 255 we will add the 255,0, the countinue count left
    // return the index so the function called us will now were are we in the array
    private int outWriteBoardBytes(byte[] b,int current, int indexA){
        try{
            int counter = 0;
            while (b[indexA] == current){

                counter++;

                if (indexA < b.length -1) // todo check if needed without the -1
                    indexA++;
                else
                    break;
            }

            if (counter<=255){
                this.out.write(counter);
                return indexA;
            }

            else{
                while (counter >= 255){
                    this.out.write(255);
                    this.out.write(0);
                    counter = counter - 255; // if it bigger then 255 we want tto write 255,0,leftCount

                }
                if (counter >=  0){
                    this.out.write(counter);
                    return indexA;
                }
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return indexA;// maybe dont need becuase it is in the scope of the func
    }


}
