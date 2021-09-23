package C7.Model.Tools.Pattern;

/**
 * A factory for creating {@link IPatternStrategy} instances.
 *
 * @author Hugo Ekstrand
 */
public class PatternStrategyFactory {

    /**
     * Creates a filled disk shaped pattern strategy.
     * @return the pattern strategy.
     */
    public static IPatternStrategy createDiskPattern(){
        return new DiskPattern();
    }

    /**
     * Creates a line shaped pattern strategy.
     * @return the pattern strategy.
     */
    public static IPatternStrategy createLinePattern(){
        return new LinePattern();
    }
}
