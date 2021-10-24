package C7;

/***
 * C7.C7Paint simply initializes the application.
 */
public class C7Paint {

    // The reason for this being the entry point instead of PaintApplications
    // is because the Shade maven dependency cannot create a jar if the entry point extends
    // Application. I.E. this class serves no other purpose other than creating a clean entry point for
    // the maven-shade-plugin.
    public static void main(String[] args) {
        PaintApplication.main(args);
    }
}
