import javax.sound.midi.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.ArrayList;

class MidiTest {
    public static void main(String[] args) throws Exception {

    	int dynamics = 100;
    	int playLength = 500;
    	int valtype = 0;
    	String root = args[0];
    	String chordName = args[1];
    	int octave = Integer.parseInt(args[2]);
    	ArrayList<Integer> chordArray = new ArrayList<>();
    	
        chordArray = changeChordToNum(root,chordName,octave);
        chordArray = invertChord(chordArray,valtype);
        
        for (int Key : chordArray ) {
        	playSound(Key,dynamics);
        	Thread.sleep(playLength);
        	playSound(Key,0);
       }        
    	Thread.sleep(playLength);
        
    }
    
    private static ArrayList<Integer> changeChordToNum(String strRoot,String strChordName,int octave) throws Exception {
    	
    	int intRoot;
    	int intSemitone;
    	ArrayList<Integer> chordArray = new ArrayList<>();
    	
    	intRoot = changeNoteToNum(strRoot,octave);
    	chordArray.add(intRoot); 
    	
        Document document = XMLDomReader.buildDocument("src/resource/chordnumbers.xml");
    	       
        Element chordElement = document.getElementById(strChordName);
        NodeList semitonesNodeList = chordElement.getElementsByTagName("semitone");        

        for (int j = 0; j < semitonesNodeList.getLength(); j++) {
    		Node semitoneNode = semitonesNodeList.item(j);
    		if(semitoneNode.getNodeType() == Node.ELEMENT_NODE) {
    			Element semitoneElement = (Element)semitoneNode;
    			intSemitone = Integer.parseInt(semitoneElement.getTextContent());
    			intSemitone = intRoot + intSemitone;
    			chordArray.add(intSemitone);
	    	}
    	}
    	
    	return chordArray;   	

    }
    
    private static int changeNoteToNum(String strNote,int octave) throws Exception {
 
    	int intNote = 0;
    	
    	Document document = XMLDomReader.buildDocument("src/resource/notenumbers.xml");

        Element noteElement = document.getElementById(strNote);
        intNote = Integer.parseInt(noteElement.getElementsByTagName("number").item(0).getTextContent());

    	intNote = intNote + octave * 15;
        return intNote;
        
    }
    
    private static ArrayList<Integer> invertChord(ArrayList<Integer> chordArray,int valtype) {
    	
    	int baf;
    	int baf2;
    	int arrayLength;
    	
    	arrayLength = chordArray.size();
    	
    	for (int i = 0; i < valtype; i++) {
    		baf = chordArray.get(0);
        	for (int j = 1; j < arrayLength ; j++) {
        		baf2 = chordArray.get(j);
        		chordArray.set(j - 1,baf2);
        	}
    		chordArray.set(arrayLength - 1,baf + 12);
    	}
    	
    	return chordArray;
    	
    	
    }
    
    private static void playSound(int intNote , int intDynamics) throws MidiUnavailableException, InvalidMidiDataException {

        Receiver receiver = MidiSystem.getReceiver();
        ShortMessage message = new ShortMessage();
        
      	message.setMessage(ShortMessage.NOTE_ON, intNote, intDynamics);
    	receiver.send(message, -1);
        	
    }
    
}