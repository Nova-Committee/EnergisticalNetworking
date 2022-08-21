package committee.nova.engnet.item;

import committee.nova.engnet.Constants;
import committee.nova.engnet.block.BlockInit;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ItemInit {
    public static final Item NETWORK_LINKER = new NetworkLinkerItem(new Item.Settings().group(BlockInit.ITEM_GROUP).maxCount(1));
    public static final Item RESONANCE_CRYSTAL = new Item(new Item.Settings().group(BlockInit.ITEM_GROUP));

    public static void register() {
        Registry.register(Registry.ITEM, Constants.id("network_linker"), NETWORK_LINKER);
        Registry.register(Registry.ITEM, Constants.id("resonance_crystal"), RESONANCE_CRYSTAL);
    }
}
