package controlCenter;

import java.util.ArrayList;

import userAnalyse.RelationSet;
import userAnalyse.UserNetMethodSet;
import userAnalyse.UserRelation;

public class UserNet {
	ArrayList<String[]> dataSet;
	public void getUserNet(String dbName, String tbName){
		UserNetMethodSet methodSet = new UserNetMethodSet();
		dataSet = methodSet.getDataSet(dbName, tbName);
		
		UserRelation relation = new UserRelation();
		
		int len = dataSet.size();
		String[] userData;
		String userName;
		String url;
		for(int i = 0; i < len; i++) {
			userData = dataSet.get(i);
			userName = userData[0];
			url = userData[1];
			
			ArrayList<RelationSet> rA = relation.getrA();
			if(!rA.contains(userName)){
//				等理清需要比对的数据的逻辑关系再写
			}
		}
	}

}
