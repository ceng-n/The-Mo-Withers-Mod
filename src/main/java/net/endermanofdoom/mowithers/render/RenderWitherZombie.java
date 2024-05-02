package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherZombie extends RenderBaseWither<EntityWitherZombie>
{
    public RenderWitherZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    protected ResourceLocation getEntityTexture(EntityWitherZombie entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/mob/wither_zombie_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/mob/wither_zombie.png");
    }

    protected ResourceLocation getAuraTexture(EntityBaseWither entity)
    {
        return new ResourceLocation("textures/blocks/concrete_powder_orange.png");
    }
}