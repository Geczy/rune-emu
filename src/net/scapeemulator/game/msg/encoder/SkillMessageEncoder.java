package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.SkillMessage;
import net.scapeemulator.game.net.game.DataOrder;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class SkillMessageEncoder extends MessageEncoder<SkillMessage> {

	public SkillMessageEncoder() {
		super(SkillMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, SkillMessage message) {
		GameFrameBuilder builder = new GameFrameBuilder(alloc, 38);
		builder.put(DataType.BYTE, DataTransformation.ADD, message.getLevel());
		builder.put(DataType.INT, DataOrder.MIDDLE, message.getExperience());
		builder.put(DataType.BYTE, message.getSkill());
		return builder.toGameFrame();
	}

}
