package ayai.apps

import ayai.components.{Position, Bounds}
import ayai.systems.CollisionSystem

import com.artemis.{Entity, World}
import com.artemis.managers.GroupManager
import com.artemis.World
import com.artemis.EntityManager
import net.liftweb.json._


object CollisionApp {
  def main(args: Array[String]) {
    val world: World = new World
    world.setSystem(new CollisionSystem(world))
    world.setManager(new GroupManager)
    world.initialize

    val p: Entity = world.createEntity
    p.addComponent(new Position(10, 10))
    p.addComponent(new Bounds(10, 10))
    p.addToWorld
    world.getManager(classOf[GroupManager]).add(p, "PLAYERS")

    // This Should Collide
    val colE: Entity = world.createEntity
    colE.addComponent(new Position(5, 5))
    colE.addComponent(new Bounds(10, 10))
    colE.addToWorld
    world.getManager(classOf[GroupManager]).add(colE, "ENEMIES")

    // This Should Not Collide
    val ncolE: Entity = world.createEntity
    ncolE.addComponent(new Position(21, 21))
    ncolE.addComponent(new Bounds(10, 10))
    ncolE.addToWorld
    world.getManager(classOf[GroupManager]).add(ncolE, "ENEMIES")

    println("Note: You should see one \"OVERLAP!\" and then \"DONE\"")
    world.process

    // convert all entities in world to JSON of IDs and positions
    val eManager : EntityManager = world.getEntityManager()
    val numEntities = eManager.getActiveEntityCount()
    var entityJString : String = "{"

    for( i <- 0 until numEntities) {
        val tempEntity : Entity = eManager.getEntity(i)
        val tempPos : Position = tempEntity.getComponent(classOf[Position])
        val tempId = tempEntity.getId()
        
        entityJString += "{ playerId = " + tempId + ", position_x = " + tempPos.x + ", position_y = " + tempPos.y + "}"
    }

    entityJString += "}"

    println(entityJString)


    println("DONE")
  }
  
}
