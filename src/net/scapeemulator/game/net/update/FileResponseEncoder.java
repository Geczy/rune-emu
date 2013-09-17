package net.scapeemulator.game.net.update;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

public final class FileResponseEncoder extends MessageToByteEncoder<FileResponse> { /* TODO: merge with other update encoder? */

	@Override
	public void encode(ChannelHandlerContext ctx, FileResponse response, ByteBuf buf) throws IOException {
		ByteBuf container = response.getContainer();
		int type = response.getType();
		int file = response.getFile();

		buf.writeByte(type);
		buf.writeShort(file);

		int compression = container.readUnsignedByte();
		if (!response.isPriority())
			compression |= 0x80;

		buf.writeByte(compression);

		int bytes = container.readableBytes();
		if (bytes > 508)
			bytes = 508;

		buf.writeBytes(container.readBytes(bytes));

		for (;;) {
			bytes = container.readableBytes();
			if (bytes == 0)
				break;
			else if (bytes > 511)
				bytes = 511;

			buf.writeByte(0xFF);
			buf.writeBytes(container.readBytes(bytes));
		}
	}

}
