package dev.quarris.commiecraft;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModRef.ID)
public class Events {

    @SubscribeEvent
    public static void saveCommieInventory(WorldEvent.Save event) {
        if (event.getWorld() instanceof ServerLevel level) {
            CommieInventoryData.getData(level).setDirty();
        }
    }

    @SubscribeEvent
    public static void loadCommieInventory(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel level) {
            CommieInventoryData.getData(level);
        }
    }

}
