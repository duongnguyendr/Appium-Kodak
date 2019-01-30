package com.cinatic;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DeviceTopicGenerator {
	private static byte[] encrypt(byte[] key, byte[] plain) throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher        cipher   = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(plain);
        return encrypted;
    }

    private static char charToAlphabet(char val)
    {
        int result = 0;
        if (val >= 128)
        {
            result = (val % 26 + 'A');
        }
        else
        {
            result = (val % 26 + 'a');
        }
        return (char) (result & 0xFF);
    }

    private static String getDeviceTopicCode(String uuid) throws Exception
    {
        String        strKey          = uuid.substring(10, 26);
        String        strPlain        = "aaaaaaaaaaaaaaaa";
        byte[]        key             = strKey.getBytes();
        byte[]        plain           = strPlain.getBytes();
        byte[]        cipher          = encrypt(key, plain);
        StringBuilder devTopicBuilder = new StringBuilder();
        devTopicBuilder.append(uuid.substring(6, 18));
        for (int i = 0; i < 8; i++)
        {
            devTopicBuilder.append(charToAlphabet((char) (cipher[i] & 0xFF)));
        }
        return devTopicBuilder.toString();
    }

    public static String generateDevicePublishTopic(String uuid) throws Exception
    {
        String deviceTopicCode = getDeviceTopicCode(uuid);
        return "dev/" + deviceTopicCode + "/sub";
    }
    public static String generateDeviceBroadcastReceiverTopic(String uuid) throws Exception {
        String deviceTopicCode = getDeviceTopicCode(uuid);
        return "dev/" + deviceTopicCode + "/pub";
    }
}
