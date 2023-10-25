package step.learning.services.kdf;

/**
 * Key Derivation Function service (by RFC 2898)
 * Computes derived key for password and salt
 */
public interface KdfService
{
    String getDerivedKey(String password, String salt);

}
