package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import java.util.Random;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherVoid;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherVoid extends RenderBaseWither<EntityWitherVoid>
{
    private final Random rnd = new Random();
    
    public RenderWitherVoid(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.addLayer(new LayerDeath());
    }

    protected ResourceLocation getEntityTexture(EntityWitherVoid entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/superboss/wither_void_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/superboss/wither_void.png");
    }

    protected ResourceLocation getAuraTexture(EntityBaseWither entity)
    {
        int i = entity.deathTicks;
        return i > 300 && (i > 380 || i / 3 % 2 != 1) ? new ResourceLocation("textures/blocks/concrete_powder_black.png") : new ResourceLocation("textures/environment/end_sky.png");
    }
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityWitherVoid entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (!entity.isEntityAlive())
        {
            float f = ((float)entity.deathTicks + partialTicks) / 200.0F;
            double d0 = f * 0.25D;
            x += this.rnd.nextGaussian() * d0;
            z += this.rnd.nextGaussian() * d0;
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    public class LayerDeath implements LayerRenderer<EntityWitherVoid>
    {
        public void doRenderLayer(EntityWitherVoid entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {
            if (entitylivingbaseIn.deathTicks > 0)
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                RenderHelper.disableStandardItemLighting();
                float f = ((float)entitylivingbaseIn.deathTicks + partialTicks) / 400.0F;
                float f1 = 0.0F;

                if (f > 0.8F)
                {
                    f1 = (f - 0.8F) / 0.2F;
                }

                Random random = new Random(432L);
                GlStateManager.disableTexture2D();
                GlStateManager.shadeModel(7425);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                GlStateManager.disableAlpha();
                GlStateManager.enableCull();
                GlStateManager.depthMask(false);
                GlStateManager.pushMatrix();
                
                int k = entitylivingbaseIn.deathTicks / 2;

                for (int i = 0; (float)i < (f + f * f) / 2.0F * 200.0F; ++i)
                {
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
                    float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
                    float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
                    bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
                    bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
                    bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, k).endVertex();
                    bufferbuilder.pos(0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, k).endVertex();
                    bufferbuilder.pos(0.0D, (double)f2, (double)(1.0F * f3)).color(255, 255, 255, k).endVertex();
                    bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, k).endVertex();
                    tessellator.draw();
                }

                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
                GlStateManager.disableCull();
                GlStateManager.disableBlend();
                GlStateManager.shadeModel(7424);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableTexture2D();
                GlStateManager.enableAlpha();
                RenderHelper.enableStandardItemLighting();
            }
            else if (entitylivingbaseIn.getRamTime() < 0)
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                RenderHelper.disableStandardItemLighting();
                float f = ((float)entitylivingbaseIn.getRamTime() + partialTicks) / 200.0F;
                float f1 = 0.0F;

                if (f > 0.8F)
                {
                    f1 = (f - 0.8F) / 0.2F;
                }

                Random random = new Random(432L);
                GlStateManager.disableTexture2D();
                GlStateManager.shadeModel(7425);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                GlStateManager.disableAlpha();
                GlStateManager.enableCull();
                GlStateManager.depthMask(false);
                GlStateManager.pushMatrix();
                
                int k = entitylivingbaseIn.getRamTime() / 2;

                for (int i = 0; (float)i < (f + f * f) / 2.0F * 100.0F; ++i)
                {
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
                    float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
                    float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
                    bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
                    bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
                    bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, k).endVertex();
                    bufferbuilder.pos(0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, k).endVertex();
                    bufferbuilder.pos(0.0D, (double)f2, (double)(1.0F * f3)).color(255, 255, 255, k).endVertex();
                    bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, k).endVertex();
                    tessellator.draw();
                }

                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
                GlStateManager.disableCull();
                GlStateManager.disableBlend();
                GlStateManager.shadeModel(7424);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableTexture2D();
                GlStateManager.enableAlpha();
                RenderHelper.enableStandardItemLighting();
            }
        }

        public boolean shouldCombineTextures()
        {
            return false;
        }
    }
}