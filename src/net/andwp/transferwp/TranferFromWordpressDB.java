package net.andwp.transferwp;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TranferFromWordpressDB {
	private List<ArchivesTable> archviesData;
	private String xmlStr = "";
	private String fileName;
	
	/**
	 * 获取转换的对象
	 * @return
	 */
	public List<ArchivesTable> getPostData(){
		return archviesData;
	}
	
	/** 
	 * @param filepath 文件全名（带路径）
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws ParseException 
	 */
	public TranferFromWordpressDB (String  fileName) throws IOException, ParserConfigurationException, SAXException, ParseException  {
		this.fileName = fileName;  
		initDOM();
	} 
	
	/**
	 * 初始化DOM  
	 */
	private void initDOM() throws ParserConfigurationException, SAXException, IOException, ParseException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document  doc = builder.parse(new File(this.fileName));
        Element element = doc.getDocumentElement();
        NodeList nodes = element.getElementsByTagName("database").item(0).getChildNodes();
        archviesData = new ArrayList<ArchivesTable>(); 
        int length = nodes.getLength(); 
        for(int index = 0; index < length; index++ ){ 
        	//  依次获取每个table节点
        	NodeList colNodes = nodes.item(index).getChildNodes();
        	ArchivesTable tab = new ArchivesTable(); 
        	for(int collen = colNodes.getLength(); collen>0; collen--){
        		Node node = colNodes.item(collen - 1);
        		
        		if("column".equals(node.getNodeName())){
        			String attbName = ((Element)node).getAttribute("name");
        			if(node.getFirstChild() == null) continue;
        			String value =  node.getFirstChild().getNodeValue();
        			if("post_date".equals(attbName)){
        				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        				tab.setPost_date(sdf.parse(value));
        				continue;
        			}
        			if("post_title".equals(attbName)){
        				tab.setPost_title(value);
        				continue;
        			}
        			if("post_type".equals(attbName)){
        				tab.setPost_type(value);
        				continue;
        			} 
        			if("categries".equals(attbName)){
        				tab.setCategries(value);
        				continue;
        			} 
        			if("post_content".equals(attbName)){
        				tab.setPost_content(value);
        				continue;
        			}   
        			if("post_tag".equals(attbName)){
        				tab.setPost_tag(value);
        				continue;
        			}   
        		}
        	}
//        	System.out.println(length + ": " + tab.toString()); 
        	if(tab.toString() != null)
        		this.archviesData.add(tab); 
        }
	}
	 
	/**
	 * 获取xml 的内容
	 * @description 测试读文件 
	 */
	public String getXmlStr() throws IOException
	{
		this.xmlStr = new FileOperate(fileName).getFileContentAsStr(); 
		return this.xmlStr;
	}
}
