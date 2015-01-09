package com.suricate.card;

import java.util.Arrays;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.suricate.utils.ApplicationValues;

/**
 * Application protocol that emulates a Suricate Badge
 */
public class SuricateService extends HostApduService {
    private static final String TAG = "SuricateService";

    private static final String DUMMY_BADGE = "#"+ApplicationValues.getInstance()._selectedBadge.getCode()+"#";

    private static final String SURICATE_SERVICE_AID = "F00004121992";

    private static final byte[] OK_SW = HexStringToByteArray("");
    private static final byte[] UNKNOWN_SW = HexStringToByteArray("6000");
    private static final byte[] SELECT_APDU = HexStringToByteArray("00A40400"
            + "06" + SURICATE_SERVICE_AID);

    @Override
    public void onDeactivated(int reason) {}

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.i(TAG, "Received APDU: " + ByteArrayToHexString(commandApdu));
        Log.i(TAG, ByteArrayToHexString(SELECT_APDU));

        if (Arrays.equals(SELECT_APDU, commandApdu)) {
            byte[] dummyBadgeBytes = DUMMY_BADGE.getBytes();
            Log.i(TAG, "Sending badge: " + dummyBadgeBytes);
            return sendRAPDU(dummyBadgeBytes, OK_SW);
        } else {
            return UNKNOWN_SW;
        }
    }

    /**
     * Utility method to convert a byte array to a hexadecimal string.
     * @param bytes Bytes to convert
     * @return String, containing hexadecimal representation.
     */
    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A',
                'B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int carac;

        for (int j = 0; j < bytes.length; j++) {
            carac = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[carac >>> 4];
            hexChars[j * 2 + 1] = hexArray[carac & 0x0F];
        }

        return new String(hexChars);
    }

    /**
     * Utility method to convert a hexadecimal string to a byte string.
     * @param s String containing hexadecimal characters to convert
     * @return Byte array generated from input
     * @throws java.lang.IllegalArgumentException if input length is incorrect
     */
    public static byte[] HexStringToByteArray(String command)
            throws IllegalArgumentException {
        int len = command.length();

        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number"
                    + "of characters");
        }

        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(command.charAt(i), 16) << 4)
                    + Character.digit(command.charAt(i+1), 16));
        }

        return data;
    }

    /**
     * Utility method to send APDU.
     * @param first First array
     * @param rest Any remaining arrays
     * @return Concatenated copy of input arrays
     */
    public static byte[] sendRAPDU(byte[] command, byte[]... statusWord) {
        int totalLength = command.length;

        for (byte[] array : statusWord) {
            totalLength += array.length;
        }

        byte[] result = Arrays.copyOf(command, totalLength);

        int offset = command.length;

        for (byte[] array : statusWord) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
}
