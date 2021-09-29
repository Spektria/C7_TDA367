package C7.Model.Tools.Pattern;

/**
 * A factory for creating {@link IPattern} instances.
 *
 * @author Hugo Ekstrand
 */
public class PatternFactory {

    /**
     * Creates a filled disk shaped pattern.
     * @return the pattern strategy.
     */
    public static IPattern createDiskPattern(){
        return new DiskPattern();
    }

    /**
     * Creates a line shaped pattern.
     * @return the pattern strategy.
     */
    public static IPattern createLinePattern(){
        return new LinePattern();
    }
}
