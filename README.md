# IMPORTANT NOTE 1:
For each lab assignment, **you must include and sign (by writing your name and student id number) the following Pledge of Honor statement at the beginning of your main method source code file. After including this statement, make sure that you do the commit and push operation on GitHub. Otherwise, your lab solution will not be graded.**

```
/* *********** Pledge of Honor ************************************************ *

I hereby certify that I have completed this lab assignment on my own
without any help from anyone else. I understand that the only sources of authorized
information in this lab assignment are (1) the course textbook, (2) the
materials posted at the course website and (3) any study notes handwritten by myself.
I have not used, accessed or received any information from any other unauthorized
source in taking this lab assignment. The effort in the assignment thus belongs
completely to me.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: <Name Surname, Student id>
********************************************************************************/
```
## IMPORTANT NOTE 2: After you complete the tasks, do not forget to commit your changes and push them to your repository on Github.

## IMPORTANT NOTE 3: This README.md file only contains the instructions for PRELAB. You will also have INLAB questions.

INLAB QUESTIONS WILL BE ANNOUNCED AT COURSE WEBSITE AT THE START OF EACH LAB: [https://sites.google.com/a/ku.edu.tr/comp132/programming-labs](https://sites.google.com/a/ku.edu.tr/comp132/programming-labs)

# Lab-6 Prelab: KuPeers 
In this programming lab, you are asked to implement a simplified Peer-to-Peer (P2P) Data-sharing application called KuPeers. The main concepts in this lab are Java Collections and related concepts, which you will practice with. You are asked to implement multiple classes and methods and test the correctness of your implementation by running the main method within the Test class. The project includes two packages (kupeers and test), and the test package with Test.java is already given to you in the template project. You need to implement the other parts in the kupeers package. Please note that we may test your codes with other test cases as well.

In P2P systems, there is no central server that holds the Data. Instead, peers (normal users of the software) save the pieces of the Data (Chunks) on their own machines and share them with each other. Data is divided into pieces called ​chunks,​ that a peer is willing to share with other peers. Likewise, a peer searches for the missing chunks that it needs to be able to reconstruct the whole Data.

In real P2P sharing software, Data can be anything such as a text file, an image, a binary file, or a directory of files. For simplicity in this lab, we assume that each Data comprises of a paragraph of sentences. Each sentence is assumed to be a Chunk. 

The Data’s chunks are spread across the peers, with no peer having all the chunks of any Data. Your goal is to enable these peers, using your application, KuPeers, to get the missing chunks of each Data, and reconstruct the Data correctly. For each Data, the process is as follows:
* A peer asks KuPeers to check if the Data exists in the KuPeers network.
* If it exists, it gets the chunks of that Data. For each chunk, the peer will:
  * Check if it already has a copy of it. If it does, it skips this chunk and goes to the next one.
  * Otherwise, it checks the locations (peers) of the chunk. Each chunk saves the locations (peers) where it can be found. From each location, it tries to download the chunk:
    * If the download fails (e.g., the peer is offline), it skips that peer and tries to download from the next one.
    * Otherwise, it confirms the download and moves on to the next chunk.
  * If the download of one chunk fails from all available locations, that chunk is skipped, and the next chunk is downloaded.

All classes that are going to be implemented by you, should be put into the kupeers​ package. Below is a description of all classes and their fields and methods.

## Test
This is the main class inside the test package, it is already provided for you and you shouldn’t edit any parts of it. 

It starts with creating three example Data: paragraphs about the Peer-to-Peer Model, Client-Server Model, and Java Collections.

It then creates 4 peers, namely: Safa, Dogan, Muge, and Vahideh. Each one of the peers has some chunks of some or all of the Data. Combining the chunks from those peers only, you will be able to reconstruct all the Data successfully (there are no missing chunks).

It then registers the four peers on the KuPeers network and displays their initial status. All peers try to download their missing chunks from the network. Finally, the status of each peer is displayed again.

## KuPeers
Using this class, peers can check if one Data exists, get information about the Data, and register in the network. It has the following fields and methods:

### Fields:
* A List of all peers in the KuPeers network (**List\<Peer\>**). 
* A Map of Data, where the key is the data name and the value is the Data object itself. Using a map helps searching for the Data faster (**Map\<String, Data\>**).

### Methods:

* public KuPeers(Data[] data) 
  * Constructor. Sets up the Data map from the given data and initializes an empty LinkedList of peers.
* public boolean dataExists(String name) 
  * Returns true if the Data with the given name exists in the Data map.
* public Data getDataInfo(String name) 
  * Searches for a Data using the given name and returns the Data object if it exists. Otherwise, returns no object (null).
* public void registerPeer(Peer p) 
  * Adds the given Peer p to the list of peers in the network and adds the peer’s chunks by calling addPeerChunksToNetwork(p) method
* private void addPeerChunksToNetwork(Peer p) 
  * Add the given Peer p's chunks to the network by updating the chunk objects locations.

## Data
A Data has a name and it stores some text. Each Data is split into a number of chunks once created. 

### Fields:

* Name of the Data as a String.
* Size of the Data (number of sentences) as an integer.
* Text of the Data (the main part of the Data that is going to be split into chunks) as a String.
* The list of chunks a Data is split into (**List\<Chunk\>**).

### Methods:

* public Data(String name, String text) 
  * Sets the name and text fields, and calls setChunksAndSize method.
* private void setChunksAndSize() 
  * This method splits the text into an array of sentences. All sentences are ended with a dot (.)  (See the note below.) 
  * Then, it saves the length as the size of the Data and creates a new Chunk object for each statement, and adds it to the list of chunks. Chunks of each Data should have IDs: 0, 1, 2, etc, starting from zero.
* public String toString() 
  * String representation of Data (See sample output).

Note: You can use use the String class’ split() method with argument string as “\\.\s” to split the text into sentences. 

Example:

``` java
String str = “Hello World. This is KuPeers.”;
String[] strArr = str.split(“\\.\s”);
System.out.println(strArr); // Output: “[“Hello World.”, “This is KuPeers.”]”
```

## Chunk
Each Data is split into a number of chunks. Each chunk represents one sentence from the Data text. Also, each chunk stores the locations (peers) where it can be found. The Chunk class allows peers to share chunks.

### Fields:
* A set of all peers that have a copy of this chunk (**Set\<Peer\>**).
* A sentence from a Data.
* The index of this chunk (sentence) in the Data text.
* The Data object to which a chunk belongs.

### Methods:

* public Chunk(String text, int id, Data data) 
  * Constructor. Sets the text, id, and Data fields and initializes locations as an empty Set.
* public void addLocation(Peer p) 
  * Adds peer p to the set of locations.
* public boolean availableAt(Peer p) 
  * Returns true if peer p has a copy of this chunk and false otherwise.
* public String getText(Peer p) 
  * If this chunk is available at peer p, returns its text, otherwise displays the statement “You do not have this chunk, please download it first.” and returns null. This is to simulate a chunk’s existence on a peer’s machine.
* public boolean copyChunk(Peer src, Peer dest) 
  * First checks if the source and destination peers are online and if the source peer has a copy of this chunk.
  * If all conditions are met, it copies the chunk to dest using the Peer class’s receiveChunk method. It also adds dest to the location set. Finally, it returns true.
  * Otherwise, returns false.

## Peer
Each peer belongs to a KuPeers network and has a number of chunks that it is willing to share with other peers in the same network. To download Data completely, a peer downloads the missing chunks of that Data from other peers in the same network. The download of a chunk is a 3-step process as follows:
  1. A peer calls its downloadChunk method with the needed chunk.
  2. The method checks the locations of that chunk and tries to download it from each location (Peer) using the Chunk class’ copyChunk method with the source peer and the calling peer as the destination.
  3. The Chunk class’ copyChunk method checks if the chunk can be downloaded from the source to the destination. If it can, it calls the Peer class’s receiveChunk method on the destination peer, with the chunk and its Data, which in turn adds the chunk to the peer’s chunks map.

Note: This only shows the scenario where the download succeeds. More details are given below.

### Fields:
* A static integer variable that keeps thhe count of all peers, to generate peer IDs.
* The ID of the peer as integer.
* Name of the peer as String.
* A Map that stores chunks, where the key is a Data object and the value is a list of Chunks (**Map\<Data, List\<Chunk\>\>**).
* Status of the peer as boolean.
* The KuPeers object (the network) the peer belongs to.

### Methods:
* public Peer(String name, Map\<Data, List\<Chunk\>\> initialChunks) 
  * Constructor. Sets the peer’s name, and status as online by default increments the total number of peers, and assigns it as the peer’s ID. It also sets the chunks field as a new Map object filling it with the given initialChunks map.
* public void joinNetwork(KuPeers network) 
  * Sets the KuPeers object and calls that object’s registerPeer method.
* Write a function that takes a Data object and a Chunk object as arguments and returns true if this peer has the given chunk of the given data and false otherwise.
* public void receiveChunk(Data data, Chunk chunk) 
  * Adds the given chunk of the given data to this peer’s chunks Map. Use LinkedList for the list of chunks. Note that if a peer already has some chunks of the given data, you should append the chunk to that list and not overwrite the list.
* Write a function that takes a Chunk object as an argument and does the following:
  * Gets the locations (peers) where chunk exists and tries to download the chunk from each of these locations. 
    * If the download succeeds from any location, it immediately returns true without trying the other locations.
    * Otherwise, it tries the other locations.
  * If all downloads fail from all available locations, it returns false.

Note: you need to use the Chunk class’s copyChunk method for the download.

* public boolean downloadData(String name) 
  * It first checks if the peer has joined the network. 
    * If not, it displays the statement “Peer hasn't joined the network yet, download aborted.” and immediately returns false.
    * Otherwise, It checks if data with the given name exists in the network.
      * If it doesn’t, it displays the statement “Data does not exist in the network, download aborted” and immediately returns false.
      * Otherwise, it gets the data with the given name from the network. 
        * For each of the chunks of that data, it checks if it has a copy of it. 
          * If it does, it skips the chunk and moves on to the next one. 
          * Otherwise, it tries to download the chunk.
            * If downloading a chunk fails, it continues downloading the remaining chunks but should return false at the end of the method.
        * Once all downloads finish, it should return true if the peer has all chunks of the given data, otherwise, it returns false. You are advised to use the methods described above in this method.
* public String toString()
  * String representation of Peer (See sample output).
  
# Sample Output

Your output should be similar to the following. Note that the order of data a peer has can be different than below since access to a HashMap is random. However, the order of the chunks a peer has (of some data) should be exactly the same as below.
``` console
Creating datas and chunks...
Creating peers with inital chunks...
Setting up the KuPeers network...
Registering peers in KuPeers network...
---------------------------
Peers before any downloads:
---------------------------

peer#1
Name: Safa
Status: Online

Chunks:
-------
Data Name: Java Collections
Data is composed of 10 chunks.
Chunks the peer has (1st index is 0): 0, 6, 8.

Data Name: Peer-to-Peer Model
Data is composed of 5 chunks.
Chunks the peer has (1st index is 0): 2, 3, 4.


peer#2
Name: Dogan
Status: Online

Chunks:
-------
Data Name: Java Collections
Data is composed of 10 chunks.
Chunks the peer has (1st index is 0): 0, 1, 2, 3.

Data Name: Client-Server Model
Data is composed of 7 chunks.
Chunks the peer has (1st index is 0): 3, 4.

Data Name: Peer-to-Peer Model
Data is composed of 5 chunks.
Chunks the peer has (1st index is 0): 0, 1.


peer#3
Name: Muge
Status: Online

Chunks:
-------
Data Name: Java Collections
Data is composed of 10 chunks.
Chunks the peer has (1st index is 0): 3, 4, 5, 6.

Data Name: Client-Server Model
Data is composed of 7 chunks.
Chunks the peer has (1st index is 0): 0, 1, 2.


peer#4
Name: Vahideh
Status: Online

Chunks:
-------
Data Name: Java Collections
Data is composed of 10 chunks.
Chunks the peer has (1st index is 0): 7, 8, 9.

Data Name: Client-Server Model
Data is composed of 7 chunks.
Chunks the peer has (1st index is 0): 5, 6.

Data Name: Peer-to-Peer Model
Data is composed of 5 chunks.
Chunks the peer has (1st index is 0): 2.


Peers try to download the missing chunks of all data...

----------------------
Peers after downloads:
----------------------

peer#1
Name: Safa
Status: Online

Chunks:
-------
Data Name: Java Collections
Data is composed of 10 chunks.
Chunks the peer has (1st index is 0): 0, 6, 8, 1, 2, 3, 4, 5, 7, 9.

Data Name: Peer-to-Peer Model
Data is composed of 5 chunks.
Chunks the peer has (1st index is 0): 2, 3, 4, 0, 1.

Data Name: Client-Server Model
Data is composed of 7 chunks.
Chunks the peer has (1st index is 0): 0, 1, 2, 3, 4, 5, 6.


peer#2
Name: Dogan
Status: Online

Chunks:
-------
Data Name: Java Collections
Data is composed of 10 chunks.
Chunks the peer has (1st index is 0): 0, 1, 2, 3, 4, 5, 6, 7, 8, 9.

Data Name: Client-Server Model
Data is composed of 7 chunks.
Chunks the peer has (1st index is 0): 3, 4, 0, 1, 2, 5, 6.

Data Name: Peer-to-Peer Model
Data is composed of 5 chunks.
Chunks the peer has (1st index is 0): 0, 1, 2, 3, 4.


peer#3
Name: Muge
Status: Online

Chunks:
-------
Data Name: Java Collections
Data is composed of 10 chunks.
Chunks the peer has (1st index is 0): 3, 4, 5, 6, 0, 1, 2, 7, 8, 9.

Data Name: Client-Server Model
Data is composed of 7 chunks.
Chunks the peer has (1st index is 0): 0, 1, 2, 3, 4, 5, 6.

Data Name: Peer-to-Peer Model
Data is composed of 5 chunks.
Chunks the peer has (1st index is 0): 0, 1, 2, 3, 4.


peer#4
Name: Vahideh
Status: Online

Chunks:
-------
Data Name: Java Collections
Data is composed of 10 chunks.
Chunks the peer has (1st index is 0): 7, 8, 9, 0, 1, 2, 3, 4, 5, 6.

Data Name: Client-Server Model
Data is composed of 7 chunks.
Chunks the peer has (1st index is 0): 5, 6, 0, 1, 2, 3, 4.

Data Name: Peer-to-Peer Model
Data is composed of 5 chunks.
Chunks the peer has (1st index is 0): 2, 0, 1, 3, 4.
```
