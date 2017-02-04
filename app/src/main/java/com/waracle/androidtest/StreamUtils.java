package com.waracle.androidtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Riad on 20/05/2015.
 */
class StreamUtils {
    private static final String TAG = StreamUtils.class.getSimpleName();

    //// Can you see what's wrong with this???
    //static byte[] readUnknownFully(InputStream stream) throws IOException {
    //    // Read in stream of bytes
    //    ArrayList<Byte> data = new ArrayList<>();
    //    while (true) {
    //        int result = stream.read();
    //        if (result == -1) {
    //            break;
    //        }
    //        data.add((byte) result);
    //    }

    //    // Convert ArrayList<Byte> to byte[]
    //    byte[] bytes = new byte[data.size()];
    //    for (int i = 0; i < bytes.length; i++) {
    //        bytes[i] = data.get(i);
    //    }

    //    // Return the raw byte array.
    //    return bytes;
    //}

    static String readJson(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }

    static Bitmap readBitmap(InputStream stream) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        return bitmap;
    }

    static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
