package cn.focus.proxypool.dao;

import java.util.List;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import cn.focus.proxypool.model.ProxyUrls;
@DAO(catalog="focus.proxy")
public interface ProxyUrlsDAO {

	@SQL("SELECT  id, name, url FROM proxy_urls WHERE id = :1")
	ProxyUrls get(Integer id );

	
	@SQL("SELECT  id, name, url FROM proxy_urls WHERE id IN (:1)")
	List<ProxyUrls> getList(List<Integer> idList );
	
	@SQL("SELECT  id, name, url FROM proxy_urls")
	List<ProxyUrls> getAllData( );
	
	@SQL("SELECT  url FROM proxy_urls WHERE name = :1")
	String getUrl(String urlname );
	
	@SQL("SELECT  id, name, url FROM proxy_urls WHERE id IN (:1) ORDER BY find_in_set(id , :2)")
	List<ProxyUrls> getOrderList(List<Integer> idList, String orderIdsStr );
    
	    @ReturnGeneratedKeys
	@SQL("INSERT INTO proxy_urls ("+
			   		"id" + 
	    	       "#if(:1.name!=null){,name}"+
	    	       "#if(:1.url!=null){,url}"+
	    		") VALUES("+
	    		   ":1.id"+
	    	       "#if(:1.name!=null){,:1.name}"+
	    	       "#if(:1.url!=null){,:1.url}"+
	    		")") 
	Long save(ProxyUrls proxyurls );
	
		@SQL("UPDATE proxy_urls SET id=:1.id " +
		    		    	       "#if(:1.name!=null){,name=:1.name}"+
	    		    	       "#if(:1.url!=null){,url=:1.url}"+
	    	    " WHERE id=:1.id ")
	void update(ProxyUrls proxyurls );
	
	@SQL("DELETE FROM proxy_urls WHERE id= :1")
	int delete(Integer id);
	
	@SQL("SELECT COUNT(1) FROM proxy_urls")
	int count();
	
}