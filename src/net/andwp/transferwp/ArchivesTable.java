package net.andwp.transferwp;
import java.util.Date;

/**
 * 表结构， 文章的表
 * @author andwp
 *
 *
 导出的sql***************************************************  
SELECT 
IFNULL((
SELECT group_concat( term1.name, ' ' )
FROM puxterm_relationships ship1
INNER JOIN puxterm_taxonomy tax1 ON tax1.term_taxonomy_id = ship1.term_taxonomy_id
INNER JOIN puxterms term1 ON term1.term_id = tax1.term_id
WHERE p.id = ship1.object_id
AND tax1.taxonomy = 'post_tag'), '')as post_tag,
IFNULL((SELECT  term1.name  
FROM puxterm_relationships ship1
INNER JOIN puxterm_taxonomy tax1 ON tax1.term_taxonomy_id = ship1.term_taxonomy_id
INNER JOIN puxterms term1 ON term1.term_id = tax1.term_id
WHERE p.id = ship1.object_id
AND tax1.taxonomy = 'category' limit 0,1),'') as categries
,p.post_title, p.post_date, p.post_content, p.post_status AS post_type from puxposts  as p ORDER BY `post_tag`  DESC
 *
 *
 *
 *
 */
public class ArchivesTable {
	private Date post_date;
	private String post_title;
	private String post_content; 
	private String categries;
	/**
	 * 发表类型（publish , inherit, rubish..)
	 */
	private String post_type;
	/**
	 * ，分割开
	 */
	private String post_tag;  
	public Date getPost_date() {
		return post_date;
	}
	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getCategries() {
		return categries;
	}
	public void setCategries(String categries) {
		this.categries = categries;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	} 
	public String getPost_type() {
		return post_type;
	}
	public void setPost_type(String post_type) {
		this.post_type = post_type;
	}
	
	public String getPost_tag() {
		return post_tag;
	}
	public void setPost_tag(String post_tag) {
		this.post_tag = post_tag;
	}
	
	@Override
	public String toString() {
		 StringBuilder builder = new StringBuilder();
		 builder.append(" post_date:" + post_date);
		 builder.append(" post_title:" + post_title);
		 builder.append(" post_content:" + post_content);
		 builder.append(" categries:" + categries);
		 builder.append(" post_type:" + post_type); 
		 builder.append(" post_tag:" + post_tag);
		if(post_title == null || post_content == null) return null;
		return builder.toString();
	}
}
