package com.cogworks.gaslib.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class GasolineDripParticle extends TextureSheetParticle {
    private final float initialSize;

    protected GasolineDripParticle(ClientLevel level, double x, double y, double z,
                                   double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z, xd, yd, zd);
        this.lifetime = 40;
        this.initialSize = 0.14f;
        this.quadSize = this.initialSize;
        this.gravity = 0.06f;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.quadSize = this.initialSize;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level,
                                       double x, double y, double z,
                                       double xd, double yd, double zd) {
            return new GasolineDripParticle(level, x, y, z, xd, yd, zd, sprites);
        }
    }
}