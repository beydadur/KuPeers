/* *********** Pledge of Honor ************************************************ *

I hereby certify that I have completed this lab assignment on my own
without any help from anyone else. I understand that the only sources of authorized
information in this lab assignment are (1) the course textbook, (2) the
materials posted at the course website and (3) any study notes handwritten by myself.
I have not used, accessed or received any information from any other unauthorized
source in taking this lab assignment. The effort in the assignment thus belongs
completely to me.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: <Beyda Dur, 75881>
********************************************************************************/

package test;

import java.util.*;
import kupeers.*;

public class Test {

	public static void main(String[] args) {
		// The following code creates 3 Data Objects with Paragraphs of text.
		System.out.println("Creating datas and chunks...");
		Data p2pData = new Data("Peer-to-Peer Model",
				"Peer-to-peer file sharing is the distribution and sharing of digital "
						+ "media using peer-to-peer (P2P) networking technology. P2P file sharing "
						+ "allows users to access media files such as books, music, movies, etc. "
						+ "The nodes (peers) of such networks are end-user computers and "
						+ "distribution servers. Peer-to-peer file sharing technology has evolved "
						+ "through several design stages from the early networks like Napster, "
						+ "to the later models like the BitTorrent protocol. " + "Source: Wikipedia.");

		Data clSrvrData = new Data("Client-Server Model",
				"The client–server model is a distributed application structure that "
						+ "partitions tasks or workloads between the providers of a resource or "
						+ "service, called servers, and service requesters, called clients. Often "
						+ "clients and servers communicate over a computer network on separate "
						+ "hardware, but both client and server may reside in the same system. "
						+ "A server host runs one or more server programs which share their "
						+ "resources with clients. A client does not share any of its resources, "
						+ "but requests a server's data or service function. Clients therefore "
						+ "initiate communication sessions with servers which await incoming "
						+ "requests. Examples of computer applications that use the client–server "
						+ "model are Email, network printing, and the World Wide Web. " + "Source: Wikipedia.");

		Data collData = new Data("Java Collections",
				"Almost all collections in Java are derived from the java.util.Collection "
						+ "interface. Collection defines the basic parts of all collections. The "
						+ "interface states the add() and remove() methods for adding to and "
						+ "removing from a collection respectively. Also required is the toArray() "
						+ "method, which converts the collection into a simple array of all the "
						+ "elements in the collection. Finally, the contains() method checks if "
						+ "a specified element is in the collection. The Collection interface is "
						+ "a subinterface of java.lang.Iterable, so any Collection may be the target "
						+ "of a for-each statement. The Iterable interface provides the iterator() "
						+ "method used by for-each statements. All collections have an iterator that "
						+ "goes through all of the elements in the collection. Additionally, "
						+ "Collection is a generic. Source: Wikipedia.");

		// The following code splits the Data into List of Chunks (Sentences).
		List<Chunk> p2pChunks = p2pData.getChunks();
		List<Chunk> clSrvrChunks = clSrvrData.getChunks();
		List<Chunk> collChunks = collData.getChunks();

		// The following code initializes 4 Peer objects with inital chunks.
		System.out.println("Creating peers with inital chunks...");
		Map<Data, List<Chunk>> safaMap = new HashMap<Data, List<Chunk>>();
		safaMap.put(p2pData, new LinkedList<Chunk>(p2pChunks.subList(2, 5)));
		List<Chunk> safaCollList = new LinkedList<Chunk>();
		safaCollList.add(collChunks.get(0));
		safaCollList.add(collChunks.get(6));
		safaCollList.add(collChunks.get(8));
		safaMap.put(collData, safaCollList);
		Peer safa = new Peer("Safa", safaMap);

		HashMap<Data, List<Chunk>> doganMap = new HashMap<>();
		doganMap.put(p2pData, new LinkedList<Chunk>(p2pChunks.subList(0, 2)));
		doganMap.put(clSrvrData, new LinkedList<Chunk>(clSrvrChunks.subList(3, 5)));
		doganMap.put(collData, new LinkedList<Chunk>(collChunks.subList(0, 4)));
		Peer dogan = new Peer("Dogan", doganMap);

		HashMap<Data, List<Chunk>> mugeMap = new HashMap<>();
		mugeMap.put(clSrvrData, new LinkedList<Chunk>(clSrvrChunks.subList(0, 3)));
		mugeMap.put(collData, new LinkedList<Chunk>(collChunks.subList(3, 7)));
		Peer muge = new Peer("Muge", mugeMap);

		HashMap<Data, List<Chunk>> vahidehMap = new HashMap<>();
		vahidehMap.put(p2pData, new LinkedList<Chunk>(p2pChunks.subList(2, 3)));
		vahidehMap.put(clSrvrData, new LinkedList<Chunk>(clSrvrChunks.subList(5, 7)));
		vahidehMap.put(collData, new LinkedList<Chunk>(collChunks.subList(7, 10)));
		Peer vahideh = new Peer("Vahideh", vahidehMap);

		// Initializing Peer, Data, String arrays.
		// These are examples for Initializing the arrays at time of declaration of the
		// arrays.
		Peer[] peers = new Peer[] { safa, dogan, muge, vahideh };
		Data[] texts = new Data[] { p2pData, clSrvrData, collData };
		String[] dataNames = new String[] { p2pData.getName(), clSrvrData.getName(), collData.getName() };

		// Creating the KuPeers object.

		System.out.println("Setting up the KuPeers network...");
		KuPeers network = new KuPeers(texts);

		// Adding Peers to the KuPeers object.
		System.out.println("Registering peers in KuPeers network...");
		for (Peer p : peers) {
			p.joinNetwork(network);
		}

		System.out.println("---------------------------");
		System.out.println("Peers before any downloads:");
		System.out.println("---------------------------\n");
		for (Peer p : peers) {
			System.out.println(p);
		}
		
		// In-lab Task-2
		System.out.println("--------In-lab Part 2: ----------------");
		System.out.println();
		Map<Data, List<Chunk>> commonMap1 =dogan.findCommonChunks(safa);
		System.out.println("Common chunks between Dogan and Safa:");
		for (Data data : commonMap1.keySet()) {
		System.out.println(data.getName());
		for (Chunk chunk : commonMap1.get(data)) {
		System.out.println(chunk.getId());
		}
		}
		Map<Data, List<Chunk>> commonMap2 =muge.findCommonChunks(dogan);
		System.out.println("Common chunks between Muge and Dogan:");
		for (Data data : commonMap2.keySet()) {
		System.out.println(data.getName());
		for (Chunk chunk : commonMap2.get(data)) {
		System.out.println(chunk.getId());
		}
		}
		System.out.println();

		System.out.println("Peers try to download the missing chunks of all data...");
		for (Peer p : peers) {
			for (String c : dataNames) {
				p.downloadData(c);
			}
		}

		System.out.println();
		System.out.println("----------------------");
		System.out.println("Peers after downloads:");
		System.out.println("----------------------\n");

		for (Peer p : peers) {
			System.out.println(p);
		}
		
		// In-lab Task-1
		System.out.println("--------In-lab Part 1: ----------------");
		System.out.println();
		System.out.println("----------------------");
		System.out.println("Peers after sort:");
		System.out.println("----------------------\n");
		for (Peer p: peers) {
		p.sortChunks(collData);
		System.out.println(p);
		}
	}
}
