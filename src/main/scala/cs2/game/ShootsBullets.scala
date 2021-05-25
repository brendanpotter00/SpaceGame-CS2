package cs2.game

/** a trait representing the ability/requirement of an extending class to have 
 *  a shoot method that returns a newly created Bullet object
 */
trait ShootsBullets {
  
  /** an abstract method that must be overridden in order to instantiate an
   *  inheriting class (no edits are required here)
   * 
   *  @return Bullet - newly created Bullet object that is fired
   */
  def shoot():Bullet
   
}