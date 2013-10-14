package net.scapeemulator.game.model.player;

import static net.scapeemulator.game.msg.impl.inter.InterfaceOpenMessage.CLOSABLE;
import static net.scapeemulator.game.msg.impl.inter.InterfaceOpenMessage.STATIC;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.scapeemulator.game.msg.impl.inter.InterfaceCloseMessage;
import net.scapeemulator.game.msg.impl.inter.InterfaceOpenMessage;
import net.scapeemulator.game.msg.impl.inter.InterfaceRootMessage;

public final class InterfaceSet {
    
    private static final int UNSET = -1;
    private static final int FIXED_TAB_OFFSET = 83;
    private static final int RESIZABLE_TAB_OFFSET = 93;

    /**
     * The enumeration for each of the display modes.
     */
    public enum DisplayMode {
        FIXED, RESIZABLE;
    }
    
    /**
     * The enumeration for each of the component types.
     */
    public enum ComponentType {
        
        /**
         * Each of the fixed interface components.
         */
        WINDOW_FIXED(Interface.FIXED, 11), 
        OVERLAY_FIXED(Interface.FIXED, 5),
        CHAT_OPTIONS_FIXED(Interface.FIXED, 14, 751),
        CHATBOX_FIXED(Interface.FIXED, 75, 752),
        ATTACK_TAB_FIXED(Interface.FIXED, Tab.ATTACK + FIXED_TAB_OFFSET, UNSET),
        SKILL_TAB_FIXED(Interface.FIXED, Tab.SKILLS + FIXED_TAB_OFFSET, Interface.SKILLS), 
        QUEST_TAB_FIXED(Interface.FIXED, Tab.QUEST + FIXED_TAB_OFFSET, Interface.QUESTS),
        INVENTORY_TAB_FIXED(Interface.FIXED, Tab.INVENTORY + FIXED_TAB_OFFSET, Interface.INVENTORY),
        EQUIPMENT_TAB_FIXED(Interface.FIXED, Tab.EQUIPMENT + FIXED_TAB_OFFSET, Interface.EQUIPMENT),
        PRAYER_TAB_FIXED(Interface.FIXED, Tab.PRAYER + FIXED_TAB_OFFSET, Interface.PRAYER), 
        MAGIC_TAB_FIXED(Interface.FIXED, Tab.MAGIC + FIXED_TAB_OFFSET, Interface.MAGIC),
        FRIENDS_TAB_FIXED(Interface.FIXED, Tab.FRIENDS + FIXED_TAB_OFFSET, Interface.FRIENDS),
        IGNORES_TAB_FIXED(Interface.FIXED, Tab.IGNORES + FIXED_TAB_OFFSET, Interface.IGNORES),
        CLAN_TAB_FIXED(Interface.FIXED, Tab.CLAN + FIXED_TAB_OFFSET, Interface.CLAN),
        SETTINGS_TAB_FIXED(Interface.FIXED, Tab.SETTINGS + FIXED_TAB_OFFSET, Interface.SETTINGS),
        EMOTES_TAB_FIXED(Interface.FIXED, Tab.EMOTES + FIXED_TAB_OFFSET, Interface.EMOTES),
        MUSIC_TAB_FIXED(Interface.FIXED, Tab.MUSIC + FIXED_TAB_OFFSET, Interface.MUSIC),
        LOGOUT_TAB_FIXED(Interface.FIXED, Tab.LOGOUT + FIXED_TAB_OFFSET, Interface.LOGOUT),
        
        /* 
         * Each of the resizable interface components.
         */
        WINDOW_RESIZABLE(Interface.RESIZABLE, 6), 
        OVERLAY_RESIZABLE(Interface.RESIZABLE, 5),
        CHAT_OPTIONS_RESIZABLE(Interface.RESIZABLE, 23, 751),
        CHATBOX_RESIZABLE(Interface.RESIZABLE, 70, 752), 
        ATTACK_TAB_RESIZABLE(Interface.RESIZABLE, Tab.ATTACK + RESIZABLE_TAB_OFFSET, UNSET),
        SKILL_TAB_RESIZABLE(Interface.RESIZABLE, Tab.SKILLS + RESIZABLE_TAB_OFFSET, Interface.SKILLS), 
        QUEST_TAB_RESIZABLE(Interface.RESIZABLE, Tab.QUEST + RESIZABLE_TAB_OFFSET, Interface.QUESTS),
        INVENTORY_TAB_RESIZABLE(Interface.RESIZABLE, Tab.INVENTORY + RESIZABLE_TAB_OFFSET, Interface.INVENTORY),
        EQUIPMENT_TAB_RESIZABLE(Interface.RESIZABLE, Tab.EQUIPMENT + RESIZABLE_TAB_OFFSET, Interface.EQUIPMENT),
        PRAYER_TAB_RESIZABLE(Interface.RESIZABLE, Tab.PRAYER + RESIZABLE_TAB_OFFSET, Interface.PRAYER), 
        MAGIC_TAB_RESIZABLE(Interface.RESIZABLE, Tab.MAGIC + RESIZABLE_TAB_OFFSET, Interface.MAGIC),
        FRIENDS_TAB_RESIZABLE(Interface.RESIZABLE, Tab.FRIENDS + RESIZABLE_TAB_OFFSET, Interface.FRIENDS),
        IGNORES_TAB_RESIZABLE(Interface.RESIZABLE, Tab.IGNORES + RESIZABLE_TAB_OFFSET, Interface.IGNORES),
        CLAN_TAB_RESIZABLE(Interface.RESIZABLE, Tab.CLAN + RESIZABLE_TAB_OFFSET, Interface.CLAN),
        SETTINGS_TAB_RESIZABLE(Interface.RESIZABLE, Tab.SETTINGS + RESIZABLE_TAB_OFFSET, Interface.SETTINGS),
        EMOTES_TAB_RESIZABLE(Interface.RESIZABLE, Tab.EMOTES + RESIZABLE_TAB_OFFSET, Interface.EMOTES),
        MUSIC_TAB_RESIZABLE(Interface.RESIZABLE, Tab.MUSIC + RESIZABLE_TAB_OFFSET, Interface.MUSIC),
        LOGOUT_TAB_RESIZABLE(Interface.RESIZABLE, Tab.LOGOUT + RESIZABLE_TAB_OFFSET, Interface.LOGOUT),
        
        /*
         * Other components. 
         */
        CHATBOX_INPUT(752, 8, 137), 
        CHATBOX_OVERLAY(752, 12, UNSET);
        
        /**
         * Each of the lists for components of each display mode.
         */
        private static List<ComponentType> fixedComponents, resizableComponents;
        
        private final int widgetId, componentId, defaultId, mode;
        
        ComponentType(int widgetId, int componentId) {
            this(widgetId, componentId, UNSET, CLOSABLE);
        }
        
        ComponentType(int widgetId, int componentId, int defaultId) {
            this(widgetId, componentId, defaultId, STATIC);
        }
        
        ComponentType(int widgetId, int componentId, int defaultId, int mode) {
            this.widgetId = widgetId;
            this.componentId = componentId;
            this.defaultId = defaultId;
            this.mode = mode;
        }
        
        public int getWidgetId() {
            return widgetId; 
        }
        
        public int getComponentId() {
            return componentId;
        }
        
        public int getDefaultId() {
            return defaultId;
        }
        
        public int getMode() {
            return mode;
        }
        
        public static ComponentType getWindow(DisplayMode mode) {
            switch(mode) {
                case FIXED:
                    return WINDOW_FIXED;
                case RESIZABLE:
                    return WINDOW_RESIZABLE;
            }
            throw new RuntimeException();
        }
        
        public static ComponentType getOverlay(DisplayMode mode) {
            switch(mode) {
                case FIXED:
                    return OVERLAY_FIXED;
                case RESIZABLE:
                    return OVERLAY_RESIZABLE;
            }
            throw new RuntimeException();
        }
        
        public static ComponentType getInventoryTab(DisplayMode mode) {
            switch(mode) {
                case FIXED:
                    return INVENTORY_TAB_FIXED;
                case RESIZABLE:
                    return INVENTORY_TAB_RESIZABLE;
            }
            throw new RuntimeException();
        }
        
        public static List<ComponentType> getComponentTypes(DisplayMode mode) {
            switch(mode) {
                case FIXED:
                    if(fixedComponents == null) {
                        fixedComponents = new LinkedList<>();
                        for(ComponentType type : values()) {
                            if(type.name().endsWith("FIXED")) {
                                fixedComponents.add(type);
                            }
                        }
                        fixedComponents.add(CHATBOX_INPUT);
                        fixedComponents.add(CHATBOX_OVERLAY);
                    }
                    return fixedComponents;
                case RESIZABLE:
                    if(resizableComponents == null) {
                        resizableComponents = new LinkedList<>();
                        for(ComponentType type : values()) {
                            if(type.name().endsWith("RESIZABLE")) {
                                resizableComponents.add(type);
                            }
                        }
                        resizableComponents.add(CHATBOX_INPUT);
                        resizableComponents.add(CHATBOX_OVERLAY);
                    }
                    return resizableComponents;
            }
            throw new RuntimeException();
        }
    }
    
    /**
     * The component class.
     */
    public class Component {
              
        private final ComponentType type;
        private ComponentListener listener;
        private int currentId;
        
        /**
         * Constructs a new {@link Component};
         * @param type The component type that this component is based off of. 
         */
        public Component(ComponentType type) {
            this.type = type;
            currentId = type.getDefaultId();
        }
        
        /**
         * Gets the current widget id.
         */
        public int getCurrentId() {
            return currentId;
        }
        
        /**
         * Attaches a listener to the component.
         */
        public Component setListener(ComponentListener listener) {
            this.listener = listener;
            return this;
        }
        
        /**
         * Removes a listener from the component.
         */
        public void removeListener() {
            listener = null;
        }
        
        /**
         * Sets the current widget id.
         */
        public Component set(int id) {
            currentId = id;
            return this;
        }
        
        /**
         * Alerts the listeners that an input for the interface was pressed.
         */
        public void alertInputPressed(int componentId, int dynamicId) {
            if(listener != null) {
                listener.inputPressed(this, componentId, dynamicId);
            }
        }
        
        /**
         * Forces the component closed call to all the registered listeners.
         */
        public void forceComponentClosed() {
            if(listener != null) {
                listener.componentClosed(this);
            }
        }
        
        /**
         * Refreshes the component by sending an open interface message to the player.
         */
        public void refresh() {
            if(currentId != UNSET) {
                player.send(new InterfaceOpenMessage(type.getWidgetId(), type.getComponentId(), currentId, type.getMode()));
            }
        }
        
        /**
         * Resets the component; if the component is static refreshes the component and if it is
         * closable will request that the client close that interface.
         */
        public void reset() {
            switch(type.getMode()) {
                case STATIC:
                    if(currentId != type.getDefaultId()) {
                        
                        currentId = type.getDefaultId();
                        if(currentId != UNSET) {
                            refresh();
                            return;
                        }
                        
                        /* Close the interface if it is just going to go back to being unset */
                        player.send(new InterfaceCloseMessage(type.getWidgetId(), type.getComponentId()));                     
                    }
                    break;
                case CLOSABLE:
                    if(currentId != UNSET) {
                        player.send(new InterfaceCloseMessage(type.getWidgetId(), type.getComponentId()));
                        if(listener != null) {
                            listener.componentClosed(this);
                        }
                        currentId = UNSET;
                    }
                    break;
            }
        }
    }
    
    /**
     * The player to send updates to when interfaces are opened or closed.
     */
    private final Player player;
    
    /**
     * Each of the components that the player can have opened.
     */
    private final Map<ComponentType, Component> components = new HashMap<>(); 
    private DisplayMode mode = DisplayMode.FIXED;

    public InterfaceSet(Player player) {
        this.player = player;
    }

    public DisplayMode getDisplayMode() {
        return mode;
    }

    public void setDisplayMode(DisplayMode mode) {
        this.mode = mode;
    }

    public void init() {
        
        /* Send the proper root widget */
        switch(mode) {
            case FIXED:
                player.send(new InterfaceRootMessage(Interface.FIXED));
                break;
            case RESIZABLE:
                player.send(new InterfaceRootMessage(Interface.RESIZABLE));
                break;
        }

        /* Clear out all the current components if any exist */
        components.clear();
        
        /* Get the components for the display mode, and refresh each one */
        List<ComponentType> types = ComponentType.getComponentTypes(mode);
        for(ComponentType type : types) {
            
            /* Create and refresh the component */
            Component component = new Component(type);
            component.refresh();
            
            /* Register the component to the component mapping */
            components.put(type, component);
        }
    }
    
    public void openWindow(int id) {
        Component window = components.get(ComponentType.getWindow(mode));
        window.set(id).refresh(); 
    }

    public void openWindow(int id, ComponentListener listener) {
        Component window = components.get(ComponentType.getWindow(mode));
        window.set(id).setListener(listener).refresh();
    }
    
    public void closeWindow() {
        Component window = components.get(ComponentType.getWindow(mode));
        window.reset();
    }
    
    public void openOverlay(int id) {
        Component overlay = components.get(ComponentType.getOverlay(mode));
        overlay.set(id).refresh();
    }
    
    public void closeOverlay() {
        Component overlay = components.get(ComponentType.getOverlay(mode));
        overlay.reset();
    }
    
    public void openChatbox(int id) {
        Component chatbox = components.get(ComponentType.CHATBOX_OVERLAY);
        chatbox.set(id).refresh();
    }
    
    public void openChatbox(int id, ComponentListener listener) {
        Component chatbox = components.get(ComponentType.CHATBOX_OVERLAY);
        chatbox.set(id).setListener(listener).refresh();
    }
    
    public Component getChatbox() {
        return components.get(ComponentType.CHATBOX_OVERLAY);
    }
    
    public void closeChatbox() {
        Component chatbox = components.get(ComponentType.CHATBOX_OVERLAY);
        chatbox.reset();
    }
    
    public void openInventory(int id) {
        Component chatbox = components.get(ComponentType.getInventoryTab(mode));
        chatbox.set(id).refresh();
    }
    
    public void closeInventory() {
        Component chatbox = components.get(ComponentType.getInventoryTab(mode));
        chatbox.reset();
    }
    
    public Component getComponent(int id) {
        for(Component component : components.values()) {
            if(component.currentId == id) {
                return component;
            }
        }
        return null;
    }
    
    public void resetAll() {
        for(Component component : components.values()) {
            component.reset();
        }
    }
}
