package kupeers;

import java.util.LinkedHashSet;
import java.util.Set;

public class Chunk  implements Comparable<Chunk> {

	private Set<Peer> peers;
	private String sentence;
	private int id;
	private Data chunkData;

	public Chunk(String text, int id, Data data) {
		this.sentence = text;
		this.id = id;
		this.chunkData = data;
		this.peers = new LinkedHashSet<Peer>();
	}

	public Set<Peer> getPeers() {
		return peers;
	}

	public void setPeers(Set<Peer> peers) {
		this.peers = peers;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public int getId() {
		return id;
	}

	public void setId(int index) {
		this.id = index;
	}

	public Data getChunkData() {
		return chunkData;
	}

	public void setChunkData(Data chunkData) {
		this.chunkData = chunkData;
	}

	public void addLocation(Peer p) {
		peers.add(p);
	}

	public boolean availableAt(Peer p) {
		if (p.getChunksMap().containsKey(chunkData)) {
			return p.getChunksMap().get(chunkData).contains(this);
		}
		return false;
	}

	public String getText(Peer p) {
		if (availableAt(p))
			return sentence;
		System.out.println("You do not have this chunk, please download it first.");
		return null;
	}

	public boolean copyChunk(Peer src, Peer dest) {
		if (src.isStatus() == true && dest.isStatus() == true && src.getChunksMap().containsKey(chunkData)) {
			dest.receiveChunk(chunkData, this);
		}
		return false;
	}
	
	public int compareTo(Chunk ch){
		if (this.id < ch.id) return -1;
		if (this.id > ch.id) return 1;
		return 0;
	}
	
	public void sortChunks(Data data) {
		for (Chunk c : data.getChunks()) {
			compareTo(c);
		}
	}
}