package za.lana.aluriantech.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Nullable;

import static za.lana.aluriantech.util.StructurePlacerAPI.createRandom;

//this is based from vanilla structure block entity code as a reference only

public class StructurePlacerLib {

    public static final String AUTHOR_KEY = "author";
    @Nullable
    private Identifier templateName;
    private String author = "";
    private BlockPos offset = new BlockPos(0, 1, 0);
    private Vec3i size;
    private BlockMirror mirror;
    private BlockRotation rotation;
    private StructureBlockMode mode;
    private boolean ignoreEntities;
    private boolean powered;
    private boolean showAir;
    private boolean showBoundingBox;
    private float integrity;
    private long seed;
    private final NbtCompound nbt = new NbtCompound();
    private final ServerWorld world;


    public StructurePlacerLib(ServerWorld world, Identifier templateName, BlockPos blockPos, BlockMirror mirror, BlockRotation rotation, boolean ignoreEntities, float integrity, BlockPos offset) {
        this.world = world;
        this.size = Vec3i.ZERO;
        this.mirror = BlockMirror.NONE;
        this.rotation = BlockRotation.NONE;
        this.ignoreEntities = true;
        this.showBoundingBox = true;
        this.integrity = 1.0F;
    }


    public String getTemplateName() {
        return this.templateName == null ? "" : this.templateName.toString();
    }

    public boolean hasStructureName() {
        return this.templateName != null;
    }

    public void setTemplateName(@Nullable String templateName) {
        this.setTemplateName(StringHelper.isEmpty(templateName) ? null : Identifier.tryParse(templateName));
    }

    public void setTemplateName(@Nullable Identifier templateName) {
        this.templateName = templateName;
    }

    public BlockPos getOffset() {
        return this.offset;
    }

    public void setOffset(BlockPos offset) {
        this.offset = offset;
    }

    public Vec3i getSize() {
        return this.size;
    }

    public void setSize(Vec3i size) {
        this.size = size;
    }

    public BlockMirror getMirror() {
        return this.mirror;
    }

    public void setMirror(BlockMirror mirror) {
        this.mirror = mirror;
    }

    public BlockRotation getRotation() {
        return this.rotation;
    }

    public void setRotation(BlockRotation rotation) {
        this.rotation = rotation;
    }

    public boolean shouldIgnoreEntities() {
        return this.ignoreEntities;
    }

    public void setIgnoreEntities(boolean ignoreEntities) {
        this.ignoreEntities = ignoreEntities;
    }

    public float getIntegrity() {
        return this.integrity;
    }

    public void setIntegrity(float integrity) {
        this.integrity = integrity;
    }

    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public boolean loadAndTryPlaceStructure(ServerWorld world) {
        if (this.templateName != null) {
            StructureTemplate structureTemplate = world.getStructureTemplateManager().getTemplate(this.templateName).orElse(null);
            if (structureTemplate == null) {
                return false;
            } else if (structureTemplate.getSize().equals(this.size)) {
                this.loadAndPlaceStructure(world, structureTemplate);
                return true;
            } else {
                this.loadStructure(structureTemplate);
                return false;
            }
        } else {
            return false;
        }
    }

    private void loadStructure(StructureTemplate template) {
        this.author = !StringHelper.isEmpty(template.getAuthor()) ? template.getAuthor() : "";
        this.size = template.getSize();
        //this.markDirty();
    }

    @Nullable
    private StructureTemplate getStructureTemplate(ServerWorld world) {
        return this.templateName == null ? null : world.getStructureTemplateManager().getTemplate(this.templateName).orElse(null);
    }

    private void loadAndPlaceStructure(ServerWorld world, StructureTemplate template) {
        this.loadStructure(template);
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities);
        if (this.integrity < 1.0F) {
            structurePlacementData.clearProcessors().addProcessor(new BlockRotStructureProcessor(MathHelper.clamp(this.integrity, 0.0F, 1.0F))).setRandom(createRandom(this.seed));
        }

        BlockPos blockPos = (BlockPos.ORIGIN).add(this.offset);
        template.place(world, blockPos, blockPos, structurePlacementData, createRandom(this.seed), 2);
    }
    public boolean loadStructure(ServerWorld world) {
        StructureTemplate structureTemplate = this.getStructureTemplate(world);
        if (structureTemplate == null) {
            return false;
        } else {
            this.loadStructure(structureTemplate);
            return true;
        }
    }
    public void loadAndPlaceStructure(ServerWorld world) {
        StructureTemplate structureTemplate = this.getStructureTemplate(world);
        if (structureTemplate != null) {
            this.loadAndPlaceStructure(world, structureTemplate);
        }

    }

    public void unloadStructure() {
        if (this.templateName != null) {
            ServerWorld serverWorld = this.world;
            StructureTemplateManager structureTemplateManager = serverWorld.getStructureTemplateManager();
            structureTemplateManager.unloadTemplate(this.templateName);
        }
    }

    public boolean isStructureAvailable() {
        if (this.mode == StructureBlockMode.LOAD && !this.world.isClient && this.templateName != null) {
            ServerWorld serverWorld = (ServerWorld)this.world;
            StructureTemplateManager structureTemplateManager = serverWorld.getStructureTemplateManager();

            try {
                return structureTemplateManager.getTemplate(this.templateName).isPresent();
            } catch (InvalidIdentifierException var4) {
                return false;
            }
        } else {
            return false;
        }
    }
}
