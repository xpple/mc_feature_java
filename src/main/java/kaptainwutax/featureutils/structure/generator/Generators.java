package kaptainwutax.featureutils.structure.generator;

import kaptainwutax.featureutils.Feature;
import kaptainwutax.featureutils.structure.BuriedTreasure;
import kaptainwutax.featureutils.structure.DesertPyramid;
import kaptainwutax.featureutils.structure.EndCity;
import kaptainwutax.featureutils.structure.Shipwreck;

import java.util.HashMap;
import java.util.Map;

public class Generators {
	private static final Map<Class<? extends Feature<?, ?>>, Generator.GeneratorFactory<?>> REGISTRY = new HashMap<>();

	static {
		register(DesertPyramid.class, DesertPyramidGenerator::new);
		register(BuriedTreasure.class, BuriedTreasureGenerator::new);
		register(Shipwreck.class, ShipwreckGenerator::new);
		register(EndCity.class, EndCityGenerator::new);
	}

	public static <T extends Feature<?, ?>> void register(Class<T> clazz, Generator.GeneratorFactory<?> lootFactory) {
		REGISTRY.put(clazz, lootFactory);
	}

	public static <T extends Feature<?, ?>> Generator.GeneratorFactory<?> get(Class<T> clazz) {
		return REGISTRY.get(clazz);
	}
}