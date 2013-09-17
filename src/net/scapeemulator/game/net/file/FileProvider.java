package net.scapeemulator.game.net.file;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FileProvider {

	private static final File root = new File("data/game/www/");
	private final boolean codeOnly;

	public FileProvider(boolean codeOnly) {
		this.codeOnly = codeOnly;
	}

	public FileRegion serve(String path) throws IOException {
		path = rewrite(path);
		if (codeOnly && !path.matches("^/(jogl_\\d_\\d\\.lib|(loader|loader_gl|runescape)\\.jar|(jogl|runescape|runescape_gl)\\.pack200|unpackclass.pack)$"))
			return null;

		File f = new File(root, path);
		if (!f.getAbsolutePath().startsWith(root.getAbsolutePath()))
			return null;

		if (!f.exists() || !f.isFile())
			return null;

		return new DefaultFileRegion(FileChannel.open(f.toPath(), StandardOpenOption.READ), 0, f.length());
	}

	private String rewrite(String path) {
		Pattern pattern = Pattern.compile("^/jogl_(\\d)_(\\d)_-?\\d+\\.lib$");
		Matcher matcher = pattern.matcher(path);
		if (matcher.matches()) {
			return "/jogl_" + matcher.group(1) + "_" + matcher.group(2) + ".lib";
		}

		pattern = Pattern.compile("^/(jogl|runescape|runescape_gl)_-?\\d+\\.pack200$");
		matcher = pattern.matcher(path);
		if (matcher.matches()) {
			return "/" + matcher.group(1) + ".pack200";
		}

		pattern = Pattern.compile("^/(loader|loader_gl|runescape)_-?\\d+\\.jar$");
		matcher = pattern.matcher(path);
		if (matcher.matches()) {
			return "/" + matcher.group(1) + ".jar";
		}

		if (path.matches("^/unpackclass_-?\\d+\\.pack$")) {
			return "/unpackclass.pack";
		}

		if (path.equals("/")) {
			return "/index.html";
		}

		return path;
	}

}
