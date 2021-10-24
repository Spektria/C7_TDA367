package C7.Services;

import javax.imageio.ImageIO;
import java.util.Arrays;

/**
 * Image format names.
 * @author Hugo Ekstrand
 */
public enum ImageFormatName {
    PNG("png"),
    JPG("jpg"),
    BMP("bmp"),
    GIF("gif"),
    TIF("tif"),
    ;

    private final String format;
    ImageFormatName(String format){
        if(!Arrays.asList(ImageIO.getWriterFileSuffixes()).contains(format))
            throw new IllegalArgumentException("Image suffix + " + format + " unsupported");
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}


