package userAnalyse;

import java.util.HashSet;
import java.util.Objects;

public class RelationSet {

	    String userID1;
	    String userID2;
//	    int rdInTopics;
//	    int rdInComment;
	    HashSet<String> urlSet;

	    public int getRdInTopics() {
	        if(urlSet==null||urlSet.isEmpty()){
	            return 0;
	        }else{
	          return urlSet.size(); 
	        }
//	        return rdInTopics;
	    }

	    public String getUserID1() {
	        return userID1;
	    }

	    public String getUserID2() {
	        return userID2;
	    }
	    
	    public void getInfo(){
	        System.out.println(userID1+"\t"+userID2+"\t"+urlSet.size());
	    }
	    
	     public void addRdInTopics(String topic) {
	        if(urlSet==null){
	            urlSet=new HashSet();
	        }
	        urlSet.add(topic);
	    }
	    
//	    public void addRdInTopics() {
//	        this.rdInTopics ++;
//	    }
//
//	    public int getRdInComment() {
//	        return rdInComment;
//	    }
//
//	    public void setRdInComment(int rdInComment) {
//	        this.rdInComment = rdInComment;
//	    }
	    
	    public RelationSet(String userID1, String userID2) {
	        this.userID1 = userID1;
	        this.userID2 = userID2;
//	        rdInTopics = 0;
//	        rdInComment = 0;
	    }

	    
	    @Override
	    public int hashCode() {
	        int hash = 5;
	        return hash;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final RelationSet other = (RelationSet) obj;
	        
	        if (Objects.equals(this.userID1, other.userID1)&&Objects.equals(this.userID2, other.userID2)) {
	            return true;
	        }
	        if (Objects.equals(this.userID2, other.userID1)&&Objects.equals(this.userID1, other.userID2)) {
	            return true;
	        }
	        return false;
	    }
	    
//	    public static void main(String[] args){
//	        ArrayList<UserRelationship> rA = new ArrayList();
//	        UserRelationship r = new UserRelationship("a1","a2");
//	        int index = -1;
//	        if(rA.contains(r)){
//	            index = rA.indexOf(r);
//	            
//	        }  
//	    }
//
	    void addRd(int rd) {
//	        rdInTopics=rd;
	    }
}
