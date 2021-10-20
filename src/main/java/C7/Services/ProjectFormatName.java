package C7.Services;

import javax.imageio.ImageIO;
import java.util.Arrays;

/**
 * Project format names.
 * @author Isak Gustafsson
 */
public enum ProjectFormatName {
    //All accepted project formats
    //Source: https://unicode.org/emoji/charts/full-emoji-list.html
    RasterProject("c7\uD83D\uDDBC");

    private final String format;
    ProjectFormatName(String format){
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
