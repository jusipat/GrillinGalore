package net.cflip.grillingalore.client;

import net.cflip.grillingalore.client.gui.GrillScreen;
import net.cflip.grillingalore.registry.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class GrillinGaloreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(ModScreens.GRILL, GrillScreen::new);
	}
}
