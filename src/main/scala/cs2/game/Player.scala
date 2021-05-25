package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import cs2.util.Vec2
import scalafx.scene.paint.Color
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import java.{util => ju}
import scala.collection.mutable


/** The player representation for a simple game based on sprites. Handles all 
 *  information regarding the player's positions, movements, and abilities.
 *  
 *  @param avatar the image representing the player
 *  @param initPos the initial position of the '''center''' of the player
 *  @param bulletPic the image of the bullets fired by this player
 */
class Player(avatar:Image, initPos:Vec2, private val bulletPic:Image) 
                extends Sprite(avatar, initPos) with ShootsBullets {

  /** moves the player sprite one "step" to the left.  The amount of this 
   *  movement will likely need to be tweaked in order for the movement to feel 
   *  natural.
   *  
   *  @return none/Unit
   */


  var bulletSpeed:Int = -10 /*-30*/
  var lives = 5
  var score1:Int = 0
  
  def DeepCopyP():Player = {
    var copy = new Player(avatar, this.pos.clone, bulletPic)
    copy.score1 = score1
    copy.lives = lives
    return copy
  }
  var speedOfPlayer:Double = 24
  def moveLeft() { 
    pos -= Vec2(speedOfPlayer, 0)
    if(pos.x <= 0) {
      pos.x = 0
    }
  }
                  
  /** moves the player sprite one "step" to the right (see note above)
   * 
   *  @return none/Unit
   */

  def moveRight() { 
    pos += Vec2(speedOfPlayer, 0)
    if(pos.x >= 540) {
      pos.x = 540
    }
  }
  def moveDown() {
    pos += Vec2(0, speedOfPlayer)
    if(pos.y >= 540) {
      pos.y = 540
    }
  }
  def moveUp() {
    pos -= Vec2(0, speedOfPlayer)
    if(pos.y <= 50) {
      pos.y = 50
    }
  }
  /** creates a new Bullet instance beginning from the player, with an 
   *  appropriate velocity
   * 
   *  @return Bullet - the newly created Bullet object that was fired
   */
  def shoot():Bullet = { 
    val path2 = getClass.getResource("/images/bullet2.png")
    val img2 = new Image(path2.toString)
    new Bullet(img2, this.pos.clone, Vec2(0, bulletSpeed)) 
     
  } 
  
  def getImage():Image = {
    avatar
  }

  def score() = {
    score1 = (score1 + 50).toInt
  }

  def scoreBoss() = {
    score1 = (score1 + 1000).toInt
  }

  def hit() = {
    lives = (lives - 1).toInt
  }

  def moveBack() = {
    pos += new Vec2(300 - pos.x, 540 - pos.y)
  }

  def Position():Vec2 = {
    pos
  }
  object pExplo {
      val path = getClass.getResource("/images/spritesheet.png")
      val img = new Image(path.toString)
  }
  
}
/*object pExplosion {
  val path = getClass.getResource("/images/spritesheet.png")
  val img = new Image(path.toString)
}
*/
                            