require 'java'

java_import 'net.scapeemulator.game.model.player.Equipment'

# Common administrator commands

RuneEmulator::Bootstrap.bind_item_option(:two) { |player, inventory, item, option, context|
  if option.eql?("equip") || option.eql?("wield") || option.eql?("wear")
    Equipment::equip(player, item.getSlot())
    context.stop
  end
}