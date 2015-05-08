package storeUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import commonTools.Regex;

/**
 *
 * @author xz
 */
public class ReadDB {

    private DataSource ds;
    private Connection conn;
    public static int maxID = 100;//最大ID：发展规模
    //topic-> TimeList
    private HashMap<String, ArrayList<String>> topicTimeList;
    //topic->userList
    private HashMap<String,ArrayList<String>> topicUserList;
    //topic->user list -> user postCount 话题-用户-发帖数
    private HashMap<String, HashMap<String, Integer[]>> topicUserPostCount;
    //user->firstPostTime
    private HashMap<String, String[]> userFirstPostTime;
    //topic->hit,commentCount
    private HashMap<String, String[]> topicHitCommentCount;
    //user->postCount(<year)
    private HashMap<String, Integer[]> userPostCount;
    //topic-user-id-comment
    private HashMap<String, ArrayList<String[]>> topic_user_comment_RS;//数据库查询结果：话题-用户-ID-评论
    //topic->userList
    private HashMap<String, HashSet<String>> topicUserSet;
    //topic->commentCount
    private HashMap<String,Integer> topicCommentCount;
    public ReadDB() {
        ds = new DataSource();
        conn = ds.getConnection();
    }

    public void close() {
        ds.close();
    }

    public HashMap<String, ArrayList<String>> getTopicUserList() {
        return topicUserList;
    }
    
    /**
     * 查询标题列表（按时间升序）
     *
     * @param dbName
     * @param tbName
     * @return
     */
    public ArrayList<String> queryComment(String dbName, String tbName) {
        ArrayList<String> list = new ArrayList<String>();
        String sql = "";
        sql = "select pagetitle from " + dbName + "." + tbName + " order by date(datetimes)";
        System.out.println(sql);

        ResultSet rs = null;
        try {
            rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                String title = rs.getString(1);
                list.add(title);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Integer> queryCommentNum(String dbName, String tbName) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        String sql = "";
        sql = "select commentCount from " + dbName + "." + tbName;
        System.out.println(sql);
        ResultSet rs;
        try {
            rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Integer> queryHitNum(String dbName, String tbName) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        String sql = "";
        sql = "select hitCount from " + dbName + "." + tbName;
        System.out.println(sql);
        ResultSet rs;
        try {
            rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     *
     * @param dbName
     * @param tbName
     * @param flag 1:hitCount/commentCount 0:commentCount/hitCount
     * @return
     */
    public ArrayList<Float> queryRate(String dbName, String tbName) {
        ArrayList<Float> list = new ArrayList<>();
        String sql = "";
        sql = "select commentCount,hitCount  from " + dbName + "." + tbName;
        System.out.println(sql);
        ResultSet rs;
        try {
            rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                float rate = 0.0f;
                int cNum = rs.getInt(1);
                int hNum = rs.getInt(2);
                if (cNum >= hNum && cNum != 0) {
                    rate = (float) hNum / cNum;
                } else if (hNum != 0) {
                    rate = (float) cNum / hNum;
                }
                list.add(rate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean isExist(String dbName, String tbName) {
        boolean flag = false;
        String sql = "";
        sql = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA=\""
                + dbName + "\" and TABLE_NAME=\"" + tbName + "\";";
        System.out.println(sql);
        ResultSet resultSet = null;
        try {
            resultSet = conn.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean createTB(String dbName, String tbName) {
        boolean flag = true;
        String sql = "";
        if (tbName.contains("_50")) {
            sql = "CREATE TABLE " + dbName + "." + tbName + " (\n"
                    + "  `ID` int(11) NOT NULL,\n"
                    + "  `pageUrl` varchar(255) NOT NULL DEFAULT '',\n"
                    + "  `pageTitle` varchar(255) DEFAULT NULL,\n"
                    + "  `dateTimeS` varchar(30) DEFAULT NULL,\n"
                    + "  `dateTime` datetime DEFAULT NULL,\n"
                    + "  `userUrl` varchar(255) DEFAULT NULL,\n"
                    + "  `userID` varchar(30) DEFAULT NULL,\n"
                    + "  `userName` varchar(30) DEFAULT NULL,\n"
                    + "  `userContent` text,\n"
                    + "  PRIMARY KEY (`ID`,`pageUrl`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        } else if (tbName.contains("topicNum_")) {
            sql = "CREATE TABLE " + dbName + "." + tbName + " (\n"
                    + "  `pagetitle` varchar(255) NOT NULL,\n"
                    + "  `num` int(11) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`pagetitle`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        } else if (tbName.contains("userInf_")) {
            sql = "CREATE TABLE " + dbName + "." + tbName + " (\n"
                    + "  `userID` varchar(100) NOT NULL,\n"
                    + "  `inf` double(255,16) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`userID`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        } else if (tbName.contains("_target")) {
            sql = "CREATE TABLE " + dbName + "." + tbName + " (\n"
                    + "  `userID` varchar(100) NOT NULL,\n"
                    + "  `inf1` double(255,16) DEFAULT NULL,\n"
                    + "  `inf2` double(255,16) DEFAULT NULL,\n"
                    + "  `num` double(255,16) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`userID`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        }
        System.out.println(sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            flag = false;
//			e.printStackTrace();
        }
        return flag;
    }

    public boolean createMiddleTB(String dbName, String tbName_middle, String tbName_input) {
        boolean flag = true;
        String sql = "";
        sql = "INSERT INTO " + dbName + "." + tbName_middle + " SELECT pageTitle,COUNT(*) FROM " + dbName + "." + tbName_input + " GROUP BY pageTitle";
        System.out.println(sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            flag = false;
//			e.printStackTrace();
        }
        return flag;
    }

    public boolean insertOutTB(String dbName, String tbName_input, String tbName_middle, String tbName_output) {
        boolean flag = true;
        String sql = "";
        sql = "INSERT INTO " + dbName + "." + tbName_output + " (SELECT * FROM " + dbName + "." + tbName_input + " WHERE pageTitle IN (SELECT pageTitle FROM " + dbName + "." + tbName_middle + " WHERE num>=50))";
        System.out.println(sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            flag = false;
//			e.printStackTrace();
        }
        return flag;
    }

    public HashSet<String> queryUserInfoByTopic(String dbName, String tbName_content, String topic) {
        HashSet<String> list = new HashSet<>();
        String sql = "SELECT userUrl FROM " + dbName + "." + tbName_content + " WHERE pageTitle=\"" + topic + "\"";
        System.out.println(sql);
        ResultSet rs = null;
        try {
            rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                String url = rs.getString(1);
                String uid = parseUID(url);
                list.add(uid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public HashMap<String, Integer> queryUserInfo(String dbName, String tbName_user) {
        HashMap<String, Integer> map = new HashMap<>();
        String sql = "SELECT userID,credits FROM " + dbName + "." + tbName_user;
        System.out.println(sql);
        ResultSet rs = null;
        try {
            rs = conn.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                String uid = rs.getString(1);
                int aInt = rs.getInt(2);
                map.put(uid, aInt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    private String parseUID(String userUrl) {
        String userID = "";
        if (userUrl.contains("gfan")) {
            userID = Regex.getNum(userUrl);
        }
        if (userUrl.contains("anzhi")) {
            userID = Regex.getNum(userUrl);
        }
        if (userUrl.contains("in189")) {
            userID = Regex.getNum_s(userUrl);
        }
        return userID;
    }

    public void queryTopicPostAndTime(String dbName, String tbName, int year, int maxID) {
        String sql = "";
        if (maxID > 0) {
            sql = "SELECT pageTitle,userID,dateTimes FROM " + dbName + "." + tbName + "  where ID<=" + maxID + " ORDER BY dateTimes ASC;";
        } else {
            sql = "SELECT pageTitle,userID,dateTimes FROM " + dbName + "." + tbName + " ORDER BY dateTimes ASC;";
        }
        System.out.println(sql);
        ResultSet resultSet = null;
        String pageTitle, userID, dateTime;
        try {
            resultSet = conn.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                pageTitle = resultSet.getString(1);
                userID = resultSet.getString(2);
                dateTime = resultSet.getString(3);
                addToTopicUserPostCount(pageTitle, userID);
                addToTopicTimeList(pageTitle, dateTime);
                addToTopicUserList(pageTitle,userID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<String, ArrayList<String>> getTopicTimeList() {
        return topicTimeList;
    }

    private void addToTopicUserPostCount(String pageTitle, String userID) {
        if (topicUserPostCount == null) {
            topicUserPostCount = new HashMap<>();
        }
        if (!topicUserPostCount.containsKey(pageTitle)) {
            topicUserPostCount.put(pageTitle, new HashMap());
        }
        if (!topicUserPostCount.get(pageTitle).containsKey(userID)) {
            topicUserPostCount.get(pageTitle).put(userID, new Integer[1]);
            topicUserPostCount.get(pageTitle).get(userID)[0] = 0;
        }
        topicUserPostCount.get(pageTitle).get(userID)[0]++;
    }

    private void addToTopicTimeList(String pageTitle, String dateTime) {
        if (topicTimeList == null) {
            topicTimeList = new HashMap<>();
        }
        if (!topicTimeList.containsKey(pageTitle)) {
            topicTimeList.put(pageTitle, new ArrayList());
        }
        topicTimeList.get(pageTitle).add(dateTime);
    }

    public HashMap<String, HashMap<String, Integer[]>> getTopicUserPostCount() {
        return topicUserPostCount;
    }

    public HashMap<String, String[]> queryUserFirstPostTime(String dbName, String tbName, int year, int maxID) {
        String sql = "";
        sql = "SELECT userID,min(dateTimeS) FROM  " + dbName + "." + tbName + " GROUP BY userID;";
        if (maxID > 0) {
            sql = "SELECT userID,min(dateTimeS) FROM  " + dbName + "." + tbName + "  where ID<=" + maxID + " GROUP BY userID;";
        }
        System.out.println(sql);
        ResultSet resultSet = null;
        String userID, dateTimes;
        try {
            resultSet = conn.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                userID = resultSet.getString(1);
                dateTimes = resultSet.getString(2);
                addToUserFirstPost(userID, dateTimes);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userFirstPostTime;
    }

    private void addToUserFirstPost(String userID, String dateTimes) {
        if (userFirstPostTime == null) {
            userFirstPostTime = new HashMap<>();
        }
        if (!userFirstPostTime.containsKey(userID)) {
            userFirstPostTime.put(userID, new String[1]);
        }
        userFirstPostTime.get(userID)[0] = dateTimes;
    }

    public HashMap<String, String[]> queryTopicHitCommentCount(String dbName, String blockName, String tbName) {
        String sql = "";
        sql = "SELECT b.pageTitle,a.hitCount,a.commentCount FROM " + dbName + "." + blockName + " AS a , " + dbName + "." + tbName + "  AS b WHERE b.id=0 and a.contentUrl = b.pageUrl";
        System.out.println(sql);
        ResultSet resultSet = null;
        String pageTitle, hitCount, CommentCount;
        try {
            resultSet = conn.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                pageTitle = resultSet.getString(1);
                hitCount = resultSet.getString(3);
                CommentCount = resultSet.getString(2);
                addTopicHitCommentCount(pageTitle, hitCount, CommentCount);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topicHitCommentCount;
    }

    private void addTopicHitCommentCount(String pageTitle, String hitCount, String CommentCount) {
        if (topicHitCommentCount == null) {
            topicHitCommentCount = new HashMap<>();
        }
        if (!topicHitCommentCount.containsKey(pageTitle)) {
            topicHitCommentCount.put(pageTitle, new String[2]);
        }
        topicHitCommentCount.get(pageTitle)[0] = hitCount;
        topicHitCommentCount.get(pageTitle)[1] = CommentCount;
    }

    public HashMap<String, Integer[]> queryUserPostCount(String dbName, String tbName_content, int year) {
        String sql = "";
        sql = "SELECT userID,COUNT(*) FROM  " + dbName + "." + tbName_content + " WHERE YEAR(DATE(dateTimeS))<" + year + " GROUP BY userID";
        System.out.println(sql);
        ResultSet resultSet = null;
        String userID;
        int postCount;
        try {
            resultSet = conn.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                userID = resultSet.getString(1);
                postCount = resultSet.getInt(2);
                addToUserPostCount(userID, postCount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userPostCount;
    }

    private void addToUserPostCount(String userID, int postCount) {
        if (userPostCount == null) {
            userPostCount = new HashMap();
        }
        if (!userPostCount.containsKey(userID)) {
            Integer[] temp = new Integer[1];
            temp[0] = 0;
//            temp[1]=0;
            userPostCount.put(userID, temp);
        }
        userPostCount.get(userID)[0] += postCount;
    }
 
    public HashMap<String, ArrayList<String[]>> getTopic_user_comment_RS() {
        return topic_user_comment_RS;
    }


    public HashMap<String, HashSet<String>> getTopicUserSet() {
        return topicUserSet;
    }

    public boolean insertTB_user(String dbName, String tbName_userInf, ArrayList<String[]> userInf_store) {
        boolean flag = true;
        String sql = "";
        String s0 = "";
        for (int i = 0; i < userInf_store.size(); i++) {
            s0 += "(";
            s0 += "\"" + userInf_store.get(i)[0] + "\",\"" + userInf_store.get(i)[1] + "\"";
            s0 += "),";
        }
        s0 = s0.substring(0, s0.length() - 1);
        sql = "replace into " + dbName + "." + tbName_userInf + " (userID,inf) values " + s0;
        System.out.println("(" + userInf_store.size() + ")" + sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException ex) {
//            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public HashMap<String, Integer[]> queryUserInfo_hit_comment_num(String dbName, String tbName, int year) {
        HashMap<String, Integer[]> result=new HashMap<>();
        String sql="";
        if(tbName.contains("cars")){
            sql="SELECT userID,HitCount,CommentCount FROM  " + dbName + "." + tbName + "  WHERE YEAR(DATE(dateTimeS))="+year;
        }else{
            sql="SELECT userID,HitCount,CommentCount FROM  " + dbName + "." + tbName + "  WHERE YEAR(DATE(dateTimeS))="+year;
        }
        ResultSet rs=null;
        System.out.println(sql);
        try {
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                String userID=rs.getString(1);
                int hitNum=rs.getInt(2);
                int commentNum=rs.getInt(3);
                if(!result.containsKey(userID)){
                    Integer[]temp=new Integer[3];
                    temp[0]=0;
                    temp[1]=0;
                    temp[2]=0;
                    result.put(userID, temp);
                }
                result.get(userID)[0]+=hitNum;
                result.get(userID)[1]+=commentNum;
                result.get(userID)[2]+=1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean insertTB_user2(String dbName, String tbName_userInf, ArrayList<String[]> userInf_store) {
        boolean flag = true;
        String sql = "";
        String s0 = "";
        for (int i = 0; i < userInf_store.size(); i++) {
            s0 += "(";
            s0 += "\"" + userInf_store.get(i)[0] + "\",\"" + userInf_store.get(i)[1] + "\",\""+userInf_store.get(i)[2]+"\",\""+userInf_store.get(i)[3]+"\"";
            s0 += "),";
        }
        s0 = s0.substring(0, s0.length() - 1);
        sql = "replace into " + dbName + "." + tbName_userInf + " (userID,inf1,inf2,num) values " + s0;
        System.out.println("(" + userInf_store.size() + ")" + sql);
        try {
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public HashMap<String, Double> queryUserInf_x(String dbName, String tbName_x) {
        HashMap<String, Double> userInfo_x=new HashMap<>();
        String sql = "select * from "+dbName+"."+tbName_x;
        ResultSet rs=null;
        System.out.println(sql);
        try {
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                String userID = rs.getString(1);
                double inf = rs.getDouble(2);
                userInfo_x.put(userID, inf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userInfo_x;
    }

    public HashMap<String, Double[]> queryUserInf_y(String dbName, String tbName_y) {
        HashMap<String, Double[]> userInfo_y=new HashMap<>();
        String sql = "select * from "+dbName+"."+tbName_y;
        ResultSet rs=null;
        System.out.println(sql);
        try {
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                String userID = rs.getString(1);
                double inf1 = rs.getDouble(2);
                double inf2 = rs.getDouble(3);
                double inf3 = rs.getDouble(4);
                Double[] inf=new Double[3];
                inf[0]=inf1;
                inf[1]=inf2;
                inf[2]=inf3;
                userInfo_y.put(userID, inf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userInfo_y;
    }

    public HashMap<String, String[]> queryTopicHitCommentCount2(String dbName, String blockName, String tbName_year) {
        HashMap<String, String[]> result=new HashMap<>();
        String sql="";
        sql="SELECT pageTitle,COUNT(*) FROM "+dbName+"."+tbName_year+" GROUP BY pageTitle";
        ResultSet resultSet=null;
        System.out.println(sql);
        String pageTitle, hitCount, CommentCount;
        try {
            resultSet = conn.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                pageTitle = resultSet.getString(1);
                hitCount = "0";
                CommentCount = resultSet.getString(2);
                addTopicHitCommentCount(pageTitle, hitCount, CommentCount);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topicHitCommentCount;
    }
    public HashMap<String, Integer> queryTopicCommentCount(String dbName,String tbName_year) {
        String sql="";
        sql="SELECT pageTitle,COUNT(*) FROM "+dbName+"."+tbName_year+" GROUP BY pageTitle";
        ResultSet resultSet=null;
        System.out.println(sql);
        String pageTitle;
        int CommentCount;
        try {
            resultSet = conn.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                pageTitle = resultSet.getString(1);
                CommentCount = resultSet.getInt(2);
                addTopicCommentCount(pageTitle, CommentCount);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topicCommentCount;
    }

    private void addTopicCommentCount(String pageTitle, int CommentCount) {
        if(topicCommentCount==null){
            topicCommentCount=new HashMap<>();
        }
        if(!topicCommentCount.containsKey(pageTitle)){
            topicCommentCount.put(pageTitle, CommentCount);
        }
    }

    private void addToTopicUserList(String pageTitle, String userID) {
        if(topicUserList==null){
            topicUserList=new HashMap<>();
        }
        if(!topicUserList.containsKey(pageTitle)){
            topicUserList.put(pageTitle, new ArrayList<String>());
        }
        topicUserList.get(pageTitle).add(userID);
    }
    ////////////
    private ArrayList<String> timeList;
    private HashMap<String,Integer> dayNum;
    public void queryCommentTimeInfo(String dbName, String tbName) {
        timeList=new ArrayList<>();
        dayNum=new HashMap<>();
        String sql="";
        sql="SELECT DATE(dateTimeS),COUNT(*) FROM  "+dbName+"."+tbName+"  GROUP BY DATE(dateTimeS) ORDER BY DATE(dateTimeS) ASC";
        ResultSet rs=null;
        try {
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                String day = rs.getString(1);
                int num=rs.getInt(2);
                timeList.add(day);
                dayNum.put(day, num);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }

    public HashMap<String, Integer> getDayNum() {
        return dayNum;
    }

    public void queryTopicUserList(String dbName, String tbName, String starTime, String endTime) {
        String sql="";
        sql="SELECT pageTitle,userID FROM   "+dbName+"."+tbName+"   WHERE DATE(dateTimeS) BETWEEN \""+starTime+"\" AND \""+endTime+"\" ";
        ResultSet rs=null;
        try {
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                String topic = rs.getString(1);
                String userID=rs.getString(2);
                if(topic.contains("天下第一拆")){
                    topic="天下第一拆";
                }else if(topic.contains("西街拆迁")||topic.contains("西街百姓")||
                        topic.contains("强拆")||topic.contains("商州西街")||topic.contains("西街群众")
                        ||topic.contains("西街被拆")||topic.contains("西街土地")||topic.contains("西街乡党")||topic.contains("商洛西街")
                        ||topic.contains("西街马甲")
                        ||topic.contains("西街请愿")
                        ||topic.contains("西街痕")
                        ||topic.contains("西街吟")
                        ||topic.contains("西街项目")
                        ||topic.contains("西街社区")||topic.contains("街谈巷议")||topic.contains("西街旧城") ||topic.contains("西街作认购") ||topic.contains("西街葛某")                        
                        ){
                    topic="西街拆迁";
                }
//                    else if(topic.contains("西街百姓")){
//                    topic="西街百姓";
//                }else{
//                    if(topic.contains("商州西街")){
//                        topic="商州西街";
//                    }else if(topic.contains("西街群众")){
//                        topic="西街群众";
//                    }else if(topic.contains("西街土地")){
//                        topic="西街土地";
//                    }else if(topic.contains("西街乡党")){
//                        topic="西街乡党";
//                    }else if(topic.contains("商洛西街")){
//                        topic="商洛西街";
//                    }else if(topic.contains("强拆")){
//                        topic="强拆";
//                    }else if(topic.contains("西街被拆")){
//                        topic="西街被拆";
//                    }
//                }
                addToTopicUserSet(topic, userID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addToTopicUserSet(String topic, String userID) {
        if(topicUserSet==null){
            topicUserSet=new HashMap<>();
        }
        if(!topicUserSet.containsKey(topic)){
            topicUserSet.put(topic, new HashSet<String>());
        }
        topicUserSet.get(topic).add(userID);
    }

    public ArrayList<Integer> queryUserCredits(String dbName, String tbName_targetuser, String tbName_user) {
        ArrayList<Integer> u_info=new ArrayList<>();
        String sql="";
        sql="SELECT credits fROM "+dbName+"."+tbName_user+"  WHERE userName in (SELECT userName FROM "+dbName+"."+tbName_targetuser+")";
        ResultSet rs=null;
        System.out.println(sql);
        try {
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                u_info.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return u_info;
    }

    public ArrayList<Integer> queryUserCredits(String dbName, String tbName_user) {
        ArrayList<Integer> u_info=new ArrayList<>();
        String sql="";
        sql="SELECT credits fROM "+dbName+"."+tbName_user;
        ResultSet rs=null;
        System.out.println(sql);
        try {
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                u_info.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return u_info;
    }


    public ArrayList<Integer> queryUserCredits2(String dbName, String tbName_user, String tbName_content, String topicID, int maxID) {
         ArrayList<Integer> u_info=new ArrayList<>();
        String sql="";
        sql="SELECT  credits FROM "+dbName+"."+tbName_user+"   WHERE userName in (SELECT userID FROM "+dbName+"."+tbName_content+"   WHERE pageTitle=\""+topicID+"\" and id<="+maxID+")";
        System.out.println(sql);
        ResultSet rs=null;
        try {
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                u_info.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u_info;
    }
    
}

