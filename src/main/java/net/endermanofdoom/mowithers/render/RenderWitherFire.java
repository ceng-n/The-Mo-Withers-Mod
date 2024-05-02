package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherFire;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherFire extends RenderBaseWither<EntityWitherFire>
{
    public RenderWitherFire(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    protected ResourceLocation getEntityTexture(EntityWitherFire entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/element/wither_fire_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/element/wither_fire.png");
    }

    protected ResourceLocation getAuraTexture(EntityWitherFire entity)
    {
        return new ResourceLocation(MoWithers.MODID, "textures/entity/wither/element/wither_fire_armor.png");
    }
}