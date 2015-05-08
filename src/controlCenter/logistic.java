package controlCenter;

public class logistic {
    DataSet : 
    	<userName1,userName1,HashSet<url>>;
    	<userName1,userName2,HashSet<url>>;
    NewData:
    	<newUserName, newUrl>;
    	
    for(NewData.list){
    	if(newUserName != userName1){   //与第一个用户名不同
    		if(newUserName != userName2){   //与第一个用户名不同，与第二个用户名不同
    			if(!newUrl.isExist<HashSet<url>>){  //此用户为新加入的用户 （url也不存在）
    				add(newUserName, newUserName, new HashSet<newUrl>);  
    			}else{   //此用户与现有的两个用户参与的是同一个帖子，所以与两个用户均存在关系  （url存在）
    				add(newUserName, userName1, new HashSet<newUrl>);    
    				add(newUserName, userName2, new HashSet<newUrl>);
    			}
    		}else{   //与第一个用户名不同,与第二个用户名相同
    			if(!newUrl.isExist<HashSet<url>>){   //此用户为老用户，但参与了一个新的帖子（原纪录中存在此用户和其他用户参与过其他的帖子）
    				add(newUserName, newUserName, new HashSet<newUrl>);
    			}else{    //此用户为老用户，参与了一个已存在的帖子，即在同一个帖子里不断发言
    				continue;
    			}
    		}
    	}else{   //与第一个用户名相同
    		if(newUserName == userName2){    //与第一个用户名相同,与第二个用户名相同
    			if(!newUrl.isExist<HashSet<url>>){   //此用户为老用户，但参与了一个新的帖子（原来的记录中只有此用户自己一个人参与的若干个帖子）
    				add(userName, userName, hashSet.add(newUrl));
    			}else{   //此用户为老用户，但参与的是一个已存在的帖子，即在同一个帖子里不断发言
    				continue;
    			}
    		}else{  //与第一个用户名相同,与第二个用户名不同
    			if(!newUrl.isExist<HashSet<url>>){ //此用户为老用户，但参与了一个新的帖子
    				add(newUserName, newUserName, new HashSet<newUrl>); 
    			}else{    //此用户参与了同一个帖子
    				continue;      
    			}
    		}
    	}
    } 
}
