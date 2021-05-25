package cs2.game

import scalafx.scene.canvas.GraphicsContext
import cs2.util.Vec2  
import scalafx.scene.image.Image
import scala.collection.mutable
import java.nio.Buffer

/** contains the control and logic to present a coordinated set of Enemy objects.
 *  For now, this class generates a "grid" of enemy objects centered near the 
 *  top of the screen.
 *  
 *  @param nRows - number of rows of enemy objects
 *  @param nCols - number of columns of enemy objects
 */
class EnemySwarm(private val nRows:Int, private val nCols:Int, private val enemyPicture:Image, private val bulletPicture:Image) extends ShootsBullets {
	
	/** method to display all Enemy objects contained within this EnemySwarm
	 * 
	 *  @param g - the GraphicsContext to draw into
	 *  @return none/Unit
	 */
  
 
  var swarm1 = mutable.Buffer.tabulate[Enemy](nCols)((i => new Enemy(enemyPicture, new Vec2((i+1) * (150), 200), bulletPicture)))
  swarm1 ++= mutable.Buffer.tabulate[Enemy](nCols)((i => new Enemy(enemyPicture, new Vec2((i+1) * (150), 100), bulletPicture)))
  
  def movement1() = {
    this.swarm1.map(_.movement())
    //swarm1.foreach(_.movement)
    //println("him")
  }
  override def clone():EnemySwarm = {
    var copy = new EnemySwarm(nRows, nCols, enemyPicture, bulletPicture)
    copy.swarm1= this.swarm1.map(_.clone())
    //println("him")
    return copy
  }

  def returnEnemy():Enemy = {
    ???
  }

	def display(g:GraphicsContext) { 
    for(enemies <- swarm1){
      enemies.display(g)
    }
  }

  def size() = {
    swarm1.length
  }
  
  /** overridden method of ShootsBullets. Creates a single, new bullet instance 
   *  originating from a random enemy in the swarm. (Not a bullet from every 
   *  object, just a single from a random enemy)
   *  
   *  @return Bullet - the newly created Bullet object fired from the swarm
   */
  def shoot():Bullet = { 
      var shooter = scala.util.Random.nextInt(swarm1.length) 
      swarm1(shooter).shoot
      
    }
    object eExplo {
      val path = getClass.getResource("/images/spritesheet.png")
      val img = new Image(path.toString)
  }

}
  
