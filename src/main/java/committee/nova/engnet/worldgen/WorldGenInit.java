package committee.nova.engnet.worldgen;

import committee.nova.engnet.Constants;
import committee.nova.engnet.block.BlockInit;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Arrays;

public class WorldGenInit {
    private static ConfiguredFeature<?, ?> RESONANCE_CRYSTAL_ORE_CONFIGURED_FEATURE = new ConfiguredFeature
            (Feature.ORE, new OreFeatureConfig(
                    new BlockMatchRuleTest(Blocks.END_STONE),
                    BlockInit.RESONANCE_CRYSTAL_ORE.getDefaultState(),
                    3));

    public static PlacedFeature RESONANCE_CRYSTAL_ORE_PLACED_FEATURE = new PlacedFeature(
            RegistryEntry.of(RESONANCE_CRYSTAL_ORE_CONFIGURED_FEATURE),
            Arrays.asList(
                    CountPlacementModifier.of(3),
                    SquarePlacementModifier.of(),
                    HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop())));

    public static void register() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                Constants.id("resonance_crystal_ore_worldgen"), RESONANCE_CRYSTAL_ORE_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, Constants.id("resonance_crystal_ore_worldgen"),
                RESONANCE_CRYSTAL_ORE_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        Constants.id("resonance_crystal_ore_worldgen")));
    }
}
