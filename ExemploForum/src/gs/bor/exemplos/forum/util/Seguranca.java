package gs.bor.exemplos.forum.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// implementa hashing e checagem de senhas
// assista o vídeo do tom scott pra entender

public class Seguranca {
  
  private static final Random rnd = new SecureRandom();
  private static final int SALT_LEN = 256, ITERATIONS = 10000, KEY_LENGTH = 256;
  
  // salt seguro e aleatório
  public static byte[] nextSalt() {
    byte[] salt = new byte[SALT_LEN];
    rnd.nextBytes(salt);
    return salt;
  }
  
  // começando com uma senha e um salt, produzir uma hash
  public static byte[] hashear(char[] password, byte[] salt) {
    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
    Arrays.fill(password, Character.MIN_VALUE);
    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new AssertionError("Erro ao hashear: " + e.getMessage(), e);
    } finally {
      spec.clearPassword();
    }
  }
  
  // vê se uma senha bate com um certo hash e salt
  public static boolean senhaBate(char[] password, byte[] salt, byte[] hash) {
    byte[] pwdHash = hashear(password, salt);
    Arrays.fill(password, Character.MIN_VALUE);
    if (pwdHash.length != hash.length) return false;
    for (int i = 0; i < pwdHash.length; i++) {
      if (pwdHash[i] != hash[i]) return false;
    }
    return true;
  }
}
