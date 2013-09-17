package net.scapeemulator.game.net.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class UpdateDecoder extends ByteToMessageDecoder {

	private enum State {
		READ_VERSION, READ_REQUEST
	}

	private State state = State.READ_VERSION;

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf buf, MessageBuf<Object> out) throws Exception {
		if (buf.readableBytes() < 4)
			return;

		if (state == State.READ_VERSION) {
			state = State.READ_REQUEST;
			out.add(new UpdateVersionMessage(buf.readInt()));
		} else {
			int opcode = buf.readUnsignedByte();
			if (opcode == 0 || opcode == 1) {
				int type = buf.readUnsignedByte();
				int file = buf.readUnsignedShort();
				out.add(new FileRequest(opcode == 1, type, file));
			} else if (opcode == 4) {
				int key = buf.readUnsignedByte();
				buf.readerIndex(buf.readerIndex() + 2);
				out.add(new UpdateEncryptionMessage(key));
			} else {
				/*
				 * other unused opcodes:
				 *
				 * 2 - logged in
				 * 3 - logged out
				 * 6 - connection initiated
				 * 7 - connection done
				 */
				buf.readerIndex(buf.readerIndex() + 3);
				return; /* TODO print a warning or add support for reading these opcodes? */
			}
		}
	}

}
