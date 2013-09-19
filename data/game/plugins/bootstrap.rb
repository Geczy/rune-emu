require 'java'

java_import 'net.scapeemulator.game.button.ButtonHandler'
java_import 'net.scapeemulator.game.model.ExtendedOption'
java_import 'net.scapeemulator.game.item.ItemHandler'
java_import 'net.scapeemulator.game.command.CommandHandler'
java_import 'net.scapeemulator.game.item.ItemOnItemHandlerAdapter'
java_import 'net.scapeemulator.game.item.ItemOnObjectHandler'
java_import 'net.scapeemulator.game.object.ObjectHandler'
java_import 'net.scapeemulator.game.player.PlayerHandler'
java_import 'net.scapeemulator.game.npc.NPCHandler'
java_import 'net.scapeemulator.game.model.World'
java_import 'net.scapeemulator.game.dialogue.Dialogue'
java_import 'net.scapeemulator.game.dialogue.Stage'

# To eliminate the annoying warning messages about reinitializing constants
$VERBOSE = nil

module RuneEmulator

	# The constants for each permission type
	ADMINISTRATOR = 2
	MODERATOR     = 1
	PLAYER        = 0

	# The list of NPCs to populate
	NPC_LIST = World::getWorld().getNpcs()

	class Bootstrap
		class << self
			def bind_cmd(name, permission=ADMINISTRATOR, &block)
				$ctx.add_command_handler(ProcCommandHandler.new(name, permission, block))
			end

			def bind_button(*args, &block)

				# By default bind button one
				option = ExtendedOption::ONE

				# If there are more arguments than the parent/child ids set the option
				if args.length == 3
					option = args[2]
				end

				$ctx.add_button_handler(ProcButtonHandler.new(option, args[0], args[1], block))
			end

			def bind_item_option(option, &block)
				$ctx.add_item_handler(ProcItemHandler.new(option, block))
			end

			def bind_object_option(option, &block)
				$ctx.add_object_handler(ProcObjectHandler.new(option, block))
			end

			def bind_item_on_object(item, object, &block)
				$ctx.add_item_on_object_handler(ProcItemOnObjectHandler.new(item, object, block))
			end

			def bind_item_on_item(item_one, item_two, &block)
				handler = ProcItemOnItemHandlerOne.new(item_one, item_two, block)

				# If the block has more than 4 arguments assume it is using handler two
				if block.arity > 4
					handler = ProcItemOnItemHandlerTwo.new(item_one, item_two, block)
				end

				$ctx.add_item_on_item_handler(handler)
			end

			def bind_npc_option(option, &block)
				$ctx.add_npc_handler(ProcNPCHandler.new(option, block))
			end
		end

		class ProcButtonHandler < ButtonHandler
			def initialize(option, parent, child, proc)
				super(option, parent, child)
				@proc = proc
			end

			def handle(player, param)
				@proc.call player, child, param
			end
		end

		class ProcCommandHandler < CommandHandler
			def initialize(name, permission, proc)
				super(name)
				@permission = permission
				@proc = proc
			end

			def handle(player, args)
				if player.rights >= @permission
					@proc.call player, args
				end		
			end
		end

		class ProcItemOnItemHandlerOne < ItemOnItemHandlerAdapter
			def initialize(item_one, item_two, proc) 
				super(item_one, item_two)
				@proc = proc
			end

			def handle(player, inventory, item_one, item_two)
				@proc.call player, inventory, item_one, item_two
			end
		end

		class ProcItemOnItemHandlerTwo < ItemOnItemHandlerAdapter
			def initialize(item_one, item_two, proc) 
				super(item_one, item_two)
				@proc = proc
			end

			def handle(player, inventory_one, inventory_two, item_one, item_two)
				@proc.call player, inventory_one, inventory_two, item_one, item_two
			end
		end

		class ProcItemHandler < ItemHandler
			def initialize(option, proc) 
				super(option)
				@proc = proc
			end

			def handle(player, inventory, item, option, context)
				@proc.call player, inventory, item, option, context
			end
		end

		class ProcItemOnObjectHandler < ItemOnObjectHandler
			def initialize(item, object, proc) 
				super(item, object)
				@proc = proc
			end

			def handle(player, object, item)
				@proc.call player, object, item
			end
		end

		class ProcObjectHandler < ObjectHandler
			def initialize(option, proc) 
				super(option)
				@proc = proc
			end

			def handle(player, object, option, context)
				@proc.call player, object, option, context
			end
		end

		class ProcPlayerHandler < PlayerHandler
			def initialize(option, proc) 
				super(option)
				@proc = proc
			end

			def handle(player, selected_player)
				@proc.call selected_player, option
			end
		end

		class ProcNPCHandler < NPCHandler
			def initialize(option, proc) 
				super(option)
				@proc = proc
			end

			def handle(player, npc, option, context)
				@proc.call player, npc, option, context
			end
		end
	end

	class Utilities

		DIALOGUES = {}

		class << self
			def build_dialogue(name, &block)
				builder = DialogueBuilder.new
				block.call builder
				DIALOGUES[name] = builder.dialogue
			end

			def get_dialogue(name)
				DIALOGUES.fetch(name)
			end
		end

		class DialogueBuilder
			attr_reader :dialogue

			def append_start(&block)
				stage = StageWrapper.new
				block.call(stage)

				@dialogue = Dialogue.new
				@dialogue.set_starting_stage(stage)
			end

			def append_stage(name, &block)
				stage = StageWrapper.new
				block.call(stage)
				@dialogue.add_stage(name, stage)
			end

			class StageWrapper < Stage
				def initialize()
					super()
				end

				def on_init(&block)
					@init_proc = block
				end

				def initializeContext(ctx)
					if @init_proc
						@init_proc.call ctx
					end
				end

				def on_option(&block)
					@option_proc = block
				end

				def handleOption(ctx, option)
					if @option_proc
						@option_proc.call ctx, option 
					end
				end
			end
		end
	end
end