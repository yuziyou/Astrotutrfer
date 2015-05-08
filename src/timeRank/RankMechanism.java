package timeRank;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.HashMap;

import storeUtil.ReadDB;

/**
 *论坛排序机制模拟
 * @author xz
 */
public class RankMechanism {
    private ArrayList<String> rankedList;
    private ArrayList<String> dataList;
    private String dbName;
    private String tbName;
    private int maxListNum=40;
    private int flag=0;
    private HashMap<String,Integer[]> positionCount;

    /**
     * 
     * @return 
     */
    public HashMap<String, Integer[]> getPositionCount() {
        return positionCount;
    }
    /**
     * 
     * @param dbName 数据库
     * @param tbName 表名
     * @param flag 0，位置；1，页面
     */
    public RankMechanism(String dbName, String tbName,int flag) {
        this.dbName = dbName;
        this.tbName = tbName;
        this.flag=flag;
        init();
    }
    
    /**
     * 排序
     */
    public void doRank(){
        String topic="";
        for (int i = 0; i < dataList.size(); i++) {
            topic = dataList.get(i);
            if(i%1000==0){
                System.out.println("..........................."+i+"/"+dataList.size()); 
            }
            updateRankedList(topic);
        }
    }
    
    /**
     * 初始化
     */
    private void init() {
        ReadDB readDB = new ReadDB();
        dataList  = readDB.queryComment(dbName, tbName);
        rankedList=new ArrayList<>();
        positionCount=new HashMap<>();
    }
    
    /**
     * 更新话题列表
     * @param topic 
     */
    private void updateRankedList(String topic) {
        if(!rankedList.contains(topic)){
            rankedList.add(0, topic);
        }else{
            int index = rankedList.indexOf(topic);
            rankedList.remove(index);
//            rankedList.r
            rankedList.add(0, topic);
            if(flag==0){
                addToPositionCount(index);
            }else if(flag==1){
                addToPagePositionCount(index);
            }
            
        }
//        if(rankedList.size()>40){
//            rankedList.remove(rankedList.size()-1);
//        }      
    }

    /**
     * 统计每个位置话题的阅读数量
     * @param index 
     */
    private void addToPositionCount(int index) {
        String position = String.valueOf(index+1);
        if(!positionCount.containsKey(position)){
            Integer[] temp=new Integer[1];
            temp[0]=0;
            positionCount.put(position, temp);
        }
        positionCount.get(position)[0]++;
    }
    /**
     * 统计每个页面话题的阅读数量
     * @param index 
     */
    private void addToPagePositionCount(int index) {
        int page=index/maxListNum+1;
        String position = String.valueOf(page);
        if(!positionCount.containsKey(position)){
            Integer[] temp=new Integer[1];
            temp[0]=0;
            positionCount.put(position, temp);
        }
        positionCount.get(position)[0]++;
    }
    
}

