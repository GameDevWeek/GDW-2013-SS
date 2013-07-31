package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.physix.PhysixObject;

/**
 * Ground Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public class GroundEnemy extends AbstractEnemy {
    private float movetime, lasttime, hunttime;
    private boolean intelligence, normalMode, huntMode, waitMode, moveLR;
    private Astronaut p;
    private Image img;
    private AssetLoader a = AssetLoader.getInstance();
    private EntityManager m;
    private World w = World.getInstance();
    private Vector2f lastposition;
    private float speed = 80;

    private String rechts = "animtest", links = "animtest",
            current = "animtest";

    public GroundEnemy() {
        img = a.getImage("groundEnemy");
        /*
         * if (Math.random() >= 0.5) { intelligence = true; } else {
         * intelligence = false; }
         */
        intelligence = false;
        normalMode = true;
        huntMode = waitMode = false;
        hunttime = 0;
        moveLR = false;
        setLeft_animation(links);
        setRight_animation(rechts);
        setCurrent(current);
        p = w.getAstronaut();
        m = w.getEntityManager();
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        Vector2f position = getPosition();
        g.drawImage(img, position.x - (img.getWidth() / 2),
                position.y - (img.getHeight() / 2));
        // g.drawString(this.hashCode(), position.x, position.y);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        p = w.getAstronaut();
//        System.out.println(normalMode + "     " + huntMode + "    " + waitMode);

        if (p != null) {
            // float dt = delta / 1000.f;
            movetime += delta;
            // TODO clamp dt if dt > 1/60.f ?
            if (!intelligence) {
                if (movetime <= 1000) {
                    if (moveLR) {
                        setVelocityX(speed);
                    } else {
                        setVelocityX(-speed);
                    }
                } else {
                    movetime %= 1000;
                    if (moveLR) {
                        moveLR = false;
                    } else {
                        moveLR = true;
                    }
                }
            } else {
                if (normalMode) {
                    if (calcPlayerDistance(p) < 300) {
                        normalMode = false;
                        huntMode = true;
                    } else {
                        if (movetime <= 3000) {
                            if (moveLR) {
                                setVelocityX(speed);
                            } else {
                                setVelocityX(-speed);
                            }
                        } else {
                            movetime %= 3000;
                            if (moveLR) {
                                moveLR = false;
                            } else {
                                moveLR = true;
                            }
                        }
                    }
                } else if (huntMode) {
                    if (hunttime == 0) {
                        lastposition = this.getPosition().copy();
                        lasttime = movetime;
                    }
                    hunttime += delta;
                    if (hunttime > 3000) {
                        huntMode = false;
                        waitMode = true;
                    } else {
                        this.setVelocityX(speed * calcPlayerDirection(p).x);
                    }
                } else if (waitMode) {
                    if (Math.abs(this.getPosition().x - lastposition.x) < 1f) {
                        normalMode = true;
                        waitMode = false;
                        hunttime = 0;
                        this.movetime = lasttime;
                        if (moveLR) {
                            setVelocityX(speed);
                        } else {
                            setVelocityX(-speed);
                        }
                        this.getPosition().x = this.lastposition.x;
                    } else {
                        setVelocityX(speed
                                * (new Vector2f(lastposition.x
                                        - this.getPosition().x, 0.0f)
                                        .normalise().x));
                    }
                }
            }
        }
    }

    private void MoveLR() {
        if (movetime <= 1000) {
            if (moveLR) {
                setVelocityX(speed);
            } else {
                setVelocityX(-speed);
            }
        } else {
            movetime %= 1000;
            if (moveLR) {
                moveLR = false;
            } else {
                moveLR = true;
            }
        }
    }

    private Vector2f calcPlayerDirection(Player player) {
        Vector2f direction = new Vector2f();
        direction = calcPlayerPosition(player);
        direction.normalise();
        return direction;
    }

    private float calcPlayerDistance(Player player) {
        Vector2f direction = new Vector2f();
        direction = calcPlayerPosition(player);
        return (float) Math.sqrt((direction.x * direction.x)
                + (direction.y * direction.y));
    }

    private Vector2f calcPlayerPosition(Player player) {
        Vector2f direction = new Vector2f();
        direction.x = player.getPosition().x - this.getPosition().x;
        direction.y = player.getPosition().y - this.getPosition().y;
        return direction;
    }

    public void setReferences(Astronaut p) {
        this.p = p;
    }

    @Override
    public void beginContact(PhysixObject object) {
        System.out.println("Do collision handling");
    }

    @Override
    public void endContact(PhysixObject object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beginContact(Contact object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endContact(Contact object) {
        // TODO Auto-generated method stub
        
    }
}
