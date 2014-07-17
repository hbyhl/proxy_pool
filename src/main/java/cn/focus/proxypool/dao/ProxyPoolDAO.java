package cn.focus.proxypool.dao;

import java.util.List;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import cn.focus.proxypool.model.ProxyPool;
@DAO(catalog="focus.proxy")
public interface ProxyPoolDAO {

	@SQL("SELECT  id, ip, port, urlname, level, status, createtime, updatetime, failures, source FROM proxy_pool WHERE id = :1")
	ProxyPool get(Integer id );
	
	@SQL("SELECT  id, ip, port, urlname, level, status, createtime, updatetime, failures, source FROM proxy_pool WHERE ip = :1 and port = :2 and urlname = :3 and level = :4")
	ProxyPool get(String ip, Integer port, String urlname, Integer level);
	
	@SQL("SELECT  id, ip, port, urlname, level, status, createtime, updatetime, failures, source FROM proxy_pool WHERE id IN (:1)")
	List<ProxyPool> getList(List<Integer> idList );
	
	@SQL("SELECT  id, ip, port, urlname, level, status, createtime, updatetime, failures, source FROM proxy_pool WHERE failures =0 and urlname =:1 and level =:2 and status =:3")
	List<ProxyPool> getUsableList(String urlname, Integer level, Integer status );
	
	@SQL("SELECT  id, ip, port, urlname, level, status, createtime, updatetime, failures, source FROM proxy_pool WHERE status = 1")
	List<ProxyPool> getUsableList( );
	
	
	@SQL("SELECT  id, ip, port, urlname, level, status, createtime, updatetime, failures, source FROM proxy_pool WHERE id IN (:1) ORDER BY find_in_set(id , :2)")
	List<ProxyPool> getOrderList(List<Integer> idList, String orderIdsStr );
    
	    @ReturnGeneratedKeys
	@SQL("INSERT INTO proxy_pool ("+
			 	   		  /* "#if(:1.id!=null){id}"+*/
			    	       "#if(:1.ip!=null){ip}"+
	    	       "#if(:1.port!=null){,port}"+
	    	       "#if(:1.urlname!=null){,urlname}"+
	    	       "#if(:1.level!=null){,level}"+
	    	       "#if(:1.status!=null){,status}"+
	    	       "#if(:1.createtime!=null){,createtime}"+
	    	       "#if(:1.updatetime!=null){,updatetime}"+
	    	       "#if(:1.failures!=null){,failures}"+
	    	       "#if(:1.source!=null){,source}"+
	    		") VALUES("+
	    		 /*"#if(:1.id!=null){:1.id}"+*/
	    	    	       "#if(:1.ip!=null){:1.ip}"+
	    	       "#if(:1.port!=null){,:1.port}"+
	    	       "#if(:1.urlname!=null){,:1.urlname}"+
	    	       "#if(:1.level!=null){,:1.level}"+
	    	       "#if(:1.status!=null){,:1.status}"+
	    	       "#if(:1.createtime!=null){,:1.createtime}"+
	    	       "#if(:1.updatetime!=null){,:1.updatetime}"+
	    	       "#if(:1.failures!=null){,:1.failures}"+
	    	       "#if(:1.source!=null){,:1.source}"+
	    		")") 
	Long save(ProxyPool proxypool );
	
		@SQL("UPDATE proxy_pool SET id=:1.id " +
		    		    	       "#if(:1.ip!=null){,ip=:1.ip}"+
	    		    	       "#if(:1.port!=null){,port=:1.port}"+
	    		    	       "#if(:1.urlname!=null){,urlname=:1.urlname}"+
	    		    	       "#if(:1.level!=null){,level=:1.level}"+
	    		    	       "#if(:1.status!=null){,status=:1.status}"+
	    		    	       "#if(:1.createtime!=null){,createtime=:1.createtime}"+
	    		    	       "#if(:1.updatetime!=null){,updatetime=:1.updatetime}"+
	    		    	       "#if(:1.failures!=null){,failures=:1.failures}"+
	    		    	       "#if(:1.source!=null){,source=:1.source}"+
	    	    " WHERE id=:1.id ")
	void update(ProxyPool proxypool );
		
		@SQL("UPDATE proxy_pool SET id=:1.id " +
			    	       "#if(:1.ip!=null){,ip=:1.ip}"+
		 	       "#if(:1.port!=null){,port=:1.port}"+
		 	       "#if(:1.urlname!=null){,urlname=:1.urlname}"+
		 	       "#if(:1.level!=null){,level=:1.level}"+
		 	       "#if(:1.status!=null){,status=:1.status}"+
		 	       "#if(:1.createtime!=null){,createtime=:1.createtime}"+
		 	       "#if(:1.updatetime!=null){,updatetime=:1.updatetime}"+
		 	       "#if(:1.failures!=null){,failures=:1.failures}"+
		 	       "#if(:1.source!=null){,source=:1.source}"+
				" WHERE ip=:1.ip and port=:1.port and urlname=:1.urlname and level=:1.level ")
	void updatePlus(ProxyPool proxypool );
	
	@SQL("DELETE FROM proxy_pool WHERE id= :1")
	int delete(Integer id);
	
	@SQL("SELECT COUNT(1) FROM proxy_pool")
	int count();
	
}