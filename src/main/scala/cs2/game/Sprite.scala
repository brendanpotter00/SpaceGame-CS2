package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.scene.canvas.GraphicsContext
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
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image
import scalafx.scene.shape.Rectangle
import scala.collection.mutable.Buffer



/** A graphical sprite object used for gaming or other visual displays
 *  
 *  @constructor create a new sprite based on the given image and initial location
 *  @param img the image used to display this sprite
 *  @param pos the initial position of the '''center''' of the sprite in 2D space
 */
abstract class Sprite (protected var img:Image, var pos:Vec2) {

  /** moves the sprite a relative amount based on a specified vector
   *  
   *  @param direction - an offset that the position of the sprite should be moved by
   *  @return none/Unit
   */
  def move (direction:Vec2) {
    pos += direction
   }
  
  /** moves the sprite to a specific location specified by a vector (not a relative movement)
   *  
   *  @param location - the new location for the sprite's position
   *  @return none/Unit
   */
  def moveTo (location:Vec2) { 
   pos = location
  }
  /** Method to display the sprite at its current location in the specified Graphics2D context
   *  
   *  @param g - a GraphicsContext object capable of drawing the sprite
   *  @return none/Unit
   */

   
  def intersect(other:Sprite):Boolean = {
    var thisPos = this.pos
    var otherPos = other.pos
    
        
    if (((otherPos.x < thisPos.x + this.img.width.value) && (otherPos.x + other.img.width.value) > (thisPos.x)) &&
    (otherPos.y < thisPos.y + this.img.height.value) && ((otherPos.y + other.img.height.value) > (thisPos.y))) {    
        
          return true
    }
       //need to remove from buffer
        //need to get image from app
    
    else {return false} 
  
  }
  
  
  def display (g:GraphicsContext):Unit =  { 
    g.drawImage (this.img, pos.x, pos.y)
  }
  }