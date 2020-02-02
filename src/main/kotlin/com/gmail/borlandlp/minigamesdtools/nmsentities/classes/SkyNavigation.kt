package com.gmail.borlandlp.minigamesdtools.nmsentities.classes

import net.minecraft.server.v1_12_R1.*

class SkyNavigation(entityInsentient: EntityInsentient?, world: World?) :
    Navigation(entityInsentient, world) {
    /*
    ** Source from deobfuscated vanilla server
    public void onUpdateNavigation() {
      ++this.totalTicks;
      if (this.tryUpdatePath) {
         this.updatePath();
      }

      if (!this.noPath()) {
         Vec3d var1;
         if (this.canNavigate()) {
            this.pathFollow();
         } else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
            var1 = this.getEntityPosition();
            Vec3d var2 = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());
            if (var1.y > var2.y && !this.entity.onGround && MathHelper.floor(var1.x) == MathHelper.floor(var2.x) && MathHelper.floor(var1.z) == MathHelper.floor(var2.z)) {
               this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
            }
         }

         this.debugPathFinding();
         if (!this.noPath()) {
            var1 = this.currentPath.getPosition(this.entity);
            BlockPos var4 = (new BlockPos(var1)).down();
            AxisAlignedBB var3 = this.world.getBlockState(var4).getBoundingBox(this.world, var4);
            var1 = var1.subtract(0.0D, 1.0D - var3.maxY, 0.0D);
            this.entity.getMoveHelper().setMoveTo(var1.x, var1.y, var1.z, this.speed);
         }
      }
   }
    * */
    override fun d() {
        ++e
        if (g) {
            k()
        }
        if (!o()) {
            var var1: Vec3D
            if (this.b()) {
                n()
            } else if (c != null && c!!.e() < c!!.d()) {
                var1 = this.c()
                val var2 = c!!.a(a, c!!.e())
                if (var1.y > var2.y && !a.onGround && MathHelper.floor(var1.x) == MathHelper.floor(var2.x) && MathHelper.floor(
                        var1.z
                    ) == MathHelper.floor(var2.z)
                ) {
                    c!!.c(c!!.e() + 1)
                }
            }
            m()
            if (!o()) {
                var1 = c!!.a(a as Entity)
                val var4 = BlockPosition(var1).down()
                val var3 = b.getType(var4).e(b, var4)
                var1 = var1.a(0.0, 1.0 - var3.e, 0.0)
                a.controllerMove.a(var1.x, var1.y, var1.z, d)
            }
        }
    } /*
    public boolean pathEnding() {
        //return this.currentPath != null && this.currentPath.isPathEnding();
        return this.c != null && this.c.b();
    }
    */
}