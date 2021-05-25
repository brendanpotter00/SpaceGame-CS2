package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2

/** An enemy representation for a simple game based on sprites. Handles all 
 *  information regarding the enemy's position, movements, and abilities.
 *  
 *  @param pic the image representing the enemy
 *  @param initPos the initial position of the '''center''' of the enemy
 *  @param bulletPic the image of the bullets fired by this enemy
 */
class Enemy(pic:Image, initPos:Vec2, private val bulletPic:Image) 
                  extends Sprite(pic, initPos) with ShootsBullets {
var bulletspeed = 20
  /** creates a new Bullet instance beginning from this Enemy, with an appropriate velocity
   * 
   *  @return Bullet - the newly created Bullet object that was fired
   */
  var bulletSpeed1 = 10
  def shoot():Bullet = { 
    val path2 = getClass.getResource("/images/bullet2.png")
    val img2 = new Image(path2.toString)
    new Bullet(img2, this.pos.clone, Vec2(0, bulletSpeed1))
  }
  
  def getImage():Image = {
    pic
  }

  def position():Vec2 = {
    pos
  }

  override def clone():Enemy = {
  var copy = new Enemy(pic, this.pos.clone(), bulletPic)
  return copy
  }

  //counter and use foreach in the timer the loop will be the timer 
  var counter = 0
  var times = 0
  def movement() = {
    
    if (counter <= 50) { 
      this.pos += Vec2(0, 1.0)
      counter += 1
      //println("now")
    }
    else if (counter < 100)  { 
      this.pos -= Vec2(1.0, 0)
      counter += 1
    }
    else if (counter < 150) {
      this.pos -= Vec2(0, 1.0)
      counter += 1
    }
    else if (counter < 200) {
      this.pos += Vec2(1.0, 0)
      counter += 1
      times += 1
    }
    else {counter = 0}
    
  } 
  
}


