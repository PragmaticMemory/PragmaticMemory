package fr.pragmaticmemory.web;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
public class ProxyAnalyser {

    static final String URL = "http://www.yahoo.com/";


    public static void main(String[] args) {
        try {
            System.setProperty("java.net.useSystemProxies", "true");
            List<Proxy> proxyList = ProxySelector.getDefault().select(new URI(URL));

            for (Iterator iter = proxyList.iterator(); iter.hasNext(); ) {
                Proxy proxy = (Proxy)iter.next();
                System.out.println("proxy type : " + proxy.type());
                InetSocketAddress addr = (InetSocketAddress)proxy.address();
                if (addr == null) {
                    System.out.println("no Proxy");
                    return;
                }
                System.out.println("proxy hostname : " + addr.getHostName());
                System.out.println("proxy port : " + addr.getPort());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

