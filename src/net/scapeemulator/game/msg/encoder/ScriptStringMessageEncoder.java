package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.ScriptStringMessage;
import net.scapeemulator.game.net.game.*;

import java.io.IOException;

public final class ScriptStringMessageEncoder extends MessageEncoder<ScriptStringMessage> {

	public ScriptStringMessageEncoder() {
		super(ScriptStringMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, ScriptStringMessage message) throws IOException {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 123);
		builder.put(DataType.SHORT, DataOrder.LITTLE, message.getId());
		builder.put(DataType.SHORT, DataTransformation.ADD, 0);
		builder.putString(message.getValue());
		return builder.toGameFrame();
/* TODO what is the difference between these two packets? they seem identical
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 48);
		builder.put(DataType.SHORT, 0);
		builder.putString(message.getValue());
		builder.put(DataType.SHORT, DataOrder.LITTLE, message.getId());
		return builder.toGameFrame();
*/
	}

}
