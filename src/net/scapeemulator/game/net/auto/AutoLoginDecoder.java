package net.scapeemulator.game.net.auto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.scapeemulator.util.Base37Utils;
import net.scapeemulator.util.ByteBufUtils;
import net.scapeemulator.util.crypto.RsaKeySet;

import java.io.IOException;

public final class AutoLoginDecoder extends ByteToMessageDecoder {

	public enum State {
		READ_SIZE, READ_PAYLOAD;
	}

	private State state = State.READ_SIZE;
	private int size;

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf buf, MessageBuf<Object> out) throws IOException {
		if (state == State.READ_SIZE) {
			if (!buf.isReadable())
				return;

			state = State.READ_PAYLOAD;
			size = buf.readUnsignedByte();
		}

		if (state == State.READ_PAYLOAD) {
			if (buf.readableBytes() < size)
				return;

			int encryptedSize = buf.readUnsignedByte();
			if (encryptedSize != size - 1)
				throw new IOException("Encrypted size mismatch.");

			ByteBuf secureBuffer = ByteBufUtils.rsa(buf.readBytes(encryptedSize), RsaKeySet.MODULUS, RsaKeySet.PRIVATE_KEY);
			int encryptedType = secureBuffer.readUnsignedByte();
			if (encryptedType != 10)
				throw new IOException("Invalid encrypted block type.");

			secureBuffer.readUnsignedShort();
			int version = secureBuffer.readUnsignedShort();
			String username = Base37Utils.decodeBase37(secureBuffer.readLong());
			secureBuffer.readInt();
			String password = ByteBufUtils.readString(secureBuffer);
			secureBuffer.readInt();

			out.add(new AutoLoginRequest(version, username, password));
		}
	}

}
