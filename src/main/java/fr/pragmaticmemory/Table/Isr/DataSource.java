package fr.pragmaticmemory.Table.Isr;
import fr.pragmaticmemory.Table.Isr.inputData.Broker;
import fr.pragmaticmemory.Table.Isr.inputData.Criteria;
import fr.pragmaticmemory.Table.Isr.inputData.Issuer;
import fr.pragmaticmemory.Table.Isr.inputData.RatedCbcIssuer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Broker> getBrokerList() {
        List<Broker> list = new ArrayList<Broker>();
        list.add(new Broker(10, "Broker2"));
        list.add(new Broker(5, "Broker1"));
        return list;
    }


    public static List<Issuer> getIssuerList() {
        List<Issuer> list = new ArrayList<Issuer>();
        list.add(new Issuer(1, 0, "Child"));
        list.add(new Issuer(0, -1, "Ulti"));
        list.add(new Issuer(2, 0, "Child 2"));
        return list;
    }


    public static List<Criteria> getCriteriaList() {
        List<Criteria> list = new ArrayList<Criteria>();
        list.add(new Criteria(100, "Criteria1"));
        list.add(new Criteria(200, "Criteria2"));
        return list;
    }


    public static List<RatedCbcIssuer> getRatingList() {
        List<RatedCbcIssuer> list = new ArrayList<RatedCbcIssuer>();
        list.add(new RatedCbcIssuer(0, 5, 100, new BigDecimal(1.11)));
        list.add(new RatedCbcIssuer(0, 5, 200, new BigDecimal(1.12)));
        list.add(new RatedCbcIssuer(0, 10, 100, new BigDecimal(1.21)));
        list.add(new RatedCbcIssuer(0, 10, 200, new BigDecimal(1.22)));
        list.add(new RatedCbcIssuer(1, 5, 100, new BigDecimal(2.11)));
        list.add(new RatedCbcIssuer(1, 5, 200, new BigDecimal(2.12)));
        list.add(new RatedCbcIssuer(1, 10, 100, new BigDecimal(2.21)));
        list.add(new RatedCbcIssuer(1, 10, 200, new BigDecimal(2.22)));
        return list;
    }
}
