package net.scapeemulator.game.model.player;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import net.scapeemulator.game.model.*;
import net.scapeemulator.game.model.grounditem.GroundItemList;
import net.scapeemulator.game.model.grounditem.GroundItemSynchronizer;
import net.scapeemulator.game.model.mob.Mob;
import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.model.object.GroundObjectSynchronizer;
import net.scapeemulator.game.model.player.inventory.Inventory;
import net.scapeemulator.game.msg.*;
import net.scapeemulator.game.msg.impl.ChatMessage;
import net.scapeemulator.game.msg.impl.EnergyMessage;
import net.scapeemulator.game.msg.impl.LogoutMessage;
import net.scapeemulator.game.msg.impl.ServerMessage;
import net.scapeemulator.game.net.game.GameSession;
import net.scapeemulator.game.model.grounditem.GroundItemList.Type;

import java.util.ArrayList;
import java.util.List;
import net.scapeemulator.game.msg.impl.PlayerMenuOptionMessage;
import net.scapeemulator.game.msg.impl.inter.InterfaceTextMessage;

public final class Player extends Mob {

    private static int appearanceTicketCounter = 0;

    private static int nextAppearanceTicket() {
        if (++appearanceTicketCounter == 0) {
            appearanceTicketCounter = 1;
        }

        return appearanceTicketCounter;
    }
    private int databaseId;
    private String username;
    private String password;
    private int rights = 0;
    private GameSession session;
    private boolean regionChanging;
    private boolean updateModelLists;
    private Position lastKnownRegion;
    private final World world = World.getWorld();
    private final List<Player> localPlayers = new ArrayList<>();
    private final List<NPC> localNpcs = new ArrayList<>();
    private Appearance appearance = Appearance.DEFAULT_APPEARANCE;
    private int energy = 100;
    private final InventorySet inventorySet = new InventorySet(this);
    private ChatMessage chatMessage;
    private final PlayerSettings settings = new PlayerSettings(this);
    private final InterfaceSet interfaceSet = new InterfaceSet(this);
    private final StateSet stateSet = new StateSet(this);
    private final AccessSet accessSet = new AccessSet(this);
    private final GroundItemList groundItems = new GroundItemList(Type.LOCAL);
    private final GroundItemSynchronizer groundItemSync = new GroundItemSynchronizer(this);
    private final GroundObjectSynchronizer groundObjSync = new GroundObjectSynchronizer(this);
    private int[] appearanceTickets = new int[World.MAX_PLAYERS];
    private int appearanceTicket = nextAppearanceTicket();
    private final PlayerOption[] options = new PlayerOption[10];

    public Player() {
        init();
    }

    private void init() {
        skillSet.addListener(new SkillMessageListener(this));
        skillSet.addListener(new SkillAppearanceListener(this));

        world.getGroundObjects().addListener(groundObjSync);

        world.getGroundItems().addListener(groundItemSync);
        groundItems.addListener(groundItemSync);
        
        /* Initialize all the player options */
        for(int i = 0; i < options.length; i++) {
            options[i] = new PlayerOption();
        }
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    public GameSession getSession() {
        return session;
    }

    public void setSession(GameSession session) {
        this.session = session;
    }

    public ChannelFuture send(Message message) {
        if (session != null) {
            return session.send(message);
        } else {
            return null;
        }
    }

    public void setInterfaceText(int widgetId, int componentId, String text) {
        send(new InterfaceTextMessage(widgetId, componentId, text));
    }

    public void sendMessage(String text) {
        send(new ServerMessage(text));
    }

    public boolean isRegionChanging() {
        return regionChanging;
    }

    public Position getLastKnownRegion() {
        return lastKnownRegion;
    }
    

    public void setLastKnownRegion(Position lastKnownRegion) {
        this.lastKnownRegion = lastKnownRegion;
        this.regionChanging = true;
    }

    public List<Player> getLocalPlayers() {
        return localPlayers;
    }

    public List<NPC> getLocalNpcs() {
        return localNpcs;
    }

    public int getAppearanceTicket() {
        return appearanceTicket;
    }

    public int[] getAppearanceTickets() {
        return appearanceTickets;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
        this.appearanceTicket = nextAppearanceTicket();
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        this.send(new EnergyMessage(energy));
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessage message) {
        this.chatMessage = message;
    }
    
    public boolean isChatUpdated() {
        return chatMessage != null;
    }

    public InventorySet getInventorySet() {
        return inventorySet;
    }

    public Inventory getInventory() {
        return inventorySet.getInventory();
    }

    public Inventory getEquipment() {
        return inventorySet.getEquipment();
    }

    public int getStance() {
        Item weapon = inventorySet.getEquipment().get(Equipment.WEAPON);
        if (weapon != null) {
            return EquipmentDefinition.forId(weapon.getId()).getStance();
        } else {
            return 1426;
        }
    }

    public PlayerSettings getSettings() {
        return settings;
    }

    public StateSet getStateSet() {
        return stateSet;
    }

    public InterfaceSet getInterfaceSet() {
        return interfaceSet;
    }
    
    public AccessSet getAccessSet() {
        return accessSet;
    }

    public GroundItemList getGroundItems() {
        return groundItems;
    }

    public void setUpdateModelLists(boolean updateModelLists) {
        this.updateModelLists = updateModelLists;
    }

    public boolean getUpdateModelLists() {
        return updateModelLists;
    }
    
    public void refreshOptions() {
        for(int i = 0; i < options.length; i++) {
            PlayerOption option = options[i];
            send(new PlayerMenuOptionMessage(i + 1, option.atTop(), option.getText()));
        }
    }
    
    public PlayerOption getOption(int id) {
        return options[id];
    }

    public void refreshGroundObjects() {
        // TODO: Make this cleaner?
        groundObjSync.purge();
        World.getWorld().getGroundObjects().fireEvents(groundObjSync);
    }

    public void refreshGroundItems() {
        // TODO: Make this cleaner?
        groundItemSync.purge();
        World.getWorld().getGroundItems().fireEvents(groundItemSync);
        groundItems.fireEvents(groundItemSync);
    }

    public void logout() {
        // TODO: Make this cleaner?
        World.getWorld().getGroundItems().removeListener(groundItemSync);
        World.getWorld().getGroundObjects().removeListener(groundObjSync);

        // TODO this seems fragile
        ChannelFuture future = send(new LogoutMessage());
        if (future != null) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void reset() {
        super.reset();
        updateModelLists = false;
        regionChanging = false;
        chatMessage = null;
    }

    @Override
    public boolean isRunning() {
        return settings.isRunning();
    }
}
