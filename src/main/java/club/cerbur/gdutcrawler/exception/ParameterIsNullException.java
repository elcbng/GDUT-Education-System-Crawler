package club.cerbur.gdutcrawler.exception;

/**
 * @author cerbur
 */
public class ParameterIsNullException extends Exception {
    public ParameterIsNullException(String paramName) {
        super("Required parameters are empty." + "The parameter is " + paramName);
    }
}
