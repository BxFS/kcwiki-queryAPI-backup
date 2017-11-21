/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.handler.qtc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kcwiki.redis.JedisPoolUtils;
import static org.kcwiki.spider.akashilist.mainpage.dayid;
import redis.clients.jedis.Jedis;

/**
 *
 * @author iTeam_VEP
 */
public class ControlTower {
    private static Log logger = LogFactory.getLog(ControlTower.class);  
    
    public HashMap controller(String channel, HttpServletRequest request) {
        Jedis jedis = JedisPoolUtils.getJedis(); 
        String result = null ;
        String querystring = null ;
        try{
            if(jedis == null){    
                throw new NullPointerException("Jedis is Null");    
            }  
            switch(channel){
                default:
                    return null;
                case "area":
                    querystring = "area";
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        result = JSON.toJSONString(new org.kcwiki.spider.poidb.mainpage().test());
                        jedis.set(querystring, result);
                    }
                    break;
                case "map":
                    String mapno = request.getParameter("mapno");
                    querystring = "map" + mapno;
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        result = JSON.toJSONString(new org.kcwiki.spider.poidb.mainpage().test1(mapno));
                        
                        jedis.set(querystring, result);
                    }
                    break; 
                case "point":
                    mapno = request.getParameter("mapno");
                    String point = request.getParameter("point");
                    if(point.endsWith("null")){
                        point = null;
                    }else if(point.length()>1){
                        //point = point.substring(0,point.indexOf("(B"));
                        //point = point.substring(0,1);
                        point = point.trim();
                    }
                    String difficulty = request.getParameter("difficulty");
                    String assessment = request.getParameter("assessment");
                    if(Integer.valueOf(mapno) > 300){
                        querystring = "point" + mapno + point + difficulty + assessment ;
                    }
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        result = JSON.toJSONString(new org.kcwiki.spider.poidb.api().test(mapno,point,difficulty,assessment));
                        jedis.set(querystring, result);
                    }
                    break; 
                case "expedition":
                    querystring = "expedition" ;
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        result = JSON.toJSONString(new org.kcwiki.spider.wikiexpedition.mainpage().test());
                        jedis.set(querystring, result);
                    }
                    break; 
                case "mapfast":
                    querystring = "mapfast" ;
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        result = JSON.toJSONString(new org.kcwiki.spider.wikimap.fastSearch().test());
                        jedis.set(querystring, result);
                    }
                    break; 
                case "akashitype":
                    querystring = "akashitype" ;
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        result = JSON.toJSONString(new org.kcwiki.spider.akashilist.mainpage().getTypeList());
                        jedis.set(querystring, result);
                    }
                    break;
                case "akashilist":
                    String type = request.getParameter("type");
                    if(type == null || StringUtils.isBlank(type)) type = "all";
                    querystring = "akashilist" + dayid() + type ;
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        result = JSON.toJSONString(new org.kcwiki.spider.akashilist.mainpage().getItemList(type));
                        jedis.set(querystring, result);
                    }
                    break;
                case "akashiitem":
                    String wid = request.getParameter("wid");
                    String raw = request.getParameter("raw");
                    boolean boolean_raw = false;
                    if(raw == null)
                        raw = "false";
                    querystring = "akashiitem" + wid + raw;
                    
                    if(raw.toLowerCase().equals("true")){
                        boolean_raw = true;
                    }
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        HashMap tmp = new org.kcwiki.spider.akashilist.mainpage().getItemDetail(wid,boolean_raw);
                        result = JSON.toJSONString(tmp);
                        jedis.set(querystring, result);
                    }
                    break;   
                case "thankslist":
                    //String name = request.getParameter("name");
                    querystring = "thankslist" ;
                    result = jedis.get(querystring);
                    if(result == null || result.equals("null")) {
                        result = JSON.toJSONString(new org.kcwiki.spider.akashilist.thankslist().test1());
                        jedis.set(querystring, result);
                    }
                    break;       
            }
        }catch(NullPointerException | NumberFormatException e){    
            JedisPoolUtils.returnBrokenResource(jedis);  
            System.err.println(ExceptionUtils.getStackTrace(e));
            logger.error(e.getMessage(), e);    
        }finally{
                JedisPoolUtils.returnResource(jedis);  
        }
        HashMap<String,Object> data = JSON.parseObject(result,new TypeReference<LinkedHashMap<String, Object>>() {},Feature.OrderedField);
        
        /*JSONObject tmpJobj = new JSONObject(true);
        HashMap<String,Object> tmpHm = new LinkedHashMap<>();
        for(String key:data.keySet()){
            tmpJobj.put(key, data.get(key));
            tmpHm.put(key, data.get(key));
        }
        if(JSON.toJSONString(tmpJobj).equals(JSON.toJSONString(tmpHm))) System.out.println(true);
        if(JSON.toJSONString(tmpJobj).equals(JSON.toJSONString(data))) System.out.println(true);
        if(JSON.toJSONString(tmpHm).equals(JSON.toJSONString(data))) System.out.println(true);*/
        
        return data;
    }
}
