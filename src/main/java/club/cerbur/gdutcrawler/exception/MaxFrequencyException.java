package club.cerbur.gdutcrawler.exception;

/**
 * @author cerbur
 */
public class MaxFrequencyException extends Exception {
    public MaxFrequencyException() {
        super("Maximum number of times exceeded");
    }
}
