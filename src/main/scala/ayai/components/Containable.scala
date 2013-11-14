package ayai.components

import com.artemis.Component
import ayai.gamestate.Effect
/*
* This is the item class essentially, will tell whether an item 
* can be held by a player
*/
class Containable(var id : Int, var name : String, var effects : List[Effect] ) extends Component {
	
	def addEffect(effect : Effect) {
		effects.add(effect)
	}

}