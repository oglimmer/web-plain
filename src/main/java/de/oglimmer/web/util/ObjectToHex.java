package de.oglimmer.web.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ObjectToHex {

	public static String encode(Object obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try (ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(baos))) {
				oos.writeObject(obj);
				oos.close();
				byte[] bytes = baos.toByteArray();
				StringBuffer buff = new StringBuffer();
				for (int i = 0; i < bytes.length; i++) {
					String s = Integer.toHexString(Byte.toUnsignedInt(bytes[i]));
					if (s.length() < 2) {
						buff.append('0');
					}
					buff.append(s);
				}
				return buff.toString();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object decode(String str) {
		try {
			assert (str.length() % 2 == 0);
			byte[] byteBuff = new byte[str.length() / 2];
			for (int i = 0; i < byteBuff.length; i++) {
				int index = i * 2;
				int j = Integer.parseInt(str.substring(index, index + 2), 16);
				byteBuff[i] = (byte) j;
			}
			try (ObjectInputStream ois = new ObjectInputStream(
					new GZIPInputStream(new ByteArrayInputStream(byteBuff)))) {
				return ois.readObject();
			}
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
