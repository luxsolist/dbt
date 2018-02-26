package luxk.jdbt;

import java.io.IOException;

public abstract class UtilByteArray {
	
	public static boolean readBoolean(byte[] bytes, int offset)
			throws IOException {
		if(offset >= bytes.length) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)",
							offset, bytes.length));
		}
		return (bytes[offset] != 0);
	}
	
	public static byte readByte(byte[] bytes, int offset) throws IOException {
		if(offset >= bytes.length) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)",
							offset, bytes.length));
		}
		return bytes[offset];
	}
	
	public static int readUnsignedByte(byte[] bytes, int offset)
			throws IOException {
		if(offset >= bytes.length) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)",
							offset, bytes.length));
		}
		return (bytes[offset] & 0xff);
	}
	
	public static short readShort(byte[] bytes, int offset)
			throws IOException {
		if(offset >= bytes.length - 1) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-1",
							offset, bytes.length));
		}
		int val = ((bytes[offset]&0xff) << 8) | (bytes[offset+1]&0xff);
		return (short)val;
	}

	public static int readUnsignedShort(byte[] bytes, int offset)
			throws IOException {
		if(offset >= bytes.length - 1) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-1",
							offset, bytes.length));
		}
		return ((bytes[offset  ] & 0xff) << 8) |
				(bytes[offset+1] & 0xff);
	}
	
	public static char readChar(byte[] bytes, int offset) throws IOException {
		if(offset >= bytes.length - 1) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-1",
							offset, bytes.length));
		}
		int val = ((bytes[offset  ]&0xff) << 8) |
				   (bytes[offset+1]&0xff);
		return (char)val;
	}
	
	public static int readInt(byte[] bytes, int offset) throws IOException {
		if(offset >= bytes.length - 3) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-3",
							offset, bytes.length));
		}
		return ((bytes[offset  ] & 0xff) << 24 |
				(bytes[offset+1] & 0xff) << 16 |
				(bytes[offset+2] & 0xff) <<  8 |
				(bytes[offset+3] & 0xff));
	}
	
	public static long readLong(byte[] bytes, int offset) throws IOException {
		if(offset >= bytes.length - 7) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-7",
							offset, bytes.length));
		}
		return (((long)(bytes[offset  ] & 0xff) << 56) |
				((long)(bytes[offset+1] & 0xff) << 48) |
				((long)(bytes[offset+2] & 0xff) << 40) |
				((long)(bytes[offset+2] & 0xff) << 32) |
				((long)(bytes[offset+2] & 0xff) << 24) |
				((long)(bytes[offset+2] & 0xff) << 16) |
				((long)(bytes[offset+2] & 0xff) <<  8) |
				((long)(bytes[offset+3] & 0xff)      ));
	}

	public static int writeBoolean(boolean val, byte[] target, int offset)
			throws IOException {
		if(offset >= target.length) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)",
							offset, target.length));
		}
		target[offset] = val ? (byte)(~0) : (byte)(0);
		return 1;
	}
	
	public static int writeByte(byte val, byte[] target, int offset)
			throws IOException {
		if(offset >= target.length) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)",
							offset, target.length));
		}
		target[offset] = val;
		return 1;
	}
	
	public static int writeUnsignedByte(int val, byte[] target, int offset)
			throws IOException {
		if(offset >= target.length) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)",
							offset, target.length));
		}
		target[offset] = (byte)val;
		return 1;
	}
	
	public static int writeShort(short val, byte[] target, int offset)
			throws IOException {
		if(offset >= target.length - 1) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-1",
							offset, target.length));
		}
		target[offset  ] = (byte)(val >> 8);
		target[offset+1] = (byte)val;
		return 2;
	}

	public static int writeUnsignedShort(int val, byte[] target, int offset)
			throws IOException {
		if(offset >= target.length - 1) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-1",
							offset, target.length));
		}
		target[offset  ] = (byte)(val >> 8);
		target[offset+1] = (byte)val;
		return 2;
	}
	
	public static int writeChar(char val, byte[] target, int offset)
			throws IOException {
		if(offset >= target.length - 1) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-1",
							offset, target.length));
		}
		target[offset  ] = (byte)(val >> 8);
		target[offset+1] = (byte)val;
		return 2;
	}
	
	public static int writeInt(int val, byte[] target, int offset)
			throws IOException {
		if(offset >= target.length - 3) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-3",
							offset, target.length));
		}
		target[offset  ] = (byte)(val >> 24);
		target[offset+1] = (byte)(val >> 16);
		target[offset+2] = (byte)(val >>  8);
		target[offset+3] = (byte)val;
		return 4;
	}
	
	public static int writeLong(long val, byte[] target, int offset)
			throws IOException {
		if(offset >= target.length - 7) {
			throw new IOException(
					String.format("offset(%d) must be less then bytelen(%d)-7",
							offset, target.length));
		}
		target[offset  ] = (byte)(val >> 56);
		target[offset+1] = (byte)(val >> 48);
		target[offset+2] = (byte)(val >> 40);
		target[offset+3] = (byte)(val >> 32);
		target[offset+4] = (byte)(val >> 24);
		target[offset+5] = (byte)(val >> 16);
		target[offset+6] = (byte)(val >>  8);
		target[offset+7] = (byte)val;
		return 8;
	}
}
