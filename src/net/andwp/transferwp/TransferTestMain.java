package net.andwp.transferwp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class TransferTestMain {

	private static final String HEAD ="-";
	private static final String TAIL = ".md";
	private static final String OUTPUT_DIR ="../post/";
	private static final String INPUT_FILE ="../puxposts.xml";
	private static final String CONTENT_HEADER="---\n" +
			"layout: post\n" +
			"title: %1$s\n" +
		 	"category: %2$s\n" +
 			"tags: %3$s\n" +
			"---\n" +
			"{%% include JB/setup %%}\n" + 
			"%4$s";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {  
		try { 
			///  输入自定义的导出文件路径
			TranferFromWordpressDB tmp = new TranferFromWordpressDB(INPUT_FILE);
			List<ArchivesTable> tab = filterPostTable(tmp.getPostData());
			generateFileContent(tab.get(0));
			for(ArchivesTable data : tab){
				///  输出文件路径
				FileOperate fo = new FileOperate(OUTPUT_DIR + generateFileName(data));
				fo.saveFile(generateFileContent(data)); 
			} 
//			 System.out.print(tmp.getXmlStr());
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
	}
	
	/** 
	 * @return  返回文件内容
	 */
	private static String generateFileContent(ArchivesTable dt){
		 // 处理标题
		String  tags = dt.getPost_tag();
		if(tags !=null && !"".equals(tags)){
			tags = tags.replaceAll(",", ", ");
			tags = "[" + tags + "]";
		}
		//处理内容
		String content = 
				dt.getPost_content()
				.replaceAll("(\r\n|\r|\n|\n\r)", "  \n")
				.replaceAll("<pre.*?>", "     ").replaceAll("&lt;pre.*?&gt;", "    ")
				.replaceAll("</pre>", " ").replaceAll("&lt;/pre&gt;", " ")
				.replaceAll("<code>", "     ")
				.replaceAll("</code>", " ")  		
				.replaceAll("^=", "     =") ;  
		content =  Pattern.compile("^\\*",Pattern.MULTILINE).matcher(content).replaceAll("   *");
		content = Pattern.compile("^/\\*", Pattern.MULTILINE).matcher(content).replaceAll("   /*"); 
  		return String.format(CONTENT_HEADER,
				dt.getPost_title(),
				dt.getCategries(), 
				tags,
				content); 
	}
	
	/**
	 * 生成文件名 
	 */
	public static String generateFileName(ArchivesTable dt){
		StringBuilder builder = new StringBuilder();
		Date date = dt.getPost_date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		builder.append(sdf.format(date));
		builder.append(HEAD); 
		//文件名的space替换
		builder.append(dt.getPost_title().replaceAll(" ", "_").replaceAll("/", "__"));
		builder.append(TAIL);
		return builder.toString();
	}
	
	/**
	 *  过滤（只保留publish的数据)
	 */
	private static List<ArchivesTable>  filterPostTable(List<ArchivesTable> lsttab){
		List<ArchivesTable> tmp = new ArrayList<ArchivesTable>();
		for(ArchivesTable data : lsttab){
			if(data.getPost_type().equals("publish"))
			tmp.add(data);
		}
		return tmp;
	}
}
