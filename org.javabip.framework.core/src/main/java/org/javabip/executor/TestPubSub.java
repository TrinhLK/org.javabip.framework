package org.javabip.executor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import lsr.concurrence.provided.tests.ClientInputReader;
import lsr.concurrence.provided.tests.ClientOutputWriter;
import lsr.concurrence.provided.tests.InputChecker;

public class TestPubSub implements Runnable{

	private ServerSocket serverSocket;
	
	public TestPubSub() {
	}
	
	public void run() {
		try(ServerSocket serverSocket = new ServerSocket(9999)) {
            Socket connectionSocket = serverSocket.accept();

            ClientOutputWriter output = new ClientOutputWriter(connectionSocket.getOutputStream());
            InputChecker inputCheck = new InputChecker(new ClientInputReader(connectionSocket.getInputStream()));
            
            //Create Input&Outputstreams for the connection
            InputStream inputToServer = connectionSocket.getInputStream();
            OutputStream outputFromServer = connectionSocket.getOutputStream();

            Scanner scanner = new Scanner(inputToServer, "UTF-8");
            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
            
            ArrayList<String> topics = new ArrayList<String>();
    		ArrayList<String> msgs = new ArrayList<String>();
    		topics.add("momo");
    		topics.add("concurrence");
    		msgs.add("bonjour");
    		msgs.add("hello");

            for (int i=0 ; i<10 ; i++) {
				//System.out.println(i);
            	output.subscribeTo(topics.get(0));
    			output.subscribeTo(topics.get(1));

    			output.publish(topics.get(0), msgs.get(0));
    			output.publish(topics.get(1), msgs.get(1));

    			output.unsubscribeTo(topics.get(0));

    			output.publish(topics.get(0), msgs.get(1)); // no check as we are not supposed to
    			// receive anything
    			output.publish(topics.get(1), msgs.get(0));

    			output.unsubscribeTo(topics.get(1));
    			
//				serverPrintOut.println("subscribe to " + topics.get(0));
//				serverPrintOut.println("subscribe to " + topics.get(1));
//				
//				serverPrintOut.println(topics.get(0) + " publishes " + msgs.get(0));
//				serverPrintOut.println(topics.get(1) + " publishes " + msgs.get(1));
//				
//				serverPrintOut.println("unsubscribe to " + topics.get(0));
//				
//				serverPrintOut.println(topics.get(0) + " publishes " + msgs.get(1));
//				serverPrintOut.println(topics.get(1) + " publishes " + msgs.get(0));
//				
//				serverPrintOut.println("unsubscribe to " + topics.get(1));
			}
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		TestPubSub tps = new TestPubSub();
		tps.run();
	}
	
}
