package net.scapeemulator.flooder.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundByteHandlerAdapter;
import net.burtleburtle.bob.rand.IsaacRandom;
import net.scapeemulator.util.Base37Utils;
import net.scapeemulator.util.ByteBufUtils;
import net.scapeemulator.util.crypto.RsaKeySet;
import net.scapeemulator.util.crypto.StreamCipher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.SecureRandom;

public final class FlooderChannelHandler extends ChannelInboundByteHandlerAdapter {

	private enum State {
		READ_SERVER_SESSION_KEY,
		READ_LOGIN_STATUS,
		READ_LOGIN_PAYLOAD,
		READ_GAME_OPCODE
	}

	private static final SecureRandom random = new SecureRandom();

	private State state = State.READ_SERVER_SESSION_KEY;
	private final int[] crc;
	private final long clientSessionKey = random.nextLong();
	private String username, password;
	private long serverSessionKey, encodedUsername;
	private StreamCipher inCipher, outCipher;

	public FlooderChannelHandler(int[] crc) {
		this.crc = crc;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		Channel channel = ctx.channel();

		username = "bot" + ((InetSocketAddress) channel.localAddress()).getPort();
		password = "password";
		encodedUsername = Base37Utils.encodeBase37(username);

		ByteBuf packet = ctx.alloc().buffer();
		packet.writeByte(14);
		packet.writeByte((byte) ((encodedUsername >> 16) & 31));
		channel.write(packet);
	}

	@Override
	public void inboundBufferUpdated(ChannelHandlerContext ctx, ByteBuf buf) throws IOException {
		Channel channel = ctx.channel();

		if (state == State.READ_SERVER_SESSION_KEY) {
			if (buf.readableBytes() >= 9) {
				if (buf.readUnsignedByte() != 0)
					throw new IOException("expecting EXCHANGE_KEYS opcode");

				serverSessionKey = buf.readLong();

				ByteBuf payload = ctx.alloc().buffer();
				payload.writeInt(530);

				payload.writeByte(0);
				payload.writeByte(0);
				payload.writeByte(0);

				payload.writeByte(0);
				payload.writeShort(765);
				payload.writeShort(503);

				payload.writeByte(0);

				for (int i = 0; i < 24; i++)
					payload.writeByte(0);

				ByteBufUtils.writeString(payload, "kKmok3kJqOeN6D3mDdihco3oPeYN2KFy6W5--vZUbNA");

				payload.writeInt(0);
				payload.writeInt(0);
				payload.writeShort(0);

				for (int i = 0; i < 28; i++) {
					payload.writeInt(crc[i]);
				}

				ByteBuf secure = ctx.alloc().buffer();
				secure.writeByte(10);
				secure.writeLong(clientSessionKey);
				secure.writeLong(serverSessionKey);
				secure.writeLong(encodedUsername);
				ByteBufUtils.writeString(secure, password);

				secure = ByteBufUtils.rsa(secure, RsaKeySet.MODULUS, RsaKeySet.PUBLIC_KEY);

				payload.writeByte(secure.readableBytes());
				payload.writeBytes(secure);

				ByteBuf packet = ctx.alloc().buffer();
				packet.writeByte(18);
				packet.writeShort(payload.readableBytes());
				packet.writeBytes(payload);

				channel.write(packet);

				int[] seed = new int[4];
				seed[0] = (int) (clientSessionKey >> 32);
				seed[1] = (int) clientSessionKey;
				seed[2] = (int) (serverSessionKey >> 32);
				seed[3] = (int) serverSessionKey;
				outCipher = new IsaacRandom(seed);
				for (int i = 0; i < seed.length; i++)
					seed[i] += 50;
				inCipher = new IsaacRandom(seed);

				state = State.READ_LOGIN_STATUS;
			}
		}

		if (state == State.READ_LOGIN_STATUS) {
			if (buf.isReadable()) {
				int status = buf.readUnsignedByte();
				if (status != 2)
					throw new IOException("expecting OK login response");

				state = State.READ_LOGIN_PAYLOAD;
			}
		}

		if (state == State.READ_LOGIN_PAYLOAD) {
			if (buf.readableBytes() >= 11) {
				buf.readerIndex(buf.readerIndex() + 11);

				state = State.READ_GAME_OPCODE;
			}
		}

		if (state == State.READ_GAME_OPCODE) {

		}
	}

}
