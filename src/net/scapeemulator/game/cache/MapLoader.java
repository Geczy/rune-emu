package net.scapeemulator.game.cache;

import net.scapeemulator.cache.Cache;
import net.scapeemulator.cache.Container;
import net.scapeemulator.cache.ReferenceTable;
import net.scapeemulator.cache.util.ByteBufferUtils;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.object.ObjectType;
import net.scapeemulator.game.util.LandscapeKeyTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO: Convert to class
 */
public final class MapLoader {

    public static final int FLAG_CLIP = 0x1;
    public static final int BRIDGE_FLAG = 0x2;

    private static final Logger logger = LoggerFactory.getLogger(MapLoader.class);
    private final List<MapListener> listeners = new LinkedList<MapListener>();

    public MapLoader() {}

    public void addListener(MapListener listener) {
        listeners.add(listener);
    }

    public void load(Cache cache, LandscapeKeyTable keyTable) throws IOException {
        logger.info("Reading map and landscape files...");

        ReferenceTable rt = ReferenceTable.decode(Container.decode(cache.getStore().read(255, 5)).getData());

        for (int x = 0; x < 256; x++) {
            for (int y = 0; y < 256; y++) {
                try {
                    int landscapeId = rt.getEntryId("l" + x + "_" + y);

                    if (landscapeId != -1) {
                        readLandscape(cache, keyTable, x, y, landscapeId);
                    }

                    int mapId = rt.getEntryId("m" + x + "_" + y);

                    if(mapId != -1) {
                        readMap(cache, x, y, mapId);
                    }
                } catch (Exception ex) {
                    //logger.info("Failed to read map/landscape file " + x + ", " + y + ".", ex);
                }
            }
        }
    }

    private void readLandscape(Cache cache, LandscapeKeyTable keyTable, int x, int y, int fileId) throws IOException {
        ByteBuffer buffer = cache.getStore().read(5, fileId);

        int[] keys = keyTable.getKeys(x, y);
        
        buffer = Container.decode(buffer, keys).getData();

        int id = -1;

        while(true) {
            int deltaId = ByteBufferUtils.getSmart(buffer);
            if(deltaId == 0) {
                break;
            }

            id += deltaId;

            int pos = 0;

            while(true) {

                int deltaPos = ByteBufferUtils.getSmart(buffer);
                if(deltaPos == 0) {
                    break;
                }

                pos += deltaPos - 1;

                int localX = (pos >> 6) & 0x3F;
                int localY = pos & 0x3F;
                int height = (pos >> 12) & 0x3;

                int temp = buffer.get() & 0xFF;
                int type = temp >> 2;
                int rotation = temp & 0x3;

                Position position = new Position(x * 64 + localX, y * 64 + localY, height);

                for (MapListener listener : listeners) {
                    listener.objectDecoded(id, rotation, ObjectType.forId(type), position);
                }
            }
        }
    }

    private void readMap(Cache cache, int x, int y, int id) throws IOException {
        ByteBuffer buffer = cache.read(5, id).getData();

        for (int plane = 0; plane < 4; plane++) {
            for (int localX = 0; localX < 64; localX++) {
                for (int localY = 0; localY < 64; localY++) {

                    Position position = new Position(x * 64 + localX, y * 64 + localY, plane);

                    /* The tile variables */
                    int flags = 0;

                    for (;;) {
                        int config = buffer.get() & 0xFF;

                        if (config == 0) {
                            for (MapListener listener : listeners) {
                                listener.tileDecoded(flags, position);
                            }
                            break;
                        } else if (config == 1) {
                            int i = buffer.get() & 0xFF;

                            for (MapListener listener : listeners) {
                                listener.tileDecoded(flags, position);
                            }
                            break;
                        } else if (config <= 49) {
                            int i = buffer.get() & 0xFF;
                        } else if (config <= 81) {
                            flags = config - 49;
                        }
                    }
                }
            }
        }
    }
}
