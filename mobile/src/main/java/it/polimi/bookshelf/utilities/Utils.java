package it.polimi.bookshelf.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Utils {

    /**
     * Returns the text (string) contained in the Input Stream passed as parameter
     *
     * @param inputStream
     * @return string
     */
    public static String fromInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String readLine = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((readLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(readLine);
            }
            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
