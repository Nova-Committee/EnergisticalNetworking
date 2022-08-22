package committee.nova.engnet;

import committee.nova.engnet.block.BlockInit;
import committee.nova.engnet.item.ItemInit;
import committee.nova.engnet.worldgen.WorldGenInit;
import net.fabricmc.api.ModInitializer;

public class EnergisticalNetworking implements ModInitializer {
	@Override
	public void onInitialize() {
		BlockInit.register();
		ItemInit.register();
		WorldGenInit.register();
		Constants.LOGGER.info("Hello Fabric world!");
	}
}
