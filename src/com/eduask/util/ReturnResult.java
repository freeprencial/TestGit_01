package com.eduask.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReturnResult<T> {
	private Integer statusId;
	private Object msg;
	private List<T> list = new ArrayList<T>();
	
	public String toJson() throws IllegalArgumentException, IllegalAccessException{
		
		StringBuffer json = new StringBuffer();
		
		if(this!=null){
			json.append("{'statusId':"+statusId+",'msg':'"+msg+"','list':[");
			//循环Collection
			Field[] fields = null;
			if(list.size()>0){
				Class cla = list.get(0).getClass();
				fields = cla.getDeclaredFields();
			}
	
			for(int k = 0;k<list.size();k++){
				json.append("{");
				for(int i=0;i<fields.length;i++){
					fields[i].setAccessible(true);//获取权限
					
					String typeStr = fields[i].getType().toString();
					if(typeStr.indexOf("String")!=-1){
						json.append("'"+fields[i].getName()+"':'"+fields[i].get(list.get(k))+"'");
					}else if(typeStr.indexOf("Date")!=-1){
						
						json.append("'"+fields[i].getName()+"':'"+new SimpleDateFormat("yyyy-MM-dd").format((Date)(fields[i].get(list.get(k))))+"'");
					}else{
						json.append("'"+fields[i].getName()+"':"+fields[i].get(list.get(k)));
					}
					
					if(i!=fields.length-1){
						
						json.append(",");
					}
					
				}
				
				if(k==list.size()-1){
					json.append("}");
				}else{
					json.append("},");	
				}

			}
			

			json.append("]}");
			
		}
		
		return json.toString();
	}
	
	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	
	
	

}
