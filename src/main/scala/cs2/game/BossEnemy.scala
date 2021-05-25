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
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Stack
import scala.annotation.varargs


class BossEnemy(img:Image, img2:Image, initPos:Vec2, player:Player, private val bulletPic:Image) 
                  extends Sprite(img, initPos) with ShootsBullets {
    
    

    var bulletSpeed2 = 12
    override def shoot(): Bullet = {
        val path3 = getClass.getResource("/images/bullet2.png")
        val img3 = new Image(path3.toString)
        new Bullet(img3, this.pos.clone, Vec2(0, bulletSpeed2))
    }

    var life = 15
    
    def deepCopyB():BossEnemy = {
        var copy = new BossEnemy(this.getImage, img2, this.pos.clone, player, bulletPic)
        copy.life = life
        return copy
    }

    def hit():Int = {
        life -= 1
        life
    }

    def returnEnemy(rt:BossEnemy):BossEnemy = {
        return rt
    }

    def getImage():Image = {
        image
    }
    var x1:Double = pos.x 
    //270
    var y1:Double = pos.y
    //60
    var image = img
    def display1(g:GraphicsContext) = {
        var positionX = pos.x
        var positionY = pos.y
        
            if (life > 7) {
                g.drawImage(img, pos.x, pos.y)
            }
            else {
                g.drawImage(img2, pos.x, pos.y) 
            }     
    }
    def displayReversing(g:GraphicsContext) = {
        var positionX = pos.x
        var positionY = pos.y
        
            if (life > 7) {
                g.drawImage(img, pos.x, pos.y)
            }
            else {
                g.drawImage(img2, pos.x, pos.y)
            }     
    }
    var speedOfBoss:Double = 7
    //right player movement
    def putRight() = {
        pos += Vec2(speedOfBoss, 0)
        if(pos.x >= 540) {
            pos.x = 540
        }
    }
    //down player movement
    def putDown() = {
        pos -= Vec2(0, speedOfBoss)
        if(pos.y <= 50) {
            pos.y = 50
        }
    }
    //up player movement
    def putUp() = {
         pos += Vec2(0, speedOfBoss)
        if(pos.y >= 540) {
            pos.y = 540
        }
    }
    //left player movement
    def putLeft() = {
        pos -= Vec2(speedOfBoss, 0)
        if(pos.x <= 0) {
            pos.x = 0
        }
    }
}
