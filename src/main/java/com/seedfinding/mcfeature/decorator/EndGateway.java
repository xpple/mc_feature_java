package com.seedfinding.mcfeature.decorator;

import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mcbiome.biome.Biomes;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.state.Dimension;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mccore.version.VersionMap;
import com.seedfinding.mcterrain.TerrainGenerator;

public class EndGateway extends BiomelessDecorator<EndGateway.Config, EndGateway.Data> {

	public static final VersionMap<Config> CONFIGS = new VersionMap<EndGateway.Config>()
		.add(MCVersion.v1_13, new EndGateway.Config(0, 3, 700))
		.add(MCVersion.v1_16, new EndGateway.Config(13, 4, 700));

	public EndGateway(MCVersion version) {
		super(CONFIGS.getAsOf(version), version);
	}

	public EndGateway(Config config, MCVersion version) {
		super(config, version);
	}

	@Override
	public String getName() {
		return name();
	}

	public static String name() {
		return "end_gateway";
	}

	public int getRarity() {
		return this.getConfig().rarity;
	}

	@Override
	public boolean canStart(EndGateway.Data data, long structureSeed, ChunkRand rand) {
		if(!super.canStart(data, structureSeed, rand)) return false;
		if(this.getVersion().isNewerOrEqualTo(MCVersion.v1_17)) {
			if(rand.nextFloat() >= 1.0f / (float)this.getRarity()) return false;
		} else {
			if(rand.nextInt(this.getRarity()) != 0) return false;
		}

		if(rand.nextInt(16) != data.offsetX) return false;
		if(rand.nextInt(16) != data.offsetZ) return false;
		return rand.nextInt(7) == data.height - 3;
	}

	@Override
	public boolean canGenerate(Data data, TerrainGenerator generator) {
		return true;
	}

	@Override
	public Dimension getValidDimension() {
		return Dimension.END;
	}

	@Override
	public boolean isValidBiome(Biome biome) {
		return biome == Biomes.END_HIGHLANDS;
	}

	@Override
	public EndGateway.Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
		this.setDecoratorSeed(structureSeed, chunkX, chunkZ, rand);
		if(rand.nextInt(this.getRarity()) != 0) return null;
		int blockX = (chunkX << 4) + rand.nextInt(16);
		int blockZ = (chunkZ << 4) + rand.nextInt(16);
		return new EndGateway.Data(this, blockX, blockZ, rand.nextInt(7) + 3);
	}

	public EndGateway.Data at(int blockX, int blockZ, int height) {
		return new EndGateway.Data(this, blockX, blockZ, height);
	}

	public static class Config extends BiomelessDecorator.Config {
		public final int rarity;

		public Config(int index, int step, int rarity) {
			super(index, step);
			this.rarity = rarity;
		}
	}

	public static class Data extends BiomelessDecorator.Data<EndGateway> {
		public final int blockX;
		public final int blockZ;
		public final int offsetX;
		public final int offsetZ;
		public final int height;

		public Data(EndGateway feature, int blockX, int blockZ, int height) {
			super(feature, blockX >> 4, blockZ >> 4);
			this.blockX = blockX;
			this.blockZ = blockZ;
			this.offsetX = this.blockX & 15;
			this.offsetZ = this.blockZ & 15;
			this.height = height;
		}
	}

}
