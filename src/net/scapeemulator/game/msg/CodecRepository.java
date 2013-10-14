package net.scapeemulator.game.msg;

import java.util.HashMap;
import java.util.Map;

import net.scapeemulator.game.model.ExtendedOption;
import net.scapeemulator.game.msg.decoder.CameraMessageDecoder;
import net.scapeemulator.game.msg.decoder.ChatMessageDecoder;
import net.scapeemulator.game.msg.decoder.ClickMessageDecoder;
import net.scapeemulator.game.msg.decoder.CommandMessageDecoder;
import net.scapeemulator.game.msg.decoder.DisplayMessageDecoder;
import net.scapeemulator.game.msg.decoder.FlagsMessageDecoder;
import net.scapeemulator.game.msg.decoder.FocusMessageDecoder;
import net.scapeemulator.game.msg.decoder.IdleLogoutMessageDecoder;
import net.scapeemulator.game.msg.decoder.PingMessageDecoder;
import net.scapeemulator.game.msg.decoder.RegionChangedMessageDecoder;
import net.scapeemulator.game.msg.decoder.RemoveItemMessageDecoder;
import net.scapeemulator.game.msg.decoder.SceneRebuiltMessageDecoder;
import net.scapeemulator.game.msg.decoder.SequenceNumberMessageDecoder;
import net.scapeemulator.game.msg.decoder.WalkMessageDecoder;
import net.scapeemulator.game.msg.decoder.button.ButtonOptionMessageDecoder;
import net.scapeemulator.game.msg.decoder.button.OldButtonMessageDecoder;
import net.scapeemulator.game.msg.decoder.grounditem.GroundItemOptionThreeMessageDecoder;
import net.scapeemulator.game.msg.decoder.inter.InterfaceClosedMessageDecoder;
import net.scapeemulator.game.msg.decoder.inter.InterfaceInputMessageDecoder;
import net.scapeemulator.game.msg.decoder.item.ItemOnItemMessageDecoder;
import net.scapeemulator.game.msg.decoder.item.ItemOnObjectMessageDecoder;
import net.scapeemulator.game.msg.decoder.item.ItemOptionTwoMessageDecoder;
import net.scapeemulator.game.msg.decoder.item.SwapItemsMessageDecoder;
import net.scapeemulator.game.msg.decoder.npc.NPCOptionOneMessageDecoder;
import net.scapeemulator.game.msg.decoder.object.ObjectOptionOneMessageDecoder;
import net.scapeemulator.game.msg.decoder.object.ObjectOptionTwoMessageDecoder;
import net.scapeemulator.game.msg.decoder.player.PlayerOptionFourMessageDecoder;
import net.scapeemulator.game.msg.encoder.ConfigMessageEncoder;
import net.scapeemulator.game.msg.encoder.EnergyMessageEncoder;
import net.scapeemulator.game.msg.encoder.GroundItemCreateMessageEncoder;
import net.scapeemulator.game.msg.encoder.GroundItemRemoveMessageEncoder;
import net.scapeemulator.game.msg.encoder.GroundItemUpdateMessageEncoder;
import net.scapeemulator.game.msg.encoder.GroundObjectRemoveMessageEncoder;
import net.scapeemulator.game.msg.encoder.GroundObjectUpdateMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceAccessMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceAnimationMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceCloseMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceItemsMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceNPCHeadMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceOpenMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfacePlayerHeadMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceResetItemsMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceRootMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceSlottedItemsMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceTextMessageEncoder;
import net.scapeemulator.game.msg.encoder.InterfaceVisibleMessageEncoder;
import net.scapeemulator.game.msg.encoder.LogoutMessageEncoder;
import net.scapeemulator.game.msg.encoder.NpcUpdateMessageEncoder;
import net.scapeemulator.game.msg.encoder.PlacementCoordsMessageEncoder;
import net.scapeemulator.game.msg.encoder.PlayerOptionMessageEncoder;
import net.scapeemulator.game.msg.encoder.PlayerUpdateMessageEncoder;
import net.scapeemulator.game.msg.encoder.RegionChangeMessageEncoder;
import net.scapeemulator.game.msg.encoder.ResetMinimapFlagMessageEncoder;
import net.scapeemulator.game.msg.encoder.ScriptIntMessageEncoder;
import net.scapeemulator.game.msg.encoder.ScriptMessageEncoder;
import net.scapeemulator.game.msg.encoder.ScriptStringMessageEncoder;
import net.scapeemulator.game.msg.encoder.ServerMessageEncoder;
import net.scapeemulator.game.msg.encoder.SkillMessageEncoder;
import net.scapeemulator.game.util.LandscapeKeyTable;

public final class CodecRepository {

    private final MessageDecoder<?>[] inCodecs = new MessageDecoder<?>[256];
    private final Map<Class<?>, MessageEncoder<?>> outCodecs = new HashMap<>();

    public CodecRepository(LandscapeKeyTable table) {
        /* decoders */
        bind(new PingMessageDecoder());
        bind(new IdleLogoutMessageDecoder());
        bind(new OldButtonMessageDecoder());
        bind(new WalkMessageDecoder(39));
        bind(new WalkMessageDecoder(77));
        bind(new WalkMessageDecoder(215));
        bind(new ChatMessageDecoder());
        bind(new CommandMessageDecoder());
        bind(new SwapItemsMessageDecoder());
        bind(new ItemOptionTwoMessageDecoder());
        bind(new DisplayMessageDecoder());
        bind(new RemoveItemMessageDecoder());
        bind(new RegionChangedMessageDecoder());
        bind(new ClickMessageDecoder());
        bind(new FocusMessageDecoder());
        bind(new CameraMessageDecoder());
        bind(new FlagsMessageDecoder());
        bind(new SequenceNumberMessageDecoder());
        bind(new InterfaceClosedMessageDecoder());
        bind(new ObjectOptionOneMessageDecoder());
        bind(new ObjectOptionTwoMessageDecoder());
        bind(new SceneRebuiltMessageDecoder());
        bind(new GroundItemOptionThreeMessageDecoder());

        /* Bind all the item on target decoders */
        bind(new ItemOnItemMessageDecoder());
        bind(new ItemOnObjectMessageDecoder());

        /* Bind all the button option decoders */
        bind(new ButtonOptionMessageDecoder(ExtendedOption.ONE, 155));
        bind(new ButtonOptionMessageDecoder(ExtendedOption.TWO, 196));
        bind(new ButtonOptionMessageDecoder(ExtendedOption.THREE, 124));
        bind(new ButtonOptionMessageDecoder(ExtendedOption.FOUR, 199));
        bind(new ButtonOptionMessageDecoder(ExtendedOption.FIVE, 234));
        bind(new ButtonOptionMessageDecoder(ExtendedOption.SIX, 168));
        bind(new ButtonOptionMessageDecoder(ExtendedOption.SEVEN, 166));
        bind(new ButtonOptionMessageDecoder(ExtendedOption.EIGHT, 64));
        bind(new ButtonOptionMessageDecoder(ExtendedOption.NINE, 9));
        
        bind(new InterfaceInputMessageDecoder());
        
        bind(new PlayerOptionFourMessageDecoder());
        
        bind(new NPCOptionOneMessageDecoder());

        /* encoders */
        bind(new RegionChangeMessageEncoder(table));
        bind(new InterfaceRootMessageEncoder());
        bind(new InterfaceOpenMessageEncoder());
        bind(new InterfaceCloseMessageEncoder());
        bind(new InterfaceVisibleMessageEncoder());
        bind(new InterfaceTextMessageEncoder());
        bind(new ServerMessageEncoder());
        bind(new LogoutMessageEncoder());
        bind(new PlayerUpdateMessageEncoder());
        bind(new SkillMessageEncoder());
        bind(new EnergyMessageEncoder());
        bind(new InterfaceItemsMessageEncoder());
        bind(new InterfaceSlottedItemsMessageEncoder());
        bind(new InterfaceResetItemsMessageEncoder());
        bind(new ResetMinimapFlagMessageEncoder());
        bind(new ConfigMessageEncoder());
        bind(new ScriptMessageEncoder());
        bind(new NpcUpdateMessageEncoder());
        bind(new ScriptStringMessageEncoder());
        bind(new ScriptIntMessageEncoder());
        bind(new PlacementCoordsMessageEncoder());
        bind(new GroundItemCreateMessageEncoder());
        bind(new GroundItemUpdateMessageEncoder());
        bind(new GroundItemRemoveMessageEncoder());
        bind(new GroundObjectUpdateMessageEncoder());
        bind(new GroundObjectRemoveMessageEncoder());
        bind(new PlayerOptionMessageEncoder());
        bind(new InterfaceAccessMessageEncoder());
        bind(new InterfacePlayerHeadMessageEncoder());
        bind(new InterfaceNPCHeadMessageEncoder());
        bind(new InterfaceAnimationMessageEncoder());
    }

    public MessageDecoder<?> get(int opcode) {
        return inCodecs[opcode];
    }

    @SuppressWarnings("unchecked")
    public <T extends Message> MessageEncoder<T> get(Class<T> clazz) {
        return (MessageEncoder<T>) outCodecs.get(clazz);
    }

    public void bind(MessageDecoder<?> decoder) {
        inCodecs[decoder.opcode] = decoder;
    }

    public void bind(MessageEncoder<?> encoder) {
        outCodecs.put(encoder.clazz, encoder);
    }
}
