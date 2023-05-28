package dev.quarris.commiecraft.mixins;

import dev.quarris.commiecraft.CommieInventoryData;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class InventoryMixin {

    @Mutable
    @Final
    @Shadow
    public final NonNullList<ItemStack> items = CommieInventoryData.OUR_ITEMS;
    @Mutable
    @Final
    @Shadow
    public final NonNullList<ItemStack> armor = CommieInventoryData.OUR_ARMOR;
    @Mutable
    @Final
    @Shadow
    public final NonNullList<ItemStack> offhand = CommieInventoryData.OUR_OFFHAND;

    @Shadow @Final public Player player;

    @Inject(method = "save", at =  @At("HEAD"), cancellable = true)
    private void overrideSave(ListTag p_36027_, CallbackInfoReturnable<ListTag> cir) {
        if (this.player.level instanceof ServerLevel level) {
            CommieInventoryData.getData(level).setDirty();
        }
        cir.setReturnValue(new ListTag());
    }

    @Inject(method = "load", at =  @At("HEAD"), cancellable = true)
    private void overrideLoad(ListTag p_36036_, CallbackInfo ci) {
        ci.cancel();
    }
}
