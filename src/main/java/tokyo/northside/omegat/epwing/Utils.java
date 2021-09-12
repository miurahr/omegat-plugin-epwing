package tokyo.northside.omegat.epwing;

import io.github.eb4j.EBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public final class Utils {

    static final Logger LOG = LoggerFactory.getLogger(OmegatEpwingDictionary.class.getName());
    static final int BMP_PREAMBLE_LENGTH = 62;

    private Utils() {
    }

    protected static byte[] bitmap2BMP(final byte[] data, final int width, final int height) {
        final byte[] bmpPreamble = new byte[] {
                // Type
                'B', 'M',
                // File size (set at run time)
                0x00, 0x00, 0x00, 0x00,
                // Reserved
                0x00, 0x00, 0x00, 0x00,
                // offset of bitmap bits part
                0x3e, 0x00, 0x00, 0x00,
                // size of bitmap info part
                0x28, 0x00, 0x00, 0x00,
                // width (set at run time)
                0x00, 0x00, 0x00, 0x00,
                // height (set at run time)
                0x00, 0x00, 0x00, 0x00,
                // planes
                0x01, 0x00,
                // bits per pixsels
                0x01, 0x00,
                // compression mode
                0x00, 0x00, 0x00, 0x00,
                // size of bitmap bits part (set at run time)
                0x00, 0x00, 0x00, 0x00,
                // X pixels per meter
                0x6d, 0x0b, 0x00, 0x00,
                // Y pixels per meter
                0x6d, 0x0b, 0x00, 0x00,
                // Colors
                0x02, 0x00, 0x00, 0x00,
                // Important colors
                0x02, 0x00, 0x00, 0x00,
                // RGB quad of color 0   RGB quad of color 1
                (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, 0x00, 0x00, 0x00, 0x00
        };

        int linePad;
        if (width % 32 == 0) {
            linePad = 0;
        } else if (width % 32 <= 8) {
            linePad = 3;
        } else if (width % 32 <= 16) {
            linePad = 2;
        } else if (width % 32 <= 24) {
            linePad = 1;
        } else {
            linePad = 0;
        }

        int dataSize = height * (width / 2 + linePad);
        int fileSize = dataSize + BMP_PREAMBLE_LENGTH;

        byte[] bmp = new byte[fileSize];
        System.arraycopy(bmpPreamble, 0, bmp, 0, BMP_PREAMBLE_LENGTH);
        //
        bmp[2] = (byte) (fileSize & 0xff);
        bmp[3] = (byte) ((fileSize >> 8) & 0xff);
        bmp[4] = (byte) ((fileSize >> 16) & 0xff);
        bmp[5] = (byte) ((fileSize >> 24) & 0xff);

        bmp[18] = (byte) (width & 0xff);
        bmp[19] = (byte) ((width >> 8) & 0xff);
        bmp[20] = (byte) ((width >> 16) & 0xff);
        bmp[21] = (byte) ((width >> 24) & 0xff);

        bmp[22] = (byte) (height & 0xff);
        bmp[23] = (byte) ((height >> 8) & 0xff);
        bmp[24] = (byte) ((height >> 16) & 0xff);
        bmp[25] = (byte) ((height >> 24) & 0xff);

        bmp[34] = (byte) (dataSize & 0xff);
        bmp[35] = (byte) ((dataSize >> 8) & 0xff);
        bmp[36] = (byte) ((dataSize >> 16) & 0xff);
        bmp[37] = (byte) ((dataSize >> 24) & 0xff);

        int bitmapLineLength = (width + 7) / 8;

        int i = height - 1;
        int k = BMP_PREAMBLE_LENGTH;
        while (i >= 0) {
            System.arraycopy(data, bitmapLineLength * i, bmp, k, bitmapLineLength);
            i--;
            k += bitmapLineLength;
            for (int j = 0; j < linePad; j++, k++) {
                bmp[k]  = 0x00;
            }
        }
        return bmp;
    }

    /**
     * convert image data to base64.
     * @param format image format.
     * @param data image data
     * @return base64 string
     * @throws IOException when conversion failed
     */
    protected static String convertImage2Base64(final String format, final byte[] data) throws IOException {
        byte[] bytes;
        Base64.Encoder base64Encoder = Base64.getEncoder();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final BufferedImage res = ImageIO.read(new ByteArrayInputStream(data));
            ImageIO.write(res, format, baos);
            baos.flush();
            bytes = baos.toByteArray();
        }
        return base64Encoder.encodeToString(bytes);
    }
    /**
     * Convert eb_bitmap to PNG, and convert to Base64 String.
     *
     * @param data  eb_bitmap font data
     * @param width  image width
     * @param height image height
     * @return String Base64 encoded PNG data.
     * @throws IOException when the image is broken or caused error.
     */
    protected static String convertMonoGraphic2Base64(final byte[] data, final int width, final int height)
            throws IOException {
        return convertImage2Base64("png", bitmap2BMP(data, width, height));
    }

    /**
     * convert Zenkaku alphabet to Hankaku.
     *
     * convert (\uFF01 - \uFF5E) to (\u0021- \u007E) and \u3000 to \u0020
     *
     * @param text source text with zenkaku.
     * @return String converted
     */
    static String convertZen2Han(final String text) {
        StringBuilder result = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            int cp = text.codePointAt(i);
            if (0xFF00 < cp && cp < 0xFF5F) {
                result.append((char) (cp - 0xFEE0));
            } else if (cp == 0x3000) {
                result.append("\u0020");
            } else {
                result.appendCodePoint(cp);
            }
        }
        return result.toString();
    }

    static void logEBError(final EBException e) {
        switch (e.getErrorCode()) {
            case EBException.CANT_READ_DIR:
                LOG.warn("EPWING error: cannot read directory:" + e.getMessage());
                break;
            case EBException.DIR_NOT_FOUND:
                LOG.warn("EPWING error: cannot found directory:" + e.getMessage());
            default:
                LOG.warn("EPWING error: " + e.getMessage());
                break;
        }
    }
}
