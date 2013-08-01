
package de.fhtrier.gdw.ss2013.renderer;

import java.io.IOException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class DynamicParticleSystem {
    
	private ParticleSystem ps;
	private Vector2f lastPos = null;
	private boolean freeParticles;
	
	public DynamicParticleSystem(String particleSystemRes, boolean freeParticles) throws IOException {
		ps = ParticleIO.loadConfiguredSystem(particleSystemRes);
		this.freeParticles = freeParticles;
	}
	
	private DynamicParticleSystem(DynamicParticleSystem other) {
		try {
			ps = other.ps.duplicate();
		} catch(SlickException e) {
			// todo....
		}
		
		this.freeParticles = other.freeParticles;
	}
    
    @Override
    public DynamicParticleSystem clone() {
		return new DynamicParticleSystem(this);
	}
	
	public void update(int delta) {
		if (ps != null) {
            ps.update(delta);
		}
    }

	public void render() {
        ps.render();
	}
    
    public void setPosition(float x, float y) {
        if(freeParticles && lastPos != null) {
            lastPos.x -= x;
            lastPos.y -= y;
            for (int i = 0; i < ps.getEmitterCount(); i++) {
                ParticleEmitter emitter = ps.getEmitter(i);
                // move particles for delta position so they stay in their trajectory when emitter moves
                ps.moveAll(emitter, lastPos.x, lastPos.y);
            }
        }
        ps.setPosition(x, y);
        
        if(freeParticles) {
            if(lastPos == null)
                lastPos = new Vector2f(x, y);
            else
                lastPos.set(x, y);
        }
	}

}
