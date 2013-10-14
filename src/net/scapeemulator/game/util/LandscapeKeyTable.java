package net.scapeemulator.game.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LandscapeKeyTable {

	private static final Logger logger = LoggerFactory.getLogger(LandscapeKeyTable.class);
	public static final int[] EMPTY_KEY_ARRAY = new int[4];

	public static LandscapeKeyTable open(String dir) throws IOException {
		return open(new File(dir));
	}

	public static LandscapeKeyTable open(File dir) throws IOException {
		LandscapeKeyTable table = new LandscapeKeyTable();
		for (File f : dir.listFiles()) {
			String name = f.getName();
			if (name.matches("^[0-9]+\\.txt$")) {
				int region = Integer.parseInt(name.substring(0, name.length() - 4));
				table.keys.put(region, readKeys(f));
			}
		}
		logger.info("Loaded " + table.keys.size() + " landscape keys.");
		return table;
	}

	private static int[] readKeys(File f) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
			int[] keys = new int[4];
			for (int i = 0; i < keys.length; i++) {
				keys[i] = Integer.parseInt(reader.readLine());
			}
			return keys;
		}
	}

	private final Map<Integer, int[]> keys = new HashMap<>();

	public int[] getKeys(int x, int y) {
		int[] k = keys.get((x << 8) | y);
		if (k == null)
			k = EMPTY_KEY_ARRAY;
		return k;
	}

}
