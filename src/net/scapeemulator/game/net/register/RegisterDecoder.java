package net.scapeemulator.game.net.register;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.GregorianCalendar;

import net.scapeemulator.game.net.handshake.HandshakeMessage;
import net.scapeemulator.util.Base37Utils;
import net.scapeemulator.util.ByteBufUtils;
import net.scapeemulator.util.crypto.RsaKeySet;

public final class RegisterDecoder extends ByteToMessageDecoder {

	public enum State {
		READ_SIZE,
		READ_PAYLOAD;
	}

	private final int service;
	private int size;
	private State state = State.READ_SIZE;

	public RegisterDecoder(int service) {
		this.service = service;
	}

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf buf, MessageBuf<Object> out) throws Exception {
		if (service == HandshakeMessage.SERVICE_REGISTER_PERSONAL_DETAILS) {
			if (buf.readableBytes() < 6)
				return;

			int day = buf.readUnsignedByte();
			int month = buf.readUnsignedByte();
			int year = buf.readUnsignedShort();
			int country = buf.readUnsignedShort();
			out.add(new RegisterPersonalDetailsRequest(new GregorianCalendar(year, month, day), country));
		} else if (service == HandshakeMessage.SERVICE_REGISTER_USERNAME) {
			if (buf.readableBytes() < 8)
				return;

			String username = Base37Utils.decodeBase37(buf.readLong());
			out.add(new RegisterUsernameRequest(username));
		} else if (service == HandshakeMessage.SERVICE_REGISTER_COMMIT) {
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
				int affiliate = secureBuffer.readUnsignedShort();
				int day = secureBuffer.readUnsignedByte();
				int month = secureBuffer.readUnsignedByte();
				secureBuffer.readInt();
				int year = secureBuffer.readUnsignedShort();
				int country = secureBuffer.readUnsignedShort();
				secureBuffer.readInt();

				out.add(new RegisterCommitRequest(version, username, password, new GregorianCalendar(year, month, day), country));
			}
		}
	}

}
