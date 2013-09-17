package net.scapeemulator.game.msg.encoder;

import io.netty.buffer.ByteBufAllocator;
import net.scapeemulator.game.model.player.Item;
import net.scapeemulator.game.model.player.SlottedItem;
import net.scapeemulator.game.msg.MessageEncoder;
import net.scapeemulator.game.msg.impl.inter.InterfaceSlottedItemsMessage;
import net.scapeemulator.game.net.game.DataType;
import net.scapeemulator.game.net.game.GameFrame;
import net.scapeemulator.game.net.game.GameFrame.Type;
import net.scapeemulator.game.net.game.GameFrameBuilder;

public final class InterfaceSlottedItemsMessageEncoder extends MessageEncoder<InterfaceSlottedItemsMessage> {

	public InterfaceSlottedItemsMessageEncoder() {
		super(InterfaceSlottedItemsMessage.class);
	}

	@Override
	public GameFrame encode(ByteBufAllocator alloc, InterfaceSlottedItemsMessage message) {
		SlottedItem[] items = message.getItems();

		GameFrameBuilder builder = new GameFrameBuilder(alloc, 22, Type.VARIABLE_SHORT);
		builder.put(DataType.INT, (message.getId() << 16) | message.getSlot());
		builder.put(DataType.SHORT, message.getType());

		for (SlottedItem slottedItem : items) {
			int slot = slottedItem.getSlot();
			builder.putSmart(slot);

			Item item = slottedItem.getItem();
			if (item == null) {
				builder.put(DataType.SHORT, 0);
			} else {
				int amount = item.getAmount();
				builder.put(DataType.SHORT, item.getId() + 1);
				if (amount >= 255) {
					builder.put(DataType.BYTE, 255);
					builder.put(DataType.INT, amount);
				} else {
					builder.put(DataType.BYTE, amount);
				}
			}
		}

		return builder.toGameFrame();
	}

}
