package storeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataSet.ExtractResultSet;

public class StoreMysql {
	DataSource ds;
	Connection conn;
     public StoreMysql(){
    	ds = new DataSource();
 		conn = ds.getConnection();
     }
     
     public void close(){
    	 ds.close();
     }
	/**
	 * **********************************判断数据库表是否存在*****************************
	 * *****
	 * 
	 * @param dbName
	 *            数据库名
	 * @param tbName
	 *            表名
	 * @return flag success/fail
	 */
	public boolean isExist(String dbName, String tbName) {
		boolean flag = false;
		String sql = "";
		sql = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA=\""
				+ dbName + "\" and TABLE_NAME=\"" + tbName + "\";";
		ResultSet resultSet = null;
		try {
			resultSet = conn.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ds.close();
		return flag;
	}

	/**
	 * **********************************新建数据库表*********************************
	 * *
	 * 
	 * @param tbName
	 * @param dbName
	 * @return flag
	 */
	public boolean createTB(String tbName, String dbName) {
		boolean flag = true;
		String sql = "";
		if (tbName.contains("block")) {
			sql = "CREATE TABLE " + dbName + "." + tbName
					+ "(contentUrl varchar(255) NOT NULL DEFAULT \"\","
					+ "contentTitle text DEFAULT NULL,"
					+ "dateTimeS varchar(30) DEFAULT NULL,"
					+ "dateTime datetime DEFAULT NULL,"
					+ "userUrl varchar(255) DEFAULT NULL,"
					+ "userID varchar(100) DEFAULT NULL,"
					+ "userName varchar(100) DEFAULT NULL,"
					+ "userContent text,pageTitle varchar(255) DEFAULT NULL,"
					+ "pageType varchar(30) DEFAULT NULL,"
					+ "commentCount int(11) DEFAULT NULL,"
					+ "hitCount int(11) DEFAULT NULL,"
					+ "pageUrl varchar(255) DEFAULT NULL,"
					+ "isCrawled char(1) DEFAULT NULL,"
					+ "support int(11) DEFAULT NULL,"
					+ "oppose int(11) DEFAULT NULL,"
					+ "collect int(11) DEFAULT NULL,"
					+ "PRIMARY KEY (contentUrl)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		}
		if (tbName.contains("content")) {
			sql = "CREATE TABLE " + dbName + "." + tbName
					+ "( ID int(11) NOT NULL,"
					+ "pageUrl varchar(255) NOT NULL DEFAULT \"\","
					+ "pageContent text DEFAULT NULL,"
					+ "pageTitle varchar(255) DEFAULT NULL,"
					+ "dateTimeS varchar(30) DEFAULT NULL,"
					+ "dateTime datetime DEFAULT NULL,"
					+ "hitCount int(11) DEFAULT NULL,"
					+ "PRIMARY KEY (ID,pageUrl)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		}
		System.out.println(sql);
		try {
			conn.prepareStatement(sql).executeUpdate();
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
    
	/**
	 * **********************************存储block信息******************************
	 * ****
	 * 
	 * @param tbName
	 * @param dbName
	 * @param result
	 * @return
	 */
	public boolean insertBlockDB(String tbName, String dbName,
			ArrayList<ExtractResultSet> result) {
		boolean flag = insertDB(tbName, dbName, result);
		return flag;
	}

	/**
	 * @param tbName
	 * @param dbName
	 * @param result
	 * @return
	 */
	public boolean insertDB(String tbName, String dbName,
			ArrayList<ExtractResultSet> result) {
		// 存储block表
		String sql = "";
		String s0 = "";
		for (int i = 0; i < result.size(); i++) {

			ExtractResultSet data = result.get(i);
			s0 += "(";
			s0 += "\"" + data.getContentUrl() + "\"," + "\""
					+ data.getContentTitle() + "\"," + "\""
					+ data.getDateTimes() + "\"," + "\"" + data.getUserUrl()
					+ "\"," + "\"" + data.getUserID() + "\",\""
					+ data.getUserName() + "\",\"" +data.getUserContent()+ "\",\""+ data.getCommentCount()
					+ "\"," + "\"" + data.getHitCount() + "\"," + "\""
					+ data.getPageTitle() + "\"," + "\"" + data.getPageUrl()
					+ "\"," + "\"" + data.getIsCrawled() + "\",\""
					+ +data.getSupport() + "\",\"" + +data.getOppose()
					+ "\",\"" + data.getCollect() + "\",";
			s0 = s0.substring(0, s0.length() - 1);
			s0 += "),";

		}
		s0 = s0.substring(0, s0.length() - 1);
		sql = "insert into "
				+ dbName
				+ "."
				+ tbName
				+ " (contentUrl,contentTitle,dateTimeS,userUrl,userID,userName,userContent,commentCount,hitCount,pageTitle,pageUrl,isCrawled,support,oppose,collect)  values "
				+ s0;
		boolean flag = true;
		System.out.println("(" + result.size() + ")" + sql);
		try {
			conn.prepareStatement(sql).executeUpdate();
			// connection.prepareStatement(sql).execute();
		} catch (SQLException ex) {
//			Logger.getLogger(StoreMysql.class.getName()).log(Level.SEVERE,
//					null, ex);
			String message = ex.getMessage();
			System.out.println(message);
			flag = false;
		}
		ds.close();
		return flag;

	}

	/**
	 * **********************************存储content信息****************************
	 * ******
	 * 
	 * @param tbName
	 * @param dbName
	 * @param result
	 * @return
	 */
	public boolean insertContentDB(String tbName, String dbName,
			ArrayList<ExtractResultSet> result) {
		// 存储content表
		String sql = "";
		String s0 = "";
		for (int i = 0; i < result.size(); i++) {
			s0 += "(";
			ExtractResultSet data = result.get(i);
			s0 += "\"" + data.getID() + "\"," 
					+"\""  + data.getHitCount() + "\","
					+ "\"" + data.getPageUrl()+ "\"," 
					+ "\"" + data.getPageTitle() + "\"," 
					+ "\""+ data.getDateTimes() + "\"," 
					+ "\""+ data.getPageContent() + "\"," ;
			s0 = s0.substring(0, s0.length() - 1);
			s0 += "),";
		}
		if(s0.length()==0){
			return false;
		}
		s0 = s0.substring(0, s0.length() - 1);
		sql = "insert into "
				+ dbName
				+ "."
				+ tbName
				+ "(ID,hitCount,pageUrl,pageTitle,dateTimeS,pageContent)  values "
				+ s0;
		boolean flag = true;
//		System.out.println(sql);
		System.out.println("(" + result.size() + ")" + sql);
		try {
			conn.prepareStatement(sql).executeUpdate();
			// connection.prepareStatement(sql).execute();
		} catch (SQLException ex) {
			Logger.getLogger(StoreMysql.class.getName()).log(Level.SEVERE,
					null, ex);
			flag = false;
		}
		ds.close();
		return flag;

	}
    
	
	/**
	 * **********************************存储userUrlList信息****************************
	 * ******
	 * 
	 * @param tbName
	 * @param dbName
	 * @param result
	 * @return
	 */
	public boolean insertUserDB(String tbName, String dbName,
			ArrayList<ExtractResultSet> result) {
		// 存储userUrlList表
		String sql = "";
		String s0 = "";
		for (int i = 0; i < result.size(); i++) {
			s0 += "(";
			ExtractResultSet data = result.get(i);
			s0 += "\"" + data.getUserName() + "\"," 
					+"\""  + data.getUrl() + "\","
					+ "\"" + data.getRemark()+ "\","  ;
			s0 = s0.substring(0, s0.length() - 1);
			s0 += "),";
		}
		if(s0.length()==0){
			return false;
		}
		s0 = s0.substring(0, s0.length() - 1);
		sql = "replace into "
				+ dbName
				+ "."
				+ tbName
				+ "(userName,url,remark)  values "
				+ s0;
		boolean flag = true;
//		System.out.println(sql);
		System.out.println("(" + result.size() + ")" + sql);
		DataSource dataSource = new DataSource();
		Connection connection = dataSource.getConnection();
		try {
			connection.prepareStatement(sql).executeUpdate();
			// connection.prepareStatement(sql).execute();
		} catch (SQLException ex) {
			Logger.getLogger(StoreMysql.class.getName()).log(Level.SEVERE,
					null, ex);
			flag = false;
		}
		dataSource.close();
		return flag;

	}
	
	public boolean insertUserNet(String tbName, String dbName,
			ArrayList<ExtractResultSet> result) {
		// 存储userUrlList表
		String sql = "";
		String s0 = "";
		for (int i = 0; i < result.size(); i++) {
			s0 += "(";
			ExtractResultSet data = result.get(i);
			s0 += "\"" + data.getMainName() + "\"," 
					+"\""  + data.getSurName() + "\","
					+"\""  + data.getCount() + "\","
					+ "\"" + data.getRemark()+ "\","  ;
			s0 = s0.substring(0, s0.length() - 1);
			s0 += "),";
		}
		if(s0.length()==0){
			return false;
		}
		s0 = s0.substring(0, s0.length() - 1);
		sql = "replace into "
				+ dbName
				+ "."
				+ tbName
				+ "(mainName,surName,count,remark)  values "
				+ s0;
		boolean flag = true;
//		System.out.println(sql);
		System.out.println("(" + result.size() + ")" + sql);
		DataSource dataSource = new DataSource();
		Connection connection = dataSource.getConnection();
		try {
			connection.prepareStatement(sql).executeUpdate();
			// connection.prepareStatement(sql).execute();
		} catch (SQLException ex) {
			Logger.getLogger(StoreMysql.class.getName()).log(Level.SEVERE,
					null, ex);
			flag = false;
		}
		dataSource.close();
		return flag;

	}
	/**
	 * **********************************查询block信息******************************
	 * ****
	 * 
	 * @param tbName
	 * @param dbName
	 * @param userid
	 * @return temp
	 */

	public ArrayList<String> queryBlockByName_withUpdate(String dbName,
			String tbName) {
		ArrayList<String> rlist = new ArrayList<String>();
		DataSource ds = new DataSource();
		String sql = "";
		sql = "select contentUrl from " + dbName + "." + tbName
				+ " where isCrawled=\"" + "f" + "\"";
		// System.out.println(sql);
//		String sqlUpdate = "";
		Connection conn = ds.getConnection();
		ResultSet resultSet = null;
		String url = "";
		try {
			resultSet = conn.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				url = resultSet.getString(1);
//				sqlUpdate = "update " + dbName + "." + tbName
//						+ " set isCrawled=\"" + "t" + "\" where contentUrl=\""
//						+ url + "\";";
				// System.out.println(sqlUpdate);
				// conn.prepareStatement(sqlUpdate).executeUpdate();
				rlist.add(url);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
		ds.close();
		return rlist;
	}
	
	/**根据帖子URL找到所有的参与此帖子的用户
	 * @param dbName
	 * @param tbName
	 * @param URL
	 * @return
	 */
	public ArrayList<String> queryUserByUrl(String dbName, String tbName, String URL) {
			
			ArrayList<String> rlist = new ArrayList<String>();
			DataSource ds = new DataSource();
			
			String sql = "";
			sql = "select userName from " + dbName + "." + tbName
					+ " where pageUrl = \"" + URL + "\"";
	
			Connection conn = ds.getConnection();
			ResultSet resultSet = null;
			String name = "";
			try {
				resultSet = conn.prepareStatement(sql).executeQuery();
				while (resultSet.next()) {
					name = resultSet.getString(2);				
					rlist.add(name);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ds.close();
			return rlist;
		}
	
	 /**获取所有帖子的Url列表
	 * @param dbName
	 * @param tbName
	 * @return
	 */
	public ArrayList<String> queryUrlList(String dbName, String tbName) {
		
		ArrayList<String> rlist = new ArrayList<String>();
		DataSource ds = new DataSource();
		
		String sql = "";
		sql = "select pageUrl from " + dbName + "." + tbName
				+ " where ID = 0";
	
		Connection conn = ds.getConnection();
		ResultSet resultSet = null;
		String url = "";
		try {
			resultSet = conn.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				url = resultSet.getString(1);
				rlist.add(url);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ds.close();
		return rlist;
	}
	/**查询userUrlList的数据
	 * @param dbName
	 * @param tbName
	 * @return
	 */
	public HashMap<String, HashMap<String, Integer>> queryAllDataOfUserUrlList(String dbName, String tbName) {
		HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
		
		String sql = "";
		sql = "select userName,url from " + dbName + "." + tbName;
	
		ResultSet resultSet = null;
		String userName = "";
		String url = "";
		try {
			resultSet = conn.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				userName = resultSet.getString(1);
				url = resultSet.getString(2);
				if(map.containsKey(userName)){
					map.get(userName).put(url, 0);
				}else{
					HashMap<String, Integer> m = new HashMap<String, Integer>();
					m.put(url, 0);
					map.put(userName, m);
				}
//				System.out.println(i + "\t" + userName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public ArrayList<String[]> queryUserName(String dbName, String tbName) {
		ArrayList<String[]> rlist = new ArrayList<String[]>();
		DataSource ds = new DataSource();
		
		String sql = "";
		sql = "select userName,pageUrl from " + dbName + "." + tbName;
	
		Connection conn = ds.getConnection();
		ResultSet resultSet = null;
		String userName = "";
		String url = "";
		try {
			resultSet = conn.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				userName = resultSet.getString(1);
				url = resultSet.getString(2);
				String[] temp = new String[1];
				temp[0] = userName;
				temp[1] = url;
				rlist.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ds.close();
		return rlist;
	}

	public HashSet<String> queryAllUser(String dbName, String tbName) {
		HashSet<String> map = new HashSet<String>();
		
		String sql = "";
		sql = "select userName from " + dbName + "." + tbName;
	
		ResultSet resultSet = null;
		String userName = "";
		try {
			resultSet = conn.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				userName = resultSet.getString(1);
				if(map.contains(userName)){
					continue;
				}else{
					map.add(userName);
				}
//				System.out.println(i + "\t" + userName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
//	public static void main(String[] args){
//		String dbName = "taobao";
//		String tbName = "userurllist";
//		StoreMysql sql = new StoreMysql();
//		HashMap<String, Integer> queryAllUser = sql.queryAllUser(dbName, tbName);
//	}
}
