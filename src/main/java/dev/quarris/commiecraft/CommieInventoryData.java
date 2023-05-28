package dev.quarris.commiecraft;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;

public class CommieInventoryData extends SavedData {

    private static final String OUR_FILE = "commiecraft";

    public static final NonNullList<ItemStack> OUR_ITEMS = NonNullList.withSize(36, ItemStack.EMPTY);
    public static final NonNullList<ItemStack> OUR_ARMOR = NonNullList.withSize(4, ItemStack.EMPTY);
    public static final NonNullList<ItemStack> OUR_OFFHAND = NonNullList.withSize(1, ItemStack.EMPTY);

    public static CommieInventoryData getData(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(CommieInventoryData::load, CommieInventoryData::new, OUR_FILE);
    }

    public CommieInventoryData() {

    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("Inventory", this.saveInventory());
        return tag;
    }

    private static CommieInventoryData load(CompoundTag tag) {
        CommieInventoryData data = new CommieInventoryData();
        data.loadInventory(tag.getList("Inventory", Tag.TAG_COMPOUND));
        return data;
    }

    public ListTag saveInventory() {
        ListTag tag = new ListTag();

        for(int i = 0; i < OUR_ITEMS.size(); ++i) {
            if (!OUR_ITEMS.get(i).isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                OUR_ITEMS.get(i).save(compoundtag);
                tag.add(compoundtag);
            }
        }

        for(int j = 0; j < OUR_ARMOR.size(); ++j) {
            if (!OUR_ARMOR.get(j).isEmpty()) {
                CompoundTag compoundtag1 = new CompoundTag();
                compoundtag1.putByte("Slot", (byte)(j + 100));
                OUR_ARMOR.get(j).save(compoundtag1);
                tag.add(compoundtag1);
            }
        }

        for(int k = 0; k < OUR_OFFHAND.size(); ++k) {
            if (!OUR_OFFHAND.get(k).isEmpty()) {
                CompoundTag compoundtag2 = new CompoundTag();
                compoundtag2.putByte("Slot", (byte)(k + 150));
                OUR_OFFHAND.get(k).save(compoundtag2);
                tag.add(compoundtag2);
            }
        }

        return tag;
    }

    private void loadInventory(ListTag tag) {
        OUR_ITEMS.clear();
        OUR_ARMOR.clear();
        OUR_OFFHAND.clear();

        for(int i = 0; i < tag.size(); ++i) {
            CompoundTag compoundtag = tag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.of(compoundtag);
            if (!itemstack.isEmpty()) {
                if (j < OUR_ITEMS.size()) {
                    OUR_ITEMS.set(j, itemstack);
                } else if (j >= 100 && j < OUR_ARMOR.size() + 100) {
                    OUR_ARMOR.set(j - 100, itemstack);
                } else if (j >= 150 && j < OUR_OFFHAND.size() + 150) {
                    OUR_OFFHAND.set(j - 150, itemstack);
                }
            }
        }
    }
}
