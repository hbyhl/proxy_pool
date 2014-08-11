package ProxyPool;

public class ProxyLog{
	private long	Timestamp;	//本条日志产生的时间戳
	private int		ProxyDB;	//代理所在数据库编号（0为主库，1为从库，3为对外抓取的代理池）
	private ProxyPool	Proxy;	//代理IP信息
	private String 	Protocol;	//代理使用的协议名称
	private int	ConnectTime;	//建立连接消耗时间(单位毫秒)
	private int 	Status;		//连接返回状态代码。（200,404等,可自行设计）
	
	private long StartTime;
	private long EndTime;
	
	public ProxyLog(ProxyPool	proxy,int proxydb,String protocol){
		java.util.Date CreateDate = new java.util.Date();
		this.Timestamp= CreateDate.getTime();
		this.Prxoy=new ProxyPool();
		this.Proxy.copy(proxy);
		this.ProxyDB= proxydb;
		this.Protocol=protocol;
		this.ConnectTime=-1;
		this.Status=-1;
		this.StartTime=-1;
		this.EndTime=-1;
		
	}
	public void StartVerification(){
		java.util.Date StartDate = new java.util.Date();
		this.StartTime=StartDate.getTime();

	}
	
	public void EndVerification(int StatusNum){
		java.util.Date EndDate = new java.util.Date();
		long Endtime= EndDate.getTime();
		this.ConnectTime= (int)(Endtime-this.Timestamp);
		this.Status=StatusNum;
	}
	public String getLogs(){
		String logs=String.valueOf(Timestamp)+"|"+
					String.valueOf(this.ProxyDB)+"|"+
					this.Proxy.getId().toString()+"|"+
					this.Proxy.getIp()+"|"+
					this.Proxy.getPort().toString()+"|"+
					this.Proxy.getLevel().toString()+"|"+
					this.Proxy.getSource()+"|"+
					this.Proxy.getUrlname()+"|"+
					"0|"+
					this.Protocol+"|"+
					String.valueOf(this.ConnectTime)+"|"+
					String.valueOf(this.Status);
		
		return logs;
	}

}