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


class Explode (img:Image, pos:Vec2, enemyWH:Vec2) extends Sprite (img, pos){
//1: is the leading point 
    var x1 = 64
    var x2 = 0 
    var y2 = 0
    var y1 = 64
    var expCounter = 0
    var frames = 0

    def framesPassed():Int = {
        frames
    }

    def timestep():Unit = {
        frames += 1
    }
    def reverseTimeStep():Unit = {
        frames -= 1
    }
    
    override def display(g:GraphicsContext):Unit = {
        var xValue = framesPassed
        var yValue = 0
        while (xValue >= 8) {
            xValue -= 8
            yValue += 1
        } 
        g.drawImage(img, xValue * img.width.value/8, yValue * img.height.value/6, img.width.value/8, img.height.value/6, 
                    pos.x, pos.y, enemyWH.x, enemyWH.y)
    }
    
}

/*
def ex():Unit = {
    for (i <- 64 until 3072) {
        for (j <- 0 until 3008) {
            g.drawImage(enemies.swarm1.eExplo.picture, j,y3,i,y4, enemies.swarm1.pos.x, enemies.swarm1.pos.y, 7, 7)
            i + 64
            j + 64
            expCounter2 += 1
            if (expCounter2 == 8 || expCounter2 % 8 == 0) {
                y3 += 64
                y4 += 64
            }
        }
    }
}
*/
