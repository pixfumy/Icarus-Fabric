package net.pixfumy.icarus.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.pixfumy.icarus.IPlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayerEntity {
    private int timePlayed;
    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    private void readTimePlayed(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("timePlayed")) {
            this.timePlayed = tag.getInt("timePlayed");
        } else {
            this.timePlayed = 0;
        }
    }

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    private void writeTimePlayed(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("timePlayed", this.timePlayed + ((PlayerEntity)(Object)this).ticksAlive);
    }

    @Override
    public int getTimePlayed() {
        return this.timePlayed;
    }
}
