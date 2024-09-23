package sign_up_And_authentication;

import de.taimos.totp.TOTP;
import java.security.SecureRandom;
import java.util.Base64;

public class TwoFA {
    // Generate a secret key for 2FA
    public static String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20]; // A 160-bit key is standard for TOTP
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    // Generate a QR Code URL to be scanned by Google Authenticator
    public static String getQRBarcodeURL(String email, String secret) {
        String issuer = "MyApp";
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, email, secret, issuer
        );
    }

    // Verify the TOTP code
    public static boolean verifyCode(String secret, String code) {
        String generatedCode = TOTP.getOTP(secret);
        return generatedCode.equals(code);
    }
}
