package fr.pragmaticmemory.Table.Isr.TreeTable;

import fr.pragmaticmemory.Table.Isr.comparator.BrokerComparator;
import fr.pragmaticmemory.Table.Isr.comparator.CriteriaComparator;
import fr.pragmaticmemory.Table.Isr.comparator.IssuerComparator;
import fr.pragmaticmemory.Table.Isr.inputData.Broker;
import fr.pragmaticmemory.Table.Isr.inputData.Criteria;
import fr.pragmaticmemory.Table.Isr.inputData.Issuer;
import fr.pragmaticmemory.Table.Isr.inputData.RatedCbcIssuer;
import fr.pragmaticmemory.TreeTable.AbstractTreeTableModel;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
public class IsrTreeTableModel extends AbstractTreeTableModel {

    private List<Broker> brokerList;
    private List<Criteria> criteriaList;
    private List<RatedCbcIssuer> ratedCbcIssuerList;
    private List<Issuer> issuerList;
    private IndexConverter brokerIndexConverter = new IndexConverter();
    private IndexConverter criteriaIndexConverter = new IndexConverter();
    private Map<Integer, DefaultMutableTreeNode> issuerId2NodeMap = new HashMap<Integer, DefaultMutableTreeNode>();


    public IsrTreeTableModel(List<Issuer> issuerList, List<Broker> brokerList,
                             List<Criteria> criteriaList,
                             List<RatedCbcIssuer> ratedCbcIssuerList) {
        this.issuerList = issuerList;
        this.brokerList = brokerList;
        this.criteriaList = criteriaList;
        this.ratedCbcIssuerList = ratedCbcIssuerList;
        Collections.sort(brokerList, new BrokerComparator());
        Collections.sort(criteriaList, new CriteriaComparator());
        Collections.sort(issuerList, new IssuerComparator());
        buildDataTree();
    }


    public Object getChild(Object parent, int index) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)parent;
        return node.getChildAt(index);
    }


    public int getChildCount(Object parent) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)parent;
        return node.getChildCount();
    }


    public int getColumnCount() {
        return brokerIndexConverter.getSize() * criteriaIndexConverter.getSize() + 1;
    }


    public String getColumnName(int col) {
        if (col == 0) {
            return "Issuer";
        }
        col--;
        int brokerIndex = col / criteriaIndexConverter.getSize();
        int criteriaIndex = col % brokerIndexConverter.getSize();
        String brokerName = brokerList.get(brokerIndex).getName();
        String criteriaName = criteriaList.get(criteriaIndex).getName();
        return brokerName + " - " + criteriaName;
    }


    public Object getValueAt(Object node, int col) throws Exception {
        DefaultMutableTreeNode concreteNode = (DefaultMutableTreeNode)node;
        final Object userObject = concreteNode.getUserObject();
        RatingBean ratingBean = (RatingBean)userObject;
        if (col == 0) {
            return ratingBean.getIssuerName();
        }
        col--;
        if (ratingBean.hasRatingForIndex(col)) {
            return ratingBean.getRating(col);
        }
        else {
            return "EMPTY";
        }
    }


    public String getCellTipText(Object node, int col) {
        return null;
    }


    public void buildDataTree() {
        for (Issuer issuer : issuerList) {
            if (!issuerId2NodeMap.containsKey(issuer.getId())) {
                final DefaultMutableTreeNode node = new DefaultMutableTreeNode();
                node.setUserObject(new RatingBean(issuer.getName()));

                if (issuer.getParentId() != -1) {
                    issuerId2NodeMap.get(issuer.getParentId()).add(node);
                }
                else {
                    root = node;
                }
                issuerId2NodeMap.put(issuer.getId(), node);
            }
        }

        for (Broker broker : brokerList) {
            brokerIndexConverter.add(broker.getId());
        }

        for (Criteria criteria : criteriaList) {
            criteriaIndexConverter.add(criteria.getId());
        }

        for (RatedCbcIssuer ratedCbcIssuer : ratedCbcIssuerList) {
            final int issuerId = ratedCbcIssuer.getIssuerId();
            DefaultMutableTreeNode issuerNode = issuerId2NodeMap.get(issuerId);
            final RatingBean ratingBean = (RatingBean)issuerNode.getUserObject();
            Integer columnIndex = computeColumnIndex(brokerIndexConverter.getIndexOfId(ratedCbcIssuer.getBrokerId()),
                                                     criteriaIndexConverter.getIndexOfId(ratedCbcIssuer.getCriteriaId()),
                                                     criteriaIndexConverter.getSize());
            ratingBean.addRating(columnIndex, ratedCbcIssuer.getNote());
        }
    }


    public static Integer computeColumnIndex(int brokerIndex, int criteriaIndex, int criteriaNumber) {
        return brokerIndex * criteriaNumber + criteriaIndex;
    }
}
