package cs2.game

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
import scala.collection.mutable.Queue



/** main object that initiates the execution of the game, including construction
 *  of the window.
 *  Will create the stage, scene, and canvas to draw upon. Will likely contain or
 *  refer to an AnimationTimer to control the flow of the game.
 */

object SpaceGameApp extends JFXApp {
    case class prevState(Player2:Player, EnemySwarmPosition2:EnemySwarm, 
    PlayerBulletPosition2:Buffer[Bullet], EnemyBulletPositon2:Buffer[Bullet], 
    Boss1:BossEnemy, BossBulletPositon2:Buffer[Bullet])

    var GameStateStack = Stack[prevState]()
    var showStartScreen:Boolean = true
    var playerBullets = Buffer[Bullet]()
    var enemyBullets:Buffer[Bullet] = new ListBuffer[Bullet]()
    var bossBullets:Buffer[Bullet] = new ListBuffer[Bullet]()
    var s = scala.collection.mutable.Set[KeyCode]()
    var r = new scala.util.Random
    var explosions:ListBuffer[Explode] = ListBuffer[Explode]()
    //var bosses:ListBuffer[BossEnemy] = ListBuffer[BossEnemy]()
    var bossAlive = false
    var removal:ListBuffer[BossEnemy] = ListBuffer[BossEnemy]()
    var bossMove:Queue[scala.collection.mutable.Set[KeyCode]] = Queue[scala.collection.mutable.Set[KeyCode]]()
    var oldSet = scala.collection.mutable.Set[KeyCode]()
    
    stage = new JFXApp.PrimaryStage {
        title = "Galaga!"
        scene = new Scene(600,600) {
            val canvas = new Canvas(600,600)
            content = canvas
            val g = canvas.graphicsContext2D
            
            val path = getClass.getResource("/images/player.png")
            val img = new Image(path.toString)
            //var player1 = new Player(img, new Vec2(280, 500), img3)

            val path2 = getClass.getResource("/images/enemy.png")
            val img2 = new Image(path.toString)

            val path3 = getClass.getResource("/images/bullet.png")
            val img3 = new Image(path.toString)
            var player1 = new Player(img, new Vec2(280, 500), img3)

            val path4 = getClass.getResource("/images/enemy2.png")
            val img4 = new Image(path4.toString)
            
            //val path4 = getClass.getResource("/images/bullet.png")
            //val img4 = new Image(path.toString)

            var enemies = new EnemySwarm(2, 3, img4, img3)
            var bullets = Buffer[Bullet]()
            var boss1:BossEnemy = new BossEnemy(new Image("/images/enemyBossSquareLarge.png"),
                                    new Image("/images/enemyBossSquareLarge2.png"),
                                    new Vec2((player1.pos.x - 30), (player1.pos.y - 30) - 400),
                                    player1, img3)
            
            

            canvas.onKeyPressed = (e:KeyEvent) => {
                if (e.code == KeyCode.Up) {
                    s += KeyCode.Up
                }
                if (e.code == KeyCode.Right) {
                     
                    s += KeyCode.Right
                }
                if (e.code == KeyCode.Left) {
                     
                    s += KeyCode.Left
                }
                if (e.code == KeyCode.Down) {
                     
                    s += KeyCode.Down
                }
                if (e.code == KeyCode.A) {
                     
                    s += KeyCode.A
                }
                if (e.code == KeyCode.D) {
                   
                    s += KeyCode.D
                }
                if(e.code == KeyCode.Space) {
                   player1.shoot
                   s += KeyCode.Space
                }
                if (e.code == KeyCode.S) {
                   
                    s += KeyCode.S
                }
                if (e.code == KeyCode.W) {
                   player1.shoot
                    s += KeyCode.W
                }
                if (e.code == KeyCode.N) {
                    s += KeyCode.N
                }
                if (e.code == KeyCode.P) {
                    s += KeyCode.P
                }
                if (e.code == KeyCode.R) {
                    s += KeyCode.R
                }
                if (e.code == KeyCode.Q) {
                    s += KeyCode.Q
                }
            }
            canvas.onKeyReleased = (e:KeyEvent) => {
                if (e.code == KeyCode.Up) {
                    s -= (KeyCode.Up)
                }
                if (e.code == KeyCode.Down) {
                    s -= (KeyCode.Down)
                }
                if (e.code == KeyCode.Right) {
                    s -= (KeyCode.Right)
                }
                if (e.code == KeyCode.Left) {
                    s -= (KeyCode.Left)
                }
                if (e.code == KeyCode.A) {
                    s -= (KeyCode.A)
                }
                if (e.code == KeyCode.D) {
                    s -= (KeyCode.D)
                }
                if (e.code == KeyCode.Space) {
                    s -= (KeyCode.Space)
                }
                if (e.code == KeyCode.W) {
                    s -= (KeyCode.W)
                }
                if (e.code == KeyCode.S) {
                    s -= (KeyCode.S)
                }
                if (e.code == KeyCode.N) {
                    s-= (KeyCode.N)
                }
                if (e.code == KeyCode.P) {
                    s-= (KeyCode.P)
                }
                if (e.code == KeyCode.R) {
                    s-= (KeyCode.R)
                }
                if (e.code == KeyCode.Q) {
                    s-= (KeyCode.Q)
                }
            }
            canvas.requestFocus
            var countdown2 = 150
            var countdown = 100
            var countdown3 = 100
            var frames = 0.0
            var delay4Boss = 0
            var framesBossIsAlive = 0
            
            

            var moveToPos:Vec2 = new Vec2(300.0, 540.0)
            var oldT = 0L
            //timer
            val timer = AnimationTimer (t => {

                
                //start screen
                if (showStartScreen:Boolean) {
                    g.setFill(Color.Black)
                    g.fillRect(0, 0, 600, 600)
                    g.setFill(Color.White)
                    g.fillText("Press P to Start a New Game!", 230, 275)
                    g.fillText("Press Q to Quit the Game.", 240,300)
                    player1.display(g)
                    //player1.pExplo.display(g)
                    enemies.display(g)
                    if (s.contains(KeyCode.P)) {
                        showStartScreen = false
                    }
                    if(s.contains(KeyCode.Q)) {
                        stage.close()
                    }
                    
                }
                
                //reversing
                else if (s.contains(KeyCode.R) && frames > 0) {
                        if (enemies.swarm1 != 0) {
                            bossAlive = false
                        }
                        val oldGS = GameStateStack.pop()
                        player1 = oldGS.Player2
                        enemies = oldGS.EnemySwarmPosition2
                        enemyBullets = oldGS.EnemyBulletPositon2
                        playerBullets = oldGS.PlayerBulletPosition2
                        boss1 = oldGS.Boss1
                        bossBullets = oldGS.BossBulletPositon2
                        frames -= 1
                        var fixedFrames = frames + 1
                        //display
                        g.setFill(Color.Black)
                        g.fillRect(0, 0, 600, 600)
                        g.setFill(Color.White)
                        g.fillRect(0,0,600,50)
                        g.setFill(Color.Red)
                        g.fillText("Score: " + player1.score1, 10, 30)
                        g.fillText("Lives: " + player1.lives, 500, 30)
                        player1.display(g)
                        enemies.display(g)
                        if (enemies.size == 0) {
                            boss1.displayReversing(g)
                            bossBullets.foreach(_.display(g))
                        }
                        bullets.foreach(_.display(g))
                        playerBullets.foreach(_.display(g))
                        enemyBullets.foreach(_.display(g))
                        explosions.foreach(_.reverseTimeStep())
                        explosions.foreach(_.display(g))
                        //bar will go here
                        g.setFill(Color.Grey)
                        g.fillRect(5,5,100,10)
                        g.setFill(Color.Red)
                        g.fillRect(6,6, 0 + (fixedFrames/1200.0) * 100, 9)  
                }
                //game
                else if (player1.lives > 0) {
                var toRemove4 = Buffer[Bullet]()
                countdown2 -= 1
                countdown -= 1
                countdown3 -= 1
                if(t-oldT > 1e9/60) {
                    oldT = t
                    g.setFill(Color.Black)
                    g.fillRect(0, 0, 600, 600)
                    g.setFill(Color.White)
                    g.fillRect(0,0,600,50)
                    g.setFill(Color.Red)
                    g.fillText("Score: " + player1.score1, 10, 30)
                    g.fillText("Lives: " + player1.lives, 500, 30)
                    player1.display(g)
                    enemies.display(g)
                    bullets.foreach(_.timeStep)
                    bullets.foreach(_.display(g))
                    playerBullets.foreach(_.timeStep)
                    playerBullets.foreach(_.display(g))
                    enemyBullets.foreach(_.timeStep)
                    enemyBullets.foreach(_.display(g))
                    explosions.foreach(_.display(g))
                    explosions.foreach(_.timestep())

                    //bar will go here
                    g.setFill(Color.Grey)
                    g.fillRect(5,5,100,10) 
                    g.setFill(Color.Red)
                    g.fillRect(6,6, 0 + (frames/1200.0) * 100, 9)

                    if (player1.lives > 0) {
                    enemies.movement1()
                    }

                    //displaying explosions 
                    if (!s.contains(KeyCode.R)) {
                        explosions.foreach(_.timestep())
                    }
                    explosions.foreach(_.display(g))
                    for (explosion <- explosions) {
                        if (explosion.frames > 1800 ) explosions -= explosion
                        if (explosion.frames < 0 ) explosions -= explosion
                    }

                    for (enemy <- enemies.swarm1) {
                        if (enemy.intersect(player1)) {
                            player1.hit
                            player1.moveBack
                        }
                    }
                    //boss bullets intersecting player 
                    for (bullet <- bossBullets) {
                        if (bullet.intersect(player1)) {
                            player1.hit 
                            player1.moveBack
                            bossBullets -= bullet
                            explosions += new Explode(new Image("/images/spritesheet.png"), player1.Position.clone,
                                            new Vec2(player1.getImage().width.value, player1.getImage().height.value))
                        }
                    }
                    
                    //enemybullet intersections
                    for (bullet <- enemyBullets) {
                        if (bullet.intersect(player1)) {
                            player1.hit
                            //player explosions 
                            explosions += new Explode(new Image("/images/spritesheet.png"), player1.Position.clone,
                                            new Vec2(player1.getImage().width.value, player1.getImage().height.value))
                            
                            player1.moveBack
                            enemyBullets -= bullet
                        }
                    
                        
                        if (bullet.Position.y > 600) {
                            enemyBullets -= bullet 
                        }
                        for (bulletP <- playerBullets) {
                            for(bulletE <- enemyBullets) {
                                if (bullet.intersect(bulletP)) {
                                    enemyBullets -= bullet
                                    playerBullets -= bullet
                                }
                            }   
                        //attempt at deleting the player bullet when intersects Ebullet 
                            for (bulletP <- playerBullets) {
                                if (bulletP.intersect(bullet)) {
                                    explosions += new Explode(new Image("/images/spritesheet.png"), bulletP.Position.clone,
                                            new Vec2(bullet.getImage().width.value, bullet.getImage().height.value))
                                    toRemove4 += bulletP
                                }
                            }
                            
                        }
                    }
                    playerBullets --= toRemove4
                    
                    //player bullet intersection
                    var toRemove2 = Buffer[Bullet]()
                    var toRemove = Buffer[Enemy]()
                    var toRemove3 = Buffer[Bullet]()
                    var toRemovePBB = Buffer[Bullet]()
                    var toRemoveBBB = Buffer[Bullet]()
                    //deleting both the boss and player bullets when they intersect 
                    if (bossAlive == true) {
                        for (bulletP <- playerBullets) {
                            for (bulletB <- bossBullets) {
                                if (bulletP.intersect(bulletB)) {
                                    toRemovePBB += bulletP
                                    explosions += new Explode(new Image("/images/spritesheet.png"), bulletP.Position.clone,
                                            new Vec2(bulletP.getImage().width.value, bulletP.getImage().height.value))
                                }
                                if (bulletB.intersect(bulletP)) {
                                    toRemoveBBB += bulletB
                                }
                            }
                        }
                    }
                    playerBullets --= toRemovePBB
                    bossBullets --= toRemoveBBB

                    //player bullets intersecting the boss
                    if (bossAlive == true) {
                        for (bullet <- playerBullets) {
                            if (bullet.intersect(boss1)) {
                                boss1.hit
                                explosions += new Explode(new Image("/images/spritesheet.png"), boss1.pos.clone,
                                            new Vec2(boss1.getImage().width.value, boss1.getImage().height.value))
                                toRemove2 += bullet
                            }
                        }
                    }
                    //here
                    bullets --= toRemove3

                    for (bullet <- playerBullets) {
                        if(bullet.Position.y < 50) {
                            toRemove2 += bullet
                        }
                        for (i <- 0 until enemies.size) {
                            if (bullet.intersect(enemies.swarm1(i))) {
                                toRemove2 += bullet
                                //explosions for enemies
                                explosions += new Explode(new Image("/images/spritesheet.png"), enemies.swarm1(i).pos,
                                                new Vec2(enemies.swarm1(i).getImage().width.value, enemies.swarm1(i).getImage().height.value))
                                player1.score
                                toRemove += enemies.swarm1(i)
                            }
                        }
                         enemies.swarm1--= toRemove
                    }
                    playerBullets --= toRemove2
                    

                    //restricting the rate of fire of enemies
                    if (enemies.swarm1.length != 0) {
                        if (countdown2 <= 0 && scala.util.Random.nextInt(20) == 7) {
                            var thebullet = enemies.shoot
                            if (thebullet != null) {
                                enemies.shoot
                                enemyBullets += thebullet
                                countdown2 = 150
                            }
                        }
                    }
                    var preventTwice = true
                    var realLife = 15
                    var testLife = 2
                    //respawning the enemy swarm and boss
                    //change float to 5 percent or so
                    if (r.nextFloat < .05 && enemies.size == 0 && preventTwice == true) {
                        bossAlive = true
                        //boss1.life = testLife
                        preventTwice = false
                    }
                    if (bossAlive == true && !s.contains(KeyCode.R)) {
                            //display boss here 
                            boss1.display1(g)
                            bossBullets.foreach(_.timeStep)
                            bossBullets.foreach(_.display(g))
                            framesBossIsAlive += 1
                    }
                    else if(enemies.size == 0 && (r.nextFloat != 1)) {
                        enemies = new EnemySwarm(2, 3, img4, img3)
                    }
                    if (boss1.life == 0) {
                        bossAlive = false
                        boss1.life = realLife
                        player1.scoreBoss
                        preventTwice = true 
                        explosions += new Explode(new Image("/images/spritesheet.png"), boss1.pos.clone,
                                            new Vec2(boss1.getImage().width.value + 60, boss1.getImage().height.value + 40))
                        explosions += new Explode(new Image("/images/spritesheet.png"), boss1.pos.clone,
                                            new Vec2(boss1.getImage().width.value, boss1.getImage().height.value))
                        explosions += new Explode(new Image("/images/spritesheet.png"), boss1.pos.clone,
                                            new Vec2(boss1.getImage().width.value + 50, boss1.getImage().height.value + 50))
                    }

                    //keyCodes for movements and shooting 
                    if (s.contains(KeyCode.Up)) {
                        player1.moveUp
                    }
                    if (s.contains(KeyCode.Down)) {
                        player1.moveDown
                    }
                    if (s.contains(KeyCode.Left)) {
                        player1.moveLeft
                    }
                    if (s.contains(KeyCode.Right)) {
                        player1.moveRight
                    }
                    if (s.contains(KeyCode.A)) {
                        player1.moveLeft
                    }
                    if (s.contains(KeyCode.D)) {
                        player1.moveRight
                    }
                    if (s.contains(KeyCode.W)) {
                        player1.moveUp
                    }
                    if (s.contains(KeyCode.S)) {
                        player1.moveDown
                    }
                    if (s.contains(KeyCode.Space)) {
                            if (countdown <= 0) {
                                player1.shoot
                                playerBullets += player1.shoot
                                countdown = 100
                            }
                            else {
                                null
                            }
                    }
                }
                countdown -= 1 
                countdown2 -= 1
                countdown3 -= 1
                delay4Boss += 1
                if (bossAlive == true) {
                    bossMove.enqueue(s.clone())
                }
                if (bossMove.length == 40) {
                    var tmp = (bossMove.dequeue)
                    oldSet = tmp
                }
                //Boss moves
                    if (oldSet.contains(KeyCode.Up)) {
                        boss1.putUp
                    }
                    if (oldSet.contains(KeyCode.Down)) {
                        boss1.putDown()
                    }
                    if (oldSet.contains(KeyCode.Left)) {
                        boss1.putLeft
                    }
                    if (oldSet.contains(KeyCode.Right)) {
                        boss1.putRight
                    }
                    if (oldSet.contains(KeyCode.W)) {
                        boss1.putUp
                        //println("hey")
                    }
                    if (oldSet.contains(KeyCode.S)) {
                         boss1.putDown()
                    }
                    if (oldSet.contains(KeyCode.A)) {
                        boss1.putLeft
                    }
                    if (oldSet.contains(KeyCode.D)) {
                        boss1.putRight
                    }
                    if (oldSet.contains(KeyCode.Space)) {
                        if (countdown3 <= 0) {
                                boss1.shoot
                                bossBullets += boss1.shoot
                                countdown3 = 100
                            }
                            else {
                                null
                            }
                    }

                var NGS = new prevState(Player2 = player1.DeepCopyP, EnemySwarmPosition2 = enemies.clone, 
                PlayerBulletPosition2 = playerBullets.map(_.clone()), EnemyBulletPositon2 = enemyBullets.map(_.clone()), 
                Boss1 = boss1.deepCopyB, BossBulletPositon2 = bossBullets.map(_.clone()))

                GameStateStack.push(NGS)
                if (frames <= 1200) {
                frames += 1
                }
            }
            
            //gameover screen
            else {
                g.setFill(Color.Black)
                g.fillRect(0, 0, 600, 600) 
                g.setFill(Color.White)
                g.fillText("You Lose, Press N to Start a New Game!", 190, 275)
                g.fillText("Press Q to Quit the Game.", 230,300)
                if (s.contains(KeyCode.N)) {
                    player1.lives = 5
                    player1.score1 = 0  
                    bossAlive = false
                    playerBullets.clear()
                    enemyBullets.clear()
                    bossBullets.clear()
                    explosions.clear()
                    enemies = new EnemySwarm(2, 3, img4, img3)
                }
                if(s.contains(KeyCode.Q)) {
                    stage.close()
                }
            }
            }) 
            timer.start
            
        }
    }
}

