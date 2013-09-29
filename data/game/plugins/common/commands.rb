require 'java'

java_import 'net.scapeemulator.game.GameServer'
java_import 'net.scapeemulator.game.model.World'
java_import 'net.scapeemulator.game.model.object.ObjectType'
java_import 'net.scapeemulator.game.model.mob.Animation'
java_import 'net.scapeemulator.game.model.player.Item'
java_import 'net.scapeemulator.game.model.player.SkillSet'
java_import 'net.scapeemulator.game.model.object.ObjectOrientation'

# Common administrator commands

bind :cmd, :name => 'window' do 
  player.interface_set.open_window(args[0].to_i)
end

bind :cmd, :name => 'item' do 
  amount = 1
  amount = args[1].to_i if args.length > 1
  player.get_inventory.add(Item.new(args[0].to_i, amount));
end

bind :cmd, :name => 'chatbox' do 
  player.interface_set.open_chatbox(args[0].to_i)
end

bind :cmd, :name => 'inventory' do 
  player.interface_set.open_inventory(args[0].to_i)
end

bind :cmd, :name => 'text' do 
  player.set_interface_text(args[0].to_i, args[1].to_i, args[2])
end

bind :cmd, :name => 'bitstate' do 
  player.state_set.set_bit_state(args[0].to_i, args[1].to_i)
end

bind :cmd, :name => 'state' do 
  player.state_set.set_state(args[0].to_i, args[1].to_i)
end

bind :cmd, :name => 'object' do
  rot = ObjectOrientation::WEST
  rot = args[1].to_i if args.length > 1
  RuneEmulator::OBJECT_LIST.put(player.position, args[0].to_i, rot, ObjectType::PROP)
end

bind :cmd, :name => 'anim' do 
  player.playAnimation(Animation.new(args[0].to_i))
end

bind :cmd, :name => 'reload' do 
  GameServer::getInstance().reloadPlugins()
end

bind :cmd, :name => 'master' do 
  skills = player.get_skill_set
  for id in 0...Skill::AMOUNT_SKILLS
    skills.add_experience(id, SkillSet::MAXIMUM_EXPERIENCE)
  end
end

bind :cmd, :name => 'empty' do 
  player.get_inventory.empty
end

bind :cmd, :name => 'tele' do 
  h = player.get_position.height
  h = args[2].to_i if args.length > 2
  player.teleport(Position.new(args[0].to_i, args[1].to_i, h));
end

bind :cmd, :name => 'pos' do 
  player.send_message(player.position.to_string);
end