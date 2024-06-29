package org.chobit.spider.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import static org.chobit.commons.constans.Symbol.NEW_LINE;
import static org.chobit.commons.constans.Symbol.SPACE;


public class TxtReader {


    public static void main(String[] args) {
        String inPath = "/zhy/ab.txt";
        String outPath = "/zhy/abc.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inPath));
             FileWriter writer = new FileWriter(outPath)) {
            String line = null;
            while (null != (line = reader.readLine())) {
                StringBuilder builder = new StringBuilder();
                if (line.startsWith("chapter")) {
                    String[] arr = line.split(SPACE);
                    builder.append("第").append(arr[1]).append("节");
                    for (int i = 2; i < arr.length; i++) {
                        builder.append(SPACE).append(arr[i]);
                    }
                    builder.append(NEW_LINE);
                } else {
                    builder.append(line);
                }
                builder.append(NEW_LINE);
                writer.write(builder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
