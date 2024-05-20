package net.endermanofdoom.mowithers.entity.wither;

import javax.annotation.Nullable;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherDiamond extends EntityHostileWither
{

	public EntityWitherDiamond(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 50;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    protected Block getShotBlock() 
    {
		return Blocks.DIAMOND_BLOCK;
	}
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(1F);
        skull.setType(27);
        skull.setSkullTexture("wither/block/wither_diamond");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(8.0D);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.AQUA;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_ROCK;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 72D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 12D;
	}

	public double getMobSpeed() 
	{
		return 0.325D;
	}
	
    public float getWitherScale()
    {
    	return super.getWitherScale() * 3F;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_DIAMOND[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_DIAMOND[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_DIAMOND[2];
    }
    
    protected double getFlyHeight()
    {
        return 7D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.DIAMOND_BLOCK.getDefaultState()));
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.DIAMOND_BLOCK.getDefaultState()));
        }
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextInt(9) != 1 ? Items.DIAMOND : Item.getItemFromBlock(Blocks.DIAMOND_BLOCK);
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.175F;
    }
    
	public int[] getBarColor() 
	{
		return new int[] {0, 55, 255, 0, this.isArmored() || this.isSuperBoss() ? 122 : 0, this.isArmored() || this.isSuperBoss() ? 255 : 0};
	}
}
