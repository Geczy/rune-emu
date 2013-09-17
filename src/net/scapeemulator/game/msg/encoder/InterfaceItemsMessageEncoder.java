package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceItemsMessage;
import net.scapeemulator.game.net.game.DataTransformation;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrame.Type;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class InterfaceItemsMessageEncoder extends MessageEncoder<InterfaceItemsMessage> {

	public InterfaceItemsMessageEncoder() {
		super(InterfaceItemsMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, InterfaceItemsMessage message) {
		Item[] items = message.getItems();

		GameFrameBuilder builder = new GameFrameBuilder(alloc, 105, Type.VARIABLE_SHORT);
		builder.put(DataType.INT, (message.getId() << 16) | message.getSlot());
		builder.put(DataType.SHORT, message.getType());
		builder.put(DataType.SHORT, items.length);

		for (Item item : items) {
			if (item == null) {
				builder.put(DataType.BYTE, DataTransformation.SUBTRACT, 0);
				builder.put(DataType.SHORT, 0);
			} else {
				int amount = item.getAmount();
				if (amount >= 255) {
					builder.put(DataType.BYTE, DataTransformation.SUBTRACT, 255);
					builder.put(DataType.INT, amount);
				} else {
					builder.put(DataType.BYTE, DataTransformation.SUBTRACT, amount);
				}
				builder.put(DataType.SHORT, item.getId() + 1);
			}
		}

		return builder.toGameFrame();
	}

}
