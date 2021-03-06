package net.pixfumy.icarus.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class GivePlayerEquipmentMixin {
	@Shadow @Final public Profiler profiler;

	@Shadow @Final public boolean isClient;

	@Shadow @Final public Dimension dimension;

	@Shadow protected LevelProperties levelProperties;

	@Inject(method = "onEntitySpawned", at = @At("HEAD"))
	private void givePlayerEquipment(Entity entity, CallbackInfo ci) {
		if (entity instanceof PlayerEntity && this.dimension.getType() == 0
			&& this.levelProperties.getTime() < 20) {
			ItemStack elytra = new ItemStack(Item.getFromId("elytra"));
			CompoundTag unbreakable = new CompoundTag();
			unbreakable.putBoolean("Unbreakable", true);
			elytra.setTag(unbreakable);
			ItemStack fireworks = new ItemStack(Items.FIREWORKS, 64);
			((PlayerEntity)entity).setArmorSlot(2, elytra);
			((PlayerEntity)entity).inventory.setInvStack(0, fireworks);
		}
	}
}
