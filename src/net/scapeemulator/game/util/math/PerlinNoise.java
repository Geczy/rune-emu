package net.scapeemulator.game.util.math;

public final class PerlinNoise {

	public static int tileHeight(int x, int y) {
		int total = 0;

		/* 3 octaves */
		total += interpolatedNoise(x + 45365, y + 91923, 4) - 128;
		total += (interpolatedNoise(x + 10294, y + 37821, 2) - 128) / 2;
		total += (interpolatedNoise(x, y, 1) - 128) / 4;

		total = (int) (total * 0.3D) + 35;

		if (total < 10)
			return 10;
		else if (total > 60)
			return 60;
		else
			return total;
	}

	private static int interpolate(int a, int b, int t, int freqReciprocal) {
		int cosine = 65536 - Trigonometry.COSINE[t * Trigonometry.COSINE.length / (2 * freqReciprocal)] / 2;
		return (a * (65536 - cosine)) / 65536 + (b * cosine) / 65536;
	}

	private static int interpolatedNoise(int x, int y, int freqReciprocal) {
		int xt = x % freqReciprocal;
		int yt = y % freqReciprocal;

		x /= freqReciprocal;
		y /= freqReciprocal;

		int v1 = smoothNoise(x, y);
		int v2 = smoothNoise(x + 1, y);
		int v3 = smoothNoise(x, y + 1);
		int v4 = smoothNoise(x + 1, y + 1);

		int i1 = interpolate(v1, v2, xt, freqReciprocal);
		int i2 = interpolate(v3, v4, xt, freqReciprocal);

		return interpolate(i1, i2, yt, freqReciprocal);
	}

	private static int smoothNoise(int x, int y) {
		int corners = noise(x - 1, y - 1) + noise(x + 1, y - 1) + noise(x - 1, y + 1) + noise(x + 1, y + 1);
		int sides = noise(x - 1, y) + noise(x + 1, y) + noise(x, y - 1) + noise(x, y + 1);
		int center = noise(x, y);
		return corners / 16 + sides / 8 + center / 4;
	}

	private static int noise(int x, int y) {
		int n = x + y * 57;
		n = (n << 13) ^ n;
		n = (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff;
		return (n >> 19) & 0xff;
	}

	private PerlinNoise() {

	}

}
