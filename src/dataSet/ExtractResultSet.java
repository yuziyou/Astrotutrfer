package dataSet;

public class ExtractResultSet {
	
    public ExtractResultSet(){
    	
    }
    
    private int ID;
	

	private String dateTimes;
    private String dateTime;
    
    private String userID;
    private String userName;
    private String userUrl;
    private String userContent;   
    
    private String contentTitle;
    private String contentUrl;
//    private String contentAuthor;
    
    private String pageUrl;
    private String pageType;
    private String pageTitle;    
    private String pageContent;
    
//    private String replyCount;
    private int hitCount=0;
    private int commentCount=0;
    private int support=0;
    private int oppose=0;
    private int collect=0;
    private String isCrawled;
    public int getID() {
		return ID;
	}
	public void setID(int iD) {
		this.ID = iD;
	}
	public String getDateTimes() {
		return dateTimes;
	}
	public void setDateTimes(String dateTimes) {
		this.dateTimes = dateTimes;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserUrl() {
		return userUrl;
	}
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	public String getUserContent() {
		return userContent;
	}
	public void setUserContent(String userContent) {
		this.userContent = userContent;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
//	public String getContentAuthor() {
//		return contentAuthor;
//	}
//	public void setContentAuthor(String contentAuthor) {
//		this.contentAuthor = contentAuthor;
//	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
//	public String getReplyCount() {
//		return replyCount;
//	}
//	public void setReplyCount(String replyCount) {
//		this.replyCount = replyCount;
//	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getIsCrawled() {
		return isCrawled;
	}
	public void setIsCrawled(String isCrawled) {
		this.isCrawled = isCrawled;
	}
//	@Override
//	public String toString() {
//		return "ExtractResultSet [dateTimes=" + dateTimes + ", dateTime="
//				+ dateTime + ", userID=" + userID + ", userName=" + userName
//				+ ", userUrl=" + userUrl + ", userContent=" + userContent
//				+ ", contentTitle=" + contentTitle + ", contentUrl="
//				+ contentUrl
//				+ ", pageUrl=" + pageUrl + ", pageType=" + pageType
//				+ ", pageTitle=" + pageTitle + ", pageContent=" + pageContent
//				+ ",  hitCount=" + hitCount
//				+ ", commentCount=" + commentCount + ", isCrawled=" + isCrawled
//				+ "]";
//	}
    
    public ExtractResultSet resultFormat(String[] result){
    	ExtractResultSet ers = new ExtractResultSet();
    	return ers;
    }
	public int getSupport() {
		return support;
	}
	public void setSupport(int support) {
		this.support = support;
	}
	public int getOppose() {
		return oppose;
	}
	public void setOppose(int oppose) {
		this.oppose = oppose;
	}
	public int getCollect() {
		return collect;
	}
	public void setCollect(int collect) {
		this.collect = collect;
	}
	private String mainName;
	private String surName;
	private int count;
	private String url;
	private String remark;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMainName() {
		return mainName;
	}
	public void setMainName(String mainName) {
		this.mainName = mainName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	///////////////////////test
	public static void main(String[] args) {
		ExtractResultSet extractResultSet = new ExtractResultSet();
		System.out.println(extractResultSet.getCollect()+"\t"+extractResultSet.getHitCount());
	}
	
}
