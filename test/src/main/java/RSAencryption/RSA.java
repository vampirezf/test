package RSAencryption;

import org.apache.commons.codec.binary.Hex;

import java.security.interfaces.RSAPublicKey;

/**
 * @author zf
 * @date
 */
public class RSA {
    public static void main(String[] args) {

        // modulus及exponent生成
        RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
        String modulus = new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
        String exponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));

        //js加密后
        String srcPassword = "";
        //RSA解密
        String rsa = RSAUtils.decryptStringByJs(srcPassword);
    }
}
