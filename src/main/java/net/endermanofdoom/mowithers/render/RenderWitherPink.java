package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherPink;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherPink extends RenderBaseWither<EntityWitherPink>
{
    public RenderWitherPink(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    protected ResourceLocation getEntityTexture(EntityWitherPink entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/good/wither_pink_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/good/wither_pink.png");
    }

    protected ResourceLocation getAuraTexture(EntityWitherPink entity)
    {
        return new ResourceLocation("textures/blocks/concrete_powder_pink.png");
    }
}