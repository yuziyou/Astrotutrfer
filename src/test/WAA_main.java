package test;

import timeRank.RankMechanism;

public class WAA_main {
	
	public static void main(String[] args) {
		String dbName = "taobao";
		String tbName = "block_taobao_changyexianfeng";
		int flag = 0;
		
		RankMechanism rm = new RankMechanism(dbName, tbName, flag);
		rm.doRank();
	}
}
