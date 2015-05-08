package userAnalyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dataSet.ExtractResultSet;
import storeUtil.StoreMysql;

/**
 * @author acer
 *
 */
public class UserNetMethodSet {
    static StoreMysql sql_process;
    HashMap<String,HashMap<String,Integer>> queryAllData;
    ArrayList<String> getValidUserName;
//    static HashMap<String,HashMap<String, Integer>> allData;
    
	/**获取每个用户所参与过的的URL列表
	 * @param dbName
	 * @param tbName
	 * @return
	 */
	public HashMap<String,ArrayList<String>> getUserRecord(String dbName,String tbName){
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		ArrayList<String[]> rlist = sql_process.queryUserName(dbName, tbName);  //得到网民列表
		System.out.println(rlist.size() + "\t" + rlist.get(0)[0]);
		
		for(int i = 0; i < rlist.size(); i++){
			System.out.println(i + "\t" + rlist.size() + "\t" + rlist.get(i)[0]);			
			String name = rlist.get(i)[0];
			String url = rlist.get(i)[1];
			if(!result.containsKey(name)){	
				ArrayList<String> urlList = new ArrayList<String>();
			    urlList.add(url);	
			    result.put(name, urlList);
			}else{
				if(!result.get(name).contains(url)){
				   result.get(name).add(url);
				}
			}
		}
		return result;	
	}
    
	/**得到每个帖子对应的用户群
	 * @param dbName
	 * @param tbName
	 * @return
	 */
	public HashMap<String,HashSet<String>> getUserGroupByUrl(String dbName, String tbName){
		HashMap<String,HashSet<String>> urlMap = new HashMap<String,HashSet<String>>();
		
		UserNetMethodSet una = new UserNetMethodSet();
		ArrayList<String> urlList = una.getUrlList(dbName, tbName);
		
		for(int i = 0; i < urlList.size(); i++){
			String URL = urlList.get(i);
			HashSet<String> userSet = new HashSet<String>();
			ArrayList<String> userList = una.getUserList(dbName, tbName, URL);
//			加入一个去重用户名的方法，将参与帖子的用户出现的次数限制为一次
			for(int j = 0; j < userList.size(); j++){
				if(!userSet.contains(userList.get(j))){
					userSet.add(userList.get(j));
				}
			}
			urlMap.put(URL, userSet);
		}
		
		return urlMap;
	}
	
//	
	/**获取所有帖子URL列表
	 * @param dbName
	 * @param tbName
	 * @return
	 */
	public ArrayList<String> getUrlList(String dbName, String tbName){
		ArrayList<String> urlList = new ArrayList<String>();
		urlList = sql_process.queryUrlList(dbName, tbName);
		return urlList;
	}
	/**获取帖子的用户列表
	 * @param dbName
	 * @param tbName
	 * @return
	 */
	public ArrayList<String> getUserList(String dbName, String tbName,String URL){
		ArrayList<String> userList = new ArrayList<String>();
		userList = sql_process.queryUserByUrl(dbName, tbName, URL);
		return userList;
	}
//	
    /**对比在不同的俩个帖子中，相同用户出现的次数，以此来判别是否为水军
     * @param dbName
     * @param tbName
     */
//    public void checkWaterArmy(String dbName, String tbName){
//    	UserNetAnalyse una = new UserNetAnalyse();
//    	ArrayList<String> urlList = una.getUrlList(dbName, tbName);
//    	HashMap<String,HashSet<String>> userGroupByUrl = una.getUserGroupByUrl(dbName, tbName);
//    	
//    	HashSet<String> set1 = new HashSet<String>();
//    	HashSet<String> set2 = new HashSet<String>();
//    	
//    	for(int i = 0; i < urlList.size()-1; i++){
//    		String URL1 = urlList.get(i);
//    		String URL2 = urlList.get(i+1);
//    		set1 = new HashSet<String>();
//    		set2 = new HashSet<String>();
//    		set1 = userGroupByUrl.get(URL1);
//    		set2 = userGroupByUrl.get(URL2);
//    		una.listCompare(set1, set2);
//    	}    	
//	}

    /**比较两个帖子所共同参与的用户个数，并根据阈值判断哪些用户疑似为水军，并标记
     * @param queryAllData2 
     * @param list1
     * @param list2
     */
    public void setCompare(HashSet<String> set1,HashSet<String> set2){
    	int count = 0;
    	int threshhold = Math.min(set1.size(), set2.size())/2;
    	
    	Iterator<String> iter = set1.iterator();
    	while(iter.hasNext()){
    		String next = iter.next();
    		if(set2.contains(next)){
    			count = count + 1;
    		}
    	}
    	if(count > threshhold){
    		
    	}
    }
	public ArrayList<String> getValidUserName(ArrayList<String> allUserOfForum, HashMap<String, HashMap<String, Integer>> queryAllData2){
		getValidUserName = new ArrayList<String>();
		int participation = 0;
		HashMap<String,Integer> queryByUserName;
		int user_size = allUserOfForum.size();
		int data_size = queryAllData2.size();
		
		int count = 0;
		for(int j = 0; j < user_size; j++){
			String userName = allUserOfForum.get(j);
			HashMap<String,Integer> hashMap = queryAllData2.get(userName);
			count = hashMap.size() + count;
		}
		participation = count/user_size;
		System.out.println("participation : " + participation);
		
		for(int i = 0; i < user_size; i++){      //getValidUserName
			String userName = allUserOfForum.get(i);
		    queryByUserName = queryAllData2.get(userName);
		    if(queryByUserName.size() < participation){
		    	continue;
		    }
		    getValidUserName.add(userName);
		}
		System.out.println(getValidUserName.size());
		return getValidUserName;
	}
	
//  得到论坛中的所有用户
	public ArrayList<String> getAllUserOfForum(String dbName, String tbName){
		ArrayList<String> alist = new ArrayList<String>();
		HashSet<String> queryAllUser = sql_process.queryAllUser(dbName, tbName);
		Iterator<String> iter = queryAllUser.iterator();
		while(iter.hasNext()){
			String next = iter.next();
			alist.add(next);
		}
		return alist;
	}
//	两个用户所参与的话题的对比
	public int mapCompare(HashMap<String, Integer> queryByUserName1, HashMap<String, Integer> queryByUserName2){     
		int count = 0;
		String next = "";
		Iterator<String> iter = queryByUserName1.keySet().iterator();
		while(iter.hasNext()){
			next = iter.next();
			if(queryByUserName2.keySet().contains(next)){
				count = count + 1;
			}
		}
		return count;
	}
	
//	得到content数据集
	public ArrayList<String[]> getDataSet(String dbName, String tbName){
		ArrayList<String[]> alist = new ArrayList<String[]>();
		
		ArrayList<String[]> queryUserName = sql_process.queryUserName(dbName, tbName);
		return alist;
	}
//  得到用户网络
//    public HashMap<String, HashMap<String, Integer>> getUserNet(String dbName, String tbName){
//    	HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
//    	
//    	UserNetMethodSet una = new UserNetMethodSet();
//    	queryAllData = new HashMap<String,HashMap<String,Integer>>();
//    	queryAllData = sql_process.queryAllDataOfUserUrlList(dbName, tbName);
//        ArrayList<String> allUserOfForum = una.getAllUserOfForum(dbName, tbName);
//        getValidUserName = una.getValidUserName(allUserOfForum,queryAllData);
//        
//        HashMap<String, Integer> relation;
//        HashMap<String,Integer> queryByUserName1;
//        HashMap<String,Integer> queryByUserName2;
//        int i,j;
//        int mapCompare;
//        String userName1,userName2;
//        int length = getValidUserName.size();
//        
//    	for(i = 0; i < length - 1; i++){
//    		userName1 = getValidUserName.get(i);
//    		System.out.println(i + "\t" + "queryByUserName" + "****************");
//    		queryByUserName1 = queryAllData.get(userName1);
//    		for(j = i + 1; j < length; j++){
//    			relation = new HashMap<String, Integer>();
//    			userName2 = getValidUserName.get(j);
//    			System.out.println(i + "/" + j + "\t" + "queryByUserName" + "****************");
//    			queryByUserName2 = queryAllData.get(userName2);
//    			mapCompare = una.mapCompare(queryByUserName1, queryByUserName2);
//    			relation.put(userName2, mapCompare);
//    			map.put(userName1, relation);
//    			System.gc();
//    		}
//			System.gc();
//    	}
//    	return map;
//    }
    
//    public static void main(String[] args){
//    	String dbName = "taobao";
//    	String tbName = "userurllist";
//    	
//    	sql_process = new StoreMysql();
//    	ArrayList<ExtractResultSet> ersList;
//    	ExtractResultSet ers;
//    	
//    	UserNetMethodSet una = new UserNetMethodSet();
//    	System.out.println("getUserNet" + "****************");
//    	HashMap<String,HashMap<String,Integer>> userNet = una.getUserNet(dbName, tbName);    	
//    	Iterator<String> iter = userNet.keySet().iterator();
//    	int i = 1;
//    	String userName1,userName2;
//    	int count;
//    	
//    	boolean insertUserNet;
//    	HashMap<String,Integer> hashMap;
//    	
//    	while(iter.hasNext()){
//    		ersList = new ArrayList<ExtractResultSet>();
//    		userName1 = iter.next();
//    		hashMap = userNet.get(userName1);
//    		Iterator<String> iter2 = hashMap.keySet().iterator();
//    		while(iter2.hasNext()){
//    			ers = new ExtractResultSet();
//    			userName2 = iter2.next();
//    			count = hashMap.get(userName2);
//    			ers.setMainName(userName1);
//    			ers.setSurName(userName2);
//    			ers.setCount(count);
//    			ersList.add(ers);
//    			System.out.println(i + "\t" + userName1 + "\t" + userName2 + "\t" + count); 
//    			i++;
//    		}
//    		insertUserNet = sql_process.insertUserNet(tbName, dbName, ersList);
//    	}
		
    }
//	public static void main(String[] args){   //测试得到用户参与的话题贴
//		
//		String dbName = "taobao";
//		String tbName = "content_taobao_chuangyexianfeng2013_1";
//		
//		String tbName1 = "userUrlList";
//		StoreMysql storeMysql = new StoreMysql();
//		
//		ArrayList<ExtractResultSet> result = new ArrayList<ExtractResultSet>();
//		
//		
//		UserNetAnalyse una = new UserNetAnalyse();
//		HashMap<String,ArrayList<String>> userRecord = una.getUserRecord(dbName, tbName);
//		int i = 1;
//		
//	    Iterator<String> iterator = userRecord.keySet().iterator();
//	    while(iterator.hasNext()){
//	    	String userName = iterator.next();
//	    	if(!userName.isEmpty()){
//		    	ArrayList<String> arrayList = userRecord.get(userName);
//		    	Iterator<String> iterator2 = arrayList.iterator();
//		    	while(iterator2.hasNext()){
//		    		ExtractResultSet ers = new ExtractResultSet();
//		    		String url = iterator2.next();
//		    		System.out.println(i + "\t" + userName + "\t" + url);
//		    		ers.setUserName(userName);
//		    		ers.setUrl(url);
//		    		ers.setRemark(null);
//		    		result.add(ers);
//		    		i++;
//		    	}
//	    	}
////	    	if(i % 1000 == 0){
////		    	boolean insertUserDB = storeMysql.insertUserDB(tbName1, dbName, result);
////		    }
//	    }  
//	    ArrayList<ExtractResultSet> temp = new ArrayList<ExtractResultSet>();
//	    for(int k = 0; k < result.size(); k++){
//	    	ExtractResultSet e = result.get(k);
//	    	temp.add(e);
//	    	if(k % 1000 == 0){
//	    		System.out.println(k);
//	    		boolean insertUserDB = storeMysql.insertUserDB(tbName1, dbName, temp);
//	    		temp.clear();
//	    	}
//	    }
//	}
