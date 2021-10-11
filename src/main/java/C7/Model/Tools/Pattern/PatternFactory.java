package C7.Model.Tools.Pattern;

/**
 * A factory for creating {@link IPattern} instances.
 *
 * @author Hugo Ekstrand
 */
public final class PatternFactory {

    /**
     * Creates a filled disk shaped pattern.
     * @return the pattern.
     */
    public static IPattern createDiskPattern(){
        return new DiskPattern();
    }

    /**
     * Creates a line shaped pattern.
     * @return the pattern.
     */
    public static IPattern createLinePattern(){
        return new LinePattern();
    }
}
