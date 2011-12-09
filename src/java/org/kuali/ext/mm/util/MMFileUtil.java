/**
 * 
 */
package org.kuali.ext.mm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author harsha07
 */
public final class MMFileUtil {
    public static void streamOut(InputStream is, OutputStream output) throws IOException {
        BufferedInputStream bfis = null;
        BufferedOutputStream bfos = null;
        try {
            bfis = new BufferedInputStream(is);
            bfos = new BufferedOutputStream(output);
            byte[] b = new byte[65536];
            int len = 0;
            while ((len = bfis.read(b)) != -1) {
                bfos.write(b, 0, len);
            }
        }
        finally {
            if (bfis != null) {
                bfis.close();
            }
            if (bfos != null) {
                bfos.flush();
                bfos.close();
            }
        }
    }
}
