package org.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Pinger{
  public static boolean sendPingRequest(String host) throws IOException{
    InetAddress address = InetAddress.getByName(host);
    return address.isReachable(5000);

//    try {
//      // Get the InetAddress object for the given host
//      InetAddress address = InetAddress.getByName(host);
////      System.out.println("Sending Ping Request to " + host + " (" + address.getHostAddress() + ")");
//
//      // Check reachability with a 5000 millisecond (5 second) timeout
//      if(address.isReachable(5000)){
//        System.out.println("Host is reachable");
//      }
//      else {
//        System.out.println("Host is NOT reachable");
//      }
//    }
//    catch (UnknownHostException e) {
//      System.out.println("Error: Unknown host " + host);
//      e.printStackTrace();
//    }
//    catch (IOException e) {
//      System.out.println("Error: IO exception occurred");
//      e.printStackTrace();
//    }
  }
}
