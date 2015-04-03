package object.metier;

import java.util.HashMap;
import java.util.Random;

public class PlayerPool {

	private static PlayerPool instance = null;
	private HashMap<Long, PlayerClient> players;
	
	private PlayerPool() {
		players=new HashMap<Long, PlayerClient>();
	}
	
	public static PlayerPool getInstance() {
		if(instance==null) instance=new PlayerPool();
		return instance;
	}
	
	public PlayerClient registerPlayer(String email) {

		/**
		 * build a new player
		 */
		PlayerClient newPlayer=new PlayerClient();;
		newPlayer.setEmail(email);
		/**
		 * clean up the old player in the haspmap if disconnect was not done correctly
		 */
		for(PlayerClient indexPlayer:players.values()) {
			if(indexPlayer.getEmail().equals(email)) {
				players.remove(indexPlayer.getKey());
				break;
			}
		}
		
		/**
		 * compute a new key for the player
		 */
		boolean duplicate=true;
		while(duplicate) {
			newPlayer.setKey(new Random(System.currentTimeMillis()).nextLong());
			duplicate=false;
			for(PlayerClient indexPlayer:players.values()) {
				if(indexPlayer.getKey()==newPlayer.getKey()) {
					duplicate=true;
					break;
				}
			}
		}
		players.put(newPlayer.getKey(), newPlayer);
		return newPlayer;
	}
	
	public PlayerClient getPlayer(long key) {
		return players.get(key);
	}
	
}
