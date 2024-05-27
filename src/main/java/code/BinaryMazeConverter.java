package code;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class BinaryMazeConverter {
    public void binToTxt(String filePath) throws IOException {
        try (DataInputStream input = new DataInputStream(new FileInputStream(filePath));
             FileWriter output = new FileWriter("tmp.txt")) {

            int id = Integer.reverseBytes(input.readInt());
            int escape = input.read();
            short columns = Short.reverseBytes(input.readShort());
            short lines = Short.reverseBytes(input.readShort());
            short entryX = Short.reverseBytes(input.readShort());
            short entryY = Short.reverseBytes(input.readShort());
            short exitX = Short.reverseBytes(input.readShort());
            short exitY = Short.reverseBytes(input.readShort());
            int res1 = Integer.reverseBytes(input.readInt());
            int res2 = Integer.reverseBytes(input.readInt());
            int res3 = Integer.reverseBytes(input.readInt());
            int counter = Integer.reverseBytes(input.readInt());
            int solution_offset = Integer.reverseBytes(input.readInt());
            int separator = input.read();
            int wall = input.read();
            int path = input.read();

            for(int i = 0; i < lines; i++){
                if(i != 0) output.write('\n');
                for(int j = 0; j < columns; j++){
                    char c;
                    int tmp;
                    int amount;

                    tmp = input.read();
                    tmp = input.read();

                    if(tmp == wall){
                        c = 'X';
                    }else if(tmp == path){
                        c = ' ';
                    }else{
                        c = '#';
                    }

                    tmp = input.read();
                    amount = tmp + 1;
                    int count = 0;
                    j--;

                    while(count < amount){
                        j++;
                        if(j == columns){
                            j = 0;
                            i++;
                        }

                        if(i == entryY - 1 && j == entryX - 1){
                            output.write('P');
                        }
                        else if (i == exitY - 1 && j == exitX - 1){
                            output.write('K');
                        } 
                        else {
                            output.write(c);
                        }
                        count++;
                    }
                }
            }
        }
    }
}
