package userAnalyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import storeUtil.StoreMysql;

public class UserRelation {
	StoreMysql sql_process;
	
    ArrayList<RelationSet> rA;  //用户关系集
    int avgPart = 0;    //用户平均参与度
    HashMap<String, ArrayList<String>> topicUserList;  //帖子的参与用户列表
    
    /**获取有效的帖子用户列表
     * @param dbName
     * @param tbName
     * @return
     */
    public HashMap<String, ArrayList<String>> getTopicUserList(String dbName, String tbName){   
    	HashMap<String, ArrayList<String>> topicUserList_all = new HashMap<String, ArrayList<String>>();
    	ArrayList<String> queryUrlList = sql_process.queryUrlList(dbName, tbName);
    	
    	int user_size = 0;
    	for(int i = 0; i < queryUrlList.size(); i++){
    		String URL = queryUrlList.get(i);
    		ArrayList<String> queryUserByUrl = sql_process.queryUserByUrl(dbName, tbName, URL);
    		user_size = queryUserByUrl.size() + user_size;   //得到所有的用户数
    		topicUserList_all.put(URL, queryUserByUrl);
    	}
    	int topicNum = topicUserList_all.keySet().size();  //得到帖子总数
    	avgPart = user_size/topicNum;   //得到平均每个帖子的平均用户参与度
    	
    	Iterator<String> iter = topicUserList_all.keySet().iterator();  //得到大于平均用户参与度的帖子用户列表
    	while(iter.hasNext()){
    		String url = iter.next();
    		if(topicUserList_all.get(url).size() < avgPart){
    			continue;
    		}
    		topicUserList.put(url, topicUserList_all.get(url));
    	}
		return topicUserList;
    }

    /**增加新的用户关系
     * @param userName1
     * @param userName2
     * @param url
     * @return
     */
    public boolean addNewRelation(String userName1, String userName2, String url){
    	if(!userName1.equals(userName2)){
    		RelationSet r = new RelationSet(userName1, userName2);
    		rA.add(r);
    		rA.get(rA.indexOf(r)).addRdInTopics(url);
    	}
    	return true;
    }
   
    /**在已有的成员关系上添加关系
     * @param userName1
     * @param userName2
     * @param url
     * @return
     */
    public boolean addRelation(String userName1, String userName2, String url){
    	if(!userName1.equals(userName2)){
    		RelationSet r = new RelationSet(userName1, userName2);
    		rA.get(rA.indexOf(r)).addRdInTopics(url);
    	}
    	return true;
    }

    public ArrayList<RelationSet> getrA() {
        return rA;
    }

    /**检测数据集中是否含有此用户之间的关系
     * @param userID1
     * @param userID2
     * @return
     */
    public boolean isContains(String userID1, String userID2) {
    	RelationSet r = new RelationSet(userID1, userID2);
        if (rA.contains(r)) {
            return true;
        }
        return false;
    }
    
    /**向数据集中插入用户关系
     * @param topic
     * @param userID
     */
    public void insertRD(String topic, String userID) {
        if(rA==null){
            rA=new ArrayList();
        }
        if(topicUserList==null){
            topicUserList=new HashMap();
        }
        String oldUserID="";        
        ArrayList<String> userList;
        if(!topicUserList.containsKey(topic)){
            topicUserList.put(topic, new ArrayList());
            topicUserList.get(topic).add(userID);
        }else{
            topicUserList.get(topic).add(userID);
            userList=topicUserList.get(topic);
            for (int i = 0; i < userList.size()-1; i++) {
                oldUserID= userList.get(i);
                if(isContains(userID, oldUserID)){
                     addRelation(userID, oldUserID, topic);
                }else{
                    addNewRelation(userID, oldUserID, topic);
                }
            }
        }
    }

    /**插入用户关系
     * @param userID
     * @param oldUserID
     * @param rd
     */
    public void insertUserRD(String userID, String oldUserID, int rd) {
        if(rA==null){
            rA=new ArrayList();
        }
        addUserRelation(userID,oldUserID,rd);
    }

    /**添加用户关系
     * @param userID
     * @param oldUserID
     * @param rd
     */
    private void addUserRelation(String userID, String oldUserID, int rd) {
    	RelationSet r = new RelationSet(userID, oldUserID);
        rA.add(r);
        rA.get(rA.indexOf(r)).addRd(rd);
    }
}
