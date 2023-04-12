package kupeers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Data {
	
	private String name;
	private int dataSize;
	private String dataText;
	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	public Data(String name, String text) {
		this.name = name;
		this.dataText = text;
		setChunksAndSize();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public String getDataText() {
		return dataText;
	}

	public void setDataText(String dataText) {

		this.dataText = dataText;
	}
	
	public List<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(List<Chunk> chunks) {
		this.chunks = chunks;
	}

	private void setChunksAndSize() {

		String[] nameArr = dataText.split("\\.\s");
		dataSize = nameArr.length;

		for (String n : nameArr) {
			int IDs = 0;
			Chunk newChunkObject = new Chunk(n, IDs, this);
			chunks.add(newChunkObject);
			IDs++;
		}
	}

	@Override
	public String toString() {
		
//		List<Integer> intList = new LinkedList<Integer>();
//		for (Chunk c : chunks) {
//			for (Peer p : c.getPeers()) {
//				for (List<Chunk> l : p.getChunksMap().values()) {
//					for (Chunk cc : l) {
//						intList.add(cc.getId());
//					}
//				}
//			}
//		}
		List<Integer> intList = new LinkedList<Integer>();
		for (Chunk c : chunks) {
			intList.add(c.getId());
		}
		
		return "Data Name: " + name + "\nData is composed of " + dataSize + " chunks."
				+ "\nChunks the peer has (1st index is 0): " + intList;

		// son satırda for loopla chunk'ın indexini yazdır
		// concat kullan
		// peer > list of chunk > chubks ıd
	}
}