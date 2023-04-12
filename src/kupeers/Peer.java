package kupeers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Peer {

	private static int peersCount;
	private int ID;
	private String peerName;
	private Map<Data, List<Chunk>> chunksMap = new HashMap<Data, List<Chunk>>();
	private boolean status;
	private KuPeers kupeers;

	public Peer(String name, Map<Data, List<Chunk>> initialChunks) {
		this.peerName = name;
		this.status = true;
		peersCount++;
		this.ID = peersCount;
		this.chunksMap = initialChunks;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPeerName() {
		return peerName;
	}

	public void setPeerName(String peerName) {
		this.peerName = peerName;
	}

	public Map<Data, List<Chunk>> getChunksMap() {
		return chunksMap;
	}

	public void setChunksMap(Map<Data, List<Chunk>> chunksMap) {
		this.chunksMap = chunksMap;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public KuPeers getKupeers() {
		return kupeers;
	}

	public void setKupeers(KuPeers kupeers) {
		this.kupeers = kupeers;
	}

	public void joinNetwork(KuPeers network) {
		this.kupeers = network;
		network.registerPeer(this);
	}

	public boolean myHasChunk(Data data, Chunk chunk) {
		if (this.getChunksMap().containsKey(data)) {
			return this.getChunksMap().get(data).contains(chunk);
		}
		return false;
	}

	public void receiveChunk(Data data, Chunk chunk) {
		LinkedList<Chunk> linkedListChunk = new LinkedList<Chunk>();
		for (Data d : chunksMap.keySet()) {
			if (d == data)
				chunksMap.get(data).add(chunk);
			else
				linkedListChunk.add(chunk);
		}
		if (linkedListChunk.isEmpty())
			chunksMap.put(data, linkedListChunk);
	}

	public boolean myDownloadCopy(Chunk chunk) {
		for (Peer s : chunk.getPeers()) {
			if (chunk.copyChunk(s, this))
				return true;
			else
				continue;
		}
		return false;
	}

	public boolean downloadData(String name) {
		if (!kupeers.getPeersList().contains(this)) {
			System.out.println("Peer hasn't joined the network yet, download aborted.");
			return false;
		}

		for (Data d : kupeers.getDataMap().values()) {
			String str = "no";
			if (d.getName() != name) {
				str = "yes";
				break;
			}
			if (str == "yes") {
				System.out.println("Data does not exist in the network, download aborted");
			}

		}
		boolean b = true;
		Data d = kupeers.getDataMap().get(name);

		if (d.getName() == name) {
			for (Chunk c : d.getChunks()) {
				if (myHasChunk(d, c))
					continue;
				else {
					b = myDownloadCopy(c);
				}
			}
		}
		return b;
	}

	public Map<Data, List<Chunk>> findCommonChunks(Peer p) {
		Map<Data, List<Chunk>> newMap = new LinkedHashMap<Data, List<Chunk>>();
		List<Chunk> newList = new ArrayList<Chunk>();
		for (List<Chunk> l : chunksMap.values()) {
			for (Chunk c : l) {
				for (List<Chunk> ll : p.chunksMap.values()) {
					for (Chunk cc : ll) {
						if (c == cc) {
							newList.add(c);
							newMap.put(c.getChunkData(), newList);
						}
					}
				}	
			}
		}
		return newMap;
	}
	
	public void sortChunks(Data data) {
		for(List<Chunk> l : chunksMap.values()) {
			for (Chunk c : l) {
				c.compareTo(c);
			}
		}
	}

	@Override
	public String toString() {

		System.out.println("peer#" + ID + "\nName: " + peerName + "\nStatus: " + (status ? "Online" : "Offline") + "\n"
				+ "\nChunks:\n-------");
		for (Data d : chunksMap.keySet()) {
			System.out.println(d + "\n");
		}
		return "";
	}
}