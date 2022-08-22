package committee.nova.engnet.block;

import committee.nova.engnet.Constants;
import committee.nova.engnet.item.ItemInit;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.api.EnergyStorage;

import java.util.Arrays;

public final class BlockInit {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            Constants.id("main"),
            () -> new ItemStack(ItemInit.NETWORK_LINKER));

    public static final ResonanceCrystalOreBlock RESONANCE_CRYSTAL_ORE;

    public static final NetworkEmitterBlock NETWORK_EMITTER;
    public static final NetworkReceiverBlock NETWORK_RECEIVER;
    public static final NetworkTerminalBlock NETWORK_TERMINAL;

    public static final BlockEntityType<NetworkEmitterBlockEntity> NETWORK_EMITTER_BE;
    public static final BlockEntityType<NetworkReceiverBlockEntity> NETWORK_RECEIVER_BE;
    public static final BlockEntityType<NetworkTerminalBlockEntity> NETWORK_TERMINAL_BE;

    static {
        RESONANCE_CRYSTAL_ORE = new ResonanceCrystalOreBlock(AbstractBlock.Settings.of(Material.AMETHYST, MapColor.TERRACOTTA_YELLOW).hardness(4.0f).requiresTool(),
                ConstantIntProvider.create(8));

        NETWORK_EMITTER = new NetworkEmitterBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
        NETWORK_EMITTER_BE = FabricBlockEntityTypeBuilder.create(NetworkEmitterBlockEntity::new, NETWORK_EMITTER).build();
        NETWORK_RECEIVER = new NetworkReceiverBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
        NETWORK_RECEIVER_BE = FabricBlockEntityTypeBuilder.create(NetworkReceiverBlockEntity::new, NETWORK_RECEIVER).build();
        NETWORK_TERMINAL = new NetworkTerminalBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
        NETWORK_TERMINAL_BE = FabricBlockEntityTypeBuilder.create(NetworkTerminalBlockEntity::new, NETWORK_TERMINAL).build();
    }

    public static void register() {
        registerSimpleBlock("resonance_crystal_ore", RESONANCE_CRYSTAL_ORE);
        registerBlockWithEntity("network_emitter", NETWORK_EMITTER, NETWORK_EMITTER_BE);
        registerBlockWithEntity("network_receiver", NETWORK_RECEIVER, NETWORK_RECEIVER_BE);
        registerBlockWithEntity("network_terminal", NETWORK_TERMINAL, NETWORK_TERMINAL_BE, Direction.DOWN, Direction.UP);
    }

    private static void registerSimpleBlock(String name, Block block) {
        Registry.register(Registry.BLOCK, Constants.id(name), block);
        Registry.register(Registry.ITEM, Constants.id(name), new BlockItem(block, new Item.Settings().group(ITEM_GROUP)));
    }

    private static <T extends AbstractNetworkMemberBlockEntity, B extends BlockWithEntity> void registerBlockWithEntity(String name, B block, BlockEntityType<T> be) {
        Registry.register(Registry.BLOCK, Constants.id(name), block);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Constants.id(name), be);
        Registry.register(Registry.ITEM, Constants.id(name), new BlockItem(block, new Item.Settings().group(ITEM_GROUP)));
        EnergyStorage.SIDED.registerForBlockEntity((be1, dir) -> be1.energyStorage, be);
    }

    private static <T extends AbstractNetworkMemberBlockEntity, B extends BlockWithEntity> void registerBlockWithEntity(String name, B block, BlockEntityType<T> be, Direction... validDirs) {
        Registry.register(Registry.BLOCK, Constants.id(name), block);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Constants.id(name), be);
        Registry.register(Registry.ITEM, Constants.id(name), new BlockItem(block, new Item.Settings().group(ITEM_GROUP)));
        EnergyStorage.SIDED.registerForBlockEntity((be1, dir) -> {
            if (Arrays.asList(validDirs).contains(dir)) {
                return be1.energyStorage;
            } else {
                return null;
            }
        }, be);
    }
}
