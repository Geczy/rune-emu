require 'java'

java_import 'net.scapeemulator.game.dialogue.HeadAnimation'
java_import 'net.scapeemulator.game.dialogue.DialogueContext'
java_import 'net.scapeemulator.game.model.player.action.StartDialogueAction'
java_import 'net.scapeemulator.game.model.Position'

MEN_AND_WOMEN_TYPES = [ 1 ]

##RuneEmulator::Utilities.create_npc(:normal, { :type => 1 }) do |npc|
##	npc.set_position Position.new(3222, 3222)
##end

# Bind the conversation option for all of the lumbridge NPCs
#RuneEmulator::Bootstrap.bind_npc_option(:one) do |player, npc, option, context|
#	if option.eql?('talk-to')
#		if MEN_AND_WOMEN_TYPES.include?(npc.type)
#			player.start_action StartDialogueAction.new(player, npc, RuneEmulator::Utilities.get_dialogue(:lumbridge_man_woman))
#			context.stop
#		end
#	end
#end

# Build the man and women dialogue
#RuneEmulator::Utilities.build_dialogue(:lumbridge_man_woman) do |builder|

	# Append the start of the dialogue
#	builder.append_start do |stage|

		# On initialization send the player with a greeting
#		stage.on_init do |context|
#			context.open_player_conversation_dialogue([ "Salutations!", "Hello there.", "Greetings." ].sample, HeadAnimation::HAPPY, true)
#		end

		# On input just send the dialogue to the next stage
#		stage.on_option { |context| context.set_stage('response') }
#	end

	# Append the response of the dialogue	
#	builder.append_stage('response') do |stage|

		# On initialization send the player the response text
#		stage.on_init do |context|
#			context.open_npc_conversation_dialogue("Sorry, I prefer not to talk to strangers.", DialogueContext::CURRENT_TARGET, HeadAnimation::HAPPY, true)
#		end

		# Stop the dialogue on any sort of input
#		stage.on_option { |context| context.stop }
#	end
#end

#RuneEmulator::Utilities.build_dialogue(:lumbridge_hans) do |builder|

#end