package kupeers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KuPeers {
	
	private List<Peer> peersList = new ArrayList<Peer>();
	private Map<String, Data> dataMap;
	
	public KuPeers(Data[] data) {
		
		this.dataMap = new HashMap<String, Data>();
		for (Data d : data) {
		    dataMap.put(d.getName(), d);
		}  
		
		this.peersList = new LinkedList<Peer>();
	}
	
	public List<Peer> getPeersList() {
		return peersList;
	}

	public void setPeersList(List<Peer> peersList) {
		this.peersList = peersList;
	}

	public Map<String, Data> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Data> dataMap) {
		this.dataMap = dataMap;
	}

	public boolean dataExists(String name) {
		if (dataMap.containsKey(name)) return true; 
		return false;
	}
	
	public Data getDataInfo(String name) {
		if (dataMap.containsKey(name)) return dataMap.get(name);
		return null;
	}
	
	public void registerPeer(Peer p) {
		peersList.add(p);
		addPeerChunksToNetwork(p);
	}
	
	private void addPeerChunksToNetwork(Peer p) {
		for (List<Chunk> l : p.getChunksMap().values()) {
			for (Chunk c : l) {
				c.addLocation(p);
			}
		}
	}
}